package dndroller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

class RollerPanel extends JPanel {

    private static final Font INPUT_FONT  = new Font("Arial", Font.PLAIN, 12);
    private static final Font OUTPUT_FONT = new Font("Monospaced", Font.PLAIN, 12);

    private static final int MAIN_GROUP = 0, OPPOSING_GROUP = 1;
    private static final int NUM_DICE = 0, DIE_VALUE = 1;

    private Box armiesBox;
    private JTextField[] rollFields;
    private JTextField[] warFields;
    private JTextArea outputArea;
    private JRadioButton warMode, partyMode;
    private JLabel[] groupLabels;
    private JButton rollButton, clearButton;

    protected RollerPanel() {
        setLayout(new BorderLayout());

        Box topPanel = Box.createVerticalBox();

        //Set up radio options for mode
        ButtonGroup modeGroup = new ButtonGroup();
        Box modeBox = Box.createHorizontalBox();

        warMode = new JRadioButton("War Mode");
        warMode.addActionListener(new ModeListener());
        modeGroup.add(warMode);

        partyMode = new JRadioButton("Party Mode");
        partyMode.addActionListener(new ModeListener());
        partyMode.setSelected(true);
        modeGroup.add(partyMode);

        modeBox.add(Box.createHorizontalGlue());
        modeBox.add(partyMode);
        modeBox.add(Box.createHorizontalGlue());
        modeBox.add(warMode);
        modeBox.add(Box.createHorizontalGlue());

        topPanel.add(modeBox);

        //Set up input panel
        Box inputBox = Box.createHorizontalBox();

        final Dimension BOX_DIMENSION = new Dimension(0, 30);
        rollFields = new JTextField[2];
        rollFields[MAIN_GROUP] = new JTextField(10);
        rollFields[MAIN_GROUP].setMaximumSize(BOX_DIMENSION);
        rollFields[MAIN_GROUP].setFont(INPUT_FONT);
        rollFields[OPPOSING_GROUP] = new JTextField(10);
        rollFields[OPPOSING_GROUP].setMaximumSize(BOX_DIMENSION);
        rollFields[OPPOSING_GROUP].setFont(INPUT_FONT);

        groupLabels = new JLabel[2];
        groupLabels[MAIN_GROUP] = new JLabel("Main Party");
        groupLabels[OPPOSING_GROUP] = new JLabel("Opposing Party");

        inputBox.add(Box.createHorizontalGlue());
        inputBox.add(groupLabels[MAIN_GROUP]);
        inputBox.add(Box.createHorizontalStrut(5));
        inputBox.add(rollFields[MAIN_GROUP]);
        inputBox.add(Box.createHorizontalStrut(20));
        inputBox.add(groupLabels[OPPOSING_GROUP]);
        inputBox.add(Box.createHorizontalStrut(5));
        inputBox.add(rollFields[OPPOSING_GROUP]);
        inputBox.add(Box.createHorizontalGlue());

        topPanel.add(inputBox);

        armiesBox = Box.createHorizontalBox();

        warFields = new JTextField[2];
        warFields[MAIN_GROUP] = new JTextField(5);
        warFields[MAIN_GROUP].setName("Main Army");
        warFields[MAIN_GROUP].setMaximumSize(BOX_DIMENSION);
        warFields[MAIN_GROUP].setFont(INPUT_FONT);
        warFields[OPPOSING_GROUP] = new JTextField(5);
        warFields[OPPOSING_GROUP].setName("Opposing Army");
        warFields[OPPOSING_GROUP].setMaximumSize(BOX_DIMENSION);
        warFields[OPPOSING_GROUP].setFont(INPUT_FONT);

        armiesBox.add(Box.createHorizontalGlue());
        armiesBox.add(new JLabel("Main Army Size"));
        armiesBox.add(Box.createHorizontalStrut(5));
        armiesBox.add(warFields[MAIN_GROUP]);
        armiesBox.add(Box.createHorizontalStrut(20));
        armiesBox.add(new JLabel("Opposing Army Size"));
        armiesBox.add(Box.createHorizontalStrut(5));
        armiesBox.add(warFields[OPPOSING_GROUP]);
        armiesBox.add(Box.createHorizontalGlue());

        armiesBox.setVisible(false);
        topPanel.add(armiesBox);

        add(topPanel, BorderLayout.NORTH);

        //Create output panel
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.PAGE_AXIS));

        outputArea = new JTextArea();
        outputArea.setFont(OUTPUT_FONT);
        outputArea.setColumns(75);
        outputArea.setRows(30);
        outputArea.setEditable(false);
        outputArea.setMinimumSize(new Dimension(250, 400));

        JScrollPane outputPane = new JScrollPane(outputArea);
        outputPane.setMinimumSize(new Dimension(250, 400));
        outputPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel resultsLabel = new JLabel("Roll results");
        resultsLabel.setAlignmentX(CENTER_ALIGNMENT);

        outputPanel.setMinimumSize(new Dimension(250, 600));
        outputPanel.add(Box.createHorizontalStrut(10));
        outputPanel.add(resultsLabel);
        outputPanel.add(outputPane);
        outputPanel.add(Box.createHorizontalStrut(10));

        add(outputPanel, BorderLayout.CENTER);

        //Set up action button panel
        Box actionBox = Box.createHorizontalBox();
        rollButton  = new JButton("Roll");
        clearButton = new JButton("Clear");

        ActionListener buttonListener = new ButtonListener();
        rollButton.addActionListener(buttonListener);
        clearButton.addActionListener(buttonListener);

        actionBox.add(Box.createHorizontalGlue());
        actionBox.add(rollButton);
        actionBox.add(Box.createHorizontalStrut(10));
        actionBox.add(clearButton);
        actionBox.add(Box.createHorizontalGlue());

        add(actionBox, BorderLayout.SOUTH);
    }

    protected JButton getDefaultButton() {
        return rollButton;
    }

    private void decreaseArmy(JTextField armyField, int decreaseBy) {
        int armySize = -1;

        try {
            armySize = Integer.parseInt(armyField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(armyField, armyField.getText() + " isn't a valid army size.");
        }

        if (armySize > 0) {
            if (armySize <= decreaseBy) {
                armyField.setText("DEAD");
                JOptionPane.showMessageDialog(armyField, armyField.getName() + " has been destroyed!",
                        "Army died", JOptionPane.PLAIN_MESSAGE);
            } else {
                armyField.setText(Integer.toString(armySize - decreaseBy));
            }
        }
    }

    private void clearOutput() {
        outputArea.setText("");

        if (armiesBox.isVisible()) {
            warFields[MAIN_GROUP].setText("");
            warFields[OPPOSING_GROUP].setText("");
        }
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == rollButton) {
                handleRoll();
            } else if (source == clearButton) {
                clearOutput();
            } else {
                throw new UnsupportedOperationException("Unsupported button: " + source);
            }
        }

        private void handleRoll() {
            String[][] resultLines;
            StringBuilder appendText = new StringBuilder();

            if (rollFields[DIE_VALUE].getText().isEmpty()) {
                resultLines = new String[1][];
            } else {
                if (rollFields[NUM_DICE].getText().isEmpty()) {
                    rollFields[NUM_DICE].setText(rollFields[1].getText());
                }
                resultLines = new String[2][];
            }

            for (int i = 0; i < resultLines.length; i++) {
                try {
                    resultLines[i] = Roller.rollResult(groupLabels[i].getText(), rollFields[i].getText());
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(outputArea, e.getMessage());
                }
            }

            int maxLines = (resultLines.length == 1 || resultLines[0].length > resultLines[1].length)
                                ? resultLines[MAIN_GROUP].length : resultLines[OPPOSING_GROUP].length;

            for (int lineNum = 0; lineNum < maxLines - 1; lineNum++) {
                for (String[] resultLine : resultLines) {
                    if (lineNum >= resultLine.length - 1) {
                        appendText.append(String.format("     %-30s", " "));
                    } else {
                        appendText.append(String.format("     %-30s", resultLine[lineNum]));
                    }
                }
                appendText.append("\n");
            }
            appendText.append(String.format("     %-30s",
                    resultLines[MAIN_GROUP][resultLines[MAIN_GROUP].length - 1]));

            if (resultLines.length == 2) {
                appendText.append(String.format("     %-30s%n",
                        resultLines[OPPOSING_GROUP][resultLines[OPPOSING_GROUP].length - 1]));

                int partyValue = Integer.parseInt(resultLines[MAIN_GROUP][resultLines[MAIN_GROUP].length - 1]
                                                        .substring(7));
                int opposingValue = Integer.parseInt(resultLines[OPPOSING_GROUP][resultLines[OPPOSING_GROUP].length - 1]
                                                        .substring(7));

                if (armiesBox.isVisible()) {
                    decreaseArmy(warFields[MAIN_GROUP], partyValue);
                    decreaseArmy(warFields[OPPOSING_GROUP], opposingValue);
                }

                if (partyValue == opposingValue) {
                    appendText.append("     They tied!");
                } else if (partyValue > opposingValue) {
                    appendText.append("     The main ").append(armyOrParty()).append(" won!");
                } else {
                    appendText.append("     The opposing ").append(armyOrParty()).append(" won.");
                }
            }

            appendText.append("\n\n");

            outputArea.append(appendText.toString());
        }
    }

    private String armyOrParty() {
        if (warMode.isSelected()) {
            return "army";
        } else {
            return "party";
        }
    }

    private class ModeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == warMode && !armiesBox.isVisible()) {
                armiesBox.setVisible(true);
                groupLabels[MAIN_GROUP].setText("Main Army");
                groupLabels[OPPOSING_GROUP].setText("Opposing Army");
                clearOutput();
            } else if (source == partyMode && armiesBox.isVisible()) {
                armiesBox.setVisible(false);
                groupLabels[MAIN_GROUP].setText("Main Party");
                groupLabels[OPPOSING_GROUP].setText("Opposing Party");
                clearOutput();
            }
        }

    }
}
