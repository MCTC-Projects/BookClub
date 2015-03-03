/**
 * Created by Toby on 2/12/2015.
 *
 * created based off of tutorial at http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 */

import java.sql.*;
import java.util.ArrayList;

public class DB {
    public static Connection c = null;
    public static ResultSet rs = null;
    public static Statement statement = null;


    public static void  Connect() {


        //Connection c = null;
        //ResultSet rs = null;
        //Statement statement = null;
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
            rs = statement.executeQuery("select * from books2;");

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
            String sql = "INSERT INTO books2 (isbn,title,author) " +
                    "VALUES (" + b.getISBN() + ", " + b.getTitle() + "," + b.getAuthor() + ");";
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    //adds a book from the database
    public Book getBook(int isbn) {
        Book b = null;
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from books2 where isbn = " + isbn + ";";
            ResultSet rs = stmt.executeQuery(sql);

            String i = rs.getString("isbn");
            String t = rs.getString("title");
            String a = rs.getString("author");

            b = new Book(i, t, a);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return b;
    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = null;

        try {
            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from books2;";
            ResultSet rs = stmt.executeQuery(sql);
            //pull books into arraylist
            while (rs.next()) {
                String i = rs.getString("isbn");
                String t = rs.getString("title");
                String a = rs.getString("author");
                Book b = new Book(i, t, a);
                books.add(b);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return books;
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



    //adds a member to the database
    public Member getMember(int mid) {
        Member mem = null;
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from members where mid = " + mid + ";";
            ResultSet rs = stmt.executeQuery(sql);

            int m = rs.getInt("mid");
            String n = rs.getString("name");
            String e = rs.getString("email");

            mem = new Member(m, n, e);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return mem;
    }


    //todo: delete a member

    public ArrayList<Member> getAllMembers() {
        ArrayList<Member> members = null;

        try {
            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from members;";
            ResultSet rs = stmt.executeQuery(sql);
            //pull members into arraylist
            while (rs.next()) {

                int m = rs.getInt("mid");
                String n = rs.getString("name");
                String e = rs.getString("email");

                Member mem = new Member(m, n, e);
                members.add(mem);

            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return members;
    }

    //todo remove member

    //adds a ratting to the database
    void addRating(Rating r) {
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "INSERT INTO ratings (mid, isbn, rating, comments) " +
                    "VALUES (" + r.getMID() + ", " + r.getIsbn() + ", " + r.getRating() + "," + r.getComments() + ");";
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }

    // get a ranking
    public Rating getRating(String isbn, int mid) {
        Rating rating = null;
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from ratings where isbn = " + isbn + " AND mid = "+mid+";";
            ResultSet rs = stmt.executeQuery(sql);
            long l = rs.getLong("isbn");
            String i = Long.toString(l);
            int m = rs.getInt("mid");
            int r = rs.getInt("rating");
            String c = rs.getString("comments");

            rating = new Rating(i, m, r, c);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return rating;
    }

    public ArrayList<Rating> getRatingsForBook(int isbn) {
        ArrayList<Rating> ratings = null;
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from ratings where isbn = " + isbn + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                long l = rs.getLong("isbn");
                String i = Long.toString(l);
                int m = rs.getInt("mid");
                int r = rs.getInt("rating");
                String c = rs.getString("comments");

                Rating rating = new Rating(i, m, r, c);
                ratings.add(rating);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ratings;
    }


    public ArrayList<Rating> getRatingsForMember(int mid) {
        ArrayList<Rating> ratings = null;
        try {

            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from ratings where mid = " + mid + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                long l = rs.getLong("isbn");
                String i = Long.toString(l);
                int m = rs.getInt("mid");
                int r = rs.getInt("rating");
                String c = rs.getString("comments");

                Rating rating = new Rating(i, m, r, c);
                ratings.add(rating);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ratings;
    }



    public ArrayList<Rating> getAllRatings() {
        ArrayList<Rating> ratings = null;

        try {
            //start statement
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            //sql statement
            String sql = "select * from ratings;";
            ResultSet rs = stmt.executeQuery(sql);
            //pull members into arraylist
            while (rs.next()) {
                long l = rs.getLong("isbn");
                String i = Long.toString(l);
                int m = rs.getInt("mid");
                int r = rs.getInt("rating");
                String c = rs.getString("comments");

                Rating rating = new Rating(i, m, r, c);
                ratings.add(rating);

            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ratings;
    }
}