/**
 * Created by Toby on 2/12/2015.
 *
 * created based off of tutorial at http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 */

import java.sql.*;

public class DB {
    public Connection c = null;
    public ResultSet rs = null;
    public Statement statement = null;


     void Connect() {


        try {
            Class.forName("org.sqlite.JDBC");

            c = DriverManager.getConnection("jdbc:sqlite:bookclub");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        try {
            statement = c.createStatement();
            statement.setQueryTimeout(30); // 30 seconds
            rs = statement.executeQuery("select * from books;");

            // Read the results
            while (rs.next()) {
                int id = rs.getInt("isbn");
                String title = rs.getString("title");

                String author = rs.getString("auther");
                System.out.println("ISBN = " + id);
                System.out.println("Title = " + title);
                System.out.println("author = " + author);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //adds a book to the database
        void addBook(Book b) {
            try {

                //start statement
                c.setAutoCommit(false);
                Statement stmt = c.createStatement();
                //sql statement
                String sql = "INSERT INTO books (isbn,title,author) " +
                        "VALUES (" + b.getISBN() + ", " + b.getTitle() + "," + b.getAuthor() + ");";
                stmt.executeUpdate(sql);

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }



    //adds a book from the database
    public Book getBook(long isbn) {
        Book b = null;
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from books where isbn = " + isbn + ";";
            ResultSet rs = stmt.executeQuery(sql);

            long i = rs.getLong("isbn");
            String t = rs.getString("title");
            String a = rs.getString("author");

            b = new Book("" + i);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return b;
    }


    //adds a member to the database
    void addMember(Member m) {
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "INSERT INTO members (name, email) " +
                    "VALUES (" + m.getName() + "," + m.getEmail() + ");";
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    //todo: get a member set up return


    //adds a member to the database
    public Member getMember(int mid) {
        Member mem = null;
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from members where mid = "+mid+";";
            ResultSet rs = stmt.executeQuery(sql);

            int m= rs.getInt("mid");
            String n = rs.getString("name");
            String e = rs.getString("email");

            mem =  new Member(m,n,e);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return mem;
    }


    //todo: delete a member





    //adds a ratting to the database
    void addRating(Rating r) {
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "INSERT INTO ratings (mid, isbn, rating, comments) " +
                    "VALUES ("+ r.getMember().getMid() +", " + r.getBook().getISBN() + ", " + r.getRating() + "," + r.getComments() + ");";
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }

    // get a ranking
    public Rating getRating(int isbn) {
        Rating rating = null;
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from ratings where isbn = "+isbn+";";
            ResultSet rs = stmt.executeQuery(sql);

            int i = rs.getInt("isbn");
            int m= rs.getInt("mid");
            int r = rs.getInt("rating");
            String c = rs.getString("comments");

            //rating =  new Rating(new Book(""+i),new Member(m),r,c);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return rating;
    }
}
