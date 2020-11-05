package expensemanager.utils;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Utils {



    public void close() throws IOException {
        Backup.backup();
        System.exit(0);
    }


    // ======= MENU FUNCTIONS =======

    private Stage getStageFromEvent(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public void maximize(ActionEvent event) {
        Stage stage = getStageFromEvent(event);

        stage.setMaximized(!stage.isFullScreen());
    }

    public void minimize(ActionEvent event) {
        getStageFromEvent(event).setIconified(true);
    }



}
