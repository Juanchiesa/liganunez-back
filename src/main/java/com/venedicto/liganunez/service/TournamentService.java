package com.venedicto.liganunez.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.repository.TournamentRepository;

@Service
public class TournamentService {
	@Autowired
	private TournamentRepository tournamentRepository;
}
