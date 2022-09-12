import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Doctor_UI {
    static JFrame f = new JFrame();//点击登陆后弹出界面的界面实例
    static DbProcess2 dbprocess = new DbProcess2();//数据库程序实例

    public static void DoctorUI() {
        JFrame doctorui = new JFrame();
        doctorui.setTitle("心理咨询预约系统");
        doctorui.setSize(600, 500);
        doctorui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        doctorui.setLocationRelativeTo(null);

        FlowLayout flow = new FlowLayout();
        doctorui.setLayout(flow);

        JLabel label = new JLabel("医生:" + Sign_UI.getusername());
        label.setFont(new Font(null, Font.PLAIN, 20));
        doctorui.add(label);

        JLabel label1 = new JLabel("预约号:");
        label1.setFont(new Font(null, Font.PLAIN, 20));
        doctorui.add(label1);

        JTextField jTextField1 = new JTextField(8);
        doctorui.add(jTextField1);

        JLabel label2 = new JLabel("诊断结果:");
        label2.setFont(new Font(null, Font.PLAIN, 20));
        doctorui.add(label2);

        JTextField jTextField2 = new JTextField(8);
        doctorui.add(jTextField2);

        JButton jButton1 = new JButton("诊断完成");
        doctorui.add(jButton1);
        jButton1.addActionListener(e -> {
            String num = jTextField1.getText().trim();
            String result = jTextField2.getText().trim();
            if (e.getActionCommand().equals("诊断完成") && !num.isEmpty() && !result.isEmpty()){
                String sql1 = "update reserve set result = '" + result +"', state = '已诊断' where num = '" + num + "'";
                System.out.println(sql1);
                try {
                    dbprocess.connect();
                    dbprocess.sta = dbprocess.con.createStatement();
                    int a = dbprocess.sta.executeUpdate(sql1);
                    if(a==0) JOptionPane.showMessageDialog(f, "预约号填写错误");//executeUpdate返回的是改变行数，如果是0的话只可能是没有这个预约号或已被诊断
                    else JOptionPane.showMessageDialog(f, "诊断完成");
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }

            }
            else JOptionPane.showMessageDialog(f, "填写内容不全");
        });

        String[] index = {"预约号", "姓名", "性别","电话号","诊断状态","诊断结果"};
        Object[][] table_data_reserve = getObjects(index);
        DefaultTableModel tModel = new DefaultTableModel(table_data_reserve,index);
        JTable table = new JTable(tModel);
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        doctorui.add(table);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);
        doctorui.add(jScrollPane);

        JButton jButton2 = new JButton("刷新");

        doctorui.add(jButton2);
        jButton2.addActionListener(e -> {
            Object[][] data_in_table2 = getObjects(index);
            tModel.setDataVector(data_in_table2,index);
        });

        doctorui.setVisible(true);
    }

    private static Object[][] getObjects(String[] index) {
        String sql2 = "select num,name,sex,phone,state,result from reserve where doctor ='" + Sign_UI.getusername() +"'";
        System.out.println(sql2);
        LinkedList<Showdata> list = new LinkedList<>();
        try {
            dbprocess.connect();
            dbprocess.sta = dbprocess.con.createStatement();
            ResultSet result = dbprocess.sta.executeQuery(sql2);//execute返回值为true时，表示执行的是查询语句，可以通过getResultSet方法获取结果；返回值为false时，执行的是更新语句或DDL语句
            while (result.next()) {
                Showdata data = new Showdata();
                data.setNum(result.getString("num"));
                data.setName(result.getString("name"));
                data.setSex(result.getString("sex"));
                data.setPhone(result.getString("phone"));
                data.setState(result.getString("state"));
                data.setResult(result.getString("result"));
                list.add(data);
            }
            dbprocess.disconnect();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        Object[][] table_data_reserve = new Object[list.size()][index.length];
        for (int i = 0; i <list.size();i++) {
            Showdata s = list.get(i);
            table_data_reserve[i][0] = s.getNum();
            table_data_reserve[i][1] = s.getName();
            table_data_reserve[i][2] = s.getSex();
            table_data_reserve[i][3] = s.getPhone();
            table_data_reserve[i][4] = s.getState();
            table_data_reserve[i][5] = s.getResult();
        }
        return table_data_reserve;
    }
}
