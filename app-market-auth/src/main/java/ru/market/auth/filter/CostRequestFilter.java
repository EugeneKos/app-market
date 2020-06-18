package ru.market.auth.filter;

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
        Set<Long> allIdByCostLimitIds = costService.getAllIdByCostLimitIds(allCostLimitId);
        Set<Long> allMoneyAccountId = moneyAccountService.getAllIdByPersonId(personId);

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
                break;
            }
            case "DELETE":{
                isWell = Utils.checkIdInServletPath(request.getServletPath(), "cost/(\\S+)", allIdByCostLimitIds);
                break;
            }
        }

        if(costDTO == null && ("PUT".equals(requestMethod) || "POST".equals(requestMethod))){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
            return;
        } else if(costDTO != null){
            isWell = checkCostLimitAndMoneyAccount(costDTO, allCostLimitId, allMoneyAccountId);
        }

        if(isWell){
            authChain.doFilter(request, response, filterChain);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    private boolean checkCostLimitAndMoneyAccount(CostNoIdDTO costDTO, Set<Long> allCostLimitId, Set<Long> allMoneyAccountId){
        sessionDataManager.setCurrentRequestBody(costDTO);

        Long costLimitId = costDTO.getCostLimitId();
        Long moneyAccountId = costDTO.getMoneyAccountId();

        if(!allCostLimitId.contains(costLimitId)){
            return false;
        }

        return allMoneyAccountId.contains(moneyAccountId);
    }
}
