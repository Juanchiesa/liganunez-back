package com.venedicto.liganunez.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private static final String CHECK_USER_QUERY = "SELECT COUNT(*) AS user_exists FROM users WHERE user_email = ?";
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class,
			listeners = "dbRetryListeners",
			maxAttemptsExpression = "${db.retry.attempts}", 
			backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1)
	)
	public int checkUserExistence(String email) {
		return jdbcTemplate.queryForObject(CHECK_USER_QUERY, Integer.class, email);
	}
}
