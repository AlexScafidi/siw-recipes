package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.repository.ImageRepository;
import jakarta.transaction.Transactional;

@Service
public class ImageService {
	
	@Autowired
	ImageRepository imageRepo;
	
	@Transactional
	public Image save(Image image) {
		return this.imageRepo.save(image);
	}

	@Transactional
	public void delete(Long id) {
		this.imageRepo.deleteById(id);
	}
	
	public boolean existsByFilename(String filename) {
		return this.imageRepo.existsByFileName(filename);
	}

}
