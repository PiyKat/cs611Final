import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.util.ArrayList;

public class ManagerWindow extends JFrame {
  private JPanel stockMarketJPanel, customerJPanel, reportJPanel, summaryJPanel, bankTransJPanel, userCountJPanel;

  private String bankName = "Fancy Bank";
  private ArrayList<Transaction> transactionList;

  private JPanel bankBalanceJPanel;
  private JTabbedPane tabbedPane;

  private JLabel bankNameJLabel;

  private BackEndController beCtr;
  private DataController dataCtr;

  private static Font titleFont = new Font("Calibri", Font.BOLD, 32);
  private static Font textFont = new Font("Calibri", Font.BOLD, 24);
  private static Font listFont = new Font("Calibri", Font.BOLD, 14);

  private String getBankName() {
    return bankName;
  }

  private double getBankBalance() {
    return beCtr.getTotalBal();
  }

  private ArrayList<String> getAllUserIds() {
    return beCtr.getAllUserIds();
  }

  private int getTransCounts() {
    return dataCtr.getAllTrans().size();
  }

  private int getUserCounts() {
    return dataCtr.getAllUsers().size();
  }

  private JPanel initIntegerPanel(String text, int val) {
    JPanel panel = new JPanel(new GridLayout(1, 2));
    JLabel label = new JLabel(text, JLabel.CENTER);
    label.setFont(textFont);

    JTextField textField = new JTextField(Integer.toString(val));
    textField.setEditable(false);
    textField.setHorizontalAlignment(JTextField.CENTER);
    textField.setFont(textFont);

    panel.add(label);
    panel.add(textField);
    return panel;
  }

  private JPanel initBalancePanel() {
    JPanel panel = new JPanel(new GridLayout(1, 2));
    JLabel label = new JLabel("Total Bank Balance", JLabel.CENTER);
    label.setFont(textFont);

    JTextField textField = new JTextField(Double.toString(this.getBankBalance()));
    textField.setEditable(false);
    textField.setHorizontalAlignment(JTextField.CENTER);
    textField.setFont(textFont);

    panel.add(label);
    panel.add(textField);
    return panel;
  }

  private void initSummary() {
    bankNameJLabel = new JLabel(this.getBankName(), JLabel.CENTER);
    bankNameJLabel.setFont(titleFont);
    bankNameJLabel.setForeground(Color.MAGENTA);

    bankBalanceJPanel = this.initBalancePanel();

    userCountJPanel = this.initIntegerPanel("Users", this.getUserCounts());
    bankTransJPanel = this.initIntegerPanel("Transactions Today", this.getTransCounts());

    summaryJPanel = new JPanel(new GridLayout(4, 1));
    summaryJPanel.add(bankNameJLabel, 0);
    summaryJPanel.add(userCountJPanel, 1);
    summaryJPanel.add(bankBalanceJPanel, 2);
    summaryJPanel.add(bankTransJPanel, 3);
  }

  // Customer Tab
  class customerPanel extends JPanel {
    private ArrayList<String> userIds;
    private JList<String> customerJList;
    private CustomerDetailPanel detailPanel;

    class customerListListener implements ListSelectionListener {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        JList<String> list = (JList) e.getSource();
        if (/* e.getValueIsAdjusting() && */!list.isSelectionEmpty()) {
          int idx = list.getSelectedIndex();
          detailPanel.setUserId(userIds.get(idx));
        }
      }
    }

    public customerPanel(ArrayList<String> _userIds) {
      this.userIds = _userIds;
      ArrayList<String> userNames = new ArrayList<String>();

      for (String userId : userIds) {
        userNames.add(beCtr.getUserName(userId));
      }

      customerJList = new JList(userNames.toArray());
      customerJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      customerJList.addListSelectionListener(new customerListListener());

      detailPanel = new CustomerDetailPanel(userIds.get(0));

      this.setLayout(new GridLayout(1, 2));
      this.add(new JScrollPane(customerJList));
      this.add(detailPanel);
    }
  };

  class reportPanel extends JPanel {
    private JList<String> transJList;
    private TransactionPanel transPanel;

    class transSelectionListener implements ListSelectionListener {

      @Override
      public void valueChanged(ListSelectionEvent e) {
        JList<String> list = (JList) e.getSource();
        if (/* e.getValueIsAdjusting() && */!list.isSelectionEmpty()) {
          int idx = list.getSelectedIndex();
          transPanel.setTransaction(transactionList.get(idx));
        }
      }

    }

    public reportPanel() {
      this.setLayout(new GridLayout(1, 2));

      ArrayList<String> transName = new ArrayList<String>();

      for (Transaction trans : transactionList) {
        transName.add(trans.getTransId());
      }
      transJList = new JList(transName.toArray());
      transJList.setFont(listFont);
      transJList.addListSelectionListener(new transSelectionListener());

      if (transactionList.size() <= 0) {
        transPanel = new TransactionPanel();
      } else {
        transPanel = new TransactionPanel(transactionList.get(0));
      }

      this.add(new JScrollPane(transJList));
      this.add(transPanel);
    }
  };

  // Manager Window
  public ManagerWindow() {
    // Init controllers
    beCtr = new BackEndController();
    dataCtr = new DataController();

    transactionList = dataCtr.getTodayTransaction();

    // Frame settings
    this.setTitle("Manager");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new GridLayout(1, 1));
    this.setSize(800, 400);

    this.customerJPanel = new customerPanel(this.getAllUserIds());
    this.initSummary();

    reportJPanel = new reportPanel();

    stockMarketJPanel = new ManagerStockMarketPanel("Company Name");

    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Summary", summaryJPanel);
    tabbedPane.addTab("Customers", customerJPanel);
    tabbedPane.addTab("Reports", reportJPanel);
    tabbedPane.addTab("Stock Market", stockMarketJPanel);

    this.add(tabbedPane);
    this.setResizable(false);
  }

  public static void main(String[] args) {
    ManagerWindow window = new ManagerWindow();
    window.setVisible(true);
  }
};