/**
 * Created by seneda on 18/02/17.
 */
import com.seneda.structures.FormGlassCantilever;
import com.seneda.structures.FormSelectLamination;

import javax.swing.*;

public class glassBallustrade {
    JFrame frame = new JFrame("Glass Balustrade");
    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel panelMinThicks = new FormGlassCantilever().getMainPanel();
    JPanel panelEffThicknesses = new FormSelectLamination().getMainPanel();

    public glassBallustrade(){
        tabbedPane.add("Min Thicknesses", panelMinThicks);
        tabbedPane.add("Effective Thicknesses", panelEffThicknesses);

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
