import java.util.Scanner;
import java.util.Iterator;

public class ClerkMenuState implements SuperState {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean findingNextOption = true;
        WarehouseConsole functionMule = new WarehouseConsole(); // Utility for existing functionality
        ClientList clients = new ClientList(); // Reference to the client list

        while (findingNextOption) {
            System.out.println("Where do you want to go as a clerk?");
            System.out.println("1: add a client");
            System.out.println("2: show products listing");
            System.out.println("3: show clients list");
            System.out.println("4: show clients with outstanding balance");
            System.out.println("5: record payment from a client");
            System.out.println("6: become a client");
            System.out.println("11: to logout");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    functionMule.manageClients(scanner);
                    break;
                case 2:
                    functionMule.displayProducts();
                    break;
                case 3:
                    functionMule.displayClients();
                    break;
                case 4:
                    //clientsWithOutstandingBalance(clients);
                    break;
                case 5:
                    receiveClientPayment(scanner);
                    break;
                case 6:
                    OpeningState.client.run();
                    break;
                case 11:
                    System.out.println("Going back to previous state...");
                    findingNextOption = false;
                    break;
                default:
                    System.out.println("Invalid input, try again.");
            }
        }
    }

    //private void clientsWithOutstandingBalance() {
        //System.out.println("Clients with outstanding balance:");
        //ClientList clients = new ClientList(); // Get the shared instance
        //Iterator<Client> clientIterator = clients.getClients();
        //boolean foundOutstanding = false;
    
        //while (clientIterator.hasNext()) {
            //Client client = clientIterator.next();
            //if (client.getBalance() > 0) { // Check if the client has a balance
                //foundOutstanding = true;
                //System.out.println("Client ID: " + client.getID() +
                        //" | Name: " + client.getName() +
                        //" | Outstanding Balance: $" + client.getBalance());
            //}
        //}
    
        //if (!foundOutstanding) {
            //System.out.println("No clients with outstanding balance.");
        //}
    //}
    

    private void receiveClientPayment(Scanner scanner) {
        ClientList clients = new ClientList();
        System.out.println("Please login to a specific client using client ID: ");
        String input = scanner.nextLine();
        Client loggedInClient = clients.search(input);

        if (loggedInClient == null) {
            System.out.println("Failed to find client, returning...");
            return;
        }

        System.out.println("How much do you want to receive?");
        Double increase = scanner.nextDouble();
        scanner.nextLine();
        loggedInClient.addToBalance(increase);
        System.out.println("Added $" + increase + " to " + loggedInClient.getName() + "'s account.");
        System.out.println("Updated Balance: $" + loggedInClient.getBalance());
    }
}
