package com.venedicto.liganunez.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.model.http.Tournament;
import com.venedicto.liganunez.repository.TournamentRepository;
import com.venedicto.liganunez.service.external.FirebaseService;

@Service
public class TournamentService {
	private static final Logger log = LoggerFactory.getLogger(TournamentService.class);
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private FirebaseService firebaseService;
	
	public List<Tournament> getTournaments() {
		List<Tournament> tournaments = tournamentRepository.getTournaments();
		
		tournaments.forEach(tournament -> {
			try {
				tournament.setLogo(firebaseService.getImage(tournament.getId(), "logo.png"));
			} catch (FileNotFoundException e) {
				log.error("Error inesperado con el torneo {}", tournament.getId(), e);
			}
		});
		
		return tournaments;
	}
	
	public Tournament getTournament(String tournamentId) throws FileNotFoundException {
		Tournament tournament = tournamentRepository.getTournament(tournamentId);
		tournament.setLogo(firebaseService.getImage(tournamentId, "logo.png"));
		return tournament;
	}
}
