/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.library.app.jframe;

import com.library.app.db.DBConnection;
import com.library.app.services.BookService;
import com.library.app.validation.BookValidation;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Niraj Surve
 */
public class ReturnBook extends javax.swing.JFrame {

    private final BookService bookService = new BookService();
    private final BookValidation validate = new BookValidation();

    /**
     * Creates new form HomePage
     */
    public ReturnBook() {
        initComponents();
    }
    
    private boolean validateDetails(String bookId, String studentID) {
        boolean success = true;

        String validatedBookId = validate.validateId(bookId);
        String validatedStudentId = validate.validateId(studentID);

        if ("Required".equals(validatedBookId)) {
            JOptionPane.showMessageDialog(this, "Book ID is required..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        if ("Invalid".equals(validatedBookId)) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        if ("Required".equals(validatedStudentId)) {
            JOptionPane.showMessageDialog(this, "Student ID is required..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        if ("Invalid".equals(validatedStudentId)) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }

        return success;
    }

    // fetch the book details from database and displaying it
    public void getIssuedBookDetails() {
        int bookId = Integer.parseInt(txt_bookId.getText());
        int studentId = Integer.parseInt(txt_studentId.getText());

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "select * from issued_books where book_id = ? and student_id = ? and status = ?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, bookId);
            pst.setInt(2, studentId);
            pst.setString(3, "pending");

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lbl_bookId.setText(rs.getString("book_id"));
                lbl_bookName.setText(rs.getString("book_name"));
                lbl_studentId.setText(rs.getString("student_id"));
                lbl_studentName.setText(rs.getString("student_name"));
                lbl_issuedDate.setText(rs.getString("issue_date"));
                lbl_dueDate.setText(rs.getString("due_date"));
            } else {
                JOptionPane.showMessageDialog(this, "Book with id " + bookId + " is not issued to student with id " + studentId + ".");
                lbl_bookId.setText("");
                lbl_bookName.setText("");
                lbl_studentId.setText("");
                lbl_studentName.setText("");
                lbl_issuedDate.setText("");
                lbl_dueDate.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Internal Server Error..! Please try again later");
        }

    }

    // to issue the book and add into the database
    public boolean returnBook() {
        boolean success = false;

        int bookId = Integer.parseInt(txt_bookId.getText());
        int studentId = Integer.parseInt(txt_studentId.getText());
        Long todayDate = System.currentTimeMillis();
        Date returnDate = new Date(todayDate);

        boolean validated = validateDetails(txt_bookId.getText(), txt_studentId.getText());

        if (validated) {
            try {
                Connection conn = DBConnection.getConnection();
                String sql = "update issued_books set return_date = ?, status = ? where book_id = ? and student_id = ?";

                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setDate(1, returnDate);
                pst.setString(2, "returned");
                pst.setInt(3, bookId);
                pst.setInt(4, studentId);

                int rowCount = pst.executeUpdate();

                success = rowCount > 0;

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Internal Server Error..!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter valid details..!");
        }

        return success;
    }

    // update the book quantity
    public void updateBookQuantity() {
        int bookId = Integer.parseInt(txt_bookId.getText());

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "update books set quantity = quantity + 1 where book_id = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, bookId);

            int rowCount = pst.executeUpdate();

            if (rowCount == 0) {
                System.out.println("Book quantity not updated..");
            }

        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid details..!");
        }
    }

    // checking book already allocated or not
    public boolean isAlreadyIssued() {
        boolean isAlreadyIssued = false;

        int bookId = Integer.parseInt(txt_bookId.getText());
        int studentId = Integer.parseInt(txt_studentId.getText());

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "select * from issued_books where book_id = ? and student_id = ? and status = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, bookId);
            pst.setInt(2, studentId);
            pst.setString(3, "pending");

            ResultSet rs = pst.executeQuery();

            isAlreadyIssued = rs.next();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Internal Server Error..!");
        }

        return isAlreadyIssued;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        txt_bookId = new app.bolivia.swing.JCTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_studentId = new app.bolivia.swing.JCTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lbl_bookId = new javax.swing.JLabel();
        lbl_bookName = new javax.swing.JLabel();
        lbl_issuedDate = new javax.swing.JLabel();
        lbl_studentName = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbl_dueDate = new javax.swing.JLabel();
        lbl_studentId = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Library App - Dashboard");
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(1000, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(121, 173, 220));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/menu.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, 30, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 5, 1, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 51, 51));
        jLabel2.setText("Admin");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1043, 10, 50, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Library App");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/user.png"))); // NOI18N
        jLabel27.setText("Welcome,");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 5, 120, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 40));

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/exit.png"))); // NOI18N
        jLabel4.setText("   Logout");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 200, 40));

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Features");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 200, 40));

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel6MouseExited(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/library.png"))); // NOI18N
        jLabel7.setText("   Dashboard");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 200, 40));

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel8MouseExited(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/book.png"))); // NOI18N
        jLabel8.setText("   Manage Books");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 200, 40));

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel9MouseExited(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/read-online.png"))); // NOI18N
        jLabel9.setText("   Manage Students");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 200, 40));

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));
        jPanel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10MouseExited(evt);
            }
        });
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/sell.png"))); // NOI18N
        jLabel10.setText("   Issue Book");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 200, 40));

        jPanel11.setBackground(new java.awt.Color(121, 173, 220));
        jPanel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/return-book.png"))); // NOI18N
        jLabel11.setText("   Return Book");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel11.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 200, 40));

        jPanel12.setBackground(new java.awt.Color(51, 51, 51));
        jPanel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel12MouseExited(evt);
            }
        });
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/details.png"))); // NOI18N
        jLabel12.setText("   View Records");
        jPanel12.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 200, 40));

        jPanel13.setBackground(new java.awt.Color(51, 51, 51));
        jPanel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel13MouseExited(evt);
            }
        });
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/issuedbooks.png"))); // NOI18N
        jLabel13.setText("   View Issued Books");
        jPanel13.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 200, 40));

        jPanel14.setBackground(new java.awt.Color(51, 51, 51));
        jPanel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel14MouseExited(evt);
            }
        });
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/conference.png"))); // NOI18N
        jLabel14.setText("   Defaulter List");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel3.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 200, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 200, 610));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 51, 51));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/managebooks/manage-books.png"))); // NOI18N
        jLabel30.setText("Return Book");
        jPanel15.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel16.setBackground(new java.awt.Color(255, 51, 51));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel15.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 52, 260, 2));

        jPanel18.setBackground(new java.awt.Color(121, 173, 220));
        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_bookId.setBackground(new java.awt.Color(121, 173, 220));
        txt_bookId.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txt_bookId.setForeground(new java.awt.Color(255, 255, 255));
        txt_bookId.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_bookId.setMargin(new java.awt.Insets(10, 10, 10, 10));
        txt_bookId.setPlaceholder("Book ID");
        txt_bookId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookIdActionPerformed(evt);
            }
        });
        jPanel18.add(txt_bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 210, 40));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/managebooks/book_id.png"))); // NOI18N
        jPanel18.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 105, 30, 30));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/student-id.png"))); // NOI18N
        jPanel18.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 225, 30, 30));

        txt_studentId.setBackground(new java.awt.Color(121, 173, 220));
        txt_studentId.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txt_studentId.setForeground(new java.awt.Color(255, 255, 255));
        txt_studentId.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_studentId.setMargin(new java.awt.Insets(10, 10, 10, 10));
        txt_studentId.setPlaceholder("Student ID");
        jPanel18.add(txt_studentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 210, 40));

        jPanel20.setBackground(new java.awt.Color(251, 51, 51));
        jPanel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel20MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel20MouseExited(evt);
            }
        });
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setBackground(new Color(0, 0, 0, 0));
        jLabel24.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 17)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/return-book.png"))); // NOI18N
        jLabel24.setText("Return Book");
        jPanel20.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 20));

        jPanel18.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 140, 40));

        jPanel21.setBackground(new java.awt.Color(251, 51, 51));
        jPanel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel21MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel21MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel21MouseExited(evt);
            }
        });
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setBackground(new Color(0, 0, 0, 0));
        jLabel29.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 17)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/search-icon.png"))); // NOI18N
        jLabel29.setText("Search");
        jPanel21.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 20));

        jPanel18.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 100, 40));

        jPanel15.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 340, 490));

        jPanel19.setBackground(new java.awt.Color(215, 235, 252));
        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Segoe UI Black", 1, 16)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 51));
        jLabel32.setText("Book Details");
        jPanel19.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Due Date");
        jPanel19.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 100, -1));

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Book ID");
        jPanel19.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 100, 20));

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Book Name");
        jPanel19.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 100, -1));

        jLabel28.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Student ID");
        jPanel19.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 100, -1));

        lbl_bookId.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lbl_bookId.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel19.add(lbl_bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 200, 25));

        lbl_bookName.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lbl_bookName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel19.add(lbl_bookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 200, 25));

        lbl_issuedDate.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lbl_issuedDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel19.add(lbl_issuedDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 200, 25));

        lbl_studentName.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lbl_studentName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel19.add(lbl_studentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 200, 25));

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Student Name");
        jPanel19.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 100, -1));

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Issued Date");
        jPanel19.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 100, -1));

        lbl_dueDate.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lbl_dueDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel19.add(lbl_dueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 200, 25));

        lbl_studentId.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lbl_studentId.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel19.add(lbl_studentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 200, 25));

        jPanel15.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 380, 490));

        getContentPane().add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 900, 610));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseEntered
        jPanel9.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel9MouseEntered

    private void jPanel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseEntered
        jPanel12.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel12MouseEntered

    private void jPanel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseEntered
        jPanel13.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel13MouseEntered

    private void jPanel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseEntered
        jPanel14.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel14MouseEntered

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        jPanel4.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseExited
        jPanel9.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel9MouseExited

    private void jPanel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseExited
        jPanel12.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel12MouseExited

    private void jPanel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseExited
        jPanel13.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel13MouseExited

    private void jPanel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseExited
        jPanel14.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel14MouseExited

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        jPanel4.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel4MouseExited

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        dispose();
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
        jPanel6.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseExited
        jPanel6.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel6MouseExited

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        dispose();
        HomePage homePage = new HomePage();
        homePage.setVisible(true);
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        dispose();
        ManageStudents manageStudents = new ManageStudents();
        manageStudents.setVisible(true);
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        dispose();
        ManageBooks manageBooks = new ManageBooks();
        manageBooks.setVisible(true);
    }//GEN-LAST:event_jPanel8MouseClicked

    private void txt_bookIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookIdActionPerformed

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
        if (isAlreadyIssued()) {
            if (returnBook()) {
                JOptionPane.showMessageDialog(this, "Book returned successfully..!");
                updateBookQuantity();
                lbl_bookId.setText("");
                lbl_bookName.setText("");
                lbl_studentId.setText("");
                lbl_studentName.setText("");
                lbl_issuedDate.setText("");
                lbl_dueDate.setText("");
                txt_bookId.setText("");
                txt_studentId.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Book is not returned..! Please try again later.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Book is not issued to this student..!");
        }

    }//GEN-LAST:event_jPanel20MouseClicked

    private void jPanel20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseEntered
        jPanel20.setBackground(new Color(121, 173, 220));
        jPanel20.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
    }//GEN-LAST:event_jPanel20MouseEntered

    private void jPanel20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseExited
        jPanel20.setBackground(new Color(251, 51, 51));
        jPanel20.setBorder(null);
    }//GEN-LAST:event_jPanel20MouseExited

    private void jPanel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseEntered
        jPanel8.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel8MouseEntered

    private void jPanel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseExited
        jPanel8.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel8MouseExited

    private void jPanel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseEntered
        jPanel10.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel10MouseEntered

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        jPanel10.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel10MouseExited

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        dispose();
        IssueBook issueBook = new IssueBook();
        issueBook.setVisible(true);
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseClicked
        if ("Invalid".equals(validate.validateId(txt_studentId.getText())) || "Invalid".equals(validate.validateId(txt_bookId.getText()))) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID..!");
            txt_studentId.setText("");
            txt_bookId.setText("");
        } else {
            if (!"".equals(txt_studentId.getText())) {
                getIssuedBookDetails();
            } else {
                lbl_bookId.setText("");
                lbl_bookName.setText("");
                lbl_studentId.setText("");
                lbl_studentName.setText("");
                lbl_issuedDate.setText("");
                lbl_dueDate.setText("");
            }
        }
    }//GEN-LAST:event_jPanel21MouseClicked

    private void jPanel21MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseEntered
        jPanel21.setBackground(new Color(121, 173, 220));
        jPanel21.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
    }//GEN-LAST:event_jPanel21MouseEntered

    private void jPanel21MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseExited
        jPanel21.setBackground(new Color(251, 51, 51));
        jPanel21.setBorder(null);
    }//GEN-LAST:event_jPanel21MouseExited

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        dispose();
        ViewRecords viewRecords = new ViewRecords();
        viewRecords.setVisible(true);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        dispose();
        IssuedBookRecords issuedRecords = new IssuedBookRecords();
        issuedRecords.setVisible(true);
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        dispose();
        DefaulterList defaulterList = new DefaulterList();
        defaulterList.setVisible(true);
    }//GEN-LAST:event_jPanel14MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbl_bookId;
    private javax.swing.JLabel lbl_bookName;
    private javax.swing.JLabel lbl_dueDate;
    private javax.swing.JLabel lbl_issuedDate;
    private javax.swing.JLabel lbl_studentId;
    private javax.swing.JLabel lbl_studentName;
    private app.bolivia.swing.JCTextField txt_bookId;
    private app.bolivia.swing.JCTextField txt_studentId;
    // End of variables declaration//GEN-END:variables
}
