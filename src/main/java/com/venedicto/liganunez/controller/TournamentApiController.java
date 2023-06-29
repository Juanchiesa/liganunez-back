package com.venedicto.liganunez.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.venedicto.liganunez.api.TournamentApi;
import com.venedicto.liganunez.handler.TournamentApiHandler;
import com.venedicto.liganunez.model.http.GetTournamentHttpResponse;
import com.venedicto.liganunez.model.http.GetTournamentsHttpResponse;

@RestController
public class TournamentApiController implements TournamentApi {
    private static final Logger log = LoggerFactory.getLogger(TournamentApiController.class);
    @Autowired
    private TournamentApiHandler handler;
    
    public ResponseEntity<GetTournamentHttpResponse> getTournamentData(String id, String code) {
        GetTournamentHttpResponse response = new GetTournamentHttpResponse();
        log.info("[Get Tournament] Consulta del torneo {}", id );
    	return handler.getTournament(response, id);
    }
    
	public ResponseEntity<GetTournamentsHttpResponse> getTournamentsData() {
		GetTournamentsHttpResponse response = new GetTournamentsHttpResponse();
		log.info("[Get Tournaments] Consulta de torneos");
		return handler.getTournaments(response);
	}
}
