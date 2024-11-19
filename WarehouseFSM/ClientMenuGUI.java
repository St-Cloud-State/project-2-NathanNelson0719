import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ClientMenuGUI extends JFrame {
    private final ClerkMenuGUI clerkMenuGUI;
    private final LoginGUI loginGUI;

    public ClientMenuGUI(ClerkMenuGUI clerkMenuGUI, LoginGUI loginGUI) {
        this.clerkMenuGUI = clerkMenuGUI;
        this.loginGUI = loginGUI;

        setTitle("Client Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1));

        JButton clientDetailsButton = new JButton("View Client Details");
        JButton showProductsButton = new JButton("Show Products Listing");
        JButton showTransactionsButton = new JButton("Show Transactions");
        JButton displayWishlistButton = new JButton("Display Wishlist");
        JButton addToWishlistButton = new JButton("Add to Wishlist");
        JButton placeOrderButton = new JButton("Place Order");
        JButton logoutButton = new JButton("Logout");

        add(clientDetailsButton);
        add(showProductsButton);
        add(showTransactionsButton);
        add(displayWishlistButton);
        add(addToWishlistButton);
        add(placeOrderButton);
        add(logoutButton);

        clientDetailsButton.addActionListener(e -> displayClientDetails());
        showProductsButton.addActionListener(e -> displayProducts());
        showTransactionsButton.addActionListener(e -> displayTransactions());
        displayWishlistButton.addActionListener(e -> displayWishlist());
        addToWishlistButton.addActionListener(e -> addToWishlist());
        placeOrderButton.addActionListener(e -> placeOrder());
        logoutButton.addActionListener(e -> {
            clerkMenuGUI.setVisible(true); // Navigate back to Clerk Menu
            dispose();
        });
    }

    private Client getLoggedInClient() {
        String clientID = Context.getInstance().getClientID();
        if (clientID != null && !clientID.isEmpty()) {
            return new ClientList().search(clientID);
        }
        return null; // No logged-in client found
    }

    private void displayClientDetails() {
        Client loggedInClient = getLoggedInClient();
        if (loggedInClient != null) {
            JOptionPane.showMessageDialog(this, loggedInClient.toString(), "Client Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No client is currently logged in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayProducts() {
        Catalog catalog = Catalog.getInstance();
        StringBuilder productsList = new StringBuilder("Products:\n");
        Iterator<Product> iterator = catalog.getAllProducts();
        int index = 1;
        while (iterator.hasNext()) {
            Product product = iterator.next();
            productsList.append(index++).append(". ").append(product.getProductInfo()).append("\n");
        }
        JOptionPane.showMessageDialog(this, productsList.toString(), "Products Listing", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayTransactions() {
        Client loggedInClient = getLoggedInClient();
        if (loggedInClient != null) {
            StringBuilder transactions = new StringBuilder("Transactions:\n");
            loggedInClient.getTransactions().forEachRemaining(transaction -> transactions.append(transaction).append("\n"));
            JOptionPane.showMessageDialog(this, transactions.toString(), "Transactions", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No client is currently logged in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayWishlist() {
        Client loggedInClient = getLoggedInClient();
        if (loggedInClient != null) {
            Wishlist wishlist = loggedInClient.getWishlist();
            StringBuilder wishlistDetails = new StringBuilder("Wishlist:\n");
            for (Map.Entry<Product, Integer> entry : wishlist.getProductsWithQuantities().entrySet()) {
                wishlistDetails.append(entry.getKey().getProductInfo())
                        .append(" - Quantity: ")
                        .append(entry.getValue())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, wishlistDetails.toString(), "Wishlist", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No client is currently logged in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addToWishlist() {
        Client loggedInClient = getLoggedInClient();
        if (loggedInClient != null) {
            Catalog catalog = Catalog.getInstance();
            List<Product> productList = new ArrayList<>();
            StringBuilder productsList = new StringBuilder("Products:\n");
            Iterator<Product> iterator = catalog.getAllProducts();
            int index = 1;

            // Populate the product list and display
            while (iterator.hasNext()) {
                Product product = iterator.next();
                productList.add(product);
                productsList.append(index++).append(". ").append(product.getName()).append(" (ID: ").append(product.getID()).append(")\n");
            }

            String selectedProductIndex = JOptionPane.showInputDialog(this, "Available Products:\n" + productsList + "\nEnter the number of the product to add to your wishlist:");
            if (selectedProductIndex != null) {
                try {
                    int productIndex = Integer.parseInt(selectedProductIndex);
                    if (productIndex > 0 && productIndex <= productList.size()) {
                        Product selectedProduct = productList.get(productIndex - 1); // Get product by index
                        String quantityInput = JOptionPane.showInputDialog(this, "Enter quantity:");
                        int quantity = Integer.parseInt(quantityInput);

                        loggedInClient.addToWishlist(selectedProduct, quantity);
                        JOptionPane.showMessageDialog(this, "Added " + quantity + " of " + selectedProduct.getName() + " to wishlist.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid product number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No client is currently logged in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void placeOrder() {
        Client loggedInClient = getLoggedInClient();
        if (loggedInClient != null) {
            Wishlist wishlist = loggedInClient.getWishlist();
            if (wishlist.getProductsWithQuantities().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Wishlist is empty. Add products before placing an order.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double totalCost = 0.0;
            for (Map.Entry<Product, Integer> entry : wishlist.getProductsWithQuantities().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                totalCost += product.getPrice() * quantity;
            }

            if (loggedInClient.getBalance() < totalCost) {
                JOptionPane.showMessageDialog(this, "Insufficient balance to place the order.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            wishlist.getProductsWithQuantities().forEach((product, quantity) -> {
                product.addToQuantity(-quantity);
                loggedInClient.getWishlist().removeProduct(product);
            });

            loggedInClient.addToBalance(-totalCost);
            JOptionPane.showMessageDialog(this, "Order placed successfully! Total cost: $" + totalCost, "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No client is currently logged in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
