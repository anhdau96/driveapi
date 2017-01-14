package UI;

import Config.ConfigService;
import com.mycompany.driveapi.DriveManager;
import controller.DBController;
import model.FileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
    public static boolean flag;
    public static String getProgress() {
        return progress;
    }

    public UploadManager getUploadManager(){
        return this;
    }

    public UploadManager() throws IOException, SQLException, ClassNotFoundException {
        uploadingTableModel = new UploadingTableModel();
        dbController=new DBController();
        driveManager=new DriveManager();
        setUploadingTable();
//        startUpload();
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

//    private void startUpload(){
//        tabbedPane1.setSelectedIndex(1);
//        DownloadThread downloadThread = new DownloadThread(getUploadManager());
//        downloadThread.driveManager = driveManager;
//        downloadThread.files = files;
//        downloadThread.uploadingTable = uploadingTable;
//        downloadThread.uploadingTableModel = uploadingTableModel;
//        downloadThread.uploadingStatus=uploadingStatus;
//        downloadThread.start();
//    }


    public static void changProgess(String progress){
        UploadManager.progress=progress;
    }

    public void updateStatus(String progress){

        uploadingStatus.append(progress);
        uploadingStatus.append("\n");
    }

    private void setUploadingTable() throws SQLException, ClassNotFoundException {

        java.io.File saveFolder=new java.io.File(ConfigService.getInstance().get("savePath"));
        System.out.println(Config.ConfigService.getInstance().get("savePath"));
        File[] allFile = saveFolder.listFiles();
        ArrayList<File> readyUpFile=new ArrayList<>();

        for (int i=0; i<allFile.length;i++) {
//            if (allFile==null){break;}
            System.out.println(allFile[i].getName());
            System.out.println(FilenameUtils.removeExtension(allFile[i].getName()));
            FileUpload file = dbController.findFile(FilenameUtils.removeExtension(allFile[i].getName()));
            if (file!=null && file.upload==0){
                readyUpFile.add(allFile[i]);
                uploadingTableModel.addRow(new String[]{
                        ""+i,allFile[i].getName(),file.upload+"",allFile[i].length()/1024/1024+"mb"
                });
            }else{
                updateStatus("skipping "+allFile[i].getName());
            }

        }
        files = new File[readyUpFile.size()];
        files= readyUpFile.toArray(files);
        System.out.println(readyUpFile.get(0));

        uploadingTable.setModel(uploadingTableModel);
        uploadingTable.getColumnModel().getColumn(0).setMaxWidth(50);
    }

    public static void startThread() throws IOException, SQLException, ClassNotFoundException {
        JFrame frame = new JFrame("UploadManager");
        frame.setContentPane(new UploadManager().container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//        frame.setSize(640,480);
        frame.setLocationRelativeTo(null);
    }
}

