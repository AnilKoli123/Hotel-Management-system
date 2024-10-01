import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Hotel Management System class
public class HotelManagementSystem {
    private List<Room> rooms = new ArrayList<>(); // List of rooms
    private List<Booking> bookings = new ArrayList<>(); // List of bookings
    private Scanner scanner = new Scanner(System.in);

    // Method to add a room to the system
    public void addRoom() {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt(); // Input room number
        scanner.nextLine(); // Consume the newline
        System.out.print("Enter room type (e.g., Single, Double, Suite): ");
        String type = scanner.nextLine(); // Input room type
        
        rooms.add(new Room(roomNumber, type)); // Add room to list
        System.out.println("Room added successfully.");
    }

    // Method to book a room
    public void bookRoom() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer contact number: ");
        String contact = scanner.nextLine();
        System.out.print("Enter customer address: ");
        String address = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();

        // Display available rooms
        System.out.println("Available rooms:");
        boolean hasAvailableRooms = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room); // Show only available rooms
                hasAvailableRooms = true;
            }
        }

        if (!hasAvailableRooms) {
            System.out.println("No rooms available for booking.");
            return;
        }

        // Choose a room number
        System.out.print("Enter room number to book: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Find the selected room
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                Customer customer = new Customer(name, contact, address, email);
                bookings.add(new Booking(room, customer)); // Add booking to list
                room.setAvailable(false); // Mark room as unavailable
                System.out.println("Room booked successfully for " + customer.getName());
                return;
            }
        }

        System.out.println("Room not available or invalid room number.");
    }

    // Method to view all bookings
    public void viewBookings() {
        System.out.println("Current bookings:");
        if (bookings.isEmpty()) {
            System.out.println("No bookings made yet.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking); // Display each booking
            }
        }
    }

    // Method to cancel a booking
    public void cancelBooking() {
        System.out.print("Enter the customer's name for the booking to cancel: ");
        String name = scanner.nextLine();
        
        Booking foundBooking = null;
        for (Booking booking : bookings) {
            if (booking.getCustomer().getName().equalsIgnoreCase(name)) {
                foundBooking = booking;
                break;
            }
        }

        if (foundBooking != null) {
            bookings.remove(foundBooking); // Remove booking
            foundBooking.getRoom().setAvailable(true); // Mark the room as available again
            System.out.println("Booking for " + name + " has been cancelled.");
        } else {
            System.out.println("No booking found under the name " + name);
        }
    }

    // Method to search for a booking by customer name
    public void searchBooking() {
        System.out.print("Enter the customer's name to search for: ");
        String name = scanner.nextLine();
        
        for (Booking booking : bookings) {
            if (booking.getCustomer().getName().equalsIgnoreCase(name)) {
                System.out.println("Booking found: " + booking);
                return;
            }
        }

        System.out.println("No booking found for the customer named " + name);
    }

    // Method to view available rooms
    public void viewAvailableRooms() {
        System.out.println("Available rooms:");
        boolean hasAvailableRooms = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room); // Show available rooms
                hasAvailableRooms = true;
            }
        }

        if (!hasAvailableRooms) {
            System.out.println("No rooms available.");
        }
    }

    // Method to update customer details
    public void updateCustomerDetails() {
        System.out.print("Enter the customer's name to update details: ");
        String name = scanner.nextLine();

        for (Booking booking : bookings) {
            if (booking.getCustomer().getName().equalsIgnoreCase(name)) {
                Customer customer = booking.getCustomer();
                System.out.println("Customer found: " + customer);
                
                System.out.print("Enter new contact number (leave blank to keep the current): ");
                String newContact = scanner.nextLine();
                if (!newContact.isBlank()) {
                    customer.setContact(newContact); // Update contact if provided
                }

                System.out.print("Enter new address (leave blank to keep the current): ");
                String newAddress = scanner.nextLine();
                if (!newAddress.isBlank()) {
                    customer.setAddress(newAddress); // Update address if provided
                }

                System.out.print("Enter new email (leave blank to keep the current): ");
                String newEmail = scanner.nextLine();
                if (!newEmail.isBlank()) {
                    customer.setEmail(newEmail); // Update email if provided
                }

                System.out.println("Customer details updated successfully.");
                return;
            }
        }

        System.out.println("No customer found with the name " + name);
    }

    // Main method to start the system
    public static void main(String[] args) {
        HotelManagementSystem hms = new HotelManagementSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display menu
            System.out.println("\nHotel Management System");
            System.out.println("1. Add Room");
            System.out.println("2. Book Room");
            System.out.println("3. View Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Search Booking by Customer Name");
            System.out.println("6. View Available Rooms");
            System.out.println("7. Update Customer Details");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Execute based on choice
            switch (choice) {
                case 1:
                    hms.addRoom();
                    break;
                case 2:
                    hms.bookRoom();
                    break;
                case 3:
                    hms.viewBookings();
                    break;
                case 4:
                    hms.cancelBooking();
                    break;
                case 5:
                    hms.searchBooking();
                    break;
                case 6:
                    hms.viewAvailableRooms();
                    break;
                case 7:
                    hms.updateCustomerDetails();
                    break;
                case 8:
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }
}

// Room class to represent a room
class Room {
    private int roomNumber; // Room number
    private String type; // Room type
    private boolean isAvailable; // Room availability

    public Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isAvailable = true; // Room is available by default
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

// Customer class to represent a customer
class Customer {
    private String name; // Customer name
    private String contact; // Customer contact number
    private String address; // Customer address
    private String email; // Customer email

    public Customer(String name, String contact, String address, String email) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

// Booking class to represent a booking
class Booking {
    private Room room; // Booked room
    private Customer customer; // Customer who booked the room

    public Booking(Room room, Customer customer) {
        this.room = room;
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "room=" + room +
                ", customer=" + customer +
                '}';
    }
}
