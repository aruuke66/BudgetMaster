package com.example.budgetmaster;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter;
import javafx.scene.control.Label;

public class ExpensesPopupController {
    @FXML
    private TextField amountTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private DatePicker datePicker;

    private ListView<String> expensesListView;
    private MainWindowController mainWindowController;

    public void setExpensesListView(ListView<String> expensesListView) {
        this.expensesListView = expensesListView;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void addButtonClicked() throws IOException {
        String amount = amountTextField.getText();
        String name = nameTextField.getText();
        LocalDate date = datePicker.getValue();

        if (amount.isEmpty() || name.isEmpty() || date == null) {
            return; // Don't add if any field is empty
        }

        // Extract numeric part of the amount string
        String[] amountParts = amount.split(" - ");
        if (amountParts.length < 1) {
            return; // Invalid amount format
        }

        String amountValue = amountParts[0];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = date.format(formatter);

        // Create the expense record
        String expenseRecord = amountValue + " KGS - " + name + " - " + formattedDate;

        if (expensesListView.getItems().contains(expenseRecord)) {
            return; // Don't add if the entry already exists
        }

        double expensesAmount = Double.parseDouble(amountValue);
        expensesListView.getItems().add(expenseRecord);
        saveRecordToFile(expenseRecord, "expenses.txt");

        clearFields();

        // Update current budget label
        Label currentBudgetLabel = mainWindowController.getCurrentBudgetLabel();
        String currentBudgetText = currentBudgetLabel.getText().replace("Current Budget: ", "");

        if (!currentBudgetText.isEmpty()) {
            double currentBudget = Double.parseDouble(currentBudgetText);
            currentBudget -= expensesAmount;
            currentBudgetLabel.setText(String.valueOf(currentBudget));
        }
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
