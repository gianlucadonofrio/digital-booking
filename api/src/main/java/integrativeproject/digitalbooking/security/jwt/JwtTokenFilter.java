package integrativeproject.digitalbooking.security.jwt;

import integrativeproject.digitalbooking.service.impl.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(request);

            if (token != null && jwtProvider.validateToken(token)) {

                String userName = jwtProvider.getUserNameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetailsService,
                                null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        } catch (Exception e) {
            logger.error("Failed method doFilter " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

        //Obtenemos el token sin Bearer + el espacio
        private String getToken (HttpServletRequest request){

            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer"))
                return header.replace("Bearer ", "");
            return null;
        }

    }