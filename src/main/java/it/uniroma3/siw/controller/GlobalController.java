package it.uniroma3.siw.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

	/**
	 * per avere in ogni pagina, se ci sta, le info dello user
	 */
	@ModelAttribute("userDetails") 
	
	public UserDetails getUser() {
		UserDetails user = null; 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if(!(auth instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		}
				
		return user; 
	}
	
}
