package com.venedicto.liganunez.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TournamentRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
}