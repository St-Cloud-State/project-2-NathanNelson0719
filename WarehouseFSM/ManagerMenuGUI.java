import javax.swing.*;
import java.util.Iterator;

public class ManagerMenuGUI extends JFrame {
    private final LoginGUI loginGUI;

    public ManagerMenuGUI(LoginGUI loginGUI) {
        this.loginGUI = loginGUI;

        setTitle("Manager Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(e -> addProduct());

        JButton receiveShipmentButton = new JButton("Receive Shipment");
        receiveShipmentButton.addActionListener(e -> receiveShipment());

        JButton displayWaitlistButton = new JButton("Display Product Waitlist");
        displayWaitlistButton.addActionListener(e -> displayProductWaitlist());

        JButton loginAsClerkButton = new JButton("Login as Clerk");
        loginAsClerkButton.addActionListener(e -> new ClerkMenuGUI(loginGUI, this).setVisible(true));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            loginGUI.setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(addProductButton);
        panel.add(receiveShipmentButton);
        panel.add(displayWaitlistButton);
        panel.add(loginAsClerkButton);
        panel.add(logoutButton);

        add(panel);
    }

    private void addProduct() {
        String name = JOptionPane.showInputDialog(this, "Enter Product Name:", "Add Product", JOptionPane.PLAIN_MESSAGE);
        String priceInput = JOptionPane.showInputDialog(this, "Enter Product Price:", "Add Product", JOptionPane.PLAIN_MESSAGE);
        String description = JOptionPane.showInputDialog(this, "Enter Product Description:", "Add Product", JOptionPane.PLAIN_MESSAGE);
        String category = JOptionPane.showInputDialog(this, "Enter Product Category:", "Add Product", JOptionPane.PLAIN_MESSAGE);
        String quantityInput = JOptionPane.showInputDialog(this, "Enter Product Quantity:", "Add Product", JOptionPane.PLAIN_MESSAGE);

        if (name == null || priceInput == null || description == null || category == null || quantityInput == null) {
            JOptionPane.showMessageDialog(this, "Action canceled or incomplete input provided.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceInput);
            int quantity = Integer.parseInt(quantityInput);

            Product newProduct = new Product(name, price, description, category, quantity);
            Catalog.getInstance().addProduct(newProduct);

            JOptionPane.showMessageDialog(this, "Product added successfully! ID: " + newProduct.getID(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input for price or quantity. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void receiveShipment() {
        Catalog catalog = Catalog.getInstance();
        StringBuilder productList = new StringBuilder("Products:\n");
        int productIndex = 1;

        for (Iterator<Product> iterator = catalog.getAllProducts(); iterator.hasNext(); productIndex++) {
            Product product = iterator.next();
            productList.append(productIndex).append(": ").append(product.getName()).append(" (ID: ").append(product.getID()).append(")\n");
        }

        String productInput = JOptionPane.showInputDialog(this, productList + "Enter the product number to receive shipment for:", "Receive Shipment", JOptionPane.PLAIN_MESSAGE);

        if (productInput == null || productInput.trim().isEmpty()) {
            return;
        }

        try {
            int selectedProductIndex = Integer.parseInt(productInput);
            Product selectedProduct = getProductByIndex(selectedProductIndex);

            if (selectedProduct == null) {
                JOptionPane.showMessageDialog(this, "Invalid product selection.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String quantityInput = JOptionPane.showInputDialog(this, "Enter the quantity to add to " + selectedProduct.getName() + ":", "Receive Shipment", JOptionPane.PLAIN_MESSAGE);

            if (quantityInput == null || quantityInput.trim().isEmpty()) {
                return;
            }

            int quantity = Integer.parseInt(quantityInput);
            selectedProduct.addToQuantity(quantity);

            JOptionPane.showMessageDialog(this, "Received shipment of " + quantity + " for " + selectedProduct.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayProductWaitlist() {
        Catalog catalog = Catalog.getInstance();
        StringBuilder productList = new StringBuilder("Products:\n");
        int productIndex = 1;

        for (Iterator<Product> iterator = catalog.getAllProducts(); iterator.hasNext(); productIndex++) {
            Product product = iterator.next();
            productList.append(productIndex).append(": ").append(product.getName()).append(" (ID: ").append(product.getID()).append(")\n");
        }

        String productInput = JOptionPane.showInputDialog(this, productList + "Enter the product number to view waitlist:", "Product Waitlist", JOptionPane.PLAIN_MESSAGE);

        if (productInput == null || productInput.trim().isEmpty()) {
            return;
        }

        try {
            int selectedProductIndex = Integer.parseInt(productInput);
            Product selectedProduct = getProductByIndex(selectedProductIndex);

            if (selectedProduct == null) {
                JOptionPane.showMessageDialog(this, "Invalid product selection.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String waitlist = selectedProduct.getWaitlist();
            JOptionPane.showMessageDialog(this, "Waitlist for " + selectedProduct.getName() + ":\n" + waitlist, "Product Waitlist", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Product getProductByIndex(int index) {
        Catalog catalog = Catalog.getInstance();
        Iterator<Product> iterator = catalog.getAllProducts();
        int currentIndex = 1;

        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (currentIndex == index) {
                return product;
            }
            currentIndex++;
        }

        return null; // Product not found
    }
}
