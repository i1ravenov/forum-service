package telran.java47.forum.dto;
 
import java.util.Set;

import lombok.Getter;

@Getter
public class PostUpdateDto {
	String title;
	String content;
	Set<String> tags;
}
