package kr.or.connect.todo.persistence;

public class TodoSqls {
	
	// SQL 명령어들
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	
	/*
	static final String INSERT =
			"INSERT INTO todo(id,todo,completed,date)"
			+ "values(:id,:todo,0,"
			+ new Timestamp(System.currentTimeMillis())
			+ ")";
	*/
	static final String UPDATE_BY_ID =
			"UPDATE todo SET todo=:todo,"
			+ "completed=:completed,"
			+ "date=:date "
			+ "WHERE id=:id";

	static final String SELECT_BY_DATE =
			"SELECT id, todo, completed, date FROM todo ORDER BY date DESC ";
	
	static final String SELECT_BY_ID =
			"SELECT id, todo, completed, date FROM todo WHERE id=:id";
			
}
