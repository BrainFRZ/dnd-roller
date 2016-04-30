package dndroller;

import java.awt.Dimension;
import javax.swing.JFrame;

public class DnDRoller {

    public static void main(String[] args) {
        JFrame frame = new JFrame("DnD Roller");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        RollerPanel rollerPanel = new RollerPanel();
        frame.getContentPane().add(rollerPanel);
        frame.getRootPane().setDefaultButton(rollerPanel.getDefaultButton());

        Dimension minSize = new Dimension(650, 675);
        frame.setMinimumSize(minSize);
        frame.setPreferredSize(minSize);
        frame.pack();
        frame.setVisible(true);
    }

}
