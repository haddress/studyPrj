package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class UserDaoTest {
    public static void main(String[] args) throws SQLException
    , ClassNotFoundException {

//		ConnectionMaker connectionMaker = new DConnectionMaker();
		
//    	UserDao dao = new DaoFactory().userDao();


    	
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        
		DaoFactory factory = new DaoFactory();
		UserDao dao1 = factory.userDao();
		UserDao dao2 = factory.userDao();
		
		ApplicationContext context1 =
				new AnnotationConfigApplicationContext(DaoFactory.class);
		
		UserDao dao3 = context1.getBean("userDao", UserDao.class);
		UserDao dao4 = context1.getBean("userDao", UserDao.class);

		System.out.println(dao1);
		System.out.println(dao2);
		System.out.println(dao3);
		System.out.println(dao4);



    }
}
