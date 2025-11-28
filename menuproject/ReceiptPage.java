import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class ReceiptPage extends JFrame {
    private int userId;
    
    public ReceiptPage(int userId) {
        this.userId = userId;
        
        setTitle("Receipt - Sujal Commercial Bank");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel titleLabel = new JLabel("Transaction Receipt");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBounds(170, 30, 300, 40);
        mainPanel.add(titleLabel);
        
        // Get last transaction
        Transaction lastTransaction = TransactionService.getLastTransaction(userId);
        
        if (lastTransaction == null) {
            JLabel noTransLabel = new JLabel("No transactions found");
            noTransLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noTransLabel.setForeground(Color.GRAY);
            noTransLabel.setBounds(200, 250, 250, 30);
            mainPanel.add(noTransLabel);
        } else {
            // Receipt panel
            JPanel receiptPanel = new JPanel();
            receiptPanel.setLayout(null);
            receiptPanel.setBackground(Color.WHITE);
            receiptPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
            receiptPanel.setBounds(50, 90, 500, 400);
            
            // Bank name
            JLabel bankLabel = new JLabel("Sujal Commercial Bank", SwingConstants.CENTER);
            bankLabel.setFont(new Font("Arial", Font.BOLD, 20));
            bankLabel.setForeground(new Color(0, 102, 204));
            bankLabel.setBounds(50, 20, 400, 30);
            receiptPanel.add(bankLabel);
            
            // Separator
            JSeparator separator1 = new JSeparator();
            separator1.setBounds(50, 60, 400, 2);
            receiptPanel.add(separator1);
            
            int yPos = 80;
            int lineHeight = 35;
            
            // Transaction ID
            addReceiptLine(receiptPanel, "Transaction ID:", String.valueOf(lastTransaction.transactionId), yPos);
            yPos += lineHeight;
            
            // Type
            addReceiptLine(receiptPanel, "Type:", lastTransaction.type, yPos);
            yPos += lineHeight;
            
            // Amount
            addReceiptLine(receiptPanel, "Amount:", String.format("Rs. %.2f", lastTransaction.amount), yPos);
            yPos += lineHeight;
            
            // Description
            addReceiptLine(receiptPanel, "Description:", lastTransaction.description, yPos);
            yPos += lineHeight;
            
            // Balance After
            addReceiptLine(receiptPanel, "Balance After:", String.format("Rs. %.2f", lastTransaction.balanceAfter), yPos);
            yPos += lineHeight;
            
            // Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            addReceiptLine(receiptPanel, "Date & Time:", sdf.format(lastTransaction.date), yPos);
            yPos += lineHeight;
            
            // Separator
            JSeparator separator2 = new JSeparator();
            separator2.setBounds(50, yPos + 10, 400, 2);
            receiptPanel.add(separator2);
            
            // Footer
            JLabel footerLabel = new JLabel("Thank you for banking with us!", SwingConstants.CENTER);
            footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            footerLabel.setForeground(Color.GRAY);
            footerLabel.setBounds(50, 350, 400, 20);
            receiptPanel.add(footerLabel);
            
            mainPanel.add(receiptPanel);
        }
        
        // Back button
        JButton backButton = createStyledButton("Back to Dashboard");
        backButton.setBounds(175, 510, 250, 45);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DashboardPage(userId).setVisible(true);
                dispose();
            }
        });
        mainPanel.add(backButton);
        
        add(mainPanel);
    }
    
    private void addReceiptLine(JPanel panel, String label, String value, int y) {
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Arial", Font.BOLD, 13));
        labelComp.setForeground(new Color(0, 51, 102));
        labelComp.setBounds(50, y, 150, 25);
        panel.add(labelComp);
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Arial", Font.PLAIN, 13));
        valueComp.setForeground(Color.BLACK);
        valueComp.setBounds(210, y, 240, 25);
        panel.add(valueComp);
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
}
