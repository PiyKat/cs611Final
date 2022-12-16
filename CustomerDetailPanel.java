import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomerDetailPanel extends JPanel {
  private TwoStringPanel namePanel, idPanel, accNumPanel;
  private JButton accButton;
  private String userId;
  private static Font titleFont = new Font("Calibri", Font.BOLD, 32);
  private static Font textFont = new Font("Calibri", Font.PLAIN, 20);
  private static Font boldTextFont = new Font("Calibri", Font.BOLD, 20);

  private BackEndController beCtr;

  class TwoStringPanel extends JPanel {
    private JLabel label1, label2;

    public TwoStringPanel(String type, String text) {
      this.setLayout(new GridLayout(1, 2));
      label1 = new JLabel(type, JLabel.CENTER);
      label1.setFont(boldTextFont);

      label2 = new JLabel(text, JLabel.CENTER);
      label2.setFont(boldTextFont);

      this.add(label1);
      this.add(label2);
    }

    public void setText(String text) {
      label2.setText(text);
    }

  };

  private String getUserName() {
    return beCtr.getUserName(userId);
  }

  class accButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      JFrame window = new ManagerAccountsWindow(userId);
      window.setVisible(true);
    }
  };

  public CustomerDetailPanel(String _userId) {
    this.beCtr = new BackEndController();
    this.userId = _userId;

    ArrayList<String> accIds = beCtr.getAllAccounts(userId);

    this.setLayout(new GridLayout(4, 1));

    this.namePanel = new TwoStringPanel("User name", this.getUserName());
    this.idPanel = new TwoStringPanel("User id", this.userId);
    this.accNumPanel = new TwoStringPanel("Accounts count", Integer.toString(accIds.size()));

    this.add(namePanel);
    this.add(idPanel);
    this.add(accNumPanel);

    accButton = new JButton("View Accounts");
    accButton.setFont(boldTextFont);

    this.add(accButton);
    accButton.addActionListener(new accButtonListener());

  }

  public void setUserId(String _userId) {
    this.userId = _userId;
    this.namePanel.setText(this.userId);
    this.idPanel.setText(this.getUserName());
  }
}
