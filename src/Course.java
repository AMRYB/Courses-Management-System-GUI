import java.util.*;

public class Course {
    private String courseId;
    private String courseName;
    private List<String> studentIds;
    private String instructorId;
    private FileManager fileManager;
    private FileManager page;

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentIds = new ArrayList<>();
        this.instructorId = "";
        this.fileManager = new FileManager("Courses.txt");
        page = new FileManager("page.txt");
    }


    public void setInstructor(String instructorId) {
        this.instructorId = instructorId;
        List<String> coursesData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < coursesData.size(); i++) {
            String[] parts = coursesData.get(i).split(",");
            if (parts.length >= 2 && parts[0].equals(courseId)) {
                StringBuilder studentIdsStr = new StringBuilder();
                if (parts.length > 3) {
                    for (int j = 3; j < parts.length; j++) {
                        studentIdsStr.append(parts[j]);
                        if (j < parts.length - 1) {
                            studentIdsStr.append(",");
                        }
                    }
                }
                String updatedData = courseId + "," + courseName + "," + instructorId + "," + studentIdsStr.toString();
                coursesData.set(i, updatedData);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("course not found");
            return;
        }
        fileManager.write(coursesData);
    }


    public void makePage(String courseId, String room, String branch, String startDate, String endDate, String price , String daysOfCourse) {
        this.instructorId = instructorId;
        List<String> coursesData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < coursesData.size(); i++) {
            String[] parts = coursesData.get(i).split(",");
            if (parts.length >= 2 && parts[0].equals(courseId)) {
                StringBuilder studentIdsStr = new StringBuilder();
                if (parts.length > 3) {
                    for (int j = 3; j < parts.length; j++) {
                        studentIdsStr.append(parts[j]);
                        if (j < parts.length - 1) {
                            studentIdsStr.append(",");
                        }
                    }
                }
                String updatedData = courseId + "," + courseName + "," + instructorId + "," + studentIdsStr.toString() + "," + room  + "," + branch  + "," + startDate + "," + endDate + "," + price + "," + daysOfCourse;
                coursesData.set(i, updatedData);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("course not found");
            return;
        }
        page.write(coursesData);
    }




    public void addStudent(String studentId) {
        List<String> coursesData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < coursesData.size(); i++) {
            String[] parts = coursesData.get(i).split(",");
            if (parts.length >= 2 && parts[0].equals(courseId)) {
                StringBuilder students = new StringBuilder();
                if (parts.length > 3) {
                    for (int j = 3; j < parts.length; j++) {
                        students.append(parts[j]).append(",");
                    }
                }
                students.append(studentId);
                String updatedData = courseId + "," + courseName + "," + parts[2] + "," + students.toString();
                coursesData.set(i, updatedData);
                studentIds.add(studentId);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("course not found");
            return;
        }
        fileManager.write(coursesData);
    }




    public void displayCourseDetails() {
        List<String> coursesData = fileManager.read();
        for (String data : coursesData) {
            String[] parts = data.split(",");
            System.out.println("Course ID: " + parts[0]);
            System.out.println("Course Name: " + parts[1]);
            System.out.println("Students: " + (parts.length > 2 ? parts[2] : "None"));
            System.out.println("Instructor ID: " + (parts.length > 3 ? parts[3] : "Not assigned"));
        }
    }

    public void deleteCourse(String courseId) {
        List<String> coursesData = fileManager.read();
        boolean found = false;
        for (int i = 0; i < coursesData.size(); i++) {
            String[] parts = coursesData.get(i).split(",");
            if (parts.length >= 2 && parts[0].equals(courseId)) {
                coursesData.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            fileManager.write(coursesData);
            System.out.println("Course with ID " + courseId + " deleted successfully.");
        } else {
            System.out.println("Course with ID " + courseId + " not found.");
        }
    }
}