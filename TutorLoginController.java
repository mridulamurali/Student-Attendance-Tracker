package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class TutorLoginController {
    public Button login;
    Stage dialogStage = new Stage();
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");
    PreparedStatement preparedStatement1 = null;
    ResultSet resultSet1 = null;
    @FXML
    private TextField textemail;
    @FXML
    private PasswordField textPwd;
    public TutorLoginController() throws SQLException {
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
        String emailid = textemail.getText();
        String pwd = textPwd.getText();

        String reg = "SELECT * FROM tutorlogin WHERE BINARY emailid = ? and BINARY pwd = ?";

        try {
            preparedStatement1 = con.prepareStatement(reg);
            preparedStatement1.setString(1, emailid);
            preparedStatement1.setString(2, pwd);
            resultSet1 = preparedStatement1.executeQuery();

            if (!resultSet1.next()) {
                infoBox("Please enter correct Email ID and Password", null, "Failed");
            } else {
                infoBox("Login Successful", null, "Success");
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorProfilePage.fxml"));
                    Parent root;
                    root = loader.load();
                    TutorPageController controller = loader.getController();
                    controller.setLabelText();
                    dialogStage.setScene(new Scene(root));
                    dialogStage.setTitle("Attendance Viewer");
                    dialogStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
