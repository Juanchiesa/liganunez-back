package com.venedicto.liganunez.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.venedicto.liganunez.model.http.Tournament;
import com.venedicto.liganunez.repository.mappers.TournamentRowMapper;

@RunWith(MockitoJUnitRunner.class)
public class TournamentRepositoryTest {
	@InjectMocks
	private TournamentRepository repository;
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void getTournaments_ok() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(TournamentRowMapper.class))).thenReturn(generateTournamentsList(3));
		
		List<Tournament> response = repository.getTournaments();
		
		assertEquals(3, response.size());
		Mockito.verify(jdbcTemplate, Mockito.times(1)).query(Mockito.anyString(), Mockito.any(TournamentRowMapper.class));
	}
	
	@Test
	public void getTournament_ok() {
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(TournamentRowMapper.class), Mockito.eq("0"))).thenReturn(generateTournamentsList(3).get(0));
		
		Tournament response = repository.getTournament("0");
		
		
		assertEquals("0", response.getId());
		Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForObject(Mockito.anyString(), Mockito.any(TournamentRowMapper.class), Mockito.eq("0"));
	}
	
	/** Adicional **/
	private List<Tournament> generateTournamentsList(int nTournaments) {
		List<Tournament> tournaments = new ArrayList<>();
		
		for(int i=0; i<nTournaments; i++) {
			Tournament tournament = new Tournament();
			tournament.setId(String.valueOf(i));
			tournaments.add(tournament);
		}
		
		return tournaments;
	}
}
