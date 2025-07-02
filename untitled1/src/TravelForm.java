import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TravelForm extends JFrame {
    JRadioButton maleBtn, femaleBtn;
    JComboBox<String> destinationCombo;
    JButton submitBtn;
    ButtonGroup genderGroup;

    public TravelForm() {
        setTitle("Travel Form");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Gender radio buttons
        maleBtn = new JRadioButton("Male");
        femaleBtn = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleBtn);
        genderGroup.add(femaleBtn);

        // Destination dropdown
        String[] destinations = {"Select Destination", "New York", "Paris", "Tokyo", "Sydney"};
        destinationCombo = new JComboBox<>(destinations);

        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> onSubmit());

        add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleBtn);
        genderPanel.add(femaleBtn);
        add(genderPanel);

        add(new JLabel("Destination:"));
        add(destinationCombo);

        // Empty labels to align button
        add(new JLabel(""));
        add(submitBtn);

        setVisible(true);
    }

    private void onSubmit() {
        String gender = null;
        if (maleBtn.isSelected()) gender = "Male";
        else if (femaleBtn.isSelected()) gender = "Female";

        String destination = (String) destinationCombo.getSelectedItem();

        // Validation
        if (gender == null) {
            JOptionPane.showMessageDialog(this, "Please select your gender.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (destinationCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a destination.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmation message
        String message = "Gender: " + gender + "\nDestination: " + destination;
        JOptionPane.showMessageDialog(this, message, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new TravelForm();
    }
}
