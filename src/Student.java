import java.util.*;

public class Student extends User {
    private String level;
    private double GPA;
    public List<String> enrolledCourses;
    private FileManager fileManager;
    private FileManager surveyFile;

    public Student(String id, String name, int age, String username, String password, String level, double GPA) {
        super(id, name, age, username, password);
        this.level = level;
        this.GPA = GPA;
        this.enrolledCourses = new ArrayList<>();
        this.fileManager = new FileManager("Students.txt");
        surveyFile = new FileManager("Survey.txt");
    }

    public void enrollInCourse(String courseId) {
        List<String> studentsData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < studentsData.size(); i++) {
            String[] parts = studentsData.get(i).split(",");
            if (parts.length >= 7 && parts[0].equals(id)) {
                StringBuilder courses = new StringBuilder();
                if (parts.length > 7) {
                    for (int j = 7; j < parts.length; j++) {
                        courses.append(parts[j]).append(",");
                    }
                }
                courses.append(courseId).append(",undefined");
                String studentData = id + "," + name + "," + age + "," + username + "," + password + "," + level + "," + GPA + "," + courses.toString();
                studentsData.set(i, studentData);
                enrolledCourses.add(courseId);
                enrolledCourses.add("undefined");
                found = true;
                break;
            }
        }
        if (!found) {
            enrolledCourses.add(courseId);
            enrolledCourses.add("undefined");
            String studentData = id + "," + name + "," + age + "," + username + "," + password + "," + level + "," + GPA + "," + String.join(",", enrolledCourses);
            studentsData.add(studentData);
        }
        fileManager.write(studentsData);
    }

    public void addStudent() {
        String studentData = id + "," + name + "," + age + "," + username + "," + password + "," + level + "," + GPA;
        fileManager.write(studentData);
    }

    public void makeSurvey(String survey , String courseID){
        List<String> surveyData = surveyFile.read();
        String data = id + "," + courseID + "," + survey;
        surveyFile.write(data);
    }

    public void displayAllStudents() {
        List<String> studentsData = fileManager.read();
        if (studentsData.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("ID | Name | Age | Level | GPA | Enrolled Courses");
        System.out.println("-------------------------------------------------");
        for (String data : studentsData) {
            String[] parts = data.split(",");
            if (parts.length >= 7) {
                System.out.println(parts[0] + " | " + parts[1] + " | " + parts[2] + " | " +
                        parts[5] + " | " + parts[6] + " | " + (parts.length > 7 ? parts[7] : "None"));
            }
        }
    }

    public void searchStudent(String studentId) {
        List<String> studentsData = fileManager.read();
        boolean found = false;
        for (String data : studentsData) {
            String[] parts = data.split(",");
            if (parts.length >= 7 && parts[0].equals(studentId)) {
                System.out.println("Student Found:");
                System.out.println("ID: " + parts[0]);
                System.out.println("Name: " + parts[1]);
                System.out.println("Age: " + parts[2]);
                System.out.println("Level: " + parts[5]);
                System.out.println("GPA: " + parts[6]);
                System.out.println("Enrolled Courses: " + (parts.length > 7 ? parts[7] : "None"));
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }

    public void getEnrolledCourses(String course){
        List<String> studentsData = fileManager.read();
        if(course.equals("none")){
            for(String data : studentsData) {
                String[] parts = data.split(",");
                if (parts.length >= 7 && parts[0].equals(id)) {
                    System.out.println("Enrolled Courses:");
                    for (int i = 7; i < parts.length; i+=2) {
                        System.out.println(parts[i]);
                    }
                }
            }
        }
        else{
            for(String data : studentsData) {
                String[] parts = data.split(",");
                if (parts.length >= 7 && parts[0].equals(id)) {
                    System.out.println("Course Grades:");
                    for (int i = 7; i < parts.length; i+=2) {
                        if(parts[i].equals(course)){
                            System.out.println(parts[i] + " " + parts[i + 1]);
                        }
                    }
                }
            }
        }
    }
    public void updateStudent(String studentId, String newName, int newAge, String newLevel, double newGPA) {
        List<String> studentsData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < studentsData.size(); i++) {
            String[] parts = studentsData.get(i).split(",");
            if (parts.length >= 7 && parts[0].equals(studentId)) {
                StringBuilder courses = new StringBuilder();
                for (int j = 7; j < parts.length; j++) {
                    courses.append(parts[j]);
                    if (j < parts.length - 1) {
                        courses.append(",");
                    }
                }
                String updatedData = studentId + "," + newName + "," + newAge + "," + parts[3] + "," + parts[4] + "," + newLevel + "," + newGPA + "," + courses.toString();
                studentsData.set(i, updatedData);
                found = true;
                break;
            }
        }
        if (found) {
            fileManager.write(studentsData);
            System.out.println("Student with ID " + studentId + " updated successfully.");
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }

    public void deleteStudent(String studentId) {
        List<String> studentsData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < studentsData.size(); i++) {
            String[] parts = studentsData.get(i).split(",");
            if (parts.length >= 7 && parts[0].equals(studentId)) {
                studentsData.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            fileManager.write(studentsData);
            System.out.println("Student with ID " + studentId + " deleted successfully.");
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }

    }

    public void assignGrade(String studentId, String courseId, String grade) {
        List<String> studentsData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < studentsData.size(); i++) {
            String[] parts = studentsData.get(i).split(",");
            if (parts.length >= 7 && parts[0].equals(studentId)) {
                StringBuilder updatedCourses = new StringBuilder();
                boolean courseFound = false;
                for (int j = 7; j < parts.length; j += 2) {
                    if (j < parts.length - 1 && parts[j].equals(courseId)) {
                        updatedCourses.append(courseId).append(",").append(grade);
                        courseFound = true;
                    } else if (j < parts.length - 1) {
                        updatedCourses.append(parts[j]).append(",").append(parts[j + 1]);
                    }
                    if (j + 2 < parts.length) {
                        updatedCourses.append(",");
                    }
                }
                if (!courseFound) {
                    System.out.println("Course " + courseId + " not found for student " + studentId);
                    return;
                }
                String updatedData = parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + "," + parts[4] + "," + parts[5] + "," + parts[6] + "," + updatedCourses.toString();
                studentsData.set(i, updatedData);
                found = true;
                // Update enrolledCourses in memory
                for (int k = 0; k < enrolledCourses.size(); k += 2) {
                    if (enrolledCourses.get(k).equals(courseId)) {
                        enrolledCourses.set(k + 1, grade);
                        break;
                    }
                }
                break;
            }
        }
        if (found) {
            fileManager.write(studentsData);
            System.out.println("Grade " + grade + " assigned to course " + courseId + " for student " + studentId);
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }
    public String getName(){
        return username;
    }
}


