import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    
    // Transfer money between accounts
    public static boolean transferMoney(int senderId, int receiverId, double amount) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return false;
            
            conn.setAutoCommit(false);
            
            // Get sender balance
            double senderBalance = AccountService.getBalance(senderId);
            if (senderBalance < amount) {
                conn.rollback();
                return false; // Insufficient balance
            }
            
            // Get receiver balance
            double receiverBalance = AccountService.getBalance(receiverId);
            
            // Update sender balance
            double newSenderBalance = senderBalance - amount;
            if (!AccountService.updateBalance(senderId, newSenderBalance)) {
                conn.rollback();
                return false;
            }
            
            // Update receiver balance
            double newReceiverBalance = receiverBalance + amount;
            if (!AccountService.updateBalance(receiverId, newReceiverBalance)) {
                conn.rollback();
                return false;
            }
            
            // Record sender transaction
            String senderDesc = "Transfer to " + UserService.getUserName(receiverId);
            recordTransaction(senderId, receiverId, "TRANSFER", amount, newSenderBalance, senderDesc);
            
            // Record receiver transaction
            String receiverDesc = "Received from " + UserService.getUserName(senderId);
            recordTransaction(receiverId, senderId, "RECEIVE", amount, newReceiverBalance, receiverDesc);
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true);
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Load money to account
    public static boolean loadMoney(int userId, double amount) {
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return false;
            
            conn.setAutoCommit(false);
            
            // Get current balance
            double currentBalance = AccountService.getBalance(userId);
            
            // Update balance
            double newBalance = currentBalance + amount;
            if (!AccountService.updateBalance(userId, newBalance)) {
                conn.rollback();
                return false;
            }
            
            // Record transaction
            recordTransaction(userId, null, "LOAD", amount, newBalance, "Money loaded to account");
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Record transaction
    private static boolean recordTransaction(int userId, Integer otherUserId, String type, 
                                            double amount, double balanceAfter, String description) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return false;
            
            String sql = "INSERT INTO transactions (sender_id, receiver_id, transaction_type, amount, balance_after, description) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            
            if (type.equals("TRANSFER")) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, otherUserId);
            } else if (type.equals("RECEIVE")) {
                pstmt.setInt(1, otherUserId);
                pstmt.setInt(2, userId);
            } else { // LOAD
                pstmt.setInt(1, userId);
                pstmt.setNull(2, Types.INTEGER);
            }
            
            pstmt.setString(3, type);
            pstmt.setDouble(4, amount);
            pstmt.setDouble(5, balanceAfter);
            pstmt.setString(6, description);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Get last transaction for a user
    public static Transaction getLastTransaction(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return null;
            
            String sql = "SELECT * FROM transactions WHERE sender_id = ? OR receiver_id = ? " +
                        "ORDER BY transaction_date DESC LIMIT 1";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getInt("sender_id"),
                    rs.getInt("receiver_id"),
                    rs.getString("transaction_type"),
                    rs.getDouble("amount"),
                    rs.getDouble("balance_after"),
                    rs.getString("description"),
                    rs.getTimestamp("transaction_date")
                );
            }
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Get all transactions for a user
    public static List<Transaction> getAllTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return transactions;
            
            String sql = "SELECT * FROM transactions WHERE sender_id = ? OR receiver_id = ? " +
                        "ORDER BY transaction_date DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getInt("sender_id"),
                    rs.getInt("receiver_id"),
                    rs.getString("transaction_type"),
                    rs.getDouble("amount"),
                    rs.getDouble("balance_after"),
                    rs.getString("description"),
                    rs.getTimestamp("transaction_date")
                ));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return transactions;
    }
}

// Transaction class to hold transaction data
class Transaction {
    public int transactionId;
    public int senderId;
    public int receiverId;
    public String type;
    public double amount;
    public double balanceAfter;
    public String description;
    public Timestamp date;
    
    public Transaction(int transactionId, int senderId, int receiverId, String type, 
                      double amount, double balanceAfter, String description, Timestamp date) {
        this.transactionId = transactionId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.date = date;
    }
}
