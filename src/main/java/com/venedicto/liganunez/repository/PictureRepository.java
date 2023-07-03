package com.venedicto.liganunez.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import com.venedicto.liganunez.model.http.Picture;

@Repository
public class PictureRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private static final String CREATE_PICTURE = "INSERT INTO pictures(picture_id, picture_date, picture_place) VALUES(?, ?, ?)";
	private static final String DELETE_PICTURE = "DELETE FROM pictures WHERE picture_id = ?";
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void createPicture(String id, Picture picture) {
		jdbcTemplate.update(CREATE_PICTURE, id, picture.getDate(), picture.getPlace());
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void deletePicture(String id) {
		jdbcTemplate.update(DELETE_PICTURE, id);
	}
}
