package com.venedicto.liganunez.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.venedicto.liganunez.api.TournamentApi;
import com.venedicto.liganunez.handler.TournamentApiHandler;
import com.venedicto.liganunez.model.GetTournamentHttpResponse;
import com.venedicto.liganunez.model.HttpResponse;
import com.venedicto.liganunez.model.Tournament;
import com.venedicto.liganunez.validator.TournamentValidator;

@RestController
public class TournamentApiController implements TournamentApi {
    private static final Logger log = LoggerFactory.getLogger(TournamentApiController.class);
    @Autowired
    private TournamentApiHandler handler;
    @Autowired
    private TournamentValidator validator;
    
    public ResponseEntity<HttpResponse> createTournament(String token, Tournament body) {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<HttpResponse> deleteTournament(String tournamentId, String token) {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<GetTournamentHttpResponse> getTournamentData(String id, String code) {
        return new ResponseEntity<GetTournamentHttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<HttpResponse> updateTournament(String id, String token, Tournament body) {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
}
