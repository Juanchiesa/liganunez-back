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
	private static final String SELECT_PICTURE_DOWNLOADS = "SELECT COUNT(*) FROM downloads WHERE download_picture_id = ?";
	private static final String SELECT_PICTURES = "SELECT picture_id, picture_date, picture_place, picture_tournament FROM pictures ORDER BY picture_upload_date DESC";
	private static final String SELECT_PICTURES_DOWNLOADS = "SELECT COUNT(*) FROM downloads";
	private static final String SELECT_PICTURES_WITHOUT_FILTERS = "SELECT picture_id, picture_date, picture_place, picture_tournament FROM pictures WHERE picture_tournament = ? ORDER BY picture_upload_date DESC LIMIT ? OFFSET ?";
	private static final String SELECT_PICTURES_WITH_DATE_FILTER = "SELECT picture_id, picture_date, picture_place, picture_tournament FROM pictures WHERE picture_tournament = ? AND picture_date = ? ORDER BY picture_upload_date DESC LIMIT ? OFFSET ?";
	private static final String SELECT_PICTURES_WITH_PLACE_FILTER = "SELECT picture_id, picture_date, picture_place, picture_tournament FROM pictures WHERE picture_tournament = ? AND picture_place = ? ORDER BY picture_upload_date DESC LIMIT ? OFFSET ?";
	private static final String SELECT_PICTURES_WITH_DATE_AND_PLACE_FILTER = "SELECT picture_id, picture_date, picture_place, picture_tournament FROM pictures WHERE picture_tournament = ? AND picture_date = ? AND picture_place = ? ORDER BY picture_upload_date DESC LIMIT ? OFFSET ?";
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void createPicture(String id, String place, String date, String tournamentId) {
		jdbcTemplate.update(CREATE_PICTURE, id, date, place, tournamentId);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public List<Picture> getPictures() {
		return jdbcTemplate.query(SELECT_PICTURES, new PictureRowMapper());
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public List<Picture> getPictures(String tournamentId, int pageNumber, String place, String date) {
		int offset = (pageNumber * pageSize) - pageSize;
		
		if(place != null && date != null) {
			return jdbcTemplate.query(SELECT_PICTURES_WITH_DATE_AND_PLACE_FILTER, new PictureRowMapper(), tournamentId, date, place, pageSize, offset);
		} else if(place != null) {
			return jdbcTemplate.query(SELECT_PICTURES_WITH_PLACE_FILTER, new PictureRowMapper(), tournamentId, place, pageSize, offset);
		} else if(date != null) {
			return jdbcTemplate.query(SELECT_PICTURES_WITH_DATE_FILTER, new PictureRowMapper(), tournamentId, date, pageSize, offset);
		}
		
		return jdbcTemplate.query(SELECT_PICTURES_WITHOUT_FILTERS, new PictureRowMapper(), tournamentId, pageSize, offset);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public void deletePicture(String id) {
		jdbcTemplate.update(DELETE_PICTURE, id);
	}
	
	/** Stats **/
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public int getPicturesStats() {
		return jdbcTemplate.queryForObject(SELECT_PICTURES_DOWNLOADS, Integer.class);
	}
	
	@Retryable(retryFor = CannotGetJdbcConnectionException.class, listeners = "dbRetryListeners", maxAttemptsExpression = "${db.retry.attempts}",  backoff = @Backoff(delayExpression = "${db.retry.delay}", maxDelayExpression = "${db.timeout}", multiplier = 1))
	public int getPictureStats(String id) {
		return jdbcTemplate.queryForObject(SELECT_PICTURE_DOWNLOADS, Integer.class, id);
	}
}
