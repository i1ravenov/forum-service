package telran.java47.forum.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.java47.forum.model.Post;

public interface ForumRepository extends MongoRepository<Post, String> {

	public List<Post> findPostsByAuthor(String author);

	Stream<Post> findByTagsInIgnoreCase(List<String> tags);

	@Query("{dateCreated: {$gte: ?0, $lt: ?1}}")
	public List<Post> findAllByPeriod(LocalDate dateFrom, LocalDate dateTo);
}
