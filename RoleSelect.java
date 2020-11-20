package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class RoleSelect {
    Scene scene;
    @FXML
    private RadioButton tutor;
    @FXML
    private RadioButton student;
    
    private Stage dialogStage;

    //method describing event to occur when the Submit button is pressed, redirecting to appropriate Login page
    public void GoToLoginPage(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        if (student.isSelected()) {
            scene = new Scene(FXMLLoader.load(getClass().getResource("StudentLogin.fxml")));
        }
        if (tutor.isSelected()) {
            scene = new Scene(FXMLLoader.load((getClass().getResource("TutorLogin.fxml"))));
        }
        dialogStage.setScene(scene);
        dialogStage.setTitle("Attendance Management System");
        dialogStage.show();
    }
}
