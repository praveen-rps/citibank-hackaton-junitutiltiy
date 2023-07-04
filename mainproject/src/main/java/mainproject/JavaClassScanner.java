package mainproject;

import org.reflections.Reflections;

import java.util.Set;

public class JavaClassScanner {
    public static void main(String[] args) {
        Set<String> classNames = getAllJavaClassNames();
        for (String className : classNames) {
            System.out.println(className);
        }
    }

    public static Set<String> getAllJavaClassNames() {
        // Replace "com.example" with the base package of your project or any other package you want to scan
        String basePackage = "mainproject";

        Reflections reflections = new Reflections(basePackage);
        // Get all class names in the specified package and its sub-packages
        return reflections.getAllTypes();
    }
}
