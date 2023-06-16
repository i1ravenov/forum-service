package telran.java47.forum.service;

import java.util.List;

import telran.java47.forum.dto.CommentDto;
import telran.java47.forum.dto.PeriodDto;
import telran.java47.forum.dto.PostDto;
import telran.java47.forum.dto.PostUpdateDto;

public interface ForumService {
	
	PostDto createPost(String author, PostUpdateDto post);
	
	PostDto getPostById(String id);
	
	void addLikeToPost(String id);
	
	List<PostDto> findPostsByAuthor(String author);
	
	PostDto addCommentToPost(String postId, String author, CommentDto comment);

	PostDto deletePost(String id);
	
	List<PostDto> findPostsByTags(List<String> tags);
	
	List<PostDto> findPostsByPeriod(PeriodDto period);
	
	PostDto updatePost(String id, PostUpdateDto postUpdateDto);
}
