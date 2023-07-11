package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CommentRepository;
import jakarta.transaction.Transactional;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commRepo;
	
	@Transactional
	public Comment saveComment(Comment comment) {
		return this.commRepo.save(comment);
	}
	
	@Transactional
	public void deleteComment(Long id) {
		this.commRepo.deleteById(id);
	}

	public boolean existsByAuthor(User author) {
		return this.commRepo.existsByAuthor(author);
	}

	public Comment getComment(Long id) {
		return this.commRepo.findById(id).get();
	}
}
