import javax.swing.*;

public class Success {

    public void showUI(){
        javax.swing.JFrame jf= new javax.swing.JFrame();
        jf.setTitle("登录成功界面");
        jf.setSize(300,400);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        java.awt.FlowLayout flow=new java.awt.FlowLayout();
        jf.setLayout(flow);

        javax.swing.ImageIcon icon = new javax.swing.ImageIcon("C:\\Users\\63515\\Desktop\\R-C1.jpg");
        javax.swing.JLabel jla= new javax.swing.JLabel(icon);
        java.awt.Dimension dm0=new java.awt.Dimension(280,200);
        jla.setPreferredSize(dm0);
        jf.add(jla);

        jf.setVisible(true);
    }
}
