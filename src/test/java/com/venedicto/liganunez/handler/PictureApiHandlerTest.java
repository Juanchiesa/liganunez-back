package com.venedicto.liganunez.handler;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.DownloadStatsHttpResponse;
import com.venedicto.liganunez.model.http.GetPicturesHttpResponse;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.model.http.UploadPicturesHttpResponse;
import com.venedicto.liganunez.service.AuthService;
import com.venedicto.liganunez.service.PictureService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@RunWith(MockitoJUnitRunner.class)
public class PictureApiHandlerTest {
	@InjectMocks
	private PictureApiHandler handler;
	@Mock
	private AuthService authService;
	@Mock
	private PictureService pictureService;
	@Mock
	private CommonsMultipartResolver multipartResolver;
	
	@Test
	public void uploadPicture_ok() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		ResponseEntity<UploadPicturesHttpResponse> response = handler.uploadPicture(null, new UploadPicturesHttpResponse(), "12345", "xxxxx", "place", "2023-07-12", null);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void uploadPicture_wrongAuthCode() {
		Mockito.when(authService.readSessionToken("12345")).thenThrow(MalformedJwtException.class);
		ResponseEntity<UploadPicturesHttpResponse> response = handler.uploadPicture(null, new UploadPicturesHttpResponse(), "12345", "xxxxx", "place", "2023-07-12", null);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	@Test
	public void uploadPicture_expiredSession() {
		Mockito.when(authService.readSessionToken("12345")).thenThrow(ExpiredJwtException.class);
		ResponseEntity<UploadPicturesHttpResponse> response = handler.uploadPicture(null, new UploadPicturesHttpResponse(), "12345", "xxxxx", "place", "2023-07-12", null);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	@Test
	public void uploadPicture_dbTimeout() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.when(pictureService.uploadPicture("xxxxx", "place", "2023-07-12", null)).thenThrow(CannotGetJdbcConnectionException.class);
		
		ResponseEntity<UploadPicturesHttpResponse> response = handler.uploadPicture(null, new UploadPicturesHttpResponse(), "12345", "xxxxx", "place", "2023-07-12", null);
		
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void uploadPicture_wrongTournament() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.when(pictureService.uploadPicture("xxxxx", "place", "2023-07-12", null)).thenThrow(EmptyResultDataAccessException.class);
		
		ResponseEntity<UploadPicturesHttpResponse> response = handler.uploadPicture(null, new UploadPicturesHttpResponse(), "12345", "xxxxx", "place", "2023-07-12", null);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void uploadPicture_internalError() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.when(pictureService.uploadPicture("xxxxx", "place", "2023-07-12", null)).thenThrow(RuntimeException.class);
		
		ResponseEntity<UploadPicturesHttpResponse> response = handler.uploadPicture(null, new UploadPicturesHttpResponse(), "12345", "xxxxx", "place", "2023-07-12", null);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void deletePicture_ok() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		ResponseEntity<HttpResponse> response = handler.deletePicture(new HttpResponse(), "12345", "xxxxx", "aaaaa");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void deletePicture_wrongAuthCode() {
		Mockito.when(authService.readSessionToken("12345")).thenThrow(MalformedJwtException.class);
		ResponseEntity<HttpResponse> response = handler.deletePicture(new HttpResponse(), "12345", "xxxxx", "aaaaa");
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	@Test
	public void deletePicture_expiredSession() {
		Mockito.when(authService.readSessionToken("12345")).thenThrow(ExpiredJwtException.class);
		ResponseEntity<HttpResponse> response = handler.deletePicture(new HttpResponse(), "12345", "xxxxx", "aaaaa");
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	@Test
	public void deletePicture_dbTimeout() throws FileNotFoundException {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.doThrow(CannotGetJdbcConnectionException.class).when(pictureService).deletePicture("aaaaa", "xxxxx");
		
		ResponseEntity<HttpResponse> response = handler.deletePicture(new HttpResponse(), "12345", "xxxxx", "aaaaa");
		
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void deletePicture_fileNotFound() throws FileNotFoundException {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.doThrow(FileNotFoundException.class).when(pictureService).deletePicture("aaaaa", "xxxxx");
		
		ResponseEntity<HttpResponse> response = handler.deletePicture(new HttpResponse(), "12345", "xxxxx", "aaaaa");
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void deletePicture_internalError() throws FileNotFoundException {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.doThrow(RuntimeException.class).when(pictureService).deletePicture("aaaaa", "xxxxx");
		
		ResponseEntity<HttpResponse> response = handler.deletePicture(new HttpResponse(), "12345", "xxxxx", "aaaaa");
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void getPictures_ok() {
		Mockito.when(pictureService.getPictures("xxxxx", 1, null, null)).thenReturn(getPictures(1));
		ResponseEntity<GetPicturesHttpResponse> response = handler.getPictures(new GetPicturesHttpResponse(), "xxxxx", 1, null, null);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void getPictures_dbTimeout() {
		Mockito.when(pictureService.getPictures("xxxxx", 1, null, null)).thenThrow(CannotGetJdbcConnectionException.class);
		ResponseEntity<GetPicturesHttpResponse> response = handler.getPictures(new GetPicturesHttpResponse(), "xxxxx", 1, null, null);
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void getPictures_internalError() {
		Mockito.when(pictureService.getPictures("xxxxx", 1, null, null)).thenThrow(RuntimeException.class);
		ResponseEntity<GetPicturesHttpResponse> response = handler.getPictures(new GetPicturesHttpResponse(), "xxxxx", 1, null, null);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void getPicturesStats_ok() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.when(pictureService.getPicturesStats()).thenReturn(50);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPicturesStats(new DownloadStatsHttpResponse(), "12345");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void getPicturesStats_dbTimeout() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.when(pictureService.getPicturesStats()).thenThrow(CannotGetJdbcConnectionException.class);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPicturesStats(new DownloadStatsHttpResponse(), "12345");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void getPicturesStats_internalError() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.when(pictureService.getPicturesStats()).thenThrow(RuntimeException.class);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPicturesStats(new DownloadStatsHttpResponse(), "12345");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	@Test
	public void getPicturesStats_wrongAuthCode() {
		Mockito.when(authService.readSessionToken("12345")).thenThrow(MalformedJwtException.class);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPicturesStats(new DownloadStatsHttpResponse(), "12345");
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	@Test
	public void getPicturesStats_expiredSession() {
		Mockito.when(authService.readSessionToken("12345")).thenThrow(ExpiredJwtException.class);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPicturesStats(new DownloadStatsHttpResponse(), "12345");
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	@Test
	public void getPictureStats_ok() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.when(pictureService.getPictureStats("1")).thenReturn(25);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPictureStats(new DownloadStatsHttpResponse(), "1", "12345");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void getPictureStats_dbTimeout() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.when(pictureService.getPictureStats("1")).thenThrow(CannotGetJdbcConnectionException.class);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPictureStats(new DownloadStatsHttpResponse(), "1", "12345");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void getPictureStats_internalError() {
		Mockito.when(authService.readSessionToken("12345")).thenReturn(getUserData());
		Mockito.when(pictureService.getPictureStats("1")).thenThrow(RuntimeException.class);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPictureStats(new DownloadStatsHttpResponse(), "1", "12345");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	@Test
	public void getPictureStats_wrongAuthCode() {
		Mockito.when(authService.readSessionToken("12345")).thenThrow(MalformedJwtException.class);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPictureStats(new DownloadStatsHttpResponse(), "1", "12345");
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	@Test
	public void getPictureStats_expiredSession() {
		Mockito.when(authService.readSessionToken("12345")).thenThrow(ExpiredJwtException.class);
		ResponseEntity<DownloadStatsHttpResponse> response = handler.getPictureStats(new DownloadStatsHttpResponse(), "1", "12345");
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	//Adicional
	private UserData getUserData() {
		UserData userData = new UserData();
		
		userData.setId("aaaaa");
		userData.setCreationDate("2023-07-12");
		userData.setLastUpdateDate("2023-07-2023");
		
		return userData;
	}
	
	private List<Picture> getPictures(int nPictures) {
		List<Picture> pictures = new ArrayList<>();
		
		for(int i=0; i<nPictures; i++) {
			Picture picture = new Picture();
			picture.setId(String.valueOf(i));
			picture.setFile("https://......../.png");
			picture.setPlace("place");
			picture.setDate("2023-01-01");
			pictures.add(picture);
		}
		
		return pictures;
	}
}