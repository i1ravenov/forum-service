package telran.java47.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java47.accounting.dao.UserAccountRepository;
import telran.java47.accounting.model.UserAccount;

@Component
@Order(20)
@RequiredArgsConstructor
public class AdminFilter implements Filter {

	final UserAccountRepository userAccountRepository;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			String login = request.getUserPrincipal().getName();
			UserAccount userAccount = userAccountRepository.findById(login).orElse(null);
			String path = request.getServletPath();
			if (!userAccount.getRoles().contains("ADMIN") && !isOwner(path, login)) {
				response.sendError(403, "access is not allowed");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean isOwner(String path, String login) {
		return path.contains(login) && path.matches("/account/user/\\w+/?");
	}

	private boolean checkEndPoint(String method, String path) {
		return "DELETE".equalsIgnoreCase(method) || path.matches("/account/user/\\w+/role/\\w+/?");	
	}
}
