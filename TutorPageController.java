package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class TutorPageController {
    //creating required objects
    public TableView<Student> table;

    public TableColumn<String, String> rnocol;

    public TableColumn<String, Integer> presentcol;

    public TableColumn<String, Integer> latecol;

    public TableColumn<String, Integer> excusedcol;

    public TableColumn<String, Integer> unexcusedcol;

    public ChoiceBox month; //shows a set of months and allows the user to select a single choice

    public ChoiceBox subject; //shows a set of subjects and allows the user to select a single choice

    Label TitleLabel;

    Stage dialogStage = new Stage();

    ObservableList<Student> list = FXCollections.observableArrayList();

    public void DisplayRecords(ActionEvent event) {
        table.getItems().clear();
        String sub = "";
        String mon = "";

        sub = (String) subject.getValue(); //subject and month are retrieved from event
        mon = (String) month.getValue();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");  //connection is established to MySQL database via JDBC
            Statement s = con.createStatement();    //creates a statement that needs to be passed
            String getrec = "Select * from " + sub + " where month = '" + mon + "'";
            ResultSet rs = s.executeQuery(getrec);  //query is executed using statement s and result is saved in ResultSet
            while (rs.next()) { //next() throws SQLException
                Student o = new Student(sub, mon, rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(1));
                list.add(o);    //adds the details of a particular student as rows in the table displayed
            }
            try {   //set properties to cells of each column of the table
                rnocol.setCellValueFactory(new PropertyValueFactory<>("Rollnum"));
                presentcol.setCellValueFactory(new PropertyValueFactory<>("Present"));
                latecol.setCellValueFactory(new PropertyValueFactory<>("Late"));
                excusedcol.setCellValueFactory(new PropertyValueFactory<>("Excused"));
                unexcusedcol.setCellValueFactory(new PropertyValueFactory<>("Unexcused"));
                table.setItems(list);
                rs.close();
            } catch (Exception e) { //Exception is handled
                e.printStackTrace();
            }
        } catch (SQLException throwables) { //SQLException thrown by next()
            throwables.printStackTrace();
        }
    }
    //to set label for the frame
    public void setLabelText() {
        TitleLabel.setText("Attendance Viewer For CSE G2");
    }

    public void EditRecords(ActionEvent event) {
        try {
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            Node node = (Node) event.getSource();
            dialogStage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EditRecordPage.fxml")));//opens EditRecordPage
            dialogStage.setScene(scene);
            dialogStage.setTitle("Attendance Management System");
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent event) throws IOException {
        Stage dialogStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("RoleSelect.fxml")); //redirects to RoleSelect page
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.setTitle("Attendance Management System: CSE-G2");
        dialogStage.show();
    }
}
