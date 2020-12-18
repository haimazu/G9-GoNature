package controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
public class PaymentController {

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
    
    
    
    
    
}
