package com.venedicto.liganunez.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.venedicto.liganunez.api.PictureApi;
import com.venedicto.liganunez.handler.PictureApiHandler;
import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.model.http.GetPicturesHttpResponse;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.UploadPicturesHttpResponse;
import com.venedicto.liganunez.utils.HttpUtils;
import com.venedicto.liganunez.validator.PictureValidator;

@RestController
public class PictureApiController implements PictureApi {
    private static final Logger log = LoggerFactory.getLogger(PictureApiController.class);
    @Autowired
    private PictureApiHandler handler;
    @Autowired
    private PictureValidator validator;
    
    public ResponseEntity<HttpResponse> deletePicture(String tournamentId, String id, String token) {
    	HttpResponse response = new HttpResponse();
    	
    	log.info("[Delete picture] Se solicitó eliminar la imagen {} correspondiente al torneo {}", id, tournamentId);
        return handler.deletePicture(response, token, tournamentId, id);
    }

	public ResponseEntity<GetPicturesHttpResponse> getPictures(String tournamentId, Integer pageNumber, String place, String date) {
		GetPicturesHttpResponse response = new GetPicturesHttpResponse();
		
		if(date != null) {
			List<Error> errors = validator.validateDateFormat(date);
			if(!errors.isEmpty()) {
				return HttpUtils.badRequestResponse(log, response, errors);
			}
		}
		
		log.info("[Get pictures] Se solicitaron las imagenes del torneo {}", tournamentId);
        return handler.getPictures(response, tournamentId, pageNumber, place, date);
    }

	public ResponseEntity<UploadPicturesHttpResponse> uploadPictures(MultipartHttpServletRequest request, String token, String place, String date, String tournamentId, List<MultipartFile> files) {
		UploadPicturesHttpResponse response = new UploadPicturesHttpResponse();
		
		List<Error> errors = validator.validateFileUpload(tournamentId, place, date);
		if(!errors.isEmpty()) {
			return HttpUtils.badRequestResponse(log, response, errors);
		}
		
		log.info("[Upload pictures] Se procederá a subir una cantidad de {} fotos", files.size());
		return handler.uploadPicture(request, response, token, tournamentId, place, date, files);
	}
}
