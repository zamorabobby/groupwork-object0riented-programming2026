package com.mycompany.studentregistrationform;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.regex.Pattern;

public class StudentRegistrationForm extends Application {

    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField confirmEmailField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final PasswordField confirmPasswordField = new PasswordField();
    private final ComboBox<String> yearCombo = new ComboBox<>();
    private final ComboBox<String> monthCombo = new ComboBox<>();
    private final ComboBox<String> dayCombo = new ComboBox<>();
    private final ToggleGroup genderToggle = new ToggleGroup();
    private final RadioButton maleRadio = new RadioButton("Male");
    private final RadioButton femaleRadio = new RadioButton("Female");
    private final ChoiceBox<String> deptChoice = new ChoiceBox<>();
    private final TextArea displayArea = new TextArea();
    private final Label errorLabel = new Label();

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Top title (blue background like screenshot)
        Label titleLabel = new Label("New Student Registration Form");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPrefWidth(600);
        titleLabel.setStyle("-fx-background-color: #4682B4; -fx-padding: 10;");
        root.setTop(titleLabel);

        // Main form grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(20));
        grid.setVgap(12);
        grid.setHgap(15);

        // Labels and fields
        grid.add(new Label("Student First Name"), 0, 0);
        firstNameField.setPromptText("");
        firstNameField.setPrefWidth(300);
        grid.add(firstNameField, 1, 0);

        grid.add(new Label("Student Last Name"), 0, 1);
        lastNameField.setPrefWidth(300);
        grid.add(lastNameField, 1, 1);

        grid.add(new Label("Email Address"), 0, 2);
        emailField.setPrefWidth(300);
        grid.add(emailField, 1, 2);

        grid.add(new Label("Confirm Email Address"), 0, 3);
        confirmEmailField.setPrefWidth(300);
        grid.add(confirmEmailField, 1, 3);

        grid.add(new Label("Password"), 0, 4);
        passwordField.setPrefWidth(300);
        grid.add(passwordField, 1, 4);

        grid.add(new Label("Confirm Password"), 0, 5);
        confirmPasswordField.setPrefWidth(300);
        grid.add(confirmPasswordField, 1, 5);

        grid.add(new Label("Date of Birth"), 0, 6);
        yearCombo.setPromptText("SelectYear");
        monthCombo.setPromptText("SelectMonth");
        dayCombo.setPromptText("SelectDay");
        HBox dobBox = new HBox(10, yearCombo, monthCombo, dayCombo);
        grid.add(dobBox, 1, 6);

        grid.add(new Label("Gender"), 0, 7);
        maleRadio.setToggleGroup(genderToggle);
        femaleRadio.setToggleGroup(genderToggle);
        HBox genderBox = new HBox(30, maleRadio, femaleRadio);
        grid.add(genderBox, 1, 7);

        grid.add(new Label("Department"), 0, 8);
        deptChoice.getItems().addAll(
            "Civil",
            "Computer Science and Engineering",
            "Electrical",
            "Electronics and Communication",
            "Mechanical"
        );
        deptChoice.setPrefWidth(300);
        grid.add(deptChoice, 1, 8);

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox buttons = new HBox(40, submitButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);
        grid.add(buttons, 0, 9, 2, 1);

        Label dataLabel = new Label("Your Data is Below:");
        dataLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        grid.add(dataLabel, 0, 10, 2, 1);

        displayArea.setEditable(false);
        displayArea.setPrefHeight(80);
        displayArea.setPrefWidth(450);
        grid.add(displayArea, 0, 11, 2, 1);

        errorLabel.setTextFill(Color.RED);
        grid.add(errorLabel, 0, 12, 2, 1);

        // Populate DOB combos
        for (int y = 1966; y <= 2010; y++) yearCombo.getItems().add(String.valueOf(y));
        for (int m = 1; m <= 12; m++) monthCombo.getItems().add(String.format("%02d", m));
        for (int d = 1; d <= 31; d++) dayCombo.getItems().add(String.format("%02d", d));
        yearCombo.setValue("2000");
        monthCombo.setValue("01");
        dayCombo.setValue("01");

        yearCombo.setOnAction(e -> updateDays());
        monthCombo.setOnAction(e -> updateDays());
        cancelButton.setOnAction(e -> clearForm());
        submitButton.setOnAction(e -> submitForm());

        root.setCenter(grid);

        Scene scene = new Scene(root, 600, 750);
        stage.setTitle("New Student Registration Form");
        stage.setScene(scene);
        stage.show();
    }

    private void updateDays() {
        dayCombo.getItems().clear();
        String yStr = yearCombo.getValue();
        String mStr = monthCombo.getValue();
        if (yStr != null && mStr != null) {
            int y = Integer.parseInt(yStr);
            int m = Integer.parseInt(mStr);
            YearMonth ym = YearMonth.of(y, m);
            for (int d = 1; d <= ym.lengthOfMonth(); d++) {
                dayCombo.getItems().add(String.format("%02d", d));
            }
            dayCombo.setValue("01");
        }
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        confirmEmailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        yearCombo.setValue("2000");
        monthCombo.setValue("01");
        dayCombo.setValue("01");
        genderToggle.selectToggle(null);
        deptChoice.setValue(null);
        displayArea.clear();
        errorLabel.setText("");
    }

    private void submitForm() {
        StringBuilder errors = new StringBuilder();
        String fn = firstNameField.getText().trim();
        String ln = lastNameField.getText().trim();
        String em = emailField.getText().trim();
        String cem = confirmEmailField.getText().trim();
        String pw = passwordField.getText();
        String cpw = confirmPasswordField.getText();
        String y = yearCombo.getValue();
        String m = monthCombo.getValue();
        String d = dayCombo.getValue();
        RadioButton selGender = (RadioButton) genderToggle.getSelectedToggle();
        String gender = selGender != null ? (selGender == maleRadio ? "Male" : "Female") : null;
        String dept = deptChoice.getValue();

        if (fn.isEmpty() || ln.isEmpty()) errors.append("Student names required.\n");
        if (em.isEmpty() || !Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", em)) errors.append("Valid email address required.\n");
        if (!em.equals(cem)) errors.append("Email addresses do not match.\n");
        if (pw.length() < 8 || pw.length() > 20 || !pw.matches(".*[a-zA-Z].*") || !pw.matches(".*\\d.*")) errors.append("Password: 8-20 characters, at least 1 letter and 1 digit.\n");
        if (!pw.equals(cpw)) errors.append("Passwords do not match.\n");
        if (y == null || m == null || d == null) errors.append("Complete date of birth.\n");
        else {
            LocalDate dob = LocalDate.of(Integer.parseInt(y), Integer.parseInt(m), Integer.parseInt(d));
            LocalDate now = LocalDate.now();
            int age = now.getYear() - dob.getYear();
            if (dob.isAfter(now.minusYears(age))) age--;
            if (age < 16 || age > 60) errors.append("Age must be between 16 and 60.\n");
        }
        if (gender == null) errors.append("Select gender.\n");
        if (dept == null) errors.append("Select department.\n");

        if (errors.length() > 0) {
            errorLabel.setText(errors.toString());
            Alert alert = new Alert(Alert.AlertType.ERROR, errors.toString(), ButtonType.OK);
            alert.show();
            return;
        }

        String csvFile = "students.csv";
        int counter = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) counter++;
        } catch (IOException ignored) {}
        String id = String.format("2026-%05d", counter);
        String fullName = ln + " " + fn;
        String dobStr = y + "-" + m + "-" + d;
        String emailFormatted = em.toLowerCase().replace(" ", "_");
        String record = id + " | " + fullName + " | " + gender + " | " + dept + " | " + dobStr + " | " + emailFormatted;

        displayArea.setText(record);

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile, true))) {
            writer.println(record);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Save failed: " + e.getMessage(), ButtonType.OK);
            alert.show();
            return;
        }

        Alert success = new Alert(Alert.AlertType.INFORMATION, "Registration saved successfully!", ButtonType.OK);
        success.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}