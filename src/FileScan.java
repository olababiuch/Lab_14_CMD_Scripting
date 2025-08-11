import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileScan {

    public static void main(String[] args) {
        File selectedFile = null;
        Path file = null;

        if (args.length > 0) {
            file = Paths.get(System.getProperty("user.dir"), "src", args[0]);
            selectedFile = file.toFile();
            if (!selectedFile.exists()) {
                System.out.println("File not found: " + selectedFile.getAbsolutePath());
                return;
            }
            System.out.println("Scanning file from argument: " + selectedFile.getAbsolutePath());
            processFile(file, selectedFile);
        } else {

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "src"));

            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                file = selectedFile.toPath();
                System.out.println("Scanning file from chooser: " + selectedFile.getAbsolutePath());
                processFile(file, selectedFile);
            } else {
                System.out.println("No file selected.");
            }
        }
    }

    private static void processFile(Path file, File selectedFile) {
        String rec;
        int lineCount = 0, wordCount = 0, charCount = 0;

        try (
                InputStream in = new BufferedInputStream(Files.newInputStream(file));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            System.out.println("\nFile contents:");
            while ((rec = reader.readLine()) != null) {
                System.out.println(rec);
                lineCount++;
                if (!rec.trim().isEmpty()) {
                    wordCount += rec.trim().split("\\s+").length;
                }
                charCount += rec.length();
            }
            System.out.println("\nFile stats:");
            System.out.println("File name: " + selectedFile.getName());
            System.out.println("Lines: " + lineCount);
            System.out.println("Words: " + wordCount);
            System.out.println("Characters: " + charCount);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
