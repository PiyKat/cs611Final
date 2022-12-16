import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ManagerAccountsWindow extends JFrame {

  private String userId;
  private BackEndController bec;
  private ArrayList<String> accIds;
  private static Font titleFont = new Font("Calibri", Font.BOLD, 32);
  private static Font textFont = new Font("Calibri", Font.PLAIN, 20);
  private static Font boldTextFont = new Font("Calibri", Font.BOLD, 20);

  private String getAccountTypes(String accId) {
    return bec.getAccountType(accId);
  }

  private double getAccountBalance(String accId) {
    return bec.getAccBal(accId);
  }

  private JPanel initTopPanel() {
    JPanel panel = new JPanel(new GridLayout(1, 3));
    JLabel label1, label2, label3;

    label1 = new JLabel("Account Type", JLabel.CENTER);
    label1.setFont(titleFont);

    label2 = new JLabel("Account Id", JLabel.CENTER);
    label2.setFont(titleFont);

    label3 = new JLabel("Balance", JLabel.CENTER);
    label3.setFont(titleFont);

    panel.add(label1);
    panel.add(label2);
    panel.add(label3);

    return panel;
  }

  private JPanel initAccountJPanel(String accId) {
    JPanel panel = new JPanel(new GridLayout(1, 3));
    JLabel typeLabel = new JLabel(this.getAccountTypes(accId), JLabel.CENTER);
    JLabel idLabel = new JLabel(accId, JLabel.CENTER);

    idLabel.setFont(textFont);
    typeLabel.setFont(textFont);

    JTextField balanceField = new JTextField("$ " + Double.toString(this.getAccountBalance(accId)));
    balanceField.setHorizontalAlignment(JTextField.CENTER);
    balanceField.setFont(textFont);
    balanceField.setEditable(false);

    panel.add(typeLabel);
    panel.add(idLabel);
    panel.add(balanceField);

    return panel;
  }

  public ManagerAccountsWindow(String _userId) {
    this.userId = _userId;
    this.bec = new BackEndController();
    this.accIds = bec.getAllAccounts(userId);
    this.setTitle("Accounts - " + this.userId);
    this.setSize(800, 400);

    this.setLayout(new GridLayout(accIds.size() + 1, 1));

    this.add(this.initTopPanel());
    for (String accId : accIds) {
      this.add(initAccountJPanel(accId));
    }
  }
}
