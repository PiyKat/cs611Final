import javax.swing.*;
import java.awt.*;

public class CustomerTransPanel extends JFrame {
  private JPanel transJPanel;

  public CustomerTransPanel(String customerName, String[] transList) {
    this.setLayout(new GridLayout(1, 1));
    this.setSize(800, 800);
    this.setName(customerName);

    transJPanel = new JPanel(new GridLayout(1, 2));
    JLabel topLabel = new JLabel("Transcations");
    JList<String> transJList = new JList<String>(transList);

    transJPanel.add(topLabel);
    transJPanel.add(transJList);
  }
}
