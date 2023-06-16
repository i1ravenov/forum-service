package telran.java47.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Post {
	@Id
	String id;
	@Setter
	String title;
	@Setter
	String content;
	@Setter
	String author;
	@Setter
	LocalDateTime dateCreated;
	@Setter
	Set<String> tags = new HashSet<>();;
	@Setter
	int likes;
	List<Comment> comments = new ArrayList<>();
	
	public Post(String title, String content, String author, LocalDateTime dateCreated, Set<String> tags,
			int likes) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.content = content;
		this.author = author;
		this.dateCreated = dateCreated;
		this.tags = tags;
		this.likes = likes;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public void incrementLikes() {
		likes++;
	}
}
