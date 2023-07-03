package com.venedicto.liganunez.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.model.http.PictureInfo;
import com.venedicto.liganunez.repository.PictureRepository;
import com.venedicto.liganunez.service.external.FirebaseFiles;
import com.venedicto.liganunez.validator.PictureValidator;

@Service
public class PictureService {
	@Autowired
	private FirebaseFiles filesService;
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
}