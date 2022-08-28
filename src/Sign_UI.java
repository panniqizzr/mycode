import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sign_UI {
    static JFrame f = new JFrame();//点击登陆后弹出界面的界面实例
    static DbProcess2 dbprocess = new DbProcess2();//数据库程序实例
    static JTextField usernameField = new JTextField();
    public static void signUI(){
        //窗体类
        JFrame signui = new JFrame();
        //窗体名称
        signui.setTitle("心理预约系统登录界面");
        //窗体大小（具体值跟电脑显示器的像素有关，可调整到合适大小）
        signui.setSize(400, 500);
        //设置退出进程的方法
        signui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置居中显示用3
        signui.setLocationRelativeTo(null);

        //流式布局管理器
        FlowLayout flow = new FlowLayout();
        signui.setLayout(flow);  //给窗体设置为流式布局——从左到右然后从上到下排列自己写的组件顺序

        //图片，冒号里是你存图片的地址
        ImageIcon icon = new ImageIcon("C:\\Users\\63515\\Desktop\\R-C1.jpg");
        //标签
        JLabel jla = new JLabel(icon);
        Dimension dm0=new Dimension(350,200);
        //设置大小
        jla.setPreferredSize(dm0);//应用大小到相应组件
        signui.add(jla);//将组件加到窗体上

        JLabel username = new JLabel("用户名");
        username.setFont(new Font(null,Font.PLAIN,15));
        signui.add(username);
        //文本框

        Dimension dm = new Dimension(300, 30);
        //(除了JFrame)其它所有组件设置大小都是该方法
        usernameField.setPreferredSize(dm);
        signui.add(usernameField);

        JLabel password = new JLabel("密码");
        password.setFont(new Font(null,Font.PLAIN,15));
        signui.add(password);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(dm);
        signui.add(passwordField);
        //复选框

        //按钮
        JButton jbu1 = new JButton("医生登陆");
        String table1 = "doctor_sign";
        boolean bool = true;
        signui.add(jbu1);   //给窗体添加一个按钮对象
        jbu1.addActionListener(e -> {
            if(e.getActionCommand().equals("医生登陆")) {
                sign(usernameField, passwordField,table1,bool);
            }
        });
        JButton jbu2 = new JButton("用户登录");
        String table2 = "users_sign";
        signui.add(jbu2);   //给窗体添加一个按钮对象
        jbu2.addActionListener(e -> {
            if(e.getActionCommand().equals("用户登录")) {
                sign(usernameField, passwordField,table2,!bool);
            }
        });
        JButton jbu3 = new JButton("用户注册");
        signui.add(jbu3);   //给窗体添加一个按钮对象
        jbu3.addActionListener(e -> {
            if(e.getActionCommand().equals("用户注册")) {
                if (usernameField.getText().equals(""))
                    JOptionPane.showMessageDialog(f, "账号不能为空");
                else if (String.valueOf(passwordField.getPassword()).equals(""))
                    JOptionPane.showMessageDialog(f, "密码不能为空");
                else {
                    if (register(usernameField.getText(), String.valueOf(passwordField.getPassword()),table2)) {
                        JOptionPane.showMessageDialog(f, "注册成功");
                    }
                    else
                        JOptionPane.showMessageDialog(f, "注册失败");
                }
            }
        });
        JLabel note = new JLabel("注意:注册请输入用户名与密码后点击用户注册");
        username.setFont(new Font(null,Font.PLAIN,15));
        signui.add(note);
        signui.setVisible(true);   //设置可见，放在代码最后一句
    }

    private static void sign(JTextField usernameField, JPasswordField passwordField,String table,boolean change_ui) {
        String user = usernameField.getText().trim();
        String pass = String.valueOf(passwordField.getPassword());
        if (usernameField.getText().equals(""))
            JOptionPane.showMessageDialog(f, "账号不能为空");
        else if (String.valueOf(passwordField.getPassword()).equals(""))
            JOptionPane.showMessageDialog(f, "密码不能为空");
        else {
            if (sign_in(user, pass,table)) {
                JOptionPane.showMessageDialog(f, "登录成功");
                if(change_ui) {
                    Success sc = new Success();//新的界面（跟旧的差不多后面有代码）
                    sc.showUI();
                }
                else Reserve_UI.ReserveUI();
            }
            else
                JOptionPane.showMessageDialog(f, "账号或密码错误");
        }
    }

    private static boolean sign_in(String username, String password, String table) {

        String sql ="select * from " + table + " where username =  '" + username +"' and password = '" + password+"'";
        try {
            dbprocess.connect();
            dbprocess.sta = dbprocess.con.createStatement();
            ResultSet RS1 = dbprocess.sta.executeQuery(sql);
            return RS1.next();
        }
        catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    private static boolean register(String username, String password, String table) {

        String sql ="insert into " + table + "(username, password) VALUES ( '" + username + "' , '" + password +"')";
        try {
            dbprocess.connect();
            dbprocess.sta = dbprocess.con.createStatement();
            return !dbprocess.sta.execute(sql);//execute返回值为true时，表示执行的是查询语句，可以通过getResultSet方法获取结果；返回值为false时，执行的是更新语句或DDL语句
        }
        catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    
    public static String getusername() {
        return usernameField.getText();
    }
}

