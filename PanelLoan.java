import java.util.ArrayList;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelLoan {
    private String userId;
    private JFrame frame = new JFrame("Loan Application");
    private JPanel panel = new JPanel();
    private BackEndController bec = new BackEndController();
    private JFrame fail = new JFrame("Loan Request Unsuccessful");
    private JFrame succ = new JFrame("Loan Request Successful");

    public PanelLoan(String userId) {
        this.userId = userId;
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PanelCustomer panelCustomer = new PanelCustomer(userId);
            }
        });

        // Setting up the fail
        JLabel fLabel = new JLabel("Loan Request Unsuccessful");
        JPanel fPanel = new JPanel();
        fPanel.add(fLabel);
        fail.add(fPanel);
        fail.setSize(200, 100);

        // Setting up the succ
        JLabel sLabel = new JLabel("Loan Request Successful");
        JPanel sPanel = new JPanel();
        sPanel.add(sLabel);
        succ.add(sPanel);
        succ.setSize(200, 100);

        frame.setSize(800, 800);
        Box verticalBox = Box.createVerticalBox();

        // 1 row
        Box rowBox1 = Box.createHorizontalBox();
        JLabel label = new JLabel("Apply for a loan", JLabel.CENTER);
        rowBox1.add(label);

        // 2 row
        Box rowBox2 = Box.createHorizontalBox();
        JLabel labelCollateral = new JLabel("Collateral Value :  ");
        JTextField textCollateral = new JTextField("Fill in the total value of collateral", 20);

        rowBox2.add(labelCollateral);
        rowBox2.add(textCollateral);

        // 3 row
        Box rowBox3 = Box.createHorizontalBox();
        JLabel labelLoanType = new JLabel("Loan Type : ");
        JRadioButton jRadioEducation = new JRadioButton("Education loan");
        JRadioButton jRadioHouse = new JRadioButton("House loan");
        JRadioButton jRadioCar = new JRadioButton("Car loan");
        ButtonGroup btnGrouploan = new ButtonGroup();
        btnGrouploan.add(jRadioEducation);
        btnGrouploan.add(jRadioHouse);
        btnGrouploan.add(jRadioCar);

        jRadioEducation.setSelected(true);

        rowBox3.add(labelLoanType);
        rowBox3.add(jRadioEducation);
        rowBox3.add(jRadioHouse);
        rowBox3.add(jRadioCar);

        // 4 row
        Box rowBox4 = Box.createHorizontalBox();
        JLabel labelLoanAmount = new JLabel("Amount : ");
        JTextField textLoanAmount = new JTextField("The amount you want to borrow", 20);
        rowBox4.add(labelLoanAmount);
        rowBox4.add(textLoanAmount);

        // 5 row
        Box rowBox6 = Box.createHorizontalBox();
        ArrayList<String> accountsList = bec.getAllAccounts(userId);
        String[] accounts = new String[accountsList.size()];
        accounts = accountsList.toArray(accounts);
        JLabel boxname1 = new JLabel("Select the account");
        JList accountList = new JList(accounts);
        accountList.setSelectedIndex(0);
        rowBox6.add(boxname1);
        rowBox6.add(accountList);

        // 6 row
        Box rowBox5 = Box.createHorizontalBox();
        JButton confirmLoan = new JButton("Apply");
        rowBox5.add(confirmLoan);

        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox1);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox2);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox3);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox4);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox6);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox5);
        verticalBox.add(Box.createVerticalStrut(10));
        panel.add(verticalBox);
        frame.add(panel);

        confirmLoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double coll = Double.parseDouble(textCollateral.getText());
                    Double loanAmt = Double.parseDouble(textLoanAmount.getText());
                    String loanType = (jRadioEducation.isSelected()) ? "education"
                            : (jRadioHouse.isSelected()) ? "house" : "car";
                    if (bec.createNewLoan(userId, String.valueOf(accountList.getSelectedValue()), coll, loanType,
                            loanAmt)) {
                        succ.setVisible(true);
                        try {
                            Thread.sleep(5000);
                        } catch (Exception e1) {
                        }
                        PanelCustomer panelCustomer = new PanelCustomer(userId);
                    } else {
                        fail.setVisible(true);
                    }
                } catch (Exception x) {
                    fail.setVisible(true);
                }
            }
        });

        frame.setVisible(true);
    }
}
