package com.venedicto.liganunez.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.venedicto.liganunez.model.http.Picture;

public class PictureRowMapper implements RowMapper<Picture> {
	@Override
	public Picture mapRow(ResultSet rs, int rowNum) throws SQLException {
		Picture picture = new Picture();
		
		picture.setId(rs.getString("picture_id"));
		picture.setDate(rs.getString("picture_date"));
		picture.setPlace(rs.getString("picture_place"));
		picture.setTournamentId(rs.getString("picture_tournament"));
		
		return picture;
	}
}