package telran.java47.forum.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.forum.dto.CommentDto;
import telran.java47.forum.dto.PeriodDto;
import telran.java47.forum.dto.PostDto;
import telran.java47.forum.dto.PostUpdateDto;
import telran.java47.forum.service.ForumService;

@RestController
@RequiredArgsConstructor
public class ForumController {

	final ForumService forumService;

	@PostMapping("/forum/post/{author}")
	PostDto addPost(@PathVariable String author, @RequestBody PostUpdateDto postUpdateDto) {
		return forumService.createPost(author, postUpdateDto);
	}
	
	@GetMapping("/forum/post/{id}")
	PostDto getPostById(@PathVariable String id) {
		return forumService.getPostById(id);
	}
	
	@PutMapping("/forum/post/{id}/like")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	void addLikeToPost(@PathVariable String id) {
		forumService.addLikeToPost(id);
	}
	
	@GetMapping("/forum/posts/author/{author}")
	List<PostDto> getPostsByAuthor(@PathVariable String author) {
		return forumService.findPostsByAuthor(author);
	}
	
	@PutMapping("/forum/post/{id}/comment/{author}")
	PostDto addCommentToPost(@PathVariable String id,
							 @PathVariable String author,
							 @RequestBody CommentDto commentCreateDto) {
		return forumService.addCommentToPost(id, author, commentCreateDto);
	}
	
	@DeleteMapping("/forum/post/{id}")
	PostDto deletePostById(@PathVariable String id) {
		return forumService.deletePost(id);
	}
	
	@PostMapping("/forum/posts/tags")
	List<PostDto> findPostsByTags(@RequestBody List<String> tags) {
		return forumService.findPostsByTags(tags);
	}
	
	@PostMapping("/forum/posts/period")
	List<PostDto> findPostsByPeriod(@RequestBody PeriodDto periodDto) {
		return forumService.findPostsByPeriod(periodDto);
	}
	
	@PutMapping("/forum/post/{id}")
	PostDto updatePost(@PathVariable String id, @RequestBody PostUpdateDto postUpdateDto) {
		return forumService.updatePost(id, postUpdateDto);
	}
}
