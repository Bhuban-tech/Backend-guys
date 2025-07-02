import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CollegeManager extends JFrame {
    JTextField idField, nameField, studentsField, teachersField, affiliationField, coursesField, feeField, locationField, websiteField;
    JButton addBtn, updateBtn, deleteBtn, viewBtn;
    JTable table;
    DefaultTableModel tableModel;

    // List to hold colleges in memory
    java.util.List<College> collegeList = new ArrayList<>();
    int nextId = 1; // auto-increment id

    public CollegeManager() {
        setTitle("College Management System (In-Memory)");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 5, 5));

        idField = new JTextField();
        idField.setEditable(false); // ID auto-generated, no manual input

        nameField = new JTextField();
        studentsField = new JTextField();
        teachersField = new JTextField();
        affiliationField = new JTextField();
        coursesField = new JTextField();
        feeField = new JTextField();
        locationField = new JTextField();
        websiteField = new JTextField();

        formPanel.add(new JLabel("ID:")); formPanel.add(idField);
        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Number of Students:")); formPanel.add(studentsField);
        formPanel.add(new JLabel("Number of Teachers:")); formPanel.add(teachersField);
        formPanel.add(new JLabel("Affiliation:")); formPanel.add(affiliationField);
        formPanel.add(new JLabel("Courses Available:")); formPanel.add(coursesField);
        formPanel.add(new JLabel("Fee Structure:")); formPanel.add(feeField);
        formPanel.add(new JLabel("Location:")); formPanel.add(locationField);
        formPanel.add(new JLabel("Website:")); formPanel.add(websiteField);

        add(formPanel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        viewBtn = new JButton("View All");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(viewBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // Table for displaying data
        tableModel = new DefaultTableModel(new String[] {
                "ID", "Name", "Students", "Teachers", "Affiliation", "Courses", "Fee", "Location", "Website"
        }, 0);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);

        // Button actions
        addBtn.addActionListener(e -> addCollege());
        updateBtn.addActionListener(e -> updateCollege());
        deleteBtn.addActionListener(e -> deleteCollege());
        viewBtn.addActionListener(e -> loadColleges());

        // When user clicks a row in the table, show data in fields for update/delete
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    idField.setText(tableModel.getValueAt(row, 0).toString());
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    studentsField.setText(tableModel.getValueAt(row, 2).toString());
                    teachersField.setText(tableModel.getValueAt(row, 3).toString());
                    affiliationField.setText(tableModel.getValueAt(row, 4).toString());
                    coursesField.setText(tableModel.getValueAt(row, 5).toString());
                    feeField.setText(tableModel.getValueAt(row, 6).toString());
                    locationField.setText(tableModel.getValueAt(row, 7).toString());
                    websiteField.setText(tableModel.getValueAt(row, 8).toString());
                }
            }
        });

        setVisible(true);
    }

    void addCollege() {
        try {
            String name = nameField.getText();
            int students = Integer.parseInt(studentsField.getText());
            int teachers = Integer.parseInt(teachersField.getText());
            String affiliation = affiliationField.getText();
            String courses = coursesField.getText();
            String fee = feeField.getText();
            String location = locationField.getText();
            String website = websiteField.getText();

            College c = new College(nextId++, name, students, teachers, affiliation, courses, fee, location, website);
            collegeList.add(c);

            JOptionPane.showMessageDialog(this, "College added successfully!");
            clearFields();
            loadColleges();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for students and teachers.");
        }
    }

    void updateCollege() {
        try {
            int id = Integer.parseInt(idField.getText());
            College c = findCollegeById(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "No college found with ID: " + id);
                return;
            }
            c.setName(nameField.getText());
            c.setNumStudents(Integer.parseInt(studentsField.getText()));
            c.setNumTeachers(Integer.parseInt(teachersField.getText()));
            c.setAffiliation(affiliationField.getText());
            c.setCourses(coursesField.getText());
            c.setFeeStructure(feeField.getText());
            c.setLocation(locationField.getText());
            c.setWebsite(websiteField.getText());

            JOptionPane.showMessageDialog(this, "College updated successfully!");
            clearFields();
            loadColleges();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid ID, students and teachers numbers.");
        }
    }

    void deleteCollege() {
        try {
            int id = Integer.parseInt(idField.getText());
            College c = findCollegeById(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "No college found with ID: " + id);
                return;
            }
            collegeList.remove(c);
            JOptionPane.showMessageDialog(this, "College deleted successfully!");
            clearFields();
            loadColleges();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid ID.");
        }
    }

    void loadColleges() {
        tableModel.setRowCount(0); // clear

        for (College c : collegeList) {
            tableModel.addRow(new Object[] {
                    c.getId(), c.getName(), c.getNumStudents(), c.getNumTeachers(),
                    c.getAffiliation(), c.getCourses(), c.getFeeStructure(),
                    c.getLocation(), c.getWebsite()
            });
        }
    }

    void clearFields() {
        idField.setText("");
        nameField.setText("");
        studentsField.setText("");
        teachersField.setText("");
        affiliationField.setText("");
        coursesField.setText("");
        feeField.setText("");
        locationField.setText("");
        websiteField.setText("");
    }

    College findCollegeById(int id) {
        for (College c : collegeList) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CollegeManager::new);
    }
}

// Simple POJO to hold college data
class College {
    private int id;
    private String name;
    private int numStudents;
    private int numTeachers;
    private String affiliation;
    private String courses;
    private String feeStructure;
    private String location;
    private String website;

    public College(int id, String name, int numStudents, int numTeachers, String affiliation,
                   String courses, String feeStructure, String location, String website) {
        this.id = id;
        this.name = name;
        this.numStudents = numStudents;
        this.numTeachers = numTeachers;
        this.affiliation = affiliation;
        this.courses = courses;
        this.feeStructure = feeStructure;
        this.location = location;
        this.website = website;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getNumStudents() { return numStudents; }
    public int getNumTeachers() { return numTeachers; }
    public String getAffiliation() { return affiliation; }
    public String getCourses() { return courses; }
    public String getFeeStructure() { return feeStructure; }
    public String getLocation() { return location; }
    public String getWebsite() { return website; }

    public void setName(String name) { this.name = name; }
    public void setNumStudents(int numStudents) { this.numStudents = numStudents; }
    public void setNumTeachers(int numTeachers) { this.numTeachers = numTeachers; }
    public void setAffiliation(String affiliation) { this.affiliation = affiliation; }
    public void setCourses(String courses) { this.courses = courses; }
    public void setFeeStructure(String feeStructure) { this.feeStructure = feeStructure; }
    public void setLocation(String location) { this.location = location; }
    public void setWebsite(String website) { this.website = website; }
}
