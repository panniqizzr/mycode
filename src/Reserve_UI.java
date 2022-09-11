import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;

public class Reserve_UI {
    static JFrame f = new JFrame();//点击登陆后弹出界面的界面实例
    static DbProcess2 dbprocess = new DbProcess2();//数据库程序实例
    public static void ReserveUI() {
        JFrame reserveui = new JFrame();
        reserveui.setTitle("心理咨询预约系统");
        reserveui.setSize(700, 500);
        reserveui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        reserveui.setLocationRelativeTo(null);

        FlowLayout flow = new FlowLayout();
        reserveui.setLayout(flow);

        JLabel label1 = new JLabel("用户:" + Sign_UI.getusername());
        label1.setFont(new Font(null, Font.PLAIN, 20));
        reserveui.add(label1);

        JLabel label2 = new JLabel("姓名");
        label2.setFont(new Font(null, Font.PLAIN, 20));
        reserveui.add(label2);

        JTextField jTextField2 = new JTextField(8);
        reserveui.add(jTextField2);

        JLabel label3 = new JLabel("性别");
        label3.setFont(new Font(null, Font.PLAIN, 20));
        reserveui.add(label3);

        JTextField jTextField3 = new JTextField(8);
        reserveui.add(jTextField3);

        String[] listData1 = new String[]{"", "男", "女"};
        combos(reserveui, jTextField3, listData1);

        JLabel label4 = new JLabel("电话");
        label4.setFont(new Font(null, Font.PLAIN, 20));
        reserveui.add(label4);

        JTextField jTextField4 = new JTextField(8);
        reserveui.add(jTextField4);

        JLabel label5 = new JLabel("预约时间");
        label5.setFont(new Font(null, Font.PLAIN, 20));
        reserveui.add(label5);

        JTextField jTextField5 = new JTextField(8);
        reserveui.add(jTextField5);

        String[] listData2 = new String[]{"", "8:00-10:00", "10:00-12:00", "14:00-16:00", "16:00-18:00"};
        combos(reserveui, jTextField5, listData2);

        JLabel label6 = new JLabel("预约医师");
        label6.setFont(new Font(null, Font.PLAIN, 20));
        reserveui.add(label6);
        JTextField jTextField6 = new JTextField(8);
        reserveui.add(jTextField6);

        String[] listData3 = new String[]{"", "赵医生", "王医生", "李医生","孙医生"};
        combos(reserveui, jTextField6, listData3);

        JButton jButton1 = new JButton("预约");
        reserveui.add(jButton1);
        jButton1.addActionListener(e -> {
            String name = jTextField2.getText().trim();//将用户输入文本框中的文字转换为字符串
            String sex = jTextField3.getText().trim();
            String phone = jTextField4.getText().trim();
            String time = jTextField5.getText().trim();
            String doctor = jTextField6.getText().trim();
            if (e.getActionCommand().equals("预约")
                    && !name.isEmpty()
                    && !sex.isEmpty()
                    && !phone.isEmpty()
                    && !time.isEmpty()
                    && !doctor.isEmpty()) {
                //sql1将预约信息插入到reserve表中
                String sql1 = "insert into reserve (username,name,sex,phone,time,doctor) values(";
                sql1 = sql1 + "'" + Sign_UI.getusername() + "','" + name + "','" + sex + "','" + phone + "','" + time + "','" + doctor + "')";
                //sql2查询此医生时间是否被预约
                String sql2 = "select state from time where doctor = '" +doctor+"' and time = '" +time+"' and state = '已预约'";
                //sql3将time表中当前医生的当前时间段预约状态改为已预约
                String sql3 = "update time set state='已预约' where doctor = '" +doctor+"' and time = '" +time+"' and state = '未预约'";
                System.out.println(sql1);
                System.out.println(sql2);
                System.out.println(sql3);
                try {
                    dbprocess.connect();
                    dbprocess.sta = dbprocess.con.createStatement();
                    dbprocess.sta.execute(sql2);
                     ResultSet result = dbprocess.sta.getResultSet();

                    if(result.next()) {
                        JOptionPane.showMessageDialog(f, "医生此时间已被预约");
                    }
                    else{
                        dbprocess.sta.execute(sql1);//execute返回值为true时，表示执行的是查询语句，可以通过getResultSet方法获取结果；返回值为false时，执行的是更新语句或DDL语句
                        dbprocess.sta.executeUpdate(sql3);//返回受到影响的行数
                        JOptionPane.showMessageDialog(f, "预约成功");
                    }
                    dbprocess.disconnect();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            } else JOptionPane.showMessageDialog(f, "填写内容不全");
        });

        JButton jButton2 = new JButton("取消预约");
        reserveui.add(jButton2);
        jButton2.addActionListener(e -> {
            //sql4将将time表中当前医生的当前时间段预约状态改为未预约
            String sql4 = "update time a INNER JOIN reserve b on (a.doctor = b.doctor and a.time = b.time) set a.state='未预约' where username ='" + Sign_UI.getusername() + "'";
            //sql5删除此次预约
            String sql5 = "delete from reserve where username = '" + Sign_UI.getusername() + "'";
            System.out.println(sql4);
            System.out.println(sql5);
            try {
                dbprocess.connect();
                dbprocess.sta = dbprocess.con.createStatement();
                int a = dbprocess.sta.executeUpdate(sql4);//返回值为改变行数，改变行数为0表示没有找到此预约信息
                if(a==0) JOptionPane.showMessageDialog(f, "您还未预约");
                else {
                    dbprocess.sta.executeUpdate(sql5);//删除此预约
                    JOptionPane.showMessageDialog(f, "取消预约成功");
                }

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        });

        //将数据库中的数据通过jtable给用户显示
        String[] index = {"医生", "预约时间", "预约状态"};
        Object[][] table_data_time = getObjects(index);//获取数据库内容
        DefaultTableModel tModel = new DefaultTableModel(table_data_time,index);//Jtable的默认模型
        JTable table = new JTable(tModel);//使用模型创建一个table对象
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        reserveui.add(table);
        //竖直滚动条
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);
        reserveui.add(jScrollPane);
        //将模型的Object[][]（里面含有数据库最新数据）重新创建并显示
        JButton jButton3 = new JButton("刷新");
        reserveui.add(jButton3);
        jButton3.addActionListener(e -> {
            Object[][] data_in_table2 = getObjects(index);
            tModel.setDataVector(data_in_table2,index);
        });

        reserveui.setVisible(true);
    }
    //获取数据库数据
    private static Object[][] getObjects(String[] index) {
        String sql = "select doctor,time,state from time";
        System.out.println(sql);
        LinkedList<Showdata> list = new LinkedList<>();//双向链表
        try {
            dbprocess.connect();
            dbprocess.sta = dbprocess.con.createStatement();
            ResultSet result = dbprocess.sta.executeQuery(sql);//execute返回值为true时，表示执行的是查询语句，可以通过getResultSet方法获取结果；返回值为false时，执行的是更新语句或DDL语句
            while (result.next()) {
                Showdata data = new Showdata();
                data.setDoctor(result.getString("doctor"));
                data.setTime(result.getString("time"));
                data.setState(result.getString("state"));
                list.add(data);
            }
            dbprocess.disconnect();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        Object[][] table_data_time = new Object[list.size()][index.length];
        for (int i = 0; i <list.size();i++) {
            Showdata s = list.get(i);
            table_data_time[i][0] = s.getDoctor();
            table_data_time[i][1] = s.getTime();
            table_data_time[i][2] = s.getState();
        }
        return table_data_time;
    }
    //选项框函数
    private static void combos(JFrame reserveui, JTextField jTextField, String[] listData) {
        final JComboBox<String> comboBox2 = new JComboBox<>(listData);
        comboBox2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println("选中: " + comboBox2.getSelectedItem());
                jTextField.setText(Objects.requireNonNull(comboBox2.getSelectedItem()).toString());
            }
        });
        reserveui.add(comboBox2);
    }
}
