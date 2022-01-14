/**
 * MySQLDataSource
 * @author paul
 */

package com.pab.challenge1.datasource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.pab.challenge1.util.Constants;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class MySQLDataSource {

    private static volatile DataSource dataSource = null;

    private MySQLDataSource() {
    }

    public static DataSource getDataSource() throws NamingException {
        if (dataSource == null) {
            synchronized (MySQLDataSource.class) {
                if (dataSource == null) {
                    Context ctx = new InitialContext();
                    dataSource = (DataSource) ctx.lookup(Constants.JNDI_DATASOURCE_REGISTRY);
                }
            }
        }
        return dataSource;
    }
//    	public static DataSource getMySQLDataSource() {
//		Properties props = new Properties();
//		FileInputStream fis = null;
//		MysqlDataSource mysqlDS = null;
//		try {
//			fis = new FileInputStream("db.properties");
//			props.load(fis);
//			mysqlDS = new MysqlDataSource();
//			mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
//			mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
//			mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return mysqlDS;
//	}
//    public static DataSource getDataSource() throws NamingException {
//        if (dataSource == null) {
//            synchronized (MySQLDataSource.class) {
//                if (dataSource == null) {
//                    Context ctx = new InitialContext();
//                    dataSource = (DataSource) ctx.lookup(Constants.JNDI_DATASOURCE_REGISTRY);
//                }
//            }
//        }
//        return dataSource;
//    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
        if (Objects.nonNull(resultSet)) {
            resultSet.close();
        }
        if (Objects.nonNull(statement)) {
            statement.close();
        }
        if (Objects.nonNull(connection)) {
            connection.close();
        }
    }
}