package com.app.progettoispw202324;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button buttonLogin;

    private static String lololo;

    @FXML
    public void Login(ActionEvent event){
        String invio = "user:" + username.getText() + ",pass:" + password.getText()+ "\t" +lololo;
        System.out.println(invio);
    }

    public void setValue (String gogo){
        System.out.println("poiuytrewqasdfghjklkmnbvcxz" + gogo);
        lololo = gogo;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
