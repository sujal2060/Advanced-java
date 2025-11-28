import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoadMoneyPage extends JFrame {
    private int userId;
    private JTextField amountField;
    private JPasswordField pinField;
    
    public LoadMoneyPage(int userId) {
        this.userId = userId;
        
        setTitle("Load Money - Sujal Commercial Bank");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel titleLabel = new JLabel("Load Money");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBounds(210, 30, 200, 40);
        mainPanel.add(titleLabel);
        
        // Current balance
        double balance = AccountService.getBalance(userId);
        JLabel balanceLabel = new JLabel(String.format("Current Balance: Rs. %.2f", balance));
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        balanceLabel.setForeground(new Color(0, 153, 76));
        balanceLabel.setBounds(200, 75, 300, 25);
        mainPanel.add(balanceLabel);
        
        // Info label
        JLabel infoLabel = new JLabel("Add money to your account");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setBounds(210, 100, 250, 20);
        mainPanel.add(infoLabel);
        
        // Amount label
        JLabel amountLabel = new JLabel("Amount (Rs.):");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountLabel.setForeground(new Color(0, 51, 102));
        amountLabel.setBounds(80, 160, 150, 30);
        mainPanel.add(amountLabel);
        
        // Amount field
        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountField.setBounds(80, 190, 440, 40);
        amountField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(amountField);
        
        // PIN label
        JLabel pinLabel = new JLabel("Transaction PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pinLabel.setForeground(new Color(0, 51, 102));
        pinLabel.setBounds(80, 240, 150, 30);
        mainPanel.add(pinLabel);
        
        // PIN field
        pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setBounds(80, 270, 440, 40);
        pinField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(pinField);
        
        // Load button
        JButton loadButton = createStyledButton("Load Money");
        loadButton.setBounds(80, 340, 210, 45);
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLoadMoney();
            }
        });
        mainPanel.add(loadButton);
        
        // Back button
        JButton backButton = createStyledButton("Back");
        backButton.setBackground(new Color(128, 128, 128));
        backButton.setBounds(310, 340, 210, 45);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DashboardPage(userId).setVisible(true);
                dispose();
            }
        });
        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(new Color(100, 100, 100));
            }
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(new Color(128, 128, 128));
            }
        });
        mainPanel.add(backButton);
        
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
    
    private void handleLoadMoney() {
        String amountStr = amountField.getText().trim();
        String pin = new String(pinField.getPassword());
        
        if (amountStr.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verify PIN
        if (!AccountService.verifyPin(userId, pin)) {
            JOptionPane.showMessageDialog(this, "Invalid PIN!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Parse amount
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Load money
        boolean success = TransactionService.loadMoney(userId, amount);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                String.format("Successfully loaded Rs. %.2f to your account", amount), 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            new DashboardPage(userId).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load money!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
