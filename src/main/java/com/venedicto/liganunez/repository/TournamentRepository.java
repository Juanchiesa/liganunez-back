package com.venedicto.liganunez.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import com.venedicto.liganunez.model.http.Tournament;
import com.venedicto.liganunez.repository.mappers.TournamentRowMapper;

@Repository
public class TournamentRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public final static String SELECT_TOURNAMENT = "SELECT tournament_id, tournament_name, tournament_type FROM tournaments WHERE tournament_id = ?";
	public final static String SELECT_TOURNAMENTS = "SELECT tournament_id, tournament_name, tournament_type FROM tournaments";
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public List<Tournament> getTournaments() {
		return jdbcTemplate.query(SELECT_TOURNAMENTS, new TournamentRowMapper());
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public Tournament getTournament(String tournamentId) {
		return jdbcTemplate.queryForObject(SELECT_TOURNAMENT, new TournamentRowMapper(), tournamentId);
	}
}
