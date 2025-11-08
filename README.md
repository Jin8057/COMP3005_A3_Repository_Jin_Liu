# COMP 3005 - Assignment3-Q1
## Database Interaction with PostgreSQL and Application Programming
#### Name: Jin Liu
#### Student Number: 101344075
#### Date: November 2025

### Description
This project demonstrates how to connect a **PostgreSQL** database with a **Java (JDBC)** application to perform the four basic **CRUD operations** (Create, Read, Update, Delete) on a table named `students`.

The assignment implements:
- `getAllStudents()`: Retrieves and displays all records from the students table.
- `addStudent()` : Inserts a new student record into the students table.
- `updateStudentEmail()`: Updates the email address for a student with the specified student_id.
- `deleteStudent()`: Deletes the record of the student with the specified student_id.

### Design Explanation
DatabaseConnection.java provides a reusable connect() method that establishes a JDBC connection to PostgreSQL.

Main.java defines four clearly separated functions, each performing one CRUD operation:

getAllStudents() → Reads and prints all student records.

addStudent() → Inserts a new student using parameterized SQL (PreparedStatement).

updateStudentEmail() → Updates a student’s email by ID.

deleteStudent() → Deletes a record by ID.

Each function prints clear output messages to the console for demonstration.

A helper function setupDatabase() resets the table with initial data before running the demo.

### Database Setup
1. Open **pgAdmin** and create a new database named `A3_Students`.
2. Run the provided SQL script `db_setup.sql` to create the table and insert initial data.

### Compile and Run Commands
Download this repository:  
git clone https://github.com/Jin8057/COMP3005_A3_Repository_Jin_Liu.git  
cd COMP3005_A3_Repository_Jin_Liu
#### Option-1
1. Open the project in IntelliJ IDEA.
2. Update the connection information (URL, USER, PASSWORD) in DatabaseConnection.java
3. Run Main in IntelliJ.
#### Option-2
1. Using Maven (Recommended if installed):

   mvn compile

   mvn exec:java -Dexec.mainClass=Main
2. If exec-maven-plugin is not configured, you can also compile and run manually:

   javac -d out src/main/java/*.java

   java -cp "out;lib/postgresql-42.7.8.jar" Main

### Video Demonstration
Video Link: https://youtu.be/gzVAfTf5e7g  
Please note: English is not my first language, so my pronunciation in the demo video may sound accented.
I tried my best to clearly explain each step and function. Thank you for your understanding!