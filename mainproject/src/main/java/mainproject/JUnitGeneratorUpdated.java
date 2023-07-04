package mainproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JUnitGeneratorUpdated {

    public static void main(String[] args) {
        // Replace "com.example.MyClass" with the fully qualified name of the class you want to generate tests for
        String className = "mainproject.JavaClassNamesInClasspath";
        String packageName = "mainproject"; 
        List<Class<?>> classes = getClassesInPackage(packageName);
        
        for(Class<?> cls : classes ) {
        String junitClassName = cls + "Test";

        String junitCode = generateJUnitTestClass(className, junitClassName);

        // Replace "src/test/java" with the target directory where you want to save the generated JUnit test class
        String targetDirectory = "src/test/java";
        saveJUnitTestClass(junitClassName, junitCode, targetDirectory);
        }
    }

    private static String generateJUnitTestClass(String className, String junitClassName) {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("import org.junit.Test;\n\n");
        codeBuilder.append("public class ").append(junitClassName).append(" {\n\n");
        codeBuilder.append("    @Test\n");
        codeBuilder.append("    public void test").append(junitClassName).append("() {\n");

        // Use reflection to get the methods of the specified class
        try {
            Class<?> targetClass = Class.forName(className);
            Method[] methods = targetClass.getDeclaredMethods();

            for (Method method : methods) {
                codeBuilder.append("        // TODO: Add test cases for ").append(method.getName()).append("\n");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + className);
            e.printStackTrace();
        }

        codeBuilder.append("    }\n");
        codeBuilder.append("}\n");

        return codeBuilder.toString();
    }

    private static void saveJUnitTestClass(String junitClassName, String code, String targetDirectory) {
        String fileName = targetDirectory + "/" + junitClassName + ".java";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(code);
            System.out.println("JUnit test class generated successfully: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving JUnit test class: " + fileName);
            e.printStackTrace();
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
