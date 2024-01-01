import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.SystemColor;


public class mainPage {

    private JFrame frame;
    private JTextField textField_1;
    private JTextField textField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    mainPage window = new mainPage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public mainPage() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1444, 774);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Total Balance :");
        lblNewLabel.setBounds(438, 220, 220, 39);
        lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 30));
        frame.getContentPane().add(lblNewLabel);

        JLabel lblCashBalance = new JLabel("Current Balance :");
        lblCashBalance.setBounds(405, 283, 253, 39);
        lblCashBalance.setFont(new Font("Dialog", Font.PLAIN, 30));
        frame.getContentPane().add(lblCashBalance);


        int initialBalance = 1000000;

        textField_1 = new JTextField(Integer.toString(initialBalance));
        textField_1.setBounds(675, 284, 187, 38);
        textField_1.setFont(new Font("Dialog", Font.PLAIN, 30));
        textField_1.setEditable(false);
        textField_1.setColumns(10);
        frame.getContentPane().add(textField_1);

        textField = new JTextField(Integer.toString(initialBalance));
        textField.setBounds(668, 220, 194, 40);
        textField.setFont(new Font("Dialog", Font.PLAIN, 30));
        textField.setEditable(false);
        textField.setColumns(10);
        frame.getContentPane().add(textField);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(207, 399, 916, 121);
        frame.getContentPane().add(tabbedPane);
        

        JPanel industrials = new IndustrialPanel();
        tabbedPane.addTab("Industrials", industrials);

        JPanel materials = new MaterialPanel();
        tabbedPane.addTab("Materials", materials);

        JPanel financialservices = new FinancialPanel();
        tabbedPane.addTab("Financial Services", financialservices);

        
        tabbedPane.addTab("Consumer Goods", new ConsumerGoodsPanel());
        tabbedPane.addTab("Energy", new EnergyPanel());
        tabbedPane.addTab("Health Care", new HealthCarePanel());
        tabbedPane.addTab("Telecommunications", new TelecommunicationsPanel());
        tabbedPane.addTab("Utilities", new UtilitiesPanel());
        tabbedPane.addTab("Information Technology", new InformationTechnologyPanel());
        
        JLabel lblNewLabel_1 = new JLabel("Portfolio Management System");
        lblNewLabel_1.setBounds(289, 10, 778, 86);
        lblNewLabel_1.setBackground(new Color(192, 192, 192));
        lblNewLabel_1.setFont(new Font("Book Antiqua", Font.BOLD, 55));
        frame.getContentPane().add(lblNewLabel_1);
    }


    private Connection establishConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/project_tp";
        String username = "root";
        String password = "shreyansh";

        return DriverManager.getConnection(url, username, password);
    }
    
    
    private void executeTransaction(String selectedStock, int quantity) {
        try (Connection connection = establishConnection()) {
            String query = "SELECT currentPrice FROM stockslists WHERE name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, selectedStock);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double currentPrice = resultSet.getDouble("currentPrice");

                        double totalCost = quantity * currentPrice;

                        double currentBalance = Double.parseDouble(textField_1.getText());
                        double newBalance = currentBalance - totalCost;
                        textField_1.setText(Double.toString(newBalance));

                        JOptionPane.showMessageDialog(frame,
                                "Transaction successful!\nDebited from Account: " + totalCost);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Stock not found in the database.");
                    }
                }
            }
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }


    class IndustrialPanel extends JPanel {
        public IndustrialPanel() {
            JComboBox < String > jcb = new JComboBox < String > ();
            jcb.addItem("Adani Ports and Special Economic Zone Limited");
            jcb.addItem("Larsen & Toubro Limited");

            jcb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedStock = (String) jcb.getSelectedItem();
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            executeTransaction(selectedStock, quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity format.");
                        }
                    }
                }
            });


            add(jcb);
        }
    }

    class MaterialPanel extends JPanel {
        public MaterialPanel() {
            JComboBox < String > jcb = new JComboBox < String > ();
            jcb.addItem("Asian Paints Limited");
            jcb.addItem("Grasim Industries Limited");
            jcb.addItem("Hindalco Industries Limited");
            jcb.addItem("JSW Steel Limited");
            jcb.addItem("Tata Steel Limited");
            jcb.addItem("UltraTech Cement Limited");
            jcb.addItem("UPL Limited");

            jcb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedStock = (String) jcb.getSelectedItem();
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            executeTransaction(selectedStock, quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity format.");
                        }
                    }
                }
            });

            add(jcb);
        }
    }


    class FinancialPanel extends JPanel {
        public FinancialPanel() {
            JComboBox < String > jcb = new JComboBox < String > ();
            jcb.addItem("Axis Bank Limited");
            jcb.addItem("Bajaj Finance Limited");
            jcb.addItem("Bajaj Finserv Limited");
            jcb.addItem("HDFC Bank Limited");
            jcb.addItem("HDFC Life Insurance Company Limited");
            jcb.addItem("ICICI Bank Limited");
            jcb.addItem("Kotak Mahindra Bank Limited");

            jcb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedStock = (String) jcb.getSelectedItem();
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            executeTransaction(selectedStock, quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity format.");
                        }
                    }
                }
            });

            add(jcb);
        }
    }


    class ConsumerGoodsPanel extends JPanel {
        public ConsumerGoodsPanel() {
            JComboBox < String > jcb = new JComboBox < String > ();
            jcb.addItem("Bajaj Auto Limited");
            jcb.addItem("Britannia Industries Limited");
            jcb.addItem("Hero MotoCorp Limited");
            jcb.addItem("Hindustan Unilever Limited");
            jcb.addItem("ITC Limited");
            jcb.addItem("Mahindra & Mahindra Limited");
            jcb.addItem("Maruti Suzuki India Limited");
            jcb.addItem("Nestle India Limited");
            jcb.addItem("Tata Consumer Products Limited");
            jcb.addItem("Tata Motors Limited");
            jcb.addItem("Titan Company Limited");

            jcb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedStock = (String) jcb.getSelectedItem();
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            executeTransaction(selectedStock, quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity format.");
                        }
                    }
                }
            });

            add(jcb);
        }
    }


    class EnergyPanel extends JPanel {
        public EnergyPanel() {
            JComboBox < String > jcb = new JComboBox < String > ();
            jcb.addItem("Bharat Petroleum Corporation Limited");
            jcb.addItem("Coal India Limited");
            jcb.addItem("Indian Oil Corporation Limited");
            jcb.addItem("Oil & Natural Gas Corporation Limited");
            jcb.addItem("Power Grid Corporation of India Limited");
            jcb.addItem("Reliance Industries Limited");

            jcb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedStock = (String) jcb.getSelectedItem();
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            executeTransaction(selectedStock, quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity format.");
                        }
                    }
                }
            });

            add(jcb);
        }
    }

    class HealthCarePanel extends JPanel {
        public HealthCarePanel() {
            JComboBox < String > jcb = new JComboBox < String > ();
            jcb.addItem("Cipla Limited");
            jcb.addItem("Divi?s Laboratories Limited");
            jcb.addItem("Dr. Reddy?s Laboratories Limited");
            jcb.addItem("Sun Pharmaceutical Industries Limited");

            jcb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedStock = (String) jcb.getSelectedItem();
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            executeTransaction(selectedStock, quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity format.");
                        }
                    }
                }
            });

            add(jcb);
        }
    }


    class TelecommunicationsPanel extends JPanel {
        public TelecommunicationsPanel() {
            JComboBox < String > jcb = new JComboBox < String > ();
            jcb.addItem("Bharti Airtel Limited");

            jcb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedStock = (String) jcb.getSelectedItem();
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            executeTransaction(selectedStock, quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity format.");
                        }
                    }
                }
            });

            add(jcb);
        }
    }

    class UtilitiesPanel extends JPanel {
        public UtilitiesPanel() {
            JComboBox < String > jcb = new JComboBox < String > ();
            jcb.addItem("NTPC Limited");
            jcb.addItem("Power Grid Corporation of India Limited");

            jcb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedStock = (String) jcb.getSelectedItem();
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            executeTransaction(selectedStock, quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity format.");
                        }
                    }
                }
            });

            add(jcb);
        }
    }

    class InformationTechnologyPanel extends JPanel {
        public InformationTechnologyPanel() {
            JComboBox < String > jcb = new JComboBox < String > ();
            jcb.addItem("HCL Technologies Limited");
            jcb.addItem("Infosys Limited");
            jcb.addItem("Tata Consultancy Services Limited");
            jcb.addItem("Tech Mahindra Limited");
            jcb.addItem("Wipro Limited");

            jcb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedStock = (String) jcb.getSelectedItem();
                    String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity:");

                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            executeTransaction(selectedStock, quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity format.");
                        }
                    }
                }
            });

            add(jcb);
        }
    }
}