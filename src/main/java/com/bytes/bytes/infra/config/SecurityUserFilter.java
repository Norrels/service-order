package com.bytes.bytes.infra.config;

import com.bytes.bytes.infra.provider.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityUserFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    private static final List<String> PERMIT_LIST = List.of(
            "/kitchen/user/authenticate",
            "/kitchen/product/category/.*",
            "/customer",
            "/order"
    );

    private static final List<String> AUTH_LIST_STARTS_WITH = List.of(
            "/kitchen",
            "/customer",
            "/order"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();

        if (header != null) {
            var token = jwtProvider.validateToken(header);

            if(token == null){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            request.setAttribute("user_id", token.getSubject());
            var roles = token.getClaim("roles").asList(String.class);

            var grants = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    .toList();

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isNotProtectedEndpoint(String requestURI) {
        return PERMIT_LIST.stream().noneMatch(requestURI::matches);
    }

    private boolean protectedList(String requestURI) {
        return AUTH_LIST_STARTS_WITH.stream().noneMatch(requestURI::startsWith);
    }
}
