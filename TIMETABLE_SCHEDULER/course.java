package TIMETABLE_SCHEDULER;

import java.util.ArrayList;
import java.util.Locale;

public class course
{
    // name, credit(get,set), name of professor,size
    private String name_course;
    private Integer credits;
    private final ArrayList<String> course_profs = new ArrayList<String>();
    void setName_course(String s)
    {
        this.name_course=s;
    }
    void setCredits(int c)
    {
        this.credits=c;
    }
    String getName_course()
    {
        return this.name_course;
    }
    int getCredits()
    {
        return this.credits;
    }
    void add_courseprofs(String s)
    {
        this.course_profs.add(s);       //add a professor to course
    }
    void remove_courseprofs(String s){
        this.course_profs.removeIf(sk-> sk.equals(s));
    }      //remove a professor from course
    boolean search_prof(String s)
    {

        return this.course_profs.indexOf(s) != -1;      //if teacher is teaching that course
    }
    int size_profs_array()
    {
        return this.course_profs.size();
    }
}
