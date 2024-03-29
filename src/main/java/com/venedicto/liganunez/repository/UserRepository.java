package com.venedicto.liganunez.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import com.venedicto.liganunez.model.PasswordUpdateRequest;
import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.repository.mappers.PasswordUpdateRequestRowMapper;
import com.venedicto.liganunez.repository.mappers.UserDataRowMapper;
import com.venedicto.liganunez.repository.mappers.UserRowMapper;

@Repository
public class UserRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private static final String CHECK_USER_QUERY = "SELECT COUNT(*) AS user_exists FROM users WHERE user_email = ?";
	private static final String CREATE_DOWNLOAD = "INSERT INTO downloads(download_picture_id, download_user_id) VALUES(?, ?)";
	private static final String CREATE_PASSWORD_UPDATE_REQUEST = "INSERT INTO password_update_requests(request_code, request_user) VALUES(?, ?)";
	private static final String CREATE_USER = "INSERT INTO users(user_id, user_email, user_password, user_name, user_age, user_address) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String DELETE_PASSWORD_UPDATE_REQUEST = "DELETE FROM password_update_requests WHERE request_code = ?";
	private static final String DELETE_USER = "DELETE FROM users WHERE user_email = ?";
	private static final String SELECT_PASSWORD_UPDATE_REQUEST = "SELECT request_code, request_user, request_creation_date FROM password_update_requests WHERE request_code = ?";
	private static final String SELECT_USER = "SELECT user_id, user_email, user_password, user_name, user_age, user_address, user_permissions, user_creation_date, user_last_update FROM users WHERE user_email = ?";
	private static final String SELECT_USERS = "SELECT user_id, user_email, user_name, user_age, user_address FROM users";
	private static final String SELECT_USERS_COUNT = "SELECT COUNT(*) FROM users;";
	private static final String UPDATE_USER_PASSWORD = "UPDATE users SET user_password = ? WHERE user_email = ?";
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public int checkUserExistence(String email) {
		return jdbcTemplate.queryForObject(CHECK_USER_QUERY, Integer.class, email);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void createUser(String id, User user) {
		jdbcTemplate.update(CREATE_USER, id, user.getEmail(), user.getAccessKey(), user.getName(), user.getAge(), user.getAddress());
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public UserData getUser(String email) {
		return jdbcTemplate.queryForObject(SELECT_USER, new UserRowMapper(), email);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public List<com.venedicto.liganunez.model.http.UserData> getUsers() {
		return jdbcTemplate.query(SELECT_USERS, new UserDataRowMapper());
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void updateUserPassword(String userEmail, String newPassword) {
		jdbcTemplate.update(UPDATE_USER_PASSWORD, newPassword, userEmail);
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
	public PasswordUpdateRequest getPasswordUpdateRequest(String requestCode) {
		return jdbcTemplate.queryForObject(SELECT_PASSWORD_UPDATE_REQUEST, new PasswordUpdateRequestRowMapper(), requestCode);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void deletePasswordUpdateRequest(String code) {
		jdbcTemplate.update(DELETE_PASSWORD_UPDATE_REQUEST, code);
	}
	
	/** Stats **/
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void createDownload(String pictureId, String userId) {
		jdbcTemplate.update(CREATE_DOWNLOAD, pictureId, userId);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public Integer getUsersStats() {
		return jdbcTemplate.queryForObject(SELECT_USERS_COUNT, Integer.class);
	}
}
