import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Student student1 = null;
        Admin admin1 = null;
        Course course1 = null;
        Instructor instructor1 = null;
        FileManager StudentFile = new FileManager("Students.txt");
        FileManager AdminFile = new FileManager("Admins.txt");
        FileManager CourseFile = new FileManager("Courses.txt");
        FileManager InstructorFile = new FileManager("Instructors.txt");
        FileManager SurveyFile = new FileManager("Surveys.txt"); // Fixed filename

        boolean programRunning = true;
        
        while (programRunning) {
            // Login process
            boolean loggedIn = false;
            int type = 0;
            
            while (!loggedIn && programRunning) {
                System.out.println("\n===== LOGIN SYSTEM =====");
                System.out.println("1. Login");
                System.out.println("0. Exit Program");
                
                int loginChoice = safeNextInt(input, "Enter your choice: ", 0, 1);
                
                if (loginChoice == 0) {
                    System.out.println("Exiting program. Goodbye!");
                    programRunning = false;
                    break;
                }
                
                System.out.println("\nHello and welcome, let's login!!");
                System.out.println("Username: ");
                String user = input.next();
                System.out.println("Password: ");
                String password = input.next();
                
                // Refresh data before login
                List<String> studentsData = StudentFile.read();
                List<String> adminData = AdminFile.read();
                List<String> instructorData = InstructorFile.read();
                
                // Check admin credentials
                for (String data : adminData) {
                    String[] parts = data.split(",");
                    if (parts.length >= 5 && parts[3].equals(user) && parts[4].equals(password)) {
                        int age = Integer.parseInt(parts[2]);
                        admin1 = new Admin(parts[0], parts[1], age, parts[3], parts[4]);
                        type = 1;
                        loggedIn = true;
                        System.out.println("Login successful as Admin!");
                        break;
                    }
                }
                
                // Check student credentials if not logged in as admin
                if (!loggedIn) {
                    for (String data : studentsData) {
                        String[] parts = data.split(",");
                        if (parts.length >= 7 && parts[3].equals(user) && parts[4].equals(password)) {
                            type = 2;
                            int age = Integer.parseInt(parts[2]);
                            double gpa = Double.parseDouble(parts[6]);
                            student1 = new Student(parts[0], parts[1], age, parts[3], parts[4], parts[5], gpa);
                            loggedIn = true;
                            System.out.println("Login successful as Student!");
                            break;
                        }
                    }
                }
                
                // Check instructor credentials if still not logged in
                if (!loggedIn) {
                    for (String data : instructorData) {
                        String[] parts = data.split(",");
                        if (parts.length >= 5 && parts[3].equals(user) && parts[4].equals(password)) {
                            type = 3;
                            int age = Integer.parseInt(parts[2]);
                            instructor1 = new Instructor(parts[0], parts[1], age, parts[3], parts[4]);
                            loggedIn = true;
                            System.out.println("Login successful as Instructor!");
                            break;
                        }
                    }
                }
                
                if (!loggedIn && programRunning) {
                    System.out.println("Invalid username or password. Please try again.");
                }
            }
            
            // Main menu after login
            boolean menuRunning = loggedIn;
            
            while (menuRunning && programRunning) {
                // Refresh data for each iteration
                List<String> studentsData = StudentFile.read();
                List<String> adminData = AdminFile.read();
                List<String> courseData = CourseFile.read();
                List<String> instructorData = InstructorFile.read();
                List<String> surveyData = SurveyFile.read();
                
                int choice = 0;
                
                // Admin Menu
                if (type == 1) {
                    System.out.println("\n===== ADMIN MENU =====");
                    System.out.println("1. Edit students");
                    System.out.println("2. Edit instructors");
                    System.out.println("3. Edit courses");
                    System.out.println("4. Display all students");
                    System.out.println("5. Display all instructors");
                    System.out.println("6. Display all courses");
                    System.out.println("7. Set course details");
                    System.out.println("8. Logout");
                    System.out.println("0. Exit program");
                    
                    choice = safeNextInt(input, "Enter your choice: ", 0, 8);
                    
                    if (choice == 0) {
                        System.out.println("Exiting program. Goodbye!");
                        menuRunning = false;
                        programRunning = false;
                        continue;
                    }
                    
                    if (choice == 8) {
                        System.out.println("Logging out...");
                        menuRunning = false;
                        continue;
                    }
                    
                    handleAdminChoice(choice, input, admin1, CourseFile, courseData, studentsData, instructorData);
                }
                // Student Menu
                else if (type == 2) {
                    System.out.println("\n===== STUDENT MENU =====");
                    System.out.println("1. See courses");
                    System.out.println("2. See grades");
                    System.out.println("3. Make survey");
                    System.out.println("8. Logout");
                    System.out.println("0. Exit program");
                    
                    choice = safeNextInt(input, "Enter your choice: ", 0, 8);
                    
                    if (choice == 0) {
                        System.out.println("Exiting program. Goodbye!");
                        menuRunning = false;
                        programRunning = false;
                        continue;
                    }
                    
                    if (choice == 8) {
                        System.out.println("Logging out...");
                        menuRunning = false;
                        continue;
                    }
                    
                    handleStudentChoice(choice, input, student1);
                }
                // Instructor Menu
                else if (type == 3) {
                    System.out.println("\n===== INSTRUCTOR MENU =====");
                    System.out.println("1. Give grades");
                    System.out.println("2. Display courses");
                    System.out.println("8. Logout");
                    System.out.println("0. Exit program");
                    
                    choice = safeNextInt(input, "Enter your choice: ", 0, 8);
                    
                    if (choice == 0) {
                        System.out.println("Exiting program. Goodbye!");
                        menuRunning = false;
                        programRunning = false;
                        continue;
                    }
                    
                    if (choice == 8) {
                        System.out.println("Logging out...");
                        menuRunning = false;
                        continue;
                    }
                    
                    handleInstructorChoice(choice, input, instructor1);
                }
            }
        }
    }
    
    // Helper method for safe integer input with validation
    private static int safeNextInt(Scanner scanner, String prompt, int min, int max) {
        int result = -1;
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            
            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                
                if (result >= min && result <= max) {
                    valid = true;
                } else {
                    System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
        
        return result;
    }
    
    // Helper method to handle Admin choices
    private static void handleAdminChoice(int choice, Scanner input, Admin admin1, 
                                         FileManager CourseFile, List<String> courseData, 
                                         List<String> studentsData, List<String> instructorData) {
        String studentId, studentName, studentUsername, studentPassword, studentLevel;
        double studentGpa;
        int studentAge;
        String courseId, courseName;
        Course course1 = null;
        Student student1 = null;
        Instructor instructor1 = null;
                    
        switch (choice) {
            case 1: // Edit students
                System.out.println("\n===== EDIT STUDENTS =====");
                System.out.println("1. Add student");
                System.out.println("2. Delete student");
                System.out.println("3. Update student");
                System.out.println("0. Back to main menu");
                
                int subChoice = safeNextInt(input, "Enter your choice: ", 0, 3);
                
                if (subChoice == 0) {
                    return;
                }
                
                switch (subChoice) {
                    case 1: // Add student
                        System.out.println("\nPlease enter the student info:");
                        System.out.println("ID:");
                        studentId = input.next();
                        
                        System.out.println("Name:");
                        studentName = input.next();
                        
                        studentAge = safeNextInt(input, "Age: ", 0, 120);
                        
                        System.out.println("Username:");
                        studentUsername = input.next();
                        
                        System.out.println("Password:");
                        studentPassword = input.next();
                        
                        System.out.println("Level:");
                        studentLevel = input.next();
                        
                        System.out.println("GPA:");
                        studentGpa = safeNextDouble(input, "GPA: ", 0.0, 4.0);
                        
                        admin1.addStudent(studentId, studentName, studentAge, studentUsername, studentPassword, studentLevel, studentGpa);
                        break;
                        
                    case 2: // Delete student
                        System.out.println("Enter student ID to delete:");
                        studentId = input.next();
                        admin1.deleteStudent(studentId);
                        break;
                        
                    case 3: // Update student
                        System.out.println("Enter student's ID to update info:");
                        studentId = input.next();
                        System.out.println("Enter student's new info:");
                        
                        System.out.println("Name:");
                        studentName = input.next();
                        
                        studentAge = safeNextInt(input, "Age: ", 0, 120);
                        
                        System.out.println("Level:");
                        studentLevel = input.next();
                        
                        System.out.println("GPA:");
                        studentGpa = safeNextDouble(input, "GPA: ", 0.0, 4.0);
                        
                        admin1.updateStudent(studentId, studentName, studentAge, studentLevel, studentGpa);
                        break;
                }
                break;
                
            case 2: // Edit instructors
                System.out.println("\n===== EDIT INSTRUCTORS =====");
                System.out.println("1. Add instructor");
                System.out.println("2. Delete instructor");
                System.out.println("3. Update instructor");
                System.out.println("0. Back to main menu");
                
                subChoice = safeNextInt(input, "Enter your choice: ", 0, 3);
                
                if (subChoice == 0) {
                    return;
                }
                
                String instructorId, instructorName, instructorUsername, instructorPassword;
                int instructorAge;
                
                switch (subChoice) {
                    case 1: // Add instructor
                        System.out.println("\nPlease enter the instructor info:");
                        System.out.println("ID:");
                        instructorId = input.next();
                        
                        System.out.println("Name:");
                        instructorName = input.next();
                        
                        instructorAge = safeNextInt(input, "Age: ", 0, 120);
                        
                        System.out.println("Username:");
                        instructorUsername = input.next();
                        
                        System.out.println("Password:");
                        instructorPassword = input.next();
                        
                        admin1.addInstructor(instructorId, instructorName, instructorAge, instructorUsername, instructorPassword);
                        break;
                        
                    case 2: // Delete instructor
                        System.out.println("Enter instructor ID to delete:");
                        instructorId = input.next();
                        admin1.deleteInstructor(instructorId);
                        break;
                        
                    case 3: // Update instructor
                        System.out.println("Enter instructor's ID to update info:");
                        instructorId = input.next();
                        System.out.println("Enter instructor's new info:");
                        
                        System.out.println("Name:");
                        instructorName = input.next();
                        
                        instructorAge = safeNextInt(input, "Age: ", 0, 120);
                        
                        admin1.updateInstructor(instructorId, instructorName, instructorAge);
                        break;
                }
                break;
                
            case 3: // Edit courses
                System.out.println("\n===== EDIT COURSES =====");
                System.out.println("1. Add course");
                System.out.println("2. Delete course");
                System.out.println("3. Assign student to a course");
                System.out.println("4. Assign a course to an instructor");
                System.out.println("0. Back to main menu");
                
                subChoice = safeNextInt(input, "Enter your choice: ", 0, 4);
                
                if (subChoice == 0) {
                    return;
                }
                
                switch (subChoice) {
                    case 1: // Add course
                        System.out.println("Enter course Id:");
                        courseId = input.next();
                        System.out.println("Enter course Name:");
                        courseName = input.next();
                        
                        admin1.addCourse(courseId, courseName);
                        break;
                        
                    case 2: // Delete course
                        System.out.println("Enter course Id:");
                        courseId = input.next();
                        admin1.deleteCourse(courseId);
                        break;
                        
                    case 3: // Assign student to course
                        System.out.println("Enter course Id:");
                        courseId = input.next();
                        System.out.println("Enter student ID to assign to:");
                        studentId = input.next();
                        
                        // Find course
                        for (String data : courseData) {
                            String[] parts = data.split(",");
                            if (parts.length >= 2 && parts[0].equals(courseId)) {
                                course1 = new Course(parts[0], parts[1]);
                                break;
                            }
                        }
                        
                        if (course1 == null) {
                            System.out.println("Course not found!");
                            break;
                        }
                        
                        // Find student
                        for (String data : studentsData) {
                            String[] parts = data.split(",");
                            if (parts.length >= 7 && parts[0].equals(studentId)) {
                                int age = Integer.parseInt(parts[2]);
                                double gpa = Double.parseDouble(parts[6]);
                                student1 = new Student(parts[0], parts[1], age, parts[3], parts[4], parts[5], gpa);
                                break;
                            }
                        }
                        
                        if (student1 == null) {
                            System.out.println("Student not found!");
                            break;
                        }
                        
                        course1.addStudent(studentId);
                        student1.enrollInCourse(courseId);
                        System.out.println("Student successfully assigned to course!");
                        break;
                        
                    case 4: // Assign course to instructor
                        System.out.println("Enter course Id:");
                        courseId = input.next();
                        System.out.println("Enter instructor ID to assign to:");
                        instructorId = input.next();
                        
                        // Find course
                        for (String data : courseData) {
                            String[] parts = data.split(",");
                            if (parts.length >= 2 && parts[0].equals(courseId)) {
                                course1 = new Course(parts[0], parts[1]);
                                break;
                            }
                        }
                        
                        if (course1 == null) {
                            System.out.println("Course not found!");
                            break;
                        }
                        
                        // Find instructor
                        for (String data : instructorData) {
                            String[] parts = data.split(",");
                            if (parts.length >= 5 && parts[0].equals(instructorId)) {
                                int age = Integer.parseInt(parts[2]);
                                instructor1 = new Instructor(parts[0], parts[1], age, parts[3], parts[4]);
                                break;
                            }
                        }
                        
                        if (instructor1 == null) {
                            System.out.println("Instructor not found!");
                            break;
                        }
                        
                        course1.setInstructor(instructorId);
                        instructor1.addCourse(courseId);
                        System.out.println("Course successfully assigned to instructor!");
                        break;
                }
                break;
                
            case 4: // Display all students
                admin1.displayAllStudents();
                break;
                
            case 5: // Display all instructors
                admin1.displayAllInstructors();
                break;
                
            case 6: // Display all courses
                admin1.displayAllCourses();
                break;
                
            case 7: // Set course details
                System.out.println("Enter course Id:");
                courseId = input.next();
                
                // Find course
                for (String data : courseData) {
                    String[] parts = data.split(",");
                    if (parts.length >= 2 && parts[0].equals(courseId)) {
                        course1 = new Course(parts[0], parts[1]);
                        break;
                    }
                }
                
                if (course1 == null) {
                    System.out.println("Course not found!");
                    break;
                }
                
                // Get course details
                System.out.println("Enter room: ");
                String room = input.next();
                
                System.out.println("Enter branch: ");
                String branch = input.next();
                
                System.out.println("Enter price: ");
                String price = input.next();
                
                System.out.println("Enter start date: ");
                String startDate = input.next();
                
                System.out.println("Enter days of course: ");
                String daysOfCourse = input.next();
                
                System.out.println("Enter end date: ");
                String endDate = input.next();
                
                course1.makePage(courseId, room, branch, startDate, endDate, price, daysOfCourse);
                System.out.println("Course details successfully updated!");
                break;
        }
    }
    
    // Helper method to handle Student choices
    private static void handleStudentChoice(int choice, Scanner input, Student student1) {
        switch (choice) {
            case 1: // See courses
                student1.getEnrolledCourses("none");
                break;
                
            case 2: // See grades
                System.out.println("Enter course id:");
                String courseId = input.next();
                student1.getEnrolledCourses(courseId);
                break;
                
            case 3: // Make survey
                System.out.println("Enter course id:");
                courseId = input.next();
                
                // Clear the buffer
                input.nextLine();
                
                System.out.println("Enter survey:");
                String survey = input.nextLine();
                
                student1.makeSurvey(survey, courseId);
                System.out.println("Survey submitted successfully!");
                break;
        }
    }
    
    // Helper method to handle Instructor choices
    private static void handleInstructorChoice(int choice, Scanner input, Instructor instructor1) {
        switch (choice) {
            case 1: // Give grades
                System.out.println("Enter student ID:");
                String studentId = input.next();
                
                System.out.println("Enter course ID:");
                String courseId = input.next();
                
                System.out.println("Enter grade:");
                String grade = input.next();
                
                instructor1.assignGrade(studentId, courseId, grade);
                System.out.println("Grade assigned successfully!");
                break;
                
            case 2: // Display courses
                instructor1.displayCourses();
                break;
        }
    }
    
    // Helper method for safe double input with validation
    private static double safeNextDouble(Scanner scanner, String prompt, double min, double max) {
        double result = -1.0;
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            
            if (scanner.hasNextDouble()) {
                result = scanner.nextDouble();
                
                if (result >= min && result <= max) {
                    valid = true;
                } else {
                    System.out.println("Invalid value. Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
        
        return result;
    }
}