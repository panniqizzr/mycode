import javax.swing.*;
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
        reserveui.setTitle("心理预约系统");
        reserveui.setSize(600, 500);
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

        String[] listData3 = new String[]{"", "赵医生", "李医生", "王医生"};
        combos(reserveui, jTextField6, listData3);

        JButton jButton1 = new JButton("预约");
        reserveui.add(jButton1);
        jButton1.addActionListener(e -> {
            String name = jTextField2.getText().trim();
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
                String sql1 = "insert into reserve (username,name,sex,phone,time,doctor) values(";
                sql1 = sql1 + "'" + Sign_UI.getusername() + "','" + name + "','" + sex + "','" + phone + "','" + time + "','" + doctor + "')";
                String
                        sql2 = "update time set state='已预约' where doctor = '" +doctor+"' and time = '" +time+"' and state = '未预约'";
                System.out.println(sql1);
                try {
                    dbprocess.connect();
                    dbprocess.sta = dbprocess.con.createStatement();
                    int q = dbprocess.sta.executeUpdate(sql2);//返回受到影响的行数
                    if(q==0) {
                        JOptionPane.showMessageDialog(f, "医生此时间已被预约");
                    }
                    else{
                        dbprocess.sta.execute(sql1);//execute返回值为true时，表示执行的是查询语句，可以通过getResultSet方法获取结果；返回值为false时，执行的是更新语句或DDL语句
                        JOptionPane.showMessageDialog(f, "预约成功");
                    }
                    dbprocess.disconnect();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            } else JOptionPane.showMessageDialog(f, "填写内容不全");
        });

        String sql3 = "select doctor,time,state from time";
        LinkedList<Showdata> list = null;
        try {
            dbprocess.connect();
            dbprocess.sta = dbprocess.con.createStatement();
            ResultSet result = dbprocess.sta.executeQuery(sql3);//execute返回值为true时，表示执行的是查询语句，可以通过getResultSet方法获取结果；返回值为false时，执行的是更新语句或DDL语句
            list = new LinkedList<>();
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
        String[] index = {"doctor", "time", "state"};
        assert list != null;
        Object[][] data = new Object[list.size()][index.length];
        for (int i = 0; i <list.size();i++) {
            Showdata s = list.get(i);
            data[i][0] = s.getDoctor();
            data[i][1] = s.getTime();
            data[i][2] = s.getState();
        }
        JTable table = new JTable(data,index);
        table.setRowHeight(30);
        reserveui.add(table);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);
        reserveui.add(jScrollPane);

        reserveui.setVisible(true);
    }

    private static void combos(JFrame reserveui, JTextField jTextField, String[] listData) {
        final JComboBox<String> comboBox2 = new JComboBox<>(listData);
        comboBox2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println("选中: " + comboBox2.getSelectedItem());
                jTextField.setText(Objects.requireNonNull(comboBox2.getSelectedItem()).toString());
            }
        });
        reserveui.add(comboBox2);
    }//选项框函数
}
