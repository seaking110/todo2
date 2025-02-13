package com.example.global.config;

import com.example.global.util.Const;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    // 화이트 리스트, 해당 URI로 오는 요청은 필터로 검증하지 않음
    private static final String[] WHITE_LIST = {"/auth/login","/auth/signup"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        //화이트 리스트가 아니라면
        if (!isWhiteList(requestURI)) {
            // 세션 정보 호출
            HttpSession session = httpRequest.getSession(false);
            // 정보가 없으면 로그인 먼저 요청
            if (session == null || session.getAttribute(Const.LOGIN_MEMBER) == null) {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpResponse.getWriter().write("please login first");
                return;
            }

            log.info("로그인에 성공했습니다.");
        }
        chain.doFilter(request,response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
