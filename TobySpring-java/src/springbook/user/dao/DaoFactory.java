package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {
	
	@Bean 
	public DataSource dataSource(){
	    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

//	    dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
	    dataSource.setUrl("jdbc:mysql://localhost/springbook");
	    dataSource.setUsername("admin");
	    dataSource.setPassword("admin");

	    return (DataSource) dataSource;
	}
	
	@Bean // 오브젝트 생성을 담당하는 IoC용 메소드임을 의미
	public UserDao userDao() {
//	   return new UserDao(connectionMaker()); // 커넥션 생성 코드 분리
		
	    UserDao userDao = new UserDao();
	    userDao.setDataSource(dataSource()); // DataSource 주입
	    return userDao;
	}
	
	
	@Bean
	public ConnectionMaker connectionMaker(){
		return new DConnectionMaker();
	}
}
