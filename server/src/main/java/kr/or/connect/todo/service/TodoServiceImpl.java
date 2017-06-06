package kr.or.connect.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.todo.domain.TodoVo;
import kr.or.connect.todo.persistence.TodoDao;
// 비즈니스 컴포넌트
@Service("todoService")
public class TodoServiceImpl implements TodoService{

	//@Autowired 생성자를 통한 의존성 주입으로 인해 제거
	private TodoDao todoDAO;
	
	@Autowired
	public TodoServiceImpl(TodoDao todoDAO) {
		this.todoDAO = todoDAO;
	}
	
	public List<TodoVo> findAll() {
		return todoDAO.findAll();
	}
	public void update(TodoVo vo) {
		todoDAO.update(vo);
	}
	public void delete(Integer id) {
		todoDAO.delete(id);
	}
	public TodoVo create(TodoVo vo) {
		return todoDAO.create(vo);
	}
	public TodoVo findById(Integer id){
		return todoDAO.findById(id);
	}
}
