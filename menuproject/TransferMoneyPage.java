import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TransferMoneyPage extends JFrame {
    private int userId;
    private JTextField recipientField, amountField;
    private JPasswordField pinField;
    
    public TransferMoneyPage(int userId) {
        this.userId = userId;
        
        setTitle("Transfer Money - Sujal Commercial Bank");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel titleLabel = new JLabel("Transfer Money");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBounds(180, 30, 250, 40);
        mainPanel.add(titleLabel);
        
        // Current balance
        double balance = AccountService.getBalance(userId);
        JLabel balanceLabel = new JLabel(String.format("Available Balance: Rs. %.2f", balance));
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        balanceLabel.setForeground(new Color(0, 153, 76));
        balanceLabel.setBounds(180, 75, 300, 25);
        mainPanel.add(balanceLabel);
        
        // Recipient email label
        JLabel recipientLabel = new JLabel("Recipient Email:");
        recipientLabel.setFont(new Font("Arial", Font.BOLD, 14));
        recipientLabel.setForeground(new Color(0, 51, 102));
        recipientLabel.setBounds(80, 130, 150, 30);
        mainPanel.add(recipientLabel);
        
        // Recipient field
        recipientField = new JTextField();
        recipientField.setFont(new Font("Arial", Font.PLAIN, 14));
        recipientField.setBounds(80, 160, 440, 40);
        recipientField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(recipientField);
        
        // Amount label
        JLabel amountLabel = new JLabel("Amount (Rs.):");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountLabel.setForeground(new Color(0, 51, 102));
        amountLabel.setBounds(80, 210, 150, 30);
        mainPanel.add(amountLabel);
        
        // Amount field
        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountField.setBounds(80, 240, 440, 40);
        amountField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(amountField);
        
        // PIN label
        JLabel pinLabel = new JLabel("Transaction PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pinLabel.setForeground(new Color(0, 51, 102));
        pinLabel.setBounds(80, 290, 150, 30);
        mainPanel.add(pinLabel);
        
        // PIN field
        pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setBounds(80, 320, 440, 40);
        pinField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(pinField);
        
        // Transfer button
        JButton transferButton = createStyledButton("Transfer");
        transferButton.setBounds(80, 385, 210, 45);
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleTransfer();
            }
        });
        mainPanel.add(transferButton);
        
        // Back button
        JButton backButton = createStyledButton("Back");
        backButton.setBackground(new Color(128, 128, 128));
        backButton.setBounds(310, 385, 210, 45);
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
    
    private void handleTransfer() {
        String recipientEmail = recipientField.getText().trim();
        String amountStr = amountField.getText().trim();
        String pin = new String(pinField.getPassword());
        
        if (recipientEmail.isEmpty() || amountStr.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verify PIN
        if (!AccountService.verifyPin(userId, pin)) {
            JOptionPane.showMessageDialog(this, "Invalid PIN!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get recipient ID
        int recipientId = UserService.getUserIdByEmail(recipientEmail);
        if (recipientId == -1) {
            JOptionPane.showMessageDialog(this, "Recipient not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (recipientId == userId) {
            JOptionPane.showMessageDialog(this, "Cannot transfer to yourself!", "Error", JOptionPane.ERROR_MESSAGE);
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
        
        // Check balance
        double balance = AccountService.getBalance(userId);
        if (balance < amount) {
            JOptionPane.showMessageDialog(this, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Transfer money
        boolean success = TransactionService.transferMoney(userId, recipientId, amount);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                String.format("Successfully transferred Rs. %.2f to %s", amount, recipientEmail), 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            new DashboardPage(userId).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Transfer failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
