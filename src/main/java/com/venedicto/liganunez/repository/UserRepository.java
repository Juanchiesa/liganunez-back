package com.venedicto.liganunez.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.repository.mappers.UserRowMapper;

@Repository
public class UserRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private static final String CHECK_USER_QUERY = "SELECT COUNT(*) AS user_exists FROM users WHERE user_email = ?";
	private static final String CREATE_PASSWORD_UPDATE_REQUEST = "INSERT INTO password_update_requests(request_code, request_user) VALUES(?, ?)";
	private static final String CREATE_USER = "INSERT INTO users(user_id, user_email, user_password, user_name, user_age, user_address) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String DELETE_PASSWORD_UPDATE_REQUEST = "DELETE FROM password_update_requests WHERE request_code = ?";
	private static final String DELETE_USER = "DELETE FROM users WHERE user_email = ?";
	private static final String SELECT_USER = "SELECT user_id, user_email, user_password, user_name, user_age, user_address, user_permissions, user_creation_date, user_last_update FROM users WHERE user_email = ?";
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public int checkUserExistence(String email) {
		return jdbcTemplate.queryForObject(CHECK_USER_QUERY, Integer.class, email);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void createUser(String id, String password, User user) {
		jdbcTemplate.update(CREATE_USER, id, user.getEmail(), password, user.getName(), user.getAge(), user.getAddress());
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public UserData getUser(String email) {
		return jdbcTemplate.queryForObject(SELECT_USER, new UserRowMapper(), email);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void deleteUser(String email) {
		jdbcTemplate.update(DELETE_USER, email);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void createPasswordUpdateRequest(String userEmail, String code) {
		jdbcTemplate.update(CREATE_PASSWORD_UPDATE_REQUEST, code, userEmail);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void deletePasswordUpdateRequest(String code) {
		jdbcTemplate.update(DELETE_PASSWORD_UPDATE_REQUEST, code);
	}
}
