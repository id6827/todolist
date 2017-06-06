package kr.or.connect.todo.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.domain.TodoVo;
// DAO (Data Access Object)
@Repository("todoDAO")
public class TodoDao {
	
	// JDBC 관련변수
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	class TodoRowMapper implements RowMapper<TodoVo>{
		public TodoVo mapRow(ResultSet rs, int rowNum) throws SQLException {
			TodoVo todoVO = new TodoVo();
			todoVO.setId(rs.getInt("id"));
			todoVO.setTodo(rs.getString("todo"));
			todoVO.setCompleted(rs.getInt("completed"));
			todoVO.setDate(rs.getTimestamp("date"));
			return todoVO;
		}
	}
			
	@Autowired
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");	// AUTO_INCREMENT에 의해 자동으로 PK 결정
	}
	
	public void update(TodoVo vo) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		jdbc.update(TodoSqls.UPDATE_BY_ID, param);
	}
	
	public void delete(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		jdbc.update(TodoSqls.DELETE_BY_ID, param);
	}

	public TodoVo create(TodoVo vo) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		Number id = insertAction.executeAndReturnKey(param);
		vo.setId(id.intValue());
		return vo;
	}
	
	public TodoVo findById(Integer id) {
		Map<String, Integer> param = Collections.singletonMap("id", id);
		return jdbc.queryForObject(TodoSqls.SELECT_BY_ID, param, new TodoRowMapper());
	}
	
	public List<TodoVo> findAll() {
		List<TodoVo> todos = jdbc.query(TodoSqls.SELECT_BY_DATE, new TodoRowMapper());
		return todos;
	}
	
	
}
