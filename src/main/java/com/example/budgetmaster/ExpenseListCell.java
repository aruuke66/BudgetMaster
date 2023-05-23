package com.example.budgetmaster;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class ExpenseListCell extends ListCell<String> {
    private ListView<String> expensesListView;

    public ExpenseListCell(ListView<String> expensesListView) {
        this.expensesListView = expensesListView;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            Label expenseLabel = new Label(item);
            setGraphic(createCellContent(item, expenseLabel));
        }
    }


    private HBox createCellContent(String item, Label expenseLabel) {
        HBox hbox = new HBox();
        Label currentBudgetLabel = ((MainWindowController) expensesListView.getScene().getRoot().getProperties().get("controller")).getCurrentBudgetLabel();

        // Delete button (you can modify this part according to your requirements)
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            expensesListView.getItems().remove(item);
            String[] parts = item.split(" ");
            double expenseAmount = Double.parseDouble(parts[0]);
            double currentBudget = Double.parseDouble(currentBudgetLabel.getText());
            double newBudget = currentBudget + expenseAmount;
            ((MainWindowController) expensesListView.getScene().getRoot().getProperties().get("controller")).setCurrentBudget(newBudget);
        });

        hbox.getChildren().addAll(expenseLabel, deleteButton);
        return hbox;
    }

}
