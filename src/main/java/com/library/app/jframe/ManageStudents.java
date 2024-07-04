/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.library.app.jframe;

import com.library.app.db.DBConnection;
import com.library.app.services.StudentService;
import com.library.app.validation.StudentValidation;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Niraj Surve
 */
public class ManageStudents extends javax.swing.JFrame {

    private String studentName, className, studentId, branch;

    private final StudentService studentService = new StudentService();

    DefaultTableModel model;

    /**
     * Creates new form HomePage
     */
    public ManageStudents() {
        initComponents();
        setStudentsToTable();
    }

    // to set students details to table
    public final void setStudentsToTable() {
        try {
            Connection conn = DBConnection.getConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from students");

            while (rs.next()) {
                String tblStudentId = rs.getString("student_id");
                String tblStudentName = rs.getString("name");
                String tblClass = rs.getString("class");
                String tblBranch = rs.getString("branch");

                Object[] obj = {tblStudentId, tblStudentName, tblClass, tblBranch};
                model = (DefaultTableModel) tbl_studentDetails.getModel();
                model.addRow(obj);
            }
        } catch (SQLException e) {
        }
    }

    public void clearBooksTable() {
        DefaultTableModel tbl_model = (DefaultTableModel) tbl_studentDetails.getModel();
        tbl_model.setRowCount(0);
    }

    private boolean validateDetails(String studentId, String studentName, String className, String branchName) {
        boolean success = true;

        StudentValidation validate = new StudentValidation();
        String validatedId = validate.validateId(studentId);
        String validatedBookName = validate.validateName(studentName);

        if ("Required".equals(validatedId)) {
            JOptionPane.showMessageDialog(this, "Student ID is required..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        if ("Invalid".equals(validatedId)) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        if ("Required".equals(validatedBookName)) {
            JOptionPane.showMessageDialog(this, "Student Name is required..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        if ("Invalid".equals(validatedBookName)) {
            JOptionPane.showMessageDialog(this, "Invalid Student Name..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        if ("Select Class".equals(className)) {
            JOptionPane.showMessageDialog(this, "Please select class..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        if ("Select Branch".equals(branchName)) {
            JOptionPane.showMessageDialog(this, "Please select branch..!", "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }

        return success;
    }

    // function to add the student in the database
    public boolean addStudent() {
        boolean isAdded = false;

        studentId = txt_studentId.getText();
        studentName = txt_studentName.getText();
        className = txt_class.getSelectedItem().toString();
        branch = txt_branch.getSelectedItem().toString();

        boolean success = validateDetails(studentId, studentName, className, branch);

        if (studentService.getStudentByID(Integer.parseInt(studentId))) {
            success = false;
            JOptionPane.showMessageDialog(this, "Student with id " + studentId + " is already present..!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (studentService.getStudentByIDNameClassBranch(studentId, studentName, studentName, studentName)) {
            success = false;
            JOptionPane.showMessageDialog(this, "Student with this name, class and branch is already present..!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (success) {
            try {
                Connection conn = DBConnection.getConnection();
                String sql = "insert into students values(?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(sql);

                pst.setInt(1, Integer.parseInt(studentId));
                pst.setString(2, studentName);
                pst.setString(3, className);
                pst.setString(4, branch);

                int rowCount = pst.executeUpdate();

                isAdded = rowCount > 0;

            } catch (SQLException e) {
            }
        }

        return isAdded;
    }

    // function to update the student in the database
    public boolean updateStudent() {
        boolean isUpdated = false;

        studentId = txt_studentId.getText();
        studentName = txt_studentName.getText();
        className = txt_class.getSelectedItem().toString();
        branch = txt_branch.getSelectedItem().toString();

        boolean success = validateDetails(studentId, studentName, className, branch);

        if (!studentService.getStudentByID(Integer.parseInt(studentId))) {
            success = false;
            JOptionPane.showMessageDialog(this, "Student with id " + studentId + " not found..!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (success) {
            try {
                Connection conn = DBConnection.getConnection();
                String sql = "update students set name = ?, class = ?, branch = ? where student_id = ?";
                PreparedStatement pst = conn.prepareStatement(sql);

                pst.setString(1, studentName);
                pst.setString(2, className);
                pst.setString(3, branch);
                pst.setInt(4, Integer.parseInt(studentId));

                int rowCount = pst.executeUpdate();

                isUpdated = rowCount > 0;

            } catch (SQLException e) {
            }
        }

        return isUpdated;
    }

    // function to update the student in the database
    public boolean deleteStudent() {
        boolean isDeleted = false;

        studentId = txt_studentId.getText();
        studentName = txt_studentName.getText();
        className = txt_class.getSelectedItem().toString();
        branch = txt_branch.getSelectedItem().toString();

        boolean success = validateDetails(studentId, studentName, className, branch);

        if (success) {
            try {
                Connection conn = DBConnection.getConnection();
                
                System.out.println(studentService.getStudentByIDNameClassBranch(studentId, studentName, className, branch));

                // Check if the student with the specified ID, name, class and branch exists
                if (studentService.getStudentByIDNameClassBranch(studentId, studentName, className, branch)) {
                    String sql = "DELETE FROM students WHERE student_id = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(studentId));

                    int rowCount = pst.executeUpdate();

                    isDeleted = rowCount > 0;
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong student details..! Cannot delete student record.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception appropriately
                JOptionPane.showMessageDialog(this, "Failed to delete student. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return isDeleted;
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
        jPanel17 = new javax.swing.JPanel();
        txt_studentId = new app.bolivia.swing.JCTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_studentName = new app.bolivia.swing.JCTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txt_branch = new javax.swing.JComboBox<>();
        txt_class = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_studentDetails = new rojeru_san.complementos.RSTableMetro();

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

        jPanel9.setBackground(new java.awt.Color(121, 173, 220));
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jPanel11.setBackground(new java.awt.Color(51, 51, 51));
        jPanel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel11MouseExited(evt);
            }
        });
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
        jLabel30.setText("Manage Students");
        jPanel15.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel16.setBackground(new java.awt.Color(255, 51, 51));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel15.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 52, 260, 2));

        jPanel17.setBackground(new java.awt.Color(121, 173, 220));
        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_studentId.setBackground(new java.awt.Color(121, 173, 220));
        txt_studentId.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txt_studentId.setForeground(new java.awt.Color(255, 255, 255));
        txt_studentId.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_studentId.setMargin(new java.awt.Insets(10, 10, 10, 10));
        txt_studentId.setPlaceholder("Student ID");
        txt_studentId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_studentIdActionPerformed(evt);
            }
        });
        jPanel17.add(txt_studentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 65, 270, 40));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/managebooks/book_id.png"))); // NOI18N
        jPanel17.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 30, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/signup/user.png"))); // NOI18N
        jPanel17.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 145, 30, 30));

        txt_studentName.setBackground(new java.awt.Color(121, 173, 220));
        txt_studentName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txt_studentName.setForeground(new java.awt.Color(255, 255, 255));
        txt_studentName.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_studentName.setMargin(new java.awt.Insets(10, 10, 10, 10));
        txt_studentName.setPlaceholder("Student Name");
        txt_studentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_studentNameActionPerformed(evt);
            }
        });
        jPanel17.add(txt_studentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 270, 40));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/class-icon.png"))); // NOI18N
        jPanel17.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 225, 30, 30));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin/branch-icon.png"))); // NOI18N
        jPanel17.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 305, 30, 30));

        jPanel18.setBackground(new java.awt.Color(255, 51, 51));
        jPanel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel18MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel18MouseExited(evt);
            }
        });
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setBackground(new Color(0, 0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 17)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/managebooks/delete-icon.png"))); // NOI18N
        jLabel19.setText("Delete");
        jPanel18.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 80, -1));

        jPanel17.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 100, 40));

        jPanel19.setBackground(new java.awt.Color(255, 51, 51));
        jPanel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel19MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel19MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel19MouseExited(evt);
            }
        });
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setBackground(new Color(0, 0, 0, 0));
        jLabel20.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 17)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/managebooks/plus-icon.png"))); // NOI18N
        jLabel20.setText("Add");
        jPanel19.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 20));

        jPanel17.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 80, 40));

        jPanel20.setBackground(new java.awt.Color(255, 51, 51));
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

        jLabel21.setBackground(new Color(0, 0, 0, 0));
        jLabel21.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 17)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/managebooks/edit-icon.png"))); // NOI18N
        jLabel21.setText("Update");
        jPanel20.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 90, -1));

        jPanel17.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 110, 40));

        txt_branch.setBackground(new java.awt.Color(121, 173, 220));
        txt_branch.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_branch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Branch", "COMP", "MECH", "EXTC", "CHEM", "ELEC", "INST" }));
        jPanel17.add(txt_branch, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 270, 40));

        txt_class.setBackground(new java.awt.Color(121, 173, 220));
        txt_class.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_class.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Class", "FE", "SE", "TE", "BE" }));
        jPanel17.add(txt_class, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 270, 40));

        jPanel15.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 360, 490));

        tbl_studentDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Name", "Class", "Branch"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_studentDetails.setColorBackgoundHead(new java.awt.Color(215, 235, 252));
        tbl_studentDetails.setColorBordeFilas(new java.awt.Color(215, 235, 252));
        tbl_studentDetails.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_studentDetails.setColorFilasForeground1(new java.awt.Color(121, 173, 220));
        tbl_studentDetails.setColorFilasForeground2(new java.awt.Color(121, 173, 220));
        tbl_studentDetails.setColorForegroundHead(new java.awt.Color(51, 51, 51));
        tbl_studentDetails.setColorSelBackgound(new java.awt.Color(187, 192, 196));
        tbl_studentDetails.setColorSelForeground(new java.awt.Color(51, 51, 51));
        tbl_studentDetails.setFuenteFilas(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        tbl_studentDetails.setFuenteFilasSelect(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        tbl_studentDetails.setFuenteHead(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tbl_studentDetails.setGridColor(new java.awt.Color(255, 255, 255));
        tbl_studentDetails.setGrosorBordeFilas(0);
        tbl_studentDetails.setGrosorBordeHead(0);
        tbl_studentDetails.setRowHeight(30);
        tbl_studentDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_studentDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_studentDetails);

        jPanel15.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, -1, 490));

        getContentPane().add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 900, 610));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseEntered
        jPanel10.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel10MouseEntered

    private void jPanel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseEntered
        jPanel11.setBackground(new Color(121, 173, 220));
    }//GEN-LAST:event_jPanel11MouseEntered

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

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        jPanel10.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel10MouseExited

    private void jPanel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseExited
        jPanel11.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_jPanel11MouseExited

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

    private void txt_studentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_studentIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentIdActionPerformed

    private void txt_studentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_studentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentNameActionPerformed

    private void tbl_studentDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_studentDetailsMouseClicked
        int rowNo = tbl_studentDetails.getSelectedRow();
        TableModel tblModel = tbl_studentDetails.getModel();

        txt_studentId.setText(tblModel.getValueAt(rowNo, 0).toString());
        txt_studentName.setText(tblModel.getValueAt(rowNo, 1).toString());
        txt_class.setSelectedItem(tblModel.getValueAt(rowNo, 2).toString());
        txt_branch.setSelectedItem(tblModel.getValueAt(rowNo, 3).toString());
    }//GEN-LAST:event_tbl_studentDetailsMouseClicked

    private void jPanel19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseEntered
        jPanel19.setBackground(new Color(121, 173, 220));
        jPanel19.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
    }//GEN-LAST:event_jPanel19MouseEntered

    private void jPanel19MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseExited
        jPanel19.setBackground(new Color(251, 51, 51));
        jPanel19.setBorder(null);
    }//GEN-LAST:event_jPanel19MouseExited

    private void jPanel20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseEntered
        jPanel20.setBackground(new Color(121, 173, 220));
        jPanel20.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
    }//GEN-LAST:event_jPanel20MouseEntered

    private void jPanel20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseExited
        jPanel20.setBackground(new Color(251, 51, 51));
        jPanel20.setBorder(null);
    }//GEN-LAST:event_jPanel20MouseExited

    private void jPanel18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseEntered
        jPanel18.setBackground(new Color(121, 173, 220));
        jPanel18.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
    }//GEN-LAST:event_jPanel18MouseEntered

    private void jPanel18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseExited
        jPanel18.setBackground(new Color(251, 51, 51));
        jPanel18.setBorder(null);
    }//GEN-LAST:event_jPanel18MouseExited

    private void jPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseClicked
        if (addStudent()) {
            JOptionPane.showMessageDialog(this, "Student record added successfully..!");
            txt_studentId.setText("");
            txt_studentName.setText("");
            txt_class.setSelectedIndex(0);
            txt_branch.setSelectedIndex(0);
            clearBooksTable();
            setStudentsToTable();
        }
    }//GEN-LAST:event_jPanel19MouseClicked

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
        if (updateStudent()) {
            JOptionPane.showMessageDialog(this, "Student record updated successfully..!");
            txt_studentId.setText("");
            txt_studentName.setText("");
            txt_class.setSelectedIndex(0);
            txt_branch.setSelectedIndex(0);
            clearBooksTable();
            setStudentsToTable();
        }
    }//GEN-LAST:event_jPanel20MouseClicked

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked
        if (deleteStudent()) {
            JOptionPane.showMessageDialog(this, "Student record deleted successfully..!");
            txt_studentId.setText("");
            txt_studentName.setText("");
            txt_class.setSelectedIndex(0);
            txt_branch.setSelectedIndex(0);
            clearBooksTable();
            setStudentsToTable();
        }
    }//GEN-LAST:event_jPanel18MouseClicked

    private void jPanel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseEntered
        jPanel8.setBackground(new Color(121, 173, 220));
        jPanel8.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
    }//GEN-LAST:event_jPanel8MouseEntered

    private void jPanel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseExited
        jPanel8.setBackground(new Color(51, 51, 51));
        jPanel8.setBorder(null);
    }//GEN-LAST:event_jPanel8MouseExited

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        dispose();
        ManageBooks manageBooks = new ManageBooks();
        manageBooks.setVisible(true);
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        dispose();
        IssueBook issueBook = new IssueBook();
        issueBook.setVisible(true);
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        dispose();
        ReturnBook returnBook = new ReturnBook();
        returnBook.setVisible(true);
    }//GEN-LAST:event_jPanel11MouseClicked

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
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageStudents().setVisible(true);
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
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
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
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private rojeru_san.complementos.RSTableMetro tbl_studentDetails;
    private javax.swing.JComboBox<String> txt_branch;
    private javax.swing.JComboBox<String> txt_class;
    private app.bolivia.swing.JCTextField txt_studentId;
    private app.bolivia.swing.JCTextField txt_studentName;
    // End of variables declaration//GEN-END:variables
}
