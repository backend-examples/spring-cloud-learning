package com.example;


import com.example.service.TdengineService;
import com.taosdata.jdbc.SchemalessWriter;
import com.taosdata.jdbc.enums.SchemalessProtocolType;
import com.taosdata.jdbc.enums.SchemalessTimestampType;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;
import java.util.Properties;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TDEngineTestApplication {

    @Autowired
    private TdengineService tdengineService;
    private static final String lineDemo = "meters1,groupid=3,location=californiasanfrancisco current=10.3000002f64,voltage=219i32,phase=0.31f64 1626006833639000000";
//    private static final String telnetDemo = "stb0_0 1707095283260 4 host=host0 interface=eth0";
//    private static final String jsonDemo = "{\"metric\": \"meter_current\",\"timestamp\": 1626846400,\"value\": 10.3, \"tags\": {\"groupid\": 2, \"location\": \"California.SanFrancisco\", \"id\": \"d1001\"}}";

//    private static void init(Connection connection) throws SQLException {
//        try (Statement stmt = connection.createStatement()) {
//            stmt.execute("CREATE DATABASE IF NOT EXISTS power");
//            stmt.execute("USE demo");
//        }
//    }

    @Test
    public void testSchemalessWrite() throws SQLException {
        final String url = "jdbc:TAOS-RS://192.168.174.130:6041/?user=root&password=taosdata&batchfetch=true";

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "taosdata");
        properties.setProperty("charset", "UTF-8");

        try(Connection connection = DriverManager.getConnection(url, properties)){
            try(SchemalessWriter writer = new SchemalessWriter(connection, "demo")){
                writer.write(lineDemo, SchemalessProtocolType.LINE, SchemalessTimestampType.NANO_SECONDS);
            }
        }
    }

    @Test
    public void testCreateTable() throws SQLException, ClassNotFoundException {
        tdengineService.getConnection();
//        tdengineService.createSupTable();
//        tdengineService.createSubTables();
    }

    @SneakyThrows
    @Test
    public void testLink() {
        // load JDBC-restful driver
        Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
        // use port 6041 in url when use JDBC-restful
        String url = "jdbc:TAOS-RS://192.168.174.128:6041/?user=root&password=taosdata";
        Properties properties = new Properties();
        properties.setProperty("charset", "UTF-8");
        properties.setProperty("locale", "en_US.UTF-8");
        properties.setProperty("timezone", "UTC-8");
        Connection conn = DriverManager.getConnection(url, properties);
        Statement stmt = conn.createStatement();
        stmt.execute("create database if not exists restful_test");
        stmt.execute("use restful_test");
        stmt.execute("create table restful_test.weather(ts timestamp, temperature float) tags(location nchar(64))");
        stmt.executeUpdate("insert into t1 using restful_test.weather tags('北京') values(now, 18.2)");
        ResultSet rs = stmt.executeQuery("select * from restful_test.weather");
        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.print(meta.getColumnLabel(i) + ": " + rs.getString(i) + "\t");
            }
            System.out.println();
        }
        rs.close();

        stmt.close();
        conn.close();
    }
}
