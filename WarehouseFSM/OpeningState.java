import java.util.Scanner;

public class OpeningState implements SuperState{
    public static ClientMenuState client;
    public static ClerkMenuState clerk;
    public static ManagerMenuState manager;
    public OpeningState(){
        if(client == null || clerk == null || manager == null){
            client = new ClientMenuState();
            clerk = new ClerkMenuState();
            manager = new ManagerMenuState();
        }
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        boolean findingNextState = true;
        while(findingNextState){
            System.out.println("Where do you want to go?");
            System.out.println("1: client menu state");
            System.out.println("2: clerk menu state");
            System.out.println("3: manager menu state");
            System.out.println("11: to exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1: 
                    runner(client);
                    break;
                case 2:
                    runner(clerk);
                    break;
                case 3:
                    runner(manager);
                    break;
                case 11:
                    System.out.println("exiting...");
                    findingNextState = false;
                    break;
                default:
                    System.out.println("invalid input, try again");
            }
        }
        System.out.println("made it");
        scanner.close();
    }

    private static <T extends SuperState> void runner(T t){
        t.run();
    }
}