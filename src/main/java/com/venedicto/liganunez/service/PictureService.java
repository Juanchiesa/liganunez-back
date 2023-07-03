package com.venedicto.liganunez.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.model.http.PictureInfo;
import com.venedicto.liganunez.repository.PictureRepository;
import com.venedicto.liganunez.service.external.FirebaseService;
import com.venedicto.liganunez.validator.PictureValidator;

@Service
public class PictureService {
	private static final Logger log = LoggerFactory.getLogger(PictureService.class);
	@Autowired
	private FirebaseService filesService;
	@Autowired
	private PictureRepository pictureRepository;
	@Autowired
	private PictureValidator pictureValidator;
	
	public List<PictureInfo> uploadPicture(List<Picture> pictures) {
		List<PictureInfo> picturesInfo = new ArrayList<>();
		
		pictures.forEach(picture -> {
			if(pictureValidator.isValidFile(picture)) {
				//Insert into database
				try {
					pictureRepository.createPicture(picture.getId(), picture);
				} catch(Exception e) {
					picturesInfo.add(setPictureInfo(picture.getId(), "500"));
					return;
				}
				
				//Upload to the server, if fails rollback in the database
				try {
					filesService.uploadImage(picture.getId(), picture.getTournamentId(), picture.getFile());
					picturesInfo.add(setPictureInfo(picture.getId(), "201"));
				} catch(IllegalArgumentException e) {
					pictureRepository.deletePicture(picture.getId());
					picturesInfo.add(setPictureInfo(picture.getId(), "400"));
				} catch(Exception e) {
					pictureRepository.deletePicture(picture.getId());
					picturesInfo.add(setPictureInfo(picture.getId(), "500"));
				}
			} else {
				picturesInfo.add(setPictureInfo(picture.getId(), "400"));
			}
		});
		
		return picturesInfo;
	}

	private PictureInfo setPictureInfo(String id, String status) {
		PictureInfo pictureInfo = new PictureInfo();
		pictureInfo.setId(id);
		pictureInfo.setStatus(status);
		return pictureInfo;
	}
	
	public void deletePicture(String id, String tournamentId) throws FileNotFoundException {
		filesService.deleteImage(tournamentId, id);
		pictureRepository.deletePicture(id);
	}
	
	public List<Picture> getPictures(String tournamentId) {
		List<Picture> pictures = pictureRepository.getPictures(tournamentId);
		pictures.forEach(picture -> {
			try {
				picture.setFile(filesService.getImage(tournamentId, picture.getId()));
			} catch (FileNotFoundException e) {
				log.error("Error inesperado con la imagen {}", picture.getId(), e);
			}
		});
		
		return pictures;
	}
}