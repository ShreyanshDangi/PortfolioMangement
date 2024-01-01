package swing;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class loginPage {

    private static JFrame frame;
    private JTextField textField_2;
    private JPasswordField textField_1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    loginPage window = new loginPage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public loginPage() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1450, 767);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Login Page");
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 50));
        lblNewLabel.setBounds(531, 44, 293, 78);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_2 = new JLabel("Demat Number : ");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel_2.setBounds(283, 202, 234, 50);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Password : ");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel_3.setBounds(360, 290, 157, 44);
        frame.getContentPane().add(lblNewLabel_3);

        textField_2 = new JTextField();
        textField_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField_2.setColumns(10);
        textField_2.setBounds(555, 219, 303, 33);
        frame.getContentPane().add(textField_2);

        textField_1 = new JPasswordField();
        textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField_1.setColumns(10);
        textField_1.setBounds(555, 300, 303, 33);
        frame.getContentPane().add(textField_1);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.setBounds(743, 384, 115, 44);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Register");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton_1.setBounds(555, 384, 127, 44);
        frame.getContentPane().add(btnNewButton_1);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String demat = textField_2.getText();
                String password = new String(textField_1.getPassword());

                if (validateCredentials(demat, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials. Please enter correct credentials or Register");
                }
            }
        });

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                RegisterPage.main(null);
            }
        });
    }

    private boolean validateCredentials(String demat, String password) {
        String url = "jdbc:mysql://localhost:3306/project_tp";
        String username = "root";
        String passwordDb = "shreyansh";

        try {
            String driverClassName;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                driverClassName = "com.mysql.cj.jdbc.Driver";
            } catch (ClassNotFoundException ex) {
                Class.forName("com.mysql.jdbc.Driver");
                driverClassName = "com.mysql.jdbc.Driver";
            }

            Connection connection = DriverManager.getConnection(url, username, passwordDb);

            String sql = "SELECT * FROM registration_details WHERE demat = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, demat);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            boolean credentialsMatch = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();

            return credentialsMatch;

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static JFrame getFrame() {
        return frame;
    }
}
