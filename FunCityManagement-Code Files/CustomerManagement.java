import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerManagement {

    // Method to add a customer to the database
    public static void addCustomer(String name, String email, int points, double balance) {
        String sql = "INSERT INTO Customer (name, email, points, balance) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, points);
            pstmt.setDouble(4, balance);
            pstmt.executeUpdate();
            System.out.println("Customer added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    // Method to view all customers in the database
    public static void viewCustomers() {
        String sql = "SELECT * FROM Customer";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Points: " + rs.getInt("points"));
                System.out.println("Balance: " + rs.getDouble("balance"));
                System.out.println("----------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing customers: " + e.getMessage());
        }
    }

    // Method to delete a customer by their ID
    public static void deleteCustomer(int customerId) {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
            System.out.println("Customer deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    // Method to get all customers from the database
    public static Object[][] getCustomers() {
        String sql = "SELECT * FROM Customer";
        List<Object[]> customerList = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Object[] customer = new Object[]{
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("points"),
                    rs.getDouble("balance")
                };
                customerList.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
        return customerList.toArray(new Object[0][]); // Convert list to 2D array
    }
}
