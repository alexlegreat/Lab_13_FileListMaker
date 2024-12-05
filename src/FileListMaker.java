import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileListMaker {
    private static boolean needsToBeSaved = false;
    private static List<String> list = new ArrayList<>();
    private static String currentFileName = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu: A - Add, D - Delete, I - Insert, M - Move, C - Clear, V - View, O - Open, S - Save, Q - Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    addItem(scanner);
                    break;
                case "D":
                    deleteItem(scanner);
                    break;
                case "I":
                    insertItem(scanner);
                    break;
                case "M":
                    moveItem(scanner);
                    break;
                case "C":
                    clearList(scanner);
                    break;
                case "V":
                    viewList();
                    break;
                case "O":
                    openFile(scanner);
                    break;
                case "S":
                    saveFilePrompt(scanner);
                    break;
                case "Q":
                    quitProgram(scanner);
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter item to add: ");
        list.add(scanner.nextLine());
        needsToBeSaved = true;
    }

    private static void deleteItem(Scanner scanner) {
        viewList();
        System.out.print("Enter the index to delete: ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            list.remove(index);
            needsToBeSaved = true;
        } catch (Exception e) {
            System.out.println("Invalid index.");
        }
    }

    private static void insertItem(Scanner scanner) {
        System.out.print("Enter item to insert: ");
        String item = scanner.nextLine();
        System.out.print("Enter position to insert at: ");
        try {
            int position = Integer.parseInt(scanner.nextLine());
            list.add(position, item);
            needsToBeSaved = true;
        } catch (Exception e) {
            System.out.println("Invalid position.");
        }
    }

    private static void moveItem(Scanner scanner) {
        viewList();
        System.out.print("Enter the index to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the new position: ");
        int toIndex = Integer.parseInt(scanner.nextLine());
        try {
            String item = list.remove(fromIndex);
            list.add(toIndex, item);
            needsToBeSaved = true;
        } catch (Exception e) {
            System.out.println("Invalid indices.");
        }
    }

    private static void clearList(Scanner scanner) {
        System.out.print("Are you sure you want to clear the list? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            list.clear();
            needsToBeSaved = true;
        }
    }

    private static void viewList() {
        System.out.println("Current List:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ": " + list.get(i));
        }
    }

    private static void openFile(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("Unsaved changes. Save first? (yes/no): ");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                saveFilePrompt(scanner);
            }
        }

        System.out.print("Enter file name to open: ");
        currentFileName = scanner.nextLine();
        try {
            list = Files.readAllLines(Paths.get(currentFileName));
            needsToBeSaved = false;
            System.out.println("File loaded.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static void saveFilePrompt(Scanner scanner) {
        if (currentFileName.isEmpty()) {
            System.out.print("Enter file name to save as: ");
            currentFileName = scanner.nextLine();
        }
        try {
            Files.write(Paths.get(currentFileName), list);
            needsToBeSaved = false;
            System.out.println("File saved.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void quitProgram(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("Unsaved changes. Save first? (yes/no): ");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                saveFilePrompt(scanner);
            }
        }
        System.out.println("Goodbye!");
    }
}
