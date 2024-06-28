package TIMETABLE_SCHEDULER;

public class timetablee
{
    String[][] a = new String[6][8];//timetable for 6 days and 8 classes
    private String batch_name;

    void setBatch_name(String s)
    {
        this.batch_name = s;
    }
    String getBatch_name()
    {
        return this.batch_name;
    }
}
