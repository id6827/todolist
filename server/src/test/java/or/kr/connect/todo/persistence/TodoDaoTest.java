package or.kr.connect.todo.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.todo.TodoApplication;
import kr.or.connect.todo.domain.TodoVo;
import kr.or.connect.todo.persistence.TodoDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TodoApplication.class)
@Transactional
public class TodoDaoTest {
	
	@Autowired
	private TodoDao dao;
	
	@Test
	public void testInsertAndSelect() {
		TodoVo todo =new TodoVo(1,"test1",0,new Timestamp(System.currentTimeMillis()));
		TodoVo tmp = dao.create(todo);
		TodoVo rst = dao.findById(tmp.getId());
		assertThat(rst.getTodo(), is("test1"));
	}

}
