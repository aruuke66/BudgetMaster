package com.example.budgetmaster;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.TextFormatter;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

public class IncomePopupController {
    @FXML
    private TextField amountTextField;

    @FXML
    private TextField nameTextField;

    private ListView<String> incomeListView;

    @FXML
    private DatePicker datePicker;

    public void setIncomeListView(ListView<String> incomeListView) {
        this.incomeListView = incomeListView;
    }

    public void addButtonClicked() throws IOException {
        String amount = amountTextField.getText();
        String name = nameTextField.getText();
        LocalDate date = datePicker.getValue();

        if (amount.isEmpty() || name.isEmpty() || date == null) {
            return; // Don't add if any field is empty
        }

        String incomeRecord = amount + " - " + name + " - " + date.toString();
        incomeListView.getItems().add(incomeRecord);
        saveRecordToFile(incomeRecord, "income.txt");

        clearFields();
    }

    private void saveRecordToFile(String record, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(record);
            writer.newLine();
        }
    }

    private void clearFields() {
        amountTextField.clear();
        nameTextField.clear();
    }

    public void initialize() {
        UnaryOperator<TextFormatter.Change> numberFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };
        amountTextField.setTextFormatter(new TextFormatter<>(numberFilter));

        datePicker.setValue(LocalDate.now());
    }
}
