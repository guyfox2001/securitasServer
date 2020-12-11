package com.restfulapi.securitas.security;

import com.restfulapi.securitas.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Если в этом классе или каком-либо другом из папки "security" будет нихуя не понятно, не переживай. Это уже работает
// и меняться в будущем не будет (я так думаю), поэтому можно просто забить и не трогать.
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    // для получения связки логин/пароль
    private final UserService userDetailsService;
    // для создания нового токена
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(UserService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        // Если в хедере запроса есть параметр "Authorization", его значение запишется в authorizationHeader.
        // В параметре "Authorization" клиент будет передавать токен пользователя.
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Если authorizationHeader не пустой и начинается с символов "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // substring(7) для обрезания префикса "Bearer"
            jwt = authorizationHeader.substring(7);
            // достаем из токена логин пользователя
            username = jwtUtil.extractUsername(jwt);
        }
        // если удалось достать логин
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // получаем связку логин/пароль
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // проверяем токен
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // если токен не просрочен, то аутентифицируем пользователя
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        if(jwt == null)
            filterChain.doFilter(request, response);
        else {
            HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
            requestWrapper.addHeader("Authorization", jwt);
            filterChain.doFilter(requestWrapper, response);
        }
    }
}
