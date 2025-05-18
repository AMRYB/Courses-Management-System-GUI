import java.util.*;

public class Instructor extends User {
    private List<String> coursesTaught;
    private FileManager fileManager;

    public Instructor(String id, String name, int age, String username, String password) {
        super(id, name, age, username, password);
        this.coursesTaught = new ArrayList<>();
        this.fileManager = new FileManager("Instructors.txt");
    }

    public void addCourse(String courseId) {
        coursesTaught.add(courseId);
        List<String> instructorsData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < instructorsData.size(); i++) {
            String[] parts = instructorsData.get(i).split(",");
            if (parts.length >= 5 && parts[0].equals(id)) {
                String updatedData = id + "," + name + "," + age + "," + username + "," + password + "," + courseId;
                instructorsData.set(i, updatedData);
                found = true;
                break;
            }
        }
        if (!found) {
            String instructorData = id + "," + name + "," + age + "," + username + "," + password + "," + courseId;
            instructorsData.add(instructorData);
        }
        fileManager.write(instructorsData);
    }

    public void displayCourses() {
        List<String> instructorsData = fileManager.read();
        for (String data : instructorsData) {
            String[] parts = data.split(",");
            System.out.println("Instructor: " + name);
            System.out.println("Courses Taught: " + parts[5]);
        }
        System.out.println("No courses found for instructor " + name);
    }

    public void updateInstructor(String instructorId, String newName, int newAge) {
        List<String> instructorsData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < instructorsData.size(); i++) {
            String[] parts = instructorsData.get(i).split(",");
            if (parts.length >= 5 && parts[0].equals(instructorId)) {
                String updatedData = instructorId + "," + newName + "," + newAge + "," + parts[3] + "," + parts[4] + "," + parts[5];
                instructorsData.set(i, updatedData);
                found = true;
                break;
            }
        }
        if (found) {
            fileManager.write(instructorsData);
            System.out.println("Instructor with ID " + instructorId + " updated successfully.");
        } else {
            System.out.println("Instructor with ID " + instructorId + " not found.");
        }
    }

    public void deleteInstructor(String instructorId) {
        List<String> instructorsData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < instructorsData.size(); i++) {
            String[] parts = instructorsData.get(i).split(",");
            if (parts.length >= 5 && parts[0].equals(instructorId)) {
                instructorsData.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            fileManager.write(instructorsData);
            System.out.println("Instructor with ID " + instructorId + " deleted successfully.");
        } else {
            System.out.println("Instructor with ID " + instructorId + " not found.");
        }

    }

    public void assignGrade(String studentId, String courseId, String grade) {
        Student student = new Student("", "", 0, "", "", "", 0.0);
        student.assignGrade(studentId,courseId,grade);
    }
    public String getName(){
        return username;
    }
}