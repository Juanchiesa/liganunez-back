package com.venedicto.liganunez.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.venedicto.liganunez.api.PictureApi;
import com.venedicto.liganunez.handler.PictureApiHandler;
import com.venedicto.liganunez.model.http.GetPicturesHttpResponse;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.model.http.UploadPicturesHttpResponse;
import com.venedicto.liganunez.validator.PictureValidator;

@RestController
public class PictureApiController implements PictureApi {
    private static final Logger log = LoggerFactory.getLogger(PictureApiController.class);
    @Autowired
    private PictureApiHandler handler;
    @Autowired
    private PictureValidator validator;
    
    public ResponseEntity<HttpResponse> deletePicture(String id, String token) {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

	public ResponseEntity<List<GetPicturesHttpResponse>> getPictures(String tournamentId) {
        return new ResponseEntity<List<GetPicturesHttpResponse>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UploadPicturesHttpResponse> uploadPictures(String token, List<Picture> body) {
    	UploadPicturesHttpResponse response = new UploadPicturesHttpResponse();
        
        log.info("[Upload pictures] Se procederá a subir una cantidad de {} fotos", body.size());
    	return handler.uploadPicture(response, token, body);
    }
}
