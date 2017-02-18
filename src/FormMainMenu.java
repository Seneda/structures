import com.seneda.structures.FormSelectLamination;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by seneda on 17/02/17.
 */
public class FormMainMenu {
    private JButton ButtonMinThicknesses;
    private JButton ButtonEffectiveThicknesses;
    private JButton button3;
    private JButton button4;

    public FormMainMenu() {
        ButtonMinThicknesses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Choose Lamination Properties");
                frame.setContentPane(new FormSelectLamination().PanelMain);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
