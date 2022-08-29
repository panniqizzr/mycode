import java.sql.*;

public class DbProcess2 {
    public Connection con = null;public Statement sta=null;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //注意！：下行3306后面的q1为我建立的数据库名，要改成自己建立的数据库名！！！
    static final String DB_URL = "jdbc:mysql://localhost:3306/xl?&useSSL=false&serverTimezone=UTC";
    //数据库账号密码，也要改为自己的
    static final String USER = "root";
    static final String PASS = "201749zao";

    public DbProcess2(){
        try {
            //mysql数据库设置驱动程序类型
            Class.forName(JDBC_DRIVER);
            System.out.println("连接数据库...");
            System.out.println("mysql数据库驱动加载成功");

        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    /*public void executeUpdate(String sql)
    {
        connect();
        try{
            sta=con.createStatement();
            sta.execute(sql);
        }
        catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
        disconnect();
    }*/


    public void connect()
    {
        try{
            //mysql数据库
            con = DriverManager.getConnection(DB_URL,USER,PASS);


            if(con!=null){
                System.out.println("数据库连接成功");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public void disconnect(){
        try{
            if(con != null){
                System.out.println("数据库断开成功");
                con.close();
                con = null;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}