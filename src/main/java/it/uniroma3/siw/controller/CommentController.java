package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CommentService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.validation.CommentValidator;
import jakarta.validation.Valid;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private CommentValidator commValidator;
	@Autowired
	private RecipeService recService;
	@Autowired
	private CredentialsService credentialsService;
	
	@GetMapping("/user/formNewComment/{recipeId}")
	public String formNewComment(@PathVariable("recipeId") Long recipeId, Model model) {
		model.addAttribute("recipeId", recipeId);
		model.addAttribute("comment", new Comment());
		return "user/formNewComment.html";
	}
	
	@PostMapping("/user/formNewComment/{recipeId}")
	public String newComment (@PathVariable("recipeId") Long recipeId, @Valid @ModelAttribute("comment") Comment comm, BindingResult bindRes, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();
		comm.setAuthor(user);
		this.commValidator.validate(comm, bindRes);
		if(!bindRes.hasErrors()) {
			Recipe rec = this.recService.getRecipe(recipeId);
			rec.getComments().add(comm);
			this.commentService.saveComment(comm);
			this.recService.saveRecipe(rec);
			model.addAttribute("recipe", rec);
			model.addAttribute("currentUser", user);
			return "all/recipe.html";
		}
		return "user/formNewComment.html";
	}
	
	@GetMapping("/admin/deleteComment/{idComm}/{idRecipe}")
	public String deleteComment(@PathVariable("idComm")Long idComm, @PathVariable("idRecipe")Long idRecipe, Model model) {
		this.commentService.deleteComment(idComm);
		model.addAttribute("recipe", this.recService.getRecipe(idRecipe));
		return "all/recipe.html";
	}
	
	@GetMapping("/user/formUpdateComment/{recipeId}/{commentId}")
	public String formUpdateComment(@PathVariable("recipeId")Long recipeId, @PathVariable("commentId")Long commId, Model model) {
		model.addAttribute("recipeId", recipeId);
		model.addAttribute("comment", this.commentService.getComment(commId));
		return "user/formUpdateComment.html";
	}
	
	@PostMapping("/user/formUpdateComment/{recipeId}/{commentId}")
	public String updateComment(@PathVariable("recipeId")Long recipeId, @PathVariable("commentId")Long commId, 
			@ModelAttribute("comment")Comment comm, Model model) {
		Comment old = this.commentService.getComment(commId);
		old.setTitle(comm.getTitle());
		old.setText(comm.getText());
		old.setVote(comm.getVote());
		this.commentService.saveComment(old);
		model.addAttribute("recipe", this.recService.getRecipe(recipeId));
		model.addAttribute("currentUser", old.getAuthor());
		return "all/recipe.html";
	}
}
