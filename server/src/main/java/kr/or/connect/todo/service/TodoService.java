package kr.or.connect.todo.service;

import java.util.List;
import kr.or.connect.todo.domain.TodoVo;
//비즈니스 컴포넌트
public interface TodoService {
	
	public List<TodoVo> findAll();
	public void update(TodoVo vo);
	public void delete(Integer id);
	public TodoVo create(TodoVo vo);
	public TodoVo findById(Integer id);
	
}
