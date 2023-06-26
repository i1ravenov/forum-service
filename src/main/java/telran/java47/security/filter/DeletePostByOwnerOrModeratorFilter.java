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
import telran.java47.accounting.model.UserRole;
import telran.java47.forum.dao.ForumRepository;
import telran.java47.forum.model.Post;
import telran.java47.security.model.User;

@Component
@RequiredArgsConstructor
@Order(50)
public class DeletePostByOwnerOrModeratorFilter implements Filter {

	final ForumRepository forumRepository;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		if (checkEndPoint(request.getMethod(), path)) {
			User user = (User) request.getUserPrincipal();
			String login = request.getUserPrincipal().getName();
			String[] arr = path.split("/");
			String postId = arr[arr.length - 1];
			Post post = forumRepository.findById(postId).get();
			String author = post.getAuthor();
			if (!(login.equalsIgnoreCase(author) || user.getRoles().contains(UserRole.MODERATOR))) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return "DELETE".equalsIgnoreCase(method) && path.matches("/forum/post/[a-zA-Z0-9_-]+/?");
	}
}