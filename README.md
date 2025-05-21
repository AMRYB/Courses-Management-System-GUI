## 📌 About the Project

This project is a **Course Management System** built with Java. It allows different types of users (*Admins, Instructors, Students*) to interact with courses through a **graphical user interface (GUI)**.

The application supports user-role-based features such as:

* Creating, assigning, and registering for courses.
* Managing users and their course interactions.
* Saving/loading data persistently via file I/O.

---

## ⚙ System Workflow

### 👤 User Roles

1. **Admin**:

   * Adds/removes users.
   * Creates and manages courses.
2. **Instructor**:

   * Views and manages courses they teach.
   * Assigns course-related info.
3. **Student**:

   * Views available courses.
   * Registers for and views their enrolled courses.

### 🪟 GUI Navigation

* **MainGUI.java**: Main interface window.
* **CoursePageDialog.java**: Dialog to manage or view a course.
* **MainApp.java**: Initializes the app and opens the main GUI.

---

## 🗃 Project Files Overview

| File Name               | Description                                            |
| ----------------------- | ------------------------------------------------------ |
| `MainApp.java`          | Starts the application and initializes the UI.         |
| `MainGUI.java`          | Main graphical interface.                              |
| `CoursePageDialog.java` | Dialog window to interact with course details.         |
| `Course.java`           | Class representing a course entity.                    |
| `Instructor.java`       | Instructor user, manages the courses they teach.       |
| `Student.java`          | Student user, manages enrolled courses.                |
| `Admin.java`            | Admin user, manages all system users and courses.      |
| `User.java`             | Base class for all users (admin, instructor, student). |
| `FileManager.java`      | Handles file read/write operations to persist data.    |
| `Main.java`             | Secondary entry point or for testing.                  |

---

## 🧪 Sample Actions & Output

### Example 1: Student Registers for a Course

* GUI shows available courses.
* Student selects and registers.
* Data is saved using `FileManager`.

### Example 2: Admin Creates a New Instructor

* Admin fills instructor info.
* System adds new instructor to the list.
* Instructor can then be assigned to courses.

---

## 🧠 Key Features

* ✅ Multiple user roles (Admin, Instructor, Student)
* 🧩 Modular OOP design
* 💾 Persistent data storage via `FileManager`
* 🖼 GUI-based interaction (Java Swing)
* 📄 Well-structured code for educational use

---

## 🛠 How to Run the Project

1. Make sure you have *Java JDK (8 or above)* installed.
2. Compile all `.java` files:

```bash
javac *.java
```

3. Run the main application:

```bash
java MainApp
```
---

## 👨‍💻 Team Members

*Project Leader:* Amr Yasser Badr

*Team Members:*

* Youssef Mahmoud Younes
* Sama Saeed El-Fishawy
* Yasmin Ramadan Abdelwahid
* Mariam Mohamed El-Sayed
* Youssef El-Sayed "Arthur"