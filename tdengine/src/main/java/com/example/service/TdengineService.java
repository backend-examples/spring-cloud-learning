package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Date;
import java.util.Properties;

@Service
@Slf4j
public class TdengineService {

    /**
     * rest连接tdengine
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection( ) throws ClassNotFoundException, SQLException {
        Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
        String jdbcUrl = "jdbc:TAOS-RS://192.168.174.128:6041/demo?user=root&password=taosdata&batchfetch=true";

        return DriverManager.getConnection(jdbcUrl);
    }

    public void createSupTable() throws SQLException, ClassNotFoundException {
        String superTableName = "meters";
        String createSql = "CREATE TABLE " + superTableName + " (ts timestamp, current float, voltage int) TAGS (location binary(64), groupdId int);";
        getConnection().createStatement().execute(createSql);
    }

    public void createSubTables() throws SQLException, ClassNotFoundException {
        String superTableName = "meters"; // 超级表的名称
        String location = "location"; // 示例标签
        int deviceStartId = 1; // 设备ID起始
        int deviceEndId = 100; // 设备ID结束

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

//        CREATE TABLE d1001 USING meters TAGS ("Beijing.Chaoyang", 2);
//        INSERT INTO d1001 USING meters TAGS ("Beijng.Chaoyang", 2) VALUES (now, 10.2, 219);
        for (int deviceId = deviceStartId; deviceId <= deviceEndId; deviceId++) {
            String subTableName = "sub" + "_" + deviceId + "_" + new Timestamp(new Date().getTime()).getTime();
            log.info("子表名称：{}", subTableName);
//            String createSql = "CREATE TABLE " + subTableName + " USING " + superTableName + " TAGS (" + "'Beijing.Chaoyang'" + ", " + deviceId + ");";

            String createSql = "INSERT INTO " + subTableName + " USING " + superTableName + " TAGS (" + "'Beijing.Chaoyang'" + ", " + deviceId + ") VALUES(now, 10.2, 219);";
            statement.execute(createSql); // 执行SQL语句
        }
    }
}

