/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileandfolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Config.ConfigService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Viet Bac
 */
public class FileAndFolder {

    public static List<String> getAllFille() {
        List<String> lstFileName = new ArrayList<>();
        File c = new File(ConfigService.getInstance().get("savePath"));
        File[] listFiles = c.listFiles();
        for (File listFile : listFiles) {
            lstFileName.add(FilenameUtils.removeExtension(listFile.getName()));
        }
        return lstFileName;
    }

    public static void main(String[] args) throws MalformedURLException, IOException {
//        System.out.println("123");
//        URL url = new URL("https://r3---sn-n4v7sn7z.googlevideo.com/videoplayback?id=409a36d06148bc5c&itag=18&source=webdrive&requiressl=yes&ttl=transient&pl=26&mime=video/mp4&lmt=1474342643638415&ip=45.32.112.145&ipbits=24&expire=1484855681&sparams=expire,id,ip,ipbits,itag,lmt,mime,mm,mn,ms,mv,nh,pl,requiressl,safm,source,ttl&signature=4CC8405DC31432A04DA5AFC8A0C5256080F8A0A9.1E566A11D7E775B7045222FB86906E6F84812E8C&key=cms1&app=explorer&cms_redirect=yes&mm=31&mn=sn-n4v7sn7z&ms=au&mt=1484841354&mv=m&nh=IgpwZjAxLnNqYzA3Kg8yMTYuMjIxLjE1Ny4yNDU&safm=0&title=123");
//
//        FileUtils.copyURLToFile(url, new File("C:\\Download\\123.mp4"));
//        Path targetPath = new File("C:\\Download" + "1234.mp4").toPath();
//
//        Files.copy(url.openStream(), targetPath,
//                StandardCopyOption.REPLACE_EXISTING);
//        System.out.println("123");
    }
}
