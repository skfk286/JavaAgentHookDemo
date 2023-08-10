package agent.util;

import java.io.File;

import agent.core.JavaAgent;


public class JarUtil {
    
    public static File getThisJarFile() {
        String path = "";
        ClassLoader cl = JavaAgent.class.getClassLoader();
        if (cl == null) {
            path = ""
                    + ClassLoader.getSystemClassLoader().getResource(JavaAgent.class.getName().replace('.', '/') + ".class");
        } else {
            path = "" + cl.getResource(JavaAgent.class.getName().replace('.', '/') + ".class");
        }
        try {
            path = path.substring("jar:file:/".length(), path.indexOf("!"));
            if (path.indexOf(':') > 0)
                return new File(path);
            else
                return new File("/" + path);
        } catch (Throwable th) {
            return null;
        }
    }
}
