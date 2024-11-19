import javax.swing.*;

public class LoginGUI extends JFrame {
    public LoginGUI() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton backToMainMenuButton = new JButton("Back to Main Menu");
        backToMainMenuButton.addActionListener(e -> {
            new MainGUI().setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(backToMainMenuButton);

        add(panel);
    }
}
