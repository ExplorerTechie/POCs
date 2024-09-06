import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemoryAndConnectionLeakExample {

    // Static list to hold references, leading to a memory leak
    private static List<Object> memoryLeakList = new ArrayList<>();

    // Simulate a method that leaks database connections
    public void createConnectionLeak() {
        for (int i = 0; i < 100; i++) {
            try {
                // Simulate acquiring a database connection
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "user", "password");
                // Intentionally not closing the connection
                // connection.close(); // This line is commented out to create a leak
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Simulate a method that creates memory leaks
    public void createMemoryLeak() {
        for (int i = 0; i < 100000; i++) {
            // Adding new objects to the static list
            memoryLeakList.add(new Object()); // This will prevent garbage collection
        }
    }

    // Simulate performance issues by using a slow operation
    public void simulatePerformanceIssue() {
        for (int i = 0; i < 1000000; i++) {
            // A slow operation that consumes CPU cycles
            double result = Math.sin(i) * Math.cos(i);
        }
    }

    public static void main(String[] args) {
        MemoryAndConnectionLeakExample example = new MemoryAndConnectionLeakExample();
        
        // Create memory leak
        example.createMemoryLeak();
        
        // Create connection leak
        example.createConnectionLeak();
        
        // Simulate performance issue
        example.simulatePerformanceIssue();
        
        // Keep the application running to observe memory usage
        try {
            Thread.sleep(100000); // Sleep for 100 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
