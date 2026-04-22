import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class UkraineSitesApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Завантаження FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SiteView.fxml"));
            Parent root = loader.load();
            
            // Створення сцени
            Scene scene = new Scene(root);
            
            // Налаштування вікна
            primaryStage.setTitle("Визначні місця України");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Помилка завантаження FXML файлу!");
        }
    }

    @Override
    public void stop() {
        // Закриття з'єднання з БД при закритті програми
        database.DatabaseConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
