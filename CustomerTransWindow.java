import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.util.ArrayList;

public class CustomerTransWindow extends JFrame {
  private JPanel transJPanel;
  private JList<String> transJList;
  private ArrayList<Transaction> transactionList;
  private String userId;
  private DataController daCtr;

  class transSelectionListener implements ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {
      JList<String> list = (JList<String>) e.getSource();

      if (/* e.getValueIsAdjusting() && */!list.isSelectionEmpty()) {
        int idx = list.getSelectedIndex();
        transJPanel = new TransactionPanel(transactionList.get(idx));
      }
    }

  };

  public CustomerTransWindow(String _userId) {
    this.userId = _userId;
    this.daCtr = new DataController();
    this.setTitle("Transactions");
    this.setLayout(new GridLayout(1, 2));
    this.setSize(800, 400);

    transactionList = daCtr.getUserTrans(userId);

    ArrayList<String> transName = new ArrayList<String>();

    for (Transaction trans : transactionList) {
      transName.add(trans.getTransId());
    }

    transJList = new JList(transName.toArray());
    transJList.addListSelectionListener(new transSelectionListener());

    if (transactionList.size() <= 0) {
      transJPanel = new TransactionPanel();
    } else {
      transJPanel = new TransactionPanel(transactionList.get(0));
    }
    this.add(transJList);
    this.add(transJPanel);

  }
}
