package ru.market.auth.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;
import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.ICostLimitService;
import ru.market.domain.service.ICostService;
import ru.market.domain.service.IMoneyAccountService;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;
import ru.market.utils.JSONObjectUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@UrlFilter(urlPatterns = {"/cost", "/cost/*"})
public class CostRequestFilter implements AuthFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CostRequestFilter.class);

    private SessionDataManager sessionDataManager;
    private ICostService costService;
    private ICostLimitService costLimitService;
    private IMoneyAccountService moneyAccountService;

    public CostRequestFilter(SessionDataManager sessionDataManager,
                             ICostLimitService costLimitService,
                             ICostService costService,
                             IMoneyAccountService moneyAccountService) {

        this.sessionDataManager = sessionDataManager;
        this.costLimitService = costLimitService;
        this.costService = costService;
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = sessionDataManager.getUserData().getPersonId();
        Set<Long> allCostLimitId = costLimitService.getAllIdByPersonId(personId);
        LOGGER.debug("Id лимитов на затраты: {} полученные по personId = {}", allCostLimitId, personId);

        Set<Long> allIdByCostLimitIds = costService.getAllIdByCostLimitIds(allCostLimitId);
        LOGGER.debug("Id затрат: {} полученные по всем id лимитов = {} и personId = {}",
                allIdByCostLimitIds, allCostLimitId, personId);

        Set<Long> allMoneyAccountId = moneyAccountService.getAllIdByPersonId(personId);
        LOGGER.debug("Id денежных счетов: {} полученные по personId = {}", allMoneyAccountId, personId);

        String requestMethod = request.getMethod();

        ServletInputStream servletInputStream = request.getInputStream();

        boolean isWell = false;

        CostNoIdDTO costDTO = null;

        switch (requestMethod){
            case "PUT":{
                costDTO = JSONObjectUtil.getJsonObjectFromInputStream(servletInputStream, CostNoIdDTO.class);
                break;
            }
            case "POST":{
                costDTO = JSONObjectUtil.getJsonObjectFromInputStream(servletInputStream, CostDTO.class);
                break;
            }
            case "GET":{
                isWell = Utils.checkIdInServletPath(request.getServletPath(), "cost/(\\S+)/.+", allCostLimitId);
                LOGGER.debug("[GET request] Servlet path: [{}] CostLimitIds = {} personId = {}",
                        request.getServletPath(), allCostLimitId, personId);
                break;
            }
            case "DELETE":{
                isWell = Utils.checkIdInServletPath(request.getServletPath(), "cost/(\\S+)", allIdByCostLimitIds);
                LOGGER.debug("[DELETE request] Servlet path: [{}] CostIds = {} personId = {}",
                        request.getServletPath(), allIdByCostLimitIds, personId);
                break;
            }
        }

        if(costDTO == null && ("PUT".equals(requestMethod) || "POST".equals(requestMethod))){
            LOGGER.error("Данные по затрате не заданы. Request method = {} . Запрос: [{}] не прошел валидацию",
                    requestMethod, request.getServletPath());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
            return;
        } else if(costDTO != null){
            isWell = checkCostLimitAndMoneyAccount(costDTO, allCostLimitId, allMoneyAccountId);
        }

        if(isWell){
            authChain.doFilter(request, response, filterChain);
        } else {
            LOGGER.error("Запрос: [{}] не прошел валидацию", request.getServletPath());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    private boolean checkCostLimitAndMoneyAccount(CostNoIdDTO costDTO, Set<Long> allCostLimitId, Set<Long> allMoneyAccountId){
        sessionDataManager.setCurrentRequestBody(costDTO);

        Long costLimitId = costDTO.getCostLimitId();
        Long moneyAccountId = costDTO.getMoneyAccountId();
        LOGGER.debug("CostDTO = {} . CostLimitIds = {} . MoneyAccountIds = {}", costDTO, allCostLimitId, allMoneyAccountId);

        if(!allCostLimitId.contains(costLimitId)){
            return false;
        }

        return allMoneyAccountId.contains(moneyAccountId);
    }
}
