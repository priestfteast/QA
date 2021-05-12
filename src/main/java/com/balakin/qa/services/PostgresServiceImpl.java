package com.balakin.qa.services;

import org.springframework.security.core.parameters.P;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PostgresServiceImpl implements PostgresService{
    @Override
    public List<String> getAudioFileNames(String id) {
       List<String> audioFileNames = new ArrayList();

        Connection c = null;
        Statement stmt = null;
        try {
            Properties props = new Properties();
            props.load(new FileReader("C:/java/QA.properties"));
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(props.getProperty("DBAdresse"),
                            props.getProperty("username"), props.getProperty("password"));
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM public.\"S_Connections\"\n" +
                    "WHERE \"IDSeance\" = '"+id +
                    "' and \"AWavFile\" is not null\n" +
                    "ORDER BY \"ID\" ASC LIMIT 100;" );
            while ( rs.next() ) {
                String AWavFile = rs.getString("AWavFile");
                audioFileNames.add(AWavFile);
                String BWavFile = rs.getString("BWavFile");
                audioFileNames.add(BWavFile);

            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return audioFileNames;
    }

    public static void main(String[] args) {
        PostgresService postgresService = new PostgresServiceImpl();
        System.out.println(postgresService.getAudioFileNames("5109677924"));
    }
}
