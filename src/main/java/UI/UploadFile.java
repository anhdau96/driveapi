package UI;

import com.mycompany.driveapi.UploadThread;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by super on 1/14/2017.
 */
public class UploadFile extends JFrame {
    public JTextArea uploadStatus;
    private JPanel container;

    public UploadFile(File file) throws HeadlessException {
        initComponents();
        new UploadThread(file);
    }

    private void initComponents() {
        uploadStatus = new JTextArea();
        container = new JPanel();
        container.setPreferredSize(new Dimension(500,500));
        container.add(uploadStatus);
        this.add(container);
        this.setSize(500,500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);


    }

    public void updateStatus(String status){
        this.uploadStatus.append(status);
        this.uploadStatus.append("\n");
    }
    public static void main(String[] args) {
    }
}
