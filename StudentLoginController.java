package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StudentLoginController implements Initializable {

    Stage dialogStage = new Stage();
    Scene scene;
	//connection to mysql database is set via JDBC
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");
    PreparedStatement preparedStatement1 = null;
    ResultSet resultSet1 = null;
    @FXML
    private TextField textRno;//allows user to enter a line of unformatted text
    @FXML
    private PasswordField textPwd;//password given by the user is masked

    public StudentLoginController() throws SQLException {//might throw SQL Exception as databases are involved
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        ButtonType buttonTypeOk = new ButtonType("Ok");//button Ok is created
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);//button Cancel is created
        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(false);
        alert.showAndWait();
    }

    public void loginAction(ActionEvent event) {
        String rno = textRno.getText();//rollNo is retrieved from event
        String pwd = textPwd.getText();//password is retrieved from event

        //sql statement where ? is wildcard in java
		String reg = "SELECT * FROM studentprofile WHERE BINARY rno = ? and BINARY pwd = ?";

        try {
            preparedStatement1 = con.prepareStatement(reg);
			//rollNo and password replace the wildcard in String reg
            preparedStatement1.setString(1, rno);
            preparedStatement1.setString(2, pwd);
			//takes sql statement as parameter and returns preparedStatement object containing precompiled sql
            resultSet1 = preparedStatement1.executeQuery();//result set after query is stored throws SQL Exception
            if (!resultSet1.next()) {//next() is a method in ResultSet that is used to move row by row
                infoBox("Please enter correct Roll Number and Password", null, "Failed");//if login fails
            } else {
                infoBox("Login Successful", null, "Success");//if login is successful
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentProfilePage.fxml"));
                Parent root = loader.load();//throws IOException
                StudentPageController controller = loader.getController();//getController() is a method of loader that establishes communication between scenes
                controller.setLabelText(rno);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);
                dialogStage.setTitle("Attendance Viewer");
                dialogStage.show();

            }
        } catch (IOException e) {//IOException is caught
            e.printStackTrace();
        } catch (Exception e) {//SQL Exception is caught
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {//a method of Initializable interface is implemented


    }

}

