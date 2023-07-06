import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaClassLister {
    public static void main(String[] args) {
        // Provide the path to the Java project directory
        String projectPath = "path/to/java/project";

        List<String> javaClasses = findJavaClasses(projectPath);

        System.out.println("Java classes found in the project:");
        for (String className : javaClasses) {
            System.out.println(className);
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
}
