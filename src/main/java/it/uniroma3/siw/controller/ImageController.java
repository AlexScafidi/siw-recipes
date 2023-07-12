package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.FileStorageService;

@Controller
public class ImageController {

	@Autowired
	FileStorageService storageService;
	
	@GetMapping("/images/{filename:.+}")
	  public ResponseEntity<Resource> getImage(@PathVariable String filename) {
	    Resource file = storageService.load(filename);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	  }
}
