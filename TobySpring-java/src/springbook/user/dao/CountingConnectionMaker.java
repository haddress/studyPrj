package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
	
    int counter = 0;
    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker; // 실제로 사용될 구체클래스(ConnectionMaker)
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
    	// 실제 오브젝트 반환하기 전에 counter 증가시켜서 구분
        this.counter++; 
        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return this.counter;
    }
}
