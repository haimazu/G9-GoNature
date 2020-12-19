package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import orderData.Order;


public class ConfirmationController implements Initializable{
	
	private Order finalOrder;

	
    /**
	 * @param finalOrder
	 */
	public ConfirmationController(Order finalOrder) {
		super();
		this.finalOrder = finalOrder;
	}

	@FXML
    private Pane pnConfirmation;
    

    @FXML
    private Label txtOrderNum;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.txtOrderNum.setText(String.valueOf(this.finalOrder.getOrderNumber()));
		
	}
}
