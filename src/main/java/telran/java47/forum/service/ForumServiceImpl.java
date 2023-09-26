package telran.java47.forum.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import telran.java47.forum.dao.ForumRepository;
import telran.java47.forum.dto.CommentDto;
import telran.java47.forum.dto.PeriodDto;
import telran.java47.forum.dto.PostDto;
import telran.java47.forum.dto.PostUpdateDto;
import telran.java47.forum.exceptions.PostNotFoundException;
import telran.java47.forum.model.Comment;
import telran.java47.forum.model.Post;

@RequiredArgsConstructor
@Service
public class ForumServiceImpl implements ForumService {

	final ForumRepository forumRepo;
	final ModelMapper modelMapper;

	@Override
	public PostDto createPost(String author, PostUpdateDto postUpdateDto) {
		Post post = new Post(postUpdateDto.getTitle(), 
							postUpdateDto.getContent(), 
							author, 
							LocalDateTime.now(), 
							new HashSet<String>(postUpdateDto.getTags()), 
							0);
		forumRepo.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto getPostById(String id) {
		Post post = forumRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Post with id " + id + " not found"));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public void addLikeToPost(String id) {
		Post post = forumRepo.findById(id).orElseThrow(() -> new PostNotFoundException());
		post.incrementLikes();
		forumRepo.save(post);
	}

	@Override
	public List<PostDto> findPostsByAuthor(String author) {
		return forumRepo.findPostsByAuthor(author)
				.stream()
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto addCommentToPost(String postId, String author, CommentDto comment) {
		Post post = forumRepo.findById(postId).orElseThrow(() -> new PostNotFoundException());
		Comment cmnt = new Comment(author, comment.getMessage(), LocalDateTime.now(), 0);
		post.addComment(cmnt);
		return modelMapper.map(forumRepo.save(post), PostDto.class);
	}

	@Override
	public PostDto deletePost(String id) {
		Post post = forumRepo.findById(id).orElseThrow(() -> new PostNotFoundException());
		forumRepo.deleteById(id);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> findPostsByTags(List<String> tags) {
		return forumRepo.findByTagsInIgnoreCase(tags)
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> findPostsByPeriod(PeriodDto period) {
		return forumRepo.findAllByPeriod(period.getDateFrom(), period.getDateTo())
				.stream()
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto updatePost(String id, PostUpdateDto postUpdateDto) {
		Post post = forumRepo.findById(id).orElseThrow(() -> new PostNotFoundException());
		String content = postUpdateDto.getContent();
		if (content != null) {
			post.setContent(content);
		}
		String title = postUpdateDto.getTitle();
		if (title != null) {
			post.setTitle(title);
		}
		Set<String> tags = postUpdateDto.getTags();
		if (tags != null) {
			tags.forEach(post::addTag);
		}
		forumRepo.save(post);
		return modelMapper.map(post, PostDto.class);
	}

}
