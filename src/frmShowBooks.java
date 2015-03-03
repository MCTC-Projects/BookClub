import javax.swing.*;

/**
 * Created by boyd on 3/3/15.
 */
public class frmShowBooks {
    private JPanel panel1;
    private JPanel ImagePanel;
    private JFormattedTextField authorTxtField;
    private JFormattedTextField titleTxtField;
    private JTextArea description;

    public void setAuthorTxtField(String author) {
        JFormattedTextField authorTxtField = new JFormattedTextField(author);
        this.authorTxtField = authorTxtField;
    }

    public void setTitleTxtField(String title) {
        JFormattedTextField titleTxtField = new JFormattedTextField(title);
        this.titleTxtField = titleTxtField;
    }

    public JPanel getPanel1() {

        return panel1;
    }

    public void populateDescription(GoodReadsBook grb){
        GoodReadsResponse newResponse = new GoodReadsResponse(grb);

        newResponse.populateFromAPI();

        this.description.setText(newResponse.getBk().getDescription());

    }
}
