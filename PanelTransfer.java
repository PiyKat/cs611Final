import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class PanelTransfer {
    private String userId;
    private JFrame frame = new JFrame("Transaction");
    private JPanel centerPane = new JPanel();
    private BackEndController bec = new BackEndController();
    private JFrame fail = new JFrame("Transfer Request Unsuccessful");
    private JFrame succ = new JFrame("Transfer Request Successful");

    public PanelTransfer(String userId) {
        this.userId = userId;

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PanelCustomer panelCustomer = new PanelCustomer(userId);
            }
        });

        // Setting up the fail
        JLabel fLabel = new JLabel("Transfer Request Unsuccessful");
        JPanel fPanel = new JPanel();
        fPanel.add(fLabel);
        fail.add(fPanel);
        fail.setSize(200, 100);

        // Setting up the succ
        JLabel sLabel = new JLabel("Transfer Request Successful");
        JPanel sPanel = new JPanel();
        sPanel.add(sLabel);
        succ.add(sPanel);
        succ.setSize(200, 100);

        frame.setSize(800, 800);

        BoxLayout boxLayout1 = new BoxLayout(centerPane, BoxLayout.Y_AXIS);
        centerPane.setLayout(boxLayout1);

        Box verticalBox = Box.createVerticalBox();
        Box rowBox1 = Box.createHorizontalBox();
        JLabel label1 = new JLabel("Select the sending account: ");
        ArrayList<String> accountsList = bec.getAllAccounts(userId);
        String[] accounts = new String[accountsList.size()];
        accounts = accountsList.toArray(accounts);
        JList accountList = new JList(accounts);
        accountList.setSelectedIndex(0);
        rowBox1.add(label1);
        rowBox1.add(accountList);

        Box rowBox2 = Box.createHorizontalBox();
        JLabel label2 = new JLabel("Enter the receiving account ID:");
        JTextField text2 = new JTextField(20);
        rowBox2.add(label2);
        rowBox2.add(text2);

        Box rowBox3 = Box.createHorizontalBox();
        JLabel label3 = new JLabel("Enter the amount:");
        JTextField text3 = new JTextField(20);
        rowBox3.add(label3);
        rowBox3.add(text3);

        Box rowBox4 = Box.createHorizontalBox();
        JButton confrim4 = new JButton("Confirm");
        rowBox4.add(confrim4);

        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox1);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox2);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox3);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rowBox4);

        centerPane.add(verticalBox);
        frame.add(centerPane);
        frame.setVisible(true);

        confrim4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double amount = Double.parseDouble(text3.getText());
                    if (bec.sendMoney(userId, String.valueOf(accountList.getSelectedValue()), text2.getText(),
                            amount)) {
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

    }

}
