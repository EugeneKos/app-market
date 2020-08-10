package ru.market.auth.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;
import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.IMoneyAccountService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@UrlFilter(urlPatterns = "/money-account*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/money-account",
                methods = {ExcludeRequestMethod.Method.PUT, ExcludeRequestMethod.Method.GET})}
)
public class MoneyAccountRequestFilter implements AuthFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyAccountRequestFilter.class);

    private SessionDataManager sessionDataManager;
    private IMoneyAccountService moneyAccountService;

    public MoneyAccountRequestFilter(SessionDataManager sessionDataManager, IMoneyAccountService moneyAccountService) {
        this.sessionDataManager = sessionDataManager;
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = sessionDataManager.getUserData().getPersonId();
        Set<Long> allMoneyAccountId = moneyAccountService.getAllIdByPersonId(personId);
        LOGGER.debug("Id денежных счетов: {} полученные по personId = {}", allMoneyAccountId, personId);

        boolean isWell = false;

        String requestMethod = request.getMethod();

        if("GET".equals(requestMethod) || "DELETE".equals(requestMethod)){
            isWell = Utils.checkIdInServletPath(
                    request.getServletPath(), "money-account/(\\S+)", allMoneyAccountId
            );
            LOGGER.debug("[{} request] Servlet path: [{}] MoneyAccountIds = {} personId = {}",
                    requestMethod, request.getServletPath(), allMoneyAccountId, personId);
        }

        if(isWell){
            authChain.doFilter(request, response, filterChain);
        } else {
            LOGGER.error("Запрос: [{}] не прошел валидацию", request.getServletPath());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }

    }
}
