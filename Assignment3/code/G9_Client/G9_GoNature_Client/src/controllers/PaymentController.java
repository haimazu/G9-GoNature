package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import orderData.Order;

public class PaymentController implements Initializable {

	@FXML
	private Label txtprice;

	@FXML
	private Label txtVisitoramountPrice;

	@FXML
	private Label txtdDiscount;

	@FXML
	private Label txtTotalPrice;

	@FXML
	private JFXRadioButton radioCash;

	@FXML
	private JFXRadioButton radioPayPal;

	@FXML
	private JFXRadioButton radioCreditCard;

	@FXML
	private Button btnContinue;

	@FXML
	private JFXCheckBox CheckBoxAgreed;

	private Order pymanetOrder;
	private AlertController alert;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.txtprice.setText(String.valueOf(pymanetOrder.getPrice()));
		this.txtTotalPrice.setText(String.valueOf(pymanetOrder.getTotalPrice()));
		this.txtdDiscount.setText(String.valueOf(1 - (pymanetOrder.getTotalPrice() / pymanetOrder.getTotalPrice())));
		this.txtVisitoramountPrice.setText(String.valueOf(pymanetOrder.getVisitorsNumber()));
	}

	/**
	 * 
	 */
	public PaymentController(Order orderPyment) {
		this.pymanetOrder = orderPyment;
	}

	public boolean checkEmpty() {
		if(!(radioCash.isSelected() || radioPayPal.isSelected() || radioPayPal.isSelected() )) {
			alert.setAlert("you need to choose payment method");
			return false;
		}
		if(!CheckBoxAgreed.isSelected()) {
			alert.setAlert("you need to aprove the terms");
			return false;
		}
		return true;				
	}
	
	
	
	

}
