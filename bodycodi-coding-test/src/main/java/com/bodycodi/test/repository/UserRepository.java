package com.bodycodi.test.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bodycodi.test.dto.UserDto;

@Repository
public class UserRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    String sql = "INSERT INTO users(user_name, password) values(?,?)";
    
    public int insert(UserDto user) {
    	    
    	KeyHolder holder = new GeneratedKeyHolder();
    	jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				
				PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				return ps;
			}
			
		}, holder);
    	    
        if (holder.getKey().intValue() > 0) {
        	int re = holder.getKey().intValue(); // 저장한 사용자의 아이디 가지고 오기    
            return re;
        } else {
            throw new RuntimeException("insert error");
        }
    }


    public UserDto findUser(String username) {
        return this.jdbcTemplate.queryForObject("사용자 정보 찾기 쿼리 작성", new String[]{username}, new RowMapper<UserDto>() {

            @Override
            public UserDto mapRow(ResultSet resultSet, int i) throws SQLException {
                UserDto user = new UserDto();
                //가져온 사용자 정보 매핑

                return user;
            }
        });
    }
}
