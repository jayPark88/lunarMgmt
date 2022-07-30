package com.lunar.lunarMgmt.common.sercurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunar.lunarMgmt.common.exception.ExpiredTokenException;
import com.lunar.lunarMgmt.common.exception.ForbiddenTokenException;
import com.lunar.lunarMgmt.common.exception.NotFoundTokenException;
import com.lunar.lunarMgmt.common.exception.UncheckedException;
import com.lunar.lunarMgmt.common.exception.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.Column;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);

    } catch (ForbiddenTokenException | NotFoundTokenException | ExpiredTokenException ex) {
      log.error("exception handler filter : {}", ex.getMessage());
      setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, ex);
    }
  }

  public void setErrorResponse(HttpStatus status, HttpServletResponse response, UncheckedException exception) {

    response.setStatus(status.value());
    response.setContentType("application/json");
    ErrorResponse errResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(exception.getErrorCode().getMessage())
            .code(exception.getErrorCode().getCode())
            .status(exception.getErrorCode().getStatus())
            .build();

    try {
      response.setStatus(exception.getErrorCode().getStatus());
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(objectMapper.writeValueAsString(errResponse));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
