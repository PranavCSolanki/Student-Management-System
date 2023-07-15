package student;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class main {
    JTable table;

    main() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "");

        JFrame frame = new JFrame("STUDENT MANAGEMENT SYSTEM");
        frame.setVisible(true);
        frame.setSize(1370, 730);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultLookAndFeelDecorated(true);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(340, 20, 1000, 500);
        frame.add(scrollPane);

        Object[] column = {"SR. NO.", "NAME", "ADDRESS", "STANDERD", "DOB"};

        table = new JTable();
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLUE);
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(column);
        table.setModel(model);
        scrollPane.setViewportView(table);

        JLabel labelq = new JLabel("NAME ");
        labelq.setBounds(10, 30, 50, 20);
        frame.add(labelq);

        JTextField namef = new JTextField();
        namef.setBounds(110, 30, 200, 25);
        frame.add(namef);

        JLabel label1 = new JLabel("DATE OF BIRTH ");
        label1.setBounds(10, 70, 90, 20);
        frame.add(label1);

        JCalendar calendar = new JCalendar();
        calendar.setBounds(90, 55, 240, 145);
        calendar.setFont(new Font("arial", Font.BOLD, 12));
        frame.add(calendar);

        JLabel label2 = new JLabel("STANDERD ");
        label2.setBounds(10, 200, 70, 20);
        frame.add(label2);

        SpinnerModel model1 = new SpinnerNumberModel(1, 1, 12, 1);
        JSpinner stand = new JSpinner(model1);
        stand.setBounds(100, 200, 50, 25);
        frame.add(stand);


        JLabel label3 = new JLabel("ADDRESS");
        label3.setBounds(10, 250, 60, 20);
        frame.add(label3);

        JTextArea address = new JTextArea();
        address.setWrapStyleWord(true);
        address.setLineWrap(true);
        address.setBounds(100, 250, 200, 150);
        frame.add(address);

        JLabel label8 = new JLabel("SR NO");
        label8.setBounds(10, 430, 60, 20);
        frame.add(label8);

        JTextField srf = new JTextField();
        srf.setBounds(100, 430, 35, 25);
        frame.add(srf);

        final Object[] row = new Object[5];

        JButton add = new JButton("ADD");
        add.setBounds(40, 500, 80, 20);
        frame.add(add);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (srf.getText().equals("") || namef.getText().equals("") || address.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "PLEASE FILL THE INFORMATION");
                } else {
                    String sr = srf.getText();
                    String name = namef.getText();
                    String addr = address.getText();
                    int std = (int) model1.getValue();
                    String dat = String.valueOf(calendar.getDate());

                    try {
//                        PreparedStatement pst = con.prepareStatement("INSERT INTO `data` ( `name`, `address`, `standerd`, `dob`) VALUES (?,?,?,?)");
                        PreparedStatement pst = con.prepareStatement("INSERT INTO `data` (`sr_no`, `name`, `address`, `standerd`, `dob`) VALUES (?,?,?,?,?)");
                        pst.setString(1, sr);
                        pst.setString(2, name);
                        pst.setString(3, addr);
                        pst.setInt(4, std);
                        pst.setString(5, dat);

                        int rowsAffected = pst.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Data inserted successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error inserting data");
                        }


                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    row[0] = srf.getText();
                    row[1] = namef.getText();
                    row[2] = address.getText();
                    row[3] = model1.getValue();
                    row[4] = calendar.getDate();
                    model.addRow(row);

                }
                namef.setText("");
                address.setText("");
                srf.setText("");
            }
        });

        JButton del = new JButton("DELETE");
        del.setBounds(150, 500, 80, 20);
        frame.add(del);

        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (srf.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "PLESE ENTER THE VALID SR NO");
                } else {

                    PreparedStatement pst = null;
                    try {
//                        ResultSet resultSet = con.getMetaData().getCatalogs();


//                       if (srno(srf.getText())==srf.getText()){
//                           System.out.println("data is present in table ");
//                       }else {
//                           System.out.println("data is not present in the table");
//                       }


                        String deletesql = "delete from data where sr_no=?";
                        pst = con.prepareStatement(deletesql);
                        pst.setString(1, srf.getText());
                        frame.validate();
//                        model.removeRow();
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(null, "DATA DELETED SUCCESSFULLY");


////                        String i = srf.getText();
//                        int i = table.getSelectedRow();
////                        model.removeRow(Integer.parseInt(srf.getText()));
//                     String abc =   model.getColumnName(0);
//                        System.out.println(abc);`

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

//                    try {
//                        pst.setString(1,srf.getText());
//
//                        String i = srf.getText();
////                    int i = table.getSelectedRow();
////                        model.removeRow(model.get);
//                    } catch (SQLException ex) {
//                        throw new RuntimeException(ex);
//                    }
//
//                    int rowsAffected = 0;
//                    try {
//                        rowsAffected = pst.executeUpdate();
//                    } catch (SQLException ex) {
//                        throw new RuntimeException(ex);
//                    }

//                    if (rowsAffected > 0) {
//                        String i = srf.getText();
////                    int i = table.getSelectedRow();
//                        model.removeRow(Integer.parseInt(i));
//                        JOptionPane.showMessageDialog(null,"DATA DELETED SUCCESFULL");
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Error deleting data");
//                    }
                }
            }
        });

        JButton clr = new JButton("CLEAR");
        clr.setBounds(40, 550, 80, 20);
        frame.add(clr);
        clr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                namef.setText("");
                address.setText("");
                srf.setText("");
                JOptionPane.showMessageDialog(null, "CLEARD");
            }
        });

        JButton update = new JButton("UPDATE");
        update.setBounds(40, 600, 80, 20);
        frame.add(update);

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (srf.getText().equals("") || namef.getText().equals("") || address.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "PLESE ENTER THE DATA");
                } else {
                    int sr = Integer.parseInt( srf.getText());
                    String name = namef.getText();
                    String addr = address.getText();
                    int std = (int) model1.getValue();
                    String dat = String.valueOf(calendar.getDate());

                    try {
                        PreparedStatement pst = con.prepareStatement(
//                                "update `data` (`name`, `address`, `standerd`, `dob`) SET VALUES (?,?,?,?) where `sr_no`= ?");
                                "update `data` set `name`= ?, `address`= ?, `standerd`= ?, `dob`=?  where `sr_no`= ?");
//                        pst.setString(1, sr);
                        pst.setString(1, name);
                        pst.setString(2, addr);
                        pst.setInt(3, std);
                        pst.setString(4, dat);
                        pst.setInt(5, sr);

                        int rowsAffected = pst.executeUpdate();
                        int i = table.getSelectedRow();

                        if (rowsAffected > 0) {
//                            model.setValueAt(srf.getText(), i, 0);
                            model.setValueAt(namef.getText(), i, 1);
                            model.setValueAt(address.getText(), i, 2);
                            model.setValueAt(model1.getValue(), i, 3);
                            JOptionPane.showMessageDialog(null, "Data updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error to updateing data");
                        }

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }


                    int i = table.getSelectedRow();
                    if (i >= 0) {
                        model.setValueAt(srf.getText(), i, 0);
                        model.setValueAt(namef.getText(), i, 1);
                        model.setValueAt(address.getText(), i, 2);
                        model.setValueAt(model1.getValue(), i, 3);
                        JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESFULLY");
                    }
                }
            }
        });

        JButton ext = new JButton("EXIT");
        ext.setBounds(150, 550, 80, 20);
        frame.add(ext);
        ext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        try {

            // Execute SQL query
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM data");

            // Create a table model
            DefaultTableModel model2 = new DefaultTableModel();
            table = new JTable(model2);

            // Add columns to the table model
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model2.addColumn(rsmd.getColumnName(i));
            }

            // Add rows to the table model
            while (rs.next()) {
                Object[] row2 = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row2[i - 1] = rs.getObject(i);
                }
                model.addRow(row2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel label4 = new JLabel("SEARCH RECORD");
        label4.setBounds(300, 560, 100, 20);
        frame.add(label4);

        JLabel label7 = new JLabel("SR NO");
        label7.setBounds(410, 530, 60, 20);
        frame.add(label7);

        JTextField sr = new JTextField();
        sr.setBounds(410, 560, 35, 25);
        frame.add(sr);

        sr.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Searc(sr.getText());
            }
        });

        JLabel label6 = new JLabel("NAME");
        label6.setBounds(460, 530, 60, 20);
        frame.add(label6);

        JTextField nam = new JTextField();
        nam.setBounds(450, 560, 200, 25);
        frame.add(nam);

        nam.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Searc(nam.getText());
            }
        });

        JLabel label5 = new JLabel("ADDRESS");
        label5.setBounds(690, 530, 60, 20);
        frame.add(label5);

        JTextField addre = new JTextField();
        addre.setBounds(680, 560, 200, 25);
        frame.add(addre);

        addre.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Searc(addre.getText());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        JButton search = new JButton("SEARCH");
        search.setBounds(600, 600, 90, 20);
        frame.add(search);

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Searc(sr.getText());
                Searc(nam.getText());
                Searc(addre.getText());

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter(model);
                table.setRowSorter(tableRowSorter);
                tableRowSorter.setRowFilter(RowFilter.regexFilter(sr.getText()));

            }
        });


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                sr.setText((String) model.getValueAt(i, 0));
                namef.setText((String) model.getValueAt(i, 1));
                address.setText((String) model.getValueAt(i, 2));
                stand.setModel((SpinnerModel) model.getValueAt(i, 3));
            }
        });


        frame.validate();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new main();
    }

    public int Searc(String string) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter(model);
        table.setRowSorter(tableRowSorter);
        tableRowSorter.setRowFilter(RowFilter.regexFilter(string));

        return 0;
    }

//    public int srno(String no) throws ClassNotFoundException, SQLException {
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//
//        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "");
//
//        ResultSet resultSet = con.getMetaData().getCatalogs();
//
//        while (resultSet.next()) {
//            // Get the database name
//            String databaseName = resultSet.getString(1);
//        }
//
//        return 0;
//    }

}