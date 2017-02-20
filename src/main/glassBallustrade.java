/**
 * Created by seneda on 18/02/17.
 */
import com.seneda.structures.FormGlassCantilever;

import javax.swing.*;

public class glassBallustrade {
    JFrame frame = new JFrame("Glass Balustrade");
    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel panelMinThicks = new FormGlassCantilever().getMainPanel();

    public glassBallustrade(){
        tabbedPane.add("Min Thicknesses", panelMinThicks);

        frame.getContentPane().add(tabbedPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new glassBallustrade();
            }
        });
    }
}
