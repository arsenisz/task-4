package database;

import model.Site;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * DAO (Data Access Object) для роботи з таблицею sites
 */
public class SiteDAO {
    
    /**
     * Отримати всі записи з таблиці
     */
    public static ObservableList<Site> getAllSites() {
        ObservableList<Site> siteList = FXCollections.observableArrayList();
        String query = "SELECT * FROM sites";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Site site = new Site(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getString("region"),
                    rs.getString("image")
                );
                siteList.add(site);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return siteList;
    }
    
    /**
     * Вставити новий запис
     */
    public static boolean insertSite(Site site) {
        String query = "INSERT INTO sites (name, latitude, longitude, region, image) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, site.getName());
            pstmt.setDouble(2, site.getLatitude());
            pstmt.setDouble(3, site.getLongitude());
            pstmt.setString(4, site.getRegion());
            pstmt.setString(5, site.getImage());
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Оновити запис
     */
    public static boolean updateSite(Site site) {
        String query = "UPDATE sites SET name = ?, latitude = ?, longitude = ?, region = ?, image = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, site.getName());
            pstmt.setDouble(2, site.getLatitude());
            pstmt.setDouble(3, site.getLongitude());
            pstmt.setString(4, site.getRegion());
            pstmt.setString(5, site.getImage());
            pstmt.setInt(6, site.getId());
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Видалити запис
     */
    public static boolean deleteSite(int id) {
        String query = "DELETE FROM sites WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
