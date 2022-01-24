package in.co.bytehub.mbankaa.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import in.co.bytehub.mbankaa.response.ErrorResponse;
import in.co.bytehub.mbankaa.security.exception.AuthenticationFailedException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final String jwtSignKey;
    private final ObjectMapper objectMapper;
    private final List<String> allowedUrls;

    public AuthenticationFilter(String jwtSignKey, ObjectMapper objectMapper, List<String> allowedUrls) {
        this.jwtSignKey = jwtSignKey;
        this.objectMapper = objectMapper;
        this.allowedUrls = allowedUrls;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authHeader = Optional.ofNullable(req.getHeader("Authorization")).orElseThrow(() -> new AuthenticationFailedException("Authorization header is not present"));

            if (!authHeader.startsWith("Bearer "))
                throw new AuthenticationFailedException("Invalid Token");

            String jwtToken = authHeader.replaceAll("Bearer ", "");

            Jws<Claims> claimsJws;
            try {
                claimsJws = Jwts.parser()
                        .setSigningKey(jwtSignKey)
                        .parseClaimsJws(jwtToken);
            } catch (ExpiredJwtException e) {
                LOGGER.error("Token is expired", e);
                throw new AuthenticationFailedException("Expired Token");
            } catch (SignatureException | MalformedJwtException e) {
                LOGGER.error("Token is distorted", e);
                throw new AuthenticationFailedException("Invalid Token");
            }
            Optional.ofNullable(claimsJws.getBody().getExpiration()).orElseThrow(() -> new AuthenticationFailedException("Expired Token"));
            String userName = Optional.ofNullable(claimsJws.getBody().get("name", String.class)).orElseThrow(() -> new AuthenticationFailedException("Invalid Token"));
            String role = Optional.ofNullable(claimsJws.getBody().get("role", String.class)).orElseThrow(() -> new AuthenticationFailedException("Invalid Token"));
            
            req.setAttribute("user", userName);
            req.setAttribute("role", role);

            filterChain.doFilter(req, res);

        } catch (AuthenticationFailedException e) {
            LOGGER.error("Authentication Failed : ", e);
            res.setContentType("application/json");
            res.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse().setMessage(e.getMessage())));
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return allowedUrls.stream().anyMatch(s -> request.getRequestURI().startsWith(s));
    }
}
