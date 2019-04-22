package tool;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class myLoadMysql {
    static Connection conn = null;
    private String sql = "";
    private String myDate;
    private Statement stmt=null;

    public  void setSQL(String sql){
        this.sql=sql;
    }
    SimpleDateFormat dateFormat;

    final static String Database_url="?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT&autoReconnect=true&useSSL=false";

    public myLoadMysql(){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }


    /*
      加载数据库、表
     */
    public   Statement LoadMysql(String database_name){


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost/"+database_name+Database_url,"root", "password");
            stmt=conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            reError(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public  int CloseMysql(Statement stmt, ResultSet rs){

        try {
            if(rs!=null) rs.close();
            if (stmt!=null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        rs=null;
        stmt=null;

        return 0;
    }

    public ArrayList<String> get_all_articles(String table_name){
        ArrayList<String> list =new ArrayList<>();
        try {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("select hash_title from "+table_name);
             while(rs.next()){
                 list.add(rs.getString("hash_title"));
             }
        } catch (SQLException e) {
            e.printStackTrace();
            reError(e);
            return null;
        }

        return list;
    }

    public  int executeSQL(String arg1,String arg2,String arg3){
        System.out.println(arg3+""+arg3.length());
        try {

            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setString(1,arg1);
            statement.setString(2,arg2);
            statement.setString(3,arg3);
            statement.setBoolean(4,false);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
    }



    private static void reError(SQLException e){
        System.out.println("SQLException:"+e.getMessage());
        System.out.println("SQLState:"+e.getSQLState());
        System.out.println("VendorError:"+e.getErrorCode());
    }
}
