import javax.swing.*;
import java.awt.*;

public class ClerkMenuGUI extends JFrame {
    private final LoginGUI loginGUI;

    public ClerkMenuGUI(LoginGUI loginGUI, JFrame parentFrame) {
        this.loginGUI = loginGUI;

        setTitle("Clerk Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        JButton addClientButton = new JButton("Add a Client");
        JButton showProductsButton = new JButton("Show Products");
        JButton showClientsButton = new JButton("Show Clients");
        JButton loginAsClientButton = new JButton("Login as Client");
        JButton logoutButton = new JButton("Logout");

        add(addClientButton);
        add(showProductsButton);
        add(showClientsButton);
        add(loginAsClientButton);
        add(logoutButton);

        addClientButton.addActionListener(e -> addClient());
        showProductsButton.addActionListener(e -> displayProducts());
        showClientsButton.addActionListener(e -> displayClients());
        loginAsClientButton.addActionListener(e -> loginAsClient());
        logoutButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                parentFrame.setVisible(true); // Show the parent frame
                dispose();
            });
            //Context.getInstance().setCurrentState(null); // Clear state
            //loginGUI.setVisible(true);
            //dispose();
        });
    }

    private void addClient() {
        String name = JOptionPane.showInputDialog(this, "Enter Client Name:", "Add Client", JOptionPane.PLAIN_MESSAGE);
        String address = JOptionPane.showInputDialog(this, "Enter Client Address:", "Add Client", JOptionPane.PLAIN_MESSAGE);
        String phone = JOptionPane.showInputDialog(this, "Enter Client Phone:", "Add Client", JOptionPane.PLAIN_MESSAGE);
        String balanceInput = JOptionPane.showInputDialog(this, "Enter Starting Balance:", "Add Client", JOptionPane.PLAIN_MESSAGE);

        if (name == null || address == null || phone == null || balanceInput == null) {
            JOptionPane.showMessageDialog(this, "Action canceled or incomplete input provided.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double balance = Double.parseDouble(balanceInput);
            Client newClient = new Client(name, address, phone, balance);
            new ClientList().insertClient(newClient);
            JOptionPane.showMessageDialog(this, "Client added successfully! ID: " + newClient.getID(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid balance entered. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayProducts() {
        Catalog catalog = Catalog.getInstance();
        StringBuilder productsList = new StringBuilder("Products:\n");
        catalog.getAllProducts().forEachRemaining(product -> productsList.append(product.getProductInfo()).append("\n"));
        JOptionPane.showMessageDialog(this, productsList.toString(), "Products", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayClients() {
        ClientList clientList = new ClientList();
        StringBuilder clientsList = new StringBuilder("Clients:\n");
        clientList.getClients().forEachRemaining(client -> clientsList.append(client.toString()).append("\n"));
        JOptionPane.showMessageDialog(this, clientsList.toString(), "Clients", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loginAsClient() {
        String clientID = JOptionPane.showInputDialog(this, "Enter Client ID:", "Login as Client", JOptionPane.PLAIN_MESSAGE);

        if (clientID != null && !clientID.trim().isEmpty()) {
            ClientList clientList = new ClientList();
            Client client = clientList.search(clientID);

            if (client != null) {
                Context.getInstance().setClientID(clientID);
                Context.getInstance().setCurrentState("ClerkMenu");
                new ClientMenuGUI(this, loginGUI).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Client ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
