package mainproject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

public class JUnitGenerator {

    public static void main(String[] args) {
        // Replace "com.example.MyClass" with the fully qualified name of the class you want to generate tests for
        String className = "mainproject.MyClass";
        
        
        String junitClassName = className + "Test";

        String junitCode = generateJUnitTestClass(className, junitClassName);

        // Replace "src/test/java" with the target directory where you want to save the generated JUnit test class
        String targetDirectory = "src/test/java";
        saveJUnitTestClass(junitClassName, junitCode, targetDirectory);
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
}
