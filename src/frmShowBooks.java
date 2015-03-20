import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by boyd on 3/3/15.
 */
public class frmShowBooks {
    private JPanel panel1;
    private JPanel imagePanel;
    private JFormattedTextField authorTxtField;
    private JFormattedTextField titleTxtField;
    private JTextArea description;

    public void setAuthorTxtField(String author) {
        authorTxtField.setText(author);
    }

    public void setTitleTxtField(String title) {
        titleTxtField.setText(title);
    }

    public JPanel getPanel1() {

        return panel1;
    }

    public void populateDescription(GoodReadsBook grb){
        GoodReadsResponse newResponse = new GoodReadsResponse(grb);

        newResponse.populateFromAPI();

        String description = newResponse.getBk().getDescription();

        if (description != null && !description.isEmpty()) {
            this.description.setText(description);
        } else {
            this.description.setText("Description unavailable.");
        }

        URL imageURL = null;

        try {
            imageURL = new URL(grb.getImageUrl());
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }

        //maybe we'll use this, maybe not
//        imagePanel = new ImagePanel(imageURL, 50, 75);
    }
}
