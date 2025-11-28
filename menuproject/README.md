# Sujal Commercial Bank - Mini Banking System

A comprehensive GUI-based banking application built with Java Swing and MySQL.

## Features

✅ **User Registration** - Register with name, email, address, DOB, citizenship, NID, and password  
✅ **Secure Login** - Password hashing with SHA-256  
✅ **Transaction PIN** - 4-digit PIN for secure transactions  
✅ **Transfer Money** - Send money to other users by email  
✅ **Load Money** - Add funds to your account  
✅ **View Receipt** - See details of your last transaction  
✅ **View Statement** - Complete transaction history in table format  

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J (already in parent directory: `mysql-connector-j-8.2.0.jar`)

## Database Setup

1. **Start MySQL Server** (if not already running)

2. **Create the database and tables:**
   ```bash
   mysql -u root -p < schema.sql
   ```
   
   Or manually execute the SQL file in MySQL:
   ```bash
   mysql -u root -p
   source d:/java/Advanced-java/menuproject/schema.sql
   ```

3. **Verify database creation:**
   ```sql
   USE banking_db;
   SHOW TABLES;
   ```
   You should see: `users`, `accounts`, `transactions`

## Configuration

If your MySQL credentials are different from the defaults, edit `DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/banking_db";
private static final String USER = "root";
private static final String PASSWORD = ""; // Change if needed
```

## Compilation

Navigate to the project directory and compile all Java files:

```bash
cd d:\java\Advanced-java\menuproject
javac -cp ".;..\mysql-connector-j-8.2.0.jar" *.java
```

## Running the Application

```bash
java -cp ".;..\mysql-connector-j-8.2.0.jar" BankingApp
```

## Usage Guide

### 1. Registration
- Click "Register" on the home page
- Fill in all required fields:
  - Full Name
  - Email (must be unique)
  - Address
  - Date of Birth (format: YYYY-MM-DD)
  - Citizenship Number
  - National ID (must be unique)
  - Password (minimum 6 characters)
  - Retype Password
- Click "Register" to create your account

### 2. Login
- Click "Login" on the home page
- Enter your email and password
- First-time login will prompt you to set a 4-digit transaction PIN

### 3. Set Transaction PIN
- Enter a 4-digit PIN
- Confirm the PIN
- This PIN will be required for all transactions

### 4. Dashboard
After login, you'll see your dashboard with:
- Welcome message with your name
- Current account balance
- Menu options for all banking features

### 5. Transfer Money
- Enter recipient's email address
- Enter amount to transfer
- Enter your transaction PIN
- Confirm the transfer

### 6. Load Money
- Enter amount to add to your account
- Enter your transaction PIN
- Confirm to add funds

### 7. View Receipt
- Displays details of your last transaction:
  - Transaction ID
  - Type (LOAD, TRANSFER, RECEIVE)
  - Amount
  - Description
  - Balance after transaction
  - Date and time

### 8. View Statement
- Shows all your transactions in a table
- Columns: Date, Type, Amount, Balance, Description
- Sorted by most recent first

## File Structure

```
menuproject/
├── schema.sql                  # Database schema
├── DatabaseConnection.java     # Database connection utility
├── UserService.java           # User management service
├── AccountService.java        # Account operations service
├── TransactionService.java    # Transaction handling service
├── HomePage.java              # Home/welcome page
├── RegisterPage.java          # User registration page
├── LoginPage.java             # User login page
├── SetPinPage.java            # Transaction PIN setup page
├── DashboardPage.java         # Main dashboard
├── TransferMoneyPage.java     # Money transfer page
├── LoadMoneyPage.java         # Load money page
├── ReceiptPage.java           # Transaction receipt viewer
├── StatementPage.java         # Account statement viewer
├── BankingApp.java            # Main application entry point
└── README.md                  # This file
```

## Database Schema

### users table
- user_id (Primary Key)
- name, email, address, dob
- citizenship, nid
- password (hashed)
- created_at

### accounts table
- account_id (Primary Key)
- user_id (Foreign Key)
- balance
- transaction_pin (hashed)
- pin_set (boolean)
- created_at

### transactions table
- transaction_id (Primary Key)
- sender_id, receiver_id (Foreign Keys)
- transaction_type (LOAD, TRANSFER, RECEIVE)
- amount, balance_after
- description
- transaction_date

## Security Features

- **Password Hashing**: All passwords are hashed using SHA-256
- **Transaction PIN**: Separate 4-digit PIN for transaction security
- **Input Validation**: All user inputs are validated
- **SQL Injection Prevention**: Prepared statements used throughout

## Troubleshooting

### "MySQL JDBC Driver not found"
- Ensure `mysql-connector-j-8.2.0.jar` is in the parent directory
- Check the classpath in compile and run commands

### "Connection failed"
- Verify MySQL server is running
- Check database credentials in `DatabaseConnection.java`
- Ensure `banking_db` database exists

### "Registration failed"
- Email or NID may already exist in the database
- Check all required fields are filled
- Verify password is at least 6 characters

### "Invalid PIN"
- Ensure you're entering the correct 4-digit PIN
- PIN must be exactly 4 numeric digits

## Author

Created for Sujal Commercial Bank
© 2025 All rights reserved
