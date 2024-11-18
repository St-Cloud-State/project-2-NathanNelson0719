import java.util.Scanner;

public class ClerkMenuState implements SuperState{
    public void run(){
        Scanner scanner = new Scanner(System.in);
        boolean findingNextOption = true;
        WarehouseConsole functionMule = new WarehouseConsole();
        while(findingNextOption){
            //run each choice through warehouse console classes functions, we built them already
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
            switch(choice){
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

                    break;
                case 5:

                    break;
                case 6:
                    OpeningState.client.run(); //this works because once client menu is finished it comes back to this loop so you dont need to know where you came from
                    break;
                case 11:
                    System.out.println("going back to previous state...");
                    findingNextOption = false;
                    break;
                default:
                    System.out.println("invalid input, try again");
            }
        }
    }

    private void clientsWithOutstandingBalance(){
        System.out.println("all clients with outstanding balance...");
        //implement this
    }

    private void receiveClientPayment(Scanner scanner){
        ClientList clients = new ClientList();
        System.out.println("Please login to specific client using client id: ");
        String input = scanner.nextLine();
        Client loggedInClient = clients.search(input);
        if(loggedInClient == null) { 
            System.out.println("failed to find client, returning...");
            return;
        }
        System.out.println("how much do you want to recieve?");
        Double increase = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("added " + increase + " to " + loggedInClient.getBalance());
        loggedInClient.addToBalance(increase);
        System.out.println(loggedInClient);
    }
}