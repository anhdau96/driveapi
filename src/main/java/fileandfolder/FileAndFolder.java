/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileandfolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Viet Bac
 */
public class FileAndFolder {
    public static List<String> getAllFille(){
        List<String> lstFileName = new ArrayList<>();
        File c = new File("C:\\Download");
        File[] listFiles = c.listFiles();
        for (File listFile : listFiles) {
            lstFileName.add(FilenameUtils.removeExtension(listFile.getName()));
        }
        return lstFileName;
    }
}
