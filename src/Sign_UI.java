import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sign_UI {
    static JFrame f = new JFrame();//点击登陆后弹出界面的界面实例
    static DbProcess2 dbprocess = new DbProcess2();//数据库程序实例
    static JTextField usernameField = new JTextField();//为了使getusername函数可以获取到用户名，所以将此文本框的声明放在界面函数之外
    public static void signUI(){
        //窗体类
        JFrame signui = new JFrame();
        //窗体名称
        signui.setTitle("心理咨询预约系统");
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
        //文本框
        JLabel username = new JLabel("用户名");
        username.setFont(new Font(null,Font.PLAIN,15));
        signui.add(username);
        //(除了JFrame)其它所有组件设置大小都是该方法
        Dimension dm = new Dimension(300, 30);

        usernameField.setPreferredSize(dm);
        signui.add(usernameField);

        JLabel password = new JLabel("密码");
        password.setFont(new Font(null,Font.PLAIN,15));
        signui.add(password);
        //密码框
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(dm);
        signui.add(passwordField);
        //医生登录按钮
        JButton jbu1 = new JButton("医生登陆");
        String table1 = "doctor_sign";
        boolean bool = true;//进入函数中方便判断跳转到哪个页面
        signui.add(jbu1);   //给窗体添加一个按钮对象
        jbu1.addActionListener(e -> {//这是一个lambda函数（匿名函数）
            if(e.getActionCommand().equals("医生登陆")) {
                sign(usernameField, passwordField,table1,bool);//sign函数判断用户名密码是否正确
            }
        });
        //用户登录按钮
        JButton jbu2 = new JButton("用户登录");
        String table2 = "users_sign";
        signui.add(jbu2);
        jbu2.addActionListener(e -> {
            if(e.getActionCommand().equals("用户登录")) {
                sign(usernameField, passwordField,table2,!bool);
            }
        });
        //用户注册按钮
        JButton jbu3 = new JButton("用户注册");
        signui.add(jbu3);   //给窗体添加一个按钮对象
        jbu3.addActionListener(e -> {
            if(e.getActionCommand().equals("用户注册")) {
                register(passwordField);
            }
        });
        JLabel note1 = new JLabel("注意:1.注册请输入用户名与密码后点击用户注册");
        username.setFont(new Font(null,Font.PLAIN,15));
        signui.add(note1);

        JLabel note2 = new JLabel("2.一个用户只能预约一个时间              ");
        username.setFont(new Font(null,Font.PLAIN,15));
        signui.add(note2);

        JLabel note3 = new JLabel("3.用户名不超过20个字母或汉字          ");
        username.setFont(new Font(null,Font.PLAIN,15));
        signui.add(note3);

        signui.setVisible(true);   //设置可见，放在代码最后一句
    }

    private static void register(JPasswordField passwordField) {
        String user = usernameField.getText().trim();
        String pass = String.valueOf(passwordField.getPassword());
        if (user.equals(""))
            JOptionPane.showMessageDialog(f, "账号不能为空");
        else if (pass.equals(""))
            JOptionPane.showMessageDialog(f, "密码不能为空");
        else if(user.length()>20)
            JOptionPane.showMessageDialog(f, "账号长度超过限制");
        else if (pass.length()>20)
            JOptionPane.showMessageDialog(f, "密码长度超过限制");
        else {
            String sql ="insert into users_sign (username, password) VALUES ( '" + user + "' , '" + pass +"')";
            System.out.println(sql);
            try {
                dbprocess.connect();
                dbprocess.sta = dbprocess.con.createStatement();
                if( !dbprocess.sta.execute(sql))//execute返回值为true时，表示执行的是查询语句，可以通过getResultSet方法获取结果；返回值为false时，执行的是更新语句或DDL语句或插入语句
                    JOptionPane.showMessageDialog(f, "注册成功");
            }
            catch(SQLException ex) {
                System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(f, "该用户名已被注册，请重新输入");
            }
        }
    }

    //sign函数用于判断用户名及密码是否正确然后完成页面跳转
    private static void sign(JTextField usernameField, JPasswordField passwordField,String table,boolean bool) {
        String user = usernameField.getText().trim();
        String pass = String.valueOf(passwordField.getPassword());
        if (usernameField.getText().equals(""))
            JOptionPane.showMessageDialog(f, "账号不能为空");
        else if (String.valueOf(passwordField.getPassword()).equals(""))
            JOptionPane.showMessageDialog(f, "密码不能为空");
        else {
            String sql ="select * from " + table + " where username =  '" + user +"' and password = '" + pass+"'";
            System.out.println(sql);
            try {
                dbprocess.connect();
                dbprocess.sta = dbprocess.con.createStatement();
                ResultSet RS1 = dbprocess.sta.executeQuery(sql);
                if (RS1.next()) {
                    JOptionPane.showMessageDialog(f, "登录成功");
                    if(bool) Doctor_UI.DoctorUI();//bool为ture时，跳转到医生页面，反则跳转用户页面
                    else Reserve_UI.ReserveUI();
                }
                else
                    JOptionPane.showMessageDialog(f, "账号或密码错误");
            }
            catch(SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    //getusername用于获取用户名，在后面会使用到
    public static String getusername() {
        return usernameField.getText();
    }
}

