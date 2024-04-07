package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Locale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private Button button;

    @FXML
    private ComboBox<String> comboBoxLanguage;

    @FXML
    private TextField inputEmail;

    @FXML
    private TextField inputFirstName;

    @FXML
    private TextField inputLastName;

    @FXML
    private Label labelChooseLanguage;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelLastName;

    private ResourceBundle bundle;

    public void initialize() {
        @SuppressWarnings("deprecation")
        Locale locale_en = new Locale("en", "US");
        @SuppressWarnings("deprecation")
        Locale locale_fa = new Locale("fa", "IR");
        @SuppressWarnings("deprecation")
        Locale locale_ja = new Locale("ja", "JP");

        comboBoxLanguage.getItems().addAll("English", "Farsi", "Japanese");
        comboBoxLanguage.setValue("English");

        comboBoxLanguage.setOnAction(event -> {
            String selectedLanguage = comboBoxLanguage.getValue().toString();
            if (selectedLanguage.equals("Farsi")) {
                bundle = ResourceBundle.getBundle("messages", locale_fa);
            } else if (selectedLanguage.equals("Japanese")) {
                bundle = ResourceBundle.getBundle("messages", locale_ja);
            } else {
                bundle = ResourceBundle.getBundle("messages", locale_en);
            }
            updateUI();
        });

        bundle = ResourceBundle.getBundle("messages", locale_en);
        updateUI();

        button.setOnAction(e -> saveData());
    }

    private void updateUI() {
        labelFirstName.setText(bundle.getString("label.firstName"));
        labelLastName.setText(bundle.getString("label.lastName"));
        labelEmail.setText(bundle.getString("label.email"));

        inputFirstName.setPromptText(bundle.getString("label.firstName"));
        inputLastName.setPromptText(bundle.getString("label.lastName"));
        inputEmail.setPromptText(bundle.getString("label.email"));

        labelChooseLanguage.setText(bundle.getString("label.chooseLanguage"));
        button.setText(bundle.getString("button.save"));
    }

    private void saveData() {
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String email = inputEmail.getText();
        String selectedLanguage = comboBoxLanguage.getValue().toString();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/table_localization";
            Connection conn = DriverManager.getConnection(jdbcUrl, "root", "root");

            String tableName;
            switch (selectedLanguage) {
                case "Farsi":
                    tableName = "employee_fa";
                    break;
                case "Japanese":
                    tableName = "employee_ja";
                    break;
                default:
                    tableName = "employee_en";
            }

            String sql = "INSERT INTO " + tableName + " (first_name, last_name, email) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.executeUpdate();
            System.out.println(bundle.getString("message.saved"));
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("message.saved"));
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
