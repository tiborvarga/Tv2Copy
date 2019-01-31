package tv2copy;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class ViewFXMLController implements Initializable {
    
    @FXML
    private TextField clockText;
    
    @FXML
    private RadioButton r2Radio;
    
    @FXML
    private RadioButton r2nRadio;
    
    @FXML   
    private RadioButton r3Radio;
    
    @FXML
    private RadioButton r3nRadio;
    
    @FXML
    private RadioButton r4Radio;
    
    @FXML
    private RadioButton r4nRadio;
    
    @FXML Button submitButton;
    
    @FXML
    private void submitButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
