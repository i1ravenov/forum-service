package telran.java47.security;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java47.forum.dao.ForumRepository;
import telran.java47.forum.model.Post;

@Service("customSecurity")
@RequiredArgsConstructor
public class CustomWebSecurity {

	final ForumRepository forumRepository;
	
	public boolean checkPostAuthor(String postId, String userName) {
		Post post = forumRepository.findById(postId).orElse(null);
		return post != null && userName.equalsIgnoreCase(post.getAuthor());
	}
}
