//package it.uniroma3.siw.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import it.uniroma3.siw.model.Credentials;
//import it.uniroma3.siw.model.User;
//import it.uniroma3.siw.service.CredentialsService;
//import it.uniroma3.siw.service.UserService;
//
//@Controller
//public class AuthenticationController {
//
//	@Autowired
//	CredentialsService credentialsService;
////	@Autowired CredentialsValidator credentialsValidator; 
//	@Autowired
//	UserService userService;
//	// @Autowired UserValidator userValidator;
//
//	@GetMapping(value = "/registration")
//	public String showRegistrationForm(Model model) {
//		model.addAttribute("user", new User());
//		model.addAttribute("credentials", new Credentials());
//		return "all/registrationForm.html";
//	}
//	
//
////	@PostMapping(value = "/registration")
////	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult userBd,
////			@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBd, Model model) {
////	 controllo
////		this.credentialsValidator.validate(credentials,credentialsBd);
////		this.userValidator.validate(user, userBd); 
////
////		if ((!credentialsBd.hasErrors()) && (!userBd.hasErrors())) {
////			// registro l'utente
////			userService.save(user);
////			credentials.setUser(user);
////			credentialsService.saveCredentials(credentials);
////			model.addAttribute("user", user);
////			return "all/index.html";
////		}
////		// in caso di errore/i
////		return "all/registrationForm.html";
////
////
////		userService.save(user);
////		credentials.setUser(user);
////		credentialsService.saveCredentials(credentials);
////		model.addAttribute("user", user);
////		return "all/index.html";
////	}
//	
//	@PostMapping(value = "/registration")
//	public String registerUser(@ModelAttribute("user") User user, @ModelAttribute("credentials") Credentials credentials, Model model) {
//			// registro l'utente
//			userService.save(user);
//			credentials.setUser(user);
//			credentialsService.saveCredentials(credentials);
//			model.addAttribute("user", user);
//			return "all/index.html";
//	}
//
//	@GetMapping(value = "/login")
//	public String showLoginForm() {
//		return "all/formLogin.html";
//	}
//
//	@GetMapping(value="/index")
//	public String index() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
//		if(auth instanceof AnonymousAuthenticationToken) return "all/index.html"; 
//		else {
//			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
//			//controllo se admin
//			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
//			if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) return "admin/indexAdmin.html";
//	
//		}
//		return "all/index.html"; 
//	}
//
//
//
//	@GetMapping(value = "/success")
//	public String defaultAfterLogin(Model model) {
//
//		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
//		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
//			return "admin/indexAdmin.html";
//		}
//		return "all/index.html";
//	}
//
//}

package it.uniroma3.siw.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private UserService userService;


	@GetMapping(value = "/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "all/registrationForm.html";
	}

	@PostMapping(value = "/registration")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult userBind, 
			@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credsBind, Model model) {

		// registro l'utente
		if(!userBind.hasErrors() && !credsBind.hasErrors()) {
			user.setRegistrationDate(LocalDateTime.now()); 
			this.userService.save(user);
			credentials.setUser(user);
			this.credentialsService.saveCredentials(credentials);
			model.addAttribute("user", user);
			return "all/registrationSuccess.html";
		}
		return "all/registrationForm.html";
	}

	@GetMapping(value = "/login")
	public String showLoginForm() {
		return "all/formLogin.html";
	}

	@GetMapping(value= {"/","/index"})
	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if(auth instanceof AnonymousAuthenticationToken) return "all/index.html"; 
		else {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			//controllo se admin
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) return "redirect:/admin/index";

		}
		return "all/index.html"; 
	}

	@GetMapping(value="/admin/index")
	public String adminIndex() { 
		return "admin/indexAdmin.html";
	}



	@GetMapping(value = "/success")
	public String defaultAfterLogin(Model model) {

		//		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		//		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
		//			System.out.println("trueeeeee");
		//			return "admin/indexAdmin.html";
		//		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if(auth instanceof AnonymousAuthenticationToken) return "all/index.html"; 
		else {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			//controllo se admin
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			System.out.println("success :" + credentials.getRole()); 
			if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) return "redirect:/admin/index";
			return "redirect:/index";
		}

	}

}

