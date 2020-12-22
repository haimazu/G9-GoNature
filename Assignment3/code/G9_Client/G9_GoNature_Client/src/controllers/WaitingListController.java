package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WaitingListController {

    @FXML
    private Pane pnVerify;

    @FXML
    private Button btnCheck;

    @FXML
    private Button btnHere;
    
    
    @FXML
    void check(ActionEvent event) {

    }

    @FXML
    void here(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		Pane root = FXMLLoader.load(getClass().getResource("/gui/WaitingListConfirmation.fxml"));
		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		
		Stage stage2 = (Stage) btnHere.getScene().getWindow();
	    stage2.close();
    }

}
