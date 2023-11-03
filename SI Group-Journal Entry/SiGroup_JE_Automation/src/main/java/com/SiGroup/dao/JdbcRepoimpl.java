package com.SiGroup.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.SiGroup.entites.ImageData;

@Repository
public class JdbcRepoimpl implements JdbcRepo {

	@Autowired
	JdbcTemplate jdbcTemplate ;

	

	@Override
	 public boolean isRecordExists(String someParameter) {
        String sql = "SELECT COUNT(*) FROM demo WHERE name = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, someParameter);
        return count > 0;
    }
	
	
	
	
}
