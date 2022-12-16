import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

public class TransactionPanel extends JPanel {
  private static Font titleFont = new Font("Calibri", Font.BOLD, 32);
  private static Font textFont = new Font("Calibri", Font.PLAIN, 20);
  private static Font boldTextFont = new Font("Calibri", Font.BOLD, 20);

  private JPanel fromJPanel, toJPanel, amountJPanel, dateJPanel;
  private JTextField amountJField;

  private JPanel initAccountPanel(String text, String accId, Color color) {
    JPanel panel = new JPanel(new GridLayout(1, 2));
    JLabel typeLabel = new JLabel(text, JLabel.CENTER);
    typeLabel.setFont(titleFont);

    JLabel accLabel = new JLabel(accId, JLabel.CENTER);
    accLabel.setFont(boldTextFont);
    accLabel.setForeground(color);

    panel.add(typeLabel);
    panel.add(accLabel);
    return panel;
  }

  public TransactionPanel(Transaction trans) {
    this.setLayout(new GridLayout(4, 1));
    this.dateJPanel = this.initDatePanel(trans.getTime());
    this.fromJPanel = this.initAccountPanel("Sender", trans.getSenderId(), Color.RED);
    this.toJPanel = this.initAccountPanel("Receiver", trans.getReceiverId(), Color.GREEN);

    amountJPanel = new JPanel(new GridLayout(1, 2));

    amountJField = new JTextField("$ " + Double.toString(trans.getAmt()));
    amountJField.setHorizontalAlignment(JTextField.CENTER);
    amountJField.setFont(titleFont);
    amountJField.setEditable(false);

    JLabel arrowJLabel = new JLabel("Amount", JLabel.CENTER);
    arrowJLabel.setFont(titleFont);

    amountJPanel.add(arrowJLabel);
    amountJPanel.add(amountJField);

    this.add(dateJPanel);
    this.add(fromJPanel);
    this.add(amountJPanel);
    this.add(toJPanel);
  }

  private JPanel initDatePanel(Timestamp timestamp) {
    JPanel panel = new JPanel(new GridLayout(1, 2));
    JLabel label1 = new JLabel("Date", JLabel.CENTER);
    label1.setFont(titleFont);

    JLabel label2 = new JLabel(timestamp.toString(), JLabel.CENTER);
    label2.setFont(boldTextFont);

    panel.add(label1);
    panel.add(label2);
    return panel;
  }

  public TransactionPanel() {
    this.setLayout(new GridLayout(4, 1));
    this.dateJPanel = this.initDatePanel(new Timestamp(0));
    this.fromJPanel = this.initAccountPanel("Sender", "User A", Color.RED);
    this.toJPanel = this.initAccountPanel("Receiver", "User B", Color.GREEN);

    amountJPanel = new JPanel(new GridLayout(1, 2));

    amountJField = new JTextField("$ " + Double.toString(100.0));
    amountJField.setHorizontalAlignment(JTextField.CENTER);
    amountJField.setFont(boldTextFont);
    amountJField.setEditable(false);

    JLabel arrowJLabel = new JLabel("Amount", JLabel.CENTER);
    arrowJLabel.setFont(titleFont);

    amountJPanel.add(arrowJLabel);
    amountJPanel.add(amountJField);


    this.add(dateJPanel);
    this.add(fromJPanel);
    this.add(amountJPanel);
    this.add(toJPanel);
  }

  public void setTransaction(Transaction trans) {
    this.fromJPanel = this.initAccountPanel("Sender", trans.getSenderId(), Color.RED);
    this.toJPanel = this.initAccountPanel("Receiver", trans.getReceiverId(), Color.GREEN);
    this.amountJField.setText("$ " + Double.toString(trans.getAmt()));
  }
};