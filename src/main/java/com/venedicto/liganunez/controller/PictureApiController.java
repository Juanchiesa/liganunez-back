package com.venedicto.liganunez.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.venedicto.liganunez.api.PictureApi;
import com.venedicto.liganunez.handler.PictureApiHandler;
import com.venedicto.liganunez.model.http.GetPicturesHttpResponse;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.model.http.UploadPicturesHttpResponse;

@RestController
public class PictureApiController implements PictureApi {
    private static final Logger log = LoggerFactory.getLogger(PictureApiController.class);
    @Autowired
    private PictureApiHandler handler;
    
    public ResponseEntity<HttpResponse> deletePicture(String tournamentId, String id, String token) {
    	HttpResponse response = new HttpResponse();
    	
    	log.info("[Delete picture] Se solicitó eliminar la imagen {} correspondiente al torneo {}", id, tournamentId);
        return handler.deletePicture(response, token, tournamentId, id);
    }

	public ResponseEntity<GetPicturesHttpResponse> getPictures(String tournamentId) {
		GetPicturesHttpResponse response = new GetPicturesHttpResponse();
		
		log.info("[Get pictures] Se solicitaron las imagenes del torneo {}", tournamentId);
        return handler.getPictures(response, tournamentId);
    }

    public ResponseEntity<UploadPicturesHttpResponse> uploadPictures(String token, List<Picture> body) {
    	UploadPicturesHttpResponse response = new UploadPicturesHttpResponse();
        
        log.info("[Upload pictures] Se procederá a subir una cantidad de {} fotos", body.size());
    	return handler.uploadPicture(response, token, body);
    }
}
