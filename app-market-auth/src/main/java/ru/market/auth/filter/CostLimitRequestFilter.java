package ru.market.auth.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;
import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.ICostLimitService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@UrlFilter(urlPatterns = "/cost-limit*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/cost-limit",
                methods = {ExcludeRequestMethod.Method.PUT, ExcludeRequestMethod.Method.GET})}
)
public class CostLimitRequestFilter implements AuthFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CostLimitRequestFilter.class);

    private SessionDataManager sessionDataManager;
    private ICostLimitService costLimitService;

    public CostLimitRequestFilter(SessionDataManager sessionDataManager, ICostLimitService costLimitService) {
        this.sessionDataManager = sessionDataManager;
        this.costLimitService = costLimitService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = sessionDataManager.getUserData().getPersonId();
        Set<Long> allCostLimitId = costLimitService.getAllIdByPersonId(personId);
        LOGGER.debug("Id лимитов на затраты: {} полученные по personId = {}", allCostLimitId, personId);

        boolean isWell;

        switch (request.getMethod()){
            case "GET":{
                isWell = Utils.checkIdInServletPath(
                        request.getServletPath(), "cost-limit/info/(\\S+)/.+", allCostLimitId
                );
                LOGGER.debug("[GET request] Servlet path: [{}] CostLimitIds = {} personId = {}",
                        request.getServletPath(), allCostLimitId, personId);
                break;
            }
            case "DELETE":{
                isWell = Utils.checkIdInServletPath(
                        request.getServletPath(), "cost-limit/(\\S+)", allCostLimitId
                );
                LOGGER.debug("[DELETE request] Servlet path: [{}] CostLimitIds = {} personId = {}",
                        request.getServletPath(), allCostLimitId, personId);
                break;
            }
            default:
                isWell = false;
        }

        if(isWell){
            authChain.doFilter(request, response, filterChain);
        } else {
            LOGGER.error("Запрос: [{}] не прошел валидацию", request.getServletPath());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }

    }
}
