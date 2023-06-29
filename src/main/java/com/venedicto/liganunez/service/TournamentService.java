package com.venedicto.liganunez.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.model.http.Tournament;
import com.venedicto.liganunez.repository.TournamentRepository;
import com.venedicto.liganunez.service.external.FirebaseFiles;

@Service
public class TournamentService {
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private FirebaseFiles firebaseService;
	
	public List<Tournament> getTournaments() {
		List<Tournament> tournaments = tournamentRepository.getTournaments();
		
		tournaments.forEach(tournament -> {
			tournament.setLogo(firebaseService.getImage(tournament.getId(), "logo.png"));
		});
		
		return tournaments;
	}
	
	public Tournament getTournament(String tournamentId) {
		Tournament tournament = tournamentRepository.getTournament(tournamentId);
		tournament.setLogo(firebaseService.getImage(tournamentId, "logo.png"));
		return tournament;
	}
}
