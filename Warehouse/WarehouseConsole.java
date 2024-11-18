import javax.swing.*;
import java.util.Iterator;

public class WarehouseConsole {
    private ClientList clientList = new ClientList();
    private Catalog catalog = Catalog.getInstance();

    public void displayProducts(JFrame frame) {
        StringBuilder builder = new StringBuilder("Products:\n");
        Iterator<Product> iterator = catalog.getAllProducts();
        while (iterator.hasNext()) {
            builder.append(iterator.next().getProductInfo()).append("\n");
        }
        JOptionPane.showMessageDialog(frame, builder.toString(), "Product List", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayClientWishlist(JFrame frame, Client client) {
        StringBuilder builder = new StringBuilder(client.getName() + "'s Wishlist:\n");
        client.getWishlistWithQuantities().forEach((product, quantity) -> {
            builder.append(product.getName()).append(" - Quantity: ").append(quantity).append("\n");
        });
        JOptionPane.showMessageDialog(frame, builder.toString(), "Wishlist", JOptionPane.INFORMATION_MESSAGE);
    }

    public void processWishlistPurchase(JFrame frame, Client client) {
        StringBuilder builder = new StringBuilder("Processing Purchase for ").append(client.getName()).append(":\n");
        client.getWishlistWithQuantities().forEach((product, quantity) -> {
            if (product.getQuantity() >= quantity) {
                product.addToQuantity(-quantity);
                client.removeFromWishlist(product);
                builder.append("Purchased ").append(quantity).append(" of ").append(product.getName()).append("\n");
            } else {
                builder.append("Insufficient stock for ").append(product.getName()).append("\n");
            }
        });
        JOptionPane.showMessageDialog(frame, builder.toString(), "Purchase Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addProductToWishlist(JFrame frame, Client client) {
        String productId = JOptionPane.showInputDialog(frame, "Enter product ID to add to wishlist:");
        Product product = catalog.searchProduct(productId);
        if (product == null) {
            JOptionPane.showMessageDialog(frame, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity for product " + product.getName() + ":");
        int quantity = Integer.parseInt(quantityStr);

        client.addToWishlist(product, quantity);
        JOptionPane.showMessageDialog(frame, "Added " + quantity + " of " + product.getName() + " to wishlist!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
