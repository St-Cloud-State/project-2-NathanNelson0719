import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Warehouse Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton clientButton = new JButton("Client Menu");
        JButton clerkButton = new JButton("Clerk Menu");
        JButton managerButton = new JButton("Manager Menu");

        add(clientButton);
        add(clerkButton);
        add(managerButton);

        // Login GUI instance to pass to other constructors
        LoginGUI loginGUI = new LoginGUI();

        clientButton.addActionListener(e -> {
            String clientID = JOptionPane.showInputDialog(this, "Enter Client ID:", "Login as Client", JOptionPane.PLAIN_MESSAGE);

            if (clientID != null && !clientID.trim().isEmpty()) {
                ClientList clientList = new ClientList();
                Client client = clientList.search(clientID);

                if (client != null) {
                    Context.getInstance().setClientID(clientID);
                    Context.getInstance().setCurrentState("LoginMenu");
                    ClerkMenuGUI clerkMenuGUI = new ClerkMenuGUI(loginGUI, this); // Passing ClerkMenuGUI for transitions
                    ClientMenuGUI clientMenuGUI = new ClientMenuGUI(clerkMenuGUI, loginGUI);
                    clientMenuGUI.setVisible(true);
                    dispose(); // Close the main menu
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Client ID. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (clientID != null) {
                JOptionPane.showMessageDialog(this, "Client ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clerkButton.addActionListener(e -> {
            ClerkMenuGUI clerkMenuGUI = new ClerkMenuGUI(loginGUI, this);
            clerkMenuGUI.setVisible(true);
            dispose(); // Close the main menu
        });

        managerButton.addActionListener(e -> {
            ManagerMenuGUI managerMenuGUI = new ManagerMenuGUI(loginGUI);
            managerMenuGUI.setVisible(true);
            dispose(); // Close the main menu
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);
        });
    }
}
