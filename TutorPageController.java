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
    @FXML
    public TableView<Student> table;
    @FXML
    public TableColumn<String, String> rnocol;
    @FXML
    public TableColumn<String, Integer> presentcol;
    @FXML
    public TableColumn<String, Integer> latecol;
    @FXML
    public TableColumn<String, Integer> excusedcol;
    @FXML
    public TableColumn<String, Integer> unexcusedcol;
    @FXML
    public ChoiceBox month;
    @FXML
    public ChoiceBox subject;
    @FXML
    Label TitleLabel;

    Stage dialogStage = new Stage();

    ObservableList<Student> list = FXCollections.observableArrayList();

    public void DisplayRecords(ActionEvent event) {
        table.getItems().clear();
        String sub = "";
        String mon = "";

        sub = (String) subject.getValue();
        mon = (String) month.getValue();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");
            Statement s = con.createStatement();
            String getrec = "Select * from " + sub + " where month = '" + mon + "'";
            ResultSet rs = s.executeQuery(getrec);
            while (rs.next()) {
                Student o = new Student(sub, mon, rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(1));
                list.add(o);
            }
            try {
                rnocol.setCellValueFactory(new PropertyValueFactory<>("Rollnum"));
                presentcol.setCellValueFactory(new PropertyValueFactory<>("Present"));
                latecol.setCellValueFactory(new PropertyValueFactory<>("Late"));
                excusedcol.setCellValueFactory(new PropertyValueFactory<>("Excused"));
                unexcusedcol.setCellValueFactory(new PropertyValueFactory<>("Unexcused"));
                table.setItems(list);
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void setLabelText() {
        TitleLabel.setText("Attendance Viewer For CSE G2");
    }

    public void EditRecords(ActionEvent event) {
        try {
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            Node node = (Node) event.getSource();
            dialogStage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EditRecordPage.fxml")));
            dialogStage.setScene(scene);
            dialogStage.setTitle("Attendance Management System");
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent event) throws IOException {
        Stage dialogStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("RoleSelect.fxml"));
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.setTitle("Attendance Management System: CSE-G2");
        dialogStage.show();
    }
}
