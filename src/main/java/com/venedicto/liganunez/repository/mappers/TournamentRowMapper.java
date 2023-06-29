package com.venedicto.liganunez.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.venedicto.liganunez.model.http.Tournament;

public class TournamentRowMapper implements RowMapper<Tournament> {
	@Override
	public Tournament mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tournament tournament = new Tournament();
		
		tournament.setId(rs.getString("tournament_id"));
		tournament.setCode(rs.getInt("tournament_code"));
		tournament.setName(rs.getString("tournament_name"));
		tournament.setType(Tournament.TypeEnum.fromValue(rs.getString("tournament_type")));
		
		return tournament;
	}
}