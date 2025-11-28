import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterPage extends JFrame {
    private JTextField nameField, emailField, addressField, citizenshipField, nidField;
    private JTextField dobField;
    private JPasswordField passwordField, retypePasswordField;
    
    public RegisterPage() {
        setTitle("Register - Sujal Commercial Bank");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBounds(150, 20, 300, 40);
        mainPanel.add(titleLabel);
        
        int yPos = 80;
        int fieldHeight = 35;
        int spacing = 50;
        
        // Name
        addLabel(mainPanel, "Full Name:", 50, yPos);
        nameField = addTextField(mainPanel, 200, yPos);
        yPos += spacing;
        
        // Email
        addLabel(mainPanel, "Email:", 50, yPos);
        emailField = addTextField(mainPanel, 200, yPos);
        yPos += spacing;
        
        // Address
        addLabel(mainPanel, "Address:", 50, yPos);
        addressField = addTextField(mainPanel, 200, yPos);
        yPos += spacing;
        
        // Date of Birth
        addLabel(mainPanel, "Date of Birth:", 50, yPos);
        dobField = addTextField(mainPanel, 200, yPos);
        JLabel dobHint = new JLabel("(YYYY-MM-DD)");
        dobHint.setFont(new Font("Arial", Font.ITALIC, 11));
        dobHint.setForeground(Color.GRAY);
        dobHint.setBounds(420, yPos, 100, fieldHeight);
        mainPanel.add(dobHint);
        yPos += spacing;
        
        // Citizenship
        addLabel(mainPanel, "Citizenship No:", 50, yPos);
        citizenshipField = addTextField(mainPanel, 200, yPos);
        yPos += spacing;
        
        // NID
        addLabel(mainPanel, "National ID:", 50, yPos);
        nidField = addTextField(mainPanel, 200, yPos);
        yPos += spacing;
        
        // Password
        addLabel(mainPanel, "Password:", 50, yPos);
        passwordField = addPasswordField(mainPanel, 200, yPos);
        yPos += spacing;
        
        // Retype Password
        addLabel(mainPanel, "Retype Password:", 50, yPos);
        retypePasswordField = addPasswordField(mainPanel, 200, yPos);
        yPos += spacing + 10;
        
        // Register button
        JButton registerButton = createStyledButton("Register");
        registerButton.setBounds(150, yPos, 300, 45);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });
        mainPanel.add(registerButton);
        
        // Back to login link
        JButton backButton = new JButton("Already have an account? Login");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setForeground(new Color(0, 102, 204));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBounds(180, yPos + 55, 250, 25);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HomePage().setVisible(true);
                dispose();
            }
        });
        mainPanel.add(backButton);
        
        add(mainPanel);
    }
    
    private void addLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(0, 51, 102));
        label.setBounds(x, y, 140, 35);
        panel.add(label);
    }
    
    private JTextField addTextField(JPanel panel, int x, int y) {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBounds(x, y, 300, 35);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(field);
        return field;
    }
    
    private JPasswordField addPasswordField(JPanel panel, int x, int y) {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBounds(x, y, 300, 35);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(field);
        return field;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 153, 76));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 128, 64));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 153, 76));
            }
        });
        
        return button;
    }
    
    private void handleRegistration() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String dob = dobField.getText().trim();
        String citizenship = citizenshipField.getText().trim();
        String nid = nidField.getText().trim();
        String password = new String(passwordField.getPassword());
        String retypePassword = new String(retypePasswordField.getPassword());
        
        // Validation
        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || dob.isEmpty() || 
            citizenship.isEmpty() || nid.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(retypePassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Register user
        boolean success = UserService.registerUser(name, email, address, dob, citizenship, nid, password);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            new LoginPage().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed! Email or NID may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
