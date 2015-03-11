import com.google.gson.Gson;


import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Base64;
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
            System.out.println("something went wrong");
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
    class Login{
        private boolean login;

        public Login(boolean login) {
            this.login = login;
        }
    }
}
