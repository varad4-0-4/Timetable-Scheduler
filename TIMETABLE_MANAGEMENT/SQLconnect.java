package TIMETABLE_MANAGEMENT;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLconnect
{
    String[][] d = new String[6][8];
    static final String DB_URL = "jdbc:mysql://localhost:3306/timetables";
    static final String USER = "root";
    static final String PASS = "root";
    ArrayList<String> old_Professors = new ArrayList<String>();
    ArrayList<String> old_Batches = new ArrayList<String>();
    Map<String,Integer> old_courses = new HashMap<String,Integer>();

    SQLconnect()        //non parameterised constructor
    {
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS))
        {
            Statement stmt = conn.createStatement();
            if(!tableExists("PROFESSOR"))       //to check if prof table exits or no
            {

                String sql ="CREATE TABLE PROFESSOR ( ID INTEGER not NULL AUTO_INCREMENT, " + " NAME VARCHAR (255), "+ " PRIMARY KEY ( ID ))";
                stmt.executeUpdate(sql);
            }
            else
            {
                String sql = "SELECT * FROM PROFESSOR ";
                ResultSet res = stmt.executeQuery(sql);
                //ResultSetMetaData metaData = res.getMetaData();
                while(res.next())
                {
                    old_Professors.add(res.getString("NAME"));  //return names of professor
                }
                res.close();

            }
            if(!tableExists("BATCH"))
            {
                String sql ="CREATE TABLE BATCH ( ID INTEGER not NULL AUTO_INCREMENT, "+" NAME VARCHAR (255), "+ " PRIMARY KEY ( ID ))";
                stmt.executeUpdate(sql);
            }
            else
            {
                String sql = "SELECT * FROM BATCH ";
                ResultSet res = stmt.executeQuery(sql);
                //ResultSetMetaData metaData = res.getMetaData();
                while(res.next())
                {
                    old_Batches.add(res.getString("NAME"));
                }
                res.close();

            }
            if(!tableExists("COURSE"))
            {
                String sql = " CREATE TABLE COURSE  ( ID INTEGER not NULL AUTO_INCREMENT, "+" NAME VARCHAR (255), "+" CREDITS INTEGER not NULL , "+ " PRIMARY KEY ( ID ))";
                stmt.executeUpdate(sql);
            }
            else
            {
                String sql = "SELECT * FROM COURSE ";
                ResultSet res = stmt.executeQuery(sql);
                //ResultSetMetaData metaData = res.getMetaData();
                while(res.next())
                {
                    for(int i=0;i<=1;i++)
                    {
                        old_courses.get(res.getString("NAME"));
                        old_courses.put(res.getString("NAME"),res.getInt("CREDITS"));
                    }
                }
                res.close();
            }
            stmt.close();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
        public void createtable(String table_name,int n)
        {
            try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
                Statement stmt = conn.createStatement()
            )
            {

                if(n==0) {
                    String sql3 = "CREATE TABLE " + table_name + " ( DAYNO INTEGER not NULL AUTO_INCREMENT, " + " first VARCHAR (255), " + " second VARCHAR (255), " + " third VARCHAR(255), " + " fourth VARCHAR(255), " + " fifth VARCHAR(255), " + " sixth VARCHAR(255), " + " seventh VARCHAR(255), " + " eighth VARCHAR(255), " + " PRIMARY KEY ( DAYNO ))";
                    stmt.executeUpdate(sql3);
                    //System.out.println("Trying to add batch");
                    stmt.executeUpdate("INSERT INTO BATCH (NAME) VALUES( '"+table_name+"' )");
                    //stmt2.setString(1,table_name);
                    //System.out.println("ADDED batch successfully");
                }
                else if (n==1)
                {
                    String sql3 = "CREATE TABLE " + table_name + " ( DAYNO INTEGER not NULL AUTO_INCREMENT, " + " first VARCHAR (255), " + " second VARCHAR (255), " + " third VARCHAR(255), " + " fourth VARCHAR(255), " + " fifth VARCHAR(255), " + " sixth VARCHAR(255), " + " seventh VARCHAR(255), " + " eighth VARCHAR(255), " + " PRIMARY KEY ( DAYNO ))";
                    stmt.executeUpdate(sql3);
                    //System.out.println("Trying to add professor");
                    stmt.executeUpdate("INSERT INTO PROFESSOR (NAME) VALUES( '"+table_name+"' )");
                    //stmt2.setString(1,table_name);
                    //System.out.println("ADDED Professor successfully");
                }
                else
                {
                    String sql ="CREATE TABLE " + table_name + " ( ID INTEGER not NULL AUTO_INCREMENT, "+" NAME VARCHAR (255), "+ " PRIMARY KEY ( ID ))";
                    stmt.executeUpdate(sql);
                    stmt.executeUpdate("INSERT INTO COURSE ( NAME , CREDITS ) VALUES ('"+table_name+"', "+n+" )");

                }
                //System.out.println("Table created successfully");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
        public void update_table(String table_name, String[][] c)
        {

            try(Connection conn =  DriverManager.getConnection(DB_URL,USER,PASS))
            {
                String delete = "DROP TABLE "+table_name;
                Statement stmt1 = conn.createStatement();
                stmt1.executeUpdate(delete);
                //System.out.println("Database deleted Successfully");
                String create ="CREATE TABLE " + table_name + " ( DAYNO INTEGER not NULL AUTO_INCREMENT, " + " first VARCHAR (255), " + " second VARCHAR (255), " + " third VARCHAR(255), " + " fourth VARCHAR(255), " + " fifth VARCHAR(255), " + " sixth VARCHAR(255), " + " seventh VARCHAR(255), " + " eighth VARCHAR(255), " + " PRIMARY KEY ( DAYNO ))";
                stmt1.executeUpdate(create);
                //System.out.println("Table created successfully");
                conn.setAutoCommit(false);
                PreparedStatement stmt = conn.prepareStatement(String.format("INSERT INTO `%s` (first ,second , third , fourth , fifth , sixth , seventh , eighth) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",table_name.replace("`","``")));
                for(int i=0;i<=5;i++)
                {

                    stmt.setString(1,c[i][0]);
                    stmt.setString(2,c[i][1]);
                    stmt.setString(3,c[i][2]);
                    stmt.setString(4,c[i][3]);
                    stmt.setString(5,c[i][4]);
                    stmt.setString(6,c[i][5]);
                    stmt.setString(7,c[i][6]);
                    stmt.setString(8,c[i][7]);
                    stmt.addBatch();//Speed up the work
                }
                stmt.executeBatch();
                stmt.close();
                stmt1.close();
                conn.commit();

            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        public void delete_table(String table_name)
        {
             try(Connection conn =  DriverManager.getConnection(DB_URL,USER,PASS);
                 Statement stmt = conn.createStatement())
             {
                 String delete = "DROP TABLE "+table_name;

                 stmt.executeUpdate(delete);
             }
             catch(SQLException e)
             {
                 e.printStackTrace();
             }
        }
        public String[][] load_data(String table_name)
       {
           String[][] c =new String[6][8];
           try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
               Statement stmt = conn.createStatement())
           {

              // String load = "SELECT * FROM "+table_name;
               String load = "SELECT * FROM "+table_name;
               ResultSet res = stmt.executeQuery(load);
               //ResultSetMetaData metaData = res.getMetaData();
               int k=0;
               while(res.next()&&(k<=5))
               {
                   for(int j=2;j<=9;j++)
                   {
                       c[k][j-2]=res.getString(j);
                   }
                   k++;
               }
               res.close();

           }
           catch(SQLException e)
           {
               e.printStackTrace();
           }
           return(c);
       }
    boolean tableExists(String table_name)throws SQLException
    {

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            ResultSet res = conn.getMetaData().getTables(null, null, table_name,new String[] {"TABLE"})) {
            return res.next();
        }

    }
    public void add_to_course_db(String course_name, String prof_name)
    {
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt = conn.createStatement())
        {
            stmt.executeUpdate("INSERT INTO "+course_name+" (NAME) VALUES('"+prof_name+"')");
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public ArrayList<String> get_course_record(String table_name)
    {
        ArrayList<String> profs_in_course_db =new ArrayList<String>();
        try(Connection conn =DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt = conn.createStatement())
        {
            String load = "SELECT * FROM "+table_name;
            ResultSet res = stmt.executeQuery(load);
            //System.out.println("in SQLconnect get_course_record "+table_name);
            while (res.next())
            {
                //System.out.println(res.getString("NAME"));
                profs_in_course_db.add(res.getString("NAME"));
            }
            //System.out.println("ENDED get_course_record\n");
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return (profs_in_course_db);

    }
    public void delete_record(String table_name, String record_name)
    {
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt = conn.createStatement())
        {
            String sql = "DELETE FROM "+table_name+" WHERE NAME = '"+record_name+"'";

            stmt.executeUpdate(sql);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void explicitily_delete() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS))
        {

//            Statement stmt = conn.createStatement();
//            String sql = "DROP TABLE JK";
//            stmt.executeUpdate(sql);
//            sql = "DROP TABLE PROFESSOR";
//            stmt.executeUpdate(sql);
//            sql = "DROP TABLE BATCH";
//            stmt.executeUpdate(sql);
//            sql = "DROP TABLE COURSE";
//            stmt.executeUpdate(sql);
//            sql = "DROP TABLE RM";
//            stmt.executeUpdate(sql);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
