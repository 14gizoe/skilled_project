package com.project.skilled_project.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.skilled_project.domain.user.repository.RefreshTokenRepository;
import com.project.skilled_project.global.response.ErrorResponse;
import com.project.skilled_project.global.util.JwtUtil;
import com.project.skilled_project.global.util.UserDetailsImpl;
import com.project.skilled_project.global.util.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final RefreshTokenRepository refreshTokenRepository;

  ObjectMapper objectMapper = new ObjectMapper();

  public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService,
      RefreshTokenRepository refreshTokenRepository) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain filterChain) throws ServletException, IOException {

    String tokenValue = jwtUtil.getJwtFromHeader(req);

    if (StringUtils.hasText(tokenValue)) {
      int tokenStatus = jwtUtil.validateToken(tokenValue);

      if (tokenStatus == 1) {
        log.error("Token Error");
        return;
      } else if (tokenStatus == 2) {
        try {
          Claims info = jwtUtil.getExpiredTokenClaims(tokenValue);
          if (refreshTokenRepository.existsByKey(info.getSubject())) {
            String newToken = jwtUtil.createAccessToken(info.getSubject());
            res.addHeader(JwtUtil.AUTHORIZATION_HEADER, newToken);
            res.setStatus(HttpServletResponse.SC_OK);

            String jsonResponse = objectMapper.writeValueAsString(
                new ErrorResponse("새로운 Acces Token이 발급되었습니다."));

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(jsonResponse);
          } else {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            String jsonResponse = objectMapper.writeValueAsString(
                new ErrorResponse("Access Token과 Refresh Token이 모두 만료되었습니다."));
            refreshTokenRepository.delete(info.getSubject());

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(jsonResponse);
          }
          return;
        } catch (Exception e) {
          log.error(e.getMessage());
          return;
        }
      } else { // 유효한 토큰
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        setAuthentication(info.getSubject());
      }
    }
      filterChain.doFilter(req, res);
  }

  // 인증 처리
  public void setAuthentication(String username) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = createAuthentication(username);
    context.setAuthentication(authentication);

    SecurityContextHolder.setContext(context);
  }

  // 인증 객체 생성
  private Authentication createAuthentication(String username) {
    UserDetails userDetails = new UserDetailsImpl(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, null);
  }
}
