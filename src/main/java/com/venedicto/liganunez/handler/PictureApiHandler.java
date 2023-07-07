package com.venedicto.liganunez.handler;

import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.DownloadStatsHttpResponse;
import com.venedicto.liganunez.model.http.GetPicturesHttpResponse;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.model.http.PictureInfo;
import com.venedicto.liganunez.model.http.UploadPicturesHttpResponse;
import com.venedicto.liganunez.service.AuthService;
import com.venedicto.liganunez.service.PictureService;
import com.venedicto.liganunez.utils.HttpUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class PictureApiHandler {
	private static final Logger log = LoggerFactory.getLogger(PictureApiHandler.class);
	@Autowired
	private AuthService authService;
	@Autowired
	private PictureService pictureService;
	@Autowired
    private CommonsMultipartResolver multipartResolver;
	
	public ResponseEntity<UploadPicturesHttpResponse> uploadPicture(MultipartHttpServletRequest request, UploadPicturesHttpResponse response, String token, String tournamentId, String place, String date, List<MultipartFile> pictures) {
		HttpStatus httpStatus;
		
		try {
			UserData user = authService.readSessionToken(token);
			log.trace("[Upload pictures] El usuario {} recibió acceso para cargar las imágenes", user.getId());
			
			List<PictureInfo> picturesInfo = pictureService.uploadPicture(tournamentId, place, date, pictures);
			response.setData(picturesInfo);
			multipartResolver.cleanupMultipart(request);
			
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
		} catch(MalformedJwtException | IllegalArgumentException e) {
			httpStatus = HttpStatus.FORBIDDEN;
			response.setOpCode("403");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0015));
			log.error("[Upload pictures] Token inválido", e);
		} catch(ExpiredJwtException e) {
			httpStatus = HttpStatus.UNAUTHORIZED;
			response.setOpCode("401");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0016));
			log.error("[Upload pictures] Token expirado", e);
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Upload pictures] No se pudo establecer conexión con la base de datos", e);
		} catch(EmptyResultDataAccessException e) {
			httpStatus = HttpStatus.NOT_FOUND;
			response.setOpCode("404");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0022));
			log.error("[Upload pictures] El torneo ingresado no existe", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Upload pictures] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<UploadPicturesHttpResponse>(response, httpStatus);
	}
	
	public ResponseEntity<HttpResponse> deletePicture(HttpResponse response, String token, String tournamentId, String id) {
		HttpStatus httpStatus;
		
		try {
			UserData user = authService.readSessionToken(token);
			log.trace("[Delete picture] El usuario {} recibió acceso para eliminar las imágenes", user.getId());
			
			pictureService.deletePicture(id, tournamentId);
			
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
		} catch(MalformedJwtException | IllegalArgumentException e) {
			httpStatus = HttpStatus.FORBIDDEN;
			response.setOpCode("403");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0015));
			log.error("[Delete picture] Token inválido", e);
		} catch(ExpiredJwtException e) {
			httpStatus = HttpStatus.UNAUTHORIZED;
			response.setOpCode("401");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0016));
			log.error("[Delete picture] Token expirado", e);
		} catch(FileNotFoundException e) {
			httpStatus = HttpStatus.NOT_FOUND;
			response.setOpCode("404");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0017));
			log.error("[Delete picture] Imagen no encontrada", e);
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Delete picture] No se pudo establecer conexión con la base de datos", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Delete picture] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<HttpResponse>(response, httpStatus);
	}
	
	public ResponseEntity<GetPicturesHttpResponse> getPictures(GetPicturesHttpResponse response, String tournamentId, int pageNumber, String place, String date) {
		HttpStatus httpStatus;
		
		try {
			List<Picture> pictures = pictureService.getPictures(tournamentId, pageNumber, place, date);
			response.setData(pictures);
			log.trace("[Get pictures] Se listaron {} imágenes con éxito", pictures.size());
			
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Get pictures] No se pudo establecer conexión con la base de datos", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Get pictures] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<GetPicturesHttpResponse>(response, httpStatus);
	}
	
	public ResponseEntity<DownloadStatsHttpResponse> getPicturesStats(DownloadStatsHttpResponse response) {
		HttpStatus httpStatus;
		
		try {
			int downloads = pictureService.getPicturesStats();
			response.setData(downloads);
			log.trace("[Pictures stats] Se registraron {} descargas totales", downloads);
			
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Pictures stats] No se pudo establecer conexión con la base de datos", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Pictures stats] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<DownloadStatsHttpResponse>(response, httpStatus);
	}
	
	public ResponseEntity<DownloadStatsHttpResponse> getPictureStats(DownloadStatsHttpResponse response, String id) {
		HttpStatus httpStatus;
		
		try {
			int downloads = pictureService.getPictureStats(id);
			response.setData(downloads);
			log.trace("[Picture stats] Se registraron {} descargas para esta imagen", downloads);
			
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Picture stats] No se pudo establecer conexión con la base de datos", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Picture stats] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<DownloadStatsHttpResponse>(response, httpStatus);
	}
}
