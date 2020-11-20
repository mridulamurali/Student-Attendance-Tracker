package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TutorLoginController {
    public Button login;
    Stage dialogStage = new Stage();
    Scene scene;
    //connection to mysql database is set via JDBC
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");
    PreparedStatement preparedStatement1 = null;
    ResultSet resultSet1 = null;
    @FXML
    private TextField textemail; //allows user to enter a line of unformatted text
    @FXML
    private PasswordField textPwd; //input given by user is masked

    public TutorLoginController() throws SQLException { //might throw SQL Exception as we are working with databases
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        ButtonType buttonTypeOk = new ButtonType("Ok");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(false);
        alert.showAndWait();
    }

    public void tutorloginAction(ActionEvent event) {
        String emailid = textemail.getText();//emailid and password are retrieved from event
        String pwd = textPwd.getText();

        //sql statement where ? is wildcard in java
        String reg = "SELECT * FROM tutorlogin WHERE BINARY emailid = ? and BINARY pwd = ?";

        int flag = 0;
        try {
            preparedStatement1 = con.prepareStatement(reg);
            // emailid and password replace the wildcard in String reg
            preparedStatement1.setString(1, emailid);
            preparedStatement1.setString(2, pwd);
            //takes sql statement as parameter and returns preparedStatement object containing precompiled sql
            resultSet1 = preparedStatement1.executeQuery();//result set after query is stored throws SQL Exception

            if (!resultSet1.next()) { //next() is a method in ResultSet that moves row by row
                infoBox("Please enter correct Email ID and Password", null, "Failed");//when login fails
            } else {
                infoBox("Login Successful", null, "Success");//login is successful
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorProfilePage.fxml"));
                    Parent root;
                    root = loader.load(); // throws IOException
                    TutorPageController controller = loader.getController();//getController() is a method of loader that establishes communication between scenes
                    controller.setLabelText();
                    dialogStage.setScene(new Scene(root));
                    dialogStage.setTitle("Attendance Viewer");
                    dialogStage.show();
                } catch (IOException e) {//IOException is caught
                    e.printStackTrace();
                }
            }
        } catch (Exception e) { //SQL Exception is caught
            e.printStackTrace();
        }
    }



}
