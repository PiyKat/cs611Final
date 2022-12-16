import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelCustomer extends JFrame {
    private String userId;
    private BackEndController bec = new BackEndController();
    private DataController dc = new DataController();
    JPanel custInfo = new JPanel();
    JPanel old = new JPanel();
    JTabbedPane tabbedPane = new JTabbedPane();

    public void hideWindow(){
        this.setVisible(false);
    }

    public PanelCustomer(String userId) {
        this.userId = userId;
        this.setTitle("Customer Information");
        this.setSize(800, 800);

        // Customer Information Panel
        BoxLayout boxLayout1 = new BoxLayout(custInfo, BoxLayout.Y_AXIS);
        custInfo.setLayout(boxLayout1);

        Box verticalBox1 = Box.createVerticalBox();
        Box rowBox11 = Box.createHorizontalBox();
        JLabel label1 = new JLabel("Name :" + bec.getUserName(userId));
        rowBox11.add(label1);

        Box rowBox22 = Box.createHorizontalBox();
        JButton buttonViewAccounts = new JButton("View Accounts");
        JButton buttonLoanAccounts = new JButton("View Loans");
        JButton buttonStockAccounts = new JButton("View Stocks");
        JButton actions = new JButton("Actions");
        rowBox22.add(buttonViewAccounts);
        rowBox22.add(buttonLoanAccounts);
        rowBox22.add(buttonStockAccounts);
        rowBox22.add(actions);

        verticalBox1.add(Box.createVerticalStrut(10));
        verticalBox1.add(rowBox11);
        verticalBox1.add(Box.createVerticalStrut(10));
        verticalBox1.add(rowBox22);

        custInfo.add(verticalBox1);

        this.add(custInfo);
        this.setVisible(true);

        buttonViewAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel custInfo1 = new JPanel();
                BoxLayout boxLayout = new BoxLayout(custInfo1, BoxLayout.Y_AXIS);
                EmptyBorder emptyBorder = new EmptyBorder(20, 20, 20, 20);
                custInfo1.setBorder(emptyBorder);
                custInfo1.setLayout(boxLayout);
                
                Box verticalBox = Box.createVerticalBox();
                verticalBox.add(Box.createVerticalStrut(10));
                Box rowBox1 = Box.createHorizontalBox();

                ArrayList<String> accountsList = bec.getAllAccounts(userId);
                String[] accounts = new String[accountsList.size()];
                accounts = accountsList.toArray(accounts);
                JLabel boxname1 = new JLabel("Select the account");
                JList accountList = new JList(accounts);
                // accountList.setSelectedIndex(0);
                rowBox1.add(boxname1);
                rowBox1.add(accountList);
                verticalBox.add(rowBox1);
                verticalBox.add(Box.createVerticalStrut(10));

                Box rowBox2 = Box.createHorizontalBox();
                JLabel accountType = new JLabel("");
                rowBox2.add(accountType);
                verticalBox.add(rowBox2);

                verticalBox.add(Box.createVerticalStrut(10));

                Box rowBox3 = Box.createHorizontalBox();
                JLabel accBal = new JLabel("");
                rowBox3.add(accBal);
                verticalBox.add(rowBox3);

                accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                accountList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int index = accountList.getSelectedIndex();
                        if (index != -1 && !e.getValueIsAdjusting()) {
                            accountType.setText("Type: " + bec.getAccountType(String.valueOf(accountList.getSelectedValue())));
                            accBal.setText("Balance: " + bec.getAccBal(String.valueOf(accountList.getSelectedValue())));
                            custInfo.revalidate();
                            custInfo.repaint();
                        }
                    }

                });

                custInfo.remove(old);
                old = custInfo1;
                custInfo1.add(verticalBox);

                custInfo.add(custInfo1);
                custInfo.revalidate();
                custInfo.repaint();
                custInfo.remove(custInfo);
            }
        });

        buttonLoanAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel custInfo1 = new JPanel();
                BoxLayout boxLayout = new BoxLayout(custInfo1, BoxLayout.Y_AXIS);
                EmptyBorder emptyBorder = new EmptyBorder(20, 20, 20, 20);
                custInfo1.setBorder(emptyBorder);
                custInfo1.setLayout(boxLayout);

                Box verticalBox = Box.createVerticalBox();
                verticalBox.add(Box.createVerticalStrut(10));
                

                if (bec.getAllLoans(userId).size() > 0) {
                    Box rowBox1 = Box.createHorizontalBox();
                    ArrayList<String> loansArr = bec.getAllLoans(userId);
                    String[] loans = new String[loansArr.size()];
                    loans = loansArr.toArray(loans);
                    JLabel boxname1 = new JLabel("Select the Loan");
                    JList loanList = new JList(loans);
                    // loanList.setSelectedIndex(0);
                    rowBox1.add(boxname1);
                    rowBox1.add(loanList);
                    verticalBox.add(rowBox1);
                    verticalBox.add(Box.createVerticalStrut(10));

                    Box rowBox2 = Box.createHorizontalBox();
                    JLabel loanType = new JLabel("");
                    rowBox2.add(loanType);
                    verticalBox.add(rowBox2);
                    verticalBox.add(Box.createVerticalStrut(10));
           
                    Box rowBox3 = Box.createHorizontalBox();
                    JLabel loanAmt = new JLabel("");
                    rowBox3.add(loanAmt);
                    verticalBox.add(rowBox3);

                    loanList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    loanList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int index = loanList.getSelectedIndex();
                        if (index != -1 && !e.getValueIsAdjusting()) {
                            System.out.println("hello");
                            loanType.setText("Type: " + bec.getLoanType(String.valueOf(loanList.getSelectedValue())));
                            loanAmt.setText("Status: " + bec.getLoanStatus(String.valueOf(loanList.getSelectedValue())));
                            custInfo.revalidate();
                            custInfo.repaint();
                        }
                    }

                });

                    
                } else {
                    verticalBox.add(Box.createVerticalStrut(10));
                    Box rowBox1 = Box.createHorizontalBox();
                    JLabel boxname1 = new JLabel("No Loans");
                    rowBox1.add(boxname1);
                    verticalBox.add(rowBox1);
                    verticalBox.add(Box.createVerticalStrut(10));
                }

                custInfo.remove(old);
                old = custInfo1;
                custInfo1.add(verticalBox);

                custInfo.add(custInfo1);
                custInfo.revalidate();
                custInfo.repaint();
                custInfo.remove(custInfo);
            }
        });

        buttonStockAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel custInfo1 = new JPanel();
                BoxLayout boxLayout = new BoxLayout(custInfo1, BoxLayout.Y_AXIS);
                EmptyBorder emptyBorder = new EmptyBorder(20, 20, 20, 20);
                custInfo1.setBorder(emptyBorder);
                custInfo1.setLayout(boxLayout);
                
                Box verticalBox = Box.createVerticalBox();
                verticalBox.add(Box.createVerticalStrut(10));

                if (dc.getUserStocks(userId).size() > 0) {
                    Box rowBox1 = Box.createHorizontalBox();
                    ArrayList<String> st = dc.getUserStocks(userId);
                    for (int i = 0; i < st.size(); i++) {
                        JLabel loanType = new JLabel("Stock: " + st);
                        rowBox1.add(loanType);
                    }

                    verticalBox.add(rowBox1);
                    verticalBox.add(Box.createVerticalStrut(10));

                } else {
                    verticalBox.add(Box.createVerticalStrut(10));
                    Box rowBox1 = Box.createHorizontalBox();
                    JLabel boxname1 = new JLabel("No Stocks");
                    rowBox1.add(boxname1);
                    verticalBox.add(rowBox1);
                    verticalBox.add(Box.createVerticalStrut(10));
                }

                custInfo.remove(old);
                old = custInfo1;
                custInfo1.add(verticalBox);

                custInfo.add(custInfo1);
                custInfo.revalidate();
                custInfo.repaint();
                custInfo.remove(custInfo);
            }
        });

        actions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel custInfo1 = new JPanel();
                BoxLayout boxLayout = new BoxLayout(custInfo1, BoxLayout.Y_AXIS);
                EmptyBorder emptyBorder = new EmptyBorder(20, 20, 20, 20);
                custInfo1.setBorder(emptyBorder);
                custInfo1.setLayout(boxLayout);
                // custInfo.add(custInfo1);

                Box verticalBox = Box.createVerticalBox();
                verticalBox.add(Box.createVerticalStrut(10));
                ArrayList<String> accountsList = bec.getAllAccounts(userId);
                String[] accounts = new String[accountsList.size()];
                accounts = accountsList.toArray(accounts);
                JLabel boxname1 = new JLabel("Select what you want to do:");
                verticalBox.add(boxname1);
                verticalBox.add(Box.createVerticalStrut(10));

                Box rowBox2 = Box.createHorizontalBox();
                JButton createAcc = new JButton("Create an Account");
                JButton requestLoan = new JButton("Request Loan");
                JButton transfer = new JButton("Transfer Money");
                JButton wOrD = new JButton("Withdraw or Deposit");
                JButton buyOrSell = new JButton("Buy or Sell Stocks");

                rowBox2.add(createAcc);
                rowBox2.add(requestLoan);
                rowBox2.add(transfer);
                rowBox2.add(wOrD);
                rowBox2.add(buyOrSell);

                verticalBox.add(rowBox2);
                verticalBox.add(Box.createVerticalStrut(10));

                custInfo.remove(old);
                old = custInfo1;
                custInfo1.add(verticalBox);

                custInfo.add(custInfo1);
                custInfo.revalidate();
                custInfo.repaint();
                custInfo.remove(custInfo);

                createAcc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PanelCreateAccount pan = new PanelCreateAccount(userId);
                        hideWindow();
                    }
                });

                requestLoan.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PanelLoan pan = new PanelLoan(userId);
                        hideWindow();
                    }
                });

                transfer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PanelTransfer pan = new PanelTransfer(userId);
                        hideWindow();
                    }
                });

                wOrD.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PanelDepositAndWithdraw pan = new PanelDepositAndWithdraw(userId);
                        hideWindow();
                    }
                });

                buyOrSell.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(bec.getSecurityAccounts(userId).size() > 0){
                        PanelStockCustomer pan = new PanelStockCustomer(userId);
                        }
                        else{
                            PanelCustomer c = new PanelCustomer(userId);
                        }
                        hideWindow();
                    }
                });

            }
        });

    }
}