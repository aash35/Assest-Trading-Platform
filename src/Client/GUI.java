package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI {
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


        JFrame jframe = new JFrame();

        //removes the default icon
        Image icon = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE);
        jframe.setIconImage(icon);

        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jframe.setPreferredSize(new Dimension(800,600));

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Change");
        fileMenu.add("Login");
        fileMenu.add("Store");

        menuBar.add(fileMenu);
        jframe.setJMenuBar(menuBar);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Label A"));
        panel.add(new JLabel("Label B"));
        panel.add(new JLabel("Label C"));

        panel.setBorder(BorderFactory.createTitledBorder("This is a panel"));

        //TextArea
        JTextArea textArea = new JTextArea(25, 80);
        //scroll pane is a scrollable component
        JScrollPane scrollPane = new JScrollPane(textArea);

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

        button.setToolTipText("Clicking this button results in a divide by 0 error!");
        button.addActionListener(e -> {
            System.out.println(Thread.currentThread());
        });

        JTabbedPane panel2 = new JTabbedPane();
        panel2.add("Panel 1", scrollPane);
        panel2.add("Panel 2", buttonPanel);

        // ********* BorderLayout ************
        JPanel interiorPanel = new JPanel();
        BorderLayout layoutManager = new BorderLayout();
        interiorPanel.setLayout(layoutManager);
        interiorPanel.add(new JButton("Button C"), BorderLayout.NORTH);
        interiorPanel.add(new JButton("Button D"), BorderLayout.SOUTH);
        interiorPanel.add(new JButton("Button E"), BorderLayout.WEST);
        interiorPanel.add(new JTextArea(), BorderLayout.EAST);
        interiorPanel.add(new JList<String>(new String[]{"Hello", "Im","Here"}), BorderLayout.CENTER);
        panel2.add("Panel 3",interiorPanel);

        jframe.getContentPane().add(panel2);

        jframe.pack();

        jframe.setLocationRelativeTo(null);

        jframe.setVisible(true);


    }


}
