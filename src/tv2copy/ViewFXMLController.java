package tv2copy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class ViewFXMLController implements Initializable {
    
    protected String clockNumber;
    protected String username;
    protected String password;
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
        fileGrabber();
        System.out.println(clockNumber);       
    }
    
    private void dataReader(){
        RadioButton selectedRadioButton = (RadioButton) channelGroup.getSelectedToggle();
        String toogleGroupValue = selectedRadioButton.getId();
        
        switch (toogleGroupValue){
            case "r2Radio": username = "adstream2spot"; password = "uQy63ogqNJ"; fileExt = ".mov"; break;
            case "r2nRadio": username = "adstream2spot"; password = "uQy63ogqNJ"; fileExt = ".mov"; break;
            case "r3Radio": username = "adstream3spot"; password = "Ab1Cd2Ef3"; fileExt = ".mpg"; break;
            case "r3nRadio": username = "adstream3nonspot"; password = "Ab1Cd2Ef3"; fileExt = ".mpg"; break;
            case "r4Radio": username = "adstream4spot"; password = "Wuser-5766"; fileExt = ".mov"; break;
            case "r4nRadio": username = "adstream4nonspot"; password = "Kescy-2147"; fileExt = ".mov"; break;
        } 
        
        clockNumber = "";
        
        String clockTemp = clockText.getText();
        for (int i=0; i<clockTemp.length(); i++){
            if (!clockTemp.substring(i, i+1).equals("/") && !clockTemp.substring(i, i+1).equals("\\") && !clockTemp.substring(i, i+1).equals("-") && !clockTemp.substring(i, i+1).equals("_"))
                clockNumber = clockNumber + clockTemp.substring(i, i+1);
            else{
                switch (toogleGroupValue){
                    case "r4Radio":                      
                        if (clockTemp.substring(i, i+1).equals("/") || clockTemp.substring(i, i+1).equals("\\") || clockTemp.substring(i, i+1).equals("-") || clockTemp.substring(i, i+1).equals("_"))
                            clockNumber = clockNumber + "-";
                        break;
                    case "r4nRadio":                      
                        if (clockTemp.substring(i, i+1).equals("/") || clockTemp.substring(i, i+1).equals("\\") || clockTemp.substring(i, i+1).equals("-") || clockTemp.substring(i, i+1).equals("_"))
                            clockNumber = clockNumber + "-";
                        break; 
                    default:
                        if (clockTemp.substring(i, i+1).equals("/") || clockTemp.substring(i, i+1).equals("\\") || clockTemp.substring(i, i+1).equals("-") || clockTemp.substring(i, i+1).equals("_"))
                            clockNumber = clockNumber + "_";
                        break;
                }
            }
        }     
    }
    
    public void fileGrabber(){
        String server = "video.tv2.hu";
        int port = 21;
        String user = username;
        String pass = password;
 
        FTPClient ftpClient = new FTPClient();
        
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: using retrieveFile(String, OutputStream)
            String remoteFile = clockNumber + fileExt;
            File downloadFile = new File("/Users/tiborvarga/Desktop/teszt/" + remoteFile);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
            boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
            outputStream.close();
 
            if (success) {
                System.out.println("File #1 has been downloaded successfully.");
            }
        }
        catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
