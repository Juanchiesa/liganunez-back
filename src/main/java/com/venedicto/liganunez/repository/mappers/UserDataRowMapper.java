package com.venedicto.liganunez.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.venedicto.liganunez.model.http.UserData;

public class UserDataRowMapper implements RowMapper<UserData> {
	@Override
	public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserData userData = new UserData();
		
		userData.setId(rs.getString("user_id"));
		userData.setName(rs.getString("user_name"));
		userData.setAge(rs.getInt("user_age"));
		userData.setEmail(rs.getString("user_email"));
		userData.setAddress(rs.getString("user_address"));
		
		return userData;
	}
}