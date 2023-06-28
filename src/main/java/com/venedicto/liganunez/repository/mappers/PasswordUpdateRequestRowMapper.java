package com.venedicto.liganunez.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import com.venedicto.liganunez.model.PasswordUpdateRequest;

public class PasswordUpdateRequestRowMapper implements RowMapper<PasswordUpdateRequest> {
	@Override
	public PasswordUpdateRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
		PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest();
		
		passwordUpdateRequest.setId(rs.getString("request_code"));
		passwordUpdateRequest.setUserEmail(rs.getString("request_user"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		passwordUpdateRequest.setCreationDate(LocalDateTime.parse(rs.getString("request_creation_date"), formatter));
		
		return passwordUpdateRequest;
	}
}