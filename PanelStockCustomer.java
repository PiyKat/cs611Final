import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

public class PanelStockCustomer {
    private String userId;
    private DataController dc = new DataController();
    private String[] stockSymbols, stockNames;
    private Map<String, Map<String, String>> stockList;
    private StockController sc = new StockController();
    private String selectedStock;
    private BackEndController bed;

    private void buyStock() {
    }

    class BuyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            buyStock();
        }

    };

    class StockSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList<String> list = (JList) e.getSource();
            if (/* e.getValueIsAdjusting() && */!list.isSelectionEmpty()) {
                selectedStock = list.getSelectedValue();
            }
        }

    };

    public PanelStockCustomer(String userId) {
        this.userId = userId;
        JFrame jFrame = new JFrame();

        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PanelCustomer panelCustomer = new PanelCustomer(userId);
            }
        });

        jFrame.setSize(800, 400);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel marketJPanel = new CustomerStockMarketPanel(userId);
        JPanel sellJPanel = new CustomerSellPanel(userId);

        tabbedPane.addTab("Buy Stocks", marketJPanel);
        tabbedPane.addTab("Sell Stocks", sellJPanel);

        jFrame.add(tabbedPane);

        jFrame.setVisible(true);
        
    }

    public ArrayList<String> getStocks() {
        this.stockNames = sc.returnStockCompanies();
        this.stockSymbols = sc.returnStockSymbols();
        this.stockList = sc.returnStocksList();
        ArrayList<String> stockTicker = new ArrayList<>();

        for (int i = 0; i < stockSymbols.length; i++) {
            if (stockSymbols[i] != null && stockNames[i] != null) {
                stockTicker.add(stockSymbols[i]);
            }
        }
        return stockTicker;
    }
}
