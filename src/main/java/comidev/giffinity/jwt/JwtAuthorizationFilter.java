package comidev.giffinity.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import comidev.giffinity.service.UserService;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenProvider;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtenemos el Token
        String token = getTokenByRequest(request);

        // Validamos el Token
        boolean tokenIsValid = StringUtils.hasText(token) && jwtTokenProvider.tokenIsValid(token);
        if (tokenIsValid) {

            // Obtenemos su Username
            String username = jwtTokenProvider.getUsernameByToken(token);

            // Cargamos el Usuario asociado al Token
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Establecemos la seguridad
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenByRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        boolean hasToken = StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer");

        if (hasToken) {
            String token = bearerToken.substring(7, bearerToken.length());
            return token;
        }

        return null;
    }
}
