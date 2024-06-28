package TIMETABLE_SCHEDULER;



import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class frontend
{
    int x;
    public static void main(String[] args)
    {
        frontend front=new frontend();

        JFrame message_box = new JFrame(); // Universal Message box

        Map<String,batch> B = new HashMap<String,batch>();
        Map<String,course> C = new HashMap<String, course>();
        Map<String,professor> P = new HashMap<String,professor>();

        Vector<String> all_courses =  new Vector<String>();
        all_courses.add("Select");
        Vector<String> all_batches = new Vector<String>();
        all_batches.add("Select");
        Vector<String> all_profs = new Vector<String>();
        all_profs.add("Select");
        Vector<Integer> all_credits = new Vector<Integer>();
        all_credits.add(0);
        all_credits.add(1);
        all_credits.add(2);
        all_credits.add(3);
        all_credits.add(4);

        SQLconnect sql_var = new SQLconnect();
        sql_var.explicitily_delete();
        if(sql_var.old_Professors.size()!=0)
        {
            int i=0;
            while(i!=sql_var.old_Professors.size())
            {
                P.get(sql_var.old_Professors.get(i));
                P.put(sql_var.old_Professors.get(i),new professor());
                P.get(sql_var.old_Professors.get(i)).setProf_name(sql_var.old_Professors.get(i));
                P.get(sql_var.old_Professors.get(i)).a = sql_var.load_data(sql_var.old_Professors.get(i));
                all_profs.add(sql_var.old_Professors.get(i));

                P.get(sql_var.old_Professors.get(i)).Extract_old_batches();
                //System.out.println("Going to extract courses for "+sql_var.old_Professors.get(i));
                P.get(sql_var.old_Professors.get(i)).Extract_old_courses_Professor();
                //System.out.println("extracted courses for "+sql_var.old_Professors.get(i));
                i++;
            }
        }
        if(sql_var.old_Batches.size()!=0)
        {
            int i=0;
            while(i!=sql_var.old_Batches.size())
            {
                B.get(sql_var.old_Batches.get(i));
                B.put(sql_var.old_Batches.get(i),new batch());
                B.get(sql_var.old_Batches.get(i)).setBatch_name(sql_var.old_Batches.get(i));
                B.get(sql_var.old_Batches.get(i)).a = sql_var.load_data(sql_var.old_Batches.get(i));
                all_batches.add(sql_var.old_Batches.get(i));
                B.get(sql_var.old_Batches.get(i)).Extract_old_courses();
                B.get(sql_var.old_Batches.get(i)).Extract_old_profs();
                i++;
            }
        }
        if(sql_var.old_courses.size()!=0)
        {
            for(Map.Entry<String,Integer> entry: sql_var.old_courses.entrySet())
            {
                C.get(entry.getKey());
                //System.out.println(entry.getKey());

                C.put(entry.getKey(),new course());
                C.get(entry.getKey()).setCredits(entry.getValue()-1);
                C.get(entry.getKey()).setName_course(entry.getKey());
                //System.out.println( C.get(entry.getKey()).getName_course());
                if(sql_var.get_course_record(entry.getKey()).size()>=1)
                {
                    ArrayList<String> temp = new ArrayList<String>();
                    temp = sql_var.get_course_record(entry.getKey());
                    //System.out.println(entry.getKey() + " no. of professors : "+ temp.size());
                    int i=0,k=temp.size();
                    while (i!=k)
                    {
                        C.get(entry.getKey()).add_courseprofs(temp.get(i));
                        if(P.containsKey(temp.get(i))) {
                            P.get(temp.get(i)).addCourses(entry.getKey());
                        }
                        //System.out.println(entry.getKey()+" "+temp.get(i));
                        i++;
                    }
                }
                all_courses.add(entry.getKey());
            }
        }


        JPanel home = new JPanel();
        home.setBorder(new LineBorder(new Color(0, 0, 0), 3));
        home.setBackground(new Color(128, 0, 0));
        home.setBounds(250, 50, 850, 200);
        home.setLayout(null);

        JLabel lblNewLabel_1h = new JLabel("SIT PUNE");
        lblNewLabel_1h.setForeground(new Color(255, 255, 255));
        lblNewLabel_1h.setFont(new Font("Alata", Font.BOLD, 30));
        lblNewLabel_1h.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1h.setBounds(323, 69, 218, 83);
        home.add(lblNewLabel_1h);

        JLabel Timetable_Management = new JLabel("Timetable Management");
        Timetable_Management.setForeground(new Color(255, 255, 255));
        Timetable_Management.setHorizontalAlignment(SwingConstants.CENTER);
        Timetable_Management.setFont(new Font("Alata", Font.BOLD, 30));
        Timetable_Management.setBounds(124, 132, 616, 83);
        home.add(Timetable_Management);

        JLabel System = new JLabel("System");
        System.setForeground(new Color(255, 255, 255));
        System.setHorizontalAlignment(SwingConstants.CENTER);
        System.setFont(new Font("Alata", Font.BOLD, 30));
        System.setBounds(336, 194, 218, 83);
        home.add(System);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        lblNewLabel.setIcon(new ImageIcon("C:/Users/mahaj/Desktop/Time table generator/study.jpg"));
        lblNewLabel.setBounds(194, 310, 490, 272);
        home.add(lblNewLabel);



        JPanel view = new JPanel();
        view.setBackground(new Color(255, 218, 185));
        view.setForeground(new Color(255, 218, 185));
        view.setBounds(345, 10, 744, 628);
        view.setLayout(null);

        JComboBox view_batch_v = new JComboBox(all_batches);
        view_batch_v.setBounds(149, 440, 126, 33);
        view.add(view_batch_v);

        JComboBox view_prof_v = new JComboBox(all_profs);
        view_prof_v.setBounds(528, 440, 126, 33);
        view.add(view_prof_v);

        JLabel lblNewLabelv = new JLabel("Batch:");
        lblNewLabelv.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblNewLabelv.setBounds(57, 436, 82, 32);
        view.add(lblNewLabelv);

        JLabel lblProfessor = new JLabel("Professor:");
        lblProfessor.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblProfessor.setBounds(389, 436, 98, 32);
        view.add(lblProfessor);

        JLabel lblNewLabel_1 = new JLabel("SIT (2024)");
        lblNewLabel_1.setFont(new Font("Goudy Old Style", Font.BOLD, 30));
        lblNewLabel_1.setForeground(new Color(139, 0, 0));
        lblNewLabel_1.setBounds(67, 44, 228, 44);
        view.add(lblNewLabel_1);





        final String[][] timet = { {"  Monday","","","","","","","",""},
                {"  Tuesday","","","","","","","",""},
                {"  Wednesday","","","","","","","",""},
                {"  Thursday","","","","","","","",""},
                {"  Friday","","","","","","","",""},
                {"  Saturday","","","","","","","",""}};
        String[] column ={"","1","2","3","4","5","6","7","8"};

        JTable table = new JTable(timet,column);
        table.setRowHeight(table.getRowHeight() + 20);
        table.setEnabled(false);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setBounds(32, 124, 632, 215);
        view.add(table);
        table.setSize(800,400);


        view_batch_v.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //B.get(view_batch_v.getItemAt(view_batch_v.getSelectedIndex()).toString()).a = sql_var.load_data(view_batch_v.getItemAt(view_batch_v.getSelectedIndex()).toString());
                for (int i=0;i<=5;i++)
                {
                    for (int j =0;j<=7;j++)
                    {
                        try {
                            timet[i][j + 1] = B.get(view_batch_v.getItemAt(view_batch_v.getSelectedIndex()).toString()).a[i][j];
                        }
                        catch(NullPointerException n)
                        {
                            n.getStackTrace();
                        }
                    }
                }
                view.revalidate();
                view.repaint();
            }
        });
        view_prof_v.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // P.get(view_prof_v.getItemAt(view_prof_v.getSelectedIndex()).toString()).a=sql_var.load_data(view_prof_v.getItemAt(view_prof_v.getSelectedIndex()).toString());
                for (int i=0;i<=5;i++)
                {
                    for (int j =0;j<=7;j++)
                    {
                        try
                        {
                            timet[i][j + 1] = P.get(view_prof_v.getItemAt(view_prof_v.getSelectedIndex()).toString()).a[i][j];
                        }
                        catch (NullPointerException n)
                        { }
                    }
                }
                view.revalidate();
                view.repaint();

            }
        });


        // Batch
        JPanel createbatch = new JPanel();
        createbatch.setBackground(new Color(255, 228, 225));
        createbatch.setBounds(345, 10, 744, 628);
        createbatch.setLayout(null);

        JLabel lblNewLabelb = new JLabel("New Batch\r\n");
        lblNewLabelb.setForeground(new Color(139, 0, 0));
        lblNewLabelb.setFont(new Font("Goudy Old Style", Font.BOLD, 30));
        lblNewLabelb.setBounds(51, 51, 196, 51);
        createbatch.add(lblNewLabelb);

        JLabel lblNewLabel_b = new JLabel("Batch Name: ");
        lblNewLabel_b.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblNewLabel_b.setBounds(117, 139, 174, 26);
        createbatch.add(lblNewLabel_b);

        JTextField name_batch = new JTextField();
        name_batch.setBounds(262, 139, 215, 31);
        createbatch.add(name_batch);
        name_batch.setColumns(10);

        JButton createbutton = new JButton("Add");
        createbutton.setFont(new Font("Dialog", Font.PLAIN, 17));
        createbutton.setForeground(new Color(255, 255, 255));
        createbutton.setBackground(new Color(139, 0, 0));
        createbutton.setBounds(538, 139, 85, 31);
        createbatch.add(createbutton);
        JLabel lblNewLabel_2 = new JLabel("Courses and Professors");
        lblNewLabel_2.setForeground(new Color(139, 0, 0));
        lblNewLabel_2.setFont(new Font("Goudy Old Style", Font.BOLD, 30));
        lblNewLabel_2.setBounds(51, 289, 316, 51);
        createbatch.add(lblNewLabel_2);

        JLabel lblNewLabel_1c = new JLabel("Select Course\r\n");
        lblNewLabel_1c.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblNewLabel_1c.setBounds(117, 372, 174, 26);
        createbatch.add(lblNewLabel_1c);

        JLabel lblNewLabel_1_1_1 = new JLabel("Select Professor");
        lblNewLabel_1_1_1.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblNewLabel_1_1_1.setBounds(317, 372, 174, 26);
        createbatch.add(lblNewLabel_1_1_1);

        JButton addbutton = new JButton("Add");
        addbutton.setForeground(Color.WHITE);
        addbutton.setFont(new Font("Dialog", Font.PLAIN, 17));
        addbutton.setBackground(new Color(139, 0, 0));
        addbutton.setBounds(538, 429, 85, 31);
        createbatch.add(addbutton);

        JComboBox view_courses_b = new JComboBox(all_courses);
        view_courses_b.setBounds(117, 429, 146, 31);
        createbatch.add(view_courses_b);

        JComboBox view_profs_b = new JComboBox(all_profs);
        view_profs_b.setBounds(317, 429, 146, 31);
        createbatch.add(view_profs_b);





        createbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(all_batches.indexOf(name_batch.getText())==-1) {
                    all_batches.add(name_batch.getText());
                    B.put(name_batch.getText(), new batch());
                    B.get(name_batch.getText()).setBatch_name(name_batch.getText());
                    sql_var.createtable(name_batch.getText(), 0);
                    JOptionPane.showMessageDialog(message_box, "A new batch " + name_batch.getText() + " has been created");
                }
                else
                {
                    JOptionPane.showMessageDialog(message_box,"Batch "+name_batch.getText()+" already exists\nif you still want to enter a batch by same name then\neither delete the previous record or update the existing record");
                }

            }
        });
        addbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(view_courses_b.getSelectedIndex()!=0 && view_profs_b.getSelectedIndex()!=0) {
                    if (all_batches.indexOf((name_batch.getText())) != -1) {

                        if (C.get(view_courses_b.getItemAt(view_courses_b.getSelectedIndex()).toString()).size_profs_array() >= 1) {
                            if (B.get(name_batch.getText()).add(C.get(view_courses_b.getItemAt(view_courses_b.getSelectedIndex()).toString()), P.get(view_profs_b.getItemAt(view_profs_b.getSelectedIndex()).toString())) == 1) {
                                B.get(name_batch.getText()).profs_in_batch.add(view_profs_b.getItemAt(view_profs_b.getSelectedIndex()).toString());
                                P.get(view_profs_b.getItemAt(view_profs_b.getSelectedIndex()).toString()).batches_under_profs.add(name_batch.getText());
                                sql_var.update_table(name_batch.getText(), B.get(name_batch.getText()).a);

                                sql_var.update_table(view_profs_b.getItemAt(view_profs_b.getSelectedIndex()).toString(), P.get(view_profs_b.getItemAt(view_profs_b.getSelectedIndex()).toString()).a);
                                JOptionPane.showMessageDialog(message_box, "A new course has been added to the batch " + name_batch.getText() + "\n" + "Course: " + view_courses_b.getItemAt(view_courses_b.getSelectedIndex()).toString() + "\n" + "Professor: " + view_profs_b.getItemAt(view_profs_b.getSelectedIndex()).toString());
                            } else if (B.get(name_batch.getText()).add(C.get(view_courses_b.getItemAt(view_courses_b.getSelectedIndex()).toString()), P.get(view_profs_b.getItemAt(view_profs_b.getSelectedIndex()).toString())) == 3) {

                                JOptionPane.showMessageDialog(message_box, "The Course was not assigned to this professor");
                            } else {
                                JOptionPane.showMessageDialog(message_box, "Insufficient slots");

                            }

                        } else {
                            JOptionPane.showMessageDialog(message_box, "Course selected does'nt have any professor\nHence firstly create a professor for it");
                        }
                    } else {
                        JOptionPane.showMessageDialog(message_box, "Batch does not exist!\nFirstly create the batch\n");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(message_box,"Select a valid course and valid professor");
                }
            }
        });



        //Create Course
        JPanel create_course = new JPanel();
        create_course.setBackground(new Color(255, 235, 205));
        create_course.setBounds(345, 10, 744, 628);
        create_course.setLayout(null);

        JLabel lblNewLabe2 = new JLabel("Course Details:");
        lblNewLabe2.setForeground(new Color(139, 0, 0));
        lblNewLabe2.setBackground(new Color(240, 240, 240));
        lblNewLabe2.setFont(new Font("Goudy Old Style", Font.BOLD, 30));
        lblNewLabe2.setBounds(55, 55, 198, 49);
        create_course.add(lblNewLabe2);

        JLabel lblLabel_2 = new JLabel("Course Name:");
        lblLabel_2.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblLabel_2.setBounds(135, 143, 160, 26);
        create_course.add(lblLabel_2);

        JTextField name_course = new JTextField();
        name_course.setBounds(285, 143, 198, 27);
        create_course.add(name_course);
        name_course.setColumns(10);

        JButton create_button_c = new JButton("Add");
        create_button_c.setBackground(new Color(139, 0, 0));
        create_button_c.setForeground(new Color(255, 255, 255));
        create_button_c.setFont(new Font("Dialog", Font.PLAIN, 20));
        create_button_c.setBounds(135, 324, 85, 39);
        create_course.add(create_button_c);

        JLabel lblNewLabel_2_1 = new JLabel("Credits:");
        lblNewLabel_2_1.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblNewLabel_2_1.setBounds(135, 244, 160, 26);
        create_course.add(lblNewLabel_2_1);

        JComboBox num_of_credits = new JComboBox(all_credits);
        num_of_credits.setBounds(285, 244, 138, 28);
        create_course.add(num_of_credits);

        JLabel course_img = new JLabel("");
        course_img.setIcon(new ImageIcon("C:/Users/mahaj/Desktop/Time table generator/study icon.png"));
        course_img.setBounds(379, 106, 505, 512);
        create_course.add(course_img);





        create_button_c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(all_courses.indexOf(name_course.getText())==-1) {
                    all_courses.add(name_course.getText());
                    C.put(name_course.getText(), new course());
                    C.get(name_course.getText()).setName_course(name_course.getText());
                    C.get(name_course.getText()).setCredits(num_of_credits.getSelectedIndex() - 1);
                    sql_var.createtable(name_course.getText(), num_of_credits.getSelectedIndex());
                    JOptionPane.showMessageDialog(message_box, "A new course has been created" + "\n" + "Course: " + name_course.getText() + "\n" + "Credits: " + Integer.parseInt(num_of_credits.getItemAt(num_of_credits.getSelectedIndex()).toString()));
                }
                else
                {
                    JOptionPane.showMessageDialog(message_box,name_course.getText()+" already exists !");
                }
            }
        });



        //Create Professor
        JPanel create_professor = new JPanel();
        create_professor.setBackground(new Color(250, 250, 210));
        create_professor.setBounds(345, 10, 744, 628);
        create_professor.setLayout(null);

        JLabel lblNewLabel3 = new JLabel("");
        lblNewLabel3.setIcon(new ImageIcon("C:/Users/mahaj/Desktop/Time table generator/teacher2.png"));
        lblNewLabel3.setBounds(388, 116, 497, 512);
        create_professor.add(lblNewLabel3);

        JLabel lblNewLabel_3 = new JLabel("New Professors");
        lblNewLabel_3.setFont(new Font("Goudy Old Style", Font.BOLD, 30));
        lblNewLabel_3.setForeground(new Color(139, 0, 0));
        lblNewLabel_3.setBounds(61, 86, 247, 37);
        create_professor.add(lblNewLabel_3);

        JLabel lblNewLabel_3_1 = new JLabel("Name:");
        lblNewLabel_3_1.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblNewLabel_3_1.setBounds(123, 165, 98, 37);
        create_professor.add(lblNewLabel_3_1);

        JLabel lblNewLabel_3_2 = new JLabel("Courses:");
        lblNewLabel_3_2.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblNewLabel_3_2.setBounds(123, 247, 98, 37);
        create_professor.add(lblNewLabel_3_2);

        JTextField name_professor = new JTextField();
        name_professor.setBounds(231, 172, 258, 32);
        create_professor.add(name_professor);
        name_professor.setColumns(10);

        JComboBox view_courses_p = new JComboBox(all_courses);
        view_courses_p.setBounds(233, 247, 182, 33);
        create_professor.add(view_courses_p);

        JButton createbutton_p = new JButton("Add");
        createbutton_p.setBackground(new Color(139, 0, 0));
        createbutton_p.setForeground(new Color(255, 255, 255));
        createbutton_p.setFont(new Font("Dialog", Font.PLAIN, 18));
        createbutton_p.setBounds(123, 325, 85, 37);
        create_professor.add(createbutton_p);




        createbutton_p.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                if(all_profs.indexOf(name_professor.getText())==-1) {
                    if ((view_courses_p.getSelectedIndex() != 0) && (name_professor.getText() != null)) {
                        all_profs.add(name_professor.getText());
                        P.put(name_professor.getText(), new professor());
                        P.get(name_professor.getText()).setProf_name(name_professor.getText());
                        C.get(view_courses_p.getItemAt(view_courses_p.getSelectedIndex()).toString()).add_courseprofs(name_professor.getText());
                        P.get(name_professor.getText()).addCourses(view_courses_p.getItemAt(view_courses_p.getSelectedIndex()).toString());
                        sql_var.createtable(name_professor.getText(),1);
                        sql_var.add_to_course_db(view_courses_p.getItemAt(view_courses_p.getSelectedIndex()).toString(),name_professor.getText());
                        JOptionPane.showMessageDialog(message_box, "A new Professor " + name_professor.getText() + " has been added");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(message_box,"Professor "+name_professor.getText()+" already exists\nif you still want to enter a professor by same name then\neither delete the previous record or update the existing record");
                }

            }
        });



        //Create Delete
        JPanel delete=new JPanel();
        delete.setBackground(new Color(240, 230, 140));
        delete.setBounds(345, 10, 744, 628);
        delete.setLayout(null);

        JLabel lblNewLabel4 = new JLabel("Delete");
        lblNewLabel4.setForeground(new Color(139, 0, 0));
        lblNewLabel4.setFont(new Font("Goudy Old Style", Font.BOLD, 30));
        lblNewLabel4.setBounds(69, 55, 182, 37);
        delete.add(lblNewLabel4);

        JLabel lblNewLabel_4 = new JLabel("Professor:");
        lblNewLabel_4.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblNewLabel_4.setBounds(126, 164, 149, 37);
        delete.add(lblNewLabel_4);

        JComboBox view_profs_d = new JComboBox(all_profs);
        view_profs_d.setBounds(285, 164, 149, 33);
        delete.add(view_profs_d);

        JLabel lblNewLabel_4_1 = new JLabel("Batch:");
        lblNewLabel_4_1.setFont(new Font("Dialog", Font.PLAIN, 20));
        lblNewLabel_4_1.setBounds(126, 289, 149, 37);
        delete.add(lblNewLabel_4_1);

        JComboBox view_batch_d = new JComboBox(all_batches);
        view_batch_d.setBounds(285, 289, 149, 33);
        delete.add(view_batch_d);

        JButton delete_prof = new JButton("Delete");
        delete_prof.setBackground(new Color(139, 0, 0));
        delete_prof.setForeground(new Color(255, 255, 255));
        delete_prof.setFont(new Font("Dialog", Font.PLAIN, 17));
        delete_prof.setBounds(496, 164, 98, 33);
        delete.add(delete_prof);

        JButton delete_batch = new JButton("Delete");
        delete_batch.setForeground(Color.WHITE);
        delete_batch.setFont(new Font("Dialog", Font.PLAIN, 17));
        delete_batch.setBackground(new Color(139, 0, 0));
        delete_batch.setBounds(496, 289, 98, 33);
        delete.add(delete_batch);

        JLabel lblNewLabeld = new JLabel("");
        lblNewLabeld.setIcon(new ImageIcon("C:/Users/mahaj/Desktop/Time table generator/books.png"));
        lblNewLabeld.setBounds(641, 382, 298, 236);
        delete.add(lblNewLabeld);



        delete_batch.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(view_batch_d.getSelectedIndex()!=0)
                {
                    int i=0;
                    String k =view_batch_d.getItemAt(view_batch_d.getSelectedIndex()).toString();
                    int ss=B.get(k).profs_in_batch.size();
                    sql_var.delete_record("BATCH",k);
                    while(i!=ss)
                    {
                        try{
                            if(B.get(k).profs_in_batch.get(i)!= null) {
                                P.get(B.get(k).profs_in_batch.get(i)).removebatch(k);
                                if (sql_var.tableExists(B.get(k).profs_in_batch.get(i))) {
                                    sql_var.update_table(B.get(k).profs_in_batch.get(i), P.get(B.get(k).profs_in_batch.get(i)).a);
                                }
                            }
                        }
                        catch(SQLException n)
                        { }
                        i++;
                    }
                    B.get(k).profs_in_batch.clear();

                    all_batches.remove(k);
                    B.remove(k);

                    sql_var.delete_table(k);
                    JOptionPane.showMessageDialog(message_box,k+" batch has been removed");
                }
            }
        });
        delete_prof.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(view_profs_d.getSelectedIndex()!=0)
                {
                    String k = view_profs_d.getItemAt(view_profs_d.getSelectedIndex()).toString();
                    int i=0;
                    int ss = P.get(k).batches_under_profs.size();
                    sql_var.delete_record("PROFESSOR",k);

                    while (i!=ss)
                    {
                        try{
                            if(P.get(k).batches_under_profs.get(i)!=null) {
                                B.get(P.get(k).batches_under_profs.get(i)).removeprofs(k);
                                if (sql_var.tableExists(P.get(k).batches_under_profs.get(i))) {
                                    sql_var.update_table(P.get(k).batches_under_profs.get(i), B.get(P.get(k).batches_under_profs.get(i)).a);
                                }
                            }
                        }
                        catch (SQLException n)
                        { }
                        i++;

                    }


                    ArrayList<String> courses_returned = new ArrayList<String>();
                    courses_returned = P.get(k).returnCourses();
                    int j=0;
                    int ss2 = courses_returned.size();
                    while(j!=ss2)
                    {
                        //System.out.println("Course returned " + courses_returned.get(j));
                        if(C.get(courses_returned.get(j))!=null) {
                            C.get(courses_returned.get(j)).remove_courseprofs(k);
                            sql_var.delete_record(courses_returned.get(j), k);
                        }
                        j++;
                    }


                    P.get(k).batches_under_profs.clear();
                    all_profs.remove(k);
                    P.remove(k);

                    sql_var.delete_table(k);
                    JOptionPane.showMessageDialog(message_box,"Professor "+k+" has been removed");
                }
            }

        });



        // login frame noww
        JPanel logout_panel = new JPanel();
        JFrame lf=new JFrame();
        JPanel contentPane;
        JTextField textField;
        JPasswordField passwordField;
        ButtonGroup buttonGroup = new ButtonGroup();
        lf.setBounds(50, 50, 1000, 667);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 204));
        contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        lf.setContentPane(contentPane);
        lf.setLocationRelativeTo(null);
        contentPane.setLayout(null);


        textField = new JTextField();
        textField.setText("Username");
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(textField.getText().equals("Username"))
                    textField.setText("");
                else
                    textField.selectAll();
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(textField.getText().equals(""))
                    textField.setText("Username");
            }
        });
        textField.setFont(new Font("Dialog", Font.PLAIN, 18));
        textField.setColumns(10);
        textField.setBackground(Color.WHITE);
        textField.setBounds(232, 228, 223, 36);
        contentPane.add(textField);

        passwordField = new JPasswordField();
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(passwordField.getText().equals("Password")) {
                    passwordField.setEchoChar('*');
                    passwordField.setText("");
                }
                else
                    passwordField.selectAll();
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(passwordField.getText().equals("")) {
                    passwordField.setText("Password");
                    passwordField.setEchoChar((char)0);
                }
            }
        });
        passwordField.setText("Password");
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 18));
        passwordField.setBounds(232, 307, 223, 36);
        contentPane.add(passwordField);
        JButton LoginBtn = new JButton("Log In");
        LoginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(textField.getText().equals("admin") && passwordField.getText().equals("admin") &&front.x==1) {
                    //JOptionPane.showMessageDialog(lf, "Admin Login Successful!");
                    front.x = 1;
                }
                else if(textField.getText().equals("teacher") && passwordField.getText().equals("teacher")&&front.x==2) {
                    //JOptionPane.showMessageDialog(lf, "Teacher Login Successful!");
                    front.x = 2;
                }
                else if(textField.getText().equals("student") && passwordField.getText().equals("student")&&front.x==3) {
                    //JOptionPane.showMessageDialog(lf, "Student Login Successful!");
                    front.x = 3;
                }
                else if(textField.getText().equals("") || passwordField.getText().equals("") || textField.getText().equals("Username") || passwordField.getText().equals("Password")) {
                    JOptionPane.showMessageDialog(lf, "Please fill all the required details!");
                }
                else {
                    JOptionPane.showMessageDialog(lf, "Incorrect Username or Password!");
                }

            }
        });
        LoginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        LoginBtn.setForeground(new Color(255, 255, 255));
        LoginBtn.setBackground(new Color(102, 0, 0));
        LoginBtn.setFont(new Font("Dialog", Font.PLAIN, 18));
        LoginBtn.setBounds(232, 432, 99, 48);
        contentPane.add(LoginBtn);

        JButton ResetBtn = new JButton("Reset");
        ResetBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ResetBtn.setForeground(new Color(255, 255, 255));
        ResetBtn.setFont(new Font("Dialog", Font.PLAIN, 18));
        ResetBtn.setBackground(new Color(102, 0, 0));
        ResetBtn.setBounds(356, 432, 99, 48);
        contentPane.add(ResetBtn);

        JLabel batch_img = new JLabel("");
        batch_img.setIcon(new ImageIcon("C:/Users/mahaj/Desktop/Time table generator/photo-desktop.jpg"));
        batch_img.setBounds(589, 10, 435, 610);
        contentPane.add(batch_img);

        JLabel Login = new JLabel("Login Page");
        Login.setFont(new Font("Lucida Calligraphy", Font.BOLD, 22));
        Login.setBounds(186, 36, 137, 48);
        contentPane.add(Login);

        JRadioButton Admin = new JRadioButton("Admin");
        buttonGroup.add(Admin);
        Admin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Admin.setOpaque(false);
        Admin.setBackground(new Color(255, 255, 153));
        Admin.setFont(new Font("Dialog", Font.PLAIN, 18));
        Admin.setBounds(81, 147, 103, 21);
        contentPane.add(Admin);

        JRadioButton Teacher = new JRadioButton("Teacher");
        buttonGroup.add(Teacher);
        Teacher.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Teacher.setFont(new Font("Dialog", Font.PLAIN, 18));
        Teacher.setOpaque(false);
        Teacher.setBounds(220, 147, 103, 21);
        contentPane.add(Teacher);

        JRadioButton Student = new JRadioButton("Student");
        buttonGroup.add(Student);
        Student.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Student.setOpaque(false);
        Student.setFont(new Font("Dialog", Font.PLAIN, 18));
        Student.setBounds(352, 147, 103, 21);
        contentPane.add(Student);

        JLabel Password = new JLabel(" Password:");
        Password.setFont(new Font("Dialog", Font.PLAIN, 18));
        Password.setBounds(81, 307, 103, 36);
        contentPane.add(Password);

        JLabel Username = new JLabel(" Username:");
        Username.setFont(new Font("Dialog", Font.PLAIN, 18));
        Username.setBounds(81, 228, 103, 36);
        contentPane.add(Username);

        JCheckBox chckbxShowPassword = new JCheckBox("Show Password");
        chckbxShowPassword.setOpaque(false);
        chckbxShowPassword.setFont(new Font("Dialog", Font.PLAIN, 14));
        chckbxShowPassword.setBounds(232, 361, 129, 21);
        contentPane.add(chckbxShowPassword);

        Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==Admin)
                {  front.x=1;
                    //System.out.println("admin, "+front.x);
                }

            }
        });


        Teacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==Teacher)
                { front.x=2;
                    //System.out.println("teacher "+front.x);
                }
            }
        });


        Student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==Student)
                { front.x=3;
                    //System.out.println("student "+front.x);
                }
            }
        });


        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int no;
                if (e.getSource() == LoginBtn) {
                    String userText;
                    String pwdText;
                    userText = textField.getText();
                    pwdText = passwordField.getText();

                    switch(front.x) {
                        case 1:
                            if (userText.equalsIgnoreCase("admin") && pwdText.equalsIgnoreCase("admin")) {
                                JTabbedPane t = new JTabbedPane();
                                JButton logout = new JButton("LOGOUT");
                                t.add("HOME", home);
                                t.add("VIEW", view);
                                t.add("Create Batch", createbatch);
                                t.add("Create Course", create_course);
                                t.add("Create Professor", create_professor);
                                t.add("Delete", delete);
                                t.add("LOGOUT", logout_panel);
                                t.setTabComponentAt(6,logout);
                                JOptionPane.showMessageDialog(lf, "Admin Login Successful!");

                                JFrame f=new JFrame();
                                f.add(t);
                                f.setSize(1100, 700);
                                t.setBounds(50, 50, 900, 650);
                                f.setLayout(null);
                                f.setVisible(true);
                                logout.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        f.dispose();
                                    }
                                });

                            } else {
                                no = -1;
                            }
                            break;
                        case 3:
                            if (userText.equalsIgnoreCase("student") && pwdText.equalsIgnoreCase("student")) {
                                JTabbedPane t= new JTabbedPane();
                                JButton logout = new JButton("LOGOUT");
                                t.add("HOME", home);
                                t.add("VIEW", view);
                                t.add("LOGOUT", logout_panel);
                                t.setTabComponentAt(2,logout);
                                JOptionPane.showMessageDialog(lf, "Student Login Successful!");
                                JFrame f=new JFrame();
                                f.add(t);
                                f.setSize(1100, 700);
                                t.setBounds(50, 50, 900, 650);
                                f.setLayout(null);
                                f.setVisible(true);
                                no = 2;
                                logout.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        f.dispose();
                                    }
                                });


                            } else {
                                no = -1;
                            }
                            break;
                        case 2:
                            if (userText.equalsIgnoreCase("teacher") && pwdText.equalsIgnoreCase("teacher")) {
                                JTabbedPane t = new JTabbedPane();
                                JButton logout = new JButton("LOGOUT");
                                t.add("HOME", home);
                                t.add("VIEW", view);
                                t.add("LOGOUT", logout_panel);
                                t.setTabComponentAt(2,logout);
                                JOptionPane.showMessageDialog(lf, "Teacher Login Successful!");
                                JFrame f=new JFrame();
                                f.add(t);
                                f.setSize(1100, 700);
                                t.setBounds(50, 50, 900, 650);
                                f.setLayout(null);
                                f.setVisible(true);
                                logout.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        f.dispose();
                                    }
                                });

                                no = 3;
                            } else {
                                no = -1;

                            }
                            break;

                        default:



                    }
                }
            }
        });

        ResetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ResetBtn) {
                    textField.setText("");
                    passwordField.setText("");
                }
            }
        });


        chckbxShowPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == chckbxShowPassword) {
                    if (chckbxShowPassword.isSelected()) {
                        passwordField.setEchoChar((char) 0);
                    } else {
                        passwordField.setEchoChar('*');
                    }
                }
            }
        });


        lf.setTitle("Login Frame");
        lf.setVisible(true);
        lf.setResizable(true);
        lf.setLayout(null);
        lf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

}