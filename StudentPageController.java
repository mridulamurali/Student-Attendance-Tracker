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
   //creating required objects
    public TableView<Subject> Table = new TableView<Subject>();
  
    public Label display;
  
    public TableColumn<Subject, String> subjectcol;

    public TableColumn<Subject, Integer> presentcol;
    
    public TableColumn<Subject, Integer> latecol;
   
    public TableColumn<Subject, Integer> excusedcol;
   
    public TableColumn<Subject, Integer> unexcusedcol;
  
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

 //default constructor
    public StudentPageController() throws SQLException {
    }
//function executed when display record button is clicked.
    public void DisplayRecords(ActionEvent actionEvent) {
        String sub = "";
        String mon = "";
//stores value selected from the combo box by a student 
        sub = subject.getValue();
        mon = month.getValue();
        String subjectname = setsubjectname(sub);
//create table for each subject chosen
        String tablename = sub;
//sql commands to read required details from database
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login", "root", "Mridula56");
            Statement s = con.createStatement();
            String getrec = "Select * from " + tablename + " where month = '" + mon + "' and rno='" + studrno + "'";
            ResultSet rs = s.executeQuery(getrec);
            while (rs.next()) {
                if (rs.getString(2).equals(studrno)) ;      //check whether given roll number exists(is in database)
                {
                    Subject o = new Subject(subjectname, mon, rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
                    list.add(o);    //adds the details of a particukar student as rows in the table displayed
                }
            }
            try {    //set properties to cells of each column of the table
                monthcol.setCellValueFactory(new PropertyValueFactory<>("Monthname"));
                subjectcol.setCellValueFactory(new PropertyValueFactory<>("Subname"));
                presentcol.setCellValueFactory(new PropertyValueFactory<>("Present"));
                latecol.setCellValueFactory(new PropertyValueFactory<>("Late"));
                excusedcol.setCellValueFactory(new PropertyValueFactory<>("Excused"));
                unexcusedcol.setCellValueFactory(new PropertyValueFactory<>("Unexcused"));
                Table.setItems(list);
                rs.close();
            }catch (Exception e){    //to catch if any exception occurs
                e.printStackTrace();
            }
        }   //to catch if any SQL error is thrown
         catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
//a function to return subject name correspoding to subject code chosen
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
//to set label for the frame.Ladel is the students roll number
    public void setLabelText(String rollnum) {
        studrno = rollnum;
        TitleLabel.setText(rollnum + " Attendance Viewer");
    }
//log out function to redirect back to role select frame
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
