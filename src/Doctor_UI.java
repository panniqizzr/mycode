import javax.swing.*;
import java.awt.*;

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




        doctorui.setVisible(true);
    }
}
