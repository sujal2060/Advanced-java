import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardPage extends JFrame {
    private int userId;
    private JLabel balanceLabel;
    
    public DashboardPage(int userId) {
        this.userId = userId;
        
        setTitle("Dashboard - Sujal Commercial Bank");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 250, 255));
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBounds(0, 0, 800, 120);
        
        // Welcome message
        String userName = UserService.getUserName(userId);
        JLabel welcomeLabel = new JLabel("Welcome, " + userName + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 26));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(40, 20, 500, 35);
        headerPanel.add(welcomeLabel);
        
        // Balance label
        double balance = AccountService.getBalance(userId);
        balanceLabel = new JLabel(String.format("Current Balance: Rs. %.2f", balance));
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        balanceLabel.setForeground(new Color(200, 230, 255));
        balanceLabel.setBounds(40, 60, 500, 30);
        headerPanel.add(balanceLabel);
        
        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(204, 0, 0));
        logoutButton.setBounds(670, 40, 100, 40);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HomePage().setVisible(true);
                dispose();
            }
        });
        headerPanel.add(logoutButton);
        
        mainPanel.add(headerPanel);
        
        // Menu buttons
        int buttonWidth = 340;
        int buttonHeight = 80;
        int startX = 50;
        int startY = 160;
        int spacing = 30;
        
        // Transfer Money button
        JButton transferButton = createMenuButton("Transfer Money", "Send money to another account");
        transferButton.setBounds(startX, startY, buttonWidth, buttonHeight);
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TransferMoneyPage(userId).setVisible(true);
                dispose();
            }
        });
        mainPanel.add(transferButton);
        
        // Load Money button
        JButton loadButton = createMenuButton("Load Money", "Add money to your account");
        loadButton.setBounds(startX + buttonWidth + spacing, startY, buttonWidth, buttonHeight);
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoadMoneyPage(userId).setVisible(true);
                dispose();
            }
        });
        mainPanel.add(loadButton);
        
        // View Receipt button
        JButton receiptButton = createMenuButton("View Receipt", "See your last transaction");
        receiptButton.setBounds(startX, startY + buttonHeight + spacing, buttonWidth, buttonHeight);
        receiptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ReceiptPage(userId).setVisible(true);
                dispose();
            }
        });
        mainPanel.add(receiptButton);
        
        // View Statement button
        JButton statementButton = createMenuButton("View Statement", "See all your transactions");
        statementButton.setBounds(startX + buttonWidth + spacing, startY + buttonHeight + spacing, buttonWidth, buttonHeight);
        statementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StatementPage(userId).setVisible(true);
                dispose();
            }
        });
        mainPanel.add(statementButton);
        
        add(mainPanel);
    }
    
    private JButton createMenuButton(String title, String description) {
        JButton button = new JButton();
        button.setLayout(null);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBounds(20, 15, 300, 25);
        button.add(titleLabel);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        descLabel.setForeground(Color.GRAY);
        descLabel.setBounds(20, 45, 300, 20);
        button.add(descLabel);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(240, 248, 255));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        
        return button;
    }
}
