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
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");
    PreparedStatement preparedStatement1 = null;
    ResultSet resultSet1 = null;
    @FXML
    private TextField textRno;
    @FXML
    private PasswordField textPwd;

    public StudentLoginController() throws SQLException {
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

    public void loginAction(ActionEvent event) {
        String rno = textRno.getText();
        String pwd = textPwd.getText();

        String reg = "SELECT * FROM studentprofile WHERE BINARY rno = ? and BINARY pwd = ?";

        try {
            preparedStatement1 = con.prepareStatement(reg);
            preparedStatement1.setString(1, rno);
            preparedStatement1.setString(2, pwd);
            resultSet1 = preparedStatement1.executeQuery();
            if (!resultSet1.next()) {
                infoBox("Please enter correct Roll Number and Password", null, "Failed");
            } else {
                infoBox("Login Successful", null, "Success");
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentProfilePage.fxml"));
                Parent root = loader.load();
                StudentPageController controller = loader.getController();
                controller.setLabelText(rno);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);
                dialogStage.setTitle("Attendance Viewer");
                dialogStage.show();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

}