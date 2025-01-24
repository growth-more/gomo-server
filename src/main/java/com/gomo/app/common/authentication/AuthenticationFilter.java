package com.gomo.app.common.authentication;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gomo.app.member.domain.model.Member;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String cookies = request.getHeader("Cookie");
			Member member = null;

			if (member != null) {
				SessionMember sessionMember = SessionMember.of(null, null, null, null, null);
				MemberContext.addSessionMember(sessionMember);
			}

			filterChain.doFilter(request, response);
		} finally {
			MemberContext.clear();
		}
	}
}
