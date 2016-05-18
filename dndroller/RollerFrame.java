/**************************************************************************************************
 * Program Name   :
 * Author         : Terry Weiss
 * Date           : May 11, 2016
 * Course/Section :
 * Program Description:
 **************************************************************************************************/

package dndroller;

import java.awt.Dimension;
import javax.swing.JFrame;

public class RollerFrame extends JFrame {
    private RollerFrame() {
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

    public static RollerFrame launchDnDRoller() {
        return new RollerFrame();
    }
}
