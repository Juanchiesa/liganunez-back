package com.venedicto.liganunez.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

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

import com.venedicto.liganunez.model.http.GetTournamentHttpResponse;
import com.venedicto.liganunez.model.http.GetTournamentsHttpResponse;
import com.venedicto.liganunez.service.TournamentService;

@RunWith(MockitoJUnitRunner.class)
public class TournamentApiHandlerTest {
	@InjectMocks
	private TournamentApiHandler handler;
	@Mock
	private TournamentService tournamentService;
	
	@Test
	public void getTournaments_ok() {
		ResponseEntity<GetTournamentsHttpResponse> response = handler.getTournaments(new GetTournamentsHttpResponse());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void getTournaments_dbTimeout() {
		Mockito.when(tournamentService.getTournaments()).thenThrow(CannotGetJdbcConnectionException.class);
		ResponseEntity<GetTournamentsHttpResponse> response = handler.getTournaments(new GetTournamentsHttpResponse());
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	
	@Test
	public void getTournaments_internalError() {
		Mockito.when(tournamentService.getTournaments()).thenThrow(RuntimeException.class);
		ResponseEntity<GetTournamentsHttpResponse> response = handler.getTournaments(new GetTournamentsHttpResponse());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void getTournament_ok() {
		ResponseEntity<GetTournamentHttpResponse> response = handler.getTournament(new GetTournamentHttpResponse(), "1");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void getTournament_dbTimeout() throws FileNotFoundException {
		Mockito.when(tournamentService.getTournament("1")).thenThrow(CannotGetJdbcConnectionException.class);
		ResponseEntity<GetTournamentHttpResponse> response = handler.getTournament(new GetTournamentHttpResponse(), "1");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	
	@Test
	public void getTournament_wrongTournament() throws FileNotFoundException {
		Mockito.when(tournamentService.getTournament("1")).thenThrow(EmptyResultDataAccessException.class);
		ResponseEntity<GetTournamentHttpResponse> response = handler.getTournament(new GetTournamentHttpResponse(), "1");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void getTournament_internalError() throws FileNotFoundException {
		Mockito.when(tournamentService.getTournament("1")).thenThrow(RuntimeException.class);
		ResponseEntity<GetTournamentHttpResponse> response = handler.getTournament(new GetTournamentHttpResponse(), "1");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}