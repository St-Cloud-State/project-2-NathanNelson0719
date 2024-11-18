import javax.swing.*;
import java.awt.*;

public class OpeningState implements SuperState {
    public static ClientMenuState client;
    public static ClerkMenuState clerk;
    public static ManagerMenuState manager;
    private JFrame frame;

    public OpeningState(JFrame frame) {
        this.frame = frame;

        WarehouseConsole functionMule = new WarehouseConsole();

        // Placeholder client (replace with actual login mechanism)
        Client dummyClient = new Client("Default", "Default Address", "000-000-0000", 0.0);

        if (client == null || clerk == null || manager == null) {
            client = new ClientMenuState(frame, functionMule, dummyClient);
            clerk = new ClerkMenuState(frame);
            manager = new ManagerMenuState(frame);
        }
    }

    @Override
    public void run() {
        frame.getContentPane().removeAll();
        frame.setTitle("Opening State");

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton clientMenuButton = new JButton("Client Menu State");
        JButton clerkMenuButton = new JButton("Clerk Menu State");
        JButton managerMenuButton = new JButton("Manager Menu State");
        JButton exitButton = new JButton("Exit");

        panel.add(clientMenuButton);
        panel.add(clerkMenuButton);
        panel.add(managerMenuButton);
        panel.add(exitButton);

        clientMenuButton.addActionListener(e -> client.run());
        clerkMenuButton.addActionListener(e -> clerk.run());
        managerMenuButton.addActionListener(e -> manager.run());
        exitButton.addActionListener(e -> System.exit(0));

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
}
