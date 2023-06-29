package com.venedicto.liganunez.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.GetTournamentHttpResponse;
import com.venedicto.liganunez.model.http.GetTournamentsHttpResponse;
import com.venedicto.liganunez.model.http.Tournament;
import com.venedicto.liganunez.service.TournamentService;
import com.venedicto.liganunez.utils.HttpUtils;

@Component
public class TournamentApiHandler {
	private static final Logger log = LoggerFactory.getLogger(TournamentApiHandler.class);
	@Autowired
	private TournamentService tournamentService;
	
	public ResponseEntity<GetTournamentsHttpResponse> getTournaments(GetTournamentsHttpResponse response) {
		HttpStatus httpStatus;
		
		try {
			List<Tournament> tournaments = tournamentService.getTournaments();
			response.setData(tournaments);
			
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
			log.info("[Get tournaments] Se han listado {} torneos con éxito", tournaments.size());
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Get tournaments] No se pudo establecer conexión con la base de datos", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Get tournaments] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<GetTournamentsHttpResponse>(response, httpStatus);
	}
	
	public ResponseEntity<GetTournamentHttpResponse> getTournament(GetTournamentHttpResponse response, String tournamentId) {
		HttpStatus httpStatus;
		
		try {
			Tournament tournament = tournamentService.getTournament(tournamentId);
			response.setData(tournament);
			
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
			log.info("[Get tournament] Se ha listado el torneo con éxito");
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Get tournament] No se pudo establecer conexión con la base de datos", e);
		} catch(EmptyResultDataAccessException e) {
			httpStatus = HttpStatus.NOT_FOUND;
			response.setOpCode("404");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0014));
			log.error("[Get tournament] El torneo ingresado es inexistente", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Get tournament] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<GetTournamentHttpResponse>(response, httpStatus);
	}
}
