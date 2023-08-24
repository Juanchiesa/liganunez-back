package com.venedicto.liganunez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.model.http.PictureInfo;
import com.venedicto.liganunez.repository.PictureRepository;
import com.venedicto.liganunez.service.external.FirebaseService;

@RunWith(MockitoJUnitRunner.class)
public class PictureServiceTest {
	@InjectMocks
	private PictureService service;
	@Mock
	private FirebaseService filesService;
	@Mock
	private PictureRepository pictureRepository;
	
	private List<MultipartFile> filesList;
	@Mock
	private MultipartFile file;
	
	@Before
	public void setUp() {
		filesList = new ArrayList<>();
		filesList.add(file);
	}
	
	@Test
	public void uploadPicture_ok() throws IOException {
		List<PictureInfo> response = service.uploadPicture("xxx", "Palermo", "2023-07-26", filesList);
		
		Mockito.verify(pictureRepository, Mockito.times(1)).createPicture(Mockito.anyString(), Mockito.eq("Palermo"), Mockito.eq("2023-07-26"), Mockito.eq("xxx"));
		Mockito.verify(filesService, Mockito.times(1)).uploadImage(Mockito.anyString(), Mockito.eq("xxx"), Mockito.eq(file));
		assertEquals("201", response.get(0).getStatus());
	}
	@Test(expected = EmptyResultDataAccessException.class)
	public void uploadPicture_repeatedImageId() throws IOException {
		Mockito.doThrow(EmptyResultDataAccessException.class).when(pictureRepository).createPicture(Mockito.anyString(), Mockito.eq("Palermo"), Mockito.eq("2023-07-26"), Mockito.eq("xxx"));
		service.uploadPicture("xxx", "Palermo", "2023-07-26", filesList);
	}
	@Test
	public void uploadPicture_dbError() throws IOException {
		Mockito.doThrow(RuntimeException.class).when(pictureRepository).createPicture(Mockito.anyString(), Mockito.eq("Palermo"), Mockito.eq("2023-07-26"), Mockito.eq("xxx"));
		List<PictureInfo> response = service.uploadPicture("xxx", "Palermo", "2023-07-26", filesList);
		
		Mockito.verify(pictureRepository, Mockito.times(1)).createPicture(Mockito.anyString(), Mockito.eq("Palermo"), Mockito.eq("2023-07-26"), Mockito.eq("xxx"));
		Mockito.verify(filesService, Mockito.times(0)).uploadImage(Mockito.anyString(), Mockito.eq("xxx"), Mockito.eq(file));
		assertEquals("500", response.get(0).getStatus());
	}
	@Test
	public void uploadPicture_badRequest() throws IOException {
		Mockito.doThrow(IllegalArgumentException.class).when(filesService).uploadImage(Mockito.anyString(), Mockito.eq("xxx"), Mockito.eq(file));
		List<PictureInfo> response = service.uploadPicture("xxx", "Palermo", "2023-07-26", filesList);
		
		Mockito.verify(pictureRepository, Mockito.times(1)).createPicture(Mockito.anyString(), Mockito.eq("Palermo"), Mockito.eq("2023-07-26"), Mockito.eq("xxx"));
		Mockito.verify(filesService, Mockito.times(1)).uploadImage(Mockito.anyString(), Mockito.eq("xxx"), Mockito.eq(file));
		Mockito.verify(pictureRepository, Mockito.times(1)).deletePicture(Mockito.anyString());
		assertEquals("400", response.get(0).getStatus());
	}
	@Test
	public void uploadPicture_internalError() throws IOException {
		Mockito.doThrow(RuntimeException.class).when(filesService).uploadImage(Mockito.anyString(), Mockito.eq("xxx"), Mockito.eq(file));
		List<PictureInfo> response = service.uploadPicture("xxx", "Palermo", "2023-07-26", filesList);
		
		Mockito.verify(pictureRepository, Mockito.times(1)).createPicture(Mockito.anyString(), Mockito.eq("Palermo"), Mockito.eq("2023-07-26"), Mockito.eq("xxx"));
		Mockito.verify(filesService, Mockito.times(1)).uploadImage(Mockito.anyString(), Mockito.eq("xxx"), Mockito.eq(file));
		Mockito.verify(pictureRepository, Mockito.times(1)).deletePicture(Mockito.anyString());
		assertEquals("500", response.get(0).getStatus());
	}
	
	@Test
	public void deletePicture_ok() throws FileNotFoundException {
		service.deletePicture("xxx", "aaa");
		Mockito.verify(filesService, Mockito.times(1)).deleteImage("aaa", "xxx");
		Mockito.verify(pictureRepository, Mockito.times(1)).deletePicture("xxx");
	}
	
	@Test
	public void getPictures_ok() throws FileNotFoundException {
		Mockito.when(pictureRepository.getPictures("xxx", 1, "Palermo", "2023-07-26")).thenReturn(generatePicturesList(5));
		List<Picture> response = service.getPictures("xxx", 1, "Palermo", "2023-07-26");
		
		assertEquals(5, response.size());
		Mockito.verify(filesService, Mockito.times(5)).getImage(Mockito.eq("xxx"), Mockito.anyString());
	}
	@Test
	public void getPictures_firebaseError() throws FileNotFoundException {
		Mockito.when(pictureRepository.getPictures("xxx", 1, "Palermo", "2023-07-26")).thenReturn(generatePicturesList(5));
		Mockito.when(filesService.getImage(Mockito.eq("xxx"), Mockito.anyString())).thenThrow(FileNotFoundException.class);
		List<Picture> response = service.getPictures("xxx", 1, "Palermo", "2023-07-26");
		
		assertEquals(5, response.size());
		Mockito.verify(filesService, Mockito.times(5)).getImage(Mockito.eq("xxx"), Mockito.anyString());
	}
	
	@Test
	public void getPicturesStats_ok() {
		Mockito.when(pictureRepository.getPicturesStats()).thenReturn(25);
		int response = service.getPicturesStats();
		assertEquals(25, response);
	}
	
	@Test
	public void getPictureStats_ok() {
		Mockito.when(pictureRepository.getPictureStats("aaa")).thenReturn(10);
		int response = service.getPictureStats("aaa");
		assertEquals(10, response);
	}
	
	@Test
	public void deleteAllPictures_ok() throws FileNotFoundException {
		Mockito.when(pictureRepository.getPictures()).thenReturn(generatePicturesList(10));
		service.deleteAllPictures();
		
		Mockito.verify(filesService, Mockito.times(10)).deleteImage(Mockito.anyString(), Mockito.anyString());
		Mockito.verify(pictureRepository, Mockito.times(10)).deletePicture(Mockito.anyString());
	}
	@Test
	public void deleteAllPictures_firebaseError() throws FileNotFoundException {
		Mockito.doThrow(FileNotFoundException.class).when(filesService).deleteImage(Mockito.anyString(), Mockito.anyString());
		Mockito.when(pictureRepository.getPictures()).thenReturn(generatePicturesList(10));
		service.deleteAllPictures();
		
		Mockito.verify(filesService, Mockito.times(10)).deleteImage(Mockito.anyString(), Mockito.anyString());
		Mockito.verify(pictureRepository, Mockito.times(0)).deletePicture(Mockito.anyString());
	}
	
	/** Complemento **/
	private List<Picture> generatePicturesList(int nPictures) {
		List<Picture> pictures = new ArrayList<>();
		
		for(int i=0; i<nPictures; i++) {
			Picture picture = new Picture();
			picture.setId(String.valueOf(i));
			picture.setTournamentId("xxx");
			picture.setPlace("Palermo");
			picture.setDate("2023-07-26");
			pictures.add(picture);
		}
		
		return pictures;
	}
}