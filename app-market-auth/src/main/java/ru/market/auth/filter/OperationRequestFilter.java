package ru.market.auth.filter;

import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;
import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.IBankAccountService;
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
    private SessionDataManager sessionDataManager;
    private IBankAccountService bankAccountService;

    public OperationRequestFilter(SessionDataManager sessionDataManager, IBankAccountService bankAccountService) {

        this.sessionDataManager = sessionDataManager;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = sessionDataManager.getUserData().getPersonId();
        Set<Long> allAccountId = bankAccountService.getAllAccountIdByPersonId(personId);

        String servletPath = request.getServletPath();
        ServletInputStream servletInputStream = request.getInputStream();

        OperationBasedDTO operationBasedDTO = null;

        boolean isWell = false;

        if(servletPath.contains("enrollment") || servletPath.contains("debit")){
            OperationEnrollDebitDTO enrollDebitDTO = JSONObjectUtil.getJsonObjectFromInputStream(
                    servletInputStream, OperationEnrollDebitDTO.class
            );

            if(enrollDebitDTO == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
                return;
            }

            operationBasedDTO = enrollDebitDTO;
            isWell = allAccountId.contains(enrollDebitDTO.getAccountId());

        } else if(servletPath.contains("transfer")){
            OperationTransferDTO transferDTO = JSONObjectUtil.getJsonObjectFromInputStream(
                    servletInputStream, OperationTransferDTO.class
            );

            if(transferDTO == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
                return;
            }

            Long fromAccountId = transferDTO.getFromAccountId();
            Long toAccountId = transferDTO.getToAccountId();

            if(fromAccountId.equals(toAccountId)){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
                return;
            }

            operationBasedDTO = transferDTO;
            isWell = allAccountId.contains(fromAccountId);

        } else if(servletPath.contains("account")){
            isWell = containsAccountId(servletPath, allAccountId);
        }

        if(isWell){
            if(operationBasedDTO != null){
                sessionDataManager.setCurrentRequestBody(operationBasedDTO);
            }
            authChain.doFilter(request, response, filterChain);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    private boolean containsAccountId(String servletPath, Set<Long> allAccountId){
        int lastIndexOfSlash = servletPath.lastIndexOf("operation/account/");
        if(lastIndexOfSlash == -1){
            return false;
        }
        lastIndexOfSlash = lastIndexOfSlash + 18;

        String possibleAccountId = servletPath.substring(lastIndexOfSlash, servletPath.length());

        if(!possibleAccountId.matches("\\d+")){
            return false;
        }

        return allAccountId.contains(Long.parseLong(possibleAccountId));
    }
}
