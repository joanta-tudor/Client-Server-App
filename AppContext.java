package utils;

import java.io.FileReader;
import java.util.Properties;

public class AppContext {
    private static final String path="C:\\Users\\Tudor\\Desktop\\Facultate\\An II Semestru 2\\MPP\\Networking\\AgentiePersistance\\src\\main\\resources\\config.properties";
    private static Properties prop=null;

    private static Properties load()
    {
        Properties properties=new Properties();
        try{
            properties.load(new FileReader(path));
        }catch(Exception e) {throw new RuntimeException();}
        return properties;
    }

    public static Properties getProp()
    {
        if (prop==null)
            prop=load();
        return prop;
    }
}
