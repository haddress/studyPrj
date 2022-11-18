package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
	
	// 컨텍스트 메서드의 내용을 옮김
	// UserDao는 쿼리를 작성, 실행하는 데에만 사용
	// JdbcContext가 자원 반환 + 커넥션 가져옴 + 예외처리
	
	private DataSource dataSource;
	
	// DataSource 타입의 bean 주입
	public void setDataSource(DataSource dataSource) {  
        this.dataSource = dataSource;
    }
	
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
        PreparedStatement ps = null;
        
        // 예외처리
        try {
        	c = this.dataSource.getConnection();
        	
        	ps = stmt.makePreparedStatement(c);
        	
        	ps.executeUpdate();
        } catch (SQLException e) {
        	throw e;
        } finally {
        	
        }
	}

}
