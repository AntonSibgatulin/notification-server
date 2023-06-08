package jp.konosuba.notificationserver.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.konosuba.notificationserver.data.user.token.AuthToken;
import jp.konosuba.notificationserver.data.user.token.TokenRepository;
import jp.konosuba.notificationserver.data.user.user.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,@NonNull  HttpServletResponse response,@NonNull  FilterChain filterChain) throws ServletException, IOException {
        final var token = request.getParameter("token");
        String jwt = token;
        String userPhone = null;
        if (token == null){
            filterChain.doFilter(request,response);
            return;
        }

        try {
            userPhone = jwtService.extractPhone(token);
        }catch(Exception e){
            e.printStackTrace();
            filterChain.doFilter(request,response);
            return;
        }

        if(userPhone!=null){
            AuthToken authToken = tokenRepository.getAuthTokenByToken(token);
            if (authToken == null){
                filterChain.doFilter(request,response);
                return;
            }
        }

        if (userPhone != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userPhone);
            if (jwtService.isTokenValid(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        request
                ));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

        }
        filterChain.doFilter(request,response);


    }
}
