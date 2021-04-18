package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI extends JFrame{
    public GUI(){
        //Sets the Look and Feel to Nimbus or SystemLookAndFeel if nimbus isn't found
        try{
            boolean nimbus = false;
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel((info.getClassName()));
                    nimbus = true;
                    break;
                }
            }
            if (!nimbus){
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (UnsupportedLookAndFeelException |
                ClassNotFoundException |
                IllegalAccessException |
                InstantiationException ignored) {
        }

        setLayout(new BorderLayout());
        Container container = getContentPane();

        //removes the default icon
        Image icon = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(800,600));


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Change");
        fileMenu.add("Login");
        fileMenu.add("Store");

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        //Panel to hold the button
        JPanel buttonPanel = new JPanel();
        //Button
        JButton button = new JButton("Button");
        buttonPanel.add(button);
        button.addActionListener(e -> {
            try{
                System.out.println(3 / 0);
            } catch(Exception x){
                JOptionPane.showMessageDialog(null, x.getMessage());
            }
        });

        Login loginPanel = new Login();
        container.add(loginPanel, BorderLayout.CENTER);

        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }
}
