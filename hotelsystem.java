import java.io.*;
import java.util.Scanner;

public class HotelSystem {
    // Basic arrays to track room data
    static int[] roomNumbers = {101, 102, 201, 202, 301};
    static String[] roomCategories = {"Standard", "Standard", "Deluxe", "Deluxe", "Suite"};
    static double[] roomPrices = {80.0, 80.0, 150.0, 150.0, 300.0};
    static boolean[] roomAvailability = {true, true, true, true, true};

    // Arrays to track active bookings (Max 100 entries for simplicity)
    static String[] bookingIds = new String[100];
    static String[] guestNames = new String[100];
    static int[] bookedRooms = new String[100].length == 100 ? new int[100] : new int[100]; 
    static int bookingCount = 0;

    public static void main(String[] args) {
        loadData(); // Re-loads existing database files on startup
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n🏢 === HOTEL RESERVATION SYSTEM ===");
            System.out.println("1. Search Rooms by Category");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // Clear memory buffer

            if (choice == 5) {
                System.out.println("Exiting System. Goodbye!");
                break;
            }

            switch (choice) {
                case 1: // SEARCH ROOMS
                    System.out.print("Enter category (Standard, Deluxe, Suite): ");
                    String cat = sc.nextLine();
                    System.out.println("\n--- Available " + cat + " Rooms ---");
                    for (int i = 0; i < roomNumbers.length; i++) {
                        if (roomCategories[i].equalsIgnoreCase(cat) && roomAvailability[i]) {
                            System.out.println("Room #" + roomNumbers[i] + " | Price: $" + roomPrices[i] + "/night");
                        }
                    }
                    break;

                case 2: // BOOK A ROOM
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Room Number to book: ");
                    int roomChoice = sc.nextInt();
                    System.out.print("Enter number of nights: ");
                    int nights = sc.nextInt();

                    int roomIndex = -1;
                    for (int i = 0; i < roomNumbers.length; i++) {
                        if (roomNumbers[i] == roomChoice && roomAvailability[i]) {
                            roomIndex = i;
                            break;
                        }
                    }

                    if (roomIndex != -1) {
                        double totalCost = roomPrices[roomIndex] * nights;
                        System.out.printf("Total Cost: $%.2f\n", totalCost);
                        System.out.println("Processing simulated card payment... [Approved]");

                        // Generate an ID and save booking info
                        String id = "RES" + (1000 + (int)(Math.random() * 9000));
                        bookingIds[bookingCount] = id;
                        guestNames[bookingCount] = name;
                        bookedRooms[bookingCount] = roomChoice;
                        bookingCount++;

                        roomAvailability[roomIndex] = false; // Room is now occupied
                        saveData(); // Commit updates down to files
                        System.out.println("Success! Your Reservation ID is: " + id);
                    } else {
                        System.out.println("Error: Room is either unavailable or doesn't exist.");
                    }
                    break;

                case 3: // CANCEL RESERVATION
                    System.out.print("Enter Reservation ID to cancel: ");
                    String cancelId = sc.nextLine();
                    int foundIndex = -1;

                    for (int i = 0; i < bookingCount; i++) {
                        if (bookingIds[i] != null && bookingIds[i].equalsIgnoreCase(cancelId)) {
                            foundIndex = i;
                            break;
                        }
                    }

                    if (foundIndex != -1) {
                        int freedRoom = bookedRooms[foundIndex];
                        // Make room available again
                        for (int i = 0; i < roomNumbers.length; i++) {
                            if (roomNumbers[i] == freedRoom) roomAvailability[i] = true;
                        }
                        // Remove booking record
                        bookingIds[foundIndex] = null;
                        saveData();
                        System.out.println("Success! Reservation cancelled cleanly.");
                    } else {
                        System.out.println("Error: Reservation ID not found.");
                    }
                    break;

                case 4: // VIEW BOOKING DETAILS
                    System.out.print("Enter Reservation ID: ");
                    String searchId = sc.nextLine();
                    boolean found = false;

                    for (int i = 0; i < bookingCount; i++) {
                        if (bookingIds[i] != null && bookingIds[i].equalsIgnoreCase(searchId)) {
                            System.out.println("\n--- Booking Record Found ---");
                            System.out.println("ID: " + bookingIds[i]);
                            System.out.println("Guest Name: " + guestNames[i]);
                            System.out.println("Room Number: " + bookedRooms[i]);
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("Error: No matching record found.");
                    break;

                default:
                    System.out.println("Invalid option! Please pick 1 to 5.");
            }
        }
        sc.close();
    }

    // Easy Text File Writing (Saves data)
    static void saveData() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("hotel_data.txt"));
            // Save room availability state
            for (boolean b : roomAvailability) writer.print(b + ",");
            writer.println();
            // Save active booking loops
            for (int i = 0; i < bookingCount; i++) {
                if (bookingIds[i] != null) {
                    writer.println(bookingIds[i] + "," + guestNames[i] + "," + bookedRooms[i]);
                }
            }
            writer.close();
        } catch (IOException ignored) {}
    }

    // Easy Text File Reading (Loads data)
    static void loadData() {
        File file = new File("hotel_data.txt");
        if (!file.exists()) return; // Skip if program is running for the first time

        try (Scanner fScanner = new Scanner(file)) {
            if (fScanner.hasNextLine()) {
                String[] states = fScanner.nextLine().split(",");
                for (int i = 0; i < roomAvailability.length; i++) {
                    roomAvailability[i] = Boolean.parseBoolean(states[i]);
                }
            }
            while (fScanner.hasNextLine()) {
                String[] parts = fScanner.nextLine().split(",");
                bookingIds[bookingCount] = parts[0];
                guestNames[bookingCount] = parts[1];
                bookedRooms[bookingCount] = Integer.parseInt(parts[2]);
                bookingCount++;
            }
        } catch (FileNotFoundException ignored) {}
    }
}
