package com.example.demo.controller;

import java.sql.*;

/**
 * Create by tianchun on 2019/9/26
 * 功能描述：
 */
public class ConnectionManager {

    private static ThreadLocal<Connection> connectionHolder = ThreadLocal.withInitial(() -> {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&serverTimezone=UTC", "root", "123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    });

    public static Connection getConnection() {
        return connectionHolder.get();
    }

    public static void setConnection(Connection conn) {
        connectionHolder.set(conn);
    }

    private final static String insertSql = "INSERT INTO IDGenerator VALUES()";

    private final static String selectSql = "SELECT LAST_INSERT_ID()";

    public static void main(String[] args) throws SQLException {

        Connection connection = ConnectionManager.getConnection();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Statement stmt = null;
                ResultSet rs = null;

                try {
                    stmt = connection.createStatement();
                    stmt.executeUpdate(insertSql);
                    rs = stmt.executeQuery(selectSql);
                    rs.next();
                    long id = rs.getInt(1);

                    System.out.println(Thread.currentThread().getName() + " --- Id = " + id);

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, String.valueOf(i)).start();
        }


    }
}
