import database.Database;
import ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {

        Database.initializeDatabase();

        ConsoleUI consoleUI = new ConsoleUI();

        consoleUI.start();
    }
}