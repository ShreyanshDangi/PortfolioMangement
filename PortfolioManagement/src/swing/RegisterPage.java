package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterPage {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_2;
    private JPasswordField textField_1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegisterPage window = new RegisterPage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RegisterPage() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1446, 771);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Registration Page");
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 50));
        lblNewLabel.setBounds(477, 44, 477, 72);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Name : ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel_1.setBounds(434, 200, 128, 50);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Demat Number : ");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel_2.setBounds(318, 279, 244, 60);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Password : ");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel_3.setBounds(385, 362, 177, 48);
        frame.getContentPane().add(lblNewLabel_3);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField.setBounds(593, 215, 299, 29);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField_2.setColumns(10);
        textField_2.setBounds(593, 296, 299, 29);
        frame.getContentPane().add(textField_2);

        textField_1 = new JPasswordField();
        textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField_1.setColumns(10);
        textField_1.setBounds(593, 381, 299, 29);
        frame.getContentPane().add(textField_1);

        JButton btnNewButton = new JButton("Register");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.setBounds(772, 461, 120, 40);
        frame.getContentPane().add(btnNewButton);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                String demat = textField_2.getText();
                String password = new String(textField_1.getPassword());

                String url = "jdbc:mysql://localhost:3306/project_tp";
                String username = "root";
                String passwordDb = "shreyansh";

                try {
                	Class.forName("com.mysql.cj.jdbc.Driver");  
           
                    Connection connection = DriverManager.getConnection(url, username, passwordDb);

                    String sql = "INSERT INTO registration_details (name, demat, password) VALUES (?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, name);
                    statement.setString(2, demat);
                    statement.setString(3, password);

                    statement.executeUpdate();

                    statement.close();
                    connection.close();

                    JOptionPane.showMessageDialog(frame, "Registration successful!");
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error during registration: " + ex.getMessage());
                }
            }
        });

        JButton btnNewButton_1 = new JButton("Login");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton_1.setBounds(593, 461, 95, 40);
        frame.getContentPane().add(btnNewButton_1);

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                loginPage.main(null);
            }
        });
    }
}
