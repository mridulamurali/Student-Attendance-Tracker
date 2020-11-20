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
    //create fields for components described in .fxml file
    public CheckBox pcheck;

    public Label Title;

    public CheckBox lcheck;

    public CheckBox echeck;

    public CheckBox ucheck;

    public SplitPane splitpane;

    public TextField presentval;

    public TextField lateval;

    public TextField excusedval;

    public TextField unexcusedval;

    public ChoiceBox subject;

    public TextField rollno;

    public ChoiceBox month;

    //variables to store altered values
    int p, l, e, u;
    String subname;
    String rollnum;
    String mon;
    int[] parameterflag = new int[4];

    public EditRecordController() {
    }

    /*set visibility of textboxes that take in new value of parameter only if the checkbox for its
    * corresponding parameter is checked*/
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

    //method that is called when Edit Records button is pressed
    public void EditRecords(ActionEvent event) {
        //get values of roll number, subject name, and month of the record to be altered
        rollnum = rollno.getText();
        subname = (String) subject.getValue();
        mon = (String) month.getValue();

        //based on the parameters that have been selected to have a new value, get the new value and store it
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

        //update command to MySQL table is made based on the values taken from the user
        String sql = "UPDATE " + subname + " set";
        sql = assignparameters(sql);
        sql = sql + " where rno = '" + rollnum + "' and month = '" + mon + "' ";


        //establish connection with the database and send the string prepared as argument to executeUpdate method to alter table
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");
            Statement s = con.createStatement();
            s.executeUpdate(sql);
            //display information that table was altered successfully
            infoBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //method to append to command based on selected parameters
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

    //method defining the inforBox displayed upon successful alteration of records
    public static void infoBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Record has been altered");
        alert.setTitle("Record Altered");
        alert.showAndWait();
    }

    //method to describe action of the back button that leads to the tutor's home page
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
