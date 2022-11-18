package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
	// 인터페이스를 이용한 리팩토링
	
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		return null;
	}
}
