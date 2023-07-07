package com.venedicto.liganunez.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.model.http.PictureInfo;
import com.venedicto.liganunez.repository.PictureRepository;
import com.venedicto.liganunez.service.external.FirebaseService;

@Service
public class PictureService {
	private static final Logger log = LoggerFactory.getLogger(PictureService.class);
	@Autowired
	private FirebaseService filesService;
	@Autowired
	private PictureRepository pictureRepository;
	
	public List<PictureInfo> uploadPicture(String tournamentId, String place, String date, List<MultipartFile> pictures) {
		List<PictureInfo> picturesInfo = new ArrayList<>();
		
		pictures.stream().parallel().forEach(picture -> {
			//Generate picture id
			String pictureId = UUID.randomUUID().toString();
			
			//Insert into database
			try {
				pictureRepository.createPicture(pictureId, place, date, tournamentId);
			} catch(EmptyResultDataAccessException e) {
				throw e;
			} catch(Exception e) {
				picturesInfo.add(setPictureInfo(pictureId, "500"));
				return;
			}
				
			//Upload to the server, if fails rollback in the database
			try {
				filesService.uploadImage(pictureId, tournamentId, picture);
				picturesInfo.add(setPictureInfo(pictureId, "201"));
			} catch(IllegalArgumentException e) {
				pictureRepository.deletePicture(pictureId);
				picturesInfo.add(setPictureInfo(pictureId, "400"));
			} catch(Exception e) {
				pictureRepository.deletePicture(pictureId);
				picturesInfo.add(setPictureInfo(pictureId, "500"));
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
	
	public List<Picture> getPictures(String tournamentId, int pageNumber, String place, String date) {
		List<Picture> pictures = pictureRepository.getPictures(tournamentId, pageNumber, place, date);
		pictures.stream().parallel().forEach(picture -> {
			try {
				picture.setFile(filesService.getImage(tournamentId, picture.getId()));
			} catch (FileNotFoundException e) {
				log.error("Error inesperado con la imagen {}", picture.getId(), e);
			}
		});
		
		return pictures;
	}
}