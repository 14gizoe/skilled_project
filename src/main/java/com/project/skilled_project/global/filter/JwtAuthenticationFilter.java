package com.project.skilled_project.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.skilled_project.domain.user.dto.LoginRequestDto;
import com.project.skilled_project.domain.user.entity.User;
import com.project.skilled_project.domain.user.repository.RefreshTokenRepository;
import com.project.skilled_project.global.response.CommonResponse;
import com.project.skilled_project.global.response.ErrorResponse;
import com.project.skilled_project.global.util.JwtUtil;
import com.project.skilled_project.global.util.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtUtil jwtUtil;
  ObjectMapper objectMapper = new ObjectMapper();
  private final RefreshTokenRepository refreshTokenRepository;

  public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
    this.jwtUtil = jwtUtil;
    this.refreshTokenRepository = refreshTokenRepository;
    setFilterProcessesUrl("/api/users/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
          LoginRequestDto.class);

      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.getUsername(),
              requestDto.getPassword(),
              null
          )
      );
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain, Authentication authResult)
      throws IOException {
    User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

    String accessToken = jwtUtil.createAccessToken(user.getUsername());
    String refreshToken = jwtUtil.createRefreshToken(user.getUsername()).substring(7);
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
    refreshTokenRepository.save(user.getUsername(), refreshToken);

    response.setStatus(HttpServletResponse.SC_OK);

    String jsonResponse = objectMapper.writeValueAsString(
        CommonResponse.ok(null));

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponse);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

    String jsonResponse = objectMapper.writeValueAsString(
        new ErrorResponse("로그인에 실패하였습니다."));

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponse);
  }
}
