package Config;

import java.io.*;
import java.util.Properties;

/**
 * Created by super on 1/10/2017.
 */
public class ConfigService {
    Properties prop;
    File f;
    private static ConfigService ourInstance = new ConfigService();

    public static ConfigService getInstance() {
        return ourInstance;
    }

    private ConfigService() {
        prop=new Properties();
        f=new File("config.prop");
        if (f.exists()){
            try {
                prop.load(new FileInputStream(f));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String get(String key){
        return prop.getProperty(key);
    }

    public void set(String key,String value){
        prop.setProperty(key, value);
        save();
    }

    private void save() {
        try {
            prop.store(new FileOutputStream(f),"éo thèm comment");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConfigService cs=ConfigService.getInstance();
        cs.set("haha","fsdfsdf");
    }
}
