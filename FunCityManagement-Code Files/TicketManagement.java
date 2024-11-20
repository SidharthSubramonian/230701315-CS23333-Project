import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketManagement {

    // Method to add a ticket to the database
    public static void addTicket(int customerId, int machineId) {
        String sql = "INSERT INTO Ticket (customer_id, machine_id) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, machineId);
            pstmt.executeUpdate();
            System.out.println("Ticket added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding ticket: " + e.getMessage());
        }
    }

    // Method to view all tickets in the database
    public static void viewTickets() {
        String sql = "SELECT * FROM Ticket";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Ticket ID: " + rs.getInt("ticket_id"));
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Machine ID: " + rs.getInt("machine_id"));
                System.out.println("Issue Time: " + rs.getTimestamp("issue_time"));
                System.out.println("----------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing tickets: " + e.getMessage());
        }
    }

    // Method to delete a ticket by its ID
    public static void deleteTicket(int ticketId) {
        String sql = "DELETE FROM Ticket WHERE ticket_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            pstmt.executeUpdate();
            System.out.println("Ticket deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting ticket: " + e.getMessage());
        }
    }

    // Method to get all tickets from the database
    public static Object[][] getTickets() {
        String sql = "SELECT * FROM Ticket";
        List<Object[]> ticketList = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Object[] ticket = new Object[]{
                    rs.getInt("ticket_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("machine_id"),
                    rs.getTimestamp("issue_time")
                };
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving tickets: " + e.getMessage());
        }
        return ticketList.toArray(new Object[0][]); // Convert list to 2D array
    }
}
