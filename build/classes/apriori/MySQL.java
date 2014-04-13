package apriori;

import java.sql.*;

public class MySQL {
	public Connection cn;
	
	public String open() {
		String ret = "";
		try {
			if (cn == null || cn.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				cn = DriverManager.getConnection("jdbc:mysql://dario.cs.uwec.edu/cs355group15", "WEIRC", "W633212$");
			}
		} catch (Exception e) {
			ret = e.getMessage();
		}
		return ret;
	}
	public void close() {
		if (cn == null) {
			return;
		}
		try { 
			cn.close(); 
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cn = null;
		}
	}
}
