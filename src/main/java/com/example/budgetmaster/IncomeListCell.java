package com.example.budgetmaster;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class IncomeListCell extends ListCell<String> {
    private ListView<String> incomeListView;

    public IncomeListCell(ListView<String> incomeListView) {
        this.incomeListView = incomeListView;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            Label incomeLabel = new Label(item);
            setGraphic(createCellContent(item, incomeLabel));
        }
    }

    private HBox createCellContent(String item, Label incomeLabel) {
        HBox hbox = new HBox();
        Label currentBudgetLabel = ((MainWindowController) incomeListView.getScene().getRoot().getProperties().get("controller")).getCurrentBudgetLabel();

        // Delete button (you can modify this part according to your requirements)
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            incomeListView.getItems().remove(item);
            String[] parts = item.split(" ");
            double incomeAmount = Double.parseDouble(parts[0]);
            double currentBudget = Double.parseDouble(currentBudgetLabel.getText());
            double newBudget = currentBudget - incomeAmount;
            ((MainWindowController) incomeListView.getScene().getRoot().getProperties().get("controller")).setCurrentBudget(newBudget);
        });

        hbox.getChildren().addAll(incomeLabel, deleteButton);
        return hbox;
    }
}
