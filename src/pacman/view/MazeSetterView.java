package pacman.view;

import pacman.controller.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class MazeSetterView extends JDialog {
    public MazeSetterView(JFrame menu, MenuController controller) {
        super(menu, true);
        setSize(300,250);
        setLocationRelativeTo(menu);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBackground(Color.BLACK);

        int width = getWidth();
        int height = getHeight();

        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBackground(new Color(192,192,192));

        setTitle("Determine the maze size");

        JPanel mazeSetter = new JPanel(new GridBagLayout());
        mazeSetter.setOpaque(false);
        mazeSetter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints tableContent = new GridBagConstraints();


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx=0;
        tableContent.gridy=1;
        tableContent.gridwidth = 1;
        tableContent.weightx = 0.5;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(0, 10, 0, 10);

        JLabel weightLabel =new JLabel("Choose width:");
        weightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        weightLabel.setVerticalAlignment(SwingConstants.CENTER);
        mazeSetter.add (weightLabel, tableContent);

        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx=0;
        tableContent.gridy=2;
        tableContent.gridwidth =1 ;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(0, 10, 0, 10);

        JLabel heightLabel =new JLabel("Choose height:");
        heightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        heightLabel.setVerticalAlignment(SwingConstants.CENTER);
        mazeSetter.add (heightLabel, tableContent);


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx=1;
        tableContent.gridy=1;
        tableContent.gridwidth = 1;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);
        tableContent.ipady= 10;

        SpinnerNumberModel widthSpinnerModel = new SpinnerNumberModel(10, 10, 100, 1);
        JSpinner widthSpinner = new JSpinner(widthSpinnerModel);
        setEditorKeepFocus(widthSpinner.getEditor());
        mazeSetter.add (widthSpinner, tableContent);



        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx=1;
        tableContent.gridy=2;
        tableContent.gridwidth = 1;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);
        tableContent.ipady= 10;


        SpinnerNumberModel heightSpinnerModel = new SpinnerNumberModel(10, 10, 100, 1);
        JSpinner heightSpinner = new JSpinner(heightSpinnerModel);
        setEditorKeepFocus(heightSpinner.getEditor());
        mazeSetter.add (heightSpinner, tableContent);


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx=1;
        tableContent.gridy=3;
        tableContent.gridwidth = 1;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);
        tableContent.ipady= 10;

        JButton playButton = new JButton("Play!");
        playButton.setBackground(new Color(0,153,0));
        mazeSetter.add (playButton, tableContent);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int selectedWidth = 0;
                int selectedHeight = 0;
                try {
                    widthSpinner.commitEdit();
                    heightSpinner.commitEdit();
                    selectedWidth = (int) widthSpinner.getValue();
                    selectedHeight = (int) heightSpinner.getValue();
                    if (selectedWidth < 10 || selectedWidth > 100 || selectedHeight < 10 || selectedHeight > 100)
                        throw new ParseException("Incorrect width or height", 0);
                    else
                        controller.startPlay(menu, selectedWidth, selectedHeight);
                        setVisible(false);
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please enter valid integer from 10 to 100",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }});

        mainPanel.add(mazeSetter, BorderLayout.CENTER);



        add(mainPanel);

    }

    private void setEditorKeepFocus(JComponent editor) {
        if (editor instanceof JSpinner.DefaultEditor) {
            JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
        }
    }

}