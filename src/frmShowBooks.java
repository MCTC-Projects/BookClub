import javax.swing.*;

/**
 * Created by boyd on 3/3/15.
 */
public class frmShowBooks {
    public JPanel panel1;
    private JPanel ImagePanel;
    private JFormattedTextField authorTxtField;
    private JFormattedTextField titleTxtField;
    private JTextArea description;



    public void populateDescription(GoodReadsBook grb){
        GoodReadsResponse newResponse = new GoodReadsResponse(grb);

        newResponse.populateFromAPI();

        this.description.setText(newResponse.getBk().getDescription());

    }
}
