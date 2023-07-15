package student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class login extends JFrame  {
    JTextField tfUsername;
    JPasswordField pfPassword;
    JButton btnLogin;

    public login() {
        JFrame f = new JFrame("STUDENT LOGIN SYSTEM");
        f.setVisible(true);
        f.getContentPane();
        f.setLocationRelativeTo(null);
        f.setBounds(450, 200, 500, 450);
        f.setBackground(Color.pink);
        f.setLayout(null);

        JLabel label = new JLabel("STUDENT LOGIN SYSTEM");
        label.setFont(new Font("ARIAL", Font.BOLD, 25));
        label.setBounds(80, 30, 350, 50);
        label.setForeground(Color.orange);
        f.add(label);

        JLabel label1 = new JLabel("USERNAME");
        label1.setForeground(Color.red);
        label1.setFont(new Font("arail", Font.BOLD, 16));
        label1.setBounds(50, 120, 150, 20);
        f.add(label1);

        JLabel label2 = new JLabel("PASSWORD");
        label2.setForeground(Color.red);
        label2.setFont(new Font("arail", Font.BOLD, 16));
        label2.setBounds(50, 220, 150, 20);
        f.add(label2);

        JTextField uf = new JTextField();
        uf.setBounds(180, 120, 150, 30);
        uf.setFont(new Font("arial", Font.BOLD, 16));
        f.add(uf);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(180, 220, 150, 30);
        passwordField.setEchoChar('*');
        uf.setFont(new Font("arial", Font.BOLD, 20));
        f.add(passwordField);

        JButton b = new JButton("LOGIN");
        b.setBounds(200, 300, 90, 25);
        f.add(b);


        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = uf.getText();
                String password = new String(passwordField.getPassword());

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/student", "root", "");

                    PreparedStatement pst = con.prepareStatement(
                            "SELECT * FROM login WHERE username=? AND password=?");
                    pst.setString(1, username);
                    pst.setString(2, password);

                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        new main();
                        f.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }

                    con.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }

        public static void main (String[]args){
            new login();
        }
    }

