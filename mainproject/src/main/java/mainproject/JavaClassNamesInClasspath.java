package mainproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JavaClassNamesInClasspath {

    public static void main(String[] args) {
        List<String> classNames = getAllJavaClassNamesInClasspath();
        for (String className : classNames) {
            System.out.println(className);
        }
    }

    public static List<String> getAllJavaClassNamesInClasspath() {
        List<String> classNames = new ArrayList<>();
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);

        for (String classpathEntry : classpathEntries) {
            try {
                if (classpathEntry.endsWith(".jar")) {
                    classNames.addAll(getJavaClassNamesFromJar(classpathEntry));
                } else {
                    classNames.addAll(getJavaClassNamesFromDirectory(classpathEntry));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return classNames;
    }

    private static List<String> getJavaClassNamesFromJar(String jarPath) throws IOException {
        List<String> classNames = new ArrayList<>();
        try (java.util.jar.JarFile jarFile = new java.util.jar.JarFile(jarPath)) {
            java.util.Enumeration<java.util.jar.JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                java.util.jar.JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.');
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
            }
        }
        return classNames;
    }

    private static List<String> getJavaClassNamesFromDirectory(String directoryPath) throws IOException {
        List<String> classNames = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    String className = file.getName().substring(0, file.getName().length() - ".class".length());
                    classNames.add(className);
                }
            }
        }
        return classNames;
    }
}
