import java.util.List;
import java.util.ArrayList;


public class Admin extends User {
    public Admin(String id, String name, int age, String username, String password) {
        super(id, name, age, username, password);
    }

    public void addStudent(String studentId, String name, int age, String username, String password, String level, double GPA) {
        Student student = new Student(studentId, name, age, username, password, level, GPA);
        student.addStudent();
        System.out.println("Student added successfully.");
    }

    public void addInstructor(String instructorId, String name, int age, String username, String password) {
        Instructor instructor = new Instructor(instructorId, name, age, username, password);
        String instructorData = instructorId + "," + name + "," + age + "," + username + "," + password + ",None";
        new FileManager("Instructors.txt").write(instructorData);
        System.out.println("Instructor added successfully.");
    }

    public void addCourse(String courseId, String courseName) {
        Course course = new Course(courseId, courseName);
        String courseData = courseId + "," + courseName + ",None,None";
        new FileManager("Courses.txt").write(courseData);
        System.out.println("Course added successfully.");
    }

    public void displayAllStudents() {
        Student student = new Student("", "", 0, "", "", "", 0.0);
        student.displayAllStudents();
    }

    public void displayAllInstructors() {
        FileManager fileManager = new FileManager("Instructors.txt");
        List<String> instructorsData = fileManager.read();
        if (instructorsData.isEmpty()) {
            System.out.println("No instructors found.");
            return;
        }
        System.out.println("ID | Name | Age | Username | Courses Taught");
        System.out.println("---------------------------------------------");
        for (String data : instructorsData) {
            String[] parts = data.split(",");
            if (parts.length >= 5) {
                System.out.println(parts[0] + " | " + parts[1] + " | " + parts[2] + " | " +
                        parts[3] + " | " + (parts.length > 5 ? parts[5] : "None"));
            }
        }
    }

    public void displayAllCourses() {
        FileManager fileManager = new FileManager("Courses.txt");
        List<String> coursesData = fileManager.read();
        if (coursesData.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        System.out.println("ID | Name | Students | Instructor");
        System.out.println("---------------------------------");
        for (String data : coursesData) {
            String[] parts = data.split(",");
            if (parts.length >= 4) {
                System.out.println(parts[0] + " | " + parts[1] + " | " +
                        (parts.length > 2 ? parts[2] : "None") + " | " +
                        (parts.length > 3 ? parts[3] : "None"));
            }
        }
    }

    public void searchStudent(String studentId) {
        Student student = new Student("", "", 0, "", "", "", 0.0);
        student.searchStudent(studentId);
    }

    public void updateStudent(String studentId, String newName, int newAge, String newLevel, double newGPA) {
        Student student = new Student("", "", 0, "", "", "", 0.0);
        student.updateStudent(studentId, newName, newAge, newLevel, newGPA);
    }

    public void deleteStudent(String studentId) {
        Student student = new Student("", "", 0, "", "", "", 0.0);
        student.deleteStudent(studentId);
    }

    public void updateInstructor(String instructorId, String newName, int newAge) {
        Instructor instructor = new Instructor("", "", 0, "", "");
        instructor.updateInstructor(instructorId, newName, newAge);
    }

    public void deleteInstructor(String instructorId) {
        Instructor instructor = new Instructor("", "", 0, "", "");
        instructor.deleteInstructor(instructorId);
    }

    public void deleteCourse(String courseId) {
        Course course = new Course("", "");
        course.deleteCourse(courseId);
    }
    public String getName(){
        return username;
    }

}