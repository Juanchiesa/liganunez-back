package com.venedicto.liganunez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.venedicto.liganunez.model.http.Tournament;
import com.venedicto.liganunez.model.http.Tournament.TypeEnum;
import com.venedicto.liganunez.repository.TournamentRepository;
import com.venedicto.liganunez.service.external.FirebaseService;

@RunWith(MockitoJUnitRunner.class)
public class TournamentServiceTest {
	@InjectMocks
	private TournamentService service;
	@Mock
	private TournamentRepository tournamentRepository;
	@Mock
	private FirebaseService firebaseService;
	
	@Test
	public void getTournaments_ok() throws FileNotFoundException {
		Mockito.when(tournamentRepository.getTournaments()).thenReturn(generateTournamentsList(5));
		List<Tournament> response = service.getTournaments();
		assertEquals(5, response.size());
		Mockito.verify(firebaseService, Mockito.times(5)).getImage(Mockito.anyString(), Mockito.eq("logo.png"));
	}
	
	@Test
	public void getTournaments_fileNotFound() throws FileNotFoundException {
		Mockito.when(tournamentRepository.getTournaments()).thenReturn(generateTournamentsList(5));
		Mockito.when(firebaseService.getImage(Mockito.anyString(), Mockito.eq("logo.png"))).thenThrow(FileNotFoundException.class);
		List<Tournament> response = service.getTournaments();
		assertEquals(5, response.size());
		Mockito.verify(firebaseService, Mockito.times(5)).getImage(Mockito.anyString(), Mockito.eq("logo.png"));
	}
	
	@Test
	public void getTournament_ok() throws FileNotFoundException {
		Tournament tournament = new Tournament();
		tournament.setId("1");
		Mockito.when(tournamentRepository.getTournament("1")).thenReturn(tournament);
		
		Tournament response = service.getTournament("1");
		
		assertEquals("1", response.getId());
		Mockito.verify(firebaseService).getImage("1", "logo.png");
	}
	
	/** Otros **/
	private List<Tournament> generateTournamentsList(int nTournaments) {
		List<Tournament> tournaments = new ArrayList<>();
		
		for(int i=0; i<nTournaments; i++) {
			Tournament tournament = new Tournament();
			tournament.setId(String.valueOf(i));
			tournament.setLogo("logo.png");
			tournament.setName("tournament " + i);
			tournament.setType(TypeEnum.F);
			tournaments.add(tournament);
		}
		
		return tournaments;
	}
}