import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    
    public HomePage() {
        setTitle("Sujal Commercial Bank");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(0, 102, 204);
                Color color2 = new Color(0, 51, 102);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);
        
        // Bank name label
        JLabel bankNameLabel = new JLabel("Sujal Commercial Bank", SwingConstants.CENTER);
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        bankNameLabel.setForeground(Color.WHITE);
        bankNameLabel.setBounds(100, 100, 600, 50);
        mainPanel.add(bankNameLabel);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Your Trusted Banking Partner", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(200, 220, 255));
        subtitleLabel.setBounds(100, 160, 600, 30);
        mainPanel.add(subtitleLabel);
        
        // Login button
        JButton loginButton = createStyledButton("Login");
        loginButton.setBounds(250, 300, 300, 50);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
        mainPanel.add(loginButton);
        
        // Register button
        JButton registerButton = createStyledButton("Register");
        registerButton.setBounds(250, 370, 300, 50);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterPage().setVisible(true);
                dispose();
            }
        });
        mainPanel.add(registerButton);
        
        // Footer
        JLabel footerLabel = new JLabel("© 2025 Sujal Commercial Bank. All rights reserved.", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(180, 200, 230));
        footerLabel.setBounds(100, 520, 600, 20);
        mainPanel.add(footerLabel);
        
        add(mainPanel);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
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
