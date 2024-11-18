import javax.swing.*;
import java.awt.*;

public class ClientMenuState implements SuperState {
    private JFrame frame;
    private WarehouseConsole functionMule;
    private Client loggedInClient;

    public ClientMenuState(JFrame frame, WarehouseConsole functionMule, Client loggedInClient) {
        this.frame = frame;
        this.functionMule = functionMule;
        this.loggedInClient = loggedInClient;
    }

    @Override
    public void run() {
        frame.getContentPane().removeAll();
        frame.setTitle("Client Menu State");

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton viewProductsButton = new JButton("View Products");
        JButton viewWishlistButton = new JButton("View Wishlist");
        JButton addWishlistButton = new JButton("Add to Wishlist");
        JButton checkoutButton = new JButton("Checkout");
        JButton backButton = new JButton("Back");

        panel.add(viewProductsButton);
        panel.add(viewWishlistButton);
        panel.add(addWishlistButton);
        panel.add(checkoutButton);
        panel.add(backButton);

        viewProductsButton.addActionListener(e -> functionMule.displayProducts(frame));
        viewWishlistButton.addActionListener(e -> functionMule.displayClientWishlist(frame, loggedInClient));
        addWishlistButton.addActionListener(e -> functionMule.addProductToWishlist(frame, loggedInClient));
        checkoutButton.addActionListener(e -> functionMule.processWishlistPurchase(frame, loggedInClient));
        backButton.addActionListener(e -> new OpeningState(frame).run());

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
}
