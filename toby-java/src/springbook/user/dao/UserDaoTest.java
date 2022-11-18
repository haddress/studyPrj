package springbook.user.dao;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import springbook.user.domain.User;


@RunWith(SpringJUnit4ClassRunner.class) // JUnit용 확장기능 설정
@ContextConfiguration(locations="applicationContext.xml") // 테스트 컨텍스트가 관리하는 Context 설정파일 경로
public class UserDaoTest {
	
	@Autowired // ApplicationContext에 알맞는 Bean을 알아서 매칭
    private ApplicationContext context;
	UserDao dao;
	
	@Before
	public void setUp() {            
        //ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml"); // 매번 새로 생성하지 않게 주석
		this.dao = context.getBean("userDao", UserDao.class);
	}
	
	@Test
	public void addAndGet() throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicaionContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user1 = new User("user1", "kim", "1111");
		User user2 = new User("user2", "park", "2222");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
		
	}
	
	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		ApplicationContext context = new AnnotationConfigApplicationContext(UserDaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
	}
	
	 public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
//		// AnnotationConfigApplicationContext -> @Configuration이 붙은 코드를 설정정보로 쓸 떄 사용
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//	    UserDao dao = context.getBean("userDao",UserDao.class);
//
//        User user = new User();
//        user.setId("hjna22");
//        user.setName("나현주");
//        user.setPassword("hjna22");
//
//        dao.add(user);
//
//        System.out.println(user.getId() + " 등록 성공");
//
//        User user2 = dao.get(user.getId());
//        System.out.println(user2.getName());
//        System.out.println(user2.getPassword());
//        System.out.println(user2.getId() + " 조회 성공");
        
        JUnitCore.main("spring.user.dao.UserDaoTest");
    }
	 
	 
}
