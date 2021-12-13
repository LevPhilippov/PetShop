package lev.philippov.originmvc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class SessionPopulationAfterAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if(!Objects.equals(authentication.getName(), "anonymousUser") && authentication.getName()!=null && request.getSession().getAttribute("user") == null) {
            logger.debug("Principal is: {}", authentication.getPrincipal());
            logger.debug("User {} has added into the session.", authentication.getName());
            request.getSession().setAttribute("user", authentication.getName());
        }
        filterChain.doFilter(request,response);
    }
}
