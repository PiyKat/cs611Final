
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PanelLogIn extends JPanel {
    private BackEndController bec = new BackEndController();
    private JFrame frame = new JFrame("Login");
    private JFrame loginFailed = new JFrame("Error");
    private JDialog sucJDialog;

    public PanelLogIn() {
        // Setting up the frame
        frame.setLocation(10, 10);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));

        // Setting up the loginFailed
        JLabel loginFaiLabel = new JLabel("Login Failed");
        JPanel loginFailPanel = new JPanel();
        loginFailPanel.add(loginFaiLabel);
        loginFailed.add(loginFailPanel);
        loginFailed.setSize(200, 100);

        // Setting up the succ
        this.sucJDialog = new JDialog();
        sucJDialog.setTitle("Login Successful");
        this.sucJDialog.setSize(200, 100);
        this.sucJDialog.setLayout(new GridLayout(1, 1));
        JLabel sLabel = new JLabel("Login Successful", JLabel.CENTER);
        sucJDialog.add(sLabel);
        // JPanel sPanel = new JPanel();
        // sPanel.add(sLabel);
        // succ.add(sPanel);
        // succ.setSize(200, 100);

        // setting up the panel and adding it to the frame
        // JPanel centerPane = new JPanel();
        // frame.add(centerPane, BorderLayout.CENTER);
        // centerPane.setLayout(new GridBagLayout());
        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.fill = GridBagConstraints.BOTH;
        Font font = new Font("serif", Font.BOLD, 20);
        Font font1 = new Font("serif", Font.BOLD + Font.ITALIC, 20);

        // Topic label
        JLabel labelTopic = new JLabel("------------Welcome to our Fancy Bank----------");
        LineBorder lineBorder = new LineBorder(Color.RED);
        EmptyBorder emptyBorder = new EmptyBorder(10, 200, 10, 10);
        labelTopic.setBorder(new CompoundBorder(lineBorder, emptyBorder));
        labelTopic.setForeground(Color.BLUE);
        labelTopic.setFont(font);

        frame.add(labelTopic);

        // gbc.gridx = 1;
        // gbc.gridy = 1;
        // gbc.gridwidth = 800;
        // gbc.gridheight = 1;
        // gbc.weightx = 1;
        // gbc.weighty = 0.125;
        // centerPane.add(labelTopic, gbc);

        // Adding the user Id label
        JPanel userJPanel = new JPanel(new GridLayout(1, 2));
        JLabel userJLabel = new JLabel("User ID:", JLabel.CENTER);
        userJLabel.setFont(font1);

        userJPanel.add(userJLabel);

        JTextField userJTextField = new JTextField("Enter the User Id", 20);
        userJPanel.add(userJTextField);

        // gbc.gridx = 1;
        // gbc.gridy = 2;
        // gbc.gridwidth = 3;
        // gbc.gridheight = 1;
        // gbc.weightx = 0.25;
        // gbc.weighty = 0.125;
        frame.add(userJPanel);
        // centerPane.add(userJPanel, gbc);

        // Adding the password panel
        JPanel passwordJPanel = new JPanel(new GridLayout(1, 2));

        JLabel passwordJLabel = new JLabel("Password:", JLabel.CENTER);
        passwordJLabel.setFont(font1);
        passwordJPanel.add(passwordJLabel);

        JPasswordField passwordField = new JPasswordField(20);
        passwordJPanel.add(passwordField);

        frame.add(passwordJPanel);

        // gbc.gridx = 1;
        // gbc.gridy = 3;
        // gbc.gridwidth = 3;
        // gbc.gridheight = 1;
        // gbc.weightx = 0.25;
        // gbc.weighty = 0.125;

        // centerPane.add(passwordJPanel, gbc);

        // JButton buttonManger = new JButton("Log In for Manger");
        // buttonManger.setFont(font);
        // buttonManger.setForeground(Color.darkGray);
        // gbc.gridx = 2;
        // gbc.gridy = 6;
        // gbc.gridwidth = 2;
        // gbc.gridheight = 1;
        // gbc.weightx = 0.25;
        // gbc.weighty = 0.125;
        // centerPane.add(buttonManger, gbc);

        JPanel signlogPanel = new JPanel(new GridLayout(1, 2));

        JButton buttonLogUp = new JButton("Sign Up");
        buttonLogUp.setFont(font);
        buttonLogUp.setForeground(Color.red);
        signlogPanel.add(buttonLogUp);
        // gbc.gridx = 1;
        // gbc.gridy = 6;
        // gbc.gridwidth = 2;
        // gbc.gridheight = 1;
        // gbc.weightx = 0.25;
        // gbc.weighty = 0.125;
        // centerPane.add(buttonLogUp, gbc);

        JButton buttonCustomer = new JButton("Log In");
        buttonCustomer.setFont(font);
        buttonCustomer.setForeground(Color.darkGray);
        signlogPanel.add(buttonCustomer);

        frame.add(signlogPanel);

        // gbc.gridx = 3;
        // gbc.gridy = 6;
        // gbc.gridwidth = 2;
        // gbc.gridheight = 1;
        // gbc.weightx = 0.25;
        // gbc.weighty = 0.125;
        // centerPane.add(buttonCustomer, gbc);

        buttonLogUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelLogUp panelLogUp = new PanelLogUp();
            }
        });

        buttonCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bec.validateUser(userJTextField.getText(), String.valueOf(passwordField.getPassword()))) {
                    sucJDialog.setVisible(true);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e1) {
                    }
                    sucJDialog.setVisible(false);
                    // frame.setVisible(false);
                    if (bec.isManager(userJTextField.getText())) {
                        ManagerWindow window = new ManagerWindow();
                        window.setVisible(true);
                    } else {
                        PanelCustomer panelCustomer = new PanelCustomer(userJTextField.getText());
                    }
                } else {
                    loginFailed.setVisible(true);
                    // JOptionPane.showMessageDialog(null, "loging fail","Error",
                    // JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // buttonManger.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {

        // }
        // });

        frame.setVisible(true);

    }

}