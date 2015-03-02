import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by boyd on 2/18/15.
 */
public class GoodReadsResponse {
    GoodReadsBook bk;

    //The isbn for GoodReads takes 10 digit and 13 digit without needing to specify

    GoodReadsResponse(String title,String author){
        Book b = new Book(title, author);
        bk=new GoodReadsBook(b);}

    public void populateFromAPI(){
        try {

            URL firstUrl = new URL("http://www.goodreads.com/book/show");
            URL url = new URL(firstUrl.toString()+"?format=xml&key=J3GUE84DV610O7QQhEp5Jw&id=1956");
            HttpURLConnection httpCon = (HttpURLConnection)url.openConnection();
            httpCon.setRequestMethod("GET");
            httpCon.setReadTimeout(15*1000);
            httpCon.connect();
            if(httpCon.getResponseCode()==200){
                //I got my xml parsing instruction from this http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/ website

                Scanner xmlscanner = new Scanner(httpCon.getInputStream());

                StringBuilder xmlFile = new StringBuilder();
                String line;
                while(xmlscanner.hasNextLine()){
                    line = xmlscanner.nextLine();
                    xmlFile.append(line);
                }
                xmlscanner.close();
                httpCon.disconnect();
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlFile.toString()));
                Document doc = dBuilder.parse(is);


                doc.getDocumentElement().normalize();
                NodeList descNodes = doc.getElementsByTagName("description");
                NodeList isbnNodes = doc.getElementsByTagName("isbn");
                NodeList ratingNodes = doc.getElementsByTagName("average_rating");
                this.bk.bk.setDescription(descNodes.item(0).getTextContent().replaceAll("[<][/a-z]{1,20}[>]", ""));
                this.bk.bk.setIsbn(Integer.parseInt(isbnNodes.item(0).getTextContent()));
                this.bk.AveRating = Double.parseDouble(ratingNodes.item(0).getTextContent());
            }else{
                System.out.println(httpCon.getResponseMessage().toString());
            }
        }catch(MalformedURLException mue){

        }catch(IOException ioe){
            System.out.println(ioe.toString());
        }catch(Exception e){
            System.out.println(e.toString());

        }

    }
}