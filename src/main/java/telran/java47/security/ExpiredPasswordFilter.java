package telran.java47.security;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;
import telran.java47.accounting.dao.UserAccountRepository;
import telran.java47.accounting.model.UserAccount;

@Component
@Order(10)
@RequiredArgsConstructor
public class ExpiredPasswordFilter extends GenericFilterBean {

	final UserAccountRepository userAccountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Principal principal = request.getUserPrincipal();
		if (principal != null && checkEndpoint(request.getMethod(), request.getServletPath())) {
			String login = principal.getName();
			UserAccount user = userAccountRepository.findById(login).orElse(null);
			if (user != null && user.isPasswordExpired()) {
				response.sendError(403, "Password is expired");
				return;
			}
		}

		chain.doFilter(request, response);
	}

	private boolean checkEndpoint(String method, String path) {
		return !(HttpMethod.PUT.matches(method) && path.matches("/account/password/?"));
	}
}
