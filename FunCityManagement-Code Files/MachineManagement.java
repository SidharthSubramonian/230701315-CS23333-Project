import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineManagement {

    // Method to add a machine to the database
    public static void addMachine(String location, int ticketCount, boolean dispensesTickets, String status) {
        String sql = "INSERT INTO Machine (location, ticket_count, dispenses_tickets, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, location);
            pstmt.setInt(2, ticketCount);
            pstmt.setBoolean(3, dispensesTickets);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            System.out.println("Machine added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding machine: " + e.getMessage());
        }
    }

    // Method to view all machines in the database
    public static void viewMachines() {
        String sql = "SELECT * FROM Machine";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Machine ID: " + rs.getInt("machine_id"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Ticket Count: " + rs.getInt("ticket_count"));
                System.out.println("Dispenses Tickets: " + rs.getBoolean("dispenses_tickets"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("----------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing machines: " + e.getMessage());
        }
    }

    // Method to delete a machine by its ID
    public static void deleteMachine(int machineId) {
        String sql = "DELETE FROM Machine WHERE machine_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, machineId);
            pstmt.executeUpdate();
            System.out.println("Machine deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting machine: " + e.getMessage());
        }
    }

    // Method to get all machines from the database
    public static Object[][] getMachines() {
        String sql = "SELECT * FROM Machine";
        List<Object[]> machineList = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Object[] machine = new Object[]{
                    rs.getInt("machine_id"),
                    rs.getString("location"),
                    rs.getInt("ticket_count"),
                    rs.getBoolean("dispenses_tickets"),
                    rs.getString("status")
                };
                machineList.add(machine);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving machines: " + e.getMessage());
        }
        return machineList.toArray(new Object[0][]); // Convert list to 2D array
    }
}
