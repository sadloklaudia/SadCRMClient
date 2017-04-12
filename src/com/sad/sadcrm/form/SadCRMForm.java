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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.sad.sadcrm.Application.VERSION;
import static com.sad.sadcrm.model.UserType.ADMIN;
import static com.sad.sadcrm.model.UserType.MANAGER;
import static java.awt.Color.lightGray;
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import static java.lang.String.format;
import static javax.swing.BorderFactory.createEtchedBorder;
import static javax.swing.BorderFactory.createLineBorder;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import static javax.swing.JOptionPane.*;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;
import static javax.swing.LayoutStyle.ComponentPlacement.UNRELATED;
import static javax.swing.SwingConstants.HORIZONTAL;

public class SadCRMForm extends ApplicationWindow {
    private Object[] options = {"Tak", "Nie"};

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
        // MENU DLA BUTTONA EXPORTU
        exportAdminDataMenu.add(exportAdminDataSubmenu1);
        exportAdminDataMenu.add(exportAdminDataSubmenu2);

        exportUserDataMenu.add(exportUserDataSubmenu1);

        exportAdminDataSubmenu1.addActionListener(event -> ReportsUtil.exportAll());
        exportAdminDataSubmenu2.addActionListener(event -> ReportsUtil.exportTodays(now()));
        dataExpButton1.addActionListener(this::showPopupAdmin);
        dataExpButton2.addActionListener(this::showPopupAdmin);

        exportUserDataSubmenu1.addActionListener(e -> ReportsUtil.exportMy(loggedUser));

        exportUserDataButton.addActionListener(this::showPopupUser);
    }

    private void showPopupAdmin(ActionEvent ae) {
        Component b = (Component) ae.getSource();
        Point p = b.getLocationOnScreen();
        exportAdminDataMenu.show(this, 0, 0);
        exportAdminDataMenu.setLocation(p.x, p.y + b.getHeight());
    }

    private void showPopupUser(ActionEvent ae) {
        Component b = (Component) ae.getSource();
        Point p = b.getLocationOnScreen();
        exportUserDataMenu.show(this, 0, 0);
        exportUserDataMenu.setLocation(p.x, p.y + b.getHeight());
    }

    private void initComponents() {
        changePasswordButton.setIcon(new ImageIcon(getClass().getResource("/icons/Apply.gif")));
        changePasswordButton.setText("OK");
        changePasswordButton.addActionListener(this::changePasswordButtonActionPerformed);

        cancelChangePasswordButton.setIcon(new ImageIcon(getClass().getResource("/icons/Cancel.gif")));
        cancelChangePasswordButton.setText("Anuluj");
        cancelChangePasswordButton.addActionListener(this::cancelChangePasswordButtonActionPerformed);

        jLabel46.setText("Hasło:");
        jLabel47.setText("Powtórz hasło");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
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
                                                        .addComponent(txtChangePass2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))))
                                .addContainerGap(42, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
                jDialog1Layout.createParallelGroup(LEADING)
                        .addGroup(TRAILING, jDialog1Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(jDialog1Layout.createParallelGroup(TRAILING)
                                        .addComponent(jLabel46)
                                        .addComponent(txtChangePass1, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel47)
                                        .addComponent(txtChangePass2, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        usersForManagerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        usersForManagerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersForManagerTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(usersForManagerTable);

        jButton2.setIcon(new ImageIcon(getClass().getResource("/icons/Apply.gif")));
        jButton2.setText("Wybierz");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setIcon(new ImageIcon(getClass().getResource("/icons/Cancel.gif")));
        jButton3.setText("Zamknij");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
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
                                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))
                                .addContainerGap())
        );

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        userPanel.setBackground(lightGray);
        userPanel.setBorder(createLineBorder(new java.awt.Color(0, 0, 0)));
        userPanel.setPreferredSize(new Dimension(1000, 800));

        topPanel.setBackground(lightGray);
        topPanel.setBorder(createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        topPanel.setForeground(new java.awt.Color(238, 9, 9));
        topPanel.setPreferredSize(new Dimension(1000, 50));

        mailButton.setIcon(new ImageIcon(getClass().getResource("/icons/E-mail.gif")));
        mailButton.setText("Wyślij wiadomość");
        mailButton.addActionListener(this::wyslijMailAction);

        exportUserDataButton.setIcon(new ImageIcon(getClass().getResource("/icons/Upload.gif")));
        exportUserDataButton.setText("Eksport danych");

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
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
                                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(mailButton)
                                        .addComponent(exportUserDataButton))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        leftPanel.setBackground(lightGray);
        leftPanel.setBorder(createEtchedBorder());
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(200, 750));

        addClientButton.setIcon(new ImageIcon(getClass().getResource("/icons/Create.gif")));
        addClientButton.setText("Dodaj klienta");
        addClientButton.addActionListener(this::dodajKlientaAction);

        searchButton.setIcon(new ImageIcon(getClass().getResource("/icons/Find.gif")));
        searchButton.setText("Wyszukiwanie");
        searchButton.addActionListener(this::wyszukiwanieAction);

        mailMergeButton.setIcon(new ImageIcon(getClass().getResource("/icons/Mail.gif")));
        mailMergeButton.setText("Korespondencja seryjna");
        mailMergeButton.addActionListener(this::korespondencjaSeryjnaAction);

        myContactsButton.setIcon(new ImageIcon(getClass().getResource("/icons/Address book.gif")));
        myContactsButton.setText("Tylko moje kontakty");
        myContactsButton.addActionListener(this::mojeKontaktyAction);

        myPanelButton.setIcon(new ImageIcon(getClass().getResource("/icons/Info.gif")));
        myPanelButton.setText("Mój panel");
        myPanelButton.addActionListener(this::mojPanelAction);

        logoutButton.setIcon(new ImageIcon(getClass().getResource("/icons/Exit.gif")));
        logoutButton.setText("Wyloguj");
        logoutButton.addActionListener(this::wylogujUserAction);

        exitButton.setIcon(new ImageIcon(getClass().getResource("/icons/Turn off.gif")));
        exitButton.setText("Wyjście");
        exitButton.addActionListener(this::exitUserAction);

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
                leftPanelLayout.createParallelGroup(LEADING)
                        .addComponent(addClientButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mailMergeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myContactsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myPanelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        mainUserPanel.setBorder(createLineBorder(new java.awt.Color(0, 0, 0)));
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

        jButton1.setIcon(new ImageIcon(getClass().getResource("/icons/Repair.gif")));
        jButton1.setText("Zmień hasło");
        jButton1.addActionListener(this::zmienHasloUzytkAction);

        javax.swing.GroupLayout mainUserPanelLayout = new javax.swing.GroupLayout(mainUserPanel);
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
                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel25)
                                        .addComponent(txtUserName, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel26)
                                        .addComponent(txtUserSurname, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel27)
                                        .addComponent(txtUserType, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel28)
                                        .addComponent(txtUserLogin, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addContainerGap(499, Short.MAX_VALUE))
        );

        addClientPanel.setBorder(null);
        addClientPanel.setPreferredSize(new Dimension(800, 750));

        jLabel2.setFont(timesNewRomanBold24);
        jLabel2.setText("Dodanie nowego klienta");

        jLabel4.setFont(timesNewRoman20);
        jLabel4.setText("Imię*:");

        jLabel5.setFont(timesNewRoman20);
        jLabel5.setText("Nazwisko*:");

        jLabel6.setFont(timesNewRoman20);
        jLabel6.setText("Pesel*");

        jLabel7.setFont(timesNewRoman20);
        jLabel7.setText("Nr telefonu 1");

        jLabel8.setFont(timesNewRoman20);
        jLabel8.setText("Nr telefonu 2");

        jLabel9.setFont(timesNewRoman20);
        jLabel9.setText("Klient VIP");

        jLabel10.setFont(timesNewRoman20);
        jLabel10.setText("Adres mail");

        jLabel11.setFont(timesNewRoman20);
        jLabel11.setText("Uwagi");

        jLabel12.setFont(timesNewRoman20);
        jLabel12.setText("Utworzony przez");

        jLabel13.setFont(timesNewRoman20);
        jLabel13.setText("Data utworzenia");

        jLabel15.setFont(timesNewRoman20);
        jLabel15.setText("Ulica*");

        jLabel16.setFont(timesNewRoman20);
        jLabel16.setText("Nr budynku*");

        jLabel17.setFont(timesNewRoman20);
        jLabel17.setText("Miasto*");

        jLabel18.setFont(timesNewRoman20);
        jLabel18.setText("Kod pocztowy*");

        jLabel19.setFont(timesNewRoman20);
        jLabel19.setText("Produkty");

        txtClientDesc.setColumns(20);
        txtClientDesc.setRows(5);
        jScrollPane1.setViewportView(txtClientDesc);

        txtClientCreator.setEnabled(false);

        txtClientCreateDate.setEnabled(false);

        saveClientButton.setIcon(new ImageIcon(getClass().getResource("/icons/Save.gif")));
        saveClientButton.setText("Zapisz");
        saveClientButton.addActionListener(this::zapiszKlientaAction);

        cancelClientButton.setIcon(new ImageIcon(getClass().getResource("/icons/Cancel.gif")));
        cancelClientButton.setText("Anuluj");
        cancelClientButton.addActionListener(this::anulujZapisKlientaAction);

        cbPersonalAcc.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbPersonalAcc.setText("Konto osobiste");

        cbCurrencyAcc.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbCurrencyAcc.setText("Konto walutowe");

        cbLocate.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbLocate.setText("Lokata");

        cbCurrentCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbCurrentCredit.setText("Kredyt gotówkowy");

        cbHomeCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbHomeCredit.setText("Kredyt hipoteczny");

        cbRepeatedCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbRepeatedCredit.setText("Kredyt odnawialny");

        cbCreditCard.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbCreditCard.setText("Karta kredytowa");

        cbChance.setModel(new DefaultComboBoxModel<>(new String[]{"Wybierz", "Konto osobiste", "Konto walutowe", "Lokata", "Kredyt gotówkowy", "Kredyt hipoteczny", "Kredyt odnawialny", "Karta kredytowa"}));

        jLabel14.setFont(timesNewRoman20);
        jLabel14.setText("Szansa sprzedaży");

        editClientButton.setIcon(new ImageIcon(getClass().getResource("/icons/Repair.gif")));
        editClientButton.setText("Edytuj");
        editClientButton.addActionListener(this::edytujKlientaAction);

        try {
            txtClientPostalCode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelClientModDate.setFont(timesNewRoman20);
        labelClientModDate.setText("Data modyfikacji");

        txtClientModification.setEditable(false);
        txtClientModification.setEnabled(false);

        lab.setFont(timesNewRoman20);
        lab.setText("Telefon do klienta");

        jLabel44.setFont(timesNewRoman20);
        jLabel44.setText("Data telefonu");

        txtClientTel.setEnabled(false);
        txtClientTel.addActionListener(this::txtClientTelActionPerformed);

        txtClientTelDate.setEnabled(false);

        javax.swing.GroupLayout addClientPanelLayout = new javax.swing.GroupLayout(addClientPanel);
        addClientPanel.setLayout(addClientPanelLayout);
        addClientPanelLayout.setHorizontalGroup(
                addClientPanelLayout.createParallelGroup(LEADING)
                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel13)
                                                        .addComponent(jLabel9)
                                                        .addComponent(jLabel12)
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
                                                                .addComponent(txtClientCreator, LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))))
                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel8)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel7)
                                                                .addGap(35, 35, 35)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(txtClientPhone1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                                                        .addComponent(txtClientName)
                                                                        .addComponent(txtClientSurname)
                                                                        .addComponent(txtClientPesel)
                                                                        .addComponent(txtClientPhone2, PREFERRED_SIZE, 134, PREFERRED_SIZE)))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel4)
                                                                        .addComponent(jLabel10)
                                                                        .addComponent(jLabel11))
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
                                                                                .addComponent(jLabel44)
                                                                                .addPreferredGap(RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(txtClientTelDate, PREFERRED_SIZE, 145, PREFERRED_SIZE))
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel14)
                                                                                .addPreferredGap(RELATED)
                                                                                .addComponent(cbChance, PREFERRED_SIZE, 139, PREFERRED_SIZE))
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(lab)
                                                                                .addPreferredGap(RELATED)
                                                                                .addComponent(txtClientTel))))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(14, 14, 14)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel18)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(txtClientPostalCode, PREFERRED_SIZE, 165, PREFERRED_SIZE))
                                                                        .addGroup(addClientPanelLayout.createParallelGroup(TRAILING, false)
                                                                                .addGroup(LEADING, addClientPanelLayout.createSequentialGroup()
                                                                                        .addComponent(jLabel15)
                                                                                        .addPreferredGap(RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(txtClientStreet, PREFERRED_SIZE, 165, PREFERRED_SIZE))
                                                                                .addGroup(LEADING, addClientPanelLayout.createSequentialGroup()
                                                                                        .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                                                .addComponent(jLabel16)
                                                                                                .addComponent(jLabel17)
                                                                                                .addComponent(jLabel19))
                                                                                        .addGap(41, 41, 41)
                                                                                        .addGroup(addClientPanelLayout.createParallelGroup(LEADING, false)
                                                                                                .addComponent(txtClientCity, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
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
                                                .addPreferredGap(RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtClientVip)
                                                .addGap(7, 7, 7)
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel13)
                                                        .addComponent(txtClientCreateDate, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(txtClientTelDate, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(jLabel44))
                                                .addPreferredGap(RELATED)
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel12)
                                                        .addComponent(txtClientCreator, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addPreferredGap(RELATED)
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelClientModDate)
                                                        .addComponent(txtClientModification, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addGap(19, 19, 19))
                                        .addGroup(LEADING, addClientPanelLayout.createSequentialGroup()
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(2, 2, 2)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel15)
                                                                        .addComponent(txtClientStreet, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel16)
                                                                        .addComponent(txtClientNumber, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel17)
                                                                        .addComponent(txtClientCity, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel18)
                                                                        .addComponent(txtClientPostalCode, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addComponent(jLabel19)
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
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel14)
                                                                        .addComponent(cbChance, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel4)
                                                                        .addComponent(txtClientName, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel5)
                                                                        .addComponent(txtClientSurname, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel6)
                                                                        .addComponent(txtClientPesel, TRAILING, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(txtClientPhone1, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(jLabel7))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel8)
                                                                        .addComponent(txtClientPhone2, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(txtClientMail, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(jLabel10))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel11)
                                                                        .addComponent(jScrollPane1, PREFERRED_SIZE, 197, PREFERRED_SIZE))))
                                                .addGroup(addClientPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(11, 11, 11)
                                                                .addComponent(jLabel9))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(TRAILING)
                                                                        .addComponent(txtClientTel)
                                                                        .addComponent(lab))))
                                                .addPreferredGap(RELATED, 125, Short.MAX_VALUE)))
                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        searchSearchButton.setIcon(new ImageIcon(getClass().getResource("/icons/Search.gif")));
        searchSearchButton.setText("Szukaj");
        searchSearchButton.addActionListener(this::szukajKlientaAction);

        resetSearchButton.setIcon(new ImageIcon(getClass().getResource("/icons/Refresh.gif")));
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
        tableClients.setToolTipText("Klikniej dwukrotnie aby edytować");
        tableClients.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableClients.setPreferredSize(new Dimension(780, 100));
        tableClients.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableClients.getTableHeader().setResizingAllowed(false);
        tableClients.getTableHeader().setReorderingAllowed(false);
        tableClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientsMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tableClients);

        txtDetailsClientButton.setIcon(new ImageIcon(getClass().getResource("/icons/About.gif")));
        txtDetailsClientButton.setText("Szczegóły");
        txtDetailsClientButton.addActionListener(this::szczegolyKlientaAction);

        txtSendMultipleMail.setIcon(new ImageIcon(getClass().getResource("/icons/E-mail.gif")));
        txtSendMultipleMail.setText("Wyślij wiadomość do zanaczonych kontaktów");
        txtSendMultipleMail.addActionListener(this::wyslijMailDoWszystkichAction);

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
                searchPanelLayout.createParallelGroup(LEADING)
                        .addGroup(searchPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(searchPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(searchPanelLayout.createSequentialGroup()
                                                .addGroup(searchPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel1)
                                                        .addGroup(searchPanelLayout.createSequentialGroup()
                                                                .addGroup(searchPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel29)
                                                                        .addComponent(jLabel30))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(searchPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(txtSearchSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                                        .addComponent(txtSearchPesel))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(searchPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(searchSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                                        .addComponent(resetSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel29)
                                        .addComponent(txtSearchSurname, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(searchSearchButton))
                                .addGap(4, 4, 4)
                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel30)
                                        .addComponent(txtSearchPesel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(resetSearchButton))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane7, PREFERRED_SIZE, 535, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        sendOneMailButton.setIcon(new ImageIcon(getClass().getResource("/icons/Mail.gif")));
        sendOneMailButton.setText("Wyślij");
        sendOneMailButton.addActionListener(this::wyslijjednegoMailaAction);

        clearMailButton.setIcon(new ImageIcon(getClass().getResource("/icons/Refresh.gif")));
        clearMailButton.setText("Wyczyść");
        clearMailButton.addActionListener(this::wyczyscPolaMailaAction);

        sendMailButton.setIcon(new ImageIcon(getClass().getResource("/icons/Cancel.gif")));
        sendMailButton.setText("Anuluj");
        sendMailButton.addActionListener(this::wyslijMail2Action);

        jLabel36.setFont(timesNewRoman20);
        jLabel36.setText("Temat:");

        javax.swing.GroupLayout sendMailPanelLayout = new javax.swing.GroupLayout(sendMailPanel);
        sendMailPanel.setLayout(sendMailPanelLayout);
        sendMailPanelLayout.setHorizontalGroup(
                sendMailPanelLayout.createParallelGroup(LEADING)
                        .addGroup(sendMailPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(sendMailPanelLayout.createParallelGroup(LEADING, false)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
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
                                .addGroup(sendMailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel36)
                                        .addComponent(txtMailSubject, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(jLabel35)
                                .addPreferredGap(RELATED)
                                .addComponent(jScrollPane6, PREFERRED_SIZE, 233, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addGroup(sendMailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(sendOneMailButton)
                                        .addComponent(clearMailButton)
                                        .addComponent(sendMailButton))
                                .addContainerGap(188, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
                userPanelLayout.createParallelGroup(LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(userPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(topPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(userPanelLayout.createSequentialGroup()
                                                .addComponent(leftPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(mainUserPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(searchPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(addClientPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                .addGap(0, 0, 0))
                        .addGroup(userPanelLayout.createParallelGroup(LEADING)
                                .addGroup(userPanelLayout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(sendMailPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );
        userPanelLayout.setVerticalGroup(
                userPanelLayout.createParallelGroup(LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addComponent(topPanel, PREFERRED_SIZE, 62, PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(userPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(leftPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(addClientPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(mainUserPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(searchPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(0, 0, 0))
                        .addGroup(userPanelLayout.createParallelGroup(LEADING)
                                .addGroup(userPanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(sendMailPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );

        loginPanel.setPreferredSize(new Dimension(800, 600));

        jLabel21.setFont(timesNewRomanBold24);
        jLabel21.setText("LOGOWANIE DO SYSTEMU SadCRM");

        jLabel22.setFont(timesNewRoman20);
        jLabel22.setText("Login");

        jLabel23.setFont(timesNewRoman20);
        jLabel23.setText("Hasło");

        txtLogin.addActionListener(this::txtLoginActionPerformed);

        loginButton.setIcon(new ImageIcon(getClass().getResource("/icons/Blue key.gif")));
        loginButton.setText("Zaloguj");
        loginButton.addActionListener(this::logowanieAction);

        txtPassword.addActionListener(this::txtPasswordActionPerformed);

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
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
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel22)
                                        .addComponent(txtLogin, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel23)
                                        .addComponent(txtPassword, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(UNRELATED)
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginButton))
                                .addGap(158, 158, 158))
        );

        adminPanel.setBackground(lightGray);
        adminPanel.setBorder(createLineBorder(new java.awt.Color(0, 0, 0)));
        adminPanel.setPreferredSize(new Dimension(1000, 800));

        topPanel1.setBackground(lightGray);
        topPanel1.setBorder(createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        topPanel1.setForeground(new java.awt.Color(238, 9, 9));
        topPanel1.setPreferredSize(new Dimension(1000, 50));

        dataExpButton1.setIcon(new ImageIcon(getClass().getResource("/icons/Upload.gif")));
        dataExpButton1.setText("Eksport danych");
        dataExpButton1.addActionListener(this::dataExpButton1ActionPerformed);

        javax.swing.GroupLayout topPanel1Layout = new javax.swing.GroupLayout(topPanel1);
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

        addUserButton.setIcon(new ImageIcon(getClass().getResource("/icons/Create.gif")));
        addUserButton.setText("Dodaj użytkownika");
        addUserButton.addActionListener(this::addUserButtonActionPerformed);

        searchUserButton.setIcon(new ImageIcon(getClass().getResource("/icons/Find.gif")));
        searchUserButton.setText("Wyszukiwanie");
        searchUserButton.addActionListener(this::searchUserButtonActionPerformed);

        myAdminPanelButton.setIcon(new ImageIcon(getClass().getResource("/icons/Info.gif")));
        myAdminPanelButton.setText("Mój panel");
        myAdminPanelButton.addActionListener(this::myAdminPanelButtonActionPerformed);

        logoutAdminButton.setIcon(new ImageIcon(getClass().getResource("/icons/Exit.gif")));
        logoutAdminButton.setText("Wylogowanie");
        logoutAdminButton.addActionListener(this::logoutAdminButtonActionPerformed);

        exitAdminButton.setIcon(new ImageIcon(getClass().getResource("/icons/Turn off.gif")));
        exitAdminButton.setText("Wyjście");
        exitAdminButton.addActionListener(this::exitAdminButtonActionPerformed);

        javax.swing.GroupLayout leftPanel1Layout = new javax.swing.GroupLayout(leftPanel1);
        leftPanel1.setLayout(leftPanel1Layout);
        leftPanel1Layout.setHorizontalGroup(
                leftPanel1Layout.createParallelGroup(LEADING)
                        .addComponent(addUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                        .addComponent(searchUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myAdminPanelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutAdminButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitAdminButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        mainAdminPanel.setBorder(createLineBorder(new java.awt.Color(0, 0, 0)));
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

        changeAdminPassButton.setIcon(new ImageIcon(getClass().getResource("/icons/Repair.gif")));
        changeAdminPassButton.setText("Zmiana hasła");
        changeAdminPassButton.addActionListener(this::changeAdminPassButtonActionPerformed);

        javax.swing.GroupLayout mainAdminPanelLayout = new javax.swing.GroupLayout(mainAdminPanel);
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
                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel31)
                                        .addComponent(txtAdminName, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel33)
                                        .addComponent(txtAdminSurname, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel37)
                                        .addComponent(txtAdminType, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel38)
                                        .addComponent(txtAdminLogin, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
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

        saveUserButton.setIcon(new ImageIcon(getClass().getResource("/icons/Save.gif")));
        saveUserButton.setText("Zapisz");
        saveUserButton.addActionListener(this::saveUserButtonActionPerformed);

        cancelUserButton.setIcon(new ImageIcon(getClass().getResource("/icons/Cancel.gif")));
        cancelUserButton.setText("Anuluj");
        cancelUserButton.addActionListener(this::cancelUserButtonActionPerformed);

        jLabel58.setFont(timesNewRoman20);
        jLabel58.setText("Powtórz hasło*:");

        txtAddUserDate.setEditable(false);
        txtAddUserDate.setEnabled(false);

        jLabel43.setFont(timesNewRoman20);
        jLabel43.setText("Login*:");

        txtAddUserType.setModel(new DefaultComboBoxModel<>(UserType.values()));

        javax.swing.GroupLayout addUserPanelLayout = new javax.swing.GroupLayout(addUserPanel);
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
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel39)
                                        .addComponent(txtAddUserName, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel40)
                                        .addComponent(txtAddUserSurname, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtAddUserLogin, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(jLabel43))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(TRAILING)
                                        .addComponent(jLabel41)
                                        .addComponent(txtAddUserType, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel42)
                                        .addComponent(txtAddUserPassword1, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel58)
                                        .addComponent(txtAddUserPassword2, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel48)
                                        .addComponent(txtAddUserDate, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        adminSearchButton.setIcon(new ImageIcon(getClass().getResource("/icons/Search.gif")));
        adminSearchButton.setText("Szukaj");
        adminSearchButton.addActionListener(this::adminSearchButtonActionPerformed);

        adminResetButton.setIcon(new ImageIcon(getClass().getResource("/icons/Refresh.gif")));
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
        tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUsers.setPreferredSize(new Dimension(780, 100));
        tableUsers.getTableHeader().setResizingAllowed(false);
        tableUsers.getTableHeader().setReorderingAllowed(false);
        tableUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUsersMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tableUsers);

        adminDetailsButton.setIcon(new ImageIcon(getClass().getResource("/icons/Repair.gif")));
        adminDetailsButton.setText("Edycja");
        adminDetailsButton.addActionListener(this::adminDetailsButtonActionPerformed);

        javax.swing.GroupLayout searchUserPanelLayout = new javax.swing.GroupLayout(searchUserPanel);
        searchUserPanel.setLayout(searchUserPanelLayout);
        searchUserPanelLayout.setHorizontalGroup(
                searchUserPanelLayout.createParallelGroup(LEADING)
                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel55)
                                                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel56)
                                                                        .addComponent(jLabel57))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(txtAdminSearchSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                                        .addComponent(txtAdminSearchName))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(searchUserPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(adminSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                                        .addComponent(adminResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                                .addGroup(searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel56)
                                        .addComponent(txtAdminSearchSurname, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(adminSearchButton))
                                .addGap(4, 4, 4)
                                .addGroup(searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel57)
                                        .addComponent(txtAdminSearchName, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(adminResetButton))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane8, PREFERRED_SIZE, 523, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addComponent(adminDetailsButton)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout adminPanelLayout = new javax.swing.GroupLayout(adminPanel);
        adminPanel.setLayout(adminPanelLayout);
        adminPanelLayout.setHorizontalGroup(
                adminPanelLayout.createParallelGroup(LEADING)
                        .addGroup(adminPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(adminPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(topPanel1, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(adminPanelLayout.createSequentialGroup()
                                                .addComponent(leftPanel1, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(mainAdminPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(searchUserPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(addUserPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                .addGap(0, 0, 0))
        );
        adminPanelLayout.setVerticalGroup(
                adminPanelLayout.createParallelGroup(LEADING)
                        .addGroup(adminPanelLayout.createSequentialGroup()
                                .addComponent(topPanel1, PREFERRED_SIZE, 62, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addGroup(adminPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(leftPanel1, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(addUserPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(mainAdminPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(searchUserPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(0, 0, 0))
        );

        managerPanel.setBackground(lightGray);
        managerPanel.setBorder(createLineBorder(new java.awt.Color(0, 0, 0)));
        managerPanel.setPreferredSize(new Dimension(1000, 800));

        topManagerPanel.setBackground(lightGray);
        topManagerPanel.setBorder(createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        topManagerPanel.setForeground(new java.awt.Color(238, 9, 9));
        topManagerPanel.setPreferredSize(new Dimension(1000, 50));

        reportsButton.setIcon(new ImageIcon(getClass().getResource("/icons/3d bar chart.gif")));
        reportsButton.setText("Raporty");
        reportsButton.addActionListener(this::reportsButtonActionPerformed);

        dataExpButton2.setIcon(new ImageIcon(getClass().getResource("/icons/Upload.gif")));
        dataExpButton2.setText("Eksport danych");
        dataExpButton2.addActionListener(this::dataExpButton2ActionPerformed);

        javax.swing.GroupLayout topManagerPanelLayout = new javax.swing.GroupLayout(topManagerPanel);
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
                                .addGroup(topManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(reportsButton)
                                        .addComponent(dataExpButton2))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        leftManagerPanel.setBackground(lightGray);
        leftManagerPanel.setBorder(createEtchedBorder());
        leftManagerPanel.setOpaque(false);
        leftManagerPanel.setPreferredSize(new Dimension(200, 750));

        searchClientByManagerButton.setIcon(new ImageIcon(getClass().getResource("/icons/Find.gif")));
        searchClientByManagerButton.setText("Wyszukiwanie");
        searchClientByManagerButton.addActionListener(this::searchClientByManagerButtonActionPerformed);

        myManagerPanelButton.setIcon(new ImageIcon(getClass().getResource("/icons/Info.gif")));
        myManagerPanelButton.setText("Mój panel");
        myManagerPanelButton.addActionListener(this::myManagerPanelButtonActionPerformed);

        logoutManagerButton.setIcon(new ImageIcon(getClass().getResource("/icons/Exit.gif")));
        logoutManagerButton.setText("Wylogowanie");
        logoutManagerButton.addActionListener(this::logoutManagerButtonActionPerformed);

        exitManagerButton.setIcon(new ImageIcon(getClass().getResource("/icons/Turn off.gif")));
        exitManagerButton.setText("Wyjście");
        exitManagerButton.addActionListener(this::exitManagerButtonActionPerformed);

        javax.swing.GroupLayout leftManagerPanelLayout = new javax.swing.GroupLayout(leftManagerPanel);
        leftManagerPanel.setLayout(leftManagerPanelLayout);
        leftManagerPanelLayout.setHorizontalGroup(
                leftManagerPanelLayout.createParallelGroup(LEADING)
                        .addComponent(searchClientByManagerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                        .addComponent(myManagerPanelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutManagerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitManagerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        mainManagerPanel.setBorder(createLineBorder(new java.awt.Color(0, 0, 0)));
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

        changeManagerPassButton.setIcon(new ImageIcon(getClass().getResource("/icons/Repair.gif")));
        changeManagerPassButton.setText("Zmiana hasła");
        changeManagerPassButton.addActionListener(this::changeManagerPassButtonActionPerformed);

        javax.swing.GroupLayout mainManagerPanelLayout = new javax.swing.GroupLayout(mainManagerPanel);
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
                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel50)
                                        .addComponent(txtManagerName, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel51)
                                        .addComponent(txtManagerSurname, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel52)
                                        .addComponent(txtManagerType, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(RELATED)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel53)
                                        .addComponent(txtManagerLogin, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
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

        managerSearchButton.setIcon(new ImageIcon(getClass().getResource("/icons/Search.gif")));
        managerSearchButton.setText("Szukaj");
        managerSearchButton.addActionListener(this::managerSearchButtonActionPerformed);

        managerResetButton.setIcon(new ImageIcon(getClass().getResource("/icons/Refresh.gif")));
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
        tableClientsForManager.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableClientsForManager.setPreferredSize(new Dimension(780, 100));
        tableClientsForManager.getTableHeader().setResizingAllowed(false);
        tableClientsForManager.getTableHeader().setReorderingAllowed(false);
        tableClientsForManager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientsForManagerMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tableClientsForManager);

        managerEditButton.setIcon(new ImageIcon(getClass().getResource("/icons/Repair.gif")));
        managerEditButton.setText("Edycja");
        managerEditButton.addActionListener(this::managerEditButtonActionPerformed);

        javax.swing.GroupLayout searchUserByManagerPanelLayout = new javax.swing.GroupLayout(searchUserByManagerPanel);
        searchUserByManagerPanel.setLayout(searchUserByManagerPanelLayout);
        searchUserByManagerPanelLayout.setHorizontalGroup(
                searchUserByManagerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel66)
                                                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel67)
                                                                        .addComponent(jLabel68))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(txtManagerSearchSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                                        .addComponent(txtManagerSearchPesel))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(managerSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                                        .addComponent(managerResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel67)
                                        .addComponent(txtManagerSearchSurname, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(managerSearchButton))
                                .addGap(4, 4, 4)
                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel68)
                                        .addComponent(txtManagerSearchPesel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
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

        saveClientByManagerButton.setIcon(new ImageIcon(getClass().getResource("/icons/Save.gif")));
        saveClientByManagerButton.setText("Zapisz");
        saveClientByManagerButton.addActionListener(this::saveClientByManagerButtonzapiszKlientaAction);

        cancelClientByManagerButton.setIcon(new ImageIcon(getClass().getResource("/icons/Cancel.gif")));
        cancelClientByManagerButton.setText("Anuluj");
        cancelClientByManagerButton.addActionListener(this::cancelClientByManagerButtonanulujZapisKlientaAction);

        cbEditPersonalAcc.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbEditPersonalAcc.setText("Konto osobiste");

        cbEditCurrencyAcc.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbEditCurrencyAcc.setText("Konto walutowe");

        cbEditLocate.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbEditLocate.setText("Lokata");

        cbEditCurrentCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbEditCurrentCredit.setText("Kredyt gotówkowy");

        cbEditHomeCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbEditHomeCredit.setText("Kredyt hipoteczny");

        cbEditRepeatedCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbEditRepeatedCredit.setText("Kredyt odnawialny");

        cbEditCreditCard.setFont(new java.awt.Font("Times New Roman,", 0, 18));
        cbEditCreditCard.setText("Karta kredytowa");

        cbEditChance.setModel(new DefaultComboBoxModel<>(new String[]{"Wybierz", "Konto osobiste", "Konto walutowe", "Lokata", "Kredyt gotówkowy", "Kredyt hipoteczny", "Kredyt odnawialny", "Karta kredytowa"}));

        jLabel85.setFont(timesNewRoman20);
        jLabel85.setText("Szansa sprzedaży");

        try {
            editClientPostalCode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
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

        javax.swing.GroupLayout editUserByManagerPanelLayout = new javax.swing.GroupLayout(editUserByManagerPanel);
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
                                                                                        .addPreferredGap(RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(selectUserButton, PREFERRED_SIZE, 32, PREFERRED_SIZE))
                                                                                .addGroup(LEADING, editUserByManagerPanelLayout.createParallelGroup(TRAILING)
                                                                                        .addComponent(editClientCreateDate, PREFERRED_SIZE, 237, PREFERRED_SIZE)
                                                                                        .addComponent(editClientModification, LEADING, PREFERRED_SIZE, 237, PREFERRED_SIZE))))))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addComponent(jLabel72)
                                                        .addComponent(jLabel71)
                                                        .addComponent(jLabel74)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel73)
                                                                .addGap(35, 35, 35)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(editClientPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
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
                                                                                                .addComponent(editClientCity, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
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
                                                .addPreferredGap(RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(editClientVip)
                                                .addPreferredGap(RELATED)
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel79)
                                                        .addComponent(editClientCreateDate, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(editClientTelDate, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(jLabel86))
                                                .addPreferredGap(RELATED)
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel78)
                                                        .addComponent(editClientCreator, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(selectUserButton))
                                                .addPreferredGap(RELATED)
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelClientModDate1)
                                                        .addComponent(editClientModification, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addGap(19, 19, 19))
                                        .addGroup(LEADING, editUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGap(2, 2, 2)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel80)
                                                                        .addComponent(editClientStreet, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel81)
                                                                        .addComponent(editClientNumber, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel82)
                                                                        .addComponent(editClientCity, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel83)
                                                                        .addComponent(editClientPostalCode, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
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
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel85)
                                                                        .addComponent(cbEditChance, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel70)
                                                                        .addComponent(editClientName, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel71)
                                                                        .addComponent(editClientSurname, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(jLabel72)
                                                                        .addComponent(editClientPesel, TRAILING, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(LEADING)
                                                                        .addComponent(editClientPhone, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(jLabel73))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel74)
                                                                        .addComponent(editClientPhone2, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                                                .addPreferredGap(RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(editClientMail, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
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
                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveClientByManagerButton)
                                        .addComponent(cancelClientByManagerButton))
                                .addContainerGap(87, Short.MAX_VALUE))
        );

        reportsByManagerPanel.setPreferredSize(new Dimension(800, 750));

        jLabel59.setFont(timesNewRomanBold24);
        jLabel59.setText("Generowanie raportów");

        jButton4.setIcon(new ImageIcon(getClass().getResource("/icons/Report.gif")));
        jButton4.setText("Generuj");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jLabel60.setFont(timesNewRoman20);
        jLabel60.setText("Telefony z: ");

        cbReportDate.setModel(new DefaultComboBoxModel<>(new String[]{"ostatni dzień", "ostatnie 3 dni", "ostatni tydzień", "ostatni miesiąc", "ostatnie 3 miesiące"}));

        jLabel45.setFont(timesNewRoman20);
        jLabel45.setText("Szansa sprzedaży:");

        jButton5.setIcon(new ImageIcon(getClass().getResource("/icons/Report.gif")));
        jButton5.setText("Generuj");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jLabel61.setFont(timesNewRoman20);
        jLabel61.setText("Raport pracownika:");

        txtUserReport.setEditable(false);

        jButton6.setText("...");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jButton7.setIcon(new ImageIcon(getClass().getResource("/icons/Report.gif")));
        jButton7.setText("Generuj");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        javax.swing.GroupLayout raportsByManagerPanelLayout = new javax.swing.GroupLayout(reportsByManagerPanel);
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
                                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel60)
                                                        .addComponent(cbReportDate, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
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
                                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtUserReport, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(jButton6)
                                                        .addComponent(jButton7)))
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel61)))
                                .addContainerGap(569, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout managerPanelLayout = new javax.swing.GroupLayout(managerPanel);
        managerPanel.setLayout(managerPanelLayout);
        managerPanelLayout.setHorizontalGroup(
                managerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(managerPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(topManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(managerPanelLayout.createSequentialGroup()
                                                .addComponent(leftManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(mainManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(searchUserByManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)))
                                .addGap(800, 800, 800))
                        .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(editUserByManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(reportsByManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );
        managerPanelLayout.setVerticalGroup(
                managerPanelLayout.createParallelGroup(LEADING)
                        .addGroup(managerPanelLayout.createSequentialGroup()
                                .addComponent(topManagerPanel, PREFERRED_SIZE, 62, PREFERRED_SIZE)
                                .addPreferredGap(RELATED)
                                .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                        .addComponent(leftManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(mainManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(searchUserByManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(0, 0, 0))
                        .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(editUserByManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(managerPanelLayout.createParallelGroup(LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(reportsByManagerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(LEADING)
                        .addGap(0, 1024, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(loginPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(userPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(TRAILING, layout.createSequentialGroup()
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adminPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addContainerGap()))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(managerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addContainerGap(22, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(LEADING)
                        .addGap(0, 824, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(loginPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(userPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(TRAILING, layout.createSequentialGroup()
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adminPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addContainerGap()))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(managerPanel, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addContainerGap(22, Short.MAX_VALUE)))
        );

        pack();
    }

    private void logowanieAction(ActionEvent evt) {
        processLoginAction();
    }

    private void processLoginAction() {
        leftPanel.setVisible(true);
        topPanel.setVisible(true);

        if (!showServerLocationMessageBox()) {
            return;
        }

        try {
            LoginResponse response = UserDAO.login(txtLogin.getText(), txtPassword.getText());

            showVersionParityMessageBox(response);
            loggedUser = response.getUser();

            if (loggedUser.getType() == ADMIN) {
                processAdminPanel();
            } else if (loggedUser.getType() == MANAGER) {
                processManagerPanel();
            } else {
                processUserPanel();
            }
        } catch (UserLoginException exception) {
            showMessageDialog(this, exception.getMessage(), "Błąd logowania", ERROR_MESSAGE);
        }
    }

    private boolean showServerLocationMessageBox() {
        String[] buttons = {"Z globalnym", "Z lokalnym", "Anuluj"};

        int chosenOption = showOptionDialog(this,
                "Wybierz server SadCRM z którym chcesz się połączyć:",
                "Confirmation",
                DEFAULT_OPTION, QUESTION_MESSAGE,
                null,
                buttons, buttons[0]);

        switch (chosenOption) {
            case 0:
                HttpJson.BASE_URL = HttpJson.BASE_GLOBAL_URL;
                return true;
            case 1:
                HttpJson.BASE_URL = HttpJson.BASE_LOCAL_URL;
                return true;
            default:
                return false;
        }
    }

    private void showVersionParityMessageBox(LoginResponse response) {
        if (response.getVersion().equals(VERSION)) {
            messageBox(format("Client and Server are of the same version (%s).", VERSION));
        } else {
            messageBox(format("Client (%s) and Server (%s) are of different version.", VERSION, response.getVersion()));
        }
    }

    private void dodajKlientaAction(ActionEvent evt) {
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

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    private void zapiszKlientaAction(ActionEvent evt) {
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
                } catch (ParseException e) {
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
                    client.setSellChance(cbChance.getSelectedItem().toString());
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


    private void anulujZapisKlientaAction(ActionEvent evt) {
        if (selectedClient != null) {
            processSearchPanel();
        } else {
            processUserPanel();
        }
    }

    private void mojeKontaktyAction(ActionEvent evt) {
        //enableMyClientsPanel();
        myContacts = true;
        mail = false;
        processSearchPanel();
    }

    private void wyszukiwanieAction(ActionEvent evt) {
        myContacts = false;
        mail = false;
        processSearchPanel();
    }

    private void processSearchPanel() {
        PanelsUtil.enablePanel(searchPanel, new JPanel[]{mainUserPanel, addClientPanel, sendMailPanel});

        if (myContacts) {
            txtSendMultipleMail.setVisible(false);
            txtDetailsClientButton.setVisible(true);
            tableClients.setRowSelectionAllowed(true);
            tableClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            List<Client> clients = ClientDAO.searchByUser(loggedUser);
            TableUtil.displayClients(clients, tableClients);
        } else if (mail) {
            txtSendMultipleMail.setVisible(true);
            txtDetailsClientButton.setVisible(false);
            tableClients.setRowSelectionAllowed(true);
            tableClients.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            List<Client> clients = ClientDAO.searchHasMail();
            TableUtil.displayClients(clients, tableClients);
        } else {
            txtSendMultipleMail.setVisible(false);
            txtDetailsClientButton.setVisible(true);
            tableClients.setRowSelectionAllowed(true);
            tableClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            List<Client> clients = ClientDAO.searchClients();
            TableUtil.displayClients(clients, tableClients);
        }
    }

    private void korespondencjaSeryjnaAction(ActionEvent evt) {
        myContacts = false;
        mail = true;
        processSearchPanel();
    }

    private void mojPanelAction(ActionEvent evt) {
        PanelsUtil.enablePanel(mainUserPanel, new JPanel[]{addClientPanel, searchPanel, sendMailPanel});
    }

    private void wyczyscPolaMailaAction(ActionEvent evt) {
        txtMailContent.setText("");
        txtRecipient.setText("");
        txtMailSubject.setText("");
    }

    private void wyslijMailAction(ActionEvent evt) {
        PanelsUtil.enablePanel(sendMailPanel, new JPanel[]{mainUserPanel, addClientPanel, searchPanel});
    }

    private void wyslijMail2Action(ActionEvent evt) {
        processSearchPanel();
    }

    private void wyslijjednegoMailaAction(ActionEvent evt) {
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

    private void szukajKlientaAction(ActionEvent evt) {
        List<Client> searchResults;
        if (!txtSearchSurname.getText().isEmpty() && !txtSearchPesel.getText().isEmpty()) {
            if (myContacts) {
                searchResults = ClientDAO.searchByUserSurnameAndPesel(loggedUser, txtSearchSurname.getText(), txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else if (mail) {
                searchResults = ClientDAO.searchBySurnameAndPeselAndHasMail(txtSearchSurname.getText(), txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else {
                searchResults = ClientDAO.searchBySurnameAndPesel(txtSearchSurname.getText(), txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            }
        } else if (txtSearchSurname.getText().isEmpty() && !txtSearchPesel.getText().isEmpty()) {
            if (myContacts) {
                searchResults = ClientDAO.searchByUserAndPesel(loggedUser, txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else if (mail) {
                searchResults = ClientDAO.searchByPeselAndHasMail(txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else {
                searchResults = ClientDAO.searchByPesel(txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            }
        } else if (!txtSearchSurname.getText().isEmpty() && txtSearchPesel.getText().isEmpty()) {
            if (myContacts) {
                searchResults = ClientDAO.searchByUserAndSurname(loggedUser, txtClientSurname.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else if (mail) {
                searchResults = ClientDAO.searchBySurnameAndHasMail(txtClientSurname.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else {
                searchResults = ClientDAO.searchBySurname(txtSearchSurname.getText());
                TableUtil.displayClients(searchResults, tableClients);
            }
        } else {
            if (myContacts) {
                searchResults = ClientDAO.searchByUser(loggedUser);
                TableUtil.displayClients(searchResults, tableClients);
            } else if (mail) {
                searchResults = ClientDAO.searchHasMail();
                TableUtil.displayClients(searchResults, tableClients);
            } else {
                searchResults = ClientDAO.searchClients();
                TableUtil.displayClients(searchResults, tableClients);
            }
        }
    }

    private void resetPolSzukaniaKlientaAction(ActionEvent evt) {
        // Czyszczenie wyników wyszukiwania
        txtSearchPesel.setText("");
        txtSearchSurname.setText("");
        if (myContacts) {
            List<Client> clients = ClientDAO.searchClients();
            TableUtil.displayClients(clients, tableClients);
        } else if (mail) {
            List<Client> clients = ClientDAO.searchHasMail();
            TableUtil.displayClients(clients, tableClients);
        } else {
            List<Client> clients = ClientDAO.searchByUser(loggedUser);
            TableUtil.displayClients(clients, tableClients);
        }
    }

    private void szczegolyKlientaAction(ActionEvent evt) {
        if (tableClients.getSelectedRowCount() == 1) {
            editClientAction(tableClients.getSelectedRow());
        } else {
            showMessageDialog(this, "Wybierz klienta", "Zaznacz wiersz", ERROR_MESSAGE);
        }
    }

    private void edytujKlientaAction(ActionEvent evt) {
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

    private void tableClientsMouseClicked(java.awt.event.MouseEvent evt) {
        tableClients.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (mail) {
                        // wysyłanie maila                        
                        int[] rows = tableClients.getSelectedRows();
                        List<String> receipents = new ArrayList<>();
                        String receipts = "";
                        for (int i = 0; i < rows.length; i++) {
                            receipts = receipts + tableClients.getValueAt(i, 4) + "\n";
                        }
                        System.out.println(receipts);
                        processMailPanel(receipts);
                    } else {
                        JTable target = (JTable) e.getSource();
                        int row = target.getSelectedRow();

                        editClientAction(row);
                    }

                }
            }
        });
    }

    private void wyjscieAction(ActionEvent evt) {
        exitCommonAction();
    }

    private void wyslijMailDoWszystkichAction(ActionEvent evt) {
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

    private void wylogujUserAction(ActionEvent evt) {
        int n = showOptionDialog(this,
                "Czy napewno chcesz się wylogować z programu?",
                "Wyjście",
                0, INFORMATION_MESSAGE, null, options, null);

        if (n == 0) {
            loggedUser = null;
            txtLogin.setText("");
            txtPassword.setText("");
            PanelsUtil.enablePanel(loginPanel, new JPanel[]{userPanel, adminPanel, managerPanel, addClientPanel, searchPanel, sendMailPanel, leftPanel, topPanel});
        }
    }

    private void exitUserAction(ActionEvent evt) {
        exitCommonAction();
    }

    private void txtLoginActionPerformed(ActionEvent evt) {
        processLoginAction();
    }

    private void txtPasswordActionPerformed(ActionEvent evt) {
        processLoginAction();
    }

    private void adminSearchButtonActionPerformed(ActionEvent evt) {
        List<User> searchResults;
        if (!txtAdminSearchName.getText().isEmpty() && !txtAdminSearchSurname.getText().isEmpty()) {
            searchResults = UserDAO.searchUsersBySurnameAndName(txtAdminSearchSurname.getText(), txtAdminSearchName.getText());
            TableUtil.displayUsers(searchResults, tableUsers);
        } else if (txtAdminSearchName.getText().isEmpty() && !txtAdminSearchSurname.getText().isEmpty()) {
            searchResults = UserDAO.searchUsersBySurname(txtAdminSearchSurname.getText());
            TableUtil.displayUsers(searchResults, tableUsers);
        } else if (!txtAdminSearchName.getText().isEmpty() && txtAdminSearchSurname.getText().isEmpty()) {
            searchResults = UserDAO.searchUsersByName(txtAdminSearchName.getText());
            TableUtil.displayUsers(searchResults, tableUsers);
        } else {
            searchResults = UserDAO.searchUsers();
            TableUtil.displayUsers(searchResults, tableUsers);
        }
    }

    private void adminResetButtonActionPerformed(ActionEvent evt) {
        txtAdminSearchName.setText("");
        txtAdminSearchSurname.setText("");

        List<User> users = UserDAO.searchUsers();
        TableUtil.displayUsers(users, tableUsers);
    }

    private void addUserButtonActionPerformed(ActionEvent evt) {
        PanelsUtil.enablePanel(addUserPanel, new JPanel[]{searchUserPanel, mainAdminPanel});
        clearUserFields();

        txtAddUserDate.setText(now());
        selectedUser = null;
    }

    private void cancelUserButtonActionPerformed(ActionEvent evt) {
        if (selectedUser == null) {
            PanelsUtil.enablePanel(mainAdminPanel, new JPanel[]{searchUserPanel, addUserPanel});
        } else {
            PanelsUtil.enablePanel(searchUserPanel, new JPanel[]{mainAdminPanel, addUserPanel});
        }
    }

    private void saveUserButtonActionPerformed(ActionEvent evt) {
        // ZAPISZ USERA
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
                    showMessageDialog(this,
                            "Nowy użytkownik został dodany.", "Dodawania użytkownika", INFORMATION_MESSAGE);

                    processAdminPanel();
                } catch (UserInsertException ex) {
                    showMessageDialog(this, ex.getMessage(), "Dodawania klienta", ERROR_MESSAGE);
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

    private void logoutAdminButtonActionPerformed(ActionEvent evt) {
        int n = showOptionDialog(this,
                "Czy napewno chcesz się wylogować z programu?",
                "Wyjście",
                0, INFORMATION_MESSAGE, null, options, null);

        if (n == 0) {
            loggedUser = null;
            txtLogin.setText("");
            txtPassword.setText("");
            PanelsUtil.enablePanel(loginPanel, new JPanel[]{userPanel, adminPanel, managerPanel, addClientPanel, searchPanel, sendMailPanel, leftPanel, topPanel});
        }
    }

    private void txtClientTelActionPerformed(ActionEvent evt) {
        if (txtClientTel.isSelected()) {
            txtClientTelDate.setText(now());
        } else {
            txtClientTelDate.setText(null);
        }
    }

    private void searchUserButtonActionPerformed(ActionEvent evt) {
        processAdminSearch();
    }

    private void processAdminSearch() {
        //WYSZUKIWANIE NA PANELU ADMINA        
        PanelsUtil.enablePanel(searchUserPanel, new JPanel[]{addUserPanel, mainAdminPanel});
        selectedUser = null;
        tableUsers.setRowSelectionAllowed(true);
        tableUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        List<User> users = UserDAO.searchUsers();
        TableUtil.displayUsers(users, tableUsers);
    }

    private void adminDetailsButtonActionPerformed(ActionEvent evt) {
        // Edycja usera
        if (tableUsers.getSelectedRowCount() == 1) {
            editUserAction(tableUsers.getSelectedRow());
        } else {
            showMessageDialog(this,
                    "Wybierz użytkownika",
                    "Zaznacz wiersz",
                    ERROR_MESSAGE);
        }
    }

    private void editUserAction(int row) {
        Object selectedUserId = tableUsers.getModel().getValueAt(row, 0);
        System.out.println("selectedUserId " + selectedUserId);
        selectedUser = null;
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

    private void myAdminPanelButtonActionPerformed(ActionEvent evt) {
        PanelsUtil.enablePanel(mainAdminPanel, new JPanel[]{addUserPanel, searchUserPanel});
    }

    private void exitAdminButtonActionPerformed(ActionEvent evt) {
        exitCommonAction();
    }

    private void tableUsersMouseClicked(java.awt.event.MouseEvent evt) {
        tableUsers.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    editUserAction(row);
                }
            }
        });
    }

    private void changeAdminPassButtonActionPerformed(ActionEvent evt) {
        commonChangePassword();
    }

    private void commonChangePassword() {
        jDialog1.setVisible(true);
        jDialog1.setSize(400, 200);
        txtChangePass1.setText("");
        txtChangePass2.setText("");
    }

    private void changePasswordButtonActionPerformed(ActionEvent evt) {
        if (!ValidationUtil.validatePassword(txtChangePass1.getText())) {
            showMessageDialog(this,
                    "Hasło powinno mieć co najmniej 4 znaki, nie więcej niż 10 znaków. ",
                    "Zmiana hasła",
                    ERROR_MESSAGE);
        } else if (!txtChangePass1.getText().equalsIgnoreCase(txtChangePass2.getText())) {
            showMessageDialog(this,
                    "Hasła muszą być takie same. ",
                    "Zmiana hasła",
                    ERROR_MESSAGE);
        } else {
            loggedUser.setPassword(txtChangePass1.getText());
            UserDAO.updateUser(loggedUser);

            showMessageDialog(this,
                    "Hasła zostało zmienione. ", "Zmiana hasła", INFORMATION_MESSAGE);

            jDialog1.setVisible(false);
            jDialog1.dispose();
        }
    }

    private void cancelChangePasswordButtonActionPerformed(ActionEvent evt) {
        jDialog1.setVisible(false);
        jDialog1.dispose();
    }

    private void zmienHasloUzytkAction(ActionEvent evt) {
        commonChangePassword();
    }

    private void searchClientByManagerButtonActionPerformed(ActionEvent evt) {
        processSearchClientsForManager();
    }

    private void processSearchClientsForManager() {
        //WYSZUKIWANIE NA PANELU MANAGERA        
        PanelsUtil.enablePanel(searchUserByManagerPanel, new JPanel[]{editUserByManagerPanel, mainManagerPanel, reportsByManagerPanel});
        selectedUser = null;
        tableClientsForManager.setRowSelectionAllowed(true);
        tableClientsForManager.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        List<Client> clients = ClientDAO.searchClients();
        TableUtil.displayClients(clients, tableClientsForManager);
    }

    private void myManagerPanelButtonActionPerformed(ActionEvent evt) {
        PanelsUtil.enablePanel(mainManagerPanel, new JPanel[]{editUserByManagerPanel, searchUserByManagerPanel, reportsByManagerPanel});
    }

    private void logoutManagerButtonActionPerformed(ActionEvent evt) {
        int n = showOptionDialog(this,
                "Czy napewno chcesz się wylogować z programu?",
                "Wyjście",
                0, INFORMATION_MESSAGE, null, options, null);

        if (n == 0) {
            loggedUser = null;
            txtLogin.setText("");
            txtPassword.setText("");
            PanelsUtil.enablePanel(loginPanel, new JPanel[]{userPanel, adminPanel, managerPanel});
        }
    }

    private void exitManagerButtonActionPerformed(ActionEvent evt) {
        exitCommonAction();
    }

    private void exitCommonAction() {
        // wyjście             
        int n = showOptionDialog(this,
                "Czy napewno chcesz wyjść z programu?",
                "Wyjście",
                0, INFORMATION_MESSAGE, null, options, null);

        if (n == 0) {
            System.exit(1);
        }
    }

    private void changeManagerPassButtonActionPerformed(ActionEvent evt) {
        commonChangePassword();
    }

    private void managerSearchButtonActionPerformed(ActionEvent evt) {
        List<Client> searchResults;
        if (!txtManagerSearchSurname.getText().equalsIgnoreCase("") && !txtManagerSearchPesel.getText().equalsIgnoreCase("")) {
            // search by user and pesel
            searchResults = ClientDAO.searchBySurnameAndPesel(txtManagerSearchSurname.getText(), txtManagerSearchPesel.getText());
            TableUtil.displayClients(searchResults, tableClientsForManager);
        } else if (txtManagerSearchSurname.getText().equalsIgnoreCase("") && !txtManagerSearchPesel.getText().equalsIgnoreCase("")) {
            // search by pesel
            searchResults = ClientDAO.searchByPesel(txtManagerSearchPesel.getText());
            TableUtil.displayClients(searchResults, tableClientsForManager);
        } else if (!txtManagerSearchSurname.getText().equalsIgnoreCase("") && txtManagerSearchPesel.getText().equalsIgnoreCase("")) {
            //search by surname
            searchResults = ClientDAO.searchBySurname(txtManagerSearchSurname.getText());
            TableUtil.displayClients(searchResults, tableClientsForManager);
        }
    }

    private void managerResetFieldsAction(ActionEvent evt) {
        txtManagerSearchPesel.setText("");
        txtManagerSearchSurname.setText("");

        List<Client> clients = ClientDAO.searchClients();
        TableUtil.displayClients(clients, tableClientsForManager);
    }

    private void tableClientsForManagerMouseClicked(java.awt.event.MouseEvent evt) {
        tableClientsForManager.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    editClientForManager(row);
                }
            }
        });
    }

    private void managerEditButtonActionPerformed(ActionEvent evt) {
        if (tableClientsForManager.getSelectedRowCount() == 1) {
            editClientForManager(tableClientsForManager.getSelectedRow());
        } else {
            showMessageDialog(this,
                    "Wybierz klienta",
                    "Zaznacz wiersz",
                    ERROR_MESSAGE);
        }
    }

    private void saveClientByManagerButtonzapiszKlientaAction(ActionEvent evt) {
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
        if (!selectedClient.getVip() && editClientVip.isSelected()) {
            selectedClient.setVip(true);
            isEdited = true;
        } else if (selectedClient.getVip() && !editClientVip.isSelected()) {
            selectedClient.setVip(false);
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

        String sellChan = selectedClient.getSellChance();
        if (sellChan == null && cbEditChance.getSelectedIndex() != 0) {
            selectedClient.setSellChance(cbEditChance.getSelectedItem().toString());
            isEdited = true;
        } else if (sellChan != null && !sellChan.equalsIgnoreCase(cbEditChance.getSelectedItem().toString())) {
            selectedClient.setSellChance(cbEditChance.getSelectedItem().toString());
            isEdited = true;
        }

        String selectedProd = selectedClient.getProducts();
        String prodForManager = createProductsEntryForManager();
        if (!selectedProd.equalsIgnoreCase(prodForManager)) {
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

    private void cancelClientByManagerButtonanulujZapisKlientaAction(ActionEvent evt) {
        processSearchClientsForManager();
    }

    private void editClientTelActionPerformed(ActionEvent evt) {
        if (editClientTel.isSelected()) {
            editClientTelDate.setText(now());
        } else {
            editClientTelDate.setText(null);
        }
    }

    private void processUserRaports() {
        newUser = null;
        if (usersForManagerTable.getSelectedRowCount() == 1) {
            String id = usersForManagerTable.getValueAt(usersForManagerTable.getSelectedRow(), 0).toString();
            Integer i = Integer.valueOf(id);
            newUser = UserDAO.getUserById(i);

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
            Integer i = Integer.valueOf(id);
            newUser = UserDAO.getUserById(i);
            String newName = newUser.getName() + " " + newUser.getSurname().trim();
            if (!editClientCreator.getText().trim().equalsIgnoreCase(newName)) {
                // different
                editClientCreator.setText(newUser.getName() + " " + newUser.getSurname());

            } else {
                // the same

            }

            jDialog2.dispose();
            jDialog2.setVisible(false);
            jDialog2.setAlwaysOnTop(false);
        } else {
            showMessageDialog(this, "Wybierz pracownika", "Zaznacz wiersz", ERROR_MESSAGE);
        }
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        if (isReport) {
            processUserRaports();
        } else {
            processSelectedCreator();
        }
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        jDialog2.dispose();
        jDialog2.setVisible(false);
        jDialog2.setAlwaysOnTop(false);
    }

    private void selectUserButtonActionPerformed(ActionEvent evt) {
        newUser = null;
        isReport = false;
        List<User> users = UserDAO.searchUsers();
        TableUtil.displayUsersForManager(users, usersForManagerTable);
        jDialog2.setVisible(true);
        jDialog2.setAlwaysOnTop(true);
    }

    private void usersForManagerTableMouseClicked(java.awt.event.MouseEvent evt) {
        usersForManagerTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (isReport) {
                        processUserRaports();
                    } else {
                        processSelectedCreator();
                    }
                }
            }
        });
    }

    private void reportsButtonActionPerformed(ActionEvent evt) {
        PanelsUtil.enablePanel(reportsByManagerPanel, new JPanel[]{editUserByManagerPanel, searchUserByManagerPanel, mainManagerPanel});
    }

    private void jButton4ActionPerformed(ActionEvent evt) {
        int choose = cbReportDate.getSelectedIndex();

        ReportsUtil util = new ReportsUtil();
        util.createTelephonesReport(choose);
    }

    private void jButton5ActionPerformed(ActionEvent evt) {
        ReportsUtil util = new ReportsUtil();
        util.createSellChanceReport();
    }

    private void jButton6ActionPerformed(ActionEvent evt) {
        // wybierz pracownika do raportu
        newUser = null;
        isReport = true;
        List<User> users = UserDAO.searchUsers();
        TableUtil.displayUsersForManager(users, usersForManagerTable);
        jDialog2.setVisible(true);
        jDialog2.setAlwaysOnTop(true);
    }

    private void jButton7ActionPerformed(ActionEvent evt) {
        if (!txtUserReport.getText().equalsIgnoreCase("")) {
            ReportsUtil util = new ReportsUtil();
            util.createUserReport(newUser);
        } else {
            showMessageDialog(this,
                    "Wybierz pracownika",
                    "Błąd",
                    ERROR_MESSAGE);
        }

    }

    private void dataExpButton2ActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void dataExpButton1ActionPerformed(ActionEvent evt) {
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
        editClientTelDate.setText(selectedClient.getTelDate().toString());
        editClientModification.setText(selectedClient.getTelDate().toString());

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
                } else {
                    showMessageDialog(this,
                            "Hasła muszę być takie same",
                            "Błąd dodawania użytkownika",
                            ERROR_MESSAGE);

                    return false;
                }
            } else {
                showMessageDialog(this,
                        "Hasła musi posiadadać od 4 do 10 znaków.",
                        "Błąd dodawania użytkownika",
                        ERROR_MESSAGE);

                return false;
            }

        } else {
            showMessageDialog(this,
                    result,
                    "Błąd dodawania użytkownika",
                    ERROR_MESSAGE);

            return false;
        }
    }

    private void processMailPanel(String r) {
        PanelsUtil.enablePanel(sendMailPanel, new JPanel[]{mainUserPanel, addClientPanel, searchPanel});
        txtRecipient.setText(r);

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

        txtClientModification.setText(Objects.toString(selectedClient.getTelDate()));

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

    private JButton addClientButton = new JButton();
    private JPanel addClientPanel = new JPanel();
    private JButton addUserButton = new JButton();
    private JPanel addUserPanel = new JPanel();
    private JButton adminDetailsButton = new JButton();
    private JPanel adminPanel = new JPanel();
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
    private JPanel editUserByManagerPanel = new JPanel();
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
    private JLabel jLabel10 = new JLabel();
    private JLabel jLabel11 = new JLabel();
    private JLabel jLabel12 = new JLabel();
    private JLabel jLabel13 = new JLabel();
    private JLabel jLabel14 = new JLabel();
    private JLabel jLabel15 = new JLabel();
    private JLabel jLabel16 = new JLabel();
    private JLabel jLabel17 = new JLabel();
    private JLabel jLabel18 = new JLabel();
    private JLabel jLabel19 = new JLabel();
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
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel40 = new JLabel();
    private JLabel jLabel41 = new JLabel();
    private JLabel jLabel42 = new JLabel();
    private JLabel jLabel43 = new JLabel();
    private JLabel jLabel44 = new JLabel();
    private JLabel jLabel45 = new JLabel();
    private JLabel jLabel46 = new JLabel();
    private JLabel jLabel47 = new JLabel();
    private JLabel jLabel48 = new JLabel();
    private JLabel jLabel49 = new JLabel();
    private JLabel jLabel5 = new JLabel();
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
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel60 = new JLabel();
    private JLabel jLabel61 = new JLabel();
    private JLabel jLabel66 = new JLabel();
    private JLabel jLabel67 = new JLabel();
    private JLabel jLabel68 = new JLabel();
    private JLabel jLabel69 = new JLabel();
    private JLabel jLabel7 = new JLabel();
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
    private JLabel jLabel8 = new JLabel();
    private JLabel jLabel80 = new JLabel();
    private JLabel jLabel81 = new JLabel();
    private JLabel jLabel82 = new JLabel();
    private JLabel jLabel83 = new JLabel();
    private JLabel jLabel84 = new JLabel();
    private JLabel jLabel85 = new JLabel();
    private JLabel jLabel86 = new JLabel();
    private JLabel jLabel9 = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JScrollPane jScrollPane5 = new JScrollPane();
    private JScrollPane jScrollPane6 = new JScrollPane();
    private JScrollPane jScrollPane7 = new JScrollPane();
    private JScrollPane jScrollPane8 = new JScrollPane();
    private JScrollPane jScrollPane9 = new JScrollPane();
    private JLabel lab = new JLabel();
    private JLabel lab1 = new JLabel();
    private JLabel labelClientModDate = new JLabel();
    private JLabel labelClientModDate1 = new JLabel();
    private JPanel leftManagerPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel leftPanel1 = new JPanel();
    private JButton loginButton = new JButton();
    private JPanel loginPanel = new JPanel();
    private JButton logoutAdminButton = new JButton();
    private JButton logoutButton = new JButton();
    private JButton logoutManagerButton = new JButton();
    private JButton mailButton = new JButton();
    private JButton mailMergeButton = new JButton();
    private JPanel mainAdminPanel = new JPanel();
    private JPanel mainManagerPanel = new JPanel();
    private JPanel mainUserPanel = new JPanel();
    private JButton managerEditButton = new JButton();
    private JPanel managerPanel = new JPanel();
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
    private JPanel searchPanel = new JPanel();
    private JButton searchSearchButton = new JButton();
    private JButton searchUserButton = new JButton();
    private JPanel searchUserByManagerPanel = new JPanel();
    private JPanel searchUserPanel = new JPanel();
    private JButton selectUserButton = new JButton();
    private JButton sendMailButton = new JButton();
    private JPanel sendMailPanel = new JPanel();
    private JButton sendOneMailButton = new JButton();
    private JTable tableClients = new JTable();
    private JTable tableClientsForManager = new JTable();
    private JTable tableUsers = new JTable();
    private JPanel topManagerPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel topPanel1 = new JPanel();
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
    private JPanel userPanel = new JPanel();
    private JTable usersForManagerTable = new JTable();
}
