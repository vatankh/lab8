package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {
    static  Connection  connection;
//    final static String DB_URL_LOCAL = "jdbc:postgresql://pg:5432/studs";
    final static String DB_URL_LOCAL = "jdbc:postgresql://localhost:9999/studs";
    final static String DB_USER = "s291834";
    final static String DB_PASS = "myy289";

    public static Connection connectToDp(){
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("try to connect");
            connection = DriverManager.getConnection(DB_URL_LOCAL, DB_USER, DB_PASS);
            System.out.println("Successfully connected to: pg" );

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }


}
