package telran.java47.forum.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	String user;
	String message;
	LocalDateTime dateCreated;
	int likes;
}
