package UI;

import javax.swing.*;

/**
 * Created by super on 1/10/2017.
 */
public class ConfigUI {
    private JPanel container;
    private JTextField textField1;
    private JTextField textField2;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Config");
        frame.setContentPane(new ConfigUI().container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
