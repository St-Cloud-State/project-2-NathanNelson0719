import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class ClientMenuState implements SuperState{
    public void run(){
        Scanner scanner = new Scanner(System.in);
        ClientList clients = new ClientList();
        System.out.println("Please login to specific client using client id: ");
        String input = scanner.nextLine();
        Client loggedInClient = clients.search(input);
        if(loggedInClient == null) { 
            System.out.println("failed to log in, returning...");
            return;
        }

        WarehouseConsole functionMule = new WarehouseConsole();
        boolean findingNextOption = true;
        while(findingNextOption){
            //run each choice through warehouse console classes functions, we built them already
            System.out.println("Where do you want to go as client?");
            System.out.println("1: client details");
            System.out.println("2: show products listing");
            System.out.println("3: show transactions");
            System.out.println("4: add item to wishlist");
            System.out.println("5: display wishlist");
            System.out.println("6: place an order");
            System.out.println("11: to logout");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1: 
                    displayClientDetails(loggedInClient);
                    break;
                case 2:
                    functionMule.displayProducts();
                    break;
                case 3:
                    List<Invoice> temp = Invoice.getInvoicesByClientID(loggedInClient.getID());
                    for(Invoice in : temp) {
                        System.out.println(in.getInvoiceDetails());
                    }
                    break;
                case 4:
                    functionMule.addProductToWishlist(scanner, loggedInClient);
                    break;
                case 5:
                    functionMule.displayClientWishlist(loggedInClient);
                    break;
                case 6:
                    functionMule.processWishlistPurchase(loggedInClient);
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

    private void displayClientDetails(Client client){
        System.out.println("Displaying client details... ");
        System.out.println(client);
    }
}