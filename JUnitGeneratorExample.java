import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JUnitGeneratorExample {
    public static void main(String[] args) {
        // Provide the path to the Java project directory
        String projectPath = "path/to/java/project";

        List<String> javaClasses = findJavaClasses(projectPath);

        for (String className : javaClasses) {
            generateJUnitTestClass(className);
        }
    }

    private static List<String> findJavaClasses(String projectPath) {
        List<String> javaClasses = new ArrayList<>();

        File projectDirectory = new File(projectPath);

        if (!projectDirectory.exists() || !projectDirectory.isDirectory()) {
            System.err.println("Invalid project directory: " + projectPath);
            return javaClasses;
        }

        findJavaClassesInDirectory(projectDirectory, "", javaClasses);

        return javaClasses;
    }

    private static void findJavaClassesInDirectory(File directory, String packageName, List<String> javaClasses) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                String subPackageName = packageName + file.getName() + ".";
                findJavaClassesInDirectory(file, subPackageName, javaClasses);
            } else if (file.getName().endsWith(".java")) {
                String className = packageName + file.getName().replace(".java", "");
                javaClasses.add(className);
            }
        }
    }

    private static void generateJUnitTestClass(String className) {
        String testClassName = className + "Test";
        StringBuilder sb = new StringBuilder();

        sb.append("import org.junit.Test;\n");
        sb.append("import static org.junit.Assert.*;\n\n");
        sb.append("public class ").append(testClassName).append(" {\n\n");
        sb.append("\t@Test\n");
        sb.append("\tpublic void test() {\n");
        sb.append("\t\t// TODO: Write test method for ").append(className).append("\n");
        sb.append("\t}\n");
        sb.append("}\n");

        try {
            // Create the test class file in the same package as the original class
            File testClassFile = new File(testClassName.replace(".", "/") + ".java");

            FileWriter writer = new FileWriter(testClassFile);
            writer.write(sb.toString());
            writer.close();

            System.out.println("Generated JUnit test class: " + testClassFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to generate JUnit test class for: " + className);
            e.printStackTrace();
        }
    }
}
