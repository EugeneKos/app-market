package ru.market.auth.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;
import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.IMoneyAccountService;
import ru.market.dto.operation.OperationBasedDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationTransferDTO;
import ru.market.utils.JSONObjectUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@UrlFilter(urlPatterns = "/operation*")
public class OperationRequestFilter implements AuthFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationRequestFilter.class);

    private SessionDataManager sessionDataManager;
    private IMoneyAccountService moneyAccountService;

    public OperationRequestFilter(SessionDataManager sessionDataManager, IMoneyAccountService moneyAccountService) {

        this.sessionDataManager = sessionDataManager;
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = sessionDataManager.getUserData().getPersonId();
        Set<Long> allMoneyAccountId = moneyAccountService.getAllIdByPersonId(personId);
        LOGGER.debug("Id денежных счетов: {} полученные по personId = {}", allMoneyAccountId, personId);

        String servletPath = request.getServletPath();
        ServletInputStream servletInputStream = request.getInputStream();

        OperationBasedDTO operationBasedDTO = null;

        boolean isWell = false;

        if(servletPath.contains("enrollment") || servletPath.contains("debit")){
            OperationEnrollDebitDTO enrollDebitDTO = JSONObjectUtil.getJsonObjectFromInputStream(
                    servletInputStream, OperationEnrollDebitDTO.class
            );

            if(enrollDebitDTO == null){
                LOGGER.error("Данные по операции не заданы. Запрос: [{}] не прошел валидацию", request.getServletPath());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
                return;
            }
            LOGGER.debug("Servlet path: [{}] OperationEnrollDebitDTO = {} MoneyAccountIds = {} personId = {}",
                    servletPath, enrollDebitDTO, allMoneyAccountId, personId);

            operationBasedDTO = enrollDebitDTO;
            isWell = allMoneyAccountId.contains(enrollDebitDTO.getMoneyAccountId());

        } else if(servletPath.contains("transfer")){
            OperationTransferDTO transferDTO = JSONObjectUtil.getJsonObjectFromInputStream(
                    servletInputStream, OperationTransferDTO.class
            );

            if(transferDTO == null){
                LOGGER.error("Данные по операции не заданы. Запрос: [{}] не прошел валидацию", request.getServletPath());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
                return;
            }
            LOGGER.debug("Servlet path: [{}] OperationTransferDTO = {} MoneyAccountIds = {} personId = {}",
                    servletPath, transferDTO, allMoneyAccountId, personId);

            Long fromMoneyAccountId = transferDTO.getFromMoneyAccountId();
            Long toMoneyAccountId = transferDTO.getToMoneyAccountId();

            if(fromMoneyAccountId.equals(toMoneyAccountId)){
                LOGGER.error("Некорректный запрос на операцию перевода. " +
                        "Попытка произвести операцию с одним и тем же денежным счетом");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
                return;
            }

            operationBasedDTO = transferDTO;
            isWell = allMoneyAccountId.contains(fromMoneyAccountId);

        } else if(servletPath.contains("money-account")){
            isWell = Utils.checkIdInServletPath(servletPath, "money-account/(\\S+)", allMoneyAccountId);
            LOGGER.debug("Servlet path: [{}] MoneyAccountIds = {} personId = {}",
                    servletPath, allMoneyAccountId, personId);
        }

        if(isWell){
            if(operationBasedDTO != null){
                sessionDataManager.setCurrentRequestBody(operationBasedDTO);
            }
            authChain.doFilter(request, response, filterChain);
        } else {
            LOGGER.error("Запрос: [{}] не прошел валидацию", request.getServletPath());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }
}
