import java.util.Scanner;
import java.util.Iterator;

public class ManagerMenuState implements SuperState{
    public void run(){
        Scanner scanner = new Scanner(System.in);
        boolean findingNextOption = true;
        WarehouseConsole functionMule = new WarehouseConsole();
        while(findingNextOption){
            //run each choice through warehouse console classes functions, we built them already
            System.out.println("Where do you want to go as a manager?");
            System.out.println("1: add a product");
            System.out.println("2: display waitlist for a product");
            System.out.println("3: recieve product shipment");
            System.out.println("4: become a clerk");
            System.out.println("11: to logout");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1: 
                    functionMule.manageProducts(scanner);
                    break;
                case 2:
                    displayProductWaitlist(scanner);
                    break;
                case 3:
                    functionMule.receiveProductShipment(scanner);
                    break;
                case 4:
                    OpeningState.clerk.run(); //this works because once clerk menu is finished it comes back to this loop so you dont need to know where you came from
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

    private void displayProductWaitlist(Scanner scanner){
        System.out.println("here is a listing of all products and their id: ");
        Catalog temp = Catalog.getInstance();
        Iterator<Product> products = temp.getAllProducts();
        while(products.hasNext()){
            Product product = products.next();
            System.out.println(product.getName() +  " and the id is: " + product.getID());
        }
        System.out.println("Now which product would you like the waitlist for? type their id here: ");
        String input = scanner.nextLine();
        Product chosenProduct = temp.searchProduct(input);
        if(chosenProduct == null){
            System.out.println("could not find that product, returning...");
            return;
        }
        System.out.println("waitlist for this product is: " + chosenProduct.getWaitlist());
    }


}