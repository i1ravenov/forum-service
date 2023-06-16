package telran.java47.forum.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.java47.forum.model.Comment;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	String id;
	String title;
	String content;
	String author;
	LocalDateTime dateCreated;
	Set<String> tags;
	int likes;
	List<Comment> comments;
}
