package it.uniroma3.siw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.uniroma3.siw.service.FileStorageService;
import jakarta.annotation.Resource;

@SpringBootApplication
public class SiwRicetteApplication implements CommandLineRunner {
	
	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(SiwRicetteApplication.class, args);
	}
	
	@Override
	public void run(String... arg) throws Exception {
		this.storageService.init();
	}

}
