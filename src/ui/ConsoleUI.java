package ui;

import model.Customer;
import model.Movie;
import model.Show;
import model.Theatre;

import service.CustomerService;
import service.MovieService;
import service.ShowService;
import service.TheatreService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final MovieService movieService;
    private final TheatreService theatreService;
    private final ShowService showService;
    private final CustomerService customerService;
    private final Scanner scanner;

    public ConsoleUI() {
        movieService = new MovieService();
        theatreService = new TheatreService();
        showService = new ShowService();
        customerService = new CustomerService();
        scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            displayMainMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    movieMenu();
                    break;

                case 2:
                    theatreMenu();
                    break;

                case 3:
                    showMenu();
                    break;

                case 4:
                    customerMenu();
                    break;

                case 5:
                    running = false;
                    System.out.println(
                            "\nThank you for using Movie Ticket Booking System!"
                    );
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // ========================================
    // MAIN MENU
    // ========================================

    private void displayMainMenu() {

        System.out.println("\n========================================");
        System.out.println("       MOVIE TICKET BOOKING SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Manage Movies");
        System.out.println("2. Manage Theatres");
        System.out.println("3. Manage Shows");
        System.out.println("4. Manage Customers");
        System.out.println("5. Exit");
        System.out.println("========================================");
    }

    // ========================================
    // MOVIE MANAGEMENT
    // ========================================

    private void movieMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n========================================");
            System.out.println("             MOVIE MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Movie");
            System.out.println("2. View All Movies");
            System.out.println("3. Find Movie");
            System.out.println("4. Update Movie");
            System.out.println("5. Delete Movie");
            System.out.println("6. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addMovie();
                    break;

                case 2:
                    viewMovies();
                    break;

                case 3:
                    findMovie();
                    break;

                case 4:
                    updateMovie();
                    break;

                case 5:
                    deleteMovie();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addMovie() {

        System.out.println("\n--- Add Movie ---");

        String title = readString("Title: ");
        String genre = readString("Genre: ");
        String language = readString("Language: ");
        int duration = readInt("Duration (minutes): ");
        double rating = readDouble("Rating (0-10): ");

        movieService.addMovie(
                title,
                genre,
                language,
                duration,
                rating
        );
    }

    private void viewMovies() {

        System.out.println("\n--- Movies ---");

        List<Movie> movies = movieService.getAllMovies();

        if (movies.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        System.out.printf(
                "%-5s %-25s %-15s %-12s %-10s %-8s%n",
                "ID",
                "TITLE",
                "GENRE",
                "LANGUAGE",
                "DURATION",
                "RATING"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Movie movie : movies) {

            System.out.printf(
                    "%-5d %-25s %-15s %-12s %-10d %-8.1f%n",
                    movie.getId(),
                    movie.getTitle(),
                    movie.getGenre(),
                    movie.getLanguage(),
                    movie.getDuration(),
                    movie.getRating()
            );
        }
    }

    private void findMovie() {

        int id = readInt("Enter movie ID: ");

        Movie movie = movieService.getMovieById(id);

        if (movie == null) {
            System.out.println("Movie not found.");
        } else {
            System.out.println("\nMovie found:");
            System.out.println(movie);
        }
    }

    private void updateMovie() {

        int id = readInt("Enter movie ID: ");

        Movie movie = movieService.getMovieById(id);

        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        System.out.println("\nCurrent movie:");
        System.out.println(movie);

        String title = readString("New title: ");
        String genre = readString("New genre: ");
        String language = readString("New language: ");
        int duration = readInt("New duration (minutes): ");
        double rating = readDouble("New rating (0-10): ");

        movieService.updateMovie(
                id,
                title,
                genre,
                language,
                duration,
                rating
        );
    }

    private void deleteMovie() {

        int id = readInt("Enter movie ID: ");

        Movie movie = movieService.getMovieById(id);

        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        System.out.println(movie);

        String confirmation =
                readString("Delete this movie? (yes/no): ");

        if (confirmation.equalsIgnoreCase("yes")) {
            movieService.deleteMovie(id);
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ========================================
    // THEATRE MANAGEMENT
    // ========================================

    private void theatreMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n========================================");
            System.out.println("           THEATRE MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Theatre");
            System.out.println("2. View All Theatres");
            System.out.println("3. Find Theatre");
            System.out.println("4. Update Theatre");
            System.out.println("5. Delete Theatre");
            System.out.println("6. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addTheatre();
                    break;

                case 2:
                    viewTheatres();
                    break;

                case 3:
                    findTheatre();
                    break;

                case 4:
                    updateTheatre();
                    break;

                case 5:
                    deleteTheatre();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addTheatre() {

        System.out.println("\n--- Add Theatre ---");

        String name = readString("Theatre name: ");
        String location = readString("Location: ");
        int totalSeats = readInt("Total seats: ");

        theatreService.addTheatre(
                name,
                location,
                totalSeats
        );
    }

    private void viewTheatres() {

        System.out.println("\n--- Theatres ---");

        List<Theatre> theatres =
                theatreService.getAllTheatres();

        if (theatres.isEmpty()) {
            System.out.println("No theatres found.");
            return;
        }

        System.out.printf(
                "%-5s %-25s %-20s %-12s%n",
                "ID",
                "NAME",
                "LOCATION",
                "SEATS"
        );

        System.out.println(
                "------------------------------------------------------------"
        );

        for (Theatre theatre : theatres) {

            System.out.printf(
                    "%-5d %-25s %-20s %-12d%n",
                    theatre.getId(),
                    theatre.getName(),
                    theatre.getLocation(),
                    theatre.getTotalSeats()
            );
        }
    }

    private void findTheatre() {

        int id = readInt("Enter theatre ID: ");

        Theatre theatre =
                theatreService.getTheatreById(id);

        if (theatre == null) {
            System.out.println("Theatre not found.");
        } else {
            System.out.println("\nTheatre found:");
            System.out.println(theatre);
        }
    }

    private void updateTheatre() {

        int id = readInt("Enter theatre ID: ");

        Theatre theatre =
                theatreService.getTheatreById(id);

        if (theatre == null) {
            System.out.println("Theatre not found.");
            return;
        }

        System.out.println("\nCurrent theatre:");
        System.out.println(theatre);

        String name = readString("New name: ");
        String location = readString("New location: ");
        int totalSeats = readInt("New total seats: ");

        theatreService.updateTheatre(
                id,
                name,
                location,
                totalSeats
        );
    }

    private void deleteTheatre() {

        int id = readInt("Enter theatre ID: ");

        Theatre theatre =
                theatreService.getTheatreById(id);

        if (theatre == null) {
            System.out.println("Theatre not found.");
            return;
        }

        System.out.println(theatre);

        String confirmation =
                readString("Delete this theatre? (yes/no): ");

        if (confirmation.equalsIgnoreCase("yes")) {
            theatreService.deleteTheatre(id);
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ========================================
    // SHOW MANAGEMENT
    // ========================================

    private void showMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n========================================");
            System.out.println("             SHOW MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Show");
            System.out.println("2. View All Shows");
            System.out.println("3. Find Show");
            System.out.println("4. Update Show");
            System.out.println("5. Delete Show");
            System.out.println("6. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addShow();
                    break;

                case 2:
                    viewShows();
                    break;

                case 3:
                    findShow();
                    break;

                case 4:
                    updateShow();
                    break;

                case 5:
                    deleteShow();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addShow() {

        System.out.println("\n--- Add Show ---");

        viewMovies();

        int movieId =
                readInt("Select movie ID: ");

        viewTheatres();

        int theatreId =
                readInt("Select theatre ID: ");

        String showTime =
                readString("Show time: ");

        double ticketPrice =
                readDouble("Ticket price: ");

        showService.addShow(
                movieId,
                theatreId,
                showTime,
                ticketPrice
        );
    }

    private void viewShows() {

        System.out.println("\n--- Shows ---");

        List<Show> shows =
                showService.getAllShows();

        if (shows.isEmpty()) {
            System.out.println("No shows found.");
            return;
        }

        System.out.printf(
                "%-5s %-25s %-25s %-15s %-10s%n",
                "ID",
                "MOVIE",
                "THEATRE",
                "TIME",
                "PRICE"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Show show : shows) {

            Movie movie =
                    movieService.getMovieById(show.getMovieId());

            Theatre theatre =
                    theatreService.getTheatreById(show.getTheatreId());

            String movieName =
                    movie != null
                            ? movie.getTitle()
                            : "Unknown";

            String theatreName =
                    theatre != null
                            ? theatre.getName()
                            : "Unknown";

            System.out.printf(
                    "%-5d %-25s %-25s %-15s ₹%-9.2f%n",
                    show.getId(),
                    movieName,
                    theatreName,
                    show.getShowTime(),
                    show.getTicketPrice()
            );
        }
    }

    private void findShow() {

        int id = readInt("Enter show ID: ");

        Show show =
                showService.getShowById(id);

        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        Movie movie =
                movieService.getMovieById(show.getMovieId());

        Theatre theatre =
                theatreService.getTheatreById(show.getTheatreId());

        System.out.println("\n--- Show Details ---");
        System.out.println("Show ID: " + show.getId());

        System.out.println(
                "Movie: " +
                        (movie != null ? movie.getTitle() : "Unknown")
        );

        System.out.println(
                "Theatre: " +
                        (theatre != null ? theatre.getName() : "Unknown")
        );

        System.out.println("Time: " + show.getShowTime());
        System.out.println(
                "Ticket Price: ₹" + show.getTicketPrice()
        );
    }

    private void updateShow() {

        int id = readInt("Enter show ID: ");

        Show show =
                showService.getShowById(id);

        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        System.out.println("\nCurrent show details:");

        Movie movie =
                movieService.getMovieById(show.getMovieId());

        Theatre theatre =
                theatreService.getTheatreById(show.getTheatreId());

        System.out.println(
                "Movie: " +
                        (movie != null ? movie.getTitle() : "Unknown")
        );

        System.out.println(
                "Theatre: " +
                        (theatre != null ? theatre.getName() : "Unknown")
        );

        System.out.println("Time: " + show.getShowTime());
        System.out.println(
                "Ticket Price: ₹" + show.getTicketPrice()
        );

        viewMovies();

        int movieId =
                readInt("New movie ID: ");

        viewTheatres();

        int theatreId =
                readInt("New theatre ID: ");

        String showTime =
                readString("New show time: ");

        double ticketPrice =
                readDouble("New ticket price: ");

        showService.updateShow(
                id,
                movieId,
                theatreId,
                showTime,
                ticketPrice
        );
    }

    private void deleteShow() {

        int id = readInt("Enter show ID: ");

        Show show =
                showService.getShowById(id);

        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        System.out.println("\nShow found.");

        String confirmation =
                readString("Delete this show? (yes/no): ");

        if (confirmation.equalsIgnoreCase("yes")) {
            showService.deleteShow(id);
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ========================================
    // CUSTOMER MANAGEMENT
    // ========================================

    private void customerMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n========================================");
            System.out.println("          CUSTOMER MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Find Customer");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addCustomer();
                    break;

                case 2:
                    viewCustomers();
                    break;

                case 3:
                    findCustomer();
                    break;

                case 4:
                    updateCustomer();
                    break;

                case 5:
                    deleteCustomer();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addCustomer() {

        System.out.println("\n--- Add Customer ---");

        String name =
                readString("Name: ");

        String email =
                readString("Email: ");

        String phone =
                readString("Phone: ");

        customerService.addCustomer(
                name,
                email,
                phone
        );
    }

    private void viewCustomers() {

        System.out.println("\n--- Customers ---");

        List<Customer> customers =
                customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.printf(
                "%-5s %-25s %-30s %-15s%n",
                "ID",
                "NAME",
                "EMAIL",
                "PHONE"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Customer customer : customers) {

            System.out.printf(
                    "%-5d %-25s %-30s %-15s%n",
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone()
            );
        }
    }

    private void findCustomer() {

        int id =
                readInt("Enter customer ID: ");

        Customer customer =
                customerService.getCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
        } else {
            System.out.println("\nCustomer found:");
            System.out.println(customer);
        }
    }

    private void updateCustomer() {

        int id =
                readInt("Enter customer ID: ");

        Customer customer =
                customerService.getCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("\nCurrent customer:");
        System.out.println(customer);

        String name =
                readString("New name: ");

        String email =
                readString("New email: ");

        String phone =
                readString("New phone: ");

        customerService.updateCustomer(
                id,
                name,
                email,
                phone
        );
    }

    private void deleteCustomer() {

        int id =
                readInt("Enter customer ID: ");

        Customer customer =
                customerService.getCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println(customer);

        String confirmation =
                readString("Delete this customer? (yes/no): ");

        if (confirmation.equalsIgnoreCase("yes")) {
            customerService.deleteCustomer(id);
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ========================================
    // INPUT METHODS
    // ========================================

    private String readString(String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }

    private int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid integer."
                );
            }
        }
    }

    private double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}