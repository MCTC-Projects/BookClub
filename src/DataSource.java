
/**
 * Created by Toby on 12/5/2014.
 */
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


public class DataSource {



    public static void main()
    {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");

            c = DriverManager.getConnection("jdbc:sqlite:bookclub");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        try {
            statement = c.createStatement();
            statement.setQueryTimeout(30); // 30 seconds
            ResultSet rs = statement.executeQuery("select * from books;");

            // Read the results
            while ( rs.next() ) {
                int id = rs.getInt("isbn");
                String  title = rs.getString("title");

                String  author = rs.getString("author");
                System.out.println( "ISBN = " + id );
                System.out.println( "Title = " + title );
                System.out.println( "author = " + author );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
/*
    //testing to see if this works.  add something please to test -  Toby Iverson

    public void main(){




        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try {

            DriverManager.registerDriver(new org.sqlite.JDBC());
            connection = DriverManager.getConnection("jdbc:sqlite:" +db_string);;
            statement = connection.createStatement();
            statement.setQueryTimeout(30); // 30 seconds
            ResultSet rs = statement.executeQuery("select title from books");
            while (rs.next()) {
                // Read the results
                String fileLists = rs.toString();
                System.out.println(fileLists);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

*/
