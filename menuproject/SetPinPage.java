import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SetPinPage extends JFrame {
    private JPasswordField pinField, confirmPinField;
    private int userId;
    
    public SetPinPage(int userId) {
        this.userId = userId;
        
        setTitle("Set Transaction PIN - Sujal Commercial Bank");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel titleLabel = new JLabel("Set Transaction PIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBounds(120, 50, 300, 40);
        mainPanel.add(titleLabel);
        
        // Info label
        JLabel infoLabel = new JLabel("Create a 4-digit PIN for secure transactions");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setBounds(100, 95, 320, 20);
        mainPanel.add(infoLabel);
        
        // PIN label
        JLabel pinLabel = new JLabel("Enter 4-digit PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pinLabel.setForeground(new Color(0, 51, 102));
        pinLabel.setBounds(80, 150, 150, 30);
        mainPanel.add(pinLabel);
        
        // PIN field
        pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 18));
        pinField.setBounds(80, 180, 340, 40);
        pinField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(pinField);
        
        // Confirm PIN label
        JLabel confirmLabel = new JLabel("Confirm PIN:");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmLabel.setForeground(new Color(0, 51, 102));
        confirmLabel.setBounds(80, 230, 150, 30);
        mainPanel.add(confirmLabel);
        
        // Confirm PIN field
        confirmPinField = new JPasswordField();
        confirmPinField.setFont(new Font("Arial", Font.PLAIN, 18));
        confirmPinField.setBounds(80, 260, 340, 40);
        confirmPinField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(confirmPinField);
        
        // Set PIN button
        JButton setPinButton = createStyledButton("Set PIN");
        setPinButton.setBounds(80, 320, 340, 45);
        setPinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSetPin();
            }
        });
        mainPanel.add(setPinButton);
        
        add(mainPanel);
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
    
    private void handleSetPin() {
        String pin = new String(pinField.getPassword());
        String confirmPin = new String(confirmPinField.getPassword());
        
        if (pin.isEmpty() || confirmPin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter PIN!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!pin.equals(confirmPin)) {
            JOptionPane.showMessageDialog(this, "PINs do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (pin.length() != 4 || !pin.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "PIN must be exactly 4 digits!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = AccountService.setTransactionPin(userId, pin);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "PIN set successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new DashboardPage(userId).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to set PIN!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
