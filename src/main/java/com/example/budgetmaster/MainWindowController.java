package com.example.budgetmaster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private ListView<String> expensesListView;

    @FXML
    private ListView<String> incomeListView;

    @FXML
    private Label currentBudgetLabel;

    public Label getCurrentBudgetLabel() {
        return currentBudgetLabel;
    }

    public void setCurrentBudgetLabel(double budget) {
        currentBudgetLabel.setText(String.valueOf(budget));
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
}
