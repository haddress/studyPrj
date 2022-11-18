package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Wrapper;

import javax.sql.CommonDataSource;

public interface DataSource extends CommonDataSource, Wrapper {
	// 커넥션 가져오는 메소드
	Connection getConnection() throws SQLException;
}
