package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.model.User;

public interface CommentRepository extends CrudRepository<Comment, Long> {

	boolean existsByAuthor(User author);

}
