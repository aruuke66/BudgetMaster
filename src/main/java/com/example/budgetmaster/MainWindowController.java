package com.example.budgetmaster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private ListView<String> expensesListView;

    @FXML
    private ListView<String> incomeListView;

    @FXML
    private Label currentBudgetLabel;

    private double currentBudget;

    public Label getCurrentBudgetLabel() {
        return currentBudgetLabel;
    }

    public void setCurrentBudgetLabel(double budget) {
        currentBudgetLabel.setText(String.valueOf(budget));
    }

    public void setCurrentBudget(double budget) {
        this.currentBudget = budget;
        setCurrentBudgetLabel(budget);
    }

    @FXML
    private void openExpensesPopup() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExpensesPopup.fxml"));
        Parent root = loader.load();
        ExpensesPopupController expensesPopupController = loader.getController();
        expensesPopupController.setExpensesListView(expensesListView);
        expensesPopupController.setMainWindowController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Expenses");
        stage.show();
    }

    @FXML
    private void openIncomePopup() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IncomePopup.fxml"));
        Parent root = loader.load();
        IncomePopupController incomePopupController = loader.getController();
        incomePopupController.setIncomeListView(incomeListView);
        incomePopupController.setMainWindowController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Income");
        stage.show();
    }

    @FXML
    private void initialize() {
        setCurrentBudget(0.0);

        // Set custom cell factory for expensesListView
        expensesListView.setCellFactory(listView -> new ExpenseListCell(expensesListView));
        incomeListView.setCellFactory(listView -> new IncomeListCell(incomeListView));


        // Set the controller property in the root node
        Parent root = currentBudgetLabel.getParent();
        root.getProperties().put("controller", this);
    }

    public void updateCurrentBudget(double amount) {
        currentBudget += amount;
        setCurrentBudget(currentBudget);
    }
}
