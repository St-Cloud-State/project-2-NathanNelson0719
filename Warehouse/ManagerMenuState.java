import javax.swing.*;

public class ManagerMenuState implements SuperState {
    private JFrame frame;

    public ManagerMenuState(JFrame frame) {
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

        JLabel label = new JLabel("Manager Menu");
        label.setBounds(10, 10, 200, 25);
        panel.add(label);

        JButton generateReportsButton = new JButton("Generate Reports");
        generateReportsButton.setBounds(10, 50, 200, 25);
        generateReportsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Generate Reports functionality coming soon!");
        });
        panel.add(generateReportsButton);

        JButton manageInventoryButton = new JButton("Manage Inventory");
        manageInventoryButton.setBounds(10, 90, 200, 25);
        manageInventoryButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Manage Inventory functionality coming soon!");
        });
        panel.add(manageInventoryButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBounds(10, 130, 200, 25);
        backButton.addActionListener(e -> new OpeningState(frame).run());
        panel.add(backButton);

        frame.setContentPane(panel);
        frame.revalidate();
    }
}
