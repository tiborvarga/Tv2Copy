package tv2copy;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ViewFXMLController implements Initializable {
    
    protected String clockNumber;
    protected String urlName;
    protected String fileExt;
    
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
    
    @FXML ToggleGroup channelGroup;
    
    @FXML
    private void submitButtonAction(ActionEvent event) {
        dataReader();
        System.out.println(clockNumber + " " + urlName);       
    }
    
    private void dataReader(){
        clockNumber = "";
        urlName = "";
        
        String clockTemp = clockText.getText();
        for (int i=0; i<clockTemp.length(); i++){
            if (clockTemp.substring(i, i+1).equals("/") || clockTemp.substring(i, i+1).equals("\\"))
                clockNumber = clockNumber + "_";
            else
                clockNumber = clockNumber + clockTemp.substring(i, i+1);
        } 
        
        RadioButton selectedRadioButton = (RadioButton) channelGroup.getSelectedToggle();
        String toogleGroupValue = selectedRadioButton.getId();
        
        switch (toogleGroupValue){
            case "r2Radio": urlName = "2"; fileExt = "2"; break;
            case "r2nRadio": urlName = "2n"; fileExt = "2n"; break;
            case "r3Radio": urlName = "3"; fileExt = "3"; break;
            case "r3nRadio": urlName = "3n"; fileExt = "3n"; break;
            case "r4Radio": urlName = "4"; fileExt = "4"; break;
            case "r4nRadio": urlName = "4n"; fileExt = "4n"; break;
        }       
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
