package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {
	
	@Autowired
	CredentialsService credentialsService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Credentials creds = (Credentials) o;
		String username = creds.getUsername();
		if(username != null && credentialsService.existsByUsername(username) ) {
			errors.reject("credentials.username.duplicate");
		}
	}
}
