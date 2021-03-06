package com.sad.sadcrm.form;

import com.sad.sadcrm.*;
import com.sad.sadcrm.hibernate.AddressDAO;
import com.sad.sadcrm.hibernate.ClientDAO;
import com.sad.sadcrm.hibernate.UserDAO;
import com.sad.sadcrm.hibernate.exception.ClientInsertException;
import com.sad.sadcrm.hibernate.exception.ClientUpdateException;
import com.sad.sadcrm.hibernate.exception.UserInsertException;
import com.sad.sadcrm.hibernate.exception.UserUpdateException;
import com.sad.sadcrm.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.sad.sadcrm.Application.VERSION;
import static java.awt.Color.lightGray;
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import static java.lang.Integer.valueOf;
import static java.lang.String.format;
import static javax.swing.BorderFactory.createEtchedBorder;
import static javax.swing.BorderFactory.createLineBorder;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import static javax.swing.JOptionPane.*;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;
import static javax.swing.LayoutStyle.ComponentPlacement.UNRELATED;
import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static javax.swing.SwingConstants.HORIZONTAL;

public class SadCRMForm extends ApplicationWindow {
    private User loggedUser = null;

    private Client selectedClient = null;
    private User selectedUser = null;
    private User newUser = null;

    private boolean myContacts = false;
    private boolean mail = false;
    private boolean isReport = false;

    private JPopupMenu exportAdminDataMenu = new JPopupMenu();
    private JMenuItem exportAdminDataSubmenu1 = new JMenuItem("Eksport wszystkich klientów");
    private JMenuItem exportAdminDataSubmenu2 = new JMenuItem("Eksport klientów dodanych dziś");

    private JPopupMenu exportUserDataMenu = new JPopupMenu();
    private JMenuItem exportUserDataSubmenu1 = new JMenuItem("Eksport moich klientów");

    public SadCRMForm() {
        super(VERSION);
        initComponents();
        PanelsUtil.enablePanel(loginPanel, new JPanel[]{userPanel, adminPanel, managerPanel});

        createPopupMenuForExport();
    }

    private void createPopupMenuForExport() {
        exportAdminDataMenu.add(exportAdminDataSubmenu1);
        exportAdminDataMenu.add(exportAdminDataSubmenu2);

        exportUserDataMenu.add(exportUserDataSubmenu1);

        exportAdminDataSubmenu1.addActionListener(event -> ReportsUtil.exportAll());
        exportAdminDataSubmenu2.addActionListener(event -> ReportsUtil.exportTodays(now()));
        dataExpButton1.addActionListener(this::showPopupAdmin);
        dataExpButton2.addActionListener(this::showPopupAdmin);

        exportUserDataSubmenu1.addActionListener(event -> ReportsUtil.exportMy(loggedUser));

        exportUserDataButton.addActionListener(this::showPopupUser);
    }

    private void showPopupAdmin(ActionEvent actionEvent) {
        Component source = (Component) actionEvent.getSource();
        Point point = source.getLocationOnScreen();
        exportAdminDataMenu.show(this, 0, 0);
        exportAdminDataMenu.setLocation(point.x, point.y + source.getHeight());
    }

    private void showPopupUser(ActionEvent actionEvent) {
        Component component = (Component) actionEvent.getSource();
        Point point = component.getLocationOnScreen();
        exportUserDataMenu.show(this, 0, 0);
        exportUserDataMenu.setLocation(point.x, point.y + component.getHeight());
    }

    private void initComponents() {
        changePasswordButton.setIcon(icon("/icons/Apply.gif"));
        changePasswordButton.setText("OK");
        changePasswordButton.addActionListener(this::changePasswordButtonActionPerformed);

        cancelChangePasswordButton.setIcon(icon("/icons/Cancel.gif"));
        cancelChangePasswordButton.setText("Anuluj");
        cancelChangePasswordButton.addActionListener(this::cancelChangePasswordButtonActionPerformed);

        jLabel46.setText("Hasło:");
        jLabel47.setText("Powtórz hasło");

        GroupLayout jDialog1Layout = new GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
                jDialog1Layout.createParallelGroup(LEADING)
                        .addGroup(jDialog1Layout.createSequentialGroup()
                                .addGroup(jDialog1Layout.createParallelGroup(LEADING)
                                        .addGroup(jDialog1Layout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addComponent(changePasswordButton, PREFERRED_SIZE, 152, PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cancelChangePasswordButton, PREFERRED_SIZE, 150, PREFERRED_SIZE))
                                        .addGroup(jDialog1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jDialog1Layout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel46)
                                                        .addComponent(jLabel47))
                                                .addPreferredGap(RELATED)
                                                .addGroup(jDialog1Layout.createParallelGroup(LEADING, false)
                                                        .addComponent(txtChangePass1)
                                                        .addComponent(txtChangePass2, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))))
                                .addContainerGap(42, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
                jDialog1Layout.createParallelGroup(LEADING)
                        .addGroup(TRAILING, jDialog1Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(jDialog1Layout.createParallelGroup(TRAILING)
                                        .addComponent(jLabel46)
                                        .addComponent(txtChangePass1, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(jDialog1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel47)
                                        .addComponent(txtChangePass2, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jDialog1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(changePasswordButton)
                                        .addComponent(cancelChangePasswordButton))
                                .addContainerGap(43, Short.MAX_VALUE))
        );

        jDialog2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jDialog2.setMinimumSize(new Dimension(400, 400));
        jDialog2.setResizable(true);

        jLabel54.setText("Wybierz pracownika");

        usersForManagerTable.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        usersForManagerTable.setMinimumSize(new Dimension(376, 72));
        usersForManagerTable.setPreferredSize(new Dimension(376, 72));
        usersForManagerTable.setSelectionMode(SINGLE_SELECTION);
        usersForManagerTable.addMouseListener((MouseClick) this::usersForManagerTableMouseClicked);
        jScrollPane3.setViewportView(usersForManagerTable);

        jButton2.setIcon(icon("/icons/Apply.gif"));
        jButton2.setText("Wybierz");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setIcon(icon("/icons/Cancel.gif"));
        jButton3.setText("Zamknij");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        GroupLayout jDialog2Layout = new GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
                jDialog2Layout.createParallelGroup(LEADING)
                        .addGroup(jDialog2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jDialog2Layout.createParallelGroup(LEADING)
                                        .addComponent(jLabel54)
                                        .addGroup(jDialog2Layout.createSequentialGroup()
                                                .addComponent(jButton2)
                                                .addPreferredGap(RELATED)
                                                .addComponent(jButton3))
                                        .addComponent(jScrollPane3, PREFERRED_SIZE, 372, PREFERRED_SIZE))
                                .addGap(42, 42, 42))
        );
        jDialog2Layout.setVerticalGroup(
                jDialog2Layout.createParallelGroup(LEADING)
                        .addGroup(jDialog2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel54)
                                .addPreferredGap(RELATED)
                                .addComponent(jScrollPane3, PREFERRED_SIZE, 289, PREFERRED_SIZE)
                                .addPreferredGap(RELATED, 53, Short.MAX_VALUE)
                                .addGroup(jDialog2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))
                                .addContainerGap())
        );

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        userPanel.setBackground(lightGray);
        userPanel.setBorder(createLineBorder(new Color(0, 0, 0)));
        userPanel.setPreferredSize(new Dimension(1000, 800));

        topPanel.setBackground(lightGray);
        topPanel.setBorder(createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        topPanel.setForeground(new Color(238, 9, 9));
        topPanel.setPreferredSize(new Dimension(1000, 50));

        mailButton.setIcon(icon("/icons/E-mail.gif"));
        mailButton.setText("Wyślij wiadomość");
        mailButton.addActionListener(this::wyslijMailAction);

        exportUserDataButton.setIcon(icon("/icons/Upload.gif"));
        exportUserDataButton.setText("Eksport danych");

        GroupLayout topPanelLayout = new GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
                topPanelLayout.createParallelGroup(LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addGap(196, 196, 196)
                                .addComponent(mailButton)
                                .addGap(18, 18, 18)
                                .addComponent(exportUserDataButton)
                                .addContainerGap(478, Short.MAX_VALUE))
        );
        topPanelLayout.setVerticalGroup(
                topPanelLayout.createParallelGroup(LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(topPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(mailButton)
                                        .addComponent(exportUserDataButton))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        leftPanel.setBackground(lightGray);
        leftPanel.setBorder(createEtchedBorder());
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(200, 750));

        addClientButton.setIcon(icon("/icons/Create.gif"));
        addClientButton.setText("Dodaj klienta");
        addClientButton.addActionListener(this::dodajKlientaAction);

        searchButton.setIcon(icon("/icons/Find.gif"));
        searchButton.setText("Wyszukiwanie");
        searchButton.addActionListener(this::wyszukiwanieAction);

        mailMergeButton.setIcon(icon("/icons/Mail.gif"));
        mailMergeButton.setText("Korespondencja seryjna");
        mailMergeButton.addActionListener(this::korespondencjaSeryjnaAction);

        myContactsButton.setIcon(icon("/icons/Address book.gif"));
        myContactsButton.setText("Tylko moje kontakty");
        myContactsButton.addActionListener(this::mojeKontaktyAction);

        String iconName = "/icons/Info.gif";
        myPanelButton.setIcon(icon(iconName));
        myPanelButton.setText("Mój panel");
        myPanelButton.addActionListener(this::mojPanelAction);

        logoutButton.setIcon(icon("/icons/Exit.gif"));
        logoutButton.setText("Wyloguj");
        logoutButton.addActionListener(event -> logoutMessageBox());

        exitButton.setIcon(icon("/icons/Turn off.gif"));
        exitButton.setText("Wyjście");
        exitButton.addActionListener(event -> exitCommonAction());

        GroupLayout leftPanelLayout = new GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
                leftPanelLayout.createParallelGroup(LEADING)
                        .addComponent(addClientButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mailMergeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myContactsButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myPanelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
                leftPanelLayout.createParallelGroup(LEADING)
                        .addGroup(leftPanelLayout.createSequentialGroup()
                                .addComponent(addClientButton)
                                .addPreferredGap(RELATED)
                                .addComponent(searchButton)
                                .addPreferredGap(RELATED)
                                .addComponent(mailMergeButton)
                                .addPreferredGap(RELATED)
                                .addComponent(myContactsButton)
                                .addPreferredGap(RELATED)
                                .addComponent(myPanelButton)
                                .addPreferredGap(RELATED)
                                .addComponent(logoutButton)
                                .addPreferredGap(RELATED)
                                .addComponent(exitButton)
                                .addGap(0, 500, Short.MAX_VALUE))
        );

        mainUserPanel.setBorder(createLineBorder(new Color(0, 0, 0)));
        mainUserPanel.setPreferredSize(new Dimension(800, 750));

        Font timesNewRomanBold24 = new Font("Times New Roman,", BOLD, 24);
        Font timesNewRoman20 = new Font("Times New Roman,", PLAIN, 20);
        Font verdanaBold24 = new Font("Verdana", BOLD, 24);

        jLabel3.setFont(timesNewRomanBold24);
        jLabel3.setText("Zalogowany uzytkownik:");

        jLabel25.setFont(timesNewRoman20);
        jLabel25.setText("Imię:");

        jLabel26.setFont(timesNewRoman20);
        jLabel26.setText("Nazwisko:");

        jLabel27.setFont(timesNewRoman20);
        jLabel27.setText("Typ konta:");

        jLabel28.setFont(timesNewRoman20);
        jLabel28.setText("Login:");

        txtUserName.setEditable(false);
        txtUserSurname.setEditable(false);
        txtUserLogin.setEditable(false);
        txtUserType.setEditable(false);

        jButton1.setIcon(icon("/icons/Repair.gif"));
        jButton1.setText("Zmień hasło");
        jButton1.addActionListener(event -> commonChangePassword());

        GroupLayout mainUserPanelLayout = new GroupLayout(mainUserPanel);
        mainUserPanel.setLayout(mainUserPanelLayout);
        mainUserPanelLayout.setHorizontalGroup(
                mainUserPanelLayout.createParallelGroup(LEADING)
                        .addGroup(mainUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainUserPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(mainUserPanelLayout.createSequentialGroup()
                                                .addGroup(mainUserPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel25)
                                                        .addComponent(jLabel26)
                                                        .addComponent(jLabel27)
                                                        .addComponent(jLabel28))
                                                .addGap(52, 52, 52)
                                                .addGroup(mainUserPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(txtUserLogin, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(txtUserSurname, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(txtUserName, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(txtUserType, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(jButton1)))
                                        .addComponent(jLabel3))
                                .addContainerGap(293, Short.MAX_VALUE))
        );
        mainUserPanelLayout.setVerticalGroup(
                mainUserPanelLayout.createParallelGroup(LEADING)
                        .addGroup(mainUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addGap(30, 30, 30)
                                .addGroup(mainUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel25)
                                        .addComponent(txtUserName, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel26)
                                        .addComponent(txtUserSurname, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel27)
                                        .addComponent(txtUserType, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel28)
                                        .addComponent(txtUserLogin, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addContainerGap(499, Short.MAX_VALUE))
        );

        addClientPanel.setBorder(null);
        addClientPanel.setPreferredSize(new Dimension(800, 750));

        jLabel2.setFont(timesNewRomanBold24);
        jLabel2.setText("Dodanie nowego klienta");

        nameLabel.setFont(timesNewRoman20);
        nameLabel.setText("Imię*:");

        surnameLabel.setFont(timesNewRoman20);
        surnameLabel.setText("Nazwisko*:");

        peselLabel.setFont(timesNewRoman20);
        peselLabel.setText("Pesel*");

        phone1Label.setFont(timesNewRoman20);
        phone1Label.setText("Nr telefonu 1");

        phone2Label.setFont(timesNewRoman20);
        phone2Label.setText("Nr telefonu 2");

        clientVipLabel.setFont(timesNewRoman20);
        clientVipLabel.setText("Klient VIP");

        mailLabel.setFont(timesNewRoman20);
        mailLabel.setText("Adres mail");

        additionalsLabel.setFont(timesNewRoman20);
        additionalsLabel.setText("Uwagi");

        ownerLabel.setFont(timesNewRoman20);
        ownerLabel.setText("Utworzony przez");

        createDateLabel.setFont(timesNewRoman20);
        createDateLabel.setText("Data utworzenia");

        streetLabel.setFont(timesNewRoman20);
        streetLabel.setText("Ulica*");

        buildingNoLabel.setFont(timesNewRoman20);
        buildingNoLabel.setText("Nr budynku*");

        cityLabel.setFont(timesNewRoman20);
        cityLabel.setText("Miasto*");

        postalLabel.setFont(timesNewRoman20);
        postalLabel.setText("Kod pocztowy*");

        productsLabel.setFont(timesNewRoman20);
        productsLabel.setText("Produkty");

        txtClientDesc.setColumns(20);
        txtClientDesc.setRows(5);
        jScrollPane1.setViewportView(txtClientDesc);

        txtClientCreator.setEnabled(false);

        txtClientCreateDate.setEnabled(false);

        saveClientButton.setIcon(icon("/icons/Save.gif"));
        saveClientButton.setText("Zapisz");
        saveClientButton.addActionListener(this::zapiszKlientaAction);

        cancelClientButton.setIcon(icon("/icons/Cancel.gif"));
        cancelClientButton.setText("Anuluj");
        cancelClientButton.addActionListener(this::anulujZapisKlientaAction);

        cbPersonalAcc.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbPersonalAcc.setText("Konto osobiste");

        cbCurrencyAcc.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbCurrencyAcc.setText("Konto walutowe");

        cbLocate.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbLocate.setText("Lokata");

        cbCurrentCredit.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbCurrentCredit.setText("Kredyt gotówkowy");

        cbHomeCredit.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbHomeCredit.setText("Kredyt hipoteczny");

        cbRepeatedCredit.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbRepeatedCredit.setText("Kredyt odnawialny");

        cbCreditCard.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbCreditCard.setText("Karta kredytowa");

        cbChance.setModel(new DefaultComboBoxModel<>(new String[]{"Wybierz", "Konto osobiste", "Konto walutowe", "Lokata", "Kredyt gotówkowy", "Kredyt hipoteczny", "Kredyt odnawialny", "Karta kredytowa"}));

        sellChanceLabel.setFont(timesNewRoman20);
        sellChanceLabel.setText("Szansa sprzedaży");

        editClientButton.setIcon(icon("/icons/Repair.gif"));
        editClientButton.setText("Edytuj");
        editClientButton.addActionListener(this::edytujKlientaAction);

        try {
            txtClientPostalCode.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##-###")));
        } catch (ParseException exception) {
            throw new RuntimeException(exception);
        }

        labelClientModDate.setFont(timesNewRoman20);
        labelClientModDate.setText("Data modyfikacji");

        txtClientModification.setEditable(false);
        txtClientModification.setEnabled(false);

        clientPhoneLabel.setFont(timesNewRoman20);
        clientPhoneLabel.setText("Telefon do klienta");

        phoneDateLabel.setFont(timesNewRoman20);
        phoneDateLabel.setText("Data telefonu");

        txtClientTel.setEnabled(false);
        txtClientTel.addActionListener(this::txtClientTelActionPerformed);

        txtClientTelDate.setEnabled(false);

        GroupLayout addClientPanelLayout = new GroupLayout(addClientPanel);
        addClientPanel.setLayout(addClientPanelLayout);
        addClientPanelLayout.setHorizontalGroup(
                addClientPanelLayout.createParallelGroup(LEADING)
                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(createDateLabel)
                                                        .addComponent(clientVipLabel)
                                                        .addComponent(ownerLabel)
                                                        .addComponent(labelClientModDate))
                                                .addGap(6, 6, 6)
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(txtClientCreateDate)
                                                        .addComponent(txtClientVip)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addComponent(saveClientButton, PREFERRED_SIZE, 125, PREFERRED_SIZE)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cancelClientButton, PREFERRED_SIZE, 118, PREFERRED_SIZE)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(editClientButton, PREFERRED_SIZE, 112, PREFERRED_SIZE))
                                                        .addGroup(addClientPanelLayout.createParallelGroup(TRAILING, false)
                                                                .addComponent(txtClientModification, LEADING)
                                                                .addComponent(txtClientCreator, LEADING, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))))
                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(peselLabel)
                                                        .addComponent(surnameLabel)
                                                        .addComponent(phone2Label)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addComponent(phone1Label)
                                                                .addGap(35, 35, 35)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(txtClientPhone1, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                                                        .addComponent(txtClientName)
                                                                        .addComponent(txtClientSurname)
                                                                        .addComponent(txtClientPesel)
                                                                        .addComponent(txtClientPhone2, PREFERRED_SIZE, 134, PREFERRED_SIZE)))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(nameLabel)
                                                                        .addComponent(mailLabel)
                                                                        .addComponent(additionalsLabel))
                                                                .addGap(59, 59, 59)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jScrollPane1, PREFERRED_SIZE, 232, PREFERRED_SIZE)
                                                                        .addComponent(txtClientMail, PREFERRED_SIZE, 232, PREFERRED_SIZE))))
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(cbCurrencyAcc)
                                                                        .addComponent(cbPersonalAcc)
                                                                        .addComponent(cbLocate)
                                                                        .addComponent(cbCurrentCredit)
                                                                        .addComponent(cbHomeCredit)
                                                                        .addComponent(cbRepeatedCredit)
                                                                        .addComponent(cbCreditCard)
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(phoneDateLabel)
                                                                                .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(txtClientTelDate, PREFERRED_SIZE, 145, PREFERRED_SIZE))
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(sellChanceLabel)
                                                                                .addPreferredGap(RELATED)
                                                                                .addComponent(cbChance, PREFERRED_SIZE, 139, PREFERRED_SIZE))
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(clientPhoneLabel)
                                                                                .addPreferredGap(RELATED)
                                                                                .addComponent(txtClientTel))))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(14, 14, 14)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(postalLabel)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(txtClientPostalCode, PREFERRED_SIZE, 165, PREFERRED_SIZE))
                                                                        .addGroup(addClientPanelLayout.createParallelGroup(TRAILING, false)
                                                                                .addGroup(LEADING, addClientPanelLayout.createSequentialGroup()
                                                                                        .addComponent(streetLabel)
                                                                                        .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(txtClientStreet, PREFERRED_SIZE, 165, PREFERRED_SIZE))
                                                                                .addGroup(LEADING, addClientPanelLayout.createSequentialGroup()
                                                                                        .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                                                .addComponent(buildingNoLabel)
                                                                                                .addComponent(cityLabel)
                                                                                                .addComponent(productsLabel))
                                                                                        .addGap(41, 41, 41)
                                                                                        .addGroup(addClientPanelLayout.createParallelGroup(LEADING, false)
                                                                                                .addComponent(txtClientCity, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                                                                                .addComponent(txtClientNumber, TRAILING))))))))
                                        .addComponent(jLabel2))
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        addClientPanelLayout.linkSize(HORIZONTAL, txtClientCreateDate, txtClientName, txtClientPesel, txtClientPhone1, txtClientPhone2, txtClientSurname);

        addClientPanelLayout.linkSize(HORIZONTAL, txtClientCity, txtClientNumber, txtClientPostalCode);

        addClientPanelLayout.setVerticalGroup(
                addClientPanelLayout.createParallelGroup(LEADING)
                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addGroup(addClientPanelLayout.createParallelGroup(TRAILING)
                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtClientVip)
                                                .addGap(7, 7, 7)
                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(createDateLabel)
                                                        .addComponent(txtClientCreateDate, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(txtClientTelDate, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(phoneDateLabel))
                                                .addPreferredGap(RELATED)
                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(ownerLabel)
                                                        .addComponent(txtClientCreator, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addPreferredGap(RELATED)
                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelClientModDate)
                                                        .addComponent(txtClientModification, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addGap(19, 19, 19))
                                        .addGroup(LEADING, addClientPanelLayout.createSequentialGroup()
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(2, 2, 2)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(streetLabel)
                                                                        .addComponent(txtClientStreet, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(buildingNoLabel)
                                                                        .addComponent(txtClientNumber, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(cityLabel)
                                                                        .addComponent(txtClientCity, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(postalLabel)
                                                                        .addComponent(txtClientPostalCode, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(productsLabel)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbPersonalAcc)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbCurrencyAcc)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbLocate)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbCurrentCredit)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbHomeCredit)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbRepeatedCredit)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbCreditCard)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(sellChanceLabel)
                                                                        .addComponent(cbChance, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(nameLabel)
                                                                        .addComponent(txtClientName, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(surnameLabel)
                                                                        .addComponent(txtClientSurname, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(peselLabel)
                                                                        .addComponent(txtClientPesel, TRAILING, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(txtClientPhone1, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(phone1Label))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(phone2Label)
                                                                        .addComponent(txtClientPhone2, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(txtClientMail, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(mailLabel))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(additionalsLabel)
                                                                        .addComponent(jScrollPane1, PREFERRED_SIZE, 197, PREFERRED_SIZE))))
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(11, 11, 11)
                                                                .addComponent(clientVipLabel))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(TRAILING)
                                                                        .addComponent(txtClientTel)
                                                                        .addComponent(clientPhoneLabel))))
                                                .addPreferredGap(RELATED, 125, Short.MAX_VALUE)))
                                .addGroup(addClientPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveClientButton)
                                        .addComponent(cancelClientButton)
                                        .addComponent(editClientButton))
                                .addContainerGap(87, Short.MAX_VALUE))
        );

        searchPanel.setPreferredSize(new Dimension(800, 750));

        jLabel1.setFont(timesNewRomanBold24);
        jLabel1.setText("Wyszukiwanie klienta");

        jLabel29.setFont(timesNewRoman20);
        jLabel29.setText("Nazwisko:");

        jLabel30.setFont(timesNewRoman20);
        jLabel30.setText("Pesel:");

        searchSearchButton.setIcon(icon("/icons/Search.gif"));
        searchSearchButton.setText("Szukaj");
        searchSearchButton.addActionListener(this::szukajKlientaAction);

        resetSearchButton.setIcon(icon("/icons/Refresh.gif"));
        resetSearchButton.setText("Reset");
        resetSearchButton.addActionListener(this::resetPolSzukaniaKlientaAction);

        tableClients.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        tableClients.setToolTipText("Kliknij dwukrotnie aby edytować");
        tableClients.setAutoResizeMode(AUTO_RESIZE_OFF);
        tableClients.setPreferredSize(new Dimension(780, 100));
        tableClients.setSelectionMode(SINGLE_SELECTION);
        tableClients.getTableHeader().setResizingAllowed(false);
        tableClients.getTableHeader().setReorderingAllowed(false);
        tableClients.addMouseListener((MouseClick) this::tableClientsMouseClicked);
        jScrollPane7.setViewportView(tableClients);

        txtDetailsClientButton.setIcon(icon("/icons/About.gif"));
        txtDetailsClientButton.setText("Szczegóły");
        txtDetailsClientButton.addActionListener(this::szczegolyKlientaAction);

        txtSendMultipleMail.setIcon(icon("/icons/E-mail.gif"));
        txtSendMultipleMail.setText("Wyślij wiadomość do zanaczonych kontaktów");
        txtSendMultipleMail.addActionListener(this::wyslijMailDoWszystkichAction);

        GroupLayout searchPanelLayout = new GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
                searchPanelLayout.createParallelGroup(LEADING)
                        .addGroup(searchPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(searchPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(jScrollPane7, GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(searchPanelLayout.createSequentialGroup()
                                                .addGroup(searchPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel1)
                                                        .addGroup(searchPanelLayout.createSequentialGroup()
                                                                .addGroup(searchPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel29)
                                                                        .addComponent(jLabel30))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(searchPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(txtSearchSurname, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                                        .addComponent(txtSearchPesel))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(searchPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(searchSearchButton, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                                        .addComponent(resetSearchButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addGroup(searchPanelLayout.createSequentialGroup()
                                                                .addComponent(txtDetailsClientButton)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(txtSendMultipleMail)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        searchPanelLayout.setVerticalGroup(
                searchPanelLayout.createParallelGroup(LEADING)
                        .addGroup(searchPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(30, 30, 30)
                                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel29)
                                        .addComponent(txtSearchSurname, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(searchSearchButton))
                                .addGap(4, 4, 4)
                                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel30)
                                        .addComponent(txtSearchPesel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(resetSearchButton))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane7, PREFERRED_SIZE, 535, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDetailsClientButton)
                                        .addComponent(txtSendMultipleMail))
                                .addContainerGap(26, Short.MAX_VALUE))
        );

        sendMailPanel.setPreferredSize(new Dimension(800, 750));

        jLabel32.setFont(verdanaBold24);
        jLabel32.setText("Wyślij wiadomość");

        jLabel34.setFont(timesNewRoman20);
        jLabel34.setText("Odbiorcy:");

        txtRecipient.setColumns(20);
        txtRecipient.setRows(5);
        jScrollPane5.setViewportView(txtRecipient);

        jLabel35.setFont(timesNewRoman20);
        jLabel35.setText("Wiadomość:");

        txtMailContent.setColumns(20);
        txtMailContent.setRows(5);
        jScrollPane6.setViewportView(txtMailContent);

        sendOneMailButton.setIcon(icon("/icons/Mail.gif"));
        sendOneMailButton.setText("Wyślij");
        sendOneMailButton.addActionListener(this::wyslijjednegoMailaAction);

        clearMailButton.setIcon(icon("/icons/Refresh.gif"));
        clearMailButton.setText("Wyczyść");
        clearMailButton.addActionListener(this::wyczyscPolaMailaAction);

        sendMailButton.setIcon(icon("/icons/Cancel.gif"));
        sendMailButton.setText("Anuluj");
        sendMailButton.addActionListener(event -> processSearchPanel());

        jLabel36.setFont(timesNewRoman20);
        jLabel36.setText("Temat:");

        GroupLayout sendMailPanelLayout = new GroupLayout(sendMailPanel);
        sendMailPanel.setLayout(sendMailPanelLayout);
        sendMailPanelLayout.setHorizontalGroup(
                sendMailPanelLayout.createParallelGroup(LEADING)
                        .addGroup(sendMailPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(sendMailPanelLayout.createParallelGroup(LEADING, false)
                                        .addComponent(jScrollPane6, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                                        .addComponent(jLabel32)
                                        .addComponent(jLabel34)
                                        .addComponent(jLabel35)
                                        .addComponent(jScrollPane5)
                                        .addGroup(sendMailPanelLayout.createSequentialGroup()
                                                .addComponent(sendOneMailButton, PREFERRED_SIZE, 135, PREFERRED_SIZE)
                                                .addPreferredGap(RELATED)
                                                .addComponent(clearMailButton)
                                                .addPreferredGap(RELATED)
                                                .addComponent(sendMailButton, PREFERRED_SIZE, 93, PREFERRED_SIZE))
                                        .addGroup(sendMailPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel36)
                                                .addPreferredGap(RELATED)
                                                .addComponent(txtMailSubject)))
                                .addContainerGap(233, Short.MAX_VALUE))
        );
        sendMailPanelLayout.setVerticalGroup(
                sendMailPanelLayout.createParallelGroup(LEADING)
                        .addGroup(sendMailPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel32)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel34)
                                .addPreferredGap(RELATED)
                                .addComponent(jScrollPane5, PREFERRED_SIZE, 92, PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(sendMailPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel36)
                                        .addComponent(txtMailSubject, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(jLabel35)
                                .addPreferredGap(RELATED)
                                .addComponent(jScrollPane6, PREFERRED_SIZE, 233, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addGroup(sendMailPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(sendOneMailButton)
                                        .addComponent(clearMailButton)
                                        .addComponent(sendMailButton))
                                .addContainerGap(188, Short.MAX_VALUE))
        );

        GroupLayout userPanelLayout = new GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
                userPanelLayout.createParallelGroup(LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(userPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(topPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(userPanelLayout.createSequentialGroup()
                                                .addComponent(leftPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(mainUserPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(searchPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(addClientPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                .addGap(0, 0, 0))
                        .addGroup(userPanelLayout.createParallelGroup(LEADING)
                                .addGroup(userPanelLayout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(sendMailPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );
        userPanelLayout.setVerticalGroup(
                userPanelLayout.createParallelGroup(LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addComponent(topPanel, PREFERRED_SIZE, 62, PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(userPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(leftPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(addClientPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(mainUserPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(searchPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(0, 0, 0))
                        .addGroup(userPanelLayout.createParallelGroup(LEADING)
                                .addGroup(userPanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(sendMailPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );

        loginPanel.setPreferredSize(new Dimension(800, 600));

        jLabel21.setFont(timesNewRomanBold24);
        jLabel21.setText("LOGOWANIE DO SYSTEMU SadCRM");

        jLabel22.setFont(timesNewRoman20);
        jLabel22.setText("Login");

        jLabel23.setFont(timesNewRoman20);
        jLabel23.setText("Hasło");

        txtLogin.addActionListener(event -> processLoginAction());

        loginButton.setIcon(icon("/icons/Blue key.gif"));
        loginButton.setText("Zaloguj");
        loginButton.addActionListener(event -> processLoginAction());

        txtPassword.addActionListener(event -> processLoginAction());

        GroupLayout loginPanelLayout = new GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
                loginPanelLayout.createParallelGroup(LEADING)
                        .addGroup(TRAILING, loginPanelLayout.createSequentialGroup()
                                .addContainerGap(278, Short.MAX_VALUE)
                                .addGroup(loginPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(loginPanelLayout.createSequentialGroup()
                                                .addGap(41, 41, 41)
                                                .addGroup(loginPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel22)
                                                        .addComponent(jLabel23))
                                                .addPreferredGap(RELATED)
                                                .addGroup(loginPanelLayout.createParallelGroup(LEADING, false)
                                                        .addComponent(txtLogin)
                                                        .addComponent(txtPassword, PREFERRED_SIZE, 230, PREFERRED_SIZE)))
                                        .addComponent(jLabel21)
                                        .addGroup(TRAILING, loginPanelLayout.createSequentialGroup()
                                                .addComponent(loginButton, PREFERRED_SIZE, 130, PREFERRED_SIZE)
                                                .addGap(141, 141, 141)))
                                .addGap(57, 57, 57))
        );
        loginPanelLayout.setVerticalGroup(
                loginPanelLayout.createParallelGroup(LEADING)
                        .addGroup(TRAILING, loginPanelLayout.createSequentialGroup()
                                .addContainerGap(261, Short.MAX_VALUE)
                                .addComponent(jLabel21)
                                .addGap(48, 48, 48)
                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel22)
                                        .addComponent(txtLogin, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel23)
                                        .addComponent(txtPassword, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(UNRELATED)
                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginButton))
                                .addGap(158, 158, 158))
        );

        adminPanel.setBackground(lightGray);
        adminPanel.setBorder(createLineBorder(new Color(0, 0, 0)));
        adminPanel.setPreferredSize(new Dimension(1000, 800));

        topPanel1.setBackground(lightGray);
        topPanel1.setBorder(createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        topPanel1.setForeground(new Color(238, 9, 9));
        topPanel1.setPreferredSize(new Dimension(1000, 50));

        dataExpButton1.setIcon(icon("/icons/Upload.gif"));
        dataExpButton1.setText("Eksport danych");
        dataExpButton1.addActionListener(this::dataExpButton1ActionPerformed);

        GroupLayout topPanel1Layout = new GroupLayout(topPanel1);
        topPanel1.setLayout(topPanel1Layout);
        topPanel1Layout.setHorizontalGroup(
                topPanel1Layout.createParallelGroup(LEADING)
                        .addGroup(topPanel1Layout.createSequentialGroup()
                                .addGap(194, 194, 194)
                                .addComponent(dataExpButton1)
                                .addContainerGap(658, Short.MAX_VALUE))
        );
        topPanel1Layout.setVerticalGroup(
                topPanel1Layout.createParallelGroup(LEADING)
                        .addGroup(TRAILING, topPanel1Layout.createSequentialGroup()
                                .addContainerGap(16, Short.MAX_VALUE)
                                .addComponent(dataExpButton1)
                                .addContainerGap())
        );

        leftPanel1.setBackground(lightGray);
        leftPanel1.setBorder(createEtchedBorder());
        leftPanel1.setOpaque(false);
        leftPanel1.setPreferredSize(new Dimension(200, 750));

        addUserButton.setIcon(icon("/icons/Create.gif"));
        addUserButton.setText("Dodaj użytkownika");
        addUserButton.addActionListener(this::addUserButtonActionPerformed);

        searchUserButton.setIcon(icon("/icons/Find.gif"));
        searchUserButton.setText("Wyszukiwanie");
        searchUserButton.addActionListener(event -> processAdminSearch());

        myAdminPanelButton.setIcon(icon("/icons/Info.gif"));
        myAdminPanelButton.setText("Mój panel");
        myAdminPanelButton.addActionListener(this::myAdminPanelButtonActionPerformed);

        logoutAdminButton.setIcon(icon("/icons/Exit.gif"));
        logoutAdminButton.setText("Wylogowanie");
        logoutAdminButton.addActionListener(event -> logoutMessageBox());

        exitAdminButton.setIcon(icon("/icons/Turn off.gif"));
        exitAdminButton.setText("Wyjście");
        exitAdminButton.addActionListener(event -> exitCommonAction());

        GroupLayout leftPanel1Layout = new GroupLayout(leftPanel1);
        leftPanel1.setLayout(leftPanel1Layout);
        leftPanel1Layout.setHorizontalGroup(
                leftPanel1Layout.createParallelGroup(LEADING)
                        .addComponent(addUserButton, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                        .addComponent(searchUserButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myAdminPanelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutAdminButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitAdminButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        leftPanel1Layout.setVerticalGroup(
                leftPanel1Layout.createParallelGroup(LEADING)
                        .addGroup(leftPanel1Layout.createSequentialGroup()
                                .addComponent(addUserButton)
                                .addPreferredGap(RELATED)
                                .addComponent(searchUserButton)
                                .addPreferredGap(RELATED)
                                .addComponent(myAdminPanelButton)
                                .addPreferredGap(RELATED)
                                .addComponent(logoutAdminButton)
                                .addPreferredGap(RELATED)
                                .addComponent(exitAdminButton)
                                .addGap(0, 572, Short.MAX_VALUE))
        );

        mainAdminPanel.setBorder(createLineBorder(new Color(0, 0, 0)));
        mainAdminPanel.setPreferredSize(new Dimension(800, 750));

        jLabel20.setFont(timesNewRomanBold24);
        jLabel20.setText("Zalogowany uzytkownik:");

        jLabel31.setFont(timesNewRoman20);
        jLabel31.setText("Imię:");

        jLabel33.setFont(timesNewRoman20);
        jLabel33.setText("Nazwisko:");

        jLabel37.setFont(timesNewRoman20);
        jLabel37.setText("Typ konta:");

        jLabel38.setFont(timesNewRoman20);
        jLabel38.setText("Login:");

        txtAdminName.setEditable(false);

        txtAdminSurname.setEditable(false);

        txtAdminLogin.setEditable(false);

        txtAdminType.setEditable(false);

        changeAdminPassButton.setIcon(icon("/icons/Repair.gif"));
        changeAdminPassButton.setText("Zmiana hasła");
        changeAdminPassButton.addActionListener(event -> commonChangePassword());

        GroupLayout mainAdminPanelLayout = new GroupLayout(mainAdminPanel);
        mainAdminPanel.setLayout(mainAdminPanelLayout);
        mainAdminPanelLayout.setHorizontalGroup(
                mainAdminPanelLayout.createParallelGroup(LEADING)
                        .addGroup(mainAdminPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainAdminPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(mainAdminPanelLayout.createSequentialGroup()
                                                .addGroup(mainAdminPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel31)
                                                        .addComponent(jLabel33)
                                                        .addComponent(jLabel37)
                                                        .addComponent(jLabel38))
                                                .addGap(52, 52, 52)
                                                .addGroup(mainAdminPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(txtAdminLogin, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(txtAdminSurname, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(txtAdminName, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(txtAdminType, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(changeAdminPassButton)))
                                        .addComponent(jLabel20))
                                .addContainerGap(293, Short.MAX_VALUE))
        );
        mainAdminPanelLayout.setVerticalGroup(
                mainAdminPanelLayout.createParallelGroup(LEADING)
                        .addGroup(mainAdminPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel20)
                                .addGap(30, 30, 30)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel31)
                                        .addComponent(txtAdminName, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel33)
                                        .addComponent(txtAdminSurname, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel37)
                                        .addComponent(txtAdminType, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel38)
                                        .addComponent(txtAdminLogin, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(changeAdminPassButton)
                                .addContainerGap(499, Short.MAX_VALUE))
        );

        addUserPanel.setBorder(null);
        addUserPanel.setPreferredSize(new Dimension(800, 750));

        jLabel24.setFont(timesNewRomanBold24);
        jLabel24.setText("Dodanie nowego użytkownika");

        jLabel39.setFont(timesNewRoman20);
        jLabel39.setText("Imię*:");

        jLabel40.setFont(timesNewRoman20);
        jLabel40.setText("Nazwisko*:");

        jLabel41.setFont(timesNewRoman20);
        jLabel41.setText("Typ*:");

        jLabel42.setFont(timesNewRoman20);
        jLabel42.setText("Hasło*:");

        jLabel48.setFont(timesNewRoman20);
        jLabel48.setText("Data utworzenia");

        saveUserButton.setIcon(icon("/icons/Save.gif"));
        saveUserButton.setText("Zapisz");
        saveUserButton.addActionListener(this::saveUserButtonActionPerformed);

        cancelUserButton.setIcon(icon("/icons/Cancel.gif"));
        cancelUserButton.setText("Anuluj");
        cancelUserButton.addActionListener(this::cancelUserButtonActionPerformed);

        jLabel58.setFont(timesNewRoman20);
        jLabel58.setText("Powtórz hasło*:");

        txtAddUserDate.setEditable(false);
        txtAddUserDate.setEnabled(false);

        jLabel43.setFont(timesNewRoman20);
        jLabel43.setText("Login*:");

        txtAddUserType.setModel(new DefaultComboBoxModel<>(UserType.values()));

        GroupLayout addUserPanelLayout = new GroupLayout(addUserPanel);
        addUserPanel.setLayout(addUserPanelLayout);
        addUserPanelLayout.setHorizontalGroup(
                addUserPanelLayout.createParallelGroup(LEADING)
                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(addUserPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel41)
                                                .addGap(130, 130, 130)
                                                .addComponent(txtAddUserType, PREFERRED_SIZE, 235, PREFERRED_SIZE))
                                        .addComponent(jLabel40)
                                        .addComponent(jLabel24)
                                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                                .addGroup(addUserPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel42)
                                                        .addComponent(jLabel39)
                                                        .addComponent(jLabel43)
                                                        .addComponent(jLabel58)
                                                        .addComponent(jLabel48))
                                                .addGap(18, 18, 18)
                                                .addGroup(addUserPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                                                .addComponent(saveUserButton, PREFERRED_SIZE, 125, PREFERRED_SIZE)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cancelUserButton, PREFERRED_SIZE, 118, PREFERRED_SIZE))
                                                        .addComponent(txtAddUserPassword2, PREFERRED_SIZE, 317, PREFERRED_SIZE)
                                                        .addComponent(txtAddUserPassword1, PREFERRED_SIZE, 317, PREFERRED_SIZE)
                                                        .addComponent(txtAddUserLogin, PREFERRED_SIZE, 317, PREFERRED_SIZE)
                                                        .addComponent(txtAddUserSurname, PREFERRED_SIZE, 317, PREFERRED_SIZE)
                                                        .addComponent(txtAddUserName, PREFERRED_SIZE, 317, PREFERRED_SIZE)
                                                        .addComponent(txtAddUserDate, PREFERRED_SIZE, 317, PREFERRED_SIZE))))
                                .addContainerGap(288, Short.MAX_VALUE))
        );
        addUserPanelLayout.setVerticalGroup(
                addUserPanelLayout.createParallelGroup(LEADING)
                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel24)
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel39)
                                        .addComponent(txtAddUserName, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel40)
                                        .addComponent(txtAddUserSurname, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtAddUserLogin, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(jLabel43))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(TRAILING)
                                        .addComponent(jLabel41)
                                        .addComponent(txtAddUserType, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel42)
                                        .addComponent(txtAddUserPassword1, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel58)
                                        .addComponent(txtAddUserPassword2, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel48)
                                        .addComponent(txtAddUserDate, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(addUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveUserButton)
                                        .addComponent(cancelUserButton))
                                .addContainerGap(415, Short.MAX_VALUE))
        );

        searchUserPanel.setPreferredSize(new Dimension(800, 750));

        jLabel55.setFont(timesNewRomanBold24);
        jLabel55.setText("Wyszukiwanie użytkowników");

        jLabel56.setFont(timesNewRoman20);
        jLabel56.setText("Nazwisko:");

        jLabel57.setFont(timesNewRoman20);
        jLabel57.setText("Imię:");

        adminSearchButton.setIcon(icon("/icons/Search.gif"));
        adminSearchButton.setText("Szukaj");
        adminSearchButton.addActionListener(this::adminSearchButtonActionPerformed);

        adminResetButton.setIcon(icon("/icons/Refresh.gif"));
        adminResetButton.setText("Reset");
        adminResetButton.addActionListener(this::adminResetButtonActionPerformed);

        tableUsers.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        tableUsers.setAutoResizeMode(AUTO_RESIZE_OFF);
        tableUsers.setPreferredSize(new Dimension(780, 100));
        tableUsers.getTableHeader().setResizingAllowed(false);
        tableUsers.getTableHeader().setReorderingAllowed(false);
        tableUsers.addMouseListener((MouseClick) this::tableUsersMouseClicked);
        jScrollPane8.setViewportView(tableUsers);

        adminDetailsButton.setIcon(icon("/icons/Repair.gif"));
        adminDetailsButton.setText("Edycja");
        adminDetailsButton.addActionListener(this::adminDetailsButtonActionPerformed);

        GroupLayout searchUserPanelLayout = new GroupLayout(searchUserPanel);
        searchUserPanel.setLayout(searchUserPanelLayout);
        searchUserPanelLayout.setHorizontalGroup(
                searchUserPanelLayout.createParallelGroup(LEADING)
                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(jScrollPane8, GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel55)
                                                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel56)
                                                                        .addComponent(jLabel57))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(txtAdminSearchSurname, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                                        .addComponent(txtAdminSearchName))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(adminSearchButton, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                                        .addComponent(adminResetButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addComponent(adminDetailsButton))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        searchUserPanelLayout.setVerticalGroup(
                searchUserPanelLayout.createParallelGroup(LEADING)
                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel55)
                                .addGap(30, 30, 30)
                                .addGroup(searchUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel56)
                                        .addComponent(txtAdminSearchSurname, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(adminSearchButton))
                                .addGap(4, 4, 4)
                                .addGroup(searchUserPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel57)
                                        .addComponent(txtAdminSearchName, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(adminResetButton))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane8, PREFERRED_SIZE, 523, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addComponent(adminDetailsButton)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        GroupLayout adminPanelLayout = new GroupLayout(adminPanel);
        adminPanel.setLayout(adminPanelLayout);
        adminPanelLayout.setHorizontalGroup(
                adminPanelLayout.createParallelGroup(LEADING)
                        .addGroup(adminPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(adminPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(topPanel1, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(adminPanelLayout.createSequentialGroup()
                                                .addComponent(leftPanel1, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(mainAdminPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(searchUserPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(addUserPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                .addGap(0, 0, 0))
        );
        adminPanelLayout.setVerticalGroup(
                adminPanelLayout.createParallelGroup(LEADING)
                        .addGroup(adminPanelLayout.createSequentialGroup()
                                .addComponent(topPanel1, PREFERRED_SIZE, 62, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addGroup(adminPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(leftPanel1, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(addUserPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(mainAdminPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(searchUserPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(0, 0, 0))
        );

        managerPanel.setBackground(lightGray);
        managerPanel.setBorder(createLineBorder(new Color(0, 0, 0)));
        managerPanel.setPreferredSize(new Dimension(1000, 800));

        topManagerPanel.setBackground(lightGray);
        topManagerPanel.setBorder(createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        topManagerPanel.setForeground(new Color(238, 9, 9));
        topManagerPanel.setPreferredSize(new Dimension(1000, 50));

        reportsButton.setIcon(icon("/icons/3d bar chart.gif"));
        reportsButton.setText("Raporty");
        reportsButton.addActionListener(this::reportsButtonActionPerformed);

        dataExpButton2.setIcon(icon("/icons/Upload.gif"));
        dataExpButton2.setText("Eksport danych");
        dataExpButton2.addActionListener(this::dataExpButton2ActionPerformed);

        GroupLayout topManagerPanelLayout = new GroupLayout(topManagerPanel);
        topManagerPanel.setLayout(topManagerPanelLayout);
        topManagerPanelLayout.setHorizontalGroup(
                topManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(topManagerPanelLayout.createSequentialGroup()
                                .addGap(196, 196, 196)
                                .addComponent(reportsButton)
                                .addPreferredGap(RELATED)
                                .addComponent(dataExpButton2)
                                .addContainerGap(561, Short.MAX_VALUE))
        );
        topManagerPanelLayout.setVerticalGroup(
                topManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(topManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(topManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(reportsButton)
                                        .addComponent(dataExpButton2))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        leftManagerPanel.setBackground(lightGray);
        leftManagerPanel.setBorder(createEtchedBorder());
        leftManagerPanel.setOpaque(false);
        leftManagerPanel.setPreferredSize(new Dimension(200, 750));

        searchClientByManagerButton.setIcon(icon("/icons/Find.gif"));
        searchClientByManagerButton.setText("Wyszukiwanie");
        searchClientByManagerButton.addActionListener(event -> processSearchClientsForManager());

        myManagerPanelButton.setIcon(icon("/icons/Info.gif"));
        myManagerPanelButton.setText("Mój panel");
        myManagerPanelButton.addActionListener(this::myManagerPanelButtonActionPerformed);

        logoutManagerButton.setIcon(icon("/icons/Exit.gif"));
        logoutManagerButton.setText("Wylogowanie");
        logoutManagerButton.addActionListener(event -> logoutMessageBox());

        exitManagerButton.setIcon(icon("/icons/Turn off.gif"));
        exitManagerButton.setText("Wyjście");
        exitManagerButton.addActionListener(event -> exitCommonAction());

        GroupLayout leftManagerPanelLayout = new GroupLayout(leftManagerPanel);
        leftManagerPanel.setLayout(leftManagerPanelLayout);
        leftManagerPanelLayout.setHorizontalGroup(
                leftManagerPanelLayout.createParallelGroup(LEADING)
                        .addComponent(searchClientByManagerButton, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                        .addComponent(myManagerPanelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutManagerButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitManagerButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        leftManagerPanelLayout.setVerticalGroup(
                leftManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(leftManagerPanelLayout.createSequentialGroup()
                                .addComponent(searchClientByManagerButton)
                                .addPreferredGap(RELATED)
                                .addComponent(myManagerPanelButton)
                                .addPreferredGap(RELATED)
                                .addComponent(logoutManagerButton)
                                .addPreferredGap(RELATED)
                                .addComponent(exitManagerButton)
                                .addGap(0, 608, Short.MAX_VALUE))
        );

        mainManagerPanel.setBorder(createLineBorder(Color.black));
        mainManagerPanel.setPreferredSize(new Dimension(800, 750));

        jLabel49.setFont(timesNewRomanBold24);
        jLabel49.setText("Zalogowany manager:");

        jLabel50.setFont(timesNewRoman20);
        jLabel50.setText("Imię:");

        jLabel51.setFont(timesNewRoman20);
        jLabel51.setText("Nazwisko:");

        jLabel52.setFont(timesNewRoman20);
        jLabel52.setText("Typ konta:");

        jLabel53.setFont(timesNewRoman20);
        jLabel53.setText("Login:");

        txtManagerName.setEditable(false);

        txtManagerSurname.setEditable(false);

        txtManagerLogin.setEditable(false);

        txtManagerType.setEditable(false);

        changeManagerPassButton.setIcon(icon("/icons/Repair.gif"));
        changeManagerPassButton.setText("Zmiana hasła");
        changeManagerPassButton.addActionListener(event -> commonChangePassword());

        GroupLayout mainManagerPanelLayout = new GroupLayout(mainManagerPanel);
        mainManagerPanel.setLayout(mainManagerPanelLayout);
        mainManagerPanelLayout.setHorizontalGroup(
                mainManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(mainManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainManagerPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(mainManagerPanelLayout.createSequentialGroup()
                                                .addGroup(mainManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel50)
                                                        .addComponent(jLabel51)
                                                        .addComponent(jLabel52)
                                                        .addComponent(jLabel53))
                                                .addGap(52, 52, 52)
                                                .addGroup(mainManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(txtManagerLogin, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(txtManagerSurname, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(txtManagerName, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(txtManagerType, PREFERRED_SIZE, 335, PREFERRED_SIZE)
                                                        .addComponent(changeManagerPassButton)))
                                        .addComponent(jLabel49))
                                .addContainerGap(293, Short.MAX_VALUE))
        );
        mainManagerPanelLayout.setVerticalGroup(
                mainManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(mainManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel49)
                                .addGap(30, 30, 30)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel50)
                                        .addComponent(txtManagerName, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel51)
                                        .addComponent(txtManagerSurname, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel52)
                                        .addComponent(txtManagerType, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel53)
                                        .addComponent(txtManagerLogin, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(changeManagerPassButton)
                                .addContainerGap(499, Short.MAX_VALUE))
        );

        searchUserByManagerPanel.setPreferredSize(new Dimension(800, 750));

        jLabel66.setFont(timesNewRomanBold24);
        jLabel66.setText("Wyszukiwanie klienta:");

        jLabel67.setFont(timesNewRoman20);
        jLabel67.setText("Nazwisko:");

        jLabel68.setFont(timesNewRoman20);
        jLabel68.setText("Pesel:");

        managerSearchButton.setIcon(icon("/icons/Search.gif"));
        managerSearchButton.setText("Szukaj");
        managerSearchButton.addActionListener(this::managerSearchButtonActionPerformed);

        managerResetButton.setIcon(icon("/icons/Refresh.gif"));
        managerResetButton.setText("Reset");
        managerResetButton.addActionListener(this::managerResetFieldsAction);

        tableClientsForManager.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        tableClientsForManager.setAutoResizeMode(AUTO_RESIZE_OFF);
        tableClientsForManager.setPreferredSize(new Dimension(780, 100));
        tableClientsForManager.getTableHeader().setResizingAllowed(false);
        tableClientsForManager.getTableHeader().setReorderingAllowed(false);
        tableClientsForManager.addMouseListener((MouseClick) this::tableClientsForManagerMouseClicked);
        jScrollPane9.setViewportView(tableClientsForManager);

        managerEditButton.setIcon(icon("/icons/Repair.gif"));
        managerEditButton.setText("Edycja");
        managerEditButton.addActionListener(this::managerEditButtonActionPerformed);

        GroupLayout searchUserByManagerPanelLayout = new GroupLayout(searchUserByManagerPanel);
        searchUserByManagerPanel.setLayout(searchUserByManagerPanelLayout);
        searchUserByManagerPanelLayout.setHorizontalGroup(
                searchUserByManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(jScrollPane9, GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel66)
                                                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel67)
                                                                        .addComponent(jLabel68))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(txtManagerSearchSurname, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                                        .addComponent(txtManagerSearchPesel))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(managerSearchButton, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                                        .addComponent(managerResetButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addComponent(managerEditButton))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        searchUserByManagerPanelLayout.setVerticalGroup(
                searchUserByManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel66)
                                .addGap(30, 30, 30)
                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel67)
                                        .addComponent(txtManagerSearchSurname, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(managerSearchButton))
                                .addGap(4, 4, 4)
                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel68)
                                        .addComponent(txtManagerSearchPesel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(managerResetButton))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane9, PREFERRED_SIZE, 523, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addComponent(managerEditButton)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        editUserByManagerPanel.setBorder(null);
        editUserByManagerPanel.setPreferredSize(new Dimension(800, 750));

        jLabel69.setFont(timesNewRomanBold24);
        jLabel69.setText("Edycja klienta");

        jLabel70.setFont(timesNewRoman20);
        jLabel70.setText("Imię*:");

        jLabel71.setFont(timesNewRoman20);
        jLabel71.setText("Nazwisko*:");

        jLabel72.setFont(timesNewRoman20);
        jLabel72.setText("Pesel*");

        jLabel73.setFont(timesNewRoman20);
        jLabel73.setText("Nr telefonu 1");

        jLabel74.setFont(timesNewRoman20);
        jLabel74.setText("Nr telefonu 2");

        jLabel75.setFont(timesNewRoman20);
        jLabel75.setText("Klient VIP");

        jLabel76.setFont(timesNewRoman20);
        jLabel76.setText("Adres mail");

        jLabel77.setFont(timesNewRoman20);
        jLabel77.setText("Uwagi");

        jLabel78.setFont(timesNewRoman20);
        jLabel78.setText("Utworzony przez");

        jLabel79.setFont(timesNewRoman20);
        jLabel79.setText("Data utworzenia");

        jLabel80.setFont(timesNewRoman20);
        jLabel80.setText("Ulica*");

        jLabel81.setFont(timesNewRoman20);
        jLabel81.setText("Nr budynku*");

        jLabel82.setFont(timesNewRoman20);
        jLabel82.setText("Miasto*");

        jLabel83.setFont(timesNewRoman20);
        jLabel83.setText("Kod pocztowy*");

        jLabel84.setFont(timesNewRoman20);
        jLabel84.setText("Produkty");

        editClientDesc.setColumns(20);
        editClientDesc.setRows(5);
        jScrollPane2.setViewportView(editClientDesc);

        editClientCreator.setEditable(false);

        editClientCreateDate.setEnabled(false);

        saveClientByManagerButton.setIcon(icon("/icons/Save.gif"));
        saveClientByManagerButton.setText("Zapisz");
        saveClientByManagerButton.addActionListener(this::saveClientByManagerButtonZapiszKlientaAction);

        cancelClientByManagerButton.setIcon(icon("/icons/Cancel.gif"));
        cancelClientByManagerButton.setText("Anuluj");
        cancelClientByManagerButton.addActionListener(event -> processSearchClientsForManager());

        cbEditPersonalAcc.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbEditPersonalAcc.setText("Konto osobiste");

        cbEditCurrencyAcc.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbEditCurrencyAcc.setText("Konto walutowe");

        cbEditLocate.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbEditLocate.setText("Lokata");

        cbEditCurrentCredit.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbEditCurrentCredit.setText("Kredyt gotówkowy");

        cbEditHomeCredit.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbEditHomeCredit.setText("Kredyt hipoteczny");

        cbEditRepeatedCredit.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbEditRepeatedCredit.setText("Kredyt odnawialny");

        cbEditCreditCard.setFont(new Font("Times New Roman,", PLAIN, 18));
        cbEditCreditCard.setText("Karta kredytowa");

        cbEditChance.setModel(new DefaultComboBoxModel<>(new String[]{"Wybierz", "Konto osobiste", "Konto walutowe", "Lokata", "Kredyt gotówkowy", "Kredyt hipoteczny", "Kredyt odnawialny", "Karta kredytowa"}));

        jLabel85.setFont(timesNewRoman20);
        jLabel85.setText("Szansa sprzedaży");

        try {
            editClientPostalCode.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##-###")));
        } catch (ParseException exception) {
            throw new RuntimeException(exception);
        }

        labelClientModDate1.setFont(timesNewRoman20);
        labelClientModDate1.setText("Data modyfikacji");

        editClientModification.setEditable(false);
        editClientModification.setEnabled(false);

        lab1.setFont(timesNewRoman20);
        lab1.setText("Telefon do klienta");

        jLabel86.setFont(timesNewRoman20);
        jLabel86.setText("Data telefonu");

        editClientTel.addActionListener(this::editClientTelActionPerformed);

        selectUserButton.setText("...");
        selectUserButton.addActionListener(this::selectUserButtonActionPerformed);

        GroupLayout editUserByManagerPanelLayout = new GroupLayout(editUserByManagerPanel);
        editUserByManagerPanel.setLayout(editUserByManagerPanelLayout);
        editUserByManagerPanelLayout.setHorizontalGroup(
                editUserByManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel69)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel79)
                                                                        .addComponent(jLabel75)
                                                                        .addComponent(jLabel78)
                                                                        .addComponent(labelClientModDate1))
                                                                .addGap(6, 6, 6)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(editClientVip)
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(saveClientByManagerButton, PREFERRED_SIZE, 125, PREFERRED_SIZE)
                                                                                .addPreferredGap(RELATED)
                                                                                .addComponent(cancelClientByManagerButton, PREFERRED_SIZE, 118, PREFERRED_SIZE))
                                                                        .addGroup(editUserByManagerPanelLayout.createParallelGroup(TRAILING, false)
                                                                                .addGroup(LEADING, editUserByManagerPanelLayout.createSequentialGroup()
                                                                                        .addComponent(editClientCreator, PREFERRED_SIZE, 199, PREFERRED_SIZE)
                                                                                        .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(selectUserButton, PREFERRED_SIZE, 32, PREFERRED_SIZE))
                                                                                .addGroup(LEADING, editUserByManagerPanelLayout.createParallelGroup(TRAILING)
                                                                                        .addComponent(editClientCreateDate, PREFERRED_SIZE, 237, PREFERRED_SIZE)
                                                                                        .addComponent(editClientModification, LEADING, PREFERRED_SIZE, 237, PREFERRED_SIZE))))))
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel72)
                                                        .addComponent(jLabel71)
                                                        .addComponent(jLabel74)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel73)
                                                                .addGap(35, 35, 35)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(editClientPhone, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                                                        .addComponent(editClientName)
                                                                        .addComponent(editClientSurname)
                                                                        .addComponent(editClientPesel)
                                                                        .addComponent(editClientPhone2, PREFERRED_SIZE, 134, PREFERRED_SIZE)))
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel70)
                                                                        .addComponent(jLabel76)
                                                                        .addComponent(jLabel77))
                                                                .addGap(59, 59, 59)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jScrollPane2, PREFERRED_SIZE, 232, PREFERRED_SIZE)
                                                                        .addComponent(editClientMail, PREFERRED_SIZE, 232, PREFERRED_SIZE))))
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(cbEditCurrencyAcc)
                                                                        .addComponent(cbEditPersonalAcc)
                                                                        .addComponent(cbEditLocate)
                                                                        .addComponent(cbEditCurrentCredit)
                                                                        .addComponent(cbEditHomeCredit)
                                                                        .addComponent(cbEditRepeatedCredit)
                                                                        .addComponent(cbEditCreditCard)
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel86)
                                                                                .addGap(57, 57, 57)
                                                                                .addComponent(editClientTelDate, PREFERRED_SIZE, 145, PREFERRED_SIZE))
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel85)
                                                                                .addPreferredGap(RELATED)
                                                                                .addComponent(cbEditChance, PREFERRED_SIZE, 139, PREFERRED_SIZE))
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(lab1)
                                                                                .addPreferredGap(RELATED)
                                                                                .addComponent(editClientTel))))
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGap(14, 14, 14)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel83)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(editClientPostalCode, PREFERRED_SIZE, 165, PREFERRED_SIZE))
                                                                        .addGroup(editUserByManagerPanelLayout.createParallelGroup(TRAILING, false)
                                                                                .addGroup(LEADING, editUserByManagerPanelLayout.createSequentialGroup()
                                                                                        .addComponent(jLabel80)
                                                                                        .addPreferredGap(RELATED, 108, Short.MAX_VALUE)
                                                                                        .addComponent(editClientStreet, PREFERRED_SIZE, 165, PREFERRED_SIZE))
                                                                                .addGroup(LEADING, editUserByManagerPanelLayout.createSequentialGroup()
                                                                                        .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                                                .addComponent(jLabel81)
                                                                                                .addComponent(jLabel82)
                                                                                                .addComponent(jLabel84))
                                                                                        .addGap(41, 41, 41)
                                                                                        .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING, false)
                                                                                                .addComponent(editClientCity, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                                                                                .addComponent(editClientNumber, TRAILING)))))))
                                                .addGap(23, 23, 23))))
        );
        editUserByManagerPanelLayout.setVerticalGroup(
                editUserByManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel69)
                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(TRAILING)
                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(editClientVip)
                                                .addPreferredGap(RELATED)
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel79)
                                                        .addComponent(editClientCreateDate, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(editClientTelDate, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(jLabel86))
                                                .addPreferredGap(RELATED)
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel78)
                                                        .addComponent(editClientCreator, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(selectUserButton))
                                                .addPreferredGap(RELATED)
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelClientModDate1)
                                                        .addComponent(editClientModification, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addGap(19, 19, 19))
                                        .addGroup(LEADING, editUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGap(2, 2, 2)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel80)
                                                                        .addComponent(editClientStreet, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel81)
                                                                        .addComponent(editClientNumber, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel82)
                                                                        .addComponent(editClientCity, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel83)
                                                                        .addComponent(editClientPostalCode, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(jLabel84)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbEditPersonalAcc)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbEditCurrencyAcc)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbEditLocate)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbEditCurrentCredit)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbEditHomeCredit)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbEditRepeatedCredit)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(cbEditCreditCard)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel85)
                                                                        .addComponent(cbEditChance, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel70)
                                                                        .addComponent(editClientName, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel71)
                                                                        .addComponent(editClientSurname, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel72)
                                                                        .addComponent(editClientPesel, TRAILING, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(editClientPhone, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(jLabel73))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel74)
                                                                        .addComponent(editClientPhone2, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(editClientMail, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(jLabel76))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel77)
                                                                        .addComponent(jScrollPane2, PREFERRED_SIZE, 197, PREFERRED_SIZE))))
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGap(11, 11, 11)
                                                                .addComponent(jLabel75))
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(TRAILING)
                                                                        .addComponent(editClientTel)
                                                                        .addComponent(lab1))))
                                                .addPreferredGap(RELATED, 125, Short.MAX_VALUE)))
                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveClientByManagerButton)
                                        .addComponent(cancelClientByManagerButton))
                                .addContainerGap(87, Short.MAX_VALUE))
        );

        reportsByManagerPanel.setPreferredSize(new Dimension(800, 750));

        jLabel59.setFont(timesNewRomanBold24);
        jLabel59.setText("Generowanie raportów");

        jButton4.setIcon(icon("/icons/Report.gif"));
        jButton4.setText("Generuj");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jLabel60.setFont(timesNewRoman20);
        jLabel60.setText("Telefony z: ");

        cbReportDate.setModel(new DefaultComboBoxModel<>(new String[]{"ostatni dzień", "ostatnie 3 dni", "ostatni tydzień", "ostatni miesiąc", "ostatnie 3 miesiące"}));

        jLabel45.setFont(timesNewRoman20);
        jLabel45.setText("Szansa sprzedaży:");

        jButton5.setIcon(icon("/icons/Report.gif"));
        jButton5.setText("Generuj");
        jButton5.addActionListener(event -> ReportsUtil.createSellChanceReport());

        jLabel61.setFont(timesNewRoman20);
        jLabel61.setText("Raport pracownika:");

        txtUserReport.setEditable(false);

        jButton6.setText("...");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jButton7.setIcon(icon("/icons/Report.gif"));
        jButton7.setText("Generuj");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        GroupLayout raportsByManagerPanelLayout = new GroupLayout(reportsByManagerPanel);
        reportsByManagerPanel.setLayout(raportsByManagerPanelLayout);
        raportsByManagerPanelLayout.setHorizontalGroup(
                raportsByManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(jLabel45)
                                        .addComponent(jLabel59)
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(TRAILING, false)
                                                        .addGroup(LEADING, raportsByManagerPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel61)
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(txtUserReport))
                                                        .addGroup(LEADING, raportsByManagerPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel60)
                                                                .addGap(93, 93, 93)
                                                                .addComponent(cbReportDate, PREFERRED_SIZE, 199, PREFERRED_SIZE)))
                                                .addPreferredGap(RELATED)
                                                .addComponent(jButton6, PREFERRED_SIZE, 29, PREFERRED_SIZE)))
                                .addPreferredGap(RELATED, 38, Short.MAX_VALUE)
                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(jButton5)
                                        .addComponent(jButton4)
                                        .addComponent(jButton7))
                                .addGap(219, 219, 219))
        );
        raportsByManagerPanelLayout.setVerticalGroup(
                raportsByManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel59)
                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel60)
                                                        .addComponent(cbReportDate, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel45))
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(jButton4)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton5)))
                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtUserReport, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(jButton6)
                                                        .addComponent(jButton7)))
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel61)))
                                .addContainerGap(569, Short.MAX_VALUE))
        );

        GroupLayout managerPanelLayout = new GroupLayout(managerPanel);
        managerPanel.setLayout(managerPanelLayout);
        managerPanelLayout.setHorizontalGroup(
                managerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(managerPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(topManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(managerPanelLayout.createSequentialGroup()
                                                .addComponent(leftManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(mainManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(searchUserByManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                .addGap(800, 800, 800))
                        .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(editUserByManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(reportsByManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );
        managerPanelLayout.setVerticalGroup(
                managerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(managerPanelLayout.createSequentialGroup()
                                .addComponent(topManagerPanel, PREFERRED_SIZE, 62, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(leftManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(mainManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(searchUserByManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(0, 0, 0))
                        .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(editUserByManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(reportsByManagerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(LEADING)
                        .addGap(0, 1024, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(loginPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(userPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(TRAILING, layout.createSequentialGroup()
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adminPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addContainerGap()))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(managerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addContainerGap(22, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(LEADING)
                        .addGap(0, 824, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(loginPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(userPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(TRAILING, layout.createSequentialGroup()
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adminPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addContainerGap()))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(managerPanel, PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addContainerGap(22, Short.MAX_VALUE)))
        );

        pack();
    }

    private static ImageIcon icon(String iconName) {
        return new ImageIcon(SadCRMForm.class.getResource(iconName));
    }

    private void processLoginAction() {
        leftPanel.setVisible(true);
        topPanel.setVisible(true);

        showServerLocationMessageBox();

        try {
            LoginResponse response = UserDAO.login(txtLogin.getText(), txtPassword.getText());

            showVersionParityMessageBox(response);
            loggedUser = response.getUser();
            updateFormTitle(loggedUser);
            processLoggedUserPanel();
        } catch (UserLoginException exception) {
            showMessageDialog(this, exception.getMessage(), "Błąd logowania", ERROR_MESSAGE);
        }
    }

    private void showServerLocationMessageBox() {
        messageBox("Wybierz server SadCRM z którym chcesz się połączyć:")
                .title("Confirmation")
                .type(QUESTION_MESSAGE)
                .options("Z globalnym", "Z lokalnym")
                .on(0, HttpJson::useGlobalServer)
                .on(1, HttpJson::useLocalServer)
                .ask();
    }

    private void showVersionParityMessageBox(LoginResponse response) {
        String message;
        if (response.getVersion().equals(VERSION)) {
            message = format("Client and Server are of the same version (%s).", VERSION);
        } else {
            message = format("Client (%s) and Server (%s) are of different version.", VERSION, response.getVersion());
        }
        messageBox(message).show();
    }

    private void dodajKlientaAction(ActionEvent event) {
        saveClientButton.setEnabled(true);
        editClientButton.setVisible(false);
        selectedClient = null;

        PanelsUtil.enablePanel(addClientPanel, new JPanel[]{mainUserPanel, searchPanel, sendMailPanel});
        clearClientsFields();

        addClientAction();
    }

    private void clearClientsFields() {
        txtClientName.setText("");
        txtClientSurname.setText("");
        txtClientStreet.setText("");
        txtClientNumber.setText("");
        txtClientCity.setText("");
        txtClientPostalCode.setText("");
        txtClientCreator.setText("");
        txtClientPesel.setText("");
        txtClientPhone1.setText("");
        txtClientPhone2.setText("");
        txtClientMail.setText("");
        txtClientDesc.setText("");
        txtClientModification.setText("");
        txtClientTel.setSelected(false);
        txtClientTelDate.setText("");
        txtClientVip.setSelected(false);
        txtClientCreateDate.setText("");
        cbPersonalAcc.setSelected(false);
        cbCurrencyAcc.setSelected(false);
        cbLocate.setSelected(false);
        cbCurrentCredit.setSelected(false);
        cbHomeCredit.setSelected(false);
        cbRepeatedCredit.setSelected(false);
        cbCreditCard.setSelected(false);
        cbChance.setSelectedIndex(0);
        txtClientName.setEnabled(true);
        txtClientSurname.setEnabled(true);
        txtClientStreet.setEnabled(true);
        txtClientNumber.setEnabled(true);
        txtClientCity.setEnabled(true);
        txtClientPostalCode.setEnabled(true);
        txtClientPesel.setEnabled(true);
        txtClientPhone1.setEnabled(true);
        txtClientPhone2.setEnabled(true);
        txtClientMail.setEnabled(true);
        txtClientDesc.setEnabled(true);
        txtClientVip.setEnabled(true);
        cbPersonalAcc.setEnabled(true);
        cbCurrencyAcc.setEnabled(true);
        cbLocate.setEnabled(true);
        cbCurrentCredit.setEnabled(true);
        cbHomeCredit.setEnabled(true);
        cbRepeatedCredit.setEnabled(true);
        cbCreditCard.setEnabled(true);
        cbChance.setEnabled(true);
        txtClientModification.setEnabled(false);
        txtClientTel.setEnabled(true);
        txtClientTelDate.setEnabled(false);
    }

    private void clearUserFields() {
        txtAddUserLogin.setText("");
        txtAddUserName.setText("");
        txtAddUserPassword1.setText("");
        txtAddUserPassword2.setText("");
        txtAddUserSurname.setText("");
        txtAddUserDate.setText("");
        txtAddUserType.setSelectedIndex(0);

        txtAddUserLogin.setEnabled(true);
        txtAddUserName.setEnabled(true);
        txtAddUserPassword1.setEnabled(true);
        txtAddUserPassword2.setEnabled(true);
        txtAddUserSurname.setEnabled(true);
        txtAddUserDate.setEnabled(true);
        txtAddUserType.setEnabled(true);
    }

    private void addClientAction() {
        txtClientCreateDate.setText(now());
        txtClientCreator.setText(loggedUser.getName() + " " + loggedUser.getSurname());
    }

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    private void zapiszKlientaAction(ActionEvent event) {
        if (selectedClient == null) {
            if (validateClient()) {
                Client client = new Client();
                client.setName(txtClientName.getText());
                client.setSurname(txtClientSurname.getText());
                client.setPesel(txtClientPesel.getText());
                client.setPhone1(txtClientPhone1.getText());
                client.setPhone2(txtClientPhone2.getText());
                client.setMail(txtClientMail.getText());
                client.setDescription(txtClientDesc.getText());
                client.setVip(txtClientVip.isSelected());
                client.setCreated(txtClientCreateDate.getText());
                client.setUser(loggedUser);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    Date parsedDate = dateFormat.parse(txtClientTelDate.getText());
                    client.setTelDate(new Timestamp(parsedDate.getTime()));
                } catch (ParseException exception) {
                    client.setTelDate(null);
                }
                client.setTel(txtClientTel.isSelected() ? "T" : "F");
                Address address = new Address();
                address.setStreet(txtClientStreet.getText());
                address.setNumber(txtClientNumber.getText());
                address.setCity(txtClientCity.getText());
                address.setPostCode(txtClientPostalCode.getText());
                AddressDAO.insert(address);

                client.setAddress(address);

                client.setProducts(createProductsEntry());

                if (cbChance.getSelectedIndex() != 0) {
                    client.setSellChance(cbChance.getSelectedItem());
                }

                try {
                    ClientDAO.insert(client);
                    processUserPanel();
                } catch (ClientInsertException exception) {
                    showMessageDialog(this,
                            exception.getMessage(),
                            "Dodawania klienta",
                            INFORMATION_MESSAGE);
                }
            } else {
                showMessageDialog(this,
                        "Błąd dodawania klienta.\nSkontaktuj się z administratorem.",
                        "Błąd dodawania klienta",
                        ERROR_MESSAGE);
            }
        } else {
            // edycja klienta
            boolean isEdited = false;
            boolean isAddressEdited = false;
            Address address = null;
            //save edited client
            if (!selectedClient.getName().equalsIgnoreCase(txtClientName.getText())) {
                selectedClient.setName(txtClientName.getText());
                isEdited = true;
            }
            if (!selectedClient.getSurname().equalsIgnoreCase(txtClientSurname.getText())) {
                selectedClient.setSurname(txtClientSurname.getText());
                isEdited = true;
            }
            if (!selectedClient.getPesel().equalsIgnoreCase(txtClientPesel.getText())) {
                selectedClient.setPesel(txtClientPesel.getText());
                isEdited = true;
            }
            if ((selectedClient.getPhone1() == null && !txtClientPhone1.getText().equalsIgnoreCase("")) || (selectedClient.getPhone1() != null && !selectedClient.getPhone1().equalsIgnoreCase(txtClientPhone1.getText()))) {
                selectedClient.setPhone1(txtClientPhone1.getText());
                isEdited = true;
            }
            if ((selectedClient.getPhone2() == null && !txtClientPhone2.getText().equalsIgnoreCase("")) || (selectedClient.getPhone2() != null && !selectedClient.getPhone2().equalsIgnoreCase(txtClientPhone2.getText()))) {
                selectedClient.setPhone2(txtClientPhone2.getText());
                isEdited = true;
            }
            if ((selectedClient.getMail() == null && !txtClientMail.getText().equalsIgnoreCase("")) || (selectedClient.getMail() != null && !selectedClient.getMail().equalsIgnoreCase(txtClientMail.getText()))) {
                selectedClient.setMail(txtClientMail.getText());
                isEdited = true;
            }
            if ((selectedClient.getDescription() == null && !txtClientDesc.getText().equalsIgnoreCase("")) || (selectedClient.getDescription() != null && !selectedClient.getDescription().equalsIgnoreCase(txtClientDesc.getText()))) {
                selectedClient.setDescription(txtClientDesc.getText());
                isEdited = true;
            }
            if (!selectedClient.getVip() && txtClientVip.isSelected()) {
                selectedClient.setVip(true);
                isEdited = true;
            } else if (selectedClient.getVip() && !txtClientVip.isSelected()) {
                selectedClient.setVip(false);
                isEdited = true;
            }

            if (!selectedClient.getAddress().getStreet().equalsIgnoreCase(txtClientStreet.getText())) {
                address = selectedClient.getAddress();
                address.setStreet(txtClientStreet.getText());

                isAddressEdited = true;
            }
            if (!selectedClient.getAddress().getNumber().equals(txtClientNumber.getText())) {
                if (address == null) {
                    address = selectedClient.getAddress();
                    address.setNumber(txtClientNumber.getText());
                } else {
                    address.setNumber(txtClientNumber.getText());
                }

                isAddressEdited = true;
            }

            if (!selectedClient.getAddress().getCity().equalsIgnoreCase(txtClientCity.getText())) {
                if (address == null) {
                    address = selectedClient.getAddress();
                    address.setNumber(txtClientCity.getText());
                } else {
                    address.setNumber(txtClientCity.getText());
                }

                isAddressEdited = true;
            }

            if (!selectedClient.getAddress().getPostCode().equalsIgnoreCase(txtClientPostalCode.getText())) {
                if (address == null) {
                    address = selectedClient.getAddress();
                    address.setNumber(txtClientPostalCode.getText());
                } else {
                    address.setNumber(txtClientPostalCode.getText());
                }

                isAddressEdited = true;
            }

            if (!selectedClient.getSellChance().equalsIgnoreCase(cbChance.getSelectedItem().toString())) {
                selectedClient.setSellChance(cbChance.getSelectedItem().toString());
                isEdited = true;
            }

            if (!selectedClient.getProducts().equalsIgnoreCase(createProductsEntry())) {
                selectedClient.setProducts(createProductsEntry());
                isEdited = true;
            }

            if (selectedClient.getTel().equalsIgnoreCase("F") && txtClientTel.isSelected()) {
                selectedClient.setTel("T");
                selectedClient.setTelDate(new Timestamp(new Date().getTime()));
                isEdited = true;
            } else if (selectedClient.getTel().equalsIgnoreCase("T") && !txtClientTel.isSelected()) {
                selectedClient.setTel("F");
                selectedClient.setTelDate(null);
                isEdited = true;
            }

            if (isAddressEdited) {
                if (validateClient()) {
                    selectedClient.setModified(now());
                    AddressDAO.update(address);
                    ClientDAO.update(selectedClient);
                    processSearchPanel();
                }
            } else if (isEdited) {
                if (validateClient()) {
                    selectedClient.setModified(now());
                    try {
                        ClientDAO.update(selectedClient);
                        processSearchPanel();
                    } catch (ClientUpdateException exception) {
                        showMessageDialog(this, exception.getMessage(), "Błąd dodawania klienta", ERROR_MESSAGE);
                    }
                } else {
                    showMessageDialog(this,
                            "Błąd dodawania klienta.\nSkontaktuj się z administratorem.",
                            "Błąd dodawania klienta", ERROR_MESSAGE);
                }
            } else {
                showMessageDialog(this, "Nic nie edytowano.",
                        "Edycja klienta", INFORMATION_MESSAGE);
                processSearchPanel();
            }
        }
    }

    private String createProductsEntryForManager() {
        String products = "";
        if (cbEditPersonalAcc.isSelected()) {
            products = "Konto osobiste";
        }
        if (cbEditCurrencyAcc.isSelected()) {
            if (products.isEmpty()) {
                products = "Konto walutowe";
            } else {
                products = products + ",Konto walutowe";
            }
        }
        if (cbEditLocate.isSelected()) {
            if (products.isEmpty()) {
                products = "Lokata";
            } else {
                products = products + ",Lokata";
            }
        }
        if (cbEditCurrentCredit.isSelected()) {
            if (products.isEmpty()) {
                products = "Kredyt gotówkowy";
            } else {
                products = products + ",Kredyt gotówkowy";
            }
        }
        if (cbEditHomeCredit.isSelected()) {
            if (products.isEmpty()) {
                products = "Kredyt hipoteczny";
            } else {
                products = products + ",Kredyt hipoteczny";
            }
        }
        if (cbEditRepeatedCredit.isSelected()) {
            if (products.isEmpty()) {
                products = "Kredyt odnawialny";
            } else {
                products = products + ",Kredyt odnawialny";
            }
        }
        if (cbEditCreditCard.isSelected()) {
            if (products.isEmpty()) {
                products = "Karta kredytowa";
            } else {
                products = products + ",Karta kredytowa";
            }
        }

        return products;
    }

    private String createProductsEntry() {
        String products = "";
        if (cbPersonalAcc.isSelected()) {
            products = "Konto osobiste";
        }
        if (cbCurrencyAcc.isSelected()) {
            if (products.isEmpty()) {
                products = "Konto walutowe";
            } else {
                products = products + ",Konto walutowe";
            }
        }
        if (cbLocate.isSelected()) {
            if (products.isEmpty()) {
                products = "Lokata";
            } else {
                products = products + ",Lokata";
            }
        }
        if (cbCurrentCredit.isSelected()) {
            if (products.isEmpty()) {
                products = "Kredyt gotówkowy";
            } else {
                products = products + ",Kredyt gotówkowy";
            }
        }
        if (cbHomeCredit.isSelected()) {
            if (products.isEmpty()) {
                products = "Kredyt hipoteczny";
            } else {
                products = products + ",Kredyt hipoteczny";
            }
        }
        if (cbRepeatedCredit.isSelected()) {
            if (products.isEmpty()) {
                products = "Kredyt odnawialny";
            } else {
                products = products + ",Kredyt odnawialny";
            }
        }
        if (cbCreditCard.isSelected()) {
            if (products.isEmpty()) {
                products = "Karta kredytowa";
            } else {
                products = products + ",Karta kredytowa";
            }
        }

        return products;
    }

    private boolean validateClient() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put(txtClientCity.getText(), "Wprowadź miasto");
        fieldsMap.put(txtClientNumber.getText(), "Wprowadź nr budynku");
        fieldsMap.put(txtClientStreet.getText(), "Wprowadź ulicę");
        fieldsMap.put(txtClientPesel.getText(), "Wprowadź pesel");
        fieldsMap.put(txtClientSurname.getText(), "Wprowadź nazwisko klienta");
        fieldsMap.put(txtClientName.getText(), "Wprowadź imię klienta");

        String result = ValidationUtil.validateNotNull(fieldsMap);
        if (result == null) {
            if (ValidationUtil.validatePesel(txtClientPesel.getText())) {
                if (ValidationUtil.validatePostalCode(txtClientPostalCode.getText())) {
                    return true;
                } else {
                    showMessageDialog(this,
                            "Błedny kod pocztowy",
                            "Błąd dodawania klienta",
                            ERROR_MESSAGE);
                    return false;
                }
            } else {
                showMessageDialog(this,
                        "Błedny nr pesel",
                        "Błąd dodawania klienta",
                        ERROR_MESSAGE);

                return false;
            }
        } else {
            showMessageDialog(this,
                    result,
                    "Błąd dodawania klienta",
                    ERROR_MESSAGE);
            return false;
        }
    }

    private boolean validateClientForManager() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put(editClientCity.getText(), "Wprowadź miasto");
        fieldsMap.put(editClientNumber.getText(), "Wprowadź nr budynku");
        fieldsMap.put(editClientStreet.getText(), "Wprowadź ulicę");
        fieldsMap.put(editClientPesel.getText(), "Wprowadź pesel");
        fieldsMap.put(editClientSurname.getText(), "Wprowadź nazwisko klienta");
        fieldsMap.put(editClientName.getText(), "Wprowadź imię klienta");

        String result = ValidationUtil.validateNotNull(fieldsMap);
        if (result == null) {
            if (ValidationUtil.validatePesel(editClientPesel.getText())) {
                if (ValidationUtil.validatePostalCode(editClientPostalCode.getText())) {
                    return true;
                } else {
                    showMessageDialog(this,
                            "Błedny kod pocztowy",
                            "Błąd dodawania klienta",
                            ERROR_MESSAGE);
                    return false;
                }
            } else {
                showMessageDialog(this,
                        "Błedny nr pesel",
                        "Błąd dodawania klienta",
                        ERROR_MESSAGE);

                return false;
            }
        } else {
            showMessageDialog(this,
                    result,
                    "Błąd dodawania klienta",
                    ERROR_MESSAGE);
            return false;
        }
    }

    private void anulujZapisKlientaAction(ActionEvent event) {
        if (selectedClient == null) {
            processUserPanel();
        } else {
            processSearchPanel();
        }
    }

    private void mojeKontaktyAction(ActionEvent event) {
        //enableMyClientsPanel();
        myContacts = true;
        mail = false;
        processSearchPanel();
    }

    private void wyszukiwanieAction(ActionEvent event) {
        myContacts = false;
        mail = false;
        processSearchPanel();
    }

    private void processSearchPanel() {
        PanelsUtil.enablePanel(searchPanel, new JPanel[]{mainUserPanel, addClientPanel, sendMailPanel});
        changeSelectionTypeBasedOnMail();

        Parameters parameters = Parameters.getCredentials();
        if (mail) {
            parameters.hasMail(true);
        }
        if (myContacts) {
            parameters.byUser(loggedUser);
        }
        TableUtil.displayClients(ClientDAO.fetchClientsByParameters(parameters), tableClients);
    }

    private void changeSelectionTypeBasedOnMail() {
        if (mail) {
            txtSendMultipleMail.setVisible(true);
            txtDetailsClientButton.setVisible(false);
            tableClients.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        } else {
            txtSendMultipleMail.setVisible(false);
            txtDetailsClientButton.setVisible(true);
            tableClients.setSelectionMode(SINGLE_SELECTION);
        }
        tableClients.setRowSelectionAllowed(true);
    }

    private void korespondencjaSeryjnaAction(ActionEvent event) {
        myContacts = false;
        mail = true;
        processSearchPanel();
    }

    private void mojPanelAction(ActionEvent event) {
        PanelsUtil.enablePanel(mainUserPanel, new JPanel[]{addClientPanel, searchPanel, sendMailPanel});
    }

    private void wyczyscPolaMailaAction(ActionEvent event) {
        txtMailContent.setText("");
        txtRecipient.setText("");
        txtMailSubject.setText("");
    }

    private void wyslijMailAction(ActionEvent event) {
        PanelsUtil.enablePanel(sendMailPanel, new JPanel[]{mainUserPanel, addClientPanel, searchPanel});
    }

    private void wyslijjednegoMailaAction(ActionEvent event) {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put(txtRecipient.getText(), "Wprowadź odbiorców");
        fieldsMap.put(txtMailSubject.getText(), "Wprowadź temat maila");
        fieldsMap.put(txtMailContent.getText(), "Wprowadź treść maila");

        String result = ValidationUtil.validateNotNull(fieldsMap);
        if (result == null) {
            String recipients = txtRecipient.getText();

            try {
                MailSender.sendMail(recipients, txtMailSubject.getText(), txtMailContent.getText());
                showMessageDialog(this, "Wiadmość została wysłana", "Wysylanie maila", INFORMATION_MESSAGE);

                if (mail) {
                    processSearchPanel();
                } else {
                    processUserPanel();
                }
            } catch (SendMailException exception) {
                showMessageDialog(this, exception.getMessage(), "Błąd wysyłania maila", ERROR_MESSAGE);
            }
        } else {
            showMessageDialog(this, result, "Błąd wysyłania maila", ERROR_MESSAGE);
        }
    }

    private void szukajKlientaAction(ActionEvent event) {
        Parameters parameters = Parameters.getCredentials();

        if (myContacts) {
            parameters.add("user_id", loggedUser.getId());
        }
        if (mail) {
            parameters.add("has_mail", "true");
        }
        if (!txtSearchSurname.getText().isEmpty()) {
            parameters.add("surname", txtSearchSurname.getText());
        }
        if (!txtSearchPesel.getText().isEmpty()) {
            parameters.add("pesel", txtSearchPesel.getText());
        }
        List<Client> searchResults = ClientDAO.fetchClientsByParameters(parameters);
        TableUtil.displayClients(searchResults, tableClients);
    }

    private void resetPolSzukaniaKlientaAction(ActionEvent event) {
        txtSearchPesel.setText("");
        txtSearchSurname.setText("");

        Parameters parameters = Parameters.getCredentials();
        if (myContacts) {
            parameters.byUser(loggedUser);
        }
        if (mail) {
            parameters.hasMail(true);
        }
        TableUtil.displayClients(ClientDAO.fetchClientsByParameters(parameters), tableClients);
    }

    private void szczegolyKlientaAction(ActionEvent event) {
        if (tableClients.getSelectedRowCount() == 1) {
            editClientAction(tableClients.getSelectedRow());
        } else {
            showMessageDialog(this, "Wybierz klienta", "Zaznacz wiersz", ERROR_MESSAGE);
        }
    }

    private void edytujKlientaAction(ActionEvent event) {
        saveClientButton.setEnabled(true);
        editClientButton.setEnabled(false);

        txtClientName.setEnabled(true);
        txtClientSurname.setEnabled(true);
        txtClientStreet.setEnabled(true);
        txtClientNumber.setEnabled(true);
        txtClientCity.setEnabled(true);
        txtClientPostalCode.setEnabled(true);
        txtClientPesel.setEnabled(true);
        txtClientPhone1.setEnabled(true);
        txtClientPhone2.setEnabled(true);
        txtClientMail.setEnabled(true);
        txtClientDesc.setEnabled(true);
        txtClientVip.setEnabled(true);

        cbPersonalAcc.setEnabled(true);
        cbCurrencyAcc.setEnabled(true);
        cbLocate.setEnabled(true);
        cbCurrentCredit.setEnabled(true);
        cbHomeCredit.setEnabled(true);
        cbRepeatedCredit.setEnabled(true);
        cbCreditCard.setEnabled(true);
        cbChance.setEnabled(true);

        txtClientModification.setEnabled(false);
        txtClientTel.setEnabled(true);
        txtClientTelDate.setEnabled(false);
        txtClientModification.setText(now());
    }

    private void tableClientsMouseClicked(MouseEvent mouseEvent) {
        tableClients.addMouseListener((MouseClick) event -> {
            if (event.getClickCount() == 2) {
                if (mail) {
                    int[] rows = tableClients.getSelectedRows();
                    String receipts = "";
                    for (int i = 0; i < rows.length; i++) {
                        receipts = receipts + tableClients.getValueAt(i, 4) + "\n";
                    }
                    processMailPanel(receipts);
                } else {
                    JTable target = (JTable) event.getSource();
                    int row = target.getSelectedRow();

                    editClientAction(row);
                }
            }
        });
    }

    private void wyslijMailDoWszystkichAction(ActionEvent event) {
        int[] rows = tableClients.getSelectedRows();
        String receipts = "";
        for (int row : rows) {
            receipts = receipts + tableClients.getValueAt(row, 4) + ",";
        }
        if (receipts.endsWith(",")) {
            receipts = receipts.substring(0, receipts.length() - 1);
        }
        processMailPanel(receipts);
    }

    private void logoutMessageBox() {
        messageBox("Czy napewno chcesz się wylogować z programu?")
                .title("Wyjście")
                .options("Tak", "Nie")
                .onYes(() -> {
                    loggedUser = null;
                    txtLogin.setText("");
                    txtPassword.setText("");
                    PanelsUtil.enablePanel(loginPanel, new JPanel[]{userPanel, adminPanel, managerPanel, addClientPanel, searchPanel, sendMailPanel, leftPanel, topPanel});
                })
                .ask();
    }

    private void adminSearchButtonActionPerformed(ActionEvent event) {
        Parameters parameters = Parameters.getCredentials();

        if (!txtAdminSearchSurname.getText().isEmpty()) {
            parameters.add("surname", txtAdminSearchSurname.getText());
        }

        if (!txtAdminSearchName.getText().isEmpty()) {
            parameters.add("name", txtAdminSearchName.getText());
        }

        TableUtil.displayUsers(UserDAO.fetchUsersByParameters(parameters), tableUsers);
    }

    private void adminResetButtonActionPerformed(ActionEvent event) {
        txtAdminSearchName.setText("");
        txtAdminSearchSurname.setText("");

        TableUtil.displayUsers(UserDAO.searchUsers(), tableUsers);
    }

    private void addUserButtonActionPerformed(ActionEvent event) {
        PanelsUtil.enablePanel(addUserPanel, new JPanel[]{searchUserPanel, mainAdminPanel});
        clearUserFields();

        txtAddUserDate.setText(now());
        selectedUser = null;
    }

    private void cancelUserButtonActionPerformed(ActionEvent event) {
        if (selectedUser == null) {
            PanelsUtil.enablePanel(mainAdminPanel, new JPanel[]{searchUserPanel, addUserPanel});
        } else {
            PanelsUtil.enablePanel(searchUserPanel, new JPanel[]{mainAdminPanel, addUserPanel});
        }
    }

    private void saveUserButtonActionPerformed(ActionEvent event) {
        if (selectedUser == null) {
            // dodanie
            if (validateUser()) {
                User newUserLocal = new User();
                newUserLocal.setName(txtAddUserName.getText());
                newUserLocal.setSurname(txtAddUserSurname.getText());
                newUserLocal.setLogin(txtAddUserLogin.getText());
                newUserLocal.setPassword(txtAddUserPassword1.getText());
                newUserLocal.setType(txtAddUserType.getSelectedItem());
                newUserLocal.setCreated(txtAddUserDate.getText());

                try {
                    UserDAO.insertUser(newUserLocal);
                    messageBox("Nowy użytkownik został dodany.")
                            .title("Dodawania użytkownika")
                            .type(INFORMATION_MESSAGE)
                            .show();

                    processAdminPanel();
                } catch (UserInsertException ex) {
                    messageBox(ex.getMessage())
                            .title("Dodawania klienta")
                            .type(ERROR_MESSAGE)
                            .show();
                }
            }
        } else {
            if (isUserEdited() && validateUser()) {
                selectedUser.setCreated(now());
                try {
                    UserDAO.updateUser(selectedUser);
                    processAdminSearch();
                } catch (UserUpdateException exception) {
                    showMessageDialog(this, "Taki login już istnieje", "Błąd dodawania użytkownika", ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean isUserEdited() {
        boolean isEdited = false;
        if (!selectedUser.getName().equalsIgnoreCase(txtAddUserName.getText())) {
            selectedUser.setName(txtAddUserName.getText());
            isEdited = true;
        }
        if (!selectedUser.getSurname().equalsIgnoreCase(txtAddUserSurname.getText())) {
            selectedUser.setSurname(txtAddUserSurname.getText());
            isEdited = true;
        }
        if (!selectedUser.getLogin().equalsIgnoreCase(txtAddUserLogin.getText())) {
            selectedUser.setLogin(txtAddUserLogin.getText());
            isEdited = true;
        }
        if (!selectedUser.getPassword().equalsIgnoreCase(txtAddUserPassword1.getText())) {
            selectedUser.setPassword(txtAddUserPassword1.getText());
            isEdited = true;
        }
        if (selectedUser.getType() != txtAddUserType.getSelectedItem()) {
            selectedUser.setType(txtAddUserType.getSelectedItem());
            isEdited = true;
        }
        return isEdited;
    }

    private void txtClientTelActionPerformed(ActionEvent event) {
        if (txtClientTel.isSelected()) {
            txtClientTelDate.setText(now());
        } else {
            txtClientTelDate.setText(null);
        }
    }

    private void processAdminSearch() {
        PanelsUtil.enablePanel(searchUserPanel, new JPanel[]{addUserPanel, mainAdminPanel});
        selectedUser = null;
        tableUsers.setRowSelectionAllowed(true);
        tableUsers.setSelectionMode(SINGLE_SELECTION);

        TableUtil.displayUsers(UserDAO.searchUsers(), tableUsers);
    }

    private void adminDetailsButtonActionPerformed(ActionEvent event) {
        if (tableUsers.getSelectedRowCount() == 1) {
            editUserAction(tableUsers.getSelectedRow());
        } else {
            showMessageDialog(this, "Wybierz użytkownika", "Zaznacz wiersz", ERROR_MESSAGE);
        }
    }

    private void editUserAction(int row) {
        Object selectedUserId = tableUsers.getModel().getValueAt(row, 0);
        selectedUser = UserDAO.getUserById((Integer) selectedUserId);

        PanelsUtil.enablePanel(addUserPanel, new JPanel[]{mainAdminPanel, searchUserPanel});

        txtAddUserName.setText(selectedUser.getName());
        txtAddUserSurname.setText(selectedUser.getSurname());
        txtAddUserLogin.setText(selectedUser.getLogin());
        txtAddUserDate.setText(selectedUser.getCreated());
        txtAddUserDate.setEditable(false);
        txtAddUserPassword1.setText(selectedUser.getPassword());
        txtAddUserPassword2.setText(selectedUser.getPassword());
        txtAddUserType.setSelectedItem(selectedUser.getType());
    }

    private void myAdminPanelButtonActionPerformed(ActionEvent event) {
        PanelsUtil.enablePanel(mainAdminPanel, new JPanel[]{addUserPanel, searchUserPanel});
    }

    private void tableUsersMouseClicked(MouseEvent mouseEvent) {
        tableUsers.addMouseListener((MouseClick) event -> {
            if (event.getClickCount() == 2) {
                JTable target = (JTable) event.getSource();
                editUserAction(target.getSelectedRow());
            }
        });
    }

    private void commonChangePassword() {
        jDialog1.setVisible(true);
        jDialog1.setSize(400, 200);
        txtChangePass1.setText("");
        txtChangePass2.setText("");
    }

    private void changePasswordButtonActionPerformed(ActionEvent event) {
        if (!ValidationUtil.validatePassword(txtChangePass1.getText())) {
            messageBox("Hasło powinno mieć co najmniej 4 znaki, nie więcej niż 10 znaków. ")
                    .title("Zmiana hasła")
                    .type(ERROR_MESSAGE)
                    .show();
            return;
        }

        if (!txtChangePass1.getText().equals(txtChangePass2.getText())) {
            messageBox("Hasła muszą być takie same. ")
                    .title("Zmiana hasła")
                    .type(ERROR_MESSAGE)
                    .show();
            return;
        }

        loggedUser.setPassword(txtChangePass1.getText());
        UserDAO.updateUser(loggedUser);

        messageBox("Hasła zostało zmienione. ")
                .title("Zmiana hasła")
                .type(INFORMATION_MESSAGE)
                .show();

        jDialog1.setVisible(false);
        jDialog1.dispose();
    }

    private void cancelChangePasswordButtonActionPerformed(ActionEvent event) {
        jDialog1.setVisible(false);
        jDialog1.dispose();
    }

    private void processSearchClientsForManager() {
        PanelsUtil.enablePanel(searchUserByManagerPanel, new JPanel[]{editUserByManagerPanel, mainManagerPanel, reportsByManagerPanel});
        selectedUser = null;
        tableClientsForManager.setRowSelectionAllowed(true);
        tableClientsForManager.setSelectionMode(SINGLE_SELECTION);

        List<Client> clients = ClientDAO.searchClients();
        TableUtil.displayClients(clients, tableClientsForManager);
    }

    private void myManagerPanelButtonActionPerformed(ActionEvent event) {
        PanelsUtil.enablePanel(mainManagerPanel, new JPanel[]{editUserByManagerPanel, searchUserByManagerPanel, reportsByManagerPanel});
    }

    private void exitCommonAction() {
        int option = showOptionDialog(this,
                "Czy napewno chcesz wyjść z programu?",
                "Wyjście",
                YES_NO_OPTION, INFORMATION_MESSAGE, null, new Object[]{"Tak", "Nie"}, null);

        if (option == YES_OPTION) {
            System.exit(1);
        }
    }

    private void managerSearchButtonActionPerformed(ActionEvent event) {
        Parameters parameters = Parameters.getCredentials();

        if (!txtManagerSearchPesel.getText().isEmpty()) {
            parameters.add("pesel", txtManagerSearchPesel.getText());
        }

        if (!txtManagerSearchSurname.getText().isEmpty()) {
            parameters.add("surname", txtManagerSearchSurname.getText());
        }

        TableUtil.displayClients(ClientDAO.fetchClientsByParameters(parameters), tableClientsForManager);
    }

    private void managerResetFieldsAction(ActionEvent event) {
        txtManagerSearchPesel.setText("");
        txtManagerSearchSurname.setText("");

        TableUtil.displayClients(ClientDAO.searchClients(), tableClientsForManager);
    }

    private void tableClientsForManagerMouseClicked(MouseEvent mouseEvent) {
        tableClientsForManager.addMouseListener((MouseClick) event -> {
            if (event.getClickCount() == 2) {

                JTable target = (JTable) event.getSource();
                int selectedRow = target.getSelectedRow();

                editClientForManager(selectedRow);
            }
        });
    }

    private void managerEditButtonActionPerformed(ActionEvent event) {
        if (tableClientsForManager.getSelectedRowCount() == 0) {
            showMessageDialog(this,
                    "Wybierz klienta",
                    "Zaznacz wiersz",
                    ERROR_MESSAGE);
            return;
        }
        editClientForManager(tableClientsForManager.getSelectedRow());
    }

    private void saveClientByManagerButtonZapiszKlientaAction(ActionEvent event) {
        boolean isEdited = false;
        boolean isAddressEdited = false;
        Address address = null;

        //save edited client
        if (!selectedClient.getName().equalsIgnoreCase(editClientName.getText())) {
            selectedClient.setName(editClientName.getText());
            isEdited = true;
        }
        if (!selectedClient.getSurname().equalsIgnoreCase(editClientSurname.getText())) {
            selectedClient.setSurname(editClientSurname.getText());
            isEdited = true;
        }
        if (!selectedClient.getPesel().equalsIgnoreCase(editClientPesel.getText())) {
            selectedClient.setPesel(editClientPesel.getText());
            isEdited = true;
        }
        if ((selectedClient.getPhone1() == null && !editClientPhone.getText().equalsIgnoreCase("")) || (selectedClient.getPhone1() != null && !selectedClient.getPhone1().equalsIgnoreCase(editClientPhone.getText()))) {
            selectedClient.setPhone1(editClientPhone.getText());
            isEdited = true;
        }
        if ((selectedClient.getPhone2() == null && !txtClientPhone2.getText().equalsIgnoreCase("")) || (selectedClient.getPhone2() != null && !selectedClient.getPhone2().equalsIgnoreCase(editClientPhone2.getText()))) {
            selectedClient.setPhone2(editClientPhone2.getText());
            isEdited = true;
        }
        if ((selectedClient.getMail() == null && !editClientMail.getText().equalsIgnoreCase("")) || (selectedClient.getMail() != null && !selectedClient.getMail().equalsIgnoreCase(editClientMail.getText()))) {
            selectedClient.setMail(editClientMail.getText());
            isEdited = true;
        }
        if ((selectedClient.getDescription() == null && !editClientDesc.getText().equalsIgnoreCase("")) || (selectedClient.getDescription() != null && !selectedClient.getDescription().equalsIgnoreCase(editClientDesc.getText()))) {
            selectedClient.setDescription(editClientDesc.getText());
            isEdited = true;
        }
        if (selectedClient.getVip() != editClientVip.isSelected()) {
            selectedClient.setVip(editClientVip.isSelected());
            isEdited = true;
        }

        if (!selectedClient.getAddress().getStreet().equalsIgnoreCase(editClientStreet.getText())) {
            address = selectedClient.getAddress();
            address.setStreet(editClientStreet.getText());

            isAddressEdited = true;
        }
        if (!selectedClient.getAddress().getNumber().equals(editClientNumber.getText())) {
            if (address == null) {
                address = selectedClient.getAddress();
                address.setNumber(editClientNumber.getText());
            } else {
                address.setNumber(editClientNumber.getText());
            }

            isAddressEdited = true;
        }

        // TODO
        if (!selectedClient.getAddress().getCity().equalsIgnoreCase(editClientCity.getText())) {
            if (address == null) {
                address = selectedClient.getAddress();
                address.setNumber(editClientCity.getText());
            } else {
                address.setNumber(editClientCity.getText());
            }

            isAddressEdited = true;
        }

        if (!selectedClient.getAddress().getPostCode().equalsIgnoreCase(editClientPostalCode.getText())) {
            if (address == null) {
                address = selectedClient.getAddress();
                address.setNumber(editClientPostalCode.getText());
            } else {
                address.setNumber(editClientPostalCode.getText());
            }

            isAddressEdited = true;
        }

        String sellChance = selectedClient.getSellChance();
        if (sellChance == null && cbEditChance.getSelectedIndex() != 0) {
            selectedClient.setSellChance(cbEditChance.getSelectedItem());
            isEdited = true;
        } else if (sellChance != null && !sellChance.equalsIgnoreCase(cbEditChance.getSelectedItem())) {
            selectedClient.setSellChance(cbEditChance.getSelectedItem());
            isEdited = true;
        }

        String selectedProducts = selectedClient.getProducts();
        String prodForManager = createProductsEntryForManager();
        if (!selectedProducts.equalsIgnoreCase(prodForManager)) {
            selectedClient.setProducts(prodForManager);
            isEdited = true;
        }

        if (selectedClient.getTel().equalsIgnoreCase("F") && editClientTel.isSelected()) {
            selectedClient.setTel("T");
            selectedClient.setTelDate(new Timestamp(new Date().getTime()));
            editClientTelDate.setText(now());
            isEdited = true;
        } else if (selectedClient.getTel().equalsIgnoreCase("T") && !editClientTel.isSelected()) {
            selectedClient.setTel("F");
            selectedClient.setTelDate(null);
            isEdited = true;
            editClientTelDate.setText(now());
        }

        String currentCreatorName = selectedClient.getUser().getName() + " " + selectedClient.getUser().getSurname();
        if (!editClientCreator.getText().equalsIgnoreCase(currentCreatorName)) {
            isEdited = true;
            selectedClient.setUser(newUser);
        }

        if (isAddressEdited) {
            if (validateClientForManager()) {
                selectedClient.setModified(now());
                AddressDAO.update(address);
                ClientDAO.update(selectedClient);
                processSearchClientsForManager();
            }
        } else if (isEdited) {
            if (validateClientForManager()) {
                selectedClient.setModified(now());
                try {
                    ClientDAO.update(selectedClient);
                    processSearchClientsForManager();
                } catch (ClientUpdateException exception) {
                    showMessageDialog(this, exception.getMessage(), "Błąd dodawania klienta", ERROR_MESSAGE);
                }
            } else {
                showMessageDialog(this, "Błąd dodawania klienta.\nSkontaktuj się z administratorem.", "Błąd dodawania klienta", ERROR_MESSAGE);
            }
        } else {
            showMessageDialog(this, "Nic nie edytowano.", "Edycja klienta", INFORMATION_MESSAGE);
            processSearchClientsForManager();
        }
    }

    private void editClientTelActionPerformed(ActionEvent event) {
        if (editClientTel.isSelected()) {
            editClientTelDate.setText(now());
        } else {
            editClientTelDate.setText(null);
        }
    }

    private void processUserRaports() {
        newUser = null;
        if (usersForManagerTable.getSelectedRowCount() == 1) {
            int selectedRow = usersForManagerTable.getSelectedRow();
            String id = usersForManagerTable.getValueAt(selectedRow, 0).toString();
            newUser = UserDAO.getUserById(valueOf(id));

            txtUserReport.setText(newUser.getName() + " " + newUser.getSurname());

            jDialog2.dispose();
            jDialog2.setVisible(false);
            jDialog2.setAlwaysOnTop(false);
        } else {
            showMessageDialog(this,
                    "Wybierz pracownika",
                    "Zaznacz wiersz",
                    ERROR_MESSAGE);
        }
    }

    private void processSelectedCreator() {
        newUser = null;
        if (usersForManagerTable.getSelectedRowCount() == 1) {
            String id = usersForManagerTable.getValueAt(usersForManagerTable.getSelectedRow(), 0).toString();
            newUser = UserDAO.getUserById(valueOf(id));
            String newName = newUser.getName() + " " + newUser.getSurname().trim();
            if (!editClientCreator.getText().trim().equalsIgnoreCase(newName)) {
                editClientCreator.setText(newUser.getName() + " " + newUser.getSurname());
            }

            jDialog2.dispose();
            jDialog2.setVisible(false);
            jDialog2.setAlwaysOnTop(false);
        } else {
            showMessageDialog(this, "Wybierz pracownika", "Zaznacz wiersz", ERROR_MESSAGE);
        }
    }

    private void jButton2ActionPerformed(ActionEvent event) {
        if (isReport) {
            processUserRaports();
        } else {
            processSelectedCreator();
        }
    }

    private void jButton3ActionPerformed(ActionEvent event) {
        jDialog2.dispose();
        jDialog2.setVisible(false);
        jDialog2.setAlwaysOnTop(false);
    }

    private void selectUserButtonActionPerformed(ActionEvent event) {
        newUser = null;
        isReport = false;
        TableUtil.displayUsersForManager(UserDAO.searchUsers(), usersForManagerTable);
        jDialog2.setVisible(true);
        jDialog2.setAlwaysOnTop(true);
    }

    private void usersForManagerTableMouseClicked(MouseEvent mouseEvent) {
        usersForManagerTable.addMouseListener((MouseClick) event -> {
            if (event.getClickCount() == 2) {
                if (isReport) {
                    processUserRaports();
                } else {
                    processSelectedCreator();
                }
            }
        });
    }

    private void reportsButtonActionPerformed(ActionEvent event) {
        PanelsUtil.enablePanel(reportsByManagerPanel, new JPanel[]{editUserByManagerPanel, searchUserByManagerPanel, mainManagerPanel});
    }

    private void jButton4ActionPerformed(ActionEvent event) {
        int choose = cbReportDate.getSelectedIndex();

        ReportsUtil.createTelephonesReport(choose);
    }

    private void jButton6ActionPerformed(ActionEvent event) {
        newUser = null;
        isReport = true;
        TableUtil.displayUsersForManager(UserDAO.searchUsers(), usersForManagerTable);
        jDialog2.setVisible(true);
        jDialog2.setAlwaysOnTop(true);
    }

    private void jButton7ActionPerformed(ActionEvent event) {
        if (txtUserReport.getText().isEmpty()) {
            showMessageDialog(this,
                    "Wybierz pracownika",
                    "Błąd",
                    ERROR_MESSAGE);
            return;
        }
        ReportsUtil.createUserReport(newUser);
    }

    private void dataExpButton2ActionPerformed(ActionEvent event) {
        // TODO add your handling code here:
    }

    private void dataExpButton1ActionPerformed(ActionEvent event) {
        // TODO add your handling code here:
    }

    private void editClientForManager(int row) {
        Object selectedClientId = tableClientsForManager.getModel().getValueAt(row, 0);
        selectedClient = ClientDAO.getClientById((Integer) selectedClientId);
        PanelsUtil.enablePanel(editUserByManagerPanel, new JPanel[]{mainManagerPanel, searchUserByManagerPanel, reportsByManagerPanel});

        editClientName.setText(selectedClient.getName());
        editClientSurname.setText(selectedClient.getSurname());
        editClientStreet.setText(selectedClient.getAddress().getStreet());
        editClientNumber.setText(selectedClient.getAddress().getNumber());
        editClientCity.setText(selectedClient.getAddress().getCity());
        editClientPostalCode.setText(selectedClient.getAddress().getPostCode());
        editClientCreator.setText(selectedClient.getUser().getName() + " " + selectedClient.getUser().getSurname());
        editClientPesel.setText(selectedClient.getPesel());
        editClientPhone.setText(selectedClient.getPhone1());
        editClientPhone2.setText(selectedClient.getPhone2());
        editClientMail.setText(selectedClient.getMail());
        editClientDesc.setText(selectedClient.getDescription());
        editClientVip.setSelected(selectedClient.getVip());
        editClientCreateDate.setText(selectedClient.getCreated());
        editClientModification.setText(selectedClient.getModified());
        editClientTel.setSelected(selectedClient.getTel().equalsIgnoreCase("T"));
        editClientTelDate.setEnabled(false);
        editClientTelDate.setText(selectedClient.getTelDateAsString());
        editClientModification.setText(selectedClient.getTelDateAsString());

        if (!selectedClient.getProducts().isEmpty() && selectedClient.getProducts() != null) {
            String[] products = selectedClient.getProducts().split(",");
            List<String> productsSet = Arrays.asList(products);
            if (productsSet.contains("Konto osobiste")) {
                cbEditPersonalAcc.setSelected(true);
            }
            if (productsSet.contains("Konto walutowe")) {
                cbEditCurrencyAcc.setSelected(true);
            }
            if (productsSet.contains("Lokata")) {
                cbEditLocate.setSelected(true);
            }
            if (productsSet.contains("Kredyt gotówkowy")) {
                cbEditCurrentCredit.setSelected(true);
            }
            if (productsSet.contains("Kredyt hipoteczny")) {
                cbEditHomeCredit.setSelected(true);
            }
            if (productsSet.contains("Kredyt odnawialny")) {
                cbEditRepeatedCredit.setSelected(true);
            }
            if (productsSet.contains("Karta kredytowa")) {
                cbEditCreditCard.setSelected(true);
            }
        } else {
            cbEditPersonalAcc.setSelected(false);
            cbEditCurrencyAcc.setSelected(false);
            cbEditLocate.setSelected(false);
            cbEditCurrentCredit.setSelected(false);
            cbEditHomeCredit.setSelected(false);
            cbEditRepeatedCredit.setSelected(false);
            cbEditCreditCard.setSelected(false);
        }

        cbEditChance.setSelectedItem(selectedClient.getSellChance());
    }

    private boolean validateUser() {
        Map<String, String> fieldsMap = new HashMap<>();

        fieldsMap.put(txtAddUserPassword2.getText(), "Powtóż hasło użytkownika");
        fieldsMap.put(txtAddUserPassword1.getText(), "Wprowadź hasło użytkownika");
        fieldsMap.put(txtAddUserLogin.getText(), "Wprowadź login użytkownika");
        fieldsMap.put(txtAddUserSurname.getText(), "Wprowadź nazwisko uzytkownika");
        fieldsMap.put(txtAddUserName.getText(), "Wprowadź imię użytkownika");

        String result = ValidationUtil.validateNotNull(fieldsMap);
        if (result == null) {
            if (ValidationUtil.validatePassword(txtAddUserPassword1.getText())) {
                if (ValidationUtil.validateNotEqual(txtAddUserPassword2.getText(), txtAddUserPassword1.getText())) {
                    return true;
                }
                showMessageDialog(this, "Hasła muszę być takie same",
                        "Błąd dodawania użytkownika", ERROR_MESSAGE);
                return false;

            }
            showMessageDialog(this, "Hasła musi posiadadać od 4 do 10 znaków.",
                    "Błąd dodawania użytkownika", ERROR_MESSAGE);
            return false;
        }
        showMessageDialog(this, result, "Błąd dodawania użytkownika", ERROR_MESSAGE);
        return false;
    }

    private void processMailPanel(String recipients) {
        PanelsUtil.enablePanel(sendMailPanel, new JPanel[]{mainUserPanel, addClientPanel, searchPanel});
        txtRecipient.setText(recipients);

        txtMailContent.setText("");
        txtMailSubject.setText("");
    }

    private void editClientAction(int row) {
        saveClientButton.setEnabled(false);
        editClientButton.setVisible(true);
        editClientButton.setEnabled(true);
        Object selectedClientId = tableClients.getModel().getValueAt(row, 0);

        selectedClient = ClientDAO.getClientById((Integer) selectedClientId);

        PanelsUtil.enablePanel(addClientPanel, new JPanel[]{mainUserPanel, searchPanel, sendMailPanel});

        txtClientName.setText(selectedClient.getName());
        txtClientName.setEnabled(false);
        txtClientSurname.setText(selectedClient.getSurname());
        txtClientSurname.setEnabled(false);
        txtClientStreet.setText(selectedClient.getAddress().getStreet());
        txtClientStreet.setEnabled(false);
        txtClientNumber.setText(selectedClient.getAddress().getNumber());
        txtClientNumber.setEnabled(false);
        txtClientCity.setText(selectedClient.getAddress().getCity());
        txtClientCity.setEnabled(false);
        txtClientPostalCode.setText(selectedClient.getAddress().getPostCode());
        txtClientPostalCode.setEnabled(false);
        txtClientCreator.setText(selectedClient.getUser().getName() + " " + selectedClient.getUser().getSurname());
        txtClientCreator.setEnabled(false);
        txtClientPesel.setText(selectedClient.getPesel());
        txtClientPesel.setEnabled(false);
        txtClientPhone1.setText(selectedClient.getPhone1());
        txtClientPhone1.setEnabled(false);
        txtClientPhone2.setText(selectedClient.getPhone2());
        txtClientPhone2.setEnabled(false);
        txtClientMail.setText(selectedClient.getMail());
        txtClientMail.setEnabled(false);
        txtClientDesc.setText(selectedClient.getDescription());
        txtClientDesc.setEnabled(false);
        txtClientVip.setSelected(selectedClient.getVip());
        txtClientVip.setEnabled(false);
        txtClientCreateDate.setText(selectedClient.getCreated());
        txtClientModification.setText(selectedClient.getModified());
        if ("T".equalsIgnoreCase(selectedClient.getTel())) {
            txtClientTel.setSelected(true);
        } else {
            txtClientTel.setSelected(false);
        }

        txtClientModification.setText(Objects.toString(selectedClient.getTelDateAsString()));

        if (!selectedClient.getProducts().isEmpty() && selectedClient.getProducts() != null) {
            String[] products = selectedClient.getProducts().split(",");
            List<String> productsSet = Arrays.asList(products);
            if (productsSet.contains("Konto osobiste")) {
                cbPersonalAcc.setSelected(true);
            }
            if (productsSet.contains("Konto walutowe")) {
                cbCurrencyAcc.setSelected(true);
            }
            if (productsSet.contains("Lokata")) {
                cbLocate.setSelected(true);
            }
            if (productsSet.contains("Kredyt gotówkowy")) {
                cbCurrentCredit.setSelected(true);
            }
            if (productsSet.contains("Kredyt hipoteczny")) {
                cbHomeCredit.setSelected(true);
            }
            if (productsSet.contains("Kredyt odnawialny")) {
                cbRepeatedCredit.setSelected(true);
            }
            if (productsSet.contains("Karta kredytowa")) {
                cbCreditCard.setSelected(true);
            }
        } else {
            cbPersonalAcc.setSelected(false);
            cbCurrencyAcc.setSelected(false);
            cbLocate.setSelected(false);
            cbCurrentCredit.setSelected(false);
            cbHomeCredit.setSelected(false);
            cbRepeatedCredit.setSelected(false);
            cbCreditCard.setSelected(false);
        }
        cbPersonalAcc.setEnabled(false);
        cbCurrencyAcc.setEnabled(false);
        cbLocate.setEnabled(false);
        cbCurrentCredit.setEnabled(false);
        cbHomeCredit.setEnabled(false);
        cbRepeatedCredit.setEnabled(false);
        cbCreditCard.setEnabled(false);
        txtClientTel.setEnabled(false);

        cbChance.setSelectedItem(selectedClient.getSellChance());
        cbChance.setEnabled(false);
    }

    private void processLoggedUserPanel() {
        switch (loggedUser.getType()) {
            case ADMIN:
                processAdminPanel();
                break;
            case MANAGER:
                processManagerPanel();
                break;
            case USER:
                processUserPanel();
                break;
        }
    }

    private void processManagerPanel() {
        PanelsUtil.enablePanel(managerPanel, new JPanel[]{loginPanel, userPanel, adminPanel});

        PanelsUtil.enablePanel(mainManagerPanel, new JPanel[]{searchUserByManagerPanel, editUserByManagerPanel, reportsByManagerPanel});
        txtManagerName.setText(loggedUser.getName());
        txtManagerSurname.setText(loggedUser.getSurname());
        txtManagerLogin.setText(loggedUser.getLogin());
        txtManagerType.setText(loggedUser.getType().getTitle());
    }

    private void processAdminPanel() {
        PanelsUtil.enablePanel(adminPanel, new JPanel[]{loginPanel, userPanel, managerPanel});

        PanelsUtil.enablePanel(mainAdminPanel, new JPanel[]{addUserPanel, searchUserPanel});

        txtAdminName.setText(loggedUser.getName());
        txtAdminSurname.setText(loggedUser.getSurname());
        txtAdminLogin.setText(loggedUser.getLogin());
        txtAdminType.setText(loggedUser.getType().getTitle());
    }

    private void processUserPanel() {
        PanelsUtil.enablePanel(userPanel, new JPanel[]{loginPanel, adminPanel, managerPanel});

        PanelsUtil.enablePanel(mainUserPanel, new JPanel[]{addClientPanel, searchPanel, sendMailPanel});

        txtUserName.setText(loggedUser.getName());
        txtUserSurname.setText(loggedUser.getSurname());
        txtUserLogin.setText(loggedUser.getLogin());
        txtUserType.setText(loggedUser.getType().getTitle());
    }

    private JPanel addClientPanel = new JPanel();
    private JPanel addUserPanel = new JPanel();
    private JPanel adminPanel = new JPanel();
    private JPanel editUserByManagerPanel = new JPanel();
    private JPanel leftManagerPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel leftPanel1 = new JPanel();
    private JPanel loginPanel = new JPanel();
    private JPanel mainAdminPanel = new JPanel();
    private JPanel mainManagerPanel = new JPanel();
    private JPanel mainUserPanel = new JPanel();
    private JPanel managerPanel = new JPanel();
    private JPanel searchPanel = new JPanel();
    private JPanel searchUserByManagerPanel = new JPanel();
    private JPanel searchUserPanel = new JPanel();
    private JPanel sendMailPanel = new JPanel();
    private JPanel topManagerPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel topPanel1 = new JPanel();
    private JPanel userPanel = new JPanel();
    private JButton addClientButton = new JButton();
    private JButton addUserButton = new JButton();
    private JButton adminDetailsButton = new JButton();
    private JButton adminResetButton = new JButton();
    private JButton adminSearchButton = new JButton();
    private JButton cancelChangePasswordButton = new JButton();
    private JButton cancelClientButton = new JButton();
    private JButton cancelClientByManagerButton = new JButton();
    private JButton cancelUserButton = new JButton();
    private JCheckBox cbCreditCard = new JCheckBox();
    private JCheckBox cbCurrentCredit = new JCheckBox();
    private JCheckBox cbCurrencyAcc = new JCheckBox();
    private JCheckBox cbEditCreditCard = new JCheckBox();
    private JCheckBox cbEditCurrentCredit = new JCheckBox();
    private JCheckBox cbEditCurrencyAcc = new JCheckBox();
    private JCheckBox cbEditHomeCredit = new JCheckBox();
    private JCheckBox cbEditLocate = new JCheckBox();
    private JCheckBox cbEditPersonalAcc = new JCheckBox();
    private JCheckBox cbEditRepeatedCredit = new JCheckBox();
    private JCheckBox cbHomeCredit = new JCheckBox();
    private JCheckBox cbLocate = new JCheckBox();
    private JCheckBox cbPersonalAcc = new JCheckBox();
    private JCheckBox cbRepeatedCredit = new JCheckBox();
    private JComboBox<String> cbChance = new JComboBox<>();
    private JComboBox<String> cbEditChance = new JComboBox<>();
    private JComboBox<String> cbReportDate = new JComboBox<>();
    private JButton changeAdminPassButton = new JButton();
    private JButton changeManagerPassButton = new JButton();
    private JButton changePasswordButton = new JButton();
    private JButton clearMailButton = new JButton();
    private JButton dataExpButton1 = new JButton();
    private JButton dataExpButton2 = new JButton();
    private JTextField editClientCity = new JTextField();
    private JButton editClientButton = new JButton();
    private JTextField editClientCreateDate = new JTextField();
    private JTextField editClientCreator = new JTextField();
    private JTextArea editClientDesc = new JTextArea();
    private JTextField editClientMail = new JTextField();
    private JTextField editClientModification = new JTextField();
    private JTextField editClientName = new JTextField();
    private JTextField editClientNumber = new JTextField();
    private JTextField editClientPesel = new JTextField();
    private JTextField editClientPhone = new JTextField();
    private JTextField editClientPhone2 = new JTextField();
    private JFormattedTextField editClientPostalCode = new JFormattedTextField();
    private JTextField editClientStreet = new JTextField();
    private JTextField editClientSurname = new JTextField();
    private JCheckBox editClientTel = new JCheckBox();
    private JTextField editClientTelDate = new JTextField();
    private JCheckBox editClientVip = new JCheckBox();
    private JButton exitAdminButton = new JButton();
    private JButton exitButton = new JButton();
    private JButton exitManagerButton = new JButton();
    private JButton exportUserDataButton = new JButton();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();
    private JButton jButton4 = new JButton();
    private JButton jButton5 = new JButton();
    private JButton jButton6 = new JButton();
    private JButton jButton7 = new JButton();
    private JDialog jDialog1 = new JDialog();
    private JDialog jDialog2 = new JDialog();
    private JLabel jLabel1 = new JLabel();
    private JLabel mailLabel = new JLabel();
    private JLabel additionalsLabel = new JLabel();
    private JLabel ownerLabel = new JLabel();
    private JLabel createDateLabel = new JLabel();
    private JLabel sellChanceLabel = new JLabel();
    private JLabel streetLabel = new JLabel();
    private JLabel buildingNoLabel = new JLabel();
    private JLabel cityLabel = new JLabel();
    private JLabel postalLabel = new JLabel();
    private JLabel productsLabel = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel20 = new JLabel();
    private JLabel jLabel21 = new JLabel();
    private JLabel jLabel22 = new JLabel();
    private JLabel jLabel23 = new JLabel();
    private JLabel jLabel24 = new JLabel();
    private JLabel jLabel25 = new JLabel();
    private JLabel jLabel26 = new JLabel();
    private JLabel jLabel27 = new JLabel();
    private JLabel jLabel28 = new JLabel();
    private JLabel jLabel29 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel30 = new JLabel();
    private JLabel jLabel31 = new JLabel();
    private JLabel jLabel32 = new JLabel();
    private JLabel jLabel33 = new JLabel();
    private JLabel jLabel34 = new JLabel();
    private JLabel jLabel35 = new JLabel();
    private JLabel jLabel36 = new JLabel();
    private JLabel jLabel37 = new JLabel();
    private JLabel jLabel38 = new JLabel();
    private JLabel jLabel39 = new JLabel();
    private JLabel nameLabel = new JLabel();
    private JLabel jLabel40 = new JLabel();
    private JLabel jLabel41 = new JLabel();
    private JLabel jLabel42 = new JLabel();
    private JLabel jLabel43 = new JLabel();
    private JLabel phoneDateLabel = new JLabel();
    private JLabel jLabel45 = new JLabel();
    private JLabel jLabel46 = new JLabel();
    private JLabel jLabel47 = new JLabel();
    private JLabel jLabel48 = new JLabel();
    private JLabel jLabel49 = new JLabel();
    private JLabel surnameLabel = new JLabel();
    private JLabel jLabel50 = new JLabel();
    private JLabel jLabel51 = new JLabel();
    private JLabel jLabel52 = new JLabel();
    private JLabel jLabel53 = new JLabel();
    private JLabel jLabel54 = new JLabel();
    private JLabel jLabel55 = new JLabel();
    private JLabel jLabel56 = new JLabel();
    private JLabel jLabel57 = new JLabel();
    private JLabel jLabel58 = new JLabel();
    private JLabel jLabel59 = new JLabel();
    private JLabel peselLabel = new JLabel();
    private JLabel jLabel60 = new JLabel();
    private JLabel jLabel61 = new JLabel();
    private JLabel jLabel66 = new JLabel();
    private JLabel jLabel67 = new JLabel();
    private JLabel jLabel68 = new JLabel();
    private JLabel jLabel69 = new JLabel();
    private JLabel phone1Label = new JLabel();
    private JLabel jLabel70 = new JLabel();
    private JLabel jLabel71 = new JLabel();
    private JLabel jLabel72 = new JLabel();
    private JLabel jLabel73 = new JLabel();
    private JLabel jLabel74 = new JLabel();
    private JLabel jLabel75 = new JLabel();
    private JLabel jLabel76 = new JLabel();
    private JLabel jLabel77 = new JLabel();
    private JLabel jLabel78 = new JLabel();
    private JLabel jLabel79 = new JLabel();
    private JLabel phone2Label = new JLabel();
    private JLabel jLabel80 = new JLabel();
    private JLabel jLabel81 = new JLabel();
    private JLabel jLabel82 = new JLabel();
    private JLabel jLabel83 = new JLabel();
    private JLabel jLabel84 = new JLabel();
    private JLabel jLabel85 = new JLabel();
    private JLabel jLabel86 = new JLabel();
    private JLabel clientVipLabel = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JScrollPane jScrollPane5 = new JScrollPane();
    private JScrollPane jScrollPane6 = new JScrollPane();
    private JScrollPane jScrollPane7 = new JScrollPane();
    private JScrollPane jScrollPane8 = new JScrollPane();
    private JScrollPane jScrollPane9 = new JScrollPane();
    private JLabel clientPhoneLabel = new JLabel();
    private JLabel lab1 = new JLabel();
    private JLabel labelClientModDate = new JLabel();
    private JLabel labelClientModDate1 = new JLabel();
    private JButton loginButton = new JButton();
    private JButton logoutAdminButton = new JButton();
    private JButton logoutButton = new JButton();
    private JButton logoutManagerButton = new JButton();
    private JButton mailButton = new JButton();
    private JButton mailMergeButton = new JButton();
    private JButton managerEditButton = new JButton();
    private JButton managerResetButton = new JButton();
    private JButton managerSearchButton = new JButton();
    private JButton myAdminPanelButton = new JButton();
    private JButton myContactsButton = new JButton();
    private JButton myManagerPanelButton = new JButton();
    private JButton myPanelButton = new JButton();
    private JPanel reportsByManagerPanel = new JPanel();
    private JButton reportsButton = new JButton();
    private JButton resetSearchButton = new JButton();
    private JButton saveClientButton = new JButton();
    private JButton saveClientByManagerButton = new JButton();
    private JButton saveUserButton = new JButton();
    private JButton searchButton = new JButton();
    private JButton searchClientByManagerButton = new JButton();
    private JButton searchSearchButton = new JButton();
    private JButton searchUserButton = new JButton();
    private JButton selectUserButton = new JButton();
    private JButton sendMailButton = new JButton();
    private JButton sendOneMailButton = new JButton();
    private JTable tableClients = new JTable();
    private JTable tableClientsForManager = new JTable();
    private JTable tableUsers = new JTable();
    private JTextField txtAddUserLogin = new JTextField();
    private JTextField txtAddUserName = new JTextField();
    private JPasswordField txtAddUserPassword1 = new JPasswordField();
    private JPasswordField txtAddUserPassword2 = new JPasswordField();
    private JTextField txtAddUserSurname = new JTextField();
    private JTextField txtAddUserDate = new JTextField();
    private JComboBox<UserType> txtAddUserType = new JComboBox<>();
    private JTextField txtAdminLogin = new JTextField();
    private JTextField txtAdminName = new JTextField();
    private JTextField txtAdminSearchName = new JTextField();
    private JTextField txtAdminSearchSurname = new JTextField();
    private JTextField txtAdminSurname = new JTextField();
    private JTextField txtAdminType = new JTextField();
    private JTextField txtClientCity = new JTextField();
    private JPasswordField txtChangePass1 = new JPasswordField();
    private JPasswordField txtChangePass2 = new JPasswordField();
    private JTextField txtClientCreateDate = new JTextField();
    private JTextField txtClientCreator = new JTextField();
    private JTextArea txtClientDesc = new JTextArea();
    private JTextField txtClientMail = new JTextField();
    private JTextField txtClientModification = new JTextField();
    private JTextField txtClientName = new JTextField();
    private JTextField txtClientNumber = new JTextField();
    private JTextField txtClientPesel = new JTextField();
    private JTextField txtClientPhone1 = new JTextField();
    private JTextField txtClientPhone2 = new JTextField();
    private JFormattedTextField txtClientPostalCode = new JFormattedTextField();
    private JTextField txtClientStreet = new JTextField();
    private JTextField txtClientSurname = new JTextField();
    private JCheckBox txtClientTel = new JCheckBox();
    private JTextField txtClientTelDate = new JTextField();
    private JCheckBox txtClientVip = new JCheckBox();
    private JButton txtDetailsClientButton = new JButton();
    private JTextField txtLogin = new JTextField();
    private JTextArea txtMailContent = new JTextArea();
    private JTextField txtMailSubject = new JTextField();
    private JTextField txtManagerLogin = new JTextField();
    private JTextField txtManagerName = new JTextField();
    private JTextField txtManagerSearchPesel = new JTextField();
    private JTextField txtManagerSearchSurname = new JTextField();
    private JTextField txtManagerSurname = new JTextField();
    private JTextField txtManagerType = new JTextField();
    private JPasswordField txtPassword = new JPasswordField();
    private JTextArea txtRecipient = new JTextArea();
    private JTextField txtSearchPesel = new JTextField();
    private JTextField txtSearchSurname = new JTextField();
    private JButton txtSendMultipleMail = new JButton();
    private JTextField txtUserLogin = new JTextField();
    private JTextField txtUserName = new JTextField();
    private JTextField txtUserReport = new JTextField();
    private JTextField txtUserSurname = new JTextField();
    private JTextField txtUserType = new JTextField();
    private JTable usersForManagerTable = new JTable();
}
