import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.Node;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by boyd on 2/18/15.
 */
public class GoodReadsResponse {
    private GoodReadsBook bk;
    private ArrayList<GoodReadsBook> SuggestedBooks;

    //The isbn for GoodReads takes 10 digit and 13 digit without needing to specify

    public GoodReadsResponse(GoodReadsBook grb){
        this.bk = grb;
    }

    public GoodReadsResponse(String title,String author){
        this.bk=new GoodReadsBook(title,author);
        this.SuggestedBooks = new ArrayList<GoodReadsBook>();}

    public void populateFromAPI(){
            URL url;
        try {
            if(this.bk.getISBN()==null) {

                URL firstUrl = new URL("http://www.goodreads.com/book/title.xml");
                url = new URL(firstUrl.toString() + "?key=J3GUE84DV610O7QQhEp5Jw&title=" + this.bk.getTitle().replace(' ', '+') + "&author=" + this.bk.getAuthor().replace(' ', '+'));
            }else{
                URL firstUrl = new URL("http://www.goodreads.com/book/title.xml");
                url = new URL(firstUrl.toString() + "?key=J3GUE84DV610O7QQhEp5Jw&isbn=" + this.bk.getISBN());
            }

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
                NodeList idNodes = doc.getElementsByTagName("id");
                NodeList descNodes = doc.getElementsByTagName("description");
                NodeList isbnNodes = doc.getElementsByTagName("isbn");
                NodeList ratingNodes = doc.getElementsByTagName("average_rating");
                this.bk.setDescription(descNodes.item(0).getTextContent().replaceAll("[<][/a-z]{1,20}[>]", ""));
                this.bk.setISBN(isbnNodes.item(0).getTextContent());
                this.bk.AveRating = Double.parseDouble(ratingNodes.item(0).getTextContent());
                this.bk.setId(Integer.parseInt(idNodes.item(0).getTextContent()));
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

    public ArrayList<GoodReadsBook> getSuggestedBooks() {
        return SuggestedBooks;
    }

    public void searchForBooks(){
        try {

            URL firstUrl = new URL("http://www.goodreads.com/book/show.xml");
            URL url = new URL(firstUrl.toString()+"?key=J3GUE84DV610O7QQhEp5Jw&id="+this.bk.getId());
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
                NodeList SimilarBookNodes = doc.getElementsByTagName("book");
                for(int i=1;i<SimilarBookNodes.getLength();i++){
                    Book bk = new Book(SimilarBookNodes.item(i).getChildNodes().item(1).getTextContent(),SimilarBookNodes.item(i).getChildNodes().item(8).getTextContent().split("    ")[2]);
                    GoodReadsBook newbook = new GoodReadsBook(bk.getTitle(),bk.getAuthor());

                    newbook.setAveRating(Double.parseDouble(SimilarBookNodes.item(i).getChildNodes().item(6).getTextContent()));
                    newbook.setImageUrl(SimilarBookNodes.item(i).getChildNodes().item(5).getTextContent());
                    this.SuggestedBooks.add(newbook);
                }

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

    public GoodReadsBook getBk() {
        return bk;
    }

}