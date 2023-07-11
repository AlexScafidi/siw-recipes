package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.service.CommentService;


@Component
public class CommentValidator implements Validator {
	
	@Autowired
	private CommentService commService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Comment.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Comment comm = (Comment) target;
		if(comm.getTitle() != null && this.commService.existsByAuthor(comm.getAuthor()))
			errors.reject("comment.duplicate");
		
	}

}
