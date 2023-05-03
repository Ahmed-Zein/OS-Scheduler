package UI;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

class LabeledTxtField {
    Label lbl;
    TextField textField;

    LabeledTxtField(String label) {
        lbl = new Label(label);
        textField = new TextField();
        textField.setMaxSize(150, 20);
        // Create a TextFormatter that only allows integers
        StringConverter<Integer> converter = new IntegerStringConverter();
        TextFormatter<Integer> formatter = new TextFormatter<>(converter, 0, c ->
                c.getControlNewText().matches("\\d*") ? c : null);

        // Set the TextFormatter on the TextField
        textField.setTextFormatter(formatter);
    }

    VBox build() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(lbl, textField);
        return vBox;
    }

    int data() {
        return Integer.parseInt(textField.getText());
    }

    void clear() {
        textField.setText("0");
    }

}
