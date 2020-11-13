package sample;

public class Subject extends Parameters {

    String Subname;
    String Monthname;

    Subject(String sname, String mon, int p, int l, int e, int u) {
        Subname = sname;
        Monthname = mon;
        Present = p;
        Late = l;
        Excused = e;
        Unexcused = u;
    }

    public int getPresent() {
        return Present;
    }

    public void setPresent(int present) {
        Present = present;
    }

    public int getLate() {
        return Late;
    }

    public void setLate(int late) {
        Late = late;
    }

    public int getExcused() {
        return Excused;
    }

    public void setExcused(int excused) {
        Excused = excused;
    }

    public int getUnexcused() {
        return Unexcused;
    }

    public void setUnexcused(int unexcused) {
        Unexcused = unexcused;
    }

    public String getSubname() {
        return Subname;
    }

    public void setSubname(String subname) {
        Subname = subname;
    }

    public String getMonthname() {
        return Monthname;
    }

    public void setMonthname(String monthname) {
        Monthname = monthname;
    }

}
