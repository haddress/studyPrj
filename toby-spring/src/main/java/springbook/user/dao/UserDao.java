package springbook.user.dao;

import java.sql.*;

import springbook.user.domain.User;

public class UserDao {
	
//	private static UserDao INSTANCE;
    private ConnectionMaker connectionMaker;
//	private Connection c;
//	private User user;



    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?"
        );

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setName(rs.getString("name"));
        user.setId(rs.getString("id"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
    
//	public static synchronized UserDao getInstance() {
//		if (INSTANCE == null) INSTANCE = new UserDao(???);
//		return INSTANCE;
//	}

}
