import com.google.gson.Gson;



import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;


/**
 * Created by boyd on 3/9/15.
 */

// this requires GSON from Google http://search.maven.org/#artifactdetails|com.google.code.gson|gson|2.3.1|jar

public class WebInterface {



    //The website is looking for base64 encoded bytestrings of json of {email : _email_address_, password: _password_}
    public boolean login(String emailAddress, String password){
        User user = new User(emailAddress,password);
        Boolean willLogin = false;
        Base64.Encoder b64encoder = Base64.getUrlEncoder();
        Gson jsonMaker = new Gson();
        String json = jsonMaker.toJson(user);
        String b64json = b64encoder.encodeToString(json.getBytes(Charset.forName("UTF-8")));
        try{

            URL url = new URL("http://mctc-bookclub.herokuapp.com/applogin/?data="+b64json);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15 * 1000);
            connection.connect();

            Scanner jsonScanner = new Scanner(connection.getInputStream());
            StringBuilder responseJSON = new StringBuilder();
            String line;
            while(jsonScanner.hasNextLine()){
                line = jsonScanner.nextLine();
                responseJSON.append(line);
            }
            connection.disconnect();
            willLogin = jsonMaker.fromJson(responseJSON.toString(),Login.class).login;

        }catch(MalformedURLException mue){
            System.out.println("That url wasn't right.");
        }catch(IOException ioe){
            System.out.println(ioe.toString());
        }
        return willLogin;
    }

        class User{
        private String email;
        private String password;

        User(String email,String password){
            this.email = email;
            this.password = password;
        }
    }

    public ArrayList<Book> getBooks(String email, String password){
        ArrayList<Book> list= new ArrayList<Book>();
        User user = new User(email,password);
        Base64.Encoder b64encoder = Base64.getUrlEncoder();
        Gson jsonMaker = new Gson();
        String json = jsonMaker.toJson(user);
        String b64json = b64encoder.encodeToString(json.getBytes(Charset.forName("UTF-8")));
        try{

            URL url = new URL("http://mctc-bookclub.herokuapp.com/books/?data="+b64json);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15 * 1000);
            connection.connect();
            Scanner jsonScanner = new Scanner(connection.getInputStream());
            StringBuilder responseJSON = new StringBuilder();
            String line;
            while(jsonScanner.hasNextLine()){
                line = jsonScanner.nextLine();
                responseJSON.append(line);
            }
            connection.disconnect();

            Field[] books = jsonMaker.fromJson(responseJSON.toString(),Field[].class);
            for(Field b : books){
                list.add(b.fields);
            }

        }catch(MalformedURLException mue){
            System.out.println("That url wasn't right.");
        }catch(IOException ioe){
            System.out.println(ioe.toString());
        }


        return list;
    }

    public boolean postBooks(ArrayList<Book> listOfBooks,String email, String password){
        boolean results = false;
        Book[] books = new Book[listOfBooks.size()];
        int i = 0;
        for(Book b:listOfBooks){
            books[i] = b;
            i++;
        }

        User user = new User(email,password);
        Base64.Encoder b64encoder = Base64.getUrlEncoder();
        Gson jsonMaker = new Gson();
        String json = jsonMaker.toJson(user);
        String b64json = b64encoder.encodeToString(json.getBytes(Charset.forName("UTF-8")));
        try{
            URL url = new URL("http://mctc-bookclub.herokuapp.com/books/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(15 * 1000);
            connection.setDoOutput(true);
            connection.connect();
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write("data="+b64json+"&");
            wr.write("books=" +jsonMaker.toJson(books));
            wr.flush();
            Scanner jsonScanner = new Scanner(connection.getInputStream());
            StringBuilder responseJSON = new StringBuilder();
            String line;
            while(jsonScanner.hasNextLine()){
                line = jsonScanner.nextLine();
                responseJSON.append(line);
            }
            connection.disconnect();
            results = jsonMaker.fromJson(responseJSON.toString(),Login.class).login;

        }catch(MalformedURLException mue){
            System.out.println("That url wasn't right.");
        }catch(IOException ioe){
            System.out.println(ioe.toString());
        }
        return results;
    }

    class Login{
        private boolean login;

        public Login(boolean login) {
            this.login = login;
        }
    }

    class Field{
        private Book fields;
    }
}
