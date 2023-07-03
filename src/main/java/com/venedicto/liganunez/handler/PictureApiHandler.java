package com.venedicto.liganunez.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.model.http.PictureInfo;
import com.venedicto.liganunez.model.http.UploadPicturesHttpResponse;
import com.venedicto.liganunez.service.AuthService;
import com.venedicto.liganunez.service.PictureService;
import com.venedicto.liganunez.utils.HttpUtils;

import io.jsonwebtoken.MalformedJwtException;

@Component
public class PictureApiHandler {
	private static final Logger log = LoggerFactory.getLogger(PictureApiHandler.class);
	@Autowired
	private AuthService authService;
	@Autowired
	private PictureService pictureService;
	
	public ResponseEntity<UploadPicturesHttpResponse> uploadPicture(UploadPicturesHttpResponse response, String token, List<Picture> pictures) {
		HttpStatus httpStatus;
		
		try {
			UserData user = authService.readSessionToken(token);
			log.trace("[Upload pictures] El usuario {} obtuvo acceso para realizar la carga de las imágenes", user.getId());
			
			List<PictureInfo> picturesInfo = pictureService.uploadPicture(pictures);
			response.setData(picturesInfo);
			
			httpStatus = HttpStatus.CREATED;
			response.setOpCode("201");
		} catch(MalformedJwtException | IllegalArgumentException e) {
			httpStatus = HttpStatus.FORBIDDEN;
			response.setOpCode("403");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0015));
			log.error("[Upload pictures] Token inválido", e);
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Upload pictures] No se pudo establecer conexión con la base de datos", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Upload pictures] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<UploadPicturesHttpResponse>(response, httpStatus);
	}
}
