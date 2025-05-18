import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class MainGUI {
    // Main components
    private JFrame frame;
    private JPanel currentPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Data objects
    private Student currentStudent = null;
    private Admin currentAdmin = null;
    private Instructor currentInstructor = null;
    private Course currentCourse = null;
    
    // File managers
    private FileManager studentFile;
    private FileManager adminFile;
    private FileManager courseFile;
    private FileManager instructorFile;
    private FileManager surveyFile;
    
    public MainGUI() {
        // Initialize file managers
        studentFile = new FileManager("Students.txt");
        adminFile = new FileManager("Admins.txt");
        courseFile = new FileManager("Courses.txt");
        instructorFile = new FileManager("instructors.txt");
        surveyFile = new FileManager("instructors.txt");
        
        // Create main frame
        frame = new JFrame("Educational Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        // Create main panel with card layout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        
        // Create login panel and add to main panel
        createLoginPanel();
        
        // Set the frame visible
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Educational Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        
        // Login form fields
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JLabel statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        
        // Add components to form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(userLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginButton, gbc);
        
        gbc.gridy = 3;
        formPanel.add(statusLabel, gbc);
        
        // Add panels to login panel
        loginPanel.add(titlePanel, BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
        
        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                
                if (username.isEmpty() || password.isEmpty()) {
                    statusLabel.setText("Username and password cannot be empty");
                    return;
                }
                
                int userType = authenticateUser(username, password);
                
                if (userType == 1) {
                    createAdminPanel();
                    cardLayout.show(mainPanel, "adminPanel");
                } else if (userType == 2) {
                    createStudentPanel();
                    cardLayout.show(mainPanel, "studentPanel");
                } else if (userType == 3) {
                    createInstructorPanel();
                    cardLayout.show(mainPanel, "instructorPanel");
                } else {
                    statusLabel.setText("Invalid username or password");
                }
            }
        });
        
        // Add to main panel
        mainPanel.add(loginPanel, "loginPanel");
        cardLayout.show(mainPanel, "loginPanel");
    }
    
    private int authenticateUser(String username, String password) {
        // Check student authentication
        List<String> studentsData = studentFile.read();
        for (String data : studentsData) {
            String[] parts = data.split(",");
            if (parts.length >= 5 && parts[3].equals(username) && parts[4].equals(password)) {
                int age = Integer.parseInt(parts[2]);
                double gpa = Double.parseDouble(parts[6]);
                currentStudent = new Student(parts[0], parts[1], age, parts[3], parts[4], parts[5], gpa);
                return 2; // Student type
            }
        }
        
        // Check admin authentication
        List<String> adminData = adminFile.read();
        for (String data : adminData) {
            String[] parts = data.split(",");
            if (parts.length >= 5 && parts[3].equals(username) && parts[4].equals(password)) {
                int age = Integer.parseInt(parts[2]);
                currentAdmin = new Admin(parts[0], parts[1], age, parts[3], parts[4]);
                return 1; // Admin type
            }
        }
        
        // Check instructor authentication
        List<String> instructorData = instructorFile.read();
        for (String data : instructorData) {
            String[] parts = data.split(",");
            if (parts.length >= 5 && parts[3].equals(username) && parts[4].equals(password)) {
                int age = Integer.parseInt(parts[2]);
                currentInstructor = new Instructor(parts[0], parts[1], age, parts[3], parts[4]);
                return 3; // Instructor type
            }
        }
        
        return 0; // Authentication failed
    }
    
    private void createAdminPanel() {
        JPanel adminPanel = new JPanel(new BorderLayout());
        JPanel optionsPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel welcomeLabel = new JLabel("Admin Dashboard - " + currentAdmin.getName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton editStudentsBtn = new JButton("Edit Students");
        JButton editInstructorsBtn = new JButton("Edit Instructors");
        JButton editCoursesBtn = new JButton("Edit Courses");
        JButton makeCoursePageBtn = new JButton("Make Course Page");
        JButton displayStudentsBtn = new JButton("Display All Students");
        JButton displayInstructorsBtn = new JButton("Display All Instructors");
        JButton displayCoursesBtn = new JButton("Display All Courses");
        JButton logoutBtn = new JButton("Logout");
        
        optionsPanel.add(editStudentsBtn);
        optionsPanel.add(editInstructorsBtn);
        optionsPanel.add(editCoursesBtn);
        optionsPanel.add(makeCoursePageBtn);
        optionsPanel.add(displayStudentsBtn);
        optionsPanel.add(displayInstructorsBtn);
        optionsPanel.add(displayCoursesBtn);
        optionsPanel.add(logoutBtn);
        
        // Add action listeners
        editStudentsBtn.addActionListener(e -> createEditStudentsPanel());
        editInstructorsBtn.addActionListener(e -> createEditInstructorsPanel());
        editCoursesBtn.addActionListener(e -> createEditCoursesPanel());
        
        makeCoursePageBtn.addActionListener(e -> {
            String courseId = JOptionPane.showInputDialog(frame, "Enter course ID to create page for:");
            
            if (courseId != null && !courseId.isEmpty()) {
                // Find the course
                Course course = null;
                List<String> courseData = courseFile.read();
                for (String data : courseData) {
                    String[] parts = data.split(",");
                    if (parts.length >= 2 && parts[0].equals(courseId)) {
                        course = new Course(parts[0], parts[1]);
                        break;
                    }
                }
                
                if (course == null) {
                    JOptionPane.showMessageDialog(frame, "Course not found");
                    return;
                }
                
                // Show course page dialog
                CoursePageDialog dialog = new CoursePageDialog(frame, courseId);
                dialog.setVisible(true);
                
                if (dialog.isConfirmed()) {
                    String room = dialog.getRoom();
                    String branch = dialog.getBranch();
                    String price = dialog.getPrice();
                    String startDate = dialog.getStartDate();
                    String daysOfCourse = dialog.getDaysOfCourse();
                    String endDate = dialog.getEndDate();
                    
                    course.makePage(courseId, room, branch, startDate, endDate, price, daysOfCourse);
                    JOptionPane.showMessageDialog(frame, "Course page created successfully");
                }
            }
        });
        
        displayStudentsBtn.addActionListener(e -> {
            StringBuilder students = new StringBuilder();
            currentAdmin.displayAllStudents();
            
            // Create and show a dialog with the students list
            List<String> studentsData = studentFile.read();
            for (String data : studentsData) {
                students.append(data).append("\n");
            }
            
            JTextArea textArea = new JTextArea(students.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "All Students", JOptionPane.INFORMATION_MESSAGE);
        });
        
        displayInstructorsBtn.addActionListener(e -> {
            StringBuilder instructors = new StringBuilder();
            currentAdmin.displayAllInstructors();
            
            // Create and show a dialog with the instructors list
            List<String> instructorsData = instructorFile.read();
            for (String data : instructorsData) {
                instructors.append(data).append("\n");
            }
            
            JTextArea textArea = new JTextArea(instructors.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "All Instructors", JOptionPane.INFORMATION_MESSAGE);
        });
        
        displayCoursesBtn.addActionListener(e -> {
            StringBuilder courses = new StringBuilder();
            currentAdmin.displayAllCourses();
            
            // Create and show a dialog with the courses list
            List<String> coursesData = courseFile.read();
            for (String data : coursesData) {
                courses.append(data).append("\n");
            }
            
            JTextArea textArea = new JTextArea(courses.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "All Courses", JOptionPane.INFORMATION_MESSAGE);
        });
        
        logoutBtn.addActionListener(e -> {
            currentAdmin = null;
            cardLayout.show(mainPanel, "loginPanel");
        });
        
        adminPanel.add(welcomeLabel, BorderLayout.NORTH);
        adminPanel.add(optionsPanel, BorderLayout.CENTER);
        
        mainPanel.add(adminPanel, "adminPanel");
    }
    
    private void createEditStudentsPanel() {
        JPanel editStudentsPanel = new JPanel(new BorderLayout());
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Student Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton addStudentBtn = new JButton("Add Student");
        JButton deleteStudentBtn = new JButton("Delete Student");
        JButton updateStudentBtn = new JButton("Update Student");
        JButton backBtn = new JButton("Back to Admin Panel");
        
        optionsPanel.add(addStudentBtn);
        optionsPanel.add(deleteStudentBtn);
        optionsPanel.add(updateStudentBtn);
        optionsPanel.add(backBtn);
        
        // Add student action
        addStudentBtn.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JTextField usernameField = new JTextField();
            JTextField passwordField = new JTextField();
            JTextField levelField = new JTextField();
            JTextField gpaField = new JTextField();
            
            Object[] fields = {
                "ID:", idField,
                "Name:", nameField,
                "Age:", ageField,
                "Username:", usernameField,
                "Password:", passwordField,
                "Level:", levelField,
                "GPA:", gpaField
            };
            
            int result = JOptionPane.showConfirmDialog(frame, fields, "Add Student", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String id = idField.getText();
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    String level = levelField.getText();
                    double gpa = Double.parseDouble(gpaField.getText());
                    
                    if (id.isEmpty() || name.isEmpty() || username.isEmpty() || 
                        password.isEmpty() || level.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "All fields must be filled out");
                        return;
                    }
                    
                    currentAdmin.addStudent(id, name, age, username, password, level, gpa);
                    JOptionPane.showMessageDialog(frame, "Student added successfully");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid numeric input");
                }
            }
        });
        
        // Delete student action
        deleteStudentBtn.addActionListener(e -> {
            String studentId = JOptionPane.showInputDialog(frame, "Enter student ID to delete:");
            
            if (studentId != null && !studentId.isEmpty()) {
                currentAdmin.deleteStudent(studentId);
                JOptionPane.showMessageDialog(frame, "Student deleted successfully");
            }
        });
        
        // Update student action
        updateStudentBtn.addActionListener(e -> {
            String studentId = JOptionPane.showInputDialog(frame, "Enter student ID to update:");
            
            if (studentId != null && !studentId.isEmpty()) {
                JTextField nameField = new JTextField();
                JTextField ageField = new JTextField();
                JTextField levelField = new JTextField();
                JTextField gpaField = new JTextField();
                
                Object[] fields = {
                    "Name:", nameField,
                    "Age:", ageField,
                    "Level:", levelField,
                    "GPA:", gpaField
                };
                
                int result = JOptionPane.showConfirmDialog(frame, fields, "Update Student", JOptionPane.OK_CANCEL_OPTION);
                
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String name = nameField.getText();
                        int age = Integer.parseInt(ageField.getText());
                        String level = levelField.getText();
                        double gpa = Double.parseDouble(gpaField.getText());
                        
                        if (name.isEmpty() || level.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "All fields must be filled out");
                            return;
                        }
                        
                        currentAdmin.updateStudent(studentId, name, age, level, gpa);
                        JOptionPane.showMessageDialog(frame, "Student updated successfully");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid numeric input");
                    }
                }
            }
        });
        
        // Back button action
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "adminPanel"));
        
        editStudentsPanel.add(titleLabel, BorderLayout.NORTH);
        editStudentsPanel.add(optionsPanel, BorderLayout.CENTER);
        
        mainPanel.add(editStudentsPanel, "editStudentsPanel");
        cardLayout.show(mainPanel, "editStudentsPanel");
    }
    
    private void createEditInstructorsPanel() {
        JPanel editInstructorsPanel = new JPanel(new BorderLayout());
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Instructor Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton addInstructorBtn = new JButton("Add Instructor");
        JButton deleteInstructorBtn = new JButton("Delete Instructor");
        JButton updateInstructorBtn = new JButton("Update Instructor");
        JButton backBtn = new JButton("Back to Admin Panel");
        
        optionsPanel.add(addInstructorBtn);
        optionsPanel.add(deleteInstructorBtn);
        optionsPanel.add(updateInstructorBtn);
        optionsPanel.add(backBtn);
        
        // Add Instructor action
        addInstructorBtn.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JTextField usernameField = new JTextField();
            JTextField passwordField = new JTextField();
            
            Object[] fields = {
                "ID:", idField,
                "Name:", nameField,
                "Age:", ageField,
                "Username:", usernameField,
                "Password:", passwordField
            };
            
            int result = JOptionPane.showConfirmDialog(frame, fields, "Add Instructor", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String id = idField.getText();
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    
                    if (id.isEmpty() || name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "All fields must be filled out");
                        return;
                    }
                    
                    currentAdmin.addInstructor(id, name, age, username, password);
                    JOptionPane.showMessageDialog(frame, "Instructor added successfully");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid numeric input");
                }
            }
        });
        
        // Delete Instructor action
        deleteInstructorBtn.addActionListener(e -> {
            String instructorId = JOptionPane.showInputDialog(frame, "Enter instructor ID to delete:");
            
            if (instructorId != null && !instructorId.isEmpty()) {
                currentAdmin.deleteInstructor(instructorId);
                JOptionPane.showMessageDialog(frame, "Instructor deleted successfully");
            }
        });
        
        // Update Instructor action
        updateInstructorBtn.addActionListener(e -> {
            String instructorId = JOptionPane.showInputDialog(frame, "Enter instructor ID to update:");
            
            if (instructorId != null && !instructorId.isEmpty()) {
                JTextField nameField = new JTextField();
                JTextField ageField = new JTextField();
                
                Object[] fields = {
                    "Name:", nameField,
                    "Age:", ageField
                };
                
                int result = JOptionPane.showConfirmDialog(frame, fields, "Update Instructor", JOptionPane.OK_CANCEL_OPTION);
                
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String name = nameField.getText();
                        int age = Integer.parseInt(ageField.getText());
                        
                        if (name.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "All fields must be filled out");
                            return;
                        }
                        
                        currentAdmin.updateInstructor(instructorId, name, age);
                        JOptionPane.showMessageDialog(frame, "Instructor updated successfully");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid numeric input");
                    }
                }
            }
        });
        
        // Back button action
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "adminPanel"));
        
        editInstructorsPanel.add(titleLabel, BorderLayout.NORTH);
        editInstructorsPanel.add(optionsPanel, BorderLayout.CENTER);
        
        mainPanel.add(editInstructorsPanel, "editInstructorsPanel");
        cardLayout.show(mainPanel, "editInstructorsPanel");
    }
    
    private void createEditCoursesPanel() {
        JPanel editCoursesPanel = new JPanel(new BorderLayout());
        JPanel optionsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Course Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton addCourseBtn = new JButton("Add Course");
        JButton deleteCourseBtn = new JButton("Delete Course");
        JButton assignStudentBtn = new JButton("Assign Student to Course");
        JButton assignInstructorBtn = new JButton("Assign Course to Instructor");
        JButton backBtn = new JButton("Back to Admin Panel");
        
        optionsPanel.add(addCourseBtn);
        optionsPanel.add(deleteCourseBtn);
        optionsPanel.add(assignStudentBtn);
        optionsPanel.add(assignInstructorBtn);
        optionsPanel.add(backBtn);
        
        // Add Course action
        addCourseBtn.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            
            Object[] fields = {
                "Course ID:", idField,
                "Course Name:", nameField
            };
            
            int result = JOptionPane.showConfirmDialog(frame, fields, "Add Course", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                String id = idField.getText();
                String name = nameField.getText();
                
                if (id.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out");
                    return;
                }
                
                currentAdmin.addCourse(id, name);
                JOptionPane.showMessageDialog(frame, "Course added successfully");
            }
        });
        
        // Delete Course action
        deleteCourseBtn.addActionListener(e -> {
            String courseId = JOptionPane.showInputDialog(frame, "Enter course ID to delete:");
            
            if (courseId != null && !courseId.isEmpty()) {
                currentAdmin.deleteCourse(courseId);
                JOptionPane.showMessageDialog(frame, "Course deleted successfully");
            }
        });
        
        // Assign Student to Course action
        assignStudentBtn.addActionListener(e -> {
            JTextField courseIdField = new JTextField();
            JTextField studentIdField = new JTextField();
            
            Object[] fields = {
                "Course ID:", courseIdField,
                "Student ID:", studentIdField
            };
            
            int result = JOptionPane.showConfirmDialog(frame, fields, "Assign Student to Course", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                String courseId = courseIdField.getText();
                String studentId = studentIdField.getText();
                
                if (courseId.isEmpty() || studentId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out");
                    return;
                }
                
                // Find the course and student
                Course course = null;
                Student student = null;
                
                List<String> courseData = courseFile.read();
                for (String data : courseData) {
                    String[] parts = data.split(",");
                    if (parts.length >= 2 && parts[0].equals(courseId)) {
                        course = new Course(parts[0], parts[1]);
                        break;
                    }
                }
                
                List<String> studentsData = studentFile.read();
                for (String data : studentsData) {
                    String[] parts = data.split(",");
                    if (parts.length >= 7 && parts[0].equals(studentId)) {
                        int age = Integer.parseInt(parts[2]);
                        double gpa = Double.parseDouble(parts[6]);
                        student = new Student(parts[0], parts[1], age, parts[3], parts[4], parts[5], gpa);
                        break;
                    }
                }
                
                if (course == null) {
                    JOptionPane.showMessageDialog(frame, "Course not found");
                    return;
                }
                
                if (student == null) {
                    JOptionPane.showMessageDialog(frame, "Student not found");
                    return;
                }
                
                course.addStudent(studentId);
                student.enrollInCourse(courseId);
                JOptionPane.showMessageDialog(frame, "Student assigned to course successfully");
            }
        });
        
        // Assign Course to Instructor action
        assignInstructorBtn.addActionListener(e -> {
            JTextField courseIdField = new JTextField();
            JTextField instructorIdField = new JTextField();
            
            Object[] fields = {
                "Course ID:", courseIdField,
                "Instructor ID:", instructorIdField
            };
            
            int result = JOptionPane.showConfirmDialog(frame, fields, "Assign Course to Instructor", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                String courseId = courseIdField.getText();
                String instructorId = instructorIdField.getText();
                
                if (courseId.isEmpty() || instructorId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out");
                    return;
                }
                
                // Find the course and instructor
                Course course = null;
                Instructor instructor = null;
                
                List<String> courseData = courseFile.read();
                for (String data : courseData) {
                    String[] parts = data.split(",");
                    if (parts.length >= 2 && parts[0].equals(courseId)) {
                        course = new Course(parts[0], parts[1]);
                        break;
                    }
                }
                
                List<String> instructorData = instructorFile.read();
                for (String data : instructorData) {
                    String[] parts = data.split(",");
                    if (parts.length >= 5 && parts[0].equals(instructorId)) {
                        int age = Integer.parseInt(parts[2]);
                        instructor = new Instructor(parts[0], parts[1], age, parts[3], parts[4]);
                        break;
                    }
                }
                
                if (course == null) {
                    JOptionPane.showMessageDialog(frame, "Course not found");
                    return;
                }
                
                if (instructor == null) {
                    JOptionPane.showMessageDialog(frame, "Instructor not found");
                    return;
                }
                
                course.setInstructor(instructorId);
                instructor.addCourse(courseId);
                JOptionPane.showMessageDialog(frame, "Course assigned to instructor successfully");
            }
        });
        
        // Back button action
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "adminPanel"));
        
        editCoursesPanel.add(titleLabel, BorderLayout.NORTH);
        editCoursesPanel.add(optionsPanel, BorderLayout.CENTER);
        
        mainPanel.add(editCoursesPanel, "editCoursesPanel");
        cardLayout.show(mainPanel, "editCoursesPanel");
    }
    
    private void createStudentPanel() {
        JPanel studentPanel = new JPanel(new BorderLayout());
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel welcomeLabel = new JLabel("Student Dashboard - " + currentStudent.getName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton seeCoursesBtn = new JButton("See All Courses");
        JButton seeGradesBtn = new JButton("See Grades");
        JButton makeSurveyBtn = new JButton("Make Survey");
        JButton logoutBtn = new JButton("Logout");
        
        optionsPanel.add(seeCoursesBtn);
        optionsPanel.add(seeGradesBtn);
        optionsPanel.add(makeSurveyBtn);
        optionsPanel.add(logoutBtn);
        
        // See all courses action
        seeCoursesBtn.addActionListener(e -> {
            currentStudent.getEnrolledCourses("none");
            
            // Get the enrolled courses and display them
            JOptionPane.showMessageDialog(frame, "Please check the console for enrolled courses");
        });
        
        // See grades action
        seeGradesBtn.addActionListener(e -> {
            String courseId = JOptionPane.showInputDialog(frame, "Enter course ID to see grades:");
            
            if (courseId != null && !courseId.isEmpty()) {
                currentStudent.getEnrolledCourses(courseId);
                JOptionPane.showMessageDialog(frame, "Please check the console for grade information");
            }
        });
        
        // Make survey action
        makeSurveyBtn.addActionListener(e -> {
            String courseId = JOptionPane.showInputDialog(frame, "Enter course ID for the survey:");
            
            if (courseId != null && !courseId.isEmpty()) {
                String survey = JOptionPane.showInputDialog(frame, "Enter your survey:");
                
                if (survey != null && !survey.isEmpty()) {
                    currentStudent.makeSurvey(survey, courseId);
                    JOptionPane.showMessageDialog(frame, "Survey submitted successfully");
                }
            }
        });
        
        // Logout action
        logoutBtn.addActionListener(e -> {
            currentStudent = null;
            cardLayout.show(mainPanel, "loginPanel");
        });
        
        studentPanel.add(welcomeLabel, BorderLayout.NORTH);
        studentPanel.add(optionsPanel, BorderLayout.CENTER);
        
        mainPanel.add(studentPanel, "studentPanel");
    }
    
    private void createInstructorPanel() {
        JPanel instructorPanel = new JPanel(new BorderLayout());
        JPanel optionsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel welcomeLabel = new JLabel("Instructor Dashboard - " + currentInstructor.getName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton giveGradesBtn = new JButton("Give Grades");
        JButton displayCoursesBtn = new JButton("Display Courses");
        JButton logoutBtn = new JButton("Logout");
        
        optionsPanel.add(giveGradesBtn);
        optionsPanel.add(displayCoursesBtn);
        optionsPanel.add(logoutBtn);
        
        // Give grades action
        giveGradesBtn.addActionListener(e -> {
            JTextField studentIdField = new JTextField();
            JTextField courseIdField = new JTextField();
            JTextField gradeField = new JTextField();
            
            Object[] fields = {
                "Student ID:", studentIdField,
                "Course ID:", courseIdField,
                "Grade:", gradeField
            };
            
            int result = JOptionPane.showConfirmDialog(frame, fields, "Assign Grade", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                String studentId = studentIdField.getText();
                String courseId = courseIdField.getText();
                String grade = gradeField.getText();
                
                if (studentId.isEmpty() || courseId.isEmpty() || grade.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out");
                    return;
                }
                
                currentInstructor.assignGrade(studentId, courseId, grade);
                JOptionPane.showMessageDialog(frame, "Grade assigned successfully");
            }
        });
        
        // Display courses action
        displayCoursesBtn.addActionListener(e -> {
            currentInstructor.displayCourses();
            JOptionPane.showMessageDialog(frame, "Please check the console for courses information");
        });
        
        // Logout action
        logoutBtn.addActionListener(e -> {
            currentInstructor = null;
            cardLayout.show(mainPanel, "loginPanel");
        });
        
        instructorPanel.add(welcomeLabel, BorderLayout.NORTH);
        instructorPanel.add(optionsPanel, BorderLayout.CENTER);
        
        mainPanel.add(instructorPanel, "instructorPanel");
    }
    
    public static void main(String[] args) {
        // Use EventQueue to ensure thread safety
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set look and feel to the system look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new MainGUI();
            }
        });
    }
}