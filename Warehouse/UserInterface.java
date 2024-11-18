import javax.swing.*;

public class UserInterface {
    private JFrame frame;

    public UserInterface() {
        // Create the main JFrame
        frame = new JFrame("Warehouse Management System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Start with the OpeningState
        new OpeningState(frame).run();

        // Make the frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserInterface::new);
    }
}