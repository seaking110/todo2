package com.example.todo.filter;

import com.example.member.session.Const;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.http.HttpRequest;

@Slf4j
public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/signup","/login"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);

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
