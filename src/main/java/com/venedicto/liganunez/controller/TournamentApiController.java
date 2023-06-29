package com.venedicto.liganunez.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.venedicto.liganunez.api.TournamentApi;
import com.venedicto.liganunez.handler.TournamentApiHandler;
import com.venedicto.liganunez.model.http.GetTournamentHttpResponse;
import com.venedicto.liganunez.model.http.GetTournamentsHttpResponse;
import com.venedicto.liganunez.validator.TournamentValidator;

@RestController
public class TournamentApiController implements TournamentApi {
    private static final Logger log = LoggerFactory.getLogger(TournamentApiController.class);
    @Autowired
    private TournamentApiHandler handler;
    @Autowired
    private TournamentValidator validator;
    
    public ResponseEntity<GetTournamentHttpResponse> getTournamentData(String id, String code) {
        return new ResponseEntity<GetTournamentHttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
    
	public ResponseEntity<GetTournamentsHttpResponse> getTournamentsData() {
		return new ResponseEntity<GetTournamentsHttpResponse>(HttpStatus.NOT_IMPLEMENTED);
	}
}
