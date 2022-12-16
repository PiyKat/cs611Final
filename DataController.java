import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
// import java.sql.Timestamp;
import java.sql.Timestamp;

public class DataController {
    private File dataFile;
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;
    private Element bank;
    private Timestamp time;
    private Date now;

    public DataController() {
        try {
            dataFile = new File("BankData.xml");
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (Exception e) {
        }
        if (!(dataFile.exists())) {
            createFile();
        } else {
            initFile();
        }
    }

    // Function to initialise file variables
    private void initFile() {
        try {
            document = (Document) documentBuilder.parse(dataFile);
            bank = document.getDocumentElement();
        } catch (Exception e) {
        }
    }

    // Function to create the file
    private void createFile() {
        document = (Document) documentBuilder.newDocument();
        // creating the root bank element
        bank = document.createElement("bank");
        document.appendChild(bank);

        setFile();
        initFile();
    }

    // Function to set file
    private void setFile() {
        try {
            Transformer tFormer = TransformerFactory.newInstance().newTransformer();
            tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
            Source source = new DOMSource(document);
            Result result = new StreamResult(dataFile);
            tFormer.transform(source, result);
        } catch (Exception e) {
        }
    }

    // Function to add user
    public void addUser(String userId, String name, String password, String userType) {
        Element user = document.createElement("user");
        bank.appendChild(user);

        // set attributes to user element
        Attr attr = document.createAttribute("userId");
        attr.setValue(userId);
        user.setAttributeNode(attr);
        attr = document.createAttribute("name");
        attr.setValue(name);
        user.setAttributeNode(attr);
        attr = document.createAttribute("password");
        attr.setValue(password);
        user.setAttributeNode(attr);
        attr = document.createAttribute("userType");
        attr.setValue(userType);
        user.setAttributeNode(attr);
        setFile();
    }

    // Function to get the user
    public BankHuman getUser(String userId) {
        NodeList nodeList = document.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                BankHuman user;
                if (thisNode.getAttributes().getNamedItem("userType").getNodeValue().equalsIgnoreCase("customer")) {
                    String name = thisNode.getAttributes().getNamedItem("name").getNodeValue();
                    String pass = thisNode.getAttributes().getNamedItem("password").getNodeValue();
                    String userType = thisNode.getAttributes().getNamedItem("userType").getNodeValue();
                    user = new Customer(userId, name, pass, userType);
                } else {
                    user = BankManager.getBankManager();
                }
                return user;
            }
        }
        return null;
    }

    // Function to get all users
    public ArrayList<BankHuman> getAllUsers() {
        ArrayList<BankHuman> allUsers = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            BankHuman user;
            String userId = thisNode.getAttributes().getNamedItem("userId").getNodeValue();
            String name = thisNode.getAttributes().getNamedItem("name").getNodeValue();
            String pass = thisNode.getAttributes().getNamedItem("password").getNodeValue();
            String userType = thisNode.getAttributes().getNamedItem("userType").getNodeValue();
            if (thisNode.getAttributes().getNamedItem("userType").getNodeValue().equalsIgnoreCase("customer")) {
                user = new Customer(userId, name, pass, userType);
            } else {
                user = BankManager.getBankManager();
            }
            allUsers.add(user);
        }
        return allUsers;
    }

    // Function to remove a user
    public void removeUser(String userId) {
        NodeList nodeList = document.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                thisNode.getParentNode().removeChild(thisNode);
            }
        }
    }

    // Function to add customer
    public void addCustomer(String userId, String[] accountIds, String[] loanIds, String[] stockIds) {
        Element customer = document.createElement("customer");
        bank.appendChild(customer);

        Attr attr = document.createAttribute("userId");
        attr.setValue(userId);
        customer.setAttributeNode(attr);

        if (accountIds != null) {
            for (int i = 0; i < accountIds.length; i++) {
                Element account = document.createElement("custAccount");
                customer.appendChild(account);
                Attr attr1 = document.createAttribute("accId");
                attr1.setValue(String.valueOf(accountIds[i]));
                account.setAttributeNode(attr1);
            }
        }

        if (loanIds != null) {
            for (int i = 0; i < loanIds.length; i++) {
                Element loan = document.createElement("custLoan");
                customer.appendChild(loan);
                Attr attr1 = document.createAttribute("loanId");
                attr1.setValue(String.valueOf(loanIds[i]));
                loan.setAttributeNode(attr1);
            }
        }

        if (stockIds != null) {
            for (int i = 0; i < stockIds.length; i++) {
                Element stock = document.createElement("custStock");
                customer.appendChild(stock);
                Attr attr1 = document.createAttribute("stockId");
                attr1.setValue(String.valueOf(stockIds[i]));
                stock.setAttributeNode(attr1);
            }
        }
        setFile();
    }

    // Function to get the customer
    public Customer getCustomer(String userId) {
        NodeList nodeList = document.getElementsByTagName("customer");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                Customer cust = (Customer) getUser(userId);
                NodeList accounts = ((Element) thisNode).getElementsByTagName("custAccount");
                for (int j = 0; j < accounts.getLength(); j++) {
                    Node thisAcc = accounts.item(j);
                    cust.addAccount(thisAcc.getAttributes().getNamedItem("accId").getNodeValue());
                }
                NodeList loans = ((Element) thisNode).getElementsByTagName("custLoan");
                for (int j = 0; j < loans.getLength(); j++) {
                    Node thisLoan = loans.item(j);
                    cust.addLoan(thisLoan.getAttributes().getNamedItem("loanId").getNodeValue());
                }
                NodeList stocks = ((Element) thisNode).getElementsByTagName("custStock");
                for (int j = 0; j < stocks.getLength(); j++) {
                    Node thisStock = stocks.item(j);
                    cust.addStocks(thisStock.getAttributes().getNamedItem("stockId").getNodeValue());
                }
                return cust;
            }
        }
        return null;
    }

    // Function to remove account from customer
    public void removeCustomerAcc(String userId, String accId) {
        NodeList nodeList = document.getElementsByTagName("customer");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                NodeList accounts = ((Document) thisNode).getElementsByTagName("custAccount");
                for (int j = 0; j < accounts.getLength(); j++) {
                    Node thisAcc = accounts.item(j);
                    if (thisAcc.getAttributes().getNamedItem("accId").getNodeValue().equalsIgnoreCase(accId)) {
                        thisAcc.getParentNode().removeChild(thisAcc);
                    }
                }
            }
        }
    }

    // funtion to get customer accounts
    public ArrayList<String> getCustomerAcc(String userId) {
        ArrayList<String> acc = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("customer");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                NodeList accounts = ((Document) thisNode).getElementsByTagName("custAccount");
                for (int j = 0; j < accounts.getLength(); j++) {
                    Node thisAcc = accounts.item(j);
                    acc.add(thisAcc.getAttributes().getNamedItem("accId").getNodeValue());
                }
            }
        }
        return acc;
    }

    // Function to remove account from customer
    public void removeCustomerLoan(String userId, String loanId) {
        NodeList nodeList = document.getElementsByTagName("customer");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                NodeList accounts = ((Document) thisNode).getElementsByTagName("custLoan");
                for (int j = 0; j < accounts.getLength(); j++) {
                    Node thisAcc = accounts.item(j);
                    if (thisAcc.getAttributes().getNamedItem("loanId").getNodeValue().equalsIgnoreCase(loanId)) {
                        thisAcc.getParentNode().removeChild(thisAcc);
                    }
                }
            }
        }
    }

    // Function to remove account from customer
    public void removeCustomerStock(String userId, String stockId) {
        NodeList nodeList = document.getElementsByTagName("customer");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                NodeList accounts = ((Document) thisNode).getElementsByTagName("custStock");
                for (int j = 0; j < accounts.getLength(); j++) {
                    Node thisAcc = accounts.item(j);
                    if (thisAcc.getAttributes().getNamedItem("stockId").getNodeValue().equalsIgnoreCase(stockId)) {
                        thisAcc.getParentNode().removeChild(thisAcc);
                    }
                }
            }
        }
    }

    // Function to add customer account
    public void addCustomerAcc(String userId, String accId) {
        NodeList nodeList = document.getElementsByTagName("customer");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                Element account = document.createElement("custAccount");
                thisNode.appendChild(account);
                Attr attr1 = document.createAttribute("accId");
                attr1.setValue(String.valueOf(accId));
                account.setAttributeNode(attr1);
            }
        }
        setFile();
    }

    // Function to add customer loan
    public void addCustomerLoan(String userId, String loanId) {
        NodeList nodeList = document.getElementsByTagName("customer");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                Element account = document.createElement("custLoan");
                thisNode.appendChild(account);
                Attr attr1 = document.createAttribute("loanId");
                attr1.setValue(String.valueOf(loanId));
                account.setAttributeNode(attr1);
            }
        }
        setFile();
    }

    // Function to add customer stock
    public void addCustomerStock(String userId, String loanId) {
        NodeList nodeList = document.getElementsByTagName("customer");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                Element account = document.createElement("custStock");
                thisNode.appendChild(account);
                Attr attr1 = document.createAttribute("loanId");
                attr1.setValue(String.valueOf(loanId));
                account.setAttributeNode(attr1);
            }
        }
        setFile();
    }

    // Function to remove a customer
    public void removeCustomer(String userId) {
        NodeList nodeList = document.getElementsByTagName("customer");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisNode = nodeList.item(i);
            if (thisNode.getAttributes().getNamedItem("userId").getNodeValue().equalsIgnoreCase(userId)) {
                thisNode.getParentNode().removeChild(thisNode);
            }
        }
    }

    // Function to add accounts
    public void addAccount(String accId, String accType, String userId, Currency currency, Double bal, Timestamp time) {
        Element account = document.createElement("account");
        bank.appendChild(account);

        Attr attr = document.createAttribute("accId");
        attr.setValue(accId);
        account.setAttributeNode(attr);

        Attr attr1 = document.createAttribute("accType");
        attr1.setValue(accType);
        account.setAttributeNode(attr1);

        Attr attr2 = document.createAttribute("userId");
        attr2.setValue(userId);
        account.setAttributeNode(attr2);

        Attr attr3 = document.createAttribute("currency");
        attr3.setValue(String.valueOf(currency));
        account.setAttributeNode(attr3);

        Attr attr4 = document.createAttribute("bal");
        attr4.setValue(String.valueOf(bal));
        account.setAttributeNode(attr4);

        Attr attr5 = document.createAttribute("time");
        attr5.setValue(String.valueOf(time));
        account.setAttributeNode(attr5);
        setFile();
    }

    // Function to get the account
    public Account getAccount(String accId) {
        NodeList nodeList = document.getElementsByTagName("account");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("accId").getNodeValue().equalsIgnoreCase(accId)) {
                String accType = thisAcc.getAttributes().getNamedItem("accType").getNodeValue();
                String userId = thisAcc.getAttributes().getNamedItem("userId").getNodeValue();
                Currency curr = Currency.valueOf(thisAcc.getAttributes().getNamedItem("currency").getNodeValue());
                Double bal = Double.valueOf(thisAcc.getAttributes().getNamedItem("bal").getNodeValue());
                Timestamp time = Timestamp.valueOf(thisAcc.getAttributes().getNamedItem("time").getNodeValue());
                AccountFactory af = new AccountFactory();
                return af.getOldAccount(accId, accType, userId, curr, bal, time);
            }
        }
        return null;
    }

    // function to get account time
    public Timestamp getAccTime(String accId) {
        NodeList nodeList = document.getElementsByTagName("account");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("accId").getNodeValue().equalsIgnoreCase(accId)) {
                return Timestamp.valueOf(thisAcc.getAttributes().getNamedItem("time").getNodeValue());
            }
        }
        return null;
    }

    // Function to get account balance
    public Double getAccountBalance(String accId) {
        NodeList nodeList = document.getElementsByTagName("account");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("accId").getNodeValue().equalsIgnoreCase(accId)) {
                return Double.valueOf(thisAcc.getAttributes().getNamedItem("bal").getNodeValue());
            }
        }
        return null;
    }

    // Function to get all account Ids
    public ArrayList<String> getAllAccIds() {
        try {
            ArrayList<String> allAccIds = new ArrayList<>();
            NodeList nodeList = document.getElementsByTagName("account");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node thisAcc = nodeList.item(i);
                allAccIds.add(thisAcc.getAttributes().getNamedItem("accId").getNodeValue());
            }
            return allAccIds;
        } catch (Exception e) {
            return null;
        }
    }

    // Function to get account balance
    public void setAccountBalance(String accId, Double bal) {
        NodeList nodeList = document.getElementsByTagName("account");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("accId").getNodeValue().equalsIgnoreCase(accId)) {
                ((Attr) thisAcc.getAttributes().getNamedItem("bal")).setValue(String.valueOf(bal));
            }
        }
        setFile();
    }

    // Function to get currency
    public Currency getCurrency(String accId) {
        NodeList nodeList = document.getElementsByTagName("account");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("accId").getNodeValue().equalsIgnoreCase(accId)) {
                return Currency.valueOf(thisAcc.getAttributes().getNamedItem("currency").getNodeValue());
            }
        }
        return null;
    }

    // Function to get account type
    public String getAccType(String accId) {
        NodeList nodeList = document.getElementsByTagName("account");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("accId").getNodeValue().equalsIgnoreCase(accId)) {
                return thisAcc.getAttributes().getNamedItem("accType").getNodeValue();
            }
        }
        return null;
    }

    // Function to remove a account
    public void removeAccount(String accId) {
        NodeList nodeList = document.getElementsByTagName("account");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("accId").getNodeValue().equalsIgnoreCase(accId)) {
                thisAcc.getParentNode().removeChild(thisAcc);
            }
        }
    }

    // Function to add loans
    public void addLoan(String loanId, String accId, String userId, Double collateral, Timestamp appDate,
            Timestamp reqDate, String status, String loanType, Double amt) {
        Element loan = document.createElement("loan");
        bank.appendChild(loan);

        Attr attr = document.createAttribute("loanId");
        attr.setValue(loanId);
        loan.setAttributeNode(attr);

        Attr attr1 = document.createAttribute("accId");
        attr1.setValue(accId);
        loan.setAttributeNode(attr1);

        Attr attr2 = document.createAttribute("userId");
        attr2.setValue(userId);
        loan.setAttributeNode(attr2);

        Attr attr3 = document.createAttribute("collateral");
        attr3.setValue(String.valueOf(collateral));
        loan.setAttributeNode(attr3);

        Attr attr4 = document.createAttribute("appDate");
        attr4.setValue(String.valueOf(appDate));
        loan.setAttributeNode(attr4);

        Attr attr5 = document.createAttribute("reqDate");
        attr5.setValue(String.valueOf(reqDate));
        loan.setAttributeNode(attr5);

        Attr attr6 = document.createAttribute("status");
        attr6.setValue(String.valueOf(status));
        loan.setAttributeNode(attr6);

        Attr attr7 = document.createAttribute("loanType");
        attr7.setValue(loanType);
        loan.setAttributeNode(attr7);

        Attr attr8 = document.createAttribute("amt");
        attr8.setValue(String.valueOf(amt));
        loan.setAttributeNode(attr8);
        setFile();
    }

    // Function to get the loan
    public Loan getLoan(String loanId) {
        NodeList nodeList = document.getElementsByTagName("loan");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisLoan = nodeList.item(i);
            if (thisLoan.getAttributes().getNamedItem("loanId").getNodeValue().equalsIgnoreCase(loanId)) {
                String accId = thisLoan.getAttributes().getNamedItem("accId").getNodeValue();
                String userId = thisLoan.getAttributes().getNamedItem("userId").getNodeValue();
                Double collateral = Double.valueOf(thisLoan.getAttributes().getNamedItem("collateral").getNodeValue());
                String temp = thisLoan.getAttributes().getNamedItem("appDate").getNodeValue();
                Timestamp appDate = (temp.equalsIgnoreCase("null")) ? null : Timestamp.valueOf(temp);
                Timestamp reqDate = Timestamp.valueOf(thisLoan.getAttributes().getNamedItem("reqDate").getNodeValue());
                String status = thisLoan.getAttributes().getNamedItem("status").getNodeValue();
                String loanType = thisLoan.getAttributes().getNamedItem("loanType").getNodeValue();
                Double amt = Double.valueOf(thisLoan.getAttributes().getNamedItem("amt").getNodeValue());
                Loan loan = new Loan(loanId, accId, userId, collateral, appDate, reqDate, status, loanType, amt);

                return loan;
            }
        }
        return null;
    }

    // Function to approve loan
    public void approveLoan(String loanId) {
        NodeList nodeList = document.getElementsByTagName("loan");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("loanId").getNodeValue().equalsIgnoreCase(loanId)) {
                ((Attr) thisAcc.getAttributes().getNamedItem("status")).setValue("approve");
                this.now = new java.util.Date();
                this.time = new java.sql.Timestamp(now.getTime());
                ((Attr) thisAcc.getAttributes().getNamedItem("status")).setValue(String.valueOf(time));
            }
        }
        setFile();
    }

    // function to get loan approvalDate
    public Timestamp getLoanAppDate(String loanId) {
        NodeList nodeList = document.getElementsByTagName("loan");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("loanId").getNodeValue().equalsIgnoreCase(loanId)) {
                String appDate = thisAcc.getAttributes().getNamedItem("appDate").getNodeValue();
                if (!(appDate.equalsIgnoreCase("null"))) {
                    return Timestamp.valueOf(appDate);
                }
            }
        }
        return null;
    }

    // Function to get loan account id
    public String getLoanAccId(String loanId) {
        NodeList nodeList = document.getElementsByTagName("loan");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("loanId").getNodeValue().equalsIgnoreCase(loanId)) {
                return thisAcc.getAttributes().getNamedItem("accId").getNodeValue();
            }
        }
        return null;
    }

    // Function to remove a loan
    public void removeLoan(String loanId) {
        NodeList nodeList = document.getElementsByTagName("loan");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisLoan = nodeList.item(i);
            if (thisLoan.getAttributes().getNamedItem("loanId").getNodeValue().equalsIgnoreCase(loanId)) {
                thisLoan.getParentNode().removeChild(thisLoan);
            }
        }
    }

    // Function to add stocks
    public void addStock(String stockId, double price, double priceC, double priceCP, String name, String owner,
            String timeStamp) {
        Element stock = document.createElement("stock");
        bank.appendChild(stock);

        Attr attr = document.createAttribute("stockId");
        attr.setValue(stockId);
        stock.setAttributeNode(attr);

        Attr attr1 = document.createAttribute("price");
        attr1.setValue(String.valueOf(price));
        stock.setAttributeNode(attr1);

        Attr attr2 = document.createAttribute("priceC");
        attr2.setValue(String.valueOf(priceC));
        stock.setAttributeNode(attr2);

        Attr attr3 = document.createAttribute("priceCP");
        attr3.setValue(String.valueOf(priceCP));
        stock.setAttributeNode(attr3);

        Attr attr4 = document.createAttribute("name");
        attr4.setValue(name);
        stock.setAttributeNode(attr4);

        Attr attr5 = document.createAttribute("owner");
        attr5.setValue(owner);
        stock.setAttributeNode(attr5);
        setFile();

        Attr attr6 = document.createAttribute("timestamp");
        attr6.setValue(timeStamp);
        stock.setAttributeNode(attr6);
        setFile();

    }

    // Function to get the stocks
    public Stock getStock(String stockId) {
        NodeList nodeList = document.getElementsByTagName("stock");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisStock = nodeList.item(i);
            if (thisStock.getAttributes().getNamedItem("stockId").getNodeValue().equalsIgnoreCase(stockId)) {
                Stock stock = new Stock();
                stock.setStockId(stockId);
                stock.setPrice(Double.parseDouble(thisStock.getAttributes().getNamedItem("price").getNodeValue()));
                stock.setPriceC(Double.parseDouble(thisStock.getAttributes().getNamedItem("priceC").getNodeValue()));
                stock.setPriceCP(Double.parseDouble(thisStock.getAttributes().getNamedItem("priceCP").getNodeValue()));
                stock.setName(thisStock.getAttributes().getNamedItem("name").getNodeValue());
                stock.setOwner(thisStock.getAttributes().getNamedItem("owner").getNodeValue());
                return stock;
            }
        }
        return null;
    }

    // function to get arraylist of all stocks
    public ArrayList<String> getAllStocks() {
        ArrayList<String> stocks = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("stock");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisStock = nodeList.item(i);
            stocks.add(thisStock.getAttributes().getNamedItem("stockId").getNodeValue());
        }
        return stocks;

    }

    // function to return stocks of particular user
    public ArrayList<String> getUserStocks(String userId){
        ArrayList<String> stocks = getAllStocks();
        ArrayList<String> main = new ArrayList<>();
        if(stocks.size() > 0){
            for(int i =0; i < stocks.size(); i++){
                Stock s = getStock(stocks.get(i));
                if(s.getOwner().equals(userId)){
                    main.add(s.getStockSymbol());
                }
            }
        }
        return main;
    }

    // Function to get stock price
    public Double getStockPrice(String stockId) {
        NodeList nodeList = document.getElementsByTagName("stock");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("stockId").getNodeValue().equalsIgnoreCase(stockId)) {
                return Double.valueOf(thisAcc.getAttributes().getNamedItem("price").getNodeValue());
            }
        }
        return null;
    }

    // Function to set stock price
    public void setStockPrice(String stockId, Double price) {
        NodeList nodeList = document.getElementsByTagName("stock");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisAcc = nodeList.item(i);
            if (thisAcc.getAttributes().getNamedItem("stockId").getNodeValue().equalsIgnoreCase(stockId)) {
                ((Attr) thisAcc.getAttributes().getNamedItem("price")).setValue(String.valueOf(price));
            }
        }
        setFile();
    }

    // Function to remove a stock
    public boolean removeStock(String stockId) {
        NodeList nodeList = document.getElementsByTagName("stock");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisStock = nodeList.item(i);
            if (thisStock.getAttributes().getNamedItem("stockId").getNodeValue().equalsIgnoreCase(stockId)) {
                thisStock.getParentNode().removeChild(thisStock);
            }
        }
        return true;
    }

    // Function to add trades
    public void addTrade(String tradeId, String accId, String stockId, String owner, String action, int price,
            Timestamp time, int bal) {
        Element trade = document.createElement("trade");
        bank.appendChild(trade);

        Attr attr = document.createAttribute("tradeId");
        attr.setValue(tradeId);
        trade.setAttributeNode(attr);

        Attr attr1 = document.createAttribute("accId");
        attr1.setValue(accId);
        trade.setAttributeNode(attr1);

        Attr attr2 = document.createAttribute("stockId");
        attr2.setValue(stockId);
        trade.setAttributeNode(attr2);

        Attr attr3 = document.createAttribute("owner");
        attr3.setValue(owner);
        trade.setAttributeNode(attr3);

        Attr attr4 = document.createAttribute("action");
        attr4.setValue(action);
        trade.setAttributeNode(attr4);

        Attr attr5 = document.createAttribute("price");
        attr5.setValue(String.valueOf(price));
        trade.setAttributeNode(attr5);

        Attr attr6 = document.createAttribute("time");
        attr6.setValue(String.valueOf(time));
        trade.setAttributeNode(attr6);

        Attr attr7 = document.createAttribute("bal");
        attr7.setValue(String.valueOf(bal));
        trade.setAttributeNode(attr7);
        setFile();
    }

    // Function to get the Trade
    public Trade getTrade(String tradeId) {
        NodeList nodeList = document.getElementsByTagName("trade");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisTrade = nodeList.item(i);
            if (thisTrade.getAttributes().getNamedItem("tradeId").getNodeValue().equalsIgnoreCase(tradeId)) {
                Trade trade = new Trade(tradeId);
                trade.setAccId(thisTrade.getAttributes().getNamedItem("accId").getNodeValue());
                trade.setStockId(thisTrade.getAttributes().getNamedItem("stockId").getNodeValue());
                trade.setCustomerId(thisTrade.getAttributes().getNamedItem("owner").getNodeValue());
                trade.setAction(thisTrade.getAttributes().getNamedItem("action").getNodeValue());
                trade.setProfit((Double.parseDouble(thisTrade.getAttributes().getNamedItem("profit").getNodeValue())));
                trade.setBuyPrice(
                        (Double.parseDouble(thisTrade.getAttributes().getNamedItem("buyPrice").getNodeValue())));
                trade.setSellPrice(
                        (Double.parseDouble(thisTrade.getAttributes().getNamedItem("sellPrice").getNodeValue())));
                trade.setTime(Timestamp.valueOf(thisTrade.getAttributes().getNamedItem("time").getNodeValue()));
                return trade;
            }
        }
        return null;
    }

    // Function to remove a Trade
    public void removeTrade(String tradeId) {
        NodeList nodeList = document.getElementsByTagName("trade");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisTrade = nodeList.item(i);
            if (thisTrade.getAttributes().getNamedItem("tradeId").getNodeValue().equalsIgnoreCase(tradeId)) {
                thisTrade.getParentNode().removeChild(thisTrade);
            }
        }
    }

    // Function to add transactions
    public void addTransaction(String transId, String sId, String rId, Double amt, Currency currency, Timestamp time,
            Double fee) {
        Element trans = document.createElement("trans");
        bank.appendChild(trans);

        Attr attr = document.createAttribute("transId");
        attr.setValue(transId);
        trans.setAttributeNode(attr);

        Attr attr1 = document.createAttribute("sId");
        attr1.setValue(sId);
        trans.setAttributeNode(attr1);

        Attr attr2 = document.createAttribute("rId");
        attr2.setValue(rId);
        trans.setAttributeNode(attr2);

        Attr attr3 = document.createAttribute("amt");
        attr3.setValue(String.valueOf(amt));
        trans.setAttributeNode(attr3);

        Attr attr4 = document.createAttribute("currency");
        attr4.setValue(String.valueOf(currency));
        trans.setAttributeNode(attr4);

        Attr attr5 = document.createAttribute("time");
        attr5.setValue(String.valueOf(time));
        trans.setAttributeNode(attr5);

        Attr attr6 = document.createAttribute("fee");
        attr6.setValue(String.valueOf(fee));
        trans.setAttributeNode(attr6);
        setFile();
    }

    // Function to get the Transaction
    public Transaction getTrans(String transId) {
        NodeList nodeList = document.getElementsByTagName("trans");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisTrans = nodeList.item(i);
            if (thisTrans.getAttributes().getNamedItem("transId").getNodeValue().equalsIgnoreCase(transId)) {
                String sId = thisTrans.getAttributes().getNamedItem("sId").getNodeValue();
                String rId = thisTrans.getAttributes().getNamedItem("rId").getNodeValue();
                Double amt = Double.valueOf(thisTrans.getAttributes().getNamedItem("amt").getNodeValue());
                Currency curr = Currency.valueOf(thisTrans.getAttributes().getNamedItem("currency").getNodeValue());
                Timestamp time = Timestamp.valueOf(thisTrans.getAttributes().getNamedItem("time").getNodeValue());
                Double fee = Double.valueOf(thisTrans.getAttributes().getNamedItem("fee").getNodeValue());
                Transaction trans = new Transaction(transId, sId, rId, amt, curr, time, fee);
                return trans;
            }
        }
        return null;
    }

    // Get today's transaction from the Data Base
    public ArrayList<Transaction> getTodayTransaction() {
        ArrayList<Transaction> allTrans = getAllTrans();
        ArrayList<Transaction> todayTrans = new ArrayList<>();
        this.now = new java.util.Date();
        this.time = new java.sql.Timestamp(now.getTime());
        String today = String.valueOf(time).split(" ")[0];
        for (int i = 0; i < allTrans.size(); i++) {
            String transDate = String.valueOf(allTrans.get(i).getTime()).split(" ")[0];
            if (transDate.equalsIgnoreCase(today)) {
                todayTrans.add(allTrans.get(i));
            }
        }
        return todayTrans;
    }

    // Function to get the Transaction
    public ArrayList<Transaction> getAllTrans() {
        try {
            ArrayList<Transaction> allTrans = new ArrayList<>();
            NodeList nodeList = document.getElementsByTagName("trans");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node thisTrans = nodeList.item(i);
                String transId = thisTrans.getAttributes().getNamedItem("transId").getNodeValue();
                String sId = thisTrans.getAttributes().getNamedItem("sId").getNodeValue();
                String rId = thisTrans.getAttributes().getNamedItem("rId").getNodeValue();
                Double amt = Double.valueOf(thisTrans.getAttributes().getNamedItem("amt").getNodeValue());
                Currency curr = Currency.valueOf(thisTrans.getAttributes().getNamedItem("currency").getNodeValue());
                Timestamp time = Timestamp.valueOf(thisTrans.getAttributes().getNamedItem("time").getNodeValue());
                Double fee = Double.valueOf(thisTrans.getAttributes().getNamedItem("fee").getNodeValue());
                Transaction trans = new Transaction(transId, sId, rId, amt, curr, time, fee);
                allTrans.add(trans);
            }
            return allTrans;
        } catch (Exception e) {
            return null;
        }
    }

    // Function to get the Transaction
    public ArrayList<Transaction> getUserTrans(String userId) {
        try {
            ArrayList<Transaction> allTrans = new ArrayList<>();
            NodeList nodeList = document.getElementsByTagName("trans");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node thisTrans = nodeList.item(i);
                if (thisTrans.getAttributes().getNamedItem("sId").getNodeValue().equalsIgnoreCase(userId)) {
                    String transId = thisTrans.getAttributes().getNamedItem("transId").getNodeValue();
                    String sId = thisTrans.getAttributes().getNamedItem("sId").getNodeValue();
                    String rId = thisTrans.getAttributes().getNamedItem("rId").getNodeValue();
                    Double amt = Double.valueOf(thisTrans.getAttributes().getNamedItem("amt").getNodeValue());
                    Currency curr = Currency.valueOf(thisTrans.getAttributes().getNamedItem("currency").getNodeValue());
                    Timestamp time = Timestamp.valueOf(thisTrans.getAttributes().getNamedItem("time").getNodeValue());
                    Double fee = Double.valueOf(thisTrans.getAttributes().getNamedItem("fee").getNodeValue());
                    Transaction trans = new Transaction(transId, sId, rId, amt, curr, time, fee);
                    allTrans.add(trans);
                }
            }
            return allTrans;
        } catch (Exception e) {
            return null;
        }
    }

    // Function to remove a Transaction
    public void removeTrans(String transId) {
        NodeList nodeList = document.getElementsByTagName("trans");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node thisTrans = nodeList.item(i);
            if (thisTrans.getAttributes().getNamedItem("transId").getNodeValue().equalsIgnoreCase(transId)) {
                thisTrans.getParentNode().removeChild(thisTrans);
            }
        }
    }

}