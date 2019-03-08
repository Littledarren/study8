package myutil;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseHelper {
    private static final String DBCONF_JSON = "WEB-INF/DBconf.json";

    private static String className;
    private static String url;
    private static String username;
    private static String password;


    private Connection dbc = null;
    private static DatabaseHelper ourInstance = null;

    /**
     *
     */
    private void parseConfFile(ServletContext application) {

        String realPath = application.getRealPath("/");

        try (BufferedReader br = new BufferedReader(new FileReader(realPath + DBCONF_JSON))) {
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
        }
    }

    private DatabaseHelper(ServletContext application) {
        //read DBCONF_JSON
        parseConfFile(application);

        //connect with database
        try {
            Class.forName(className);
            dbc = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("connected to database");
    }

    public static DatabaseHelper getInstance(ServletContext application) {
        if (ourInstance == null) {
            ourInstance = new DatabaseHelper(application);
        }
        return ourInstance;
    }

    public synchronized Object execSql(SqlExectable se) {
        return se.exec(dbc);
    }

}
