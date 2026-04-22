package controller;

import database.SiteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import model.Site;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Контролер для управління інтерфейсом
 */
public class SiteController implements Initializable {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField latitudeField;
    @FXML private TextField longitudeField;
    @FXML private ComboBox<String> regionComboBox;
    @FXML private TextField imageField;
    
    @FXML private TableView<Site> siteTable;
    @FXML private TableColumn<Site, Integer> idColumn;
    @FXML private TableColumn<Site, String> nameColumn;
    @FXML private TableColumn<Site, Double> latitudeColumn;
    @FXML private TableColumn<Site, Double> longitudeColumn;
    @FXML private TableColumn<Site, String> regionColumn;
    @FXML private TableColumn<Site, String> photoColumn;
    
    @FXML private Label statusLabel;
    
    private ObservableList<Site> siteList;

    /**
     * Ініціалізація контролера
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Ініціалізація регіонів
        ObservableList<String> regions = FXCollections.observableArrayList(
            "Київ",
            "Київська область",
            "Львівська область",
            "Харківська область",
            "Одеська область",
            "Дніпропетровська область",
            "Донецька область",
            "Запорізька область",
            "Івано-Франківська область",
            "Вінницька область",
            "Волинська область",
            "Житомирська область",
            "Закарпатська область",
            "Луганська область",
            "Миколаївська область",
            "Полтавська область",
            "Рівненська область",
            "Сумська область",
            "Тернопільська область",
            "Херсонська область",
            "Хмельницька область",
            "Черкаська область",
            "Чернівецька область",
            "Чернігівська область",
            "Крим"
        );
        regionComboBox.setItems(regions);
        
        // Налаштування колонок таблиці
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        photoColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        
        // Завантаження даних
        loadTableData();
        
        // Обробник вибору рядка в таблиці
        siteTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showSiteDetails(newValue)
        );
        
        statusLabel.setText("Готово. Завантажено " + siteList.size() + " записів.");
    }

    /**
     * Завантаження даних у таблицю
     */
    private void loadTableData() {
        siteList = SiteDAO.getAllSites();
        siteTable.setItems(siteList);
    }

    /**
     * Відображення деталей вибраного місця
     */
    private void showSiteDetails(Site site) {
        if (site != null) {
            idField.setText(String.valueOf(site.getId()));
            nameField.setText(site.getName());
            latitudeField.setText(String.valueOf(site.getLatitude()));
            longitudeField.setText(String.valueOf(site.getLongitude()));
            regionComboBox.setValue(site.getRegion());
            imageField.setText(site.getImage());
        }
    }

    /**
     * Вставити новий запис
     */
    @FXML
    private void handleInsert() {
        try {
            Site site = new Site();
            site.setName(nameField.getText());
            site.setLatitude(Double.parseDouble(latitudeField.getText()));
            site.setLongitude(Double.parseDouble(longitudeField.getText()));
            site.setRegion(regionComboBox.getValue());
            site.setImage(imageField.getText());
            
            if (SiteDAO.insertSite(site)) {
                statusLabel.setText("Запис успішно додано!");
                loadTableData();
                handleClear();
                showAlert(Alert.AlertType.INFORMATION, "Успіх", "Запис успішно додано!");
            } else {
                statusLabel.setText("Помилка додавання запису!");
                showAlert(Alert.AlertType.ERROR, "Помилка", "Не вдалося додати запис!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Невірний формат координат!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Заповніть всі поля!");
        }
    }

    /**
     * Оновити запис
     */
    @FXML
    private void handleUpdate() {
        try {
            Site site = new Site();
            site.setId(Integer.parseInt(idField.getText()));
            site.setName(nameField.getText());
            site.setLatitude(Double.parseDouble(latitudeField.getText()));
            site.setLongitude(Double.parseDouble(longitudeField.getText()));
            site.setRegion(regionComboBox.getValue());
            site.setImage(imageField.getText());
            
            if (SiteDAO.updateSite(site)) {
                statusLabel.setText("Запис успішно оновлено!");
                loadTableData();
                handleClear();
                showAlert(Alert.AlertType.INFORMATION, "Успіх", "Запис успішно оновлено!");
            } else {
                statusLabel.setText("Помилка оновлення запису!");
                showAlert(Alert.AlertType.ERROR, "Помилка", "Не вдалося оновити запис!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Невірний формат даних!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Оберіть запис для оновлення!");
        }
    }

    /**
     * Очистити поля
     */
    @FXML
    private void handleClear() {
        idField.clear();
        nameField.clear();
        latitudeField.clear();
        longitudeField.clear();
        regionComboBox.setValue(null);
        imageField.clear();
        siteTable.getSelectionModel().clearSelection();
        statusLabel.setText("Поля очищено");
    }

    /**
     * Видалити запис
     */
    @FXML
    private void handleDelete() {
        try {
            int id = Integer.parseInt(idField.getText());
            
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Підтвердження");
            confirmation.setHeaderText("Видалити запис?");
            confirmation.setContentText("Ви впевнені, що хочете видалити цей запис?");
            
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (SiteDAO.deleteSite(id)) {
                    statusLabel.setText("Запис успішно видалено!");
                    loadTableData();
                    handleClear();
                    showAlert(Alert.AlertType.INFORMATION, "Успіх", "Запис успішно видалено!");
                } else {
                    statusLabel.setText("Помилка видалення запису!");
                    showAlert(Alert.AlertType.ERROR, "Помилка", "Не вдалося видалити запис!");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Оберіть запис для видалення!");
        }
    }

    /**
     * Вибрати зображення
     */
    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Оберіть зображення");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        
        File selectedFile = fileChooser.showOpenDialog(imageField.getScene().getWindow());
        if (selectedFile != null) {
            imageField.setText(selectedFile.getName());
        }
    }

    /**
     * Друк (вивід у консоль)
     */
    @FXML
    private void handlePrint() {
        System.out.println("=== Список визначних місць України ===");
        for (Site site : siteList) {
            System.out.println(site);
        }
        statusLabel.setText("Дані виведено в консоль");
        showAlert(Alert.AlertType.INFORMATION, "Друк", "Дані виведено в консоль");
    }

    /**
     * Показати діалогове вікно
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
