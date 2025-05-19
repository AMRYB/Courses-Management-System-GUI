import java.io.*;
import java.util.*;


public class FileManager {
    private String filePath;
    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    public void write(String data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(data);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }



    public void write(List<String> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String line : data) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public List<String> read() {
        List<String> data = new ArrayList<>();
        File file = new File(filePath);
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (!line.trim().isEmpty()) {
                    data.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return data;
    }
}