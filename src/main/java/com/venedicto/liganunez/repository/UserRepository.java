package com.venedicto.liganunez.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import com.venedicto.liganunez.model.http.User;

@Repository
public class UserRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private static final String CHECK_USER_QUERY = "SELECT COUNT(*) AS user_exists FROM users WHERE user_email = ?";
	private static final String CREATE_USER = "INSERT INTO users(user_id, user_email, user_password, user_name, user_age, user_address) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String DELETE_USER = "DELETE FROM users WHERE user_email = ?";
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class,
			listeners = "dbRetryListeners",
			maxAttemptsExpression = "${db.retry.attempts}", 
			backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1)
	)
	public int checkUserExistence(String email) {
		return jdbcTemplate.queryForObject(CHECK_USER_QUERY, Integer.class, email);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class,
			listeners = "dbRetryListeners",
			maxAttemptsExpression = "${db.retry.attempts}", 
			backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1)
	)
	public void createUser(String id, String password, User user) {
		jdbcTemplate.update(CREATE_USER, id, user.getEmail(), password, user.getName(), user.getAge(), user.getAddress());
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class,
			listeners = "dbRetryListeners",
			maxAttemptsExpression = "${db.retry.attempts}", 
			backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1)
	)
	public void deleteUser(String email) {
		jdbcTemplate.update(DELETE_USER, email);
	}
}
