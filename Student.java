package sample;

public class Student extends Subject {
    String Rollnum;

    Student(String sname, String mon, int p, int l, int e, int u, String Rollnum) {
        super(sname, mon, p, l, e, u);
        this.Rollnum = Rollnum;
    }

    public String getRollnum() {
        return Rollnum;
    }

    public void setRollnum(String rollnum) {
        Rollnum = rollnum;
    }

}
