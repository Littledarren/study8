package myutil;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseHelper {
    private static final String conf = "WEB-INF/DBconf.json";

    private static String className;
    private static String url;
    private static String username;
    private static String password;


    private Connection dbc = null;
    private DatabaseHelper dbh = null;

    /**
     *
     */
    private void parseConfFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(conf))) {
            String temp;
            StringBuilder sb = new StringBuilder();
            while ((temp = br.readLine()) != null) {
                sb.append(temp).append("\n");
            }

            JSONObject jo = JSONObject.parseObject(sb.toString());

            url = jo.getString("url");
            className = jo.getString("className");
            username = jo.getString("username");
            password = jo.getString("password");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("JSON string can not be parsed");
        }
    }

    private DatabaseHelper() {
        //read conf
        parseConfFile();

        //connect with database
        try {
            Class.forName(className);
            dbc = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("can not load database driver");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DatabaseHelper getDbh() {
        if (dbh == null) {
            dbh = new DatabaseHelper();
        }
        return dbh;
    }


}
