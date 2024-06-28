package TIMETABLE_MANAGEMENT;



import java.util.ArrayList;


public class professor extends timetablee
{
    private final ArrayList<String> courses = new ArrayList<String>();
    ArrayList<String> batches_under_profs = new ArrayList<>();

    private String prof_name;
    boolean addCourses(String s)    //add course which teacher is teaching
    {
        return this.courses.add(s);
    }
    ArrayList<String> returnCourses()
    {

        return this.courses;
    }
    String getProf_name()
    {
        return this.prof_name;
    }
    void setProf_name(String s)
    {
        this.prof_name = s;
    }


    void Extract_old_batches()      //add batches teacher is teaching
    {
        for(int i=0;i<=5;i++)
        {
            for (int j=0;j<=7;j++) {
                try {
                    String str = this.a[i][j];
                    if (str != null) {
                        this.batches_under_profs.add(str.substring(str.indexOf("(") + 1, str.indexOf(")")));
                    }
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    //e.printStackTrace();
                }

            }
        }
    }
    void removebatch(String k)      //in case of delete batch
    {
        for(int i=0;i<=5;i++)
        {
            for(int j =0;j<=7;j++)
            {

                try {
                    String str = this.a[i][j];
                    if (str != null)
                    {
                        if (str.length() >= 1) {
                            if (str.substring(str.indexOf("(") + 1, str.indexOf(")")).equals(k)) {
                                this.a[i][j] = "";
                            }
                        }
                    }
                }
                catch(StringIndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    void Extract_old_courses_Professor()    //again store courses of teacher
    {
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 7; j++) {
                try {
                    //System.out.println("inside the loop of Extract_old_courses_professors for "+this.getProf_name());
                    String str = this.a[i][j];
                    //System.out.println(i + "  " + j + " "+ str);
                    if ((str != null) &&(this.courses.indexOf(str.substring(0,str.indexOf("(")))==-1))
                    {
                        this.courses.add(str.substring(0, str.indexOf("(")));
                    }
                }catch (StringIndexOutOfBoundsException e)
                { }
            }
        }
    }
}
