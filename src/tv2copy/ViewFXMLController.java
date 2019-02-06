package tv2copy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class ViewFXMLController implements Initializable {
    
    protected ArrayList<String> clockNumberAL = new ArrayList<>();
    protected String clockNumber;
    protected String server = "video.tv2.hu";
    protected String username;
    protected String password;
    protected String fileExt;
    protected String dest;
    protected String spotDest = "uqUEjaw27";
    protected String nonspotDest = "5wMDuJs7LE";
    protected String todayDate;
    
    @FXML
    private TextArea clockText;
    
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
    
    @FXML
    private Button submitButton;
    
    @FXML
    private ToggleGroup channelGroup;
    
    @FXML
    private Pane basePane;
    
    @FXML
    private Pane alertPane;
    
    @FXML
    private Label alertLabel;
    
    @FXML
    private void handleAlertButton(ActionEvent event) {
        basePane.setDisable(false);
        basePane.setOpacity(1);
        alertPane.setVisible(false);
        alertLabel.setText("");
    }
    
    @FXML
    private void submitButtonAction(ActionEvent event) {
        dataReader();
        fileGrabber();
        
        
    }
    
    private void alert(String text){
        basePane.setDisable(true);
        basePane.setOpacity(0.3);
        alertPane.setVisible(true);
        alertLabel.setText(text);
    }
    
    private void dataReader(){
        DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
	Date date = new Date();
	todayDate = dateFormat.format(date);
        
        RadioButton selectedRadioButton = null;
        selectedRadioButton = (RadioButton) channelGroup.getSelectedToggle();
        if (selectedRadioButton == null){
            alert("Please select a channel!");
            return;
        }
        String toogleGroupValue = selectedRadioButton.getId();
        
        switch (toogleGroupValue){
            case "r2Radio": username = "adstream2spot"; password = "uQy63ogqNJ"; fileExt = ".mov"; dest = spotDest; break;
            case "r2nRadio": username = "adstream2spot"; password = "uQy63ogqNJ"; fileExt = ".mov"; dest = nonspotDest; break;
            case "r3Radio": username = "adstream3spot"; password = "Ab1Cd2Ef3"; fileExt = ".mpg"; dest = spotDest; break;
            case "r3nRadio": username = "adstream3nonspot"; password = "Ab1Cd2Ef3"; fileExt = ".mpg"; dest = nonspotDest; break;
            case "r4Radio": username = "adstream4spot"; password = "Wuser-5766"; fileExt = ".mov"; dest = spotDest; break;
            case "r4nRadio": username = "adstream4nonspot"; password = "Kescy-2147"; fileExt = ".mov"; dest = nonspotDest; break;
        }
        
        clockNumberAL.clear();
        
        ArrayList<String> clockTempAL = new ArrayList<>();
        for (String line : clockText.getText().split("\\n"))
            clockTempAL.add(line); 

        for (int j=0; j<clockTempAL.size(); j++){
            clockNumber = "";
            String clockTemp = clockTempAL.get(j);
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
            
            if (clockNumber.equals("")){
            alert("Please insert a clock number!");
            return;
            }
            
            clockNumberAL.add(clockNumber);
        }
        System.out.println(clockNumberAL);
        
    }
    
    public void fileGrabber(){
        new File("I:\\TV2\\" + todayDate).mkdir();
        
        int port = 21;       
 
        FTPClient ftpClient = new FTPClient();
        
        try {
            ftpClient.connect(server, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: using retrieveFile(String, OutputStream)
            String remoteFile;
            for (int i=0; i<clockNumberAL.size(); i++){
                remoteFile = "";
                remoteFile = clockNumberAL.get(i) + fileExt;
                File downloadFile = new File("I:\\TV2\\" + todayDate + "\\" + remoteFile);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
                boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
                outputStream.close();
 
                if (success) {
                    System.out.println("File #" + (i+1) + " has been downloaded successfully.");
                }
            }
        }
        catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            alert("FTP connection error");
            return;
        }
        finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                alert("Unknown FTP error");
                return;
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
