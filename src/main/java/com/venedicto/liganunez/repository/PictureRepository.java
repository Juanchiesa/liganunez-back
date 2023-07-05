package com.venedicto.liganunez.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import com.venedicto.liganunez.model.http.Picture;
import com.venedicto.liganunez.repository.mappers.PictureRowMapper;

@Repository
public class PictureRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Value("${db.get.limit}")
	private int pageSize;
	
	private static final String CREATE_PICTURE = "INSERT INTO pictures(picture_id, picture_date, picture_place, picture_tournament) VALUES(?, ?, ?, ?)";
	private static final String DELETE_PICTURE = "DELETE FROM pictures WHERE picture_id = ?";
	private static final String SELECT_PICTURES = "SELECT picture_id, picture_date, picture_place, picture_tournament FROM pictures WHERE picture_tournament = ? LIMIT ? OFFSET ?";
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void createPicture(String id, Picture picture) {
		jdbcTemplate.update(CREATE_PICTURE, id, picture.getDate(), picture.getPlace(), picture.getTournamentId());
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public List<Picture> getPictures(String tournamentId, int pageNumber) {
		int offset = (pageNumber * pageSize) - pageSize;
		return jdbcTemplate.query(SELECT_PICTURES, new PictureRowMapper(), tournamentId, pageSize, offset);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void deletePicture(String id) {
		jdbcTemplate.update(DELETE_PICTURE, id);
	}
}
