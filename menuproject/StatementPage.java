import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class StatementPage extends JFrame {
    private int userId;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    
    public StatementPage(int userId) {
        this.userId = userId;
        
        setTitle("Account Statement - Sujal Commercial Bank");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel titleLabel = new JLabel("Account Statement");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBounds(320, 20, 300, 40);
        mainPanel.add(titleLabel);
        
        // User info
        String userName = UserService.getUserName(userId);
        JLabel userLabel = new JLabel("Account Holder: " + userName);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.GRAY);
        userLabel.setBounds(50, 70, 400, 20);
        mainPanel.add(userLabel);
        
        // Current balance
        double balance = AccountService.getBalance(userId);
        JLabel balanceLabel = new JLabel(String.format("Current Balance: Rs. %.2f", balance));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        balanceLabel.setForeground(new Color(0, 153, 76));
        balanceLabel.setBounds(650, 70, 250, 20);
        mainPanel.add(balanceLabel);
        
        // Table
        String[] columnNames = {"Date", "Type", "Amount", "Balance", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        transactionTable = new JTable(tableModel);
        transactionTable.setFont(new Font("Arial", Font.PLAIN, 12));
        transactionTable.setRowHeight(30);
        transactionTable.setSelectionBackground(new Color(200, 230, 255));
        transactionTable.setGridColor(new Color(220, 220, 220));
        
        // Table header
        JTableHeader header = transactionTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        
        // Column widths
        transactionTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        transactionTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        transactionTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        transactionTable.getColumnModel().getColumn(4).setPreferredWidth(300);
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBounds(50, 110, 800, 380);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        mainPanel.add(scrollPane);
        
        // Load transactions
        loadTransactions();
        
        // Back button
        JButton backButton = createStyledButton("Back to Dashboard");
        backButton.setBounds(325, 510, 250, 45);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DashboardPage(userId).setVisible(true);
                dispose();
            }
        });
        mainPanel.add(backButton);
        
        add(mainPanel);
    }
    
    private void loadTransactions() {
        List<Transaction> transactions = TransactionService.getAllTransactions(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        tableModel.setRowCount(0); // Clear existing rows
        
        for (Transaction t : transactions) {
            String date = sdf.format(t.date);
            String type = t.type;
            String amount = String.format("Rs. %.2f", t.amount);
            String balance = String.format("Rs. %.2f", t.balanceAfter);
            String description = t.description;
            
            tableModel.addRow(new Object[]{date, type, amount, balance, description});
        }
        
        if (transactions.isEmpty()) {
            tableModel.addRow(new Object[]{"No transactions found", "", "", "", ""});
        }
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
