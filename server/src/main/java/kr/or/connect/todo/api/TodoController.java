package kr.or.connect.todo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.domain.TodoVo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("api/todos")
public class TodoController {
	//@Autowired 생성자를 통한 의존성 주입으로 인해 제거
	private TodoService todoService;
	
	@Autowired
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}
	
	//@RequestMapping(method = RequestMethod.GET)
	@GetMapping
	List<TodoVo> getTodos() {
		return todoService.findAll();
	}
	@PostMapping("/create")
	public TodoVo create(@RequestBody TodoVo todo) {
		System.out.println(todo.toString());
		return todoService.create(todo);
	}
	@PutMapping("/{id}")
	public void update(@PathVariable Integer id, @RequestBody TodoVo todo) {
		//수정하면 Timestamp도 바꿔야하는거아닌가 ?...
		TodoVo original = todoService.findById(id);
		original.setCompleted(todo.getCompleted());
		todoService.update(original);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		todoService.delete(id);
	}
	
	
}
