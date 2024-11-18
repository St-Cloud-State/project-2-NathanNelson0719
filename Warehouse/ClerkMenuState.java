import javax.swing.*;

public class ClerkMenuState implements SuperState {
    private JFrame frame;

    public ClerkMenuState(JFrame frame) {
        this.frame = frame;
        setupUI();
    }

    @Override
    public void run() {
        setupUI();
    }

    private void setupUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label = new JLabel("Clerk Menu");
        label.setBounds(10, 10, 200, 25);
        panel.add(label);

        JButton manageProductsButton = new JButton("Manage Products");
        manageProductsButton.setBounds(10, 50, 200, 25);
        manageProductsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Manage Products functionality coming soon!");
        });
        panel.add(manageProductsButton);

        JButton viewOrdersButton = new JButton("View Pending Orders");
        viewOrdersButton.setBounds(10, 90, 200, 25);
        viewOrdersButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "View Orders functionality coming soon!");
        });
        panel.add(viewOrdersButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBounds(10, 130, 200, 25);
        backButton.addActionListener(e -> new OpeningState(frame).run());
        panel.add(backButton);

        frame.setContentPane(panel);
        frame.revalidate();
    }
}
