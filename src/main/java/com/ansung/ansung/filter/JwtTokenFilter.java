package com.ansung.ansung.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.utils.JwtTokenProvider;
import com.ansung.ansung.utils.JwtTokenProvider.TokenType;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter{
	private final HandlerExceptionResolver resolver;
	
	//에러처리 resolver 주입받기
	public JwtTokenFilter(HandlerExceptionResolver resolver) {
		this.resolver=resolver;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String accessToken = getTokenFromHeader(request);
		CommonApiException exception = null;
		try {
			if(accessToken != null && JwtTokenProvider.validateToken(TokenType.ACCESS_TOKEN, accessToken)) {
				Authentication authentication = JwtTokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				filterChain.doFilter(request, response);
			}else {
				//엑세스 토큰 검증 실패
				exception = new CommonApiException(CommonExceptionEnum.UNAUTHROIZED);
			}
			//엑세스 토큰 만료시 리프레시 토큰을 이용한 엑세스 토큰 재발급
		}catch(ExpiredJwtException e) {
			String refreshToken = getRefreshTokenFromCookies(request.getCookies());
			if(refreshToken != null && JwtTokenProvider.validateToken(TokenType.REFRESH_TOKEN, refreshToken)) {
				String reIssuedAccessToken = JwtTokenProvider.reIssueAccessToken(refreshToken);
				Authentication authentication = JwtTokenProvider.getAuthentication(reIssuedAccessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				response.setHeader("Authorization", reIssuedAccessToken);
				
				filterChain.doFilter(request, response);
			}else {
				//리프레시 토큰 부재
				exception = new CommonApiException(CommonExceptionEnum.UNAUTHROIZED);
			}
		}
		catch(JwtException e) {
			//토큰 위조
			exception = new CommonApiException(CommonExceptionEnum.UNAUTHROIZED);
		}finally {
			if(exception != null) {
				this.resolver.resolveException(request, response, null, exception);
			}
		}
	}
	
	private String getTokenFromHeader(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);  // "Bearer " 부분을 제거한 토큰 반환
        }
        return null;
	}
	
	private String getRefreshTokenFromCookies(Cookie[] cookies) {
		if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh_token")) {
                    return cookie.getValue();
                }
            }
        }
		return null;
	}
}
