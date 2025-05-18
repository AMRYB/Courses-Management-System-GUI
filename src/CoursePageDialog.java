import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class CoursePageDialog extends JDialog {
    private JTextField roomField;
    private JTextField branchField;
    private JTextField priceField;
    private JTextField startDateField;
    private JTextField daysOfCourseField;
    private JTextField endDateField;
    private boolean confirmed = false;
    private String courseId;
    
    public CoursePageDialog(JFrame parent, String courseId) {
        super(parent, "Create Course Page", true);
        this.courseId = courseId;
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Room field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Room:"), gbc);
        
        gbc.gridx = 1;
        roomField = new JTextField(20);
        formPanel.add(roomField, gbc);
        
        // Branch field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Branch:"), gbc);
        
        gbc.gridx = 1;
        branchField = new JTextField(20);
        formPanel.add(branchField, gbc);
        
        // Price field
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Price:"), gbc);
        
        gbc.gridx = 1;
        priceField = new JTextField(20);
        formPanel.add(priceField, gbc);
        
        // Start date field
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Start Date:"), gbc);
        
        gbc.gridx = 1;
        startDateField = new JTextField(20);
        formPanel.add(startDateField, gbc);
        
        // Days of course field
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Days of Course:"), gbc);
        
        gbc.gridx = 1;
        daysOfCourseField = new JTextField(20);
        formPanel.add(daysOfCourseField, gbc);
        
        // End date field
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("End Date:"), gbc);
        
        gbc.gridx = 1;
        endDateField = new JTextField(20);
        formPanel.add(endDateField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");
        
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    confirmed = true;
                    dispose();
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        
        // Add panels to dialog
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    private boolean validateInputs() {
        // Validate room
        if (roomField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Room cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            roomField.requestFocus();
            return false;
        }
        
        // Validate branch
        if (branchField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Branch cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            branchField.requestFocus();
            return false;
        }
        
        // Validate price
        if (priceField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Price cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            priceField.requestFocus();
            return false;
        }
        
        // Validate start date
        if (startDateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Start Date cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            startDateField.requestFocus();
            return false;
        }
        
        // Validate days of course
        if (daysOfCourseField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Days of Course cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            daysOfCourseField.requestFocus();
            return false;
        }
        
        // Validate end date
        if (endDateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "End Date cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            endDateField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public String getRoom() {
        return roomField.getText().trim();
    }
    
    public String getBranch() {
        return branchField.getText().trim();
    }
    
    public String getPrice() {
        return priceField.getText().trim();
    }
    
    public String getStartDate() {
        return startDateField.getText().trim();
    }
    
    public String getDaysOfCourse() {
        return daysOfCourseField.getText().trim();
    }
    
    public String getEndDate() {
        return endDateField.getText().trim();
    }
}