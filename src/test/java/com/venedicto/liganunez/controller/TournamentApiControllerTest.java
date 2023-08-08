package com.venedicto.liganunez.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.venedicto.liganunez.handler.TournamentApiHandler;
import com.venedicto.liganunez.model.http.GetTournamentHttpResponse;
import com.venedicto.liganunez.model.http.GetTournamentsHttpResponse;

@RunWith(MockitoJUnitRunner.class)
public class TournamentApiControllerTest {
	@InjectMocks
	private TournamentApiController controller;
	@Mock
	private TournamentApiHandler handler;
	
	@Test
	public void getTournamentData_ok() {
		controller.getTournamentData("aaaa");
		Mockito.verify(handler, Mockito.times(1)).getTournament(Mockito.any(GetTournamentHttpResponse.class), Mockito.eq("aaaa"));
	}
	
	@Test
	public void getTournamentsData_ok() {
		controller.getTournamentsData();
		Mockito.verify(handler, Mockito.times(1)).getTournaments(Mockito.any(GetTournamentsHttpResponse.class));
	}
}