package TIMETABLE_MANAGEMENT;

import java.util.ArrayList;
import java.util.Random;

public class batch extends timetablee
{

    static Random rand = new Random();
    ArrayList<String> profs_in_batch = new ArrayList<>();       //professor assigned to batch
    ArrayList<Integer> track_of_empty_slots = new ArrayList<Integer>();     // empty slots index of batch
    ArrayList<String> courses_in_batch = new ArrayList<>() ;        //courses assigned to batch
    int add(course c, professor p)
    {
        if(c.search_prof(p.getProf_name())) {
            for (int i = 0; i <= (5); i++) {
                for (int j = 0; j <= 7; j++) {
                    if ((this.a[i][j] == null) && (p.a[i][j] == null))      //if batch and professor have free period in this slot
                        track_of_empty_slots.add(i * 10 + j);   //add the empty slot
                    //creates a unique slot id for each time slot of batch
                }
            }
            int x, y;
            if (track_of_empty_slots.size() >= c.getCredits()) {     //if number of empty slots are greater than course credits
                for (int i = 0; i <= c.getCredits(); i++) {
                    int k = rand.nextInt(track_of_empty_slots.size());
                    x = track_of_empty_slots.get(k) / 10;
                    y = track_of_empty_slots.get(k) % 10;
                    this.a[x][y] = c.getName_course() + "\n(" + p.getProf_name() + ")";     //we add the course to the batch
                    p.a[x][y] = c.getName_course() + "\n(" + this.getBatch_name() + ")";       //adding to professors timetable
                    track_of_empty_slots.remove(k);                                             //remove the empty slot as it is filled
                }
                courses_in_batch.add(c.getName_course());       //add course to batch
                track_of_empty_slots.clear();                   //clear the empty slot list
                return 1;
            }
            //else
            track_of_empty_slots.clear();
            return 2;
        }

        return 3;
    }
    void removeprofs(String s)      //if professor is deleted it should be deleted from batch tt also
    {
        for(int i=0;i<=5;i++)
        {
            for(int j=0;j<=7;j++) {

                try {
                    String str = this.a[i][j];
                    if(str!=null) {
                        if (str.substring(str.indexOf("(") + 1, str.indexOf(")")).equals(s))
                            this.a[i][j] = "";
                    }
                } catch (StringIndexOutOfBoundsException e)
                {
                    //e.printStackTrace();
                }
            }
        }
    }
    void Extract_old_profs() {      //again cal total number of professor and add in array list
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 7; j++) {
                String str = this.a[i][j];
                if ((str != null)&&(str.length()!=0)) {
                    this.profs_in_batch.add(str.substring(str.indexOf("(") + 1, str.indexOf(")")));
                }
            }
        }
    }
    void Extract_old_courses()      //add course in course_in_batch arraylist
    {
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 7; j++) {
                String str = this.a[i][j];
                if ((str!=null)&&(str.length()!=0)) {
                    this.courses_in_batch.add(str.substring(0, str.indexOf("(")));
                }
            }
        }
    }


}
