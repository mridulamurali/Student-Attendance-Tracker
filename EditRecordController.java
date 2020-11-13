package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EditRecordController {
    @FXML
    public Label Title;
    @FXML
    public CheckBox pcheck;
    @FXML
    public CheckBox lcheck;
    @FXML
    public CheckBox echeck;
    @FXML
    public CheckBox ucheck;
    @FXML
    public SplitPane splitpane;
    @FXML
    public TextField presentval;
    @FXML
    public TextField lateval;
    @FXML
    public TextField excusedval;
    @FXML
    public TextField unexcusedval;
    @FXML
    public ChoiceBox subject;
    @FXML
    public TextField rollno;
    @FXML
    public ChoiceBox month;

    int p, l, e, u;
    String subname;
    String rollnum;
    String mon;
    int[] parameterflag = new int[4];

    public EditRecordController() {
    }

    public void showptext(ActionEvent event) {
        presentval.setVisible(pcheck.isSelected());
    }

    public void showltext(ActionEvent event) {
        lateval.setVisible(lcheck.isSelected());
    }

    public void showetext(ActionEvent event) {
        excusedval.setVisible(echeck.isSelected());
    }

    public void showutext(ActionEvent event) {
        unexcusedval.setVisible(ucheck.isSelected());
    }

    public void EditRecords(ActionEvent event) {
        rollnum = rollno.getText();
        subname = (String) subject.getValue();
        mon = (String) month.getValue();

        if (pcheck.isSelected()) {
            p = Integer.parseInt(presentval.getText());
            parameterflag[0] = 1;
        }
        if (lcheck.isSelected()) {
            l = Integer.parseInt(lateval.getText());
            parameterflag[1] = 1;
        }
        if (echeck.isSelected()) {
            e = Integer.parseInt(excusedval.getText());
            parameterflag[2] = 1;
        }
        if (ucheck.isSelected()) {
            u = Integer.parseInt(unexcusedval.getText());
            parameterflag[3] = 1;
        }

        String sql = "UPDATE " + subname + " set";
        sql = assignparameters(sql);
        sql = sql + " where rno = '" + rollnum + "' and month = '" + mon + "' ";
        System.out.println(sql);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");
            Statement s = con.createStatement();
            s.executeUpdate(sql);
            infoBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private String assignparameters(String sql) {
        if (parameterflag[0] == 1) {
            sql = sql + " P = " + p;
        }
        if (parameterflag[1] == 1) {
            sql = sql + " L = " + l;
        }
        if (parameterflag[2] == 1) {
            sql = sql + " E = " + e;
        }
        if (parameterflag[3] == 1) {
            sql = sql + " U = " + u;
        }
        return sql;
    }

    public static void infoBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Record has been altered");
        alert.setTitle("Record Altered");
        alert.showAndWait();
    }

    public void BackToHomePage(ActionEvent event) throws IOException {
        Stage dialogStage = new Stage();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorProfilePage.fxml"));
        Parent root;
        root = loader.load();
        TutorPageController controller = loader.getController();
        controller.setLabelText();
        dialogStage.setScene(new Scene(root));
        dialogStage.setTitle("Attendance Viewer");
        dialogStage.show();
    }
}
