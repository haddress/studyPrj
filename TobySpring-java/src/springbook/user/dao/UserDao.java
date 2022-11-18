package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao {
	
	// 인터페이스 사용
	private ConnectionMaker connectionMaker;
	
	private DataSource dataSource;
	
	// JdbcContext를 DI ------------------
	private JdbcContext jdbcContext;
	
	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	//------------------------------------
	
	// 의존관계 주입을 적용한 생성자 방식 DI
//	public UserDao(ConnectionMaker connectionMaker){ 
//		this.connectionMaker = connectionMaker;   
//	}
	
	// 의존관계 검색을 이용하는 생성자
//	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//  this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
	
	// 수정자 메소드 방식
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
	
	public void add(final User user) throws SQLException {
//		Connection c = getConnection();
//		Connection c = dataSource.getConnection();
//
//        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
//        ps.setString(1, user.getId());
//        ps.setString(2, user.getName());
//        ps.setString(3, user.getPassword());
//
//        ps.executeUpdate();
//
//        ps.close();
//        c.close();
//      
		// inner class ---------------------------
//        class AddStatement implements StatementStrategy {  
//            User user;
//            
//            // add 메서드에 사용할 User 정보는 생성자로 받음 BUT AddStatement 클래스가 선언된 곳의 user에도 직접 접근이 가능함 (외부 변수는 final 한정자를 붙여주어야 함)
//            public AddStatement(User user) {
//                this.user = user; 
//            }
//
//            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//                PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
//                ps.setString(1, user.getId());
//                          
//                return ps;
//            }
//        }
//        StatementStrategy st = new AddStatement(user);
//        jdbcContextWithStatementStrategy(st);
        
        // 익명클래스로 선언할 경우 클래스 이름도 사용하지 않을 수 있음
        // -----------------------------------------
		
		// DI 받은 JdbcContext의 컨텍스트 메소드를 사용하도록 변경
		this.jdbcContext.workWithStatementStrategy (
			new StatementStrategy() {

				@Override
				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}}
		);
	}
	
    public User get(String id) throws SQLException {
    	Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
    
    public void deleteAll() throws SQLException {
//    	Connection c = dataSource.getConnection();
//    	PreparedStatement ps = c.prepareStatement("delete from users");
//    	
//    	ps.executeUpdate();
//    	ps.close();
//    	c.close();
    	
    	// 예외가 발생하더라도 리소스를 반환할 수 있도록 try/catch 사용
//    	Connection c = null;
//        PreparedStatement ps = null;
//
//        try {
//        	// 예외 발생 가능한 코드
//            c = dataSource.getConnection();
//            ps = c.prepareStatement("delete from users");
//            ps.executeUpdate();
//        } catch(SQLException e) {
//            throw e;
//        } finally { // 예외발생유무와 상관없이 무조건 실행
//            if (ps != null) {
//            	try { 
//            		ps.close(); // 여기서도 예외가 발생할 수 있기 떄문에 try 한 번 더 사용
//            	} catch(SQLException e) {
//            		
//            	}
//            }
//            
//            if (c != null) { // NullPointerException을 방지하기 위해
//            	try {
//            		c.close(); 
//            	} catch(SQLException e) {
//            		
//            	} 
//            }
//        }
        // but 너무 복잡하므로 반복, 중복되는 코드 분리 필요 -> 전략 패턴을 활용하면 OCP를 지키면서 분리 가능
    	
    	
    	
    	// 컨텍스트 코드를 메소드로 분리
//    	StatementStrategy st = new DeleteAllStatement(); // 클라이언트에서 전략 선택
//        jdbcContextWithStatementStrategy(st); // 컨텍스트에 넘겨줌
    	
    	// 클라이언트 코드 분리 -------------
    	executeSql("DELETE FROM users WHERE 1=1");
    	// ----------------------------
    }
    
    // sql 생성 메서드 분리
    private void executeSql(final String query) throws SQLException {
    	// 익명 클래스가 접근할 수 있도록 final로 선언
    	this.jdbcContext.workWithStatementStrategy(
    		new StatementStrategy() {
		        @Override
		        public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
		            PreparedStatement ps = c.prepareStatement(query);
		            return ps;
		        }
    		}
    	);
    }
    
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
    	Connection c = null;
    	PreparedStatement ps = null;
    	
    	try {
    		c = dataSource.getConnection();
    		ps = stmt.makePreparedStatement(c);
    		ps.executeUpdate();
    	} catch (SQLException e) {
    		throw e;
    	} finally {
    	    if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
    	    if (c != null) { try { c.close(); } catch (SQLException e) {} }
    	}
    
    }
    
    
    // deleteAll() 메서드를 전략과 컨텍스트로 분리하였으나 DAO 메서드마다 전략에 해당하는 클래스를 만들어야 하고, 추가적으로 사용할 객체가 있으면 생성자와 인스턴스 변수를 추가해서 클래스 만들기가 번거로움
    // 따라서 UserDao 내부에 inner class로 정의할 수 있음 (전략마다 클래스 파일을 만들지 않아도 됨) -> add 메서드에 AddStatement 클래스 생성
    
    
    public int getCount() throws SQLException{
    	Connection c = dataSource.getConnection();
    	PreparedStatement ps = c.prepareStatement("select count(*) from users");
    	ResultSet rs = ps.executeQuery();
    	rs.next();
    	
    	int count = rs.getInt(1);
    	rs.close();
    	ps.close();
    	c.close();
    	
    	return count;
    }
    
    
    

}
