import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by DayDay on 3/20/2015.
 */
public class ImagePanel extends JPanel {

    private Image image;
    private int x;
    private int y;

    public ImagePanel(URL imageURL, int x, int y) {
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(imageURL);
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paint(g);
        g.drawImage(image, x, y, null);
    }
}
