import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

public class PanelCreateAccount {
    private BackEndController bec = new BackEndController();
    private String userId;
    private JFrame loginFailed = new JFrame("Error");
    private JFrame succ = new JFrame("Account creation Successfull");
    private JFrame jFrame = new JFrame("Create Account");

    public PanelCreateAccount(String userId) {
        this.userId = userId;
        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PanelCustomer panelCustomer = new PanelCustomer(userId);
            }
        });
        jFrame.setSize(800, 400);

        // Setting up the loginFailed
        JLabel loginFaiLabel = new JLabel("Account Creation Failed");
        JPanel loginFailPanel = new JPanel();
        loginFailPanel.add(loginFaiLabel);
        loginFailed.add(loginFailPanel);
        loginFailed.setSize(200, 100);

        // Setting up the Success
        JLabel sLabel = new JLabel("Account creation Successfull");
        JPanel sPanel = new JPanel();
        sPanel.add(sLabel);
        succ.add(sPanel);
        succ.setSize(200, 100);

        JPanel secondPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(secondPanel, BoxLayout.Y_AXIS);
        EmptyBorder emptyBorder = new EmptyBorder(20, 20, 20, 20);
        secondPanel.setBorder(emptyBorder);
        secondPanel.setLayout(boxLayout);
        jFrame.add(secondPanel);

        Box verticalBox = Box.createVerticalBox();

        Box rowBox1 = Box.createHorizontalBox();
        JLabel labelType = new JLabel("Account Type : ");
        JRadioButton jRadioSaving = new JRadioButton("Saving");
        JRadioButton jRadioChecking = new JRadioButton("Checking");
        JRadioButton jRadioSecurity = new JRadioButton("Security");

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(jRadioChecking);
        btnGroup.add(jRadioSaving);
        btnGroup.add(jRadioSecurity);

        jRadioSaving.setSelected(true);

        rowBox1.add(labelType);
        rowBox1.add(jRadioChecking);
        rowBox1.add(jRadioSaving);
        rowBox1.add(jRadioSecurity);

        Box rowBox2 = Box.createHorizontalBox();

        JLabel labelCurreny = new JLabel("Currency : ");
        JRadioButton jRadioYen = new JRadioButton("YEN");
        JRadioButton jRadioUSD = new JRadioButton("USD");
        JRadioButton jRadioINR = new JRadioButton("INR");
        ButtonGroup btnGroup1 = new ButtonGroup();
        btnGroup1.add(jRadioYen);
        btnGroup1.add(jRadioUSD);
        btnGroup1.add(jRadioINR);

        jRadioYen.setSelected(true);

        rowBox2.add(labelCurreny);
        rowBox2.add(jRadioYen);
        rowBox2.add(jRadioUSD);
        rowBox2.add(jRadioINR);

        // 3 row
        Box rowBox3 = Box.createHorizontalBox();
        JButton confirmAccount = new JButton("Confirm");
        rowBox3.add(confirmAccount);

        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox1);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox2);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox3);
        verticalBox.add(Box.createVerticalStrut(10));

        secondPanel.add(verticalBox);

        confirmAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accType = (jRadioSaving.isSelected()) ? "saving"
                        : jRadioChecking.isSelected() ? "checking" : "securities";
                String curr1 = (jRadioYen.isSelected()) ? "YEN" : (jRadioUSD.isSelected()) ? "USD" : "INR";
                Currency curr = Currency.valueOf(curr1);
                if(accType.equalsIgnoreCase("securities")){
                    if(!(bec.checkSecurityEligibility(userId))){
                        loginFailed.setVisible(true);
                        return;
                    }
                }
                bec.createNewAccount(userId, accType, curr);
                succ.setVisible(true);
                try {
                    Thread.sleep(1000);
                } catch (Exception e1) {
                }
                succ.setVisible(false);
                jFrame.setVisible(false);
                PanelCustomer panelCustomer = new PanelCustomer(userId);
            }
        });

        jFrame.setVisible(true);
    }

}
