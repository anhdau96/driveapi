package UI;

import Config.ConfigService;
import com.mycompany.driveapi.DownloadThread;
import com.mycompany.driveapi.DriveManager;
import controller.DBController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by super on 1/10/2017.
 */
public class UploadManager {

    private JPanel container;
    private JTabbedPane tabbedPane1;
    private JTable uploadingTable;
    private JButton startButton;
    private JPanel statusContainer;
    private JTextArea uploadingStatus;
    public UploadingTableModel uploadingTableModel;
    private File[] files;
    public static String progress="";
    private DBController dbController;
    DriveManager driveManager;

    public static String getProgress() {
        return progress;
    }

    public UploadManager getUploadManager(){
        return this;
    }

    public UploadManager() throws IOException {
        uploadingTableModel = new UploadingTableModel();
        dbController=new DBController();
        driveManager=new DriveManager();
        setUploadingTable();

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane1.setSelectedIndex(1);
                DownloadThread downloadThread = new DownloadThread(getUploadManager());
                downloadThread.driveManager = driveManager;
                downloadThread.files = files;
                downloadThread.uploadingTable = uploadingTable;
                downloadThread.uploadingTableModel = uploadingTableModel;
                downloadThread.jTextArea=uploadingStatus;
                downloadThread.start();
            }
        });

    }

    public static void changProgess(String progress){
        UploadManager.progress=progress;
    }

    public void updateStatus(String progress){

        uploadingStatus.append(progress);
        uploadingStatus.append("\n");
    }

    private void setUploadingTable(){

        java.io.File saveFolder=new java.io.File(ConfigService.getInstance().get("savePath"));
        System.out.println(Config.ConfigService.getInstance().get("savePath"));
        files = saveFolder.listFiles();
        for (int i=0; i<files.length;i++) {
            System.out.println(files[i].getName());
            dbController.
            uploadingTableModel.addRow(new String[]{
                    ""+i,files[i].getName(),"ahihi",files[i].length()/1024/1024+"mb"
            });
        }

        uploadingTable.setModel(uploadingTableModel);
        uploadingTable.getColumnModel().getColumn(0).setMaxWidth(50);
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("UploadManager");
        frame.setContentPane(new UploadManager().container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//        frame.setSize(640,480);
        frame.setLocationRelativeTo(null);
    }
}

