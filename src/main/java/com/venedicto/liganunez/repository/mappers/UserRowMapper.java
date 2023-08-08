package com.venedicto.liganunez.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.User;

public class UserRowMapper implements RowMapper<UserData> {
	@Override
	public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setEmail(rs.getString("user_email"));
		user.setAccessKey(rs.getString("user_password"));
		user.setName(rs.getString("user_name"));
		user.setAge(rs.getInt("user_age"));
		user.setAddress(rs.getString("user_address"));
		user.setPermissionLevel(rs.getInt("user_permissions"));

		UserData userData = new UserData();
		userData.setId(rs.getString("user_id"));
		userData.setData(user);
		userData.setCreationDate(rs.getString("user_creation_date"));
		userData.setLastUpdateDate(rs.getString("user_last_update"));
		
		return userData;
	}
}