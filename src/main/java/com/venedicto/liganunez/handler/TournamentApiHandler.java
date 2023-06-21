package com.venedicto.liganunez.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.service.TournamentService;

@Component
public class TournamentApiHandler {
	@Autowired
	private TournamentService tournamentService;
}
