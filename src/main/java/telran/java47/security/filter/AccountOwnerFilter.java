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

@Component
@Order(30)
public class AccountOwnerFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		if (checkEndPoint(request.getMethod(), path)) {
			String[] splittedPath = path.split("/");
			String owner = splittedPath[splittedPath.length - 1];
			if (!owner.equalsIgnoreCase(request.getUserPrincipal().getName())) {
				response.sendError(403, "access denied");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return ("PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))
				&& path.matches("/account/user/\\w+/?");
	}

}
