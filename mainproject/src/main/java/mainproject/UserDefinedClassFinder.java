package mainproject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserDefinedClassFinder {

    public static void main(String[] args) {
        String packageName = "mainproject"; // Replace with your package name
        List<Class<?>> classes = getClassesInPackage(packageName);
        
        // Print the class names
        for (Class<?> cls : classes) {
            System.out.println(cls.getName());
        }
    }
    
    public static List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File packageDir = new File(classLoader.getResource(path).getFile());
        
        if (packageDir.exists()) {
            File[] files = packageDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        try {
                            Class<?> cls = Class.forName(className);
                            classes.add(cls);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        
        return classes;
    }
}
