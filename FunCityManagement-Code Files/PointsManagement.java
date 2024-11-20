import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PointsManagement {

    // Method to add points for a customer in the database
    public static void addPoints(int customerId, int pointsEarned) {
        String sql = "INSERT INTO Points (customer_id, points_earned) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, pointsEarned);
            pstmt.executeUpdate();
            System.out.println("Points added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding points: " + e.getMessage());
        }
    }

    // Method to view all points records in the database
    public static void viewPoints() {
        String sql = "SELECT * FROM Points";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Point ID: " + rs.getInt("point_id"));
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Points Earned: " + rs.getInt("points_earned"));
                System.out.println("Earned Time: " + rs.getTimestamp("earned_time"));
                System.out.println("----------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing points: " + e.getMessage());
        }
    }

    // Method to delete points by their ID
    public static void deletePoints(int pointId) {
        String sql = "DELETE FROM Points WHERE point_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pointId);
            pstmt.executeUpdate();
            System.out.println("Points record deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting points: " + e.getMessage());
        }
    }

    // Method to get all points records from the database
    public static Object[][] getPoints() {
        String sql = "SELECT * FROM Points";
        List<Object[]> pointsList = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Object[] points = new Object[]{
                    rs.getInt("point_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("points_earned"),
                    rs.getTimestamp("earned_time")
                };
                pointsList.add(points);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving points records: " + e.getMessage());
        }
        return pointsList.toArray(new Object[0][]); // Convert list to 2D array
    }
}
