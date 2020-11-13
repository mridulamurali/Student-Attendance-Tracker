package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class StudentPageController {
    @FXML
    public TableView<Subject> Table = new TableView<Subject>();
    @FXML
    public Label display;
    @FXML
    public TableColumn<Subject, String> subjectcol;
    @FXML
    public TableColumn<Subject, Integer> presentcol;
    @FXML
    public TableColumn<Subject, Integer> latecol;
    @FXML
    public TableColumn<Subject, Integer> excusedcol;
    @FXML
    public TableColumn<Subject, Integer> unexcusedcol;
    @FXML
    public TableColumn<Subject, String> monthcol;

    String studrno;

    @FXML
    Label TitleLabel;

    @FXML
    ChoiceBox<String> subject = new ChoiceBox<String>();

    @FXML
    ChoiceBox<String> month = new ChoiceBox<String>();
    Stage dialogStage = new Stage();
    Scene scene;

    ObservableList<Subject> list = FXCollections.observableArrayList();


    public StudentPageController() throws SQLException {
    }

    public void DisplayRecords(ActionEvent actionEvent) {
        String sub = "";
        String mon = "";

        sub = subject.getValue();
        mon = month.getValue();
        String subjectname = setsubjectname(sub);

        String tablename = sub;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");
            Statement s = con.createStatement();
            String getrec = "Select * from " + tablename + " where month = '" + mon + "' and rno='" + studrno + "'";
            ResultSet rs = s.executeQuery(getrec);
            while (rs.next()) {
                if (rs.getString(2).equals(studrno)) ;
                {
                    Subject o = new Subject(subjectname, mon, rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
                    list.add(o);
                }
            }
            try {
                monthcol.setCellValueFactory(new PropertyValueFactory<>("Monthname"));
                subjectcol.setCellValueFactory(new PropertyValueFactory<>("Subname"));
                presentcol.setCellValueFactory(new PropertyValueFactory<>("Present"));
                latecol.setCellValueFactory(new PropertyValueFactory<>("Late"));
                excusedcol.setCellValueFactory(new PropertyValueFactory<>("Excused"));
                unexcusedcol.setCellValueFactory(new PropertyValueFactory<>("Unexcused"));
                Table.setItems(list);
                rs.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
         catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public String setsubjectname(String sname){
        String sn = new String();
        if (sname.equals("19Z301")) {
            sn = "Linear Algebra";
        } else if (sname.equals("19Z302")) {
            sn = "Data Structures";
        } else if (sname.equals("19Z303")) {
            sn = "Computer Architecture";
        } else if (sname.equals("19Z304")) {
            sn = "Discrete Structures";
        } else if (sname.equals("19Z305")) {
            sn = "Object Oriented Programming";
        } else if (sname.equals("19Z306")) {
            sn = "Economics for Engineers";
        } else if (sname.equals("19Z310")) {
            sn = "Data Structures Lab";
        } else if (sname.equals("19Z311")) {
            sn = "OOP Lab";
        } else if (sname.equals("19K312")) {
            sn = "Environmental Studies";
        }
        return sn;
    }

    public void setLabelText(String rollnum) {
        studrno = rollnum;
        TitleLabel.setText(rollnum + " Attendance Viewer");
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
