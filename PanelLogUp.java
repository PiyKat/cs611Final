import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelLogUp {
    private BackEndController bec = new BackEndController();
    private JFrame loginFailed = new JFrame("Error");
    private JFrame succ = new JFrame("Signup Successfull");

    public PanelLogUp(){
        init();
    }
    private void init(){
        //  create basic frame
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Log up for new customer");
        jFrame.setSize(600,800);
        // end frame

        // Setting up the loginFailed
        JLabel loginFaiLabel = new JLabel("Signup Failed");
        JPanel loginFailPanel = new JPanel();
        loginFailPanel.add(loginFaiLabel);
        loginFailed.add(loginFailPanel);
        loginFailed.setSize(200, 100);

        // Setting up the loginFailed
        JLabel sLabel = new JLabel("Login Successfull");
        JPanel sPanel = new JPanel();
        sPanel.add(sLabel);
        succ.add(sPanel);
        succ.setSize(200, 100);

        // create Jpanel and set layout
        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel,BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);
        jFrame.add(jPanel);
        // end

        //  create component
        JLabel labelAccount = new JLabel("User ID:");
        JTextField accountInput = new JTextField("Enter UserID");

        JLabel labelPassword = new JLabel("Password:");
        JTextField passwordInput = new JTextField("Enter Password");

        JLabel labelName = new JLabel("Name");
        JTextField nameInput = new JTextField("Enter Name");

        // JLabel labelAddress = new JLabel("your current address :");
        // JTextField addressInput = new JTextField();

        JButton buttonCreate = new JButton("Create");
        // end component

        // create selecting saving and checking box

        JPanel boxPanel = new JPanel();
        boxPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        BoxLayout horizontalLayout = new BoxLayout(boxPanel,BoxLayout.X_AXIS);
        boxPanel.setLayout(horizontalLayout);

        JLabel boxname = new JLabel("Choose the type of account you want to create");
        String[] accTypes = {"Savings", "Checking"};
        JList typeList = new JList(accTypes);
        typeList.setSelectedIndex(1);
        boxPanel.add(boxname);
        boxPanel.add(typeList);

        JPanel boxPanel1 = new JPanel();
        boxPanel1.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        // BoxLayout horizontalLayout1 = new BoxLayout(boxPanel,BoxLayout.X_AXIS);
        boxPanel.setLayout(horizontalLayout);

        JLabel boxname1 = new JLabel("Choose the currency");
        String[] currTypes = {"USD", "YEN", "INR"};
        JList currList = new JList(currTypes);
        currList.setSelectedIndex(1);
        boxPanel1.add(boxname1);
        boxPanel1.add(currList);

        jPanel.add(labelAccount);
        jPanel.add(accountInput);
        jPanel.add(labelPassword);
        jPanel.add(passwordInput);
        jPanel.add(labelName);
        jPanel.add(nameInput);
        jPanel.add(boxPanel);
        jPanel.add(boxPanel1);
        jPanel.add(buttonCreate);
        jFrame.setVisible(true);

        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bec.checkUserId(accountInput.getText())){
                    loginFailed.setVisible(true);
                }
                else{
                    bec.createNewUser(accountInput.getText(), nameInput.getText(), passwordInput.getText(), "customer");
                    bec.createNewAccount(accountInput.getText(), String.valueOf(typeList.getSelectedValue()), Currency.valueOf(String.valueOf(currList.getSelectedValue())));
                    succ.setVisible(true);
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e1) {}
                    PanelLogIn login = new PanelLogIn();
                }
            }
        });
    }
}
