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

    private Box armiesBox, siegeBox, siegeOptionsBox;
    private JTextField[] rollFields;
    private JTextField[] warFields;
    private JTextField[] siegeFields;
    private JTextArea outputArea;
    private JRadioButton warMode, partyMode, siegeMode;
    private JRadioButton mainSiegeAttack, mainArmyAttack, opposingSiegeAttack, opposingArmyAttack;
    private JLabel[] groupLabels;
    private JButton rollButton, clearButton;

    protected RollerPanel() {
        setLayout(new BorderLayout());

        Box topPanel = Box.createVerticalBox();

        //Set up radio options for mode
        ButtonGroup modeGroup = new ButtonGroup();
        Box modeBox = Box.createHorizontalBox();

        partyMode = new JRadioButton("Party Mode");
        partyMode.addActionListener(new ModeListener());
        partyMode.setSelected(true);
        modeGroup.add(partyMode);

        warMode = new JRadioButton("War Mode");
        warMode.addActionListener(new ModeListener());
        modeGroup.add(warMode);

        siegeMode = new JRadioButton("Siege Mode");
        siegeMode.addActionListener(new ModeListener());
        modeGroup.add(siegeMode);

        modeBox.add(Box.createHorizontalGlue());
        modeBox.add(partyMode);
        modeBox.add(Box.createHorizontalGlue());
        modeBox.add(warMode);
        modeBox.add(Box.createHorizontalGlue());
        modeBox.add(siegeMode);
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


        siegeBox = Box.createHorizontalBox();

        siegeFields = new JTextField[2];
        siegeFields[MAIN_GROUP] = new JTextField(5);
        siegeFields[MAIN_GROUP].setName("Main Army");
        siegeFields[MAIN_GROUP].setMaximumSize(BOX_DIMENSION);
        siegeFields[MAIN_GROUP].setFont(INPUT_FONT);
        siegeFields[OPPOSING_GROUP] = new JTextField(5);
        siegeFields[OPPOSING_GROUP].setName("Opposing Army");
        siegeFields[OPPOSING_GROUP].setMaximumSize(BOX_DIMENSION);
        siegeFields[OPPOSING_GROUP].setFont(INPUT_FONT);

        siegeBox.add(Box.createHorizontalGlue());
        siegeBox.add(new JLabel("Main Army Defense"));
        siegeBox.add(Box.createHorizontalStrut(5));
        siegeBox.add(siegeFields[MAIN_GROUP]);
        siegeBox.add(Box.createHorizontalStrut(20));
        siegeBox.add(new JLabel("Opposing Army Defense"));
        siegeBox.add(Box.createHorizontalStrut(5));
        siegeBox.add(siegeFields[OPPOSING_GROUP]);
        siegeBox.add(Box.createHorizontalGlue());
        siegeBox.setVisible(false);

        siegeOptionsBox = Box.createHorizontalBox();
        ButtonGroup mainAttackGroup = new ButtonGroup();
        ButtonGroup opposingAttackGroup = new ButtonGroup();

        mainArmyAttack  = new JRadioButton("Attack");
        mainSiegeAttack = new JRadioButton("Siege");
        mainAttackGroup.add(mainArmyAttack);
        mainAttackGroup.add(mainSiegeAttack);
        mainArmyAttack.setSelected(true);

        opposingArmyAttack  = new JRadioButton("Attack");
        opposingSiegeAttack = new JRadioButton("Siege");
        opposingArmyAttack.setSelected(true);
        opposingAttackGroup.add(opposingArmyAttack);
        opposingAttackGroup.add(opposingSiegeAttack);
        opposingArmyAttack.setSelected(true);

        siegeOptionsBox.add(Box.createHorizontalGlue());
        siegeOptionsBox.add(Box.createHorizontalGlue());
        siegeOptionsBox.add(mainArmyAttack);
        siegeOptionsBox.add(Box.createHorizontalStrut(20));
        siegeOptionsBox.add(mainSiegeAttack);
        siegeOptionsBox.add(Box.createHorizontalGlue());
        siegeOptionsBox.add(opposingArmyAttack);
        siegeOptionsBox.add(Box.createHorizontalStrut(20));
        siegeOptionsBox.add(opposingSiegeAttack);
        siegeOptionsBox.add(Box.createHorizontalGlue());
        siegeOptionsBox.add(Box.createHorizontalGlue());
        siegeOptionsBox.setVisible(false);

        topPanel.add(armiesBox);
        topPanel.add(siegeBox);
        topPanel.add(siegeOptionsBox);

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

    private void decreaseArmy(JTextField armyField, int defense, int decreaseBy) {
        int armySize = -1;

        try {
            armySize = Integer.parseInt(armyField.getText());
        } catch (NumberFormatException e) {
            String fieldText = armyField.getText();
            if (fieldText.isEmpty()) {
                JOptionPane.showMessageDialog(armyField, "Please enter an army size");
            } else if (!fieldText.equals("DEAD")) {
                JOptionPane.showMessageDialog(armyField, armyField.getText()
                                                            + " isn't a valid army size.");
            }
        }

        if (armySize > 0) {
            decreaseBy -= defense;
            if (decreaseBy < 0) {
                decreaseBy = 0;
            }
            if (armySize <= decreaseBy) {
                armyField.setText("DEAD");
                JOptionPane.showMessageDialog(armyField, armyField.getName() + " has been destroyed!",
                        "Army died", JOptionPane.PLAIN_MESSAGE);
            } else {
                armyField.setText(Integer.toString(armySize - decreaseBy));
            }
        }
    }

    private void decreaseDefense(JTextField siegeField, int decreaseBy) {
        int defense = -1;

        try {
            defense = Integer.parseInt(siegeField.getText());
        } catch (NumberFormatException e) {
            String fieldText = siegeField.getText();
            if (fieldText.isEmpty()) {
                JOptionPane.showMessageDialog(siegeField, "Please enter the army's defense");
            } else if (!fieldText.equals("DESTROYED")) {
                JOptionPane.showMessageDialog(siegeField, siegeField.getText()
                                                            + " isn't a valid army defense.");
            }
        }

        if (defense > 0) {
            if (defense <= decreaseBy) {
                siegeField.setText("DESTROYED");
                JOptionPane.showMessageDialog(siegeField,
                                                siegeField.getName() + "'s defense has been destroyed!",
                                                "Defense destroyed", JOptionPane.PLAIN_MESSAGE);
            } else {
                siegeField.setText(Integer.toString(defense - decreaseBy));
            }
        }
    }

    private void clearOutput() {
        outputArea.setText("");

        warFields[MAIN_GROUP].setText("");
        warFields[OPPOSING_GROUP].setText("");

        siegeFields[MAIN_GROUP].setText("");
        siegeFields[OPPOSING_GROUP].setText("");
        mainArmyAttack.setSelected(true);
        opposingArmyAttack.setSelected(true);
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
                throw new UnsupportedOperationException("Unsupported button was pressed");
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

            if (resultLines[MAIN_GROUP] == null) {
                return;
            }

            int maxLines = (resultLines.length == 1 || resultLines[0].length > resultLines[1].length
                                                    || resultLines[OPPOSING_GROUP] == null)
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

                if (warMode.isSelected()) {
                    decreaseArmy(warFields[MAIN_GROUP], 0, opposingValue);
                    decreaseArmy(warFields[OPPOSING_GROUP], 0, partyValue);
                } else if (siegeMode.isSelected()) {
                    performSiege(mainArmyAttack.isSelected(), opposingArmyAttack.isSelected(),
                            opposingValue, partyValue);
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
        if (warMode.isSelected() || siegeMode.isSelected()) {
            return "army";
        } else {
            return "party";
        }
    }

    private void performSiege(boolean mainAttacks, boolean opposingAttacks, int partyValue,
                                    int opposingValue)
    {
        if (mainAttacks) {
            int defense = 0;
            String defenseStr;
            do {
                defenseStr = siegeFields[OPPOSING_GROUP].getText();
                try {
                    defense = Integer.parseInt(defenseStr);
                } catch (NumberFormatException e) {
                    if (defenseStr.isEmpty()) {
                        JOptionPane.showMessageDialog(siegeFields[OPPOSING_GROUP],
                                                        "Please enter the army's defense");
                    } else if (!defenseStr.equals("DESTROYED")) {
                        JOptionPane.showMessageDialog(siegeFields[OPPOSING_GROUP], defenseStr
                                                                    + " isn't a valid army defense.");
                    }
                }
            } while (defense < 0 && !defenseStr.equals("DESTROYED"));

            decreaseArmy(warFields[OPPOSING_GROUP], defense, opposingValue);
        } else {
            decreaseDefense(siegeFields[OPPOSING_GROUP], opposingValue);
        }

        if (opposingAttacks) {
            int defense = -1;
            String defenseStr;
            do {
                defenseStr = siegeFields[MAIN_GROUP].getText();
                try {
                    defense = Integer.parseInt(defenseStr);
                } catch (NumberFormatException e) {
                    if (defenseStr.isEmpty()) {
                        JOptionPane.showMessageDialog(siegeFields[MAIN_GROUP],
                                                        "Please enter the army's defense");
                    } else if (!defenseStr.equals("DESTROYED")) {
                        JOptionPane.showMessageDialog(siegeFields[MAIN_GROUP], defenseStr
                                                                    + " isn't a valid army defense.");
                    }
                }
            } while (defense < 0 && !defenseStr.equals("DESTROYED"));

            decreaseArmy(warFields[MAIN_GROUP], defense, partyValue);
        } else {
            decreaseDefense(siegeFields[MAIN_GROUP], partyValue);
        }
    }

    private class ModeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source == warMode && (!armiesBox.isVisible() || siegeBox.isVisible())) {
                armiesBox.setVisible(true);
                siegeBox.setVisible(false);
                siegeOptionsBox.setVisible(false);
                groupLabels[MAIN_GROUP].setText("Main Army");
                groupLabels[OPPOSING_GROUP].setText("Opposing Army");
            } else if (source == partyMode && armiesBox.isVisible()) {
                armiesBox.setVisible(false);
                siegeBox.setVisible(false);
                siegeOptionsBox.setVisible(false);
                groupLabels[MAIN_GROUP].setText("Main Party");
                groupLabels[OPPOSING_GROUP].setText("Opposing Party");
            } else if (source == siegeMode && !siegeBox.isVisible()) {
                armiesBox.setVisible(true);
                siegeBox.setVisible(true);
                siegeOptionsBox.setVisible(true);
                groupLabels[MAIN_GROUP].setText("Main Army");
                groupLabels[OPPOSING_GROUP].setText("Opposing Army");
            }
        }

    }
}
