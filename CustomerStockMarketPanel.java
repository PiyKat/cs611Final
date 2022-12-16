import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.awt.*;

import java.util.*;

public class CustomerStockMarketPanel extends JPanel {
  private JList<String> stocksJList;
  private StockJPanel stockJPanel;
  private static Font titleFont = new Font("Calibri", Font.BOLD, 32);
  private static Font textFont = new Font("Calibri", Font.PLAIN, 16);
  private static Font boldTextFont = new Font("Calibri", Font.BOLD, 16);
  private static Font listFont = new Font("Calibri", Font.BOLD, 14);
  private StockController sc = new StockController();
  private BackEndController bec = new BackEndController();

  private String[] stockSymbols, stockNames;
  private Map<String, Map<String, String>> stockList;
  private String userId;

  class StockJPanel extends JPanel {
    private JLabel stockNameJLabel;
    private String stockId, stockSymbol;
    private DoubelValPanel currentPriceJPanel, changePriceJPanel, lowJPanel, highJPanel;
    private DateJPanel dateJPanel;
    private Map<String, String> stockDetail;

    class DoubelValPanel extends JPanel {
      private JLabel priceLabel;
      private JTextField priceField;
      private boolean colorful;

      public DoubelValPanel(String labelText, double val, boolean setColor) {
        this.setLayout(new GridLayout(1, 2));
        this.priceLabel = new JLabel(labelText, JLabel.CENTER);
        this.priceLabel.setFont(boldTextFont);
        this.colorful = setColor;

        this.priceField = new JTextField(Double.toString(val));
        priceField.setEditable(false);
        priceField.setFont(boldTextFont);

        if (colorful) {
          if (val < 0) {
            priceField.setForeground(Color.GREEN);
          } else {
            priceField.setForeground(Color.RED);
          }
        }

        this.add(priceLabel);
        this.add(priceField);
      }

      public void setPrice(double val) {
        priceField.setText(Double.toString(val));
        if (colorful) {
          if (val < 0) {
            priceField.setForeground(Color.GREEN);
          } else {
            priceField.setForeground(Color.RED);
          }
        }
      }
    }

    class DateJPanel extends JPanel {
      private JLabel label;
      private JTextField dateTextField;

      public DateJPanel(String date) {
        this.setLayout(new GridLayout(1, 2));
        this.label = new JLabel("Latest Trading Date", JLabel.CENTER);
        label.setFont(boldTextFont);
        this.dateTextField = new JTextField(date, JLabel.CENTER);
        dateTextField.setFont(boldTextFont);
        dateTextField.setEditable(false);
        this.add(label);
        this.add(dateTextField);
      }

      public void setText(String date) {
        this.dateTextField.setText(date);
      }
    }

    private JPanel initDateJPanel(String date) {
      JPanel panel = new JPanel(new GridLayout(1, 2));
      JLabel dateJLabel = new JLabel("Latest Trading Date", JLabel.CENTER);
      dateJLabel.setFont(textFont);
      JTextField dateJField = new JTextField(date, JLabel.CENTER);
      dateJField.setFont(textFont);
      dateJField.setEditable(false);
      panel.add(dateJLabel);
      panel.add(dateJField);
      return panel;
    }

    class updateActionListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
        ArrayList<String> secAccIds = bec.getSecurityAccounts(userId);
        if (secAccIds.size() == 0)
          return;
        sc.buyStock(secAccIds.get(0), stockSymbol, 1);

        JDialog buyDialog = new JDialog();
        buyDialog.setTitle("Bought!");
        buyDialog.setSize(200, 100);
        buyDialog.setLayout(new GridLayout(1, 1));
        JLabel buyText = new JLabel("Bought!", JLabel.CENTER);
        buyText.setFont(titleFont);
        buyDialog.add(buyText);
        buyDialog.setVisible(true);
      }

    };

    public StockJPanel(String _stockSymbol) {
      this.stockSymbol = _stockSymbol;
      this.stockDetail = stockList.get(this.stockSymbol);
      this.stockId = stockDetail.get("company");

      this.stockNameJLabel = new JLabel(this.stockId + " - " + this.stockSymbol, JLabel.CENTER);
      stockNameJLabel.setFont(titleFont);

      if (stockDetail.get("price") == null)
        return;

      double price = Double.parseDouble(stockDetail.get("price"));
      double change = Double.parseDouble(stockDetail.get("change"));
      String ltd = stockDetail.get("latest_trading_day");
      double highPrice = Double.parseDouble(stockDetail.get("high"));
      double lowPrice = Double.parseDouble(stockDetail.get("low"));

      this.currentPriceJPanel = new DoubelValPanel("Current Price", price, false);
      this.changePriceJPanel = new DoubelValPanel("Changes", change, true);
      this.dateJPanel = new DateJPanel(ltd);
      this.lowJPanel = new DoubelValPanel("Low", lowPrice, false);
      this.highJPanel = new DoubelValPanel("High", highPrice, false);

      JButton updateJButton = new JButton("Buy Stock");
      updateJButton.setFont(boldTextFont);
      updateJButton.addActionListener(new updateActionListener());

      this.setLayout(new GridLayout(7, 1));
      this.add(stockNameJLabel);
      this.add(currentPriceJPanel);
      this.add(changePriceJPanel);
      this.add(lowJPanel);
      this.add(highJPanel);
      this.add(dateJPanel);
      this.add(updateJButton);
    }

    public void setStockSymbol(String _stockSymbol) {
      this.stockSymbol = _stockSymbol;
      if (this.stockNameJLabel != null) {
        this.stockDetail = stockList.get(this.stockSymbol);

        if (stockDetail.get("price") == null) {
          this.currentPriceJPanel.setPrice(0);
          this.changePriceJPanel.setPrice(0);
          this.dateJPanel.setText("N/A");
          this.lowJPanel.setPrice(0);
          this.highJPanel.setPrice(0);
          return;
        }

        this.stockId = stockDetail.get("company");
        double price = Double.parseDouble(stockDetail.get("price"));
        double change = Double.parseDouble(stockDetail.get("change"));
        String ltd = stockDetail.get("latest_trading_day");
        double highPrice = Double.parseDouble(stockDetail.get("high"));
        double lowPrice = Double.parseDouble(stockDetail.get("low"));

        this.stockNameJLabel.setText(this.stockId + " - " + this.stockSymbol);
        this.currentPriceJPanel.setPrice(price);
        this.changePriceJPanel.setPrice(change);
        this.dateJPanel.setText(ltd);
        this.lowJPanel.setPrice(lowPrice);
        this.highJPanel.setPrice(highPrice);
      }
    }
  }

  private void setStockPanel(String stockId) {
    this.stockJPanel = new StockJPanel(stockId);
  }

  class stockListListener implements ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {
      JList<String> list = (JList<String>) e.getSource();

      if (/* e.getValueIsAdjusting() && */!list.isSelectionEmpty()) {
        int idx = list.getSelectedIndex();
        stockJPanel.setStockSymbol(stockSymbols[idx]);
      }

    }

  };

  public CustomerStockMarketPanel(String _userId) {
    this.userId = _userId;
    this.stockNames = sc.returnStockCompanies();
    this.stockSymbols = sc.returnStockSymbols();
    this.stockList = sc.returnStocksList();

    this.setLayout(new GridLayout(1, 2));

    stocksJList = new JList<String>(this.stockSymbols);
    stocksJList.setFont(listFont);
    stocksJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    stocksJList.addListSelectionListener(new stockListListener());

    this.setStockPanel(this.stockSymbols[0]);
    this.add(new JScrollPane(stocksJList));
    this.add(stockJPanel);
  }
}
