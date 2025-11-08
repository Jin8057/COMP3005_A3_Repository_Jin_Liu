import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.connect();
        if (conn == null) {
            System.out.println("Failed to establish connection.");
            return;
        }

        try {
            setupDatabase(conn);

            getAllStudents(conn);
            addStudent(conn, "Jin", "Liu", "jin.liu@example.com", "2023-09-01");
            updateStudentEmail(conn, 1, "john.new@example.com");
            deleteStudent(conn, 2);
            getAllStudents(conn);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 1. Retrieves and displays all records from the students table
    public static void getAllStudents(Connection conn) throws SQLException {
        String query = "SELECT * FROM students ORDER BY student_id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n All Students:");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s | %s%n",
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("enrollment_date"));
            }
        }
    }

    // 2. Inserts a new student record into the students table
    public static void addStudent(Connection conn, String first, String last, String email, String date) throws SQLException {
        String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, first);
            pstmt.setString(2, last);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(date));
            pstmt.executeUpdate();
            System.out.println("Added student: " + first + " " + last);
        }
    }

    // 3. Updates the email address for a student with the specified student_id
    public static void updateStudentEmail(Connection conn, int id, String newEmail) throws SQLException {
        String updateSQL = "UPDATE students SET email = ? WHERE student_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Updated email for student ID " + id);
            } else {
                System.out.println("No student found with ID " + id);
            }
        }
    }

    // 4. Deletes the record of the student with the specified student_id
    public static void deleteStudent(Connection conn, int id) throws SQLException {
        String deleteSQL = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Deleted student ID " + id);
            } else {
                System.out.println("No student found with ID " + id);
            }
        }
    }

    // Initialize the database every time running the code
    public static void setupDatabase(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("DROP TABLE IF EXISTS students;");

        stmt.executeUpdate("""
                CREATE TABLE students (
                student_id SERIAL PRIMARY KEY,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                enrollment_date DATE
            );
        """);

        stmt.executeUpdate("""
            INSERT INTO students (first_name, last_name, email, enrollment_date)
            VALUES
            ('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
            ('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
            ('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');
        """);

        System.out.println("Database table recreated with initial data.");
    }
}
