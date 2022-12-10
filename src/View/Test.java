package View;

import javax.swing.*;
import java.awt.*;

public class Test {
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("test");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - 500/2, dimension.height/2 - 400/2, 200, 100);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = jFrame.getContentPane();
//        container.setLayout(new GroupLayout());
//        container.setLayout(new CardLayout());
        container.setLayout(new GridLayout(3,2,2,2));

        container.add(new JButton("B1"));
        container.add(new JButton("B2"));
        container.add(new JButton("B3"));
        container.add(new JButton("B4"));

        jFrame.setVisible(true);
    }

}
