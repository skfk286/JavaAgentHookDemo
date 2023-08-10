package agent.util;

import java.io.File;
import java.util.Properties;


public class Configure {
    private static final Configure instance;
    
    public static String agent_dir_path;
    
    public Properties property = new Properties();
    
    static {
        File jarFile = JarUtil.getThisJarFile();
        if (jarFile == null) {
            agent_dir_path = new File("./").getAbsolutePath();
        } else {
            agent_dir_path = jarFile.getParent();
        }
        
        instance = new Configure();
    }
    
    public static final Configure getInstance() {
        return instance;
    }
    
    
    public static void main(String[] args) {
        Configure configure = Configure.getInstance();
        
        System.out.println(configure.agent_dir_path);
    }
}
