import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameManagement {

    // Method to add a game to the database
    public static void addGame(String name, String location, String status) {
        String sql = "INSERT INTO Game (name, location, status) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setString(3, status);
            pstmt.executeUpdate();
            System.out.println("Game added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding game: " + e.getMessage());
        }
    }

    // Method to view all games in the database
    public static void viewGames() {
        String sql = "SELECT * FROM Game";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Game ID: " + rs.getInt("game_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("----------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing games: " + e.getMessage());
        }
    }

    // Method to get all games from the database
    public static Object[][] getGames() {
        String sql = "SELECT * FROM Game";
        List<Object[]> gameList = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Object[] game = new Object[] {
                    rs.getInt("game_id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getString("status")
                };
                gameList.add(game);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving games: " + e.getMessage());
        }
        return gameList.toArray(new Object[0][]); // Convert list to 2D array
    }
}
