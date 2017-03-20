package com.sad.sadcrm.form;

import com.sad.sadcrm.MailUtil;
import com.sad.sadcrm.ReportsUtil;
import com.sad.sadcrm.TableUtil;
import com.sad.sadcrm.ValidationUtil;
import com.sad.sadcrm.hibernate.AddressDAO;
import com.sad.sadcrm.hibernate.ClientDAO;
import com.sad.sadcrm.hibernate.UserDAO;
import com.sad.sadcrm.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static javax.swing.SwingConstants.HORIZONTAL;

public class SadCRMForm extends javax.swing.JFrame {
    private Object[] options = {"Tak", "Nie"};

    private User loggedUser = null;

    private Client selectedClient = null;
    private User selectedUser = null;
    private User newUser = null;

    boolean myContacts = false;
    boolean mail = false;
    boolean isReport = false;

    JPopupMenu exportAdminDataMenu = new JPopupMenu();
    JMenuItem exportAdminDataSubmenu1 = new JMenuItem("Eksport wszystkich klientów");
    JMenuItem exportAdminDataSubmenu2 = new JMenuItem("Eksport klientów dodanych dziś");

    JPopupMenu exportUserDataMenu = new JPopupMenu();
    JMenuItem exportUserDataSubmenu1 = new JMenuItem("Eksport moich klientów");

    public SadCRMForm() {
        initComponents();
        setSize(1000, 700);
        PanelsUtil.enablePanel(loginPanel, new JPanel[]{userPanel, adminPanel, managerPanel});
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createPopupMenuForExport();
    }

    private void createPopupMenuForExport() {
        // MENU DLA BUTTONA EXPORTU
        exportAdminDataMenu.add(exportAdminDataSubmenu1);
        exportAdminDataMenu.add(exportAdminDataSubmenu2);

        exportUserDataMenu.add(exportUserDataSubmenu1);

        ActionListener exportAdminDataListener = this::showPopupAdmin;
        exportAdminDataSubmenu1.addActionListener(event -> ReportsUtil.exportAll());
        exportAdminDataSubmenu2.addActionListener(event -> ReportsUtil.exportTodays(now()));
        dataExpButton1.addActionListener(exportAdminDataListener);
        dataExpButton2.addActionListener(exportAdminDataListener);

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
        jDialog1 = new javax.swing.JDialog();
        changePasswordButton = new javax.swing.JButton();
        cancelChangePasswordButton = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txtChangePass1 = new javax.swing.JPasswordField();
        txtChangePass2 = new javax.swing.JPasswordField();
        jDialog2 = new javax.swing.JDialog();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        usersForManagerTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        userPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        mailButton = new javax.swing.JButton();
        exportUserDataButton = new javax.swing.JButton();
        leftPanel = new javax.swing.JPanel();
        addClientButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        mailMergeButton = new javax.swing.JButton();
        myContactsButton = new javax.swing.JButton();
        myPanelButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        mainUserPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        txtUserSurname = new javax.swing.JTextField();
        txtUserLogin = new javax.swing.JTextField();
        txtUserType = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        addClientPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtClientName = new javax.swing.JTextField();
        txtClientSurname = new javax.swing.JTextField();
        txtClientPesel = new javax.swing.JTextField();
        txtClientPhone1 = new javax.swing.JTextField();
        txtClientPhone2 = new javax.swing.JTextField();
        txtClientMail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtClientDesc = new javax.swing.JTextArea();
        txtClientCreator = new javax.swing.JTextField();
        txtClientCreateDate = new javax.swing.JTextField();
        txtClientStreet = new javax.swing.JTextField();
        txtClientNumber = new javax.swing.JTextField();
        txtCLientCity = new javax.swing.JTextField();
        saveClientButton = new javax.swing.JButton();
        cancelClientButton = new javax.swing.JButton();
        txtClientVip = new javax.swing.JCheckBox();
        cbPersonalAcc = new javax.swing.JCheckBox();
        cbCurrencyAcc = new javax.swing.JCheckBox();
        cbLocate = new javax.swing.JCheckBox();
        cbCurrenctCredit = new javax.swing.JCheckBox();
        cbHomeCredit = new javax.swing.JCheckBox();
        cbReapetedCredit = new javax.swing.JCheckBox();
        cbCreditCard = new javax.swing.JCheckBox();
        cboxChanse = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        editClientButton = new javax.swing.JButton();
        txtClientPostalCode = new javax.swing.JFormattedTextField();
        labelClientModDate = new javax.swing.JLabel();
        txtClientModification = new javax.swing.JTextField();
        lab = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtClientTel = new javax.swing.JCheckBox();
        txtClientTelDate = new javax.swing.JTextField();
        searchPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtSearchSurname = new javax.swing.JTextField();
        txtSearchPesel = new javax.swing.JTextField();
        searchSearchButton = new javax.swing.JButton();
        resetSearchButton = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableClients = new javax.swing.JTable();
        txtDetailsClientButton = new javax.swing.JButton();
        txtSendMultipleMail = new javax.swing.JButton();
        sendMailPanel = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtReceip = new javax.swing.JTextArea();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtMailContent = new javax.swing.JTextArea();
        sendOneMailButton = new javax.swing.JButton();
        clearMailButton = new javax.swing.JButton();
        sendMailButton = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtMailSubject = new javax.swing.JTextField();
        loginPanel = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        canelLoginButton = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        adminPanel = new javax.swing.JPanel();
        topPanel1 = new javax.swing.JPanel();
        dataExpButton1 = new javax.swing.JButton();
        leftPanel1 = new javax.swing.JPanel();
        addUserButton = new javax.swing.JButton();
        searchUserButton = new javax.swing.JButton();
        myAdminPanelButton = new javax.swing.JButton();
        logoutAdminButton = new javax.swing.JButton();
        exitAdminButton = new javax.swing.JButton();
        mainAdminPanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtAdminName = new javax.swing.JTextField();
        txtAdminSurname = new javax.swing.JTextField();
        txtAdminLogin = new javax.swing.JTextField();
        txtAdminType = new javax.swing.JTextField();
        changeAdminPassButton = new javax.swing.JButton();
        addUserPanel = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        txtAddUserName = new javax.swing.JTextField();
        txtAddUserSurname = new javax.swing.JTextField();
        txtAddUserLogin = new javax.swing.JTextField();
        saveUserButton = new javax.swing.JButton();
        cancelUserButton = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        txtAddUserdate = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtAddUserPassword1 = new javax.swing.JPasswordField();
        txtAddUserPassword2 = new javax.swing.JPasswordField();
        txtAddUsertype = new javax.swing.JComboBox();
        searchUserPanel = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        txtAdminSearchSurname = new javax.swing.JTextField();
        txtAdminSearchName = new javax.swing.JTextField();
        adminSearchButton = new javax.swing.JButton();
        adminResetButton = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        adminDetailsButton = new javax.swing.JButton();
        managerPanel = new javax.swing.JPanel();
        topManagerPanel = new javax.swing.JPanel();
        reportsButton = new javax.swing.JButton();
        dataExpButton2 = new javax.swing.JButton();
        leftManagerPanel = new javax.swing.JPanel();
        searchClientByManagerButton = new javax.swing.JButton();
        myManagerPanelButton = new javax.swing.JButton();
        logoutManagerButton = new javax.swing.JButton();
        exitManagerButton = new javax.swing.JButton();
        mainManagerPanel = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        txtManagerName = new javax.swing.JTextField();
        txtManagerSurname = new javax.swing.JTextField();
        txtManagerLogin = new javax.swing.JTextField();
        txtManagerType = new javax.swing.JTextField();
        changeManagerPassButton = new javax.swing.JButton();
        searchUserByManagerPanel = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        txtManagerSearchSurname = new javax.swing.JTextField();
        txtManagerSearchPesel = new javax.swing.JTextField();
        managerSearchButton = new javax.swing.JButton();
        managerResetButton = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableClientsForManager = new javax.swing.JTable();
        managerEditButton = new javax.swing.JButton();
        editUserByManagerPanel = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        editClientName = new javax.swing.JTextField();
        editClientSurname = new javax.swing.JTextField();
        editClientPesel = new javax.swing.JTextField();
        editClientPhone = new javax.swing.JTextField();
        editClientPhone2 = new javax.swing.JTextField();
        editClientMail = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        editClientDesc = new javax.swing.JTextArea();
        editClientCreator = new javax.swing.JTextField();
        editClientCreateDate = new javax.swing.JTextField();
        editClientStreet = new javax.swing.JTextField();
        editClientNumber = new javax.swing.JTextField();
        editCLientCity = new javax.swing.JTextField();
        saveClientByManagerButton = new javax.swing.JButton();
        cancelClientByManagerButton = new javax.swing.JButton();
        editClientVip = new javax.swing.JCheckBox();
        cbEditPersonalAcc = new javax.swing.JCheckBox();
        cbEditCurrencyAcc = new javax.swing.JCheckBox();
        cbEditLocate = new javax.swing.JCheckBox();
        cbEditCurrenctCredit = new javax.swing.JCheckBox();
        cbEditHomeCredit = new javax.swing.JCheckBox();
        cbEditReapetedCredit = new javax.swing.JCheckBox();
        cbEditCreditCard = new javax.swing.JCheckBox();
        cboxEditChanse = new javax.swing.JComboBox();
        jLabel85 = new javax.swing.JLabel();
        editClientPostalCode = new javax.swing.JFormattedTextField();
        labelClientModDate1 = new javax.swing.JLabel();
        editClientModification = new javax.swing.JTextField();
        lab1 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        editClientTel = new javax.swing.JCheckBox();
        editClientTelDate = new javax.swing.JTextField();
        selectUserButton = new javax.swing.JButton();
        raportsByManagerPanel = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        cboxRaportDate = new javax.swing.JComboBox();
        jLabel45 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        txtUserRaport = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        changePasswordButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Apply.gif"))); // NOI18N
        changePasswordButton.setText("OK");
        changePasswordButton.addActionListener(this::changePasswordButtonActionPerformed);

        cancelChangePasswordButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancel.gif"))); // NOI18N
        cancelChangePasswordButton.setText("Anuluj");
        cancelChangePasswordButton.addActionListener(this::cancelChangePasswordButtonActionPerformed);

        jLabel46.setText("Hasło:");

        jLabel47.setText("Powtórz hasło");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
                jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDialog1Layout.createSequentialGroup()
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jDialog1Layout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addComponent(changePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cancelChangePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jDialog1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel46)
                                                        .addComponent(jLabel47))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtChangePass1)
                                                        .addComponent(txtChangePass2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))))
                                .addContainerGap(42, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
                jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel46)
                                        .addComponent(txtChangePass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel47)
                                        .addComponent(txtChangePass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(changePasswordButton)
                                        .addComponent(cancelChangePasswordButton))
                                .addContainerGap(43, Short.MAX_VALUE))
        );

        jDialog2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog2.setMinimumSize(new java.awt.Dimension(400, 400));
        jDialog2.setResizable(false);

        jLabel54.setText("Wybierz pracownika");

        usersForManagerTable.setModel(new javax.swing.table.DefaultTableModel(
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
        usersForManagerTable.setMinimumSize(new java.awt.Dimension(376, 72));
        usersForManagerTable.setPreferredSize(new java.awt.Dimension(376, 72));
        usersForManagerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        usersForManagerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersForManagerTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(usersForManagerTable);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Apply.gif"))); // NOI18N
        jButton2.setText("Wybierz");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancel.gif"))); // NOI18N
        jButton3.setText("Zamknij");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
                jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDialog2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel54)
                                        .addGroup(jDialog2Layout.createSequentialGroup()
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton3))
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42))
        );
        jDialog2Layout.setVerticalGroup(
                jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDialog2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel54)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))
                                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        userPanel.setBackground(java.awt.Color.lightGray);
        userPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        userPanel.setPreferredSize(new java.awt.Dimension(1000, 800));

        topPanel.setBackground(java.awt.Color.lightGray);
        topPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        topPanel.setForeground(new java.awt.Color(238, 9, 9));
        topPanel.setPreferredSize(new java.awt.Dimension(1000, 50));

        mailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/E-mail.gif"))); // NOI18N
        mailButton.setText("Wyślij wiadomość");
        mailButton.addActionListener(this::wyslijMailAction);

        exportUserDataButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Upload.gif"))); // NOI18N
        exportUserDataButton.setText("Eksport danych");

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
                topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addGap(196, 196, 196)
                                .addComponent(mailButton)
                                .addGap(18, 18, 18)
                                .addComponent(exportUserDataButton)
                                .addContainerGap(478, Short.MAX_VALUE))
        );
        topPanelLayout.setVerticalGroup(
                topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(mailButton)
                                        .addComponent(exportUserDataButton))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        leftPanel.setBackground(java.awt.Color.lightGray);
        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new java.awt.Dimension(200, 750));

        addClientButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Create.gif"))); // NOI18N
        addClientButton.setText("Dodaj klienta");
        addClientButton.addActionListener(this::dodajKlientaAction);

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Find.gif"))); // NOI18N
        searchButton.setText("Wyszukiwanie");
        searchButton.addActionListener(this::wyszukiwanieAction);

        mailMergeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Mail.gif"))); // NOI18N
        mailMergeButton.setText("Korespondencja seryjna");
        mailMergeButton.addActionListener(this::korespondencjaSeryjnaAction);

        myContactsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Address book.gif"))); // NOI18N
        myContactsButton.setText("Tylko moje kontakty");
        myContactsButton.addActionListener(this::mojeKontaktyAction);

        myPanelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Info.gif"))); // NOI18N
        myPanelButton.setText("Mój panel");
        myPanelButton.addActionListener(this::mojPanelAction);

        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit.gif"))); // NOI18N
        logoutButton.setText("Wyloguj");
        logoutButton.addActionListener(this::wylogujUserAction);

        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Turn off.gif"))); // NOI18N
        exitButton.setText("Wyjście");
        exitButton.addActionListener(this::exitUserAction);

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
                leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(addClientButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mailMergeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myContactsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myPanelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
                leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftPanelLayout.createSequentialGroup()
                                .addComponent(addClientButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mailMergeButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(myContactsButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(myPanelButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logoutButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exitButton)
                                .addGap(0, 500, Short.MAX_VALUE))
        );

        mainUserPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        mainUserPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel3.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel3.setText("Zalogowany uzytkownik:");

        jLabel25.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel25.setText("Imię:");

        jLabel26.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel26.setText("Nazwisko:");

        jLabel27.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel27.setText("Typ konta:");

        jLabel28.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel28.setText("Login:");

        txtUserName.setEditable(false);

        txtUserSurname.setEditable(false);

        txtUserLogin.setEditable(false);

        txtUserType.setEditable(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Repair.gif"))); // NOI18N
        jButton1.setText("Zmień hasło");
        jButton1.addActionListener(this::zmienHasloUzytkAction);

        javax.swing.GroupLayout mainUserPanelLayout = new javax.swing.GroupLayout(mainUserPanel);
        mainUserPanel.setLayout(mainUserPanelLayout);
        mainUserPanelLayout.setHorizontalGroup(
                mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainUserPanelLayout.createSequentialGroup()
                                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel25)
                                                        .addComponent(jLabel26)
                                                        .addComponent(jLabel27)
                                                        .addComponent(jLabel28))
                                                .addGap(52, 52, 52)
                                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtUserSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton1)))
                                        .addComponent(jLabel3))
                                .addContainerGap(293, Short.MAX_VALUE))
        );
        mainUserPanelLayout.setVerticalGroup(
                mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addGap(30, 30, 30)
                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel25)
                                        .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel26)
                                        .addComponent(txtUserSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel27)
                                        .addComponent(txtUserType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel28)
                                        .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addContainerGap(499, Short.MAX_VALUE))
        );

        addClientPanel.setBorder(null);
        addClientPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel2.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel2.setText("Dodanie nowego klienta");

        jLabel4.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel4.setText("Imię*:");

        jLabel5.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel5.setText("Nazwisko*:");

        jLabel6.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel6.setText("Pesel*");

        jLabel7.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel7.setText("Nr telefonu 1");

        jLabel8.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel8.setText("Nr telefonu 2");

        jLabel9.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel9.setText("Klient VIP");

        jLabel10.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel10.setText("Adres mail");

        jLabel11.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel11.setText("Uwagi");

        jLabel12.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel12.setText("Utworzony przez");

        jLabel13.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel13.setText("Data utworzenia");

        jLabel15.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel15.setText("Ulica*");

        jLabel16.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel16.setText("Nr budynku*");

        jLabel17.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel17.setText("Miasto*");

        jLabel18.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel18.setText("Kod pocztowy*");

        jLabel19.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel19.setText("Produkty");

        txtClientDesc.setColumns(20);
        txtClientDesc.setRows(5);
        jScrollPane1.setViewportView(txtClientDesc);

        txtClientCreator.setEnabled(false);

        txtClientCreateDate.setEnabled(false);

        saveClientButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Save.gif"))); // NOI18N
        saveClientButton.setText("Zapisz");
        saveClientButton.addActionListener(this::zapiszKlientaAction);

        cancelClientButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancel.gif"))); // NOI18N
        cancelClientButton.setText("Anuluj");
        cancelClientButton.addActionListener(this::anulujZapisKlientaAction);

        cbPersonalAcc.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbPersonalAcc.setText("Konto osobiste");

        cbCurrencyAcc.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbCurrencyAcc.setText("Konto walutowe");

        cbLocate.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbLocate.setText("Lokata");

        cbCurrenctCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbCurrenctCredit.setText("Kredyt gotówkowy");

        cbHomeCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbHomeCredit.setText("Kredyt hipoteczny");

        cbReapetedCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbReapetedCredit.setText("Kredyt odnawialny");

        cbCreditCard.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbCreditCard.setText("Karta kredytowa");

        cboxChanse.setModel(new DefaultComboBoxModel(new String[]{"Wybierz", "Konto osobiste", "Konto walutowe", "Lokata", "Kredyt gotówkowy", "Kredyt hipoteczny", "Kredyt odnawialny", "Karta kredytowa"}));

        jLabel14.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel14.setText("Szansa sprzedaży");

        editClientButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Repair.gif"))); // NOI18N
        editClientButton.setText("Edytuj");
        editClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edytujKlientaAction(evt);
            }
        });

        try {
            txtClientPostalCode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelClientModDate.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        labelClientModDate.setText("Data modyfikacji");

        txtClientModification.setEditable(false);
        txtClientModification.setEnabled(false);

        lab.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        lab.setText("Telefon do klienta");

        jLabel44.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel44.setText("Data telefonu");

        txtClientTel.setEnabled(false);
        txtClientTel.addActionListener(this::txtClientTelActionPerformed);

        txtClientTelDate.setEnabled(false);

        javax.swing.GroupLayout addClientPanelLayout = new javax.swing.GroupLayout(addClientPanel);
        addClientPanel.setLayout(addClientPanelLayout);
        addClientPanelLayout.setHorizontalGroup(
                addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel13)
                                                        .addComponent(jLabel9)
                                                        .addComponent(jLabel12)
                                                        .addComponent(labelClientModDate))
                                                .addGap(6, 6, 6)
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtClientCreateDate)
                                                        .addComponent(txtClientVip)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addComponent(saveClientButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cancelClientButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(editClientButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(txtClientModification, javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(txtClientCreator, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))))
                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel8)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel7)
                                                                .addGap(35, 35, 35)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtClientPhone1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                                                        .addComponent(txtClientName)
                                                                        .addComponent(txtClientSurname)
                                                                        .addComponent(txtClientPesel)
                                                                        .addComponent(txtClientPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel4)
                                                                        .addComponent(jLabel10)
                                                                        .addComponent(jLabel11))
                                                                .addGap(59, 59, 59)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(txtClientMail, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(cbCurrencyAcc)
                                                                        .addComponent(cbPersonalAcc)
                                                                        .addComponent(cbLocate)
                                                                        .addComponent(cbCurrenctCredit)
                                                                        .addComponent(cbHomeCredit)
                                                                        .addComponent(cbReapetedCredit)
                                                                        .addComponent(cbCreditCard)
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel44)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(txtClientTelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel14)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(cboxChanse, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(lab)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(txtClientTel))))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(14, 14, 14)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel18)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(txtClientPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addClientPanelLayout.createSequentialGroup()
                                                                                        .addComponent(jLabel15)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(txtClientStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addClientPanelLayout.createSequentialGroup()
                                                                                        .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(jLabel16)
                                                                                                .addComponent(jLabel17)
                                                                                                .addComponent(jLabel19))
                                                                                        .addGap(41, 41, 41)
                                                                                        .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                .addComponent(txtCLientCity, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                                                                                .addComponent(txtClientNumber, javax.swing.GroupLayout.Alignment.TRAILING))))))))
                                        .addComponent(jLabel2))
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        addClientPanelLayout.linkSize(HORIZONTAL, new java.awt.Component[]{txtClientCreateDate, txtClientName, txtClientPesel, txtClientPhone1, txtClientPhone2, txtClientSurname});

        addClientPanelLayout.linkSize(HORIZONTAL, new java.awt.Component[]{txtCLientCity, txtClientNumber, txtClientPostalCode});

        addClientPanelLayout.setVerticalGroup(
                addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtClientVip)
                                                .addGap(7, 7, 7)
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel13)
                                                        .addComponent(txtClientCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtClientTelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel44))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel12)
                                                        .addComponent(txtClientCreator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelClientModDate)
                                                        .addComponent(txtClientModification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(19, 19, 19))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addClientPanelLayout.createSequentialGroup()
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(2, 2, 2)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel15)
                                                                        .addComponent(txtClientStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel16)
                                                                        .addComponent(txtClientNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel17)
                                                                        .addComponent(txtCLientCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel18)
                                                                        .addComponent(txtClientPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel19)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbPersonalAcc)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbCurrencyAcc)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbLocate)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbCurrenctCredit)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbHomeCredit)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbReapetedCredit)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbCreditCard)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel14)
                                                                        .addComponent(cboxChanse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel4)
                                                                        .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel5)
                                                                        .addComponent(txtClientSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel6)
                                                                        .addComponent(txtClientPesel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtClientPhone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel7))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel8)
                                                                        .addComponent(txtClientPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(txtClientMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel10))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel11)
                                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addGap(11, 11, 11)
                                                                .addComponent(jLabel9))
                                                        .addGroup(addClientPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(txtClientTel)
                                                                        .addComponent(lab))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)))
                                .addGroup(addClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveClientButton)
                                        .addComponent(cancelClientButton)
                                        .addComponent(editClientButton))
                                .addContainerGap(87, Short.MAX_VALUE))
        );

        searchPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel1.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel1.setText("Wyszukiwanie klienta");

        jLabel29.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel29.setText("Nazwisko:");

        jLabel30.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel30.setText("Pesel:");

        searchSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search.gif"))); // NOI18N
        searchSearchButton.setText("Szukaj");
        searchSearchButton.addActionListener(this::szukajKlientaAction);

        resetSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Refresh.gif"))); // NOI18N
        resetSearchButton.setText("Reset");
        resetSearchButton.addActionListener(this::resetPolSzukaniaKlientaAction);

        tableClients.setModel(new javax.swing.table.DefaultTableModel(
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
        tableClients.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableClients.setPreferredSize(new java.awt.Dimension(780, 100));
        tableClients.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableClients.getTableHeader().setResizingAllowed(false);
        tableClients.getTableHeader().setReorderingAllowed(false);
        tableClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientsMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tableClients);

        txtDetailsClientButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/About.gif"))); // NOI18N
        txtDetailsClientButton.setText("Szczegóły");
        txtDetailsClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                szczegolyKlientaAction(evt);
            }
        });

        txtSendMultipleMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/E-mail.gif"))); // NOI18N
        txtSendMultipleMail.setText("Wyślij wiadomość do zanaczonych kontaktów");
        txtSendMultipleMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyslijMailDoWszystkichAction(evt);
            }
        });

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
                searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(searchPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(searchPanelLayout.createSequentialGroup()
                                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addGroup(searchPanelLayout.createSequentialGroup()
                                                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel29)
                                                                        .addComponent(jLabel30))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(txtSearchSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                                        .addComponent(txtSearchPesel))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(searchSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                                        .addComponent(resetSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addGroup(searchPanelLayout.createSequentialGroup()
                                                                .addComponent(txtDetailsClientButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtSendMultipleMail)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        searchPanelLayout.setVerticalGroup(
                searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(searchPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(30, 30, 30)
                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel29)
                                        .addComponent(txtSearchSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchSearchButton))
                                .addGap(4, 4, 4)
                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel30)
                                        .addComponent(txtSearchPesel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(resetSearchButton))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDetailsClientButton)
                                        .addComponent(txtSendMultipleMail))
                                .addContainerGap(26, Short.MAX_VALUE))
        );

        sendMailPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel32.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel32.setText("Wyślij wiadomość");

        jLabel34.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel34.setText("Odbiorcy:");

        txtReceip.setColumns(20);
        txtReceip.setRows(5);
        jScrollPane5.setViewportView(txtReceip);

        jLabel35.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel35.setText("Wiadomość:");

        txtMailContent.setColumns(20);
        txtMailContent.setRows(5);
        jScrollPane6.setViewportView(txtMailContent);

        sendOneMailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Mail.gif"))); // NOI18N
        sendOneMailButton.setText("Wyślij");
        sendOneMailButton.addActionListener(this::wyslijjednegoMailaAction);

        clearMailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Refresh.gif"))); // NOI18N
        clearMailButton.setText("Wyczyść");
        clearMailButton.addActionListener(this::wyczyscPolaMailaAction);

        sendMailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancel.gif"))); // NOI18N
        sendMailButton.setText("Anuluj");
        sendMailButton.addActionListener(this::wyslijMail2Action);

        jLabel36.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel36.setText("Temat:");

        javax.swing.GroupLayout sendMailPanelLayout = new javax.swing.GroupLayout(sendMailPanel);
        sendMailPanel.setLayout(sendMailPanelLayout);
        sendMailPanelLayout.setHorizontalGroup(
                sendMailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(sendMailPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(sendMailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                                        .addComponent(jLabel32)
                                        .addComponent(jLabel34)
                                        .addComponent(jLabel35)
                                        .addComponent(jScrollPane5)
                                        .addGroup(sendMailPanelLayout.createSequentialGroup()
                                                .addComponent(sendOneMailButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(clearMailButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sendMailButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(sendMailPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel36)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtMailSubject)))
                                .addContainerGap(233, Short.MAX_VALUE))
        );
        sendMailPanelLayout.setVerticalGroup(
                sendMailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(sendMailPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel32)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(sendMailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel36)
                                        .addComponent(txtMailSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(sendMailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(sendOneMailButton)
                                        .addComponent(clearMailButton)
                                        .addComponent(sendMailButton))
                                .addContainerGap(188, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(userPanelLayout.createSequentialGroup()
                                                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(mainUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(addClientPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, 0))
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(userPanelLayout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(sendMailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );
        userPanelLayout.setVerticalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addClientPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mainUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0))
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(userPanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(sendMailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );

        loginPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel21.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel21.setText("LOGOWANIE DO SYSTEMU SadCRM");

        jLabel22.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel22.setText("Login");

        jLabel23.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel23.setText("Hasło");

        txtLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginActionPerformed(evt);
            }
        });

        loginButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Blue key.gif"))); // NOI18N
        loginButton.setText("Zaloguj");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logowanieAction(evt);
            }
        });

        canelLoginButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit.gif"))); // NOI18N
        canelLoginButton.setText("Wyjście");
        canelLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyjscieAction(evt);
            }
        });

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
                loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                                .addContainerGap(278, Short.MAX_VALUE)
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(loginPanelLayout.createSequentialGroup()
                                                .addGap(41, 41, 41)
                                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel22)
                                                        .addComponent(jLabel23))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtLogin)
                                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jLabel21)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                                                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(canelLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(141, 141, 141)))
                                .addGap(57, 57, 57))
        );
        loginPanelLayout.setVerticalGroup(
                loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                                .addContainerGap(261, Short.MAX_VALUE)
                                .addComponent(jLabel21)
                                .addGap(48, 48, 48)
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel22)
                                        .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel23)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginButton)
                                        .addComponent(canelLoginButton))
                                .addGap(158, 158, 158))
        );

        adminPanel.setBackground(java.awt.Color.lightGray);
        adminPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminPanel.setPreferredSize(new java.awt.Dimension(1000, 800));

        topPanel1.setBackground(java.awt.Color.lightGray);
        topPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        topPanel1.setForeground(new java.awt.Color(238, 9, 9));
        topPanel1.setPreferredSize(new java.awt.Dimension(1000, 50));

        dataExpButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Upload.gif"))); // NOI18N
        dataExpButton1.setText("Eksport danych");
        dataExpButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataExpButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanel1Layout = new javax.swing.GroupLayout(topPanel1);
        topPanel1.setLayout(topPanel1Layout);
        topPanel1Layout.setHorizontalGroup(
                topPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topPanel1Layout.createSequentialGroup()
                                .addGap(194, 194, 194)
                                .addComponent(dataExpButton1)
                                .addContainerGap(658, Short.MAX_VALUE))
        );
        topPanel1Layout.setVerticalGroup(
                topPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanel1Layout.createSequentialGroup()
                                .addContainerGap(16, Short.MAX_VALUE)
                                .addComponent(dataExpButton1)
                                .addContainerGap())
        );

        leftPanel1.setBackground(java.awt.Color.lightGray);
        leftPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        leftPanel1.setOpaque(false);
        leftPanel1.setPreferredSize(new java.awt.Dimension(200, 750));

        addUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Create.gif"))); // NOI18N
        addUserButton.setText("Dodaj użytkownika");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        searchUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Find.gif"))); // NOI18N
        searchUserButton.setText("Wyszukiwanie");
        searchUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUserButtonActionPerformed(evt);
            }
        });

        myAdminPanelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Info.gif"))); // NOI18N
        myAdminPanelButton.setText("Mój panel");
        myAdminPanelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myAdminPanelButtonActionPerformed(evt);
            }
        });

        logoutAdminButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit.gif"))); // NOI18N
        logoutAdminButton.setText("Wylogowanie");
        logoutAdminButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutAdminButtonActionPerformed(evt);
            }
        });

        exitAdminButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Turn off.gif"))); // NOI18N
        exitAdminButton.setText("Wyjście");
        exitAdminButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitAdminButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPanel1Layout = new javax.swing.GroupLayout(leftPanel1);
        leftPanel1.setLayout(leftPanel1Layout);
        leftPanel1Layout.setHorizontalGroup(
                leftPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(addUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                        .addComponent(searchUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myAdminPanelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutAdminButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitAdminButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        leftPanel1Layout.setVerticalGroup(
                leftPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftPanel1Layout.createSequentialGroup()
                                .addComponent(addUserButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchUserButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(myAdminPanelButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logoutAdminButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exitAdminButton)
                                .addGap(0, 572, Short.MAX_VALUE))
        );

        mainAdminPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        mainAdminPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel20.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel20.setText("Zalogowany uzytkownik:");

        jLabel31.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel31.setText("Imię:");

        jLabel33.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel33.setText("Nazwisko:");

        jLabel37.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel37.setText("Typ konta:");

        jLabel38.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel38.setText("Login:");

        txtAdminName.setEditable(false);

        txtAdminSurname.setEditable(false);

        txtAdminLogin.setEditable(false);

        txtAdminType.setEditable(false);

        changeAdminPassButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Repair.gif"))); // NOI18N
        changeAdminPassButton.setText("Zmiana hasła");
        changeAdminPassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeAdminPassButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainAdminPanelLayout = new javax.swing.GroupLayout(mainAdminPanel);
        mainAdminPanel.setLayout(mainAdminPanelLayout);
        mainAdminPanelLayout.setHorizontalGroup(
                mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainAdminPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainAdminPanelLayout.createSequentialGroup()
                                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel31)
                                                        .addComponent(jLabel33)
                                                        .addComponent(jLabel37)
                                                        .addComponent(jLabel38))
                                                .addGap(52, 52, 52)
                                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtAdminLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtAdminSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtAdminName, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtAdminType, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(changeAdminPassButton)))
                                        .addComponent(jLabel20))
                                .addContainerGap(293, Short.MAX_VALUE))
        );
        mainAdminPanelLayout.setVerticalGroup(
                mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainAdminPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel20)
                                .addGap(30, 30, 30)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel31)
                                        .addComponent(txtAdminName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel33)
                                        .addComponent(txtAdminSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel37)
                                        .addComponent(txtAdminType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel38)
                                        .addComponent(txtAdminLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(changeAdminPassButton)
                                .addContainerGap(499, Short.MAX_VALUE))
        );

        addUserPanel.setBorder(null);
        addUserPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel24.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel24.setText("Dodanie nowego użytkownika");

        jLabel39.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel39.setText("Imię*:");

        jLabel40.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel40.setText("Nazwisko*:");

        jLabel41.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel41.setText("Typ*:");

        jLabel42.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel42.setText("Hasło*:");

        jLabel48.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel48.setText("Data utworzenia");

        saveUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Save.gif"))); // NOI18N
        saveUserButton.setText("Zapisz");
        saveUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveUserButtonActionPerformed(evt);
            }
        });

        cancelUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancel.gif"))); // NOI18N
        cancelUserButton.setText("Anuluj");
        cancelUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelUserButtonActionPerformed(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel58.setText("Powtórz hasło*:");

        txtAddUserdate.setEditable(false);
        txtAddUserdate.setEnabled(false);

        jLabel43.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel43.setText("Login*:");

        txtAddUsertype.setModel(new DefaultComboBoxModel(new String[]{"Pracownik", "Manager", "Administrator"}));

        javax.swing.GroupLayout addUserPanelLayout = new javax.swing.GroupLayout(addUserPanel);
        addUserPanel.setLayout(addUserPanelLayout);
        addUserPanelLayout.setHorizontalGroup(
                addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel41)
                                                .addGap(130, 130, 130)
                                                .addComponent(txtAddUsertype, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel40)
                                        .addComponent(jLabel24)
                                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel42)
                                                        .addComponent(jLabel39)
                                                        .addComponent(jLabel43)
                                                        .addComponent(jLabel58)
                                                        .addComponent(jLabel48))
                                                .addGap(18, 18, 18)
                                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                                                .addComponent(saveUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cancelUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(txtAddUserPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtAddUserPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtAddUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtAddUserSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtAddUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtAddUserdate, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(288, Short.MAX_VALUE))
        );
        addUserPanelLayout.setVerticalGroup(
                addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(addUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel39)
                                        .addComponent(txtAddUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel40)
                                        .addComponent(txtAddUserSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtAddUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel43))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel41)
                                        .addComponent(txtAddUsertype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel42)
                                        .addComponent(txtAddUserPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel58)
                                        .addComponent(txtAddUserPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel48)
                                        .addComponent(txtAddUserdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveUserButton)
                                        .addComponent(cancelUserButton))
                                .addContainerGap(415, Short.MAX_VALUE))
        );

        searchUserPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel55.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel55.setText("Wyszukiwanie użytkowników");

        jLabel56.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel56.setText("Nazwisko:");

        jLabel57.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel57.setText("Imię:");

        adminSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search.gif"))); // NOI18N
        adminSearchButton.setText("Szukaj");
        adminSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminSearchButtonActionPerformed(evt);
            }
        });

        adminResetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Refresh.gif"))); // NOI18N
        adminResetButton.setText("Reset");
        adminResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminResetButtonActionPerformed(evt);
            }
        });

        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
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
        tableUsers.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableUsers.setPreferredSize(new java.awt.Dimension(780, 100));
        tableUsers.getTableHeader().setResizingAllowed(false);
        tableUsers.getTableHeader().setReorderingAllowed(false);
        tableUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUsersMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tableUsers);

        adminDetailsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Repair.gif"))); // NOI18N
        adminDetailsButton.setText("Edycja");
        adminDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminDetailsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchUserPanelLayout = new javax.swing.GroupLayout(searchUserPanel);
        searchUserPanel.setLayout(searchUserPanelLayout);
        searchUserPanelLayout.setHorizontalGroup(
                searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                                .addGroup(searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel55)
                                                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                                                .addGroup(searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel56)
                                                                        .addComponent(jLabel57))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(txtAdminSearchSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                                        .addComponent(txtAdminSearchName))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(adminSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                                        .addComponent(adminResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addComponent(adminDetailsButton))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        searchUserPanelLayout.setVerticalGroup(
                searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(searchUserPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel55)
                                .addGap(30, 30, 30)
                                .addGroup(searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel56)
                                        .addComponent(txtAdminSearchSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(adminSearchButton))
                                .addGap(4, 4, 4)
                                .addGroup(searchUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel57)
                                        .addComponent(txtAdminSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(adminResetButton))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adminDetailsButton)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout adminPanelLayout = new javax.swing.GroupLayout(adminPanel);
        adminPanel.setLayout(adminPanelLayout);
        adminPanelLayout.setHorizontalGroup(
                adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(adminPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(topPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(adminPanelLayout.createSequentialGroup()
                                                .addComponent(leftPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(mainAdminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(searchUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(addUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, 0))
        );
        adminPanelLayout.setVerticalGroup(
                adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(adminPanelLayout.createSequentialGroup()
                                .addComponent(topPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(leftPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mainAdminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0))
        );

        managerPanel.setBackground(java.awt.Color.lightGray);
        managerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        managerPanel.setPreferredSize(new java.awt.Dimension(1000, 800));

        topManagerPanel.setBackground(java.awt.Color.lightGray);
        topManagerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        topManagerPanel.setForeground(new java.awt.Color(238, 9, 9));
        topManagerPanel.setPreferredSize(new java.awt.Dimension(1000, 50));

        reportsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/3d bar chart.gif"))); // NOI18N
        reportsButton.setText("Raporty");
        reportsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportsButtonActionPerformed(evt);
            }
        });

        dataExpButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Upload.gif"))); // NOI18N
        dataExpButton2.setText("Eksport danych");
        dataExpButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataExpButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topManagerPanelLayout = new javax.swing.GroupLayout(topManagerPanel);
        topManagerPanel.setLayout(topManagerPanelLayout);
        topManagerPanelLayout.setHorizontalGroup(
                topManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topManagerPanelLayout.createSequentialGroup()
                                .addGap(196, 196, 196)
                                .addComponent(reportsButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dataExpButton2)
                                .addContainerGap(561, Short.MAX_VALUE))
        );
        topManagerPanelLayout.setVerticalGroup(
                topManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(topManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(reportsButton)
                                        .addComponent(dataExpButton2))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        leftManagerPanel.setBackground(java.awt.Color.lightGray);
        leftManagerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        leftManagerPanel.setOpaque(false);
        leftManagerPanel.setPreferredSize(new java.awt.Dimension(200, 750));

        searchClientByManagerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Find.gif"))); // NOI18N
        searchClientByManagerButton.setText("Wyszukiwanie");
        searchClientByManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchClientByManagerButtonActionPerformed(evt);
            }
        });

        myManagerPanelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Info.gif"))); // NOI18N
        myManagerPanelButton.setText("Mój panel");
        myManagerPanelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myManagerPanelButtonActionPerformed(evt);
            }
        });

        logoutManagerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit.gif"))); // NOI18N
        logoutManagerButton.setText("Wylogowanie");
        logoutManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutManagerButtonActionPerformed(evt);
            }
        });

        exitManagerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Turn off.gif"))); // NOI18N
        exitManagerButton.setText("Wyjście");
        exitManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitManagerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftManagerPanelLayout = new javax.swing.GroupLayout(leftManagerPanel);
        leftManagerPanel.setLayout(leftManagerPanelLayout);
        leftManagerPanelLayout.setHorizontalGroup(
                leftManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(searchClientByManagerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                        .addComponent(myManagerPanelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutManagerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitManagerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        leftManagerPanelLayout.setVerticalGroup(
                leftManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftManagerPanelLayout.createSequentialGroup()
                                .addComponent(searchClientByManagerButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(myManagerPanelButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logoutManagerButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exitManagerButton)
                                .addGap(0, 608, Short.MAX_VALUE))
        );

        mainManagerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        mainManagerPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel49.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel49.setText("Zalogowany manager:");

        jLabel50.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel50.setText("Imię:");

        jLabel51.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel51.setText("Nazwisko:");

        jLabel52.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel52.setText("Typ konta:");

        jLabel53.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel53.setText("Login:");

        txtManagerName.setEditable(false);

        txtManagerSurname.setEditable(false);

        txtManagerLogin.setEditable(false);

        txtManagerType.setEditable(false);

        changeManagerPassButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Repair.gif"))); // NOI18N
        changeManagerPassButton.setText("Zmiana hasła");
        changeManagerPassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeManagerPassButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainManagerPanelLayout = new javax.swing.GroupLayout(mainManagerPanel);
        mainManagerPanel.setLayout(mainManagerPanelLayout);
        mainManagerPanelLayout.setHorizontalGroup(
                mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainManagerPanelLayout.createSequentialGroup()
                                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel50)
                                                        .addComponent(jLabel51)
                                                        .addComponent(jLabel52)
                                                        .addComponent(jLabel53))
                                                .addGap(52, 52, 52)
                                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtManagerLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtManagerSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtManagerName, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtManagerType, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(changeManagerPassButton)))
                                        .addComponent(jLabel49))
                                .addContainerGap(293, Short.MAX_VALUE))
        );
        mainManagerPanelLayout.setVerticalGroup(
                mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel49)
                                .addGap(30, 30, 30)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel50)
                                        .addComponent(txtManagerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel51)
                                        .addComponent(txtManagerSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel52)
                                        .addComponent(txtManagerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel53)
                                        .addComponent(txtManagerLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(changeManagerPassButton)
                                .addContainerGap(499, Short.MAX_VALUE))
        );

        searchUserByManagerPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel66.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel66.setText("Wyszukiwanie klienta:");

        jLabel67.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel67.setText("Nazwisko:");

        jLabel68.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel68.setText("Pesel:");

        managerSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search.gif"))); // NOI18N
        managerSearchButton.setText("Szukaj");
        managerSearchButton.addActionListener(this::managerSearchButtonActionPerformed);

        managerResetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Refresh.gif"))); // NOI18N
        managerResetButton.setText("Reset");
        managerResetButton.addActionListener(this::managerResetFieldsAction);

        jScrollPane9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane9MouseClicked(evt);
            }
        });

        tableClientsForManager.setModel(new javax.swing.table.DefaultTableModel(
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
        tableClientsForManager.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableClientsForManager.setPreferredSize(new java.awt.Dimension(780, 100));
        tableClientsForManager.getTableHeader().setResizingAllowed(false);
        tableClientsForManager.getTableHeader().setReorderingAllowed(false);
        tableClientsForManager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientsForManagerMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tableClientsForManager);

        managerEditButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Repair.gif"))); // NOI18N
        managerEditButton.setText("Edycja");
        managerEditButton.addActionListener(this::managerEditButtonActionPerformed);

        javax.swing.GroupLayout searchUserByManagerPanelLayout = new javax.swing.GroupLayout(searchUserByManagerPanel);
        searchUserByManagerPanel.setLayout(searchUserByManagerPanelLayout);
        searchUserByManagerPanelLayout.setHorizontalGroup(
                searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel66)
                                                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel67)
                                                                        .addComponent(jLabel68))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(txtManagerSearchSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                                        .addComponent(txtManagerSearchPesel))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(managerSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                                        .addComponent(managerResetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addComponent(managerEditButton))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        searchUserByManagerPanelLayout.setVerticalGroup(
                searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(searchUserByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel66)
                                .addGap(30, 30, 30)
                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel67)
                                        .addComponent(txtManagerSearchSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(managerSearchButton))
                                .addGap(4, 4, 4)
                                .addGroup(searchUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel68)
                                        .addComponent(txtManagerSearchPesel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(managerResetButton))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(managerEditButton)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        editUserByManagerPanel.setBorder(null);
        editUserByManagerPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel69.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel69.setText("Edycja klienta");

        jLabel70.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel70.setText("Imię*:");

        jLabel71.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel71.setText("Nazwisko*:");

        jLabel72.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel72.setText("Pesel*");

        jLabel73.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel73.setText("Nr telefonu 1");

        jLabel74.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel74.setText("Nr telefonu 2");

        jLabel75.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel75.setText("Klient VIP");

        jLabel76.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel76.setText("Adres mail");

        jLabel77.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel77.setText("Uwagi");

        jLabel78.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel78.setText("Utworzony przez");

        jLabel79.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel79.setText("Data utworzenia");

        jLabel80.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel80.setText("Ulica*");

        jLabel81.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel81.setText("Nr budynku*");

        jLabel82.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel82.setText("Miasto*");

        jLabel83.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel83.setText("Kod pocztowy*");

        jLabel84.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel84.setText("Produkty");

        editClientDesc.setColumns(20);
        editClientDesc.setRows(5);
        jScrollPane2.setViewportView(editClientDesc);

        editClientCreator.setEditable(false);

        editClientCreateDate.setEnabled(false);

        saveClientByManagerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Save.gif"))); // NOI18N
        saveClientByManagerButton.setText("Zapisz");
        saveClientByManagerButton.addActionListener(this::saveClientByManagerButtonzapiszKlientaAction);

        cancelClientByManagerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancel.gif"))); // NOI18N
        cancelClientByManagerButton.setText("Anuluj");
        cancelClientByManagerButton.addActionListener(this::cancelClientByManagerButtonanulujZapisKlientaAction);

        cbEditPersonalAcc.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbEditPersonalAcc.setText("Konto osobiste");

        cbEditCurrencyAcc.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbEditCurrencyAcc.setText("Konto walutowe");

        cbEditLocate.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbEditLocate.setText("Lokata");

        cbEditCurrenctCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbEditCurrenctCredit.setText("Kredyt gotówkowy");

        cbEditHomeCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbEditHomeCredit.setText("Kredyt hipoteczny");

        cbEditReapetedCredit.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbEditReapetedCredit.setText("Kredyt odnawialny");

        cbEditCreditCard.setFont(new java.awt.Font("Times New Roman,", 0, 18)); // NOI18N
        cbEditCreditCard.setText("Karta kredytowa");

        cboxEditChanse.setModel(new DefaultComboBoxModel(new String[]{"Wybierz", "Konto osobiste", "Konto walutowe", "Lokata", "Kredyt gotówkowy", "Kredyt hipoteczny", "Kredyt odnawialny", "Karta kredytowa"}));

        jLabel85.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel85.setText("Szansa sprzedaży");

        try {
            editClientPostalCode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelClientModDate1.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        labelClientModDate1.setText("Data modyfikacji");

        editClientModification.setEditable(false);
        editClientModification.setEnabled(false);

        lab1.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        lab1.setText("Telefon do klienta");

        jLabel86.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel86.setText("Data telefonu");

        editClientTel.addActionListener(this::editClientTelActionPerformed);

        selectUserButton.setText("...");
        selectUserButton.addActionListener(this::selectUserButtonActionPerformed);

        javax.swing.GroupLayout editUserByManagerPanelLayout = new javax.swing.GroupLayout(editUserByManagerPanel);
        editUserByManagerPanel.setLayout(editUserByManagerPanelLayout);
        editUserByManagerPanelLayout.setHorizontalGroup(
                editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel69)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel79)
                                                                        .addComponent(jLabel75)
                                                                        .addComponent(jLabel78)
                                                                        .addComponent(labelClientModDate1))
                                                                .addGap(6, 6, 6)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(editClientVip)
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(saveClientByManagerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(cancelClientByManagerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editUserByManagerPanelLayout.createSequentialGroup()
                                                                                        .addComponent(editClientCreator, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(selectUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                        .addComponent(editClientCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(editClientModification, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel72)
                                                        .addComponent(jLabel71)
                                                        .addComponent(jLabel74)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel73)
                                                                .addGap(35, 35, 35)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(editClientPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                                                        .addComponent(editClientName)
                                                                        .addComponent(editClientSurname)
                                                                        .addComponent(editClientPesel)
                                                                        .addComponent(editClientPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel70)
                                                                        .addComponent(jLabel76)
                                                                        .addComponent(jLabel77))
                                                                .addGap(59, 59, 59)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(editClientMail, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(cbEditCurrencyAcc)
                                                                        .addComponent(cbEditPersonalAcc)
                                                                        .addComponent(cbEditLocate)
                                                                        .addComponent(cbEditCurrenctCredit)
                                                                        .addComponent(cbEditHomeCredit)
                                                                        .addComponent(cbEditReapetedCredit)
                                                                        .addComponent(cbEditCreditCard)
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel86)
                                                                                .addGap(57, 57, 57)
                                                                                .addComponent(editClientTelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel85)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(cboxEditChanse, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(lab1)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(editClientTel))))
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGap(14, 14, 14)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                                .addComponent(jLabel83)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(editClientPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editUserByManagerPanelLayout.createSequentialGroup()
                                                                                        .addComponent(jLabel80)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                                                                                        .addComponent(editClientStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editUserByManagerPanelLayout.createSequentialGroup()
                                                                                        .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(jLabel81)
                                                                                                .addComponent(jLabel82)
                                                                                                .addComponent(jLabel84))
                                                                                        .addGap(41, 41, 41)
                                                                                        .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                .addComponent(editCLientCity, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                                                                                .addComponent(editClientNumber, javax.swing.GroupLayout.Alignment.TRAILING)))))))
                                                .addGap(23, 23, 23))))
        );
        editUserByManagerPanelLayout.setVerticalGroup(
                editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel69)
                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(editClientVip)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel79)
                                                        .addComponent(editClientCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(editClientTelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel86))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel78)
                                                        .addComponent(editClientCreator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(selectUserButton))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelClientModDate1)
                                                        .addComponent(editClientModification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(19, 19, 19))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editUserByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGap(2, 2, 2)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel80)
                                                                        .addComponent(editClientStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel81)
                                                                        .addComponent(editClientNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel82)
                                                                        .addComponent(editCLientCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel83)
                                                                        .addComponent(editClientPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel84)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbEditPersonalAcc)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbEditCurrencyAcc)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbEditLocate)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbEditCurrenctCredit)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbEditHomeCredit)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbEditReapetedCredit)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cbEditCreditCard)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel85)
                                                                        .addComponent(cboxEditChanse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel70)
                                                                        .addComponent(editClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel71)
                                                                        .addComponent(editClientSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel72)
                                                                        .addComponent(editClientPesel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(editClientPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel73))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel74)
                                                                        .addComponent(editClientPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(editClientMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel76))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel77)
                                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addGap(11, 11, 11)
                                                                .addComponent(jLabel75))
                                                        .addGroup(editUserByManagerPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(editClientTel)
                                                                        .addComponent(lab1))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)))
                                .addGroup(editUserByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveClientByManagerButton)
                                        .addComponent(cancelClientByManagerButton))
                                .addContainerGap(87, Short.MAX_VALUE))
        );

        raportsByManagerPanel.setPreferredSize(new java.awt.Dimension(800, 750));

        jLabel59.setFont(new java.awt.Font("Times New Roman,", 1, 24)); // NOI18N
        jLabel59.setText("Generowanie raportów");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Report.gif"))); // NOI18N
        jButton4.setText("Generuj");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel60.setText("Telefony z: ");

        cboxRaportDate.setModel(new DefaultComboBoxModel(new String[]{"ostatni dzień", "ostatnie 3 dni", "ostatni tydzień", "ostatni miesiąc", "ostatnie 3 miesiące"}));

        jLabel45.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel45.setText("Szansa sprzedaży:");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Report.gif"))); // NOI18N
        jButton5.setText("Generuj");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Times New Roman,", 0, 20)); // NOI18N
        jLabel61.setText("Raport pracownika:");

        txtUserRaport.setEditable(false);

        jButton6.setText("...");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Report.gif"))); // NOI18N
        jButton7.setText("Generuj");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        javax.swing.GroupLayout raportsByManagerPanelLayout = new javax.swing.GroupLayout(raportsByManagerPanel);
        raportsByManagerPanel.setLayout(raportsByManagerPanelLayout);
        raportsByManagerPanelLayout.setHorizontalGroup(
                raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel45)
                                        .addComponent(jLabel59)
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, raportsByManagerPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel61)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtUserRaport))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, raportsByManagerPanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel60)
                                                                .addGap(93, 93, 93)
                                                                .addComponent(cboxRaportDate, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton5)
                                        .addComponent(jButton4)
                                        .addComponent(jButton7))
                                .addGap(219, 219, 219))
        );
        raportsByManagerPanelLayout.setVerticalGroup(
                raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel59)
                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel60)
                                                        .addComponent(cboxRaportDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel45))
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(jButton4)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton5)))
                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(raportsByManagerPanelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(raportsByManagerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtUserRaport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(managerPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(topManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(managerPanelLayout.createSequentialGroup()
                                                .addComponent(leftManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(mainManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(searchUserByManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(800, 800, 800))
                        .addGroup(managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(editUserByManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(200, 200, 200)
                                        .addComponent(raportsByManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );
        managerPanelLayout.setVerticalGroup(
                managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(managerPanelLayout.createSequentialGroup()
                                .addComponent(topManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(leftManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mainManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchUserByManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0))
                        .addGroup(managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(editUserByManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(managerPanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(raportsByManagerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1024, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap()))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(managerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(22, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 824, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(adminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap()))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(managerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(22, Short.MAX_VALUE)))
        );

        pack();
    }

    private void logowanieAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logowanieAction
        processLoginAction();
    }//GEN-LAST:event_logowanieAction

    private void processLoginAction() {
        leftPanel.setVisible(true);
        topPanel.setVisible(true);
        loggedUser = UserDAO.login(txtLogin.getText(), txtPassword.getText());
        if (loggedUser == null) {
            JOptionPane.showMessageDialog(this,
                    "Błąd logowania.\nSprawdź użytkownika i hasło",
                    "Błąd logowania",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            if (loggedUser.getType() == null || loggedUser.getType().equalsIgnoreCase("")) {
                // zalogowano jako uzytkownik
                processUserPanel();
            } else if (loggedUser.getType().equalsIgnoreCase(UserTypeConstants.ADMIN)) {
                // zalogowano jako adamin
                processAdminPanel();
            } else if (loggedUser.getType().equalsIgnoreCase(UserTypeConstants.MANAGER)) {
                // zalogowano jako manager
                processManagerPanel();
            } else {
                // zalogowano jako uzytkownik
                processUserPanel();
            }
        }
    }

    /**
     * Wyłączenie panelu dodawania klienta.
     *
     * @param evt
     */
    private void dodajKlientaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajKlientaAction
        // Dodawanie klienta
        saveClientButton.setEnabled(true);
        editClientButton.setVisible(false);
        selectedClient = null;

        PanelsUtil.enablePanel(addClientPanel, new JPanel[]{mainUserPanel, searchPanel, sendMailPanel});
        clearClientsFields();

        addClientAction();
    }//GEN-LAST:event_dodajKlientaAction

    /*
     Czyści pola klienta
     */
    private void clearClientsFields() {
        txtClientName.setText("");
        txtClientSurname.setText("");
        txtClientStreet.setText("");
        txtClientNumber.setText("");
        txtCLientCity.setText("");
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
        cbCurrenctCredit.setSelected(false);
        cbHomeCredit.setSelected(false);
        cbReapetedCredit.setSelected(false);
        cbCreditCard.setSelected(false);
        cboxChanse.setSelectedIndex(0);
        txtClientName.setEnabled(true);
        txtClientSurname.setEnabled(true);
        txtClientStreet.setEnabled(true);
        txtClientNumber.setEnabled(true);
        txtCLientCity.setEnabled(true);
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
        cbCurrenctCredit.setEnabled(true);
        cbHomeCredit.setEnabled(true);
        cbReapetedCredit.setEnabled(true);
        cbCreditCard.setEnabled(true);
        cboxChanse.setEnabled(true);
        txtClientModification.setEnabled(false);
        txtClientTel.setEnabled(true);
        txtClientTelDate.setEnabled(false);

    }

    private void clearUserFields() {
        // clear
        txtAddUserLogin.setText("");
        txtAddUserName.setText("");
        txtAddUserPassword1.setText("");
        txtAddUserPassword2.setText("");
        txtAddUserSurname.setText("");
        txtAddUserdate.setText("");
        txtAddUsertype.setSelectedIndex(0);

        // enable
        txtAddUserLogin.setEnabled(true);
        txtAddUserName.setEnabled(true);
        txtAddUserPassword1.setEnabled(true);
        txtAddUserPassword2.setEnabled(true);
        txtAddUserSurname.setEnabled(true);
        txtAddUserdate.setEnabled(true);
        txtAddUsertype.setEnabled(true);
    }

    /*
     Dodaje dane pracownika oraz datę do klienta
     */
    private void addClientAction() {
        txtClientCreateDate.setText(now());
        txtClientCreator.setText(loggedUser.getName() + " " + loggedUser.getSurname());
    }

    /*
     Zwraca dzisiejszą datę w formacie yyyy-MM-dd HH:mm:ss
     */
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    /*
     Zapis nowego klienta do bazy
     */
    private void zapiszKlientaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zapiszKlientaAction
        // Zapis nowego klienta do bazy
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
                if (txtClientVip.isSelected()) {
                    client.setVip('T');
                } else {
                    client.setVip('F');
                }
                client.setCreated(txtClientCreateDate.getText());
                client.setUser(loggedUser);

                Address address = new Address();
                address.setStreet(txtClientStreet.getText());
                address.setNumber(txtClientNumber.getText());
                address.setCity(txtCLientCity.getText());
                address.setPostCode(txtClientPostalCode.getText());
                AddressDAO.insertAddress(address);

                client.setAddress(address);

                client.setProducts(createProductsEntry());

                if (cboxChanse.getSelectedIndex() != 0) {
                    client.setSellChance(cboxChanse.getSelectedItem().toString());
                }

                String out = ClientDAO.insertClient(client);
                if (out.equals(ClientConstants.OK)) {
                    JOptionPane.showMessageDialog(this,
                            "Nowy klient został dodany.",
                            "Dodawania klienta",
                            JOptionPane.INFORMATION_MESSAGE);

                    processUserPanel();
                } else {
                    if (out.contains("Duplicate entry")) {
                        JOptionPane.showMessageDialog(this,
                                "Pesel już istnieje",
                                "Błąd dodawania klienta",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Błąd dodawania klienta.\nSkontaktuj się z administratorem.",
                                "Błąd dodawania klienta",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Błąd dodawania klienta.\nSkontaktuj się z administratorem.",
                        "Błąd dodawania klienta",
                        JOptionPane.ERROR_MESSAGE);
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
            if (selectedClient.getVip() == 'F' && txtClientVip.isSelected()) {
                selectedClient.setVip('T');
                isEdited = true;
            } else if (selectedClient.getVip() == 'T' && !txtClientVip.isSelected()) {
                selectedClient.setVip('F');
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

            if (!selectedClient.getAddress().getCity().equalsIgnoreCase(txtCLientCity.getText())) {
                if (address == null) {
                    address = selectedClient.getAddress();
                    address.setNumber(txtCLientCity.getText());
                } else {
                    address.setNumber(txtCLientCity.getText());
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

            if (!selectedClient.getSellChance().equalsIgnoreCase(cboxChanse.getSelectedItem().toString())) {
                selectedClient.setSellChance(cboxChanse.getSelectedItem().toString());
                isEdited = true;
            }

            if (!selectedClient.getProducts().equalsIgnoreCase(createProductsEntry())) {
                selectedClient.setProducts(createProductsEntry());
                isEdited = true;
            }

            if (selectedClient.getTel().equalsIgnoreCase("F") && txtClientTel.isSelected()) {
                selectedClient.setTel("T");
                selectedClient.setTelDate(now());
                isEdited = true;
            } else if (selectedClient.getTel().equalsIgnoreCase("T") && !txtClientTel.isSelected()) {
                selectedClient.setTel("F");
                selectedClient.setTelDate("");
                isEdited = true;
            }

            if (isAddressEdited) {
                if (validateClient()) {
                    selectedClient.setModified(now());
                    AddressDAO.updateAddress(address);
                    ClientDAO.updateClient(selectedClient);
                    processSearchPanel();
                }
            } else if (isEdited) {
                if (validateClient()) {
                    selectedClient.setModified(now());
                    String out = ClientDAO.updateClient(selectedClient);
                    if (out.contains("Duplicate entry")) {
                        JOptionPane.showMessageDialog(this,
                                "Pesel już istnieje",
                                "Błąd dodawania klienta",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        processSearchPanel();
                    }

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Błąd dodawania klienta.\nSkontaktuj się z administratorem.",
                            "Błąd dodawania klienta",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nic nie edytowano.",
                        "Edycja klienta",
                        JOptionPane.INFORMATION_MESSAGE);
                processSearchPanel();
            }
        }
    }//GEN-LAST:event_zapiszKlientaAction

    /*
     Tworzy obiekt String z zaznaczonych produktów
     */
    private String createProductsEntryForManager() {
        String products = "";
        if (cbEditPersonalAcc.isSelected()) {
            products = "Konto osobiste";
        }
        if (cbEditCurrencyAcc.isSelected()) {
            if (products.equals("")) {
                products = "Konto walutowe";
            } else {
                products = products + ",Konto walutowe";
            }
        }
        if (cbEditLocate.isSelected()) {
            if (products.equals("")) {
                products = "Lokata";
            } else {
                products = products + ",Lokata";
            }
        }
        if (cbEditCurrenctCredit.isSelected()) {
            if (products.equals("")) {
                products = "Kredyt gotówkowy";
            } else {
                products = products + ",Kredyt gotówkowy";
            }
        }
        if (cbEditHomeCredit.isSelected()) {
            if (products.equals("")) {
                products = "Kredyt hipoteczny";
            } else {
                products = products + ",Kredyt hipoteczny";
            }
        }
        if (cbEditReapetedCredit.isSelected()) {
            if (products.equals("")) {
                products = "Kredyt odnawialny";
            } else {
                products = products + ",Kredyt odnawialny";
            }
        }
        if (cbEditCreditCard.isSelected()) {
            if (products.equals("")) {
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
            if (products.equals("")) {
                products = "Konto walutowe";
            } else {
                products = products + ",Konto walutowe";
            }
        }
        if (cbLocate.isSelected()) {
            if (products.equals("")) {
                products = "Lokata";
            } else {
                products = products + ",Lokata";
            }
        }
        if (cbCurrenctCredit.isSelected()) {
            if (products.equals("")) {
                products = "Kredyt gotówkowy";
            } else {
                products = products + ",Kredyt gotówkowy";
            }
        }
        if (cbHomeCredit.isSelected()) {
            if (products.equals("")) {
                products = "Kredyt hipoteczny";
            } else {
                products = products + ",Kredyt hipoteczny";
            }
        }
        if (cbReapetedCredit.isSelected()) {
            if (products.equals("")) {
                products = "Kredyt odnawialny";
            } else {
                products = products + ",Kredyt odnawialny";
            }
        }
        if (cbCreditCard.isSelected()) {
            if (products.equals("")) {
                products = "Karta kredytowa";
            } else {
                products = products + ",Karta kredytowa";
            }
        }

        return products;
    }

    /**
     * Walidacja klienta przy dodaniu nowego/zapisie
     *
     * @return
     */
    private boolean validateClient() {
        Map<String, String> fieldsMap = new HashMap<String, String>();
        fieldsMap.put(txtCLientCity.getText(), "Wprowadź miasto");
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
                    JOptionPane.showMessageDialog(this,
                            "Błedny kod pocztowy",
                            "Błąd dodawania klienta",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Błedny nr pesel",
                        "Błąd dodawania klienta",
                        JOptionPane.ERROR_MESSAGE);

                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    result,
                    "Błąd dodawania klienta",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean validateClientForManager() {
        Map<String, String> fieldsMap = new HashMap<String, String>();
        fieldsMap.put(editCLientCity.getText(), "Wprowadź miasto");
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
                    JOptionPane.showMessageDialog(this,
                            "Błedny kod pocztowy",
                            "Błąd dodawania klienta",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Błedny nr pesel",
                        "Błąd dodawania klienta",
                        JOptionPane.ERROR_MESSAGE);

                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    result,
                    "Błąd dodawania klienta",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    private void anulujZapisKlientaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anulujZapisKlientaAction
        if (selectedClient != null) {
            processSearchPanel();
        } else {
            processUserPanel();
        }
    }//GEN-LAST:event_anulujZapisKlientaAction

    private void mojeKontaktyAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mojeKontaktyAction
        //enableMyClientsPanel();
        myContacts = true;
        mail = false;
        processSearchPanel();
    }//GEN-LAST:event_mojeKontaktyAction

    /**
     * Wszyscy klienci
     *
     * @param evt
     */
    private void wyszukiwanieAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyszukiwanieAction
        myContacts = false;
        mail = false;
        processSearchPanel();
    }//GEN-LAST:event_wyszukiwanieAction

    private void processSearchPanel() {
        PanelsUtil.enablePanel(searchPanel, new JPanel[]{mainUserPanel, addClientPanel, sendMailPanel});

        if (myContacts) {
            txtSendMultipleMail.setVisible(false);
            txtDetailsClientButton.setVisible(true);
            tableClients.setRowSelectionAllowed(true);
            tableClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            List<Client> clients = ClientDAO.findClientsByUser(loggedUser);
            TableUtil.displayClients(clients, tableClients);
        } else if (mail) {
            txtSendMultipleMail.setVisible(true);
            txtDetailsClientButton.setVisible(false);
            tableClients.setRowSelectionAllowed(true);
            tableClients.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            List<Client> clients = ClientDAO.findClientsWithMails();
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

    /*
     korespondencja seryjna
     */
    private void korespondencjaSeryjnaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_korespondencjaSeryjnaAction
        // KORESPONDENCJ SERYJNA
        myContacts = false;
        mail = true;
        processSearchPanel();
    }//GEN-LAST:event_korespondencjaSeryjnaAction

    /*
     Mój panel
     */
    private void mojPanelAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mojPanelAction
        PanelsUtil.enablePanel(mainUserPanel, new JPanel[]{addClientPanel, searchPanel, sendMailPanel});
    }//GEN-LAST:event_mojPanelAction

    /*
     Czyszczenie pól maila
     */
    private void wyczyscPolaMailaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyczyscPolaMailaAction
        // Wyczyszczenie pół wysyłania maila
        txtMailContent.setText("");
        txtReceip.setText("");
        txtMailSubject.setText("");
    }//GEN-LAST:event_wyczyscPolaMailaAction

    private void wyslijMailAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyslijMailAction
        // panel wysyłania maila
        PanelsUtil.enablePanel(sendMailPanel, new JPanel[]{mainUserPanel, addClientPanel, searchPanel});
    }//GEN-LAST:event_wyslijMailAction

    /*
     Panel wysyłania maila
     */
    private void wyslijMail2Action(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyslijMail2Action
        // Panel główny użytkownika
        processSearchPanel();
    }//GEN-LAST:event_wyslijMail2Action

    /*
     Walidacja pól maila + wysyłka
     */
    private void wyslijjednegoMailaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyslijjednegoMailaAction
        // Walidatcja pól wysyłania maila + wysyłka
        Map<String, String> fieldsMap = new HashMap<String, String>();
        fieldsMap.put(txtMailContent.getText(), "Wprowadź treść maila");
        fieldsMap.put(txtMailSubject.getText(), "Wprowadź temat maila");
        fieldsMap.put(txtReceip.getText(), "Wprowadź odbiorców");

        String result = ValidationUtil.validateNotNull(fieldsMap);
        if (result == null) {

            String recipients = txtReceip.getText();

            String send = MailUtil.sendMail(recipients, txtMailSubject.getText(), txtMailContent.getText());
            if (send.equalsIgnoreCase(ClientConstants.OK)) {
                JOptionPane.showMessageDialog(this,
                        "Wiadmość została wysłana",
                        "Wysylanie maila",
                        JOptionPane.INFORMATION_MESSAGE);

                if (mail) {
                    processSearchPanel();
                } else {
                    processUserPanel();
                }

            }
        } else {
            JOptionPane.showMessageDialog(this,
                    result,
                    "Błąd wysyłania maila",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_wyslijjednegoMailaAction

    /*
     Akcja szukaj
     */
    private void szukajKlientaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_szukajKlientaAction
        List<Client> searchResults = null;
        // Wyszukiwanie klienta
        if (!txtSearchSurname.getText().equals("") && !txtSearchPesel.getText().equals("")) {
            // szukanie po peselu i nazwisku  
            if (myContacts) {
                searchResults = ClientDAO.searchClientsBySurnameAndPeselAndUser(loggedUser, txtSearchSurname.getText(), txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else if (mail) {
                searchResults = ClientDAO.searchClientsBySurnameAndPeselAndMail(txtSearchSurname.getText(), txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else {
                searchResults = ClientDAO.searchClientsBySurnameAndPesel(txtSearchSurname.getText(), txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            }
        } else if (txtSearchSurname.getText().equals("") && !txtSearchPesel.getText().equals("")) {
            // szukanie po peselu
            if (myContacts) {
                searchResults = ClientDAO.searchClientsByPeselAndUser(loggedUser, txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else if (mail) {
                searchResults = ClientDAO.searchClientsByPeselAndMail(txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else {
                searchResults = ClientDAO.searchClientsByPesel(txtSearchPesel.getText());
                TableUtil.displayClients(searchResults, tableClients);
            }
        } else if (!txtSearchSurname.getText().equals("") && txtSearchPesel.getText().equals("")) {
            // szukanie po nazwisku
            if (myContacts) {
                searchResults = ClientDAO.searchClientsBySurname(loggedUser, txtClientSurname.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else if (mail) {
                searchResults = ClientDAO.searchClientsBySurnameAndMail(txtClientSurname.getText());
                TableUtil.displayClients(searchResults, tableClients);
            } else {
                searchResults = ClientDAO.searchClientsBySurname(txtSearchSurname.getText());
                TableUtil.displayClients(searchResults, tableClients);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Wprowadź kryteria wyszukiwania",
                    "Błąd wyszukiwania",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_szukajKlientaAction

    /*
     Czyszczenie pól wyszukiwania
     */
    private void resetPolSzukaniaKlientaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetPolSzukaniaKlientaAction
        // Czyszczenie wyników wyszukiwania
        txtSearchPesel.setText("");
        txtSearchSurname.setText("");
        if (myContacts) {
            List<Client> clients = ClientDAO.searchClients();
            TableUtil.displayClients(clients, tableClients);
        } else if (mail) {
            List<Client> clients = ClientDAO.findClientsWithMails();
            TableUtil.displayClients(clients, tableClients);
        } else {
            List<Client> clients = ClientDAO.findClientsByUser(loggedUser);
            TableUtil.displayClients(clients, tableClients);
        }
    }//GEN-LAST:event_resetPolSzukaniaKlientaAction

    /**
     * Szczegóły zaznaczonego klienta
     *
     * @param evt
     */
    private void szczegolyKlientaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_szczegolyKlientaAction
        // Pokaż szczegóły klienta
        if (tableClients.getSelectedRowCount() == 1) {
            editClientAction(tableClients.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Wybierz klienta",
                    "Zaznacz wiersz",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_szczegolyKlientaAction

    /**
     * Edycja klienta
     *
     * @param evt
     */
    private void edytujKlientaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edytujKlientaAction
        // Edycja klienta - włączenie pól
        saveClientButton.setEnabled(true);
        editClientButton.setEnabled(false);

        txtClientName.setEnabled(true);
        txtClientSurname.setEnabled(true);
        txtClientStreet.setEnabled(true);
        txtClientNumber.setEnabled(true);
        txtCLientCity.setEnabled(true);
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
        cbCurrenctCredit.setEnabled(true);
        cbHomeCredit.setEnabled(true);
        cbReapetedCredit.setEnabled(true);
        cbCreditCard.setEnabled(true);
        cboxChanse.setEnabled(true);
        txtClientModification.setEnabled(false);
        txtClientTel.setEnabled(true);
        txtClientTelDate.setEnabled(false);

        txtClientModification.setText(now());
    }//GEN-LAST:event_edytujKlientaAction

    /**
     * Akcja podwójne kliknięcie na klienta otwiera szczegóły
     *
     * @param evt
     */
    private void tableClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClientsMouseClicked
        tableClients.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (mail) {
                        // wysyłanie maila                        
                        int[] rows = tableClients.getSelectedRows();
                        List<String> receipents = new ArrayList<String>();
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
    }//GEN-LAST:event_tableClientsMouseClicked

    /**
     * Anuluj logowanie. Zamknięcie systemu
     *
     * @param evt
     */
    private void wyjscieAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyjscieAction
        exitCommonAction();
    }//GEN-LAST:event_wyjscieAction

    /**
     * Wysyłanie wiadomości do zaznaczonych kontaktów
     *
     * @param evt
     */
    private void wyslijMailDoWszystkichAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyslijMailDoWszystkichAction
        // wysyłanie maila                        
        int[] rows = tableClients.getSelectedRows();
        List<String> receipents = new ArrayList<String>();
        String receipts = "";
        for (int i = 0; i < rows.length; i++) {
            receipts = receipts + tableClients.getValueAt(rows[i], 4) + ",";
        }

        if (receipts.endsWith(",")) {
            receipts = receipts.substring(0, receipts.length() - 1);
        }
        processMailPanel(receipts);
    }//GEN-LAST:event_wyslijMailDoWszystkichAction

    /**
     * Akcja wyloguj
     *
     * @param evt
     */
    private void wylogujUserAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wylogujUserAction
        int n = JOptionPane.showOptionDialog(this,
                "Czy napewno chcesz wyjść z programu?",
                "Wyjście",
                0, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        if (n == 0) {
            loggedUser = null;
            txtLogin.setText("");
            txtPassword.setText("");
            PanelsUtil.enablePanel(loginPanel, new JPanel[]{userPanel, adminPanel, managerPanel, addClientPanel, searchPanel, sendMailPanel, leftPanel, topPanel});
        }
    }//GEN-LAST:event_wylogujUserAction

    /**
     * Wyjście
     *
     * @param evt
     */
    private void exitUserAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitUserAction
        exitCommonAction();
    }//GEN-LAST:event_exitUserAction

    private void txtLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginActionPerformed
        processLoginAction();
    }//GEN-LAST:event_txtLoginActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        processLoginAction();
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void adminSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminSearchButtonActionPerformed
        // Szukanie użytkowników
        List<User> searchResults = null;
        // Wyszukiwanie klienta
        if (!txtAdminSearchName.getText().equals("") && !txtAdminSearchSurname.getText().equals("")) {
            // szukanie po imieniu i nazwisku              
            searchResults = UserDAO.searchUsersBySurnameAndName(txtAdminSearchSurname.getText(), txtAdminSearchName.getText());
            TableUtil.displayUsers(searchResults, tableUsers);
        } else if (txtAdminSearchName.getText().equals("") && !txtAdminSearchSurname.getText().equals("")) {
            searchResults = UserDAO.searchUsersBySurname(txtAdminSearchSurname.getText());
            TableUtil.displayUsers(searchResults, tableUsers);
        } else if (!txtAdminSearchName.getText().equals("") && txtAdminSearchSurname.getText().equals("")) {
            searchResults = UserDAO.searchUsersByName(txtAdminSearchName.getText());
            TableUtil.displayUsers(searchResults, tableUsers);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Wprowadź kryteria wyszukiwania",
                    "Błąd wyszukiwania",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_adminSearchButtonActionPerformed

    private void adminResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminResetButtonActionPerformed
        // Czyszczenie wyszukiwania użytkowników
        txtAdminSearchName.setText("");
        txtAdminSearchSurname.setText("");

        List<User> users = UserDAO.findUsers();
        TableUtil.displayUsers(users, tableUsers);
    }//GEN-LAST:event_adminResetButtonActionPerformed

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        // DODAWANIE UŻYTKOWNIKA
        PanelsUtil.enablePanel(addUserPanel, new JPanel[]{searchUserPanel, mainAdminPanel});
        clearUserFields();

        txtAddUserdate.setText(now());
        selectedUser = null;
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void cancelUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelUserButtonActionPerformed
        // Anuluj dodawanie użytkownika
        if (selectedUser == null) {
            PanelsUtil.enablePanel(mainAdminPanel, new JPanel[]{searchUserPanel, addUserPanel});
        } else {
            PanelsUtil.enablePanel(searchUserPanel, new JPanel[]{mainAdminPanel, addUserPanel});
        }


    }//GEN-LAST:event_cancelUserButtonActionPerformed

    private void saveUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveUserButtonActionPerformed
        // ZAPISZ USERA
        if (selectedUser == null) {
            // dodanie
            if (validateUser()) {
                User newUserLocal = new User();
                newUserLocal.setName(txtAddUserName.getText());
                newUserLocal.setSurname(txtAddUserSurname.getText());
                newUserLocal.setLogin(txtAddUserLogin.getText());
                newUserLocal.setPassword(txtAddUserPassword1.getText());
                newUserLocal.setType(txtAddUsertype.getSelectedItem().toString());
                newUserLocal.setCreated(txtAddUserdate.getText());

                String out = UserDAO.insertUser(newUserLocal);
                if (out.equalsIgnoreCase(ClientConstants.OK)) {
                    JOptionPane.showMessageDialog(this,
                            "Nowy użytkownik został dodany.",
                            "Dodawania użytkownika",
                            JOptionPane.INFORMATION_MESSAGE);

                    processAdminPanel();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Podany login juz istnieje.",
                            "Dodawania klienta",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            // edycja

            boolean isEdited = false;
            if (!selectedUser.getName().equalsIgnoreCase(txtAddUserName.getText())) {
                isEdited = true;
                selectedUser.setName(txtAddUserName.getText());
            }
            if (!selectedUser.getSurname().equalsIgnoreCase(txtAddUserSurname.getText())) {
                isEdited = true;
                selectedUser.setSurname(txtAddUserSurname.getText());
            }
            if (!selectedUser.getLogin().equalsIgnoreCase(txtAddUserLogin.getText())) {
                isEdited = true;
                selectedUser.setLogin(txtAddUserLogin.getText());
            }
            if (!selectedUser.getPassword().equalsIgnoreCase(txtAddUserPassword1.getText())) {
                isEdited = true;
                selectedUser.setPassword(txtAddUserPassword1.getText());
            }
            if (!selectedUser.getType().equalsIgnoreCase(txtAddUsertype.getSelectedItem().toString())) {
                isEdited = true;
                selectedUser.setType(txtAddUsertype.getSelectedItem().toString());
            }

            if (isEdited && validateUser()) {
                selectedUser.setCreated(now());
                String out = UserDAO.updateUser(selectedUser);
                if (out.contains("Duplicate entry")) {
                    JOptionPane.showMessageDialog(this,
                            "Taki login już istnieje",
                            "Błąd dodawania użytkownika",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    processAdminSearch();
                }

            }
        }

    }//GEN-LAST:event_saveUserButtonActionPerformed

    private void logoutAdminButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutAdminButtonActionPerformed
        // WYLOGOWANIE ADMINA
        int n = JOptionPane.showOptionDialog(this,
                "Czy napewno chcesz wyjść z programu?",
                "Wyjście",
                0, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        if (n == 0) {
            loggedUser = null;
            txtLogin.setText("");
            txtPassword.setText("");
            PanelsUtil.enablePanel(loginPanel, new JPanel[]{userPanel, adminPanel, managerPanel, addClientPanel, searchPanel, sendMailPanel, leftPanel, topPanel});
        }
    }//GEN-LAST:event_logoutAdminButtonActionPerformed

    private void txtClientTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientTelActionPerformed
        if (txtClientTel.isSelected()) {
            txtClientTelDate.setText(now());
        } else {
            txtClientTelDate.setText(null);
        }
    }//GEN-LAST:event_txtClientTelActionPerformed

    private void searchUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUserButtonActionPerformed
        processAdminSearch();
    }//GEN-LAST:event_searchUserButtonActionPerformed

    private void processAdminSearch() {
        //WYSZUKIWANIE NA PANELU ADMINA        
        PanelsUtil.enablePanel(searchUserPanel, new JPanel[]{addUserPanel, mainAdminPanel});
        selectedUser = null;
        tableUsers.setRowSelectionAllowed(true);
        tableUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        List<User> users = UserDAO.findUsers();
        TableUtil.displayUsers(users, tableUsers);
    }

    private void adminDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminDetailsButtonActionPerformed
        // Edycja usera
        if (tableUsers.getSelectedRowCount() == 1) {
            editUserAction(tableUsers.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Wybierz użytkownika",
                    "Zaznacz wiersz",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_adminDetailsButtonActionPerformed

    private void editUserAction(int row) {
        Object selectedUserId = tableUsers.getModel().getValueAt(row, 0);
        System.out.println("selectedUserId " + selectedUserId);
        selectedUser = null;
        selectedUser = UserDAO.getUserById((Integer) selectedUserId);
        if (selectedUser != null) {
            PanelsUtil.enablePanel(addUserPanel, new JPanel[]{mainAdminPanel, searchUserPanel});

            txtAddUserName.setText(selectedUser.getName());
            txtAddUserSurname.setText(selectedUser.getSurname());
            txtAddUserLogin.setText(selectedUser.getLogin());
            txtAddUserdate.setText(selectedUser.getCreated());
            txtAddUserdate.setEditable(false);
            txtAddUserPassword1.setText(selectedUser.getPassword());
            txtAddUserPassword2.setText(selectedUser.getPassword());
            txtAddUsertype.setSelectedItem(selectedUser.getType());
        }
    }

    private void myAdminPanelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myAdminPanelButtonActionPerformed
        // Panel admina
        PanelsUtil.enablePanel(mainAdminPanel, new JPanel[]{addUserPanel, searchUserPanel});
    }//GEN-LAST:event_myAdminPanelButtonActionPerformed

    private void exitAdminButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitAdminButtonActionPerformed
        exitCommonAction();
    }//GEN-LAST:event_exitAdminButtonActionPerformed

    private void tableUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUsersMouseClicked
        tableUsers.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    editUserAction(row);
                }
            }
        });
    }//GEN-LAST:event_tableUsersMouseClicked

    private void changeAdminPassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeAdminPassButtonActionPerformed
        commonChangePassword();
    }//GEN-LAST:event_changeAdminPassButtonActionPerformed

    private void commonChangePassword() {
        jDialog1.setVisible(true);
        jDialog1.setSize(400, 200);
        txtChangePass1.setText("");
        txtChangePass2.setText("");
    }

    private void changePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordButtonActionPerformed
        if (!ValidationUtil.validatePassword(txtChangePass1.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Hasło powinno mieć co najmniej 4 znaki, nie więcej niż 10 znaków. ",
                    "Zmiana hasła",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!txtChangePass1.getText().equalsIgnoreCase(txtChangePass2.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Hasła muszą być takie same. ",
                    "Zmiana hasła",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            loggedUser.setPassword(txtChangePass1.getText());
            UserDAO.updateUser(loggedUser);

            JOptionPane.showMessageDialog(this,
                    "Hasła zostało zmienione. ",
                    "Zmiana hasła",
                    JOptionPane.INFORMATION_MESSAGE);

            jDialog1.setVisible(false);
            jDialog1.dispose();
        }
    }//GEN-LAST:event_changePasswordButtonActionPerformed

    private void cancelChangePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelChangePasswordButtonActionPerformed
        jDialog1.setVisible(false);
        jDialog1.dispose();
    }//GEN-LAST:event_cancelChangePasswordButtonActionPerformed

    private void zmienHasloUzytkAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zmienHasloUzytkAction
        commonChangePassword();
    }//GEN-LAST:event_zmienHasloUzytkAction

    private void searchClientByManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchClientByManagerButtonActionPerformed
        processSearchClientsForManager();
    }//GEN-LAST:event_searchClientByManagerButtonActionPerformed

    private void processSearchClientsForManager() {
        //WYSZUKIWANIE NA PANELU MANAGERA        
        PanelsUtil.enablePanel(searchUserByManagerPanel, new JPanel[]{editUserByManagerPanel, mainManagerPanel, raportsByManagerPanel});
        selectedUser = null;
        tableClientsForManager.setRowSelectionAllowed(true);
        tableClientsForManager.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        List<Client> clients = ClientDAO.searchClients();
        TableUtil.displayClients(clients, tableClientsForManager);
    }

    private void myManagerPanelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myManagerPanelButtonActionPerformed
        PanelsUtil.enablePanel(mainManagerPanel, new JPanel[]{editUserByManagerPanel, searchUserByManagerPanel, raportsByManagerPanel});
    }//GEN-LAST:event_myManagerPanelButtonActionPerformed

    private void logoutManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutManagerButtonActionPerformed
        // WYLOGOWANIE ADMINA
        int n = JOptionPane.showOptionDialog(this,
                "Czy napewno chcesz wyjść z programu?",
                "Wyjście",
                0, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        if (n == 0) {
            loggedUser = null;
            txtLogin.setText("");
            txtPassword.setText("");
            PanelsUtil.enablePanel(loginPanel, new JPanel[]{userPanel, adminPanel, managerPanel});
        }
    }//GEN-LAST:event_logoutManagerButtonActionPerformed

    private void exitManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitManagerButtonActionPerformed
        exitCommonAction();
    }//GEN-LAST:event_exitManagerButtonActionPerformed

    private void exitCommonAction() {
        // wyjście             
        int n = JOptionPane.showOptionDialog(this,
                "Czy napewno chcesz wyjść z programu?",
                "Wyjście",
                0, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        if (n == 0) {
            System.exit(1);
        }
    }

    private void changeManagerPassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeManagerPassButtonActionPerformed
        commonChangePassword();
    }//GEN-LAST:event_changeManagerPassButtonActionPerformed

    private void managerSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerSearchButtonActionPerformed
        List<Client> searchResults = null;
        if (!txtManagerSearchSurname.getText().equalsIgnoreCase("") && !txtManagerSearchPesel.getText().equalsIgnoreCase("")) {
            // search by user and pesel
            searchResults = ClientDAO.searchClientsBySurnameAndPesel(txtManagerSearchSurname.getText(), txtManagerSearchPesel.getText());
            TableUtil.displayClients(searchResults, tableClientsForManager);
        } else if (txtManagerSearchSurname.getText().equalsIgnoreCase("") && !txtManagerSearchPesel.getText().equalsIgnoreCase("")) {
            // search by pesel
            searchResults = ClientDAO.searchClientsByPesel(txtManagerSearchPesel.getText());
            TableUtil.displayClients(searchResults, tableClientsForManager);
        } else if (!txtManagerSearchSurname.getText().equalsIgnoreCase("") && txtManagerSearchPesel.getText().equalsIgnoreCase("")) {
            //search by surname
            searchResults = ClientDAO.searchClientsBySurname(txtManagerSearchSurname.getText());
            TableUtil.displayClients(searchResults, tableClientsForManager);
        }
    }//GEN-LAST:event_managerSearchButtonActionPerformed

    private void managerResetFieldsAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerResetFieldsAction
        // resetowanie pól wyszukiwania klient
        txtManagerSearchPesel.setText("");
        txtManagerSearchSurname.setText("");

        List<Client> clients = ClientDAO.searchClients();
        TableUtil.displayClients(clients, tableClientsForManager);
    }//GEN-LAST:event_managerResetFieldsAction

    private void tableClientsForManagerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClientsForManagerMouseClicked
        tableClientsForManager.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    editClientForManager(row);
                }
            }
        });
    }//GEN-LAST:event_tableClientsForManagerMouseClicked

    private void jScrollPane9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane9MouseClicked

    private void managerEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerEditButtonActionPerformed
        // Pokaż szczegóły klienta
        if (tableClientsForManager.getSelectedRowCount() == 1) {
            editClientForManager(tableClientsForManager.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Wybierz klienta",
                    "Zaznacz wiersz",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_managerEditButtonActionPerformed

    private void saveClientByManagerButtonzapiszKlientaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveClientByManagerButtonzapiszKlientaAction
        // Zapis klienta po edycji przez managera
        // edycja klienta
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
        if (selectedClient.getVip() == 'F' && editClientVip.isSelected()) {
            selectedClient.setVip('T');
            isEdited = true;
        } else if (selectedClient.getVip() == 'T' && !editClientVip.isSelected()) {
            selectedClient.setVip('F');
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
        if (!selectedClient.getAddress().getCity().equalsIgnoreCase(editCLientCity.getText())) {
            if (address == null) {
                address = selectedClient.getAddress();
                address.setNumber(editCLientCity.getText());
            } else {
                address.setNumber(editCLientCity.getText());
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
        if (sellChan == null && cboxEditChanse.getSelectedIndex() != 0) {
            selectedClient.setSellChance(cboxEditChanse.getSelectedItem().toString());
            isEdited = true;
        } else if (sellChan != null && !sellChan.equalsIgnoreCase(cboxEditChanse.getSelectedItem().toString())) {
            selectedClient.setSellChance(cboxEditChanse.getSelectedItem().toString());
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
            selectedClient.setTelDate(now());
            editClientTelDate.setText(now());
            isEdited = true;
        } else if (selectedClient.getTel().equalsIgnoreCase("T") && !editClientTel.isSelected()) {
            selectedClient.setTel("F");
            selectedClient.setTelDate("");
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
                AddressDAO.updateAddress(address);
                ClientDAO.updateClient(selectedClient);
                processSearchClientsForManager();
            }
        } else if (isEdited) {
            if (validateClientForManager()) {
                selectedClient.setModified(now());
                String out = ClientDAO.updateClient(selectedClient);
                if (out.contains("Duplicate entry")) {
                    JOptionPane.showMessageDialog(this,
                            "Pesel już istnieje",
                            "Błąd dodawania klienta",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    processSearchClientsForManager();
                }

            } else {
                JOptionPane.showMessageDialog(this,
                        "Błąd dodawania klienta.\nSkontaktuj się z administratorem.",
                        "Błąd dodawania klienta",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Nic nie edytowano.",
                    "Edycja klienta",
                    JOptionPane.INFORMATION_MESSAGE);
            processSearchClientsForManager();
        }

    }//GEN-LAST:event_saveClientByManagerButtonzapiszKlientaAction

    private void cancelClientByManagerButtonanulujZapisKlientaAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelClientByManagerButtonanulujZapisKlientaAction
        processSearchClientsForManager();
    }//GEN-LAST:event_cancelClientByManagerButtonanulujZapisKlientaAction

    private void editClientTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editClientTelActionPerformed
        if (editClientTel.isSelected()) {
            editClientTelDate.setText(now());
        } else {
            editClientTelDate.setText(null);
        }
    }//GEN-LAST:event_editClientTelActionPerformed

    private void processUserRaports() {
        newUser = null;
        if (usersForManagerTable.getSelectedRowCount() == 1) {
            String id = usersForManagerTable.getValueAt(usersForManagerTable.getSelectedRow(), 0).toString();
            Integer i = Integer.valueOf(id);
            newUser = UserDAO.getUserById(i);

            txtUserRaport.setText(newUser.getName() + " " + newUser.getSurname());

            jDialog2.dispose();
            jDialog2.setVisible(false);
            jDialog2.setAlwaysOnTop(false);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Wybierz pracownika",
                    "Zaznacz wiersz",
                    JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this,
                    "Wybierz pracownika",
                    "Zaznacz wiersz",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (isReport) {
            // process raportow
            processUserRaports();
        } else {
            // process edycji klienta przez managera
            processSelectedCreator();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jDialog2.dispose();
        jDialog2.setVisible(false);
        jDialog2.setAlwaysOnTop(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void selectUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectUserButtonActionPerformed
        newUser = null;
        isReport = false;
        List<User> users = UserDAO.findUsers();
        TableUtil.displayUsersForManager(users, usersForManagerTable);
        jDialog2.setVisible(true);
        jDialog2.setAlwaysOnTop(true);
    }//GEN-LAST:event_selectUserButtonActionPerformed

    private void usersForManagerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersForManagerTableMouseClicked
        usersForManagerTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    if (isReport) {
                        // process raportow
                        processUserRaports();
                    } else {
                        // process edycji klienta przez managera
                        processSelectedCreator();
                    }
                }
            }
        });
    }//GEN-LAST:event_usersForManagerTableMouseClicked

    private void reportsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsButtonActionPerformed
        PanelsUtil.enablePanel(raportsByManagerPanel, new JPanel[]{editUserByManagerPanel, searchUserByManagerPanel, mainManagerPanel});
    }//GEN-LAST:event_reportsButtonActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int choose = cboxRaportDate.getSelectedIndex();

        ReportsUtil util = new ReportsUtil();
        util.createTelephonesReport(choose);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ReportsUtil util = new ReportsUtil();
        util.createSellChanceReport();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // wybierz pracownika do raportu
        newUser = null;
        isReport = true;
        List<User> users = UserDAO.findUsers();
        TableUtil.displayUsersForManager(users, usersForManagerTable);
        jDialog2.setVisible(true);
        jDialog2.setAlwaysOnTop(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (!txtUserRaport.getText().equalsIgnoreCase("")) {
            ReportsUtil util = new ReportsUtil();
            util.createUserReport(newUser);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Wybierz pracownika",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void dataExpButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataExpButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataExpButton2ActionPerformed

    private void dataExpButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataExpButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataExpButton1ActionPerformed

    private void editClientForManager(int row) {
        Object selectedClientId = tableClientsForManager.getModel().getValueAt(row, 0);

        System.out.println("selectedClientId " + selectedClientId);

        selectedClient = ClientDAO.getClientById((Integer) selectedClientId);

        if (selectedClient != null) {
            PanelsUtil.enablePanel(editUserByManagerPanel, new JPanel[]{mainManagerPanel, searchUserByManagerPanel, raportsByManagerPanel});

            editClientName.setText(selectedClient.getName());
            editClientSurname.setText(selectedClient.getSurname());
            editClientStreet.setText(selectedClient.getAddress().getStreet());
            editClientNumber.setText(selectedClient.getAddress().getNumber());
            editCLientCity.setText(selectedClient.getAddress().getCity());
            editClientPostalCode.setText(selectedClient.getAddress().getPostCode());
            editClientCreator.setText(selectedClient.getUser().getName() + " " + selectedClient.getUser().getSurname());
            editClientPesel.setText(selectedClient.getPesel());
            editClientPhone.setText(selectedClient.getPhone1());
            editClientPhone2.setText(selectedClient.getPhone2());
            editClientMail.setText(selectedClient.getMail());
            editClientDesc.setText(selectedClient.getDescription());
            if (selectedClient.getVip() == 'T') {
                editClientVip.setSelected(true);
            } else {
                editClientVip.setSelected(false);
            }
            editClientCreateDate.setText(selectedClient.getCreated());
            editClientModification.setText(selectedClient.getModified());
            if (selectedClient.getTel().equalsIgnoreCase("T")) {
                editClientTel.setSelected(true);
            } else {
                editClientTel.setSelected(false);
            }

            editClientTelDate.setEnabled(false);
            editClientTelDate.setText(selectedClient.getTelDate());

            editClientModification.setText(selectedClient.getTelDate());

            if (!selectedClient.getProducts().equals("") && selectedClient.getProducts() != null) {
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
                    cbEditCurrenctCredit.setSelected(true);
                }
                if (productsSet.contains("Kredyt hipoteczny")) {
                    cbEditHomeCredit.setSelected(true);
                }
                if (productsSet.contains("Kredyt odnawialny")) {
                    cbEditReapetedCredit.setSelected(true);
                }
                if (productsSet.contains("Karta kredytowa")) {
                    cbEditCreditCard.setSelected(true);
                }
            } else {
                cbEditPersonalAcc.setSelected(false);
                cbEditCurrencyAcc.setSelected(false);
                cbEditLocate.setSelected(false);
                cbEditCurrenctCredit.setSelected(false);
                cbEditHomeCredit.setSelected(false);
                cbEditReapetedCredit.setSelected(false);
                cbEditCreditCard.setSelected(false);
            }

            cboxEditChanse.setSelectedItem(selectedClient.getSellChance());
        }
    }

    private boolean validateUser() {
        Map<String, String> fieldsMap = new HashMap<String, String>();

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
                    JOptionPane.showMessageDialog(this,
                            "Hasła muszę być takie same",
                            "Błąd dodawania użytkownika",
                            JOptionPane.ERROR_MESSAGE);

                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Hasła musi posiadadać od 4 do 10 znaków.",
                        "Błąd dodawania użytkownika",
                        JOptionPane.ERROR_MESSAGE);

                return false;
            }

        } else {
            JOptionPane.showMessageDialog(this,
                    result,
                    "Błąd dodawania użytkownika",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    private void processMailPanel(String r) {
        PanelsUtil.enablePanel(sendMailPanel, new JPanel[]{mainUserPanel, addClientPanel, searchPanel});
        txtReceip.setText(r);

        txtMailContent.setText("");
        txtMailSubject.setText("");
    }

    private void editClientAction(int row) {
        saveClientButton.setEnabled(false);
        editClientButton.setVisible(true);
        editClientButton.setEnabled(true);
        Object selectedClientId = tableClients.getModel().getValueAt(row, 0);

        System.out.println("selectedClientId " + selectedClientId);

        selectedClient = ClientDAO.getClientById((Integer) selectedClientId);

        if (selectedClient != null) {
            PanelsUtil.enablePanel(addClientPanel, new JPanel[]{mainUserPanel, searchPanel, sendMailPanel});

            txtClientName.setText(selectedClient.getName());
            txtClientName.setEnabled(false);
            txtClientSurname.setText(selectedClient.getSurname());
            txtClientSurname.setEnabled(false);
            txtClientStreet.setText(selectedClient.getAddress().getStreet());
            txtClientStreet.setEnabled(false);
            txtClientNumber.setText(selectedClient.getAddress().getNumber());
            txtClientNumber.setEnabled(false);
            txtCLientCity.setText(selectedClient.getAddress().getCity());
            txtCLientCity.setEnabled(false);
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
            if (selectedClient.getVip() == 'T') {
                txtClientVip.setSelected(true);
            } else {
                txtClientVip.setSelected(false);
            }
            txtClientVip.setEnabled(false);
            txtClientCreateDate.setText(selectedClient.getCreated());
            txtClientModification.setText(selectedClient.getModified());
            if (selectedClient.getTel().equalsIgnoreCase("T")) {
                txtClientTel.setSelected(true);
            } else {
                txtClientTel.setSelected(false);
            }

            txtClientModification.setText(selectedClient.getTelDate());

            if (!selectedClient.getProducts().equals("") && selectedClient.getProducts() != null) {
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
                    cbCurrenctCredit.setSelected(true);
                }
                if (productsSet.contains("Kredyt hipoteczny")) {
                    cbHomeCredit.setSelected(true);
                }
                if (productsSet.contains("Kredyt odnawialny")) {
                    cbReapetedCredit.setSelected(true);
                }
                if (productsSet.contains("Karta kredytowa")) {
                    cbCreditCard.setSelected(true);
                }
            } else {
                cbPersonalAcc.setSelected(false);
                cbCurrencyAcc.setSelected(false);
                cbLocate.setSelected(false);
                cbCurrenctCredit.setSelected(false);
                cbHomeCredit.setSelected(false);
                cbReapetedCredit.setSelected(false);
                cbCreditCard.setSelected(false);
            }
            cbPersonalAcc.setEnabled(false);
            cbCurrencyAcc.setEnabled(false);
            cbLocate.setEnabled(false);
            cbCurrenctCredit.setEnabled(false);
            cbHomeCredit.setEnabled(false);
            cbReapetedCredit.setEnabled(false);
            cbCreditCard.setEnabled(false);
            txtClientTel.setEnabled(false);

            cboxChanse.setSelectedItem(selectedClient.getSellChance());
            cboxChanse.setEnabled(false);

        }
    }

    private void processManagerPanel() {
        PanelsUtil.enablePanel(managerPanel, new JPanel[]{loginPanel, userPanel, adminPanel});

        PanelsUtil.enablePanel(mainManagerPanel, new JPanel[]{searchUserByManagerPanel, editUserByManagerPanel, raportsByManagerPanel});
        txtManagerName.setText(loggedUser.getName());
        txtManagerSurname.setText(loggedUser.getSurname());
        txtManagerLogin.setText(loggedUser.getLogin());
        String type = loggedUser.getType();
        if (type == null || type.equals("")) {
            type = "Pracownik";
        }
        txtManagerType.setText(type);
    }

    private void processAdminPanel() {
        PanelsUtil.enablePanel(adminPanel, new JPanel[]{loginPanel, userPanel, managerPanel});

        PanelsUtil.enablePanel(mainAdminPanel, new JPanel[]{addUserPanel, searchUserPanel});

        txtAdminName.setText(loggedUser.getName());
        txtAdminSurname.setText(loggedUser.getSurname());
        txtAdminLogin.setText(loggedUser.getLogin());
        String type = loggedUser.getType();
        if (type == null || type.equals("")) {
            type = "Pracownik";
        }
        txtAdminType.setText(type);
    }

    /*
     Włączenie panelu usera
     */
    private void processUserPanel() {
        PanelsUtil.enablePanel(userPanel, new JPanel[]{loginPanel, adminPanel, managerPanel});

        PanelsUtil.enablePanel(mainUserPanel, new JPanel[]{addClientPanel, searchPanel, sendMailPanel});

        txtUserName.setText(loggedUser.getName());
        txtUserSurname.setText(loggedUser.getSurname());
        txtUserLogin.setText(loggedUser.getLogin());

        String type = loggedUser.getType();
        if (type == null || type.equals("")) {
            type = "Pracownik";
        }
        txtUserType.setText(type);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SadCRMForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SadCRMForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SadCRMForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SadCRMForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SadCRMForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addClientButton;
    private javax.swing.JPanel addClientPanel;
    private javax.swing.JButton addUserButton;
    private javax.swing.JPanel addUserPanel;
    private javax.swing.JButton adminDetailsButton;
    private javax.swing.JPanel adminPanel;
    private javax.swing.JButton adminResetButton;
    private javax.swing.JButton adminSearchButton;
    private javax.swing.JButton cancelChangePasswordButton;
    private javax.swing.JButton cancelClientButton;
    private javax.swing.JButton cancelClientByManagerButton;
    private javax.swing.JButton cancelUserButton;
    private javax.swing.JButton canelLoginButton;
    private javax.swing.JCheckBox cbCreditCard;
    private javax.swing.JCheckBox cbCurrenctCredit;
    private javax.swing.JCheckBox cbCurrencyAcc;
    private javax.swing.JCheckBox cbEditCreditCard;
    private javax.swing.JCheckBox cbEditCurrenctCredit;
    private javax.swing.JCheckBox cbEditCurrencyAcc;
    private javax.swing.JCheckBox cbEditHomeCredit;
    private javax.swing.JCheckBox cbEditLocate;
    private javax.swing.JCheckBox cbEditPersonalAcc;
    private javax.swing.JCheckBox cbEditReapetedCredit;
    private javax.swing.JCheckBox cbHomeCredit;
    private javax.swing.JCheckBox cbLocate;
    private javax.swing.JCheckBox cbPersonalAcc;
    private javax.swing.JCheckBox cbReapetedCredit;
    private javax.swing.JComboBox cboxChanse;
    private javax.swing.JComboBox cboxEditChanse;
    private javax.swing.JComboBox cboxRaportDate;
    private javax.swing.JButton changeAdminPassButton;
    private javax.swing.JButton changeManagerPassButton;
    private javax.swing.JButton changePasswordButton;
    private javax.swing.JButton clearMailButton;
    private javax.swing.JButton dataExpButton1;
    private javax.swing.JButton dataExpButton2;
    private javax.swing.JTextField editCLientCity;
    private javax.swing.JButton editClientButton;
    private javax.swing.JTextField editClientCreateDate;
    private javax.swing.JTextField editClientCreator;
    private javax.swing.JTextArea editClientDesc;
    private javax.swing.JTextField editClientMail;
    private javax.swing.JTextField editClientModification;
    private javax.swing.JTextField editClientName;
    private javax.swing.JTextField editClientNumber;
    private javax.swing.JTextField editClientPesel;
    private javax.swing.JTextField editClientPhone;
    private javax.swing.JTextField editClientPhone2;
    private javax.swing.JFormattedTextField editClientPostalCode;
    private javax.swing.JTextField editClientStreet;
    private javax.swing.JTextField editClientSurname;
    private javax.swing.JCheckBox editClientTel;
    private javax.swing.JTextField editClientTelDate;
    private javax.swing.JCheckBox editClientVip;
    private javax.swing.JPanel editUserByManagerPanel;
    private javax.swing.JButton exitAdminButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton exitManagerButton;
    private javax.swing.JButton exportUserDataButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lab;
    private javax.swing.JLabel lab1;
    private javax.swing.JLabel labelClientModDate;
    private javax.swing.JLabel labelClientModDate1;
    private javax.swing.JPanel leftManagerPanel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel leftPanel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JButton logoutAdminButton;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton logoutManagerButton;
    private javax.swing.JButton mailButton;
    private javax.swing.JButton mailMergeButton;
    private javax.swing.JPanel mainAdminPanel;
    private javax.swing.JPanel mainManagerPanel;
    private javax.swing.JPanel mainUserPanel;
    private javax.swing.JButton managerEditButton;
    private javax.swing.JPanel managerPanel;
    private javax.swing.JButton managerResetButton;
    private javax.swing.JButton managerSearchButton;
    private javax.swing.JButton myAdminPanelButton;
    private javax.swing.JButton myContactsButton;
    private javax.swing.JButton myManagerPanelButton;
    private javax.swing.JButton myPanelButton;
    private javax.swing.JPanel raportsByManagerPanel;
    private javax.swing.JButton reportsButton;
    private javax.swing.JButton resetSearchButton;
    private javax.swing.JButton saveClientButton;
    private javax.swing.JButton saveClientByManagerButton;
    private javax.swing.JButton saveUserButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton searchClientByManagerButton;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JButton searchSearchButton;
    private javax.swing.JButton searchUserButton;
    private javax.swing.JPanel searchUserByManagerPanel;
    private javax.swing.JPanel searchUserPanel;
    private javax.swing.JButton selectUserButton;
    private javax.swing.JButton sendMailButton;
    private javax.swing.JPanel sendMailPanel;
    private javax.swing.JButton sendOneMailButton;
    private javax.swing.JTable tableClients;
    private javax.swing.JTable tableClientsForManager;
    private javax.swing.JTable tableUsers;
    private javax.swing.JPanel topManagerPanel;
    private javax.swing.JPanel topPanel;
    private javax.swing.JPanel topPanel1;
    private javax.swing.JTextField txtAddUserLogin;
    private javax.swing.JTextField txtAddUserName;
    private javax.swing.JPasswordField txtAddUserPassword1;
    private javax.swing.JPasswordField txtAddUserPassword2;
    private javax.swing.JTextField txtAddUserSurname;
    private javax.swing.JTextField txtAddUserdate;
    private javax.swing.JComboBox txtAddUsertype;
    private javax.swing.JTextField txtAdminLogin;
    private javax.swing.JTextField txtAdminName;
    private javax.swing.JTextField txtAdminSearchName;
    private javax.swing.JTextField txtAdminSearchSurname;
    private javax.swing.JTextField txtAdminSurname;
    private javax.swing.JTextField txtAdminType;
    private javax.swing.JTextField txtCLientCity;
    private javax.swing.JPasswordField txtChangePass1;
    private javax.swing.JPasswordField txtChangePass2;
    private javax.swing.JTextField txtClientCreateDate;
    private javax.swing.JTextField txtClientCreator;
    private javax.swing.JTextArea txtClientDesc;
    private javax.swing.JTextField txtClientMail;
    private javax.swing.JTextField txtClientModification;
    private javax.swing.JTextField txtClientName;
    private javax.swing.JTextField txtClientNumber;
    private javax.swing.JTextField txtClientPesel;
    private javax.swing.JTextField txtClientPhone1;
    private javax.swing.JTextField txtClientPhone2;
    private javax.swing.JFormattedTextField txtClientPostalCode;
    private javax.swing.JTextField txtClientStreet;
    private javax.swing.JTextField txtClientSurname;
    private javax.swing.JCheckBox txtClientTel;
    private javax.swing.JTextField txtClientTelDate;
    private javax.swing.JCheckBox txtClientVip;
    private javax.swing.JButton txtDetailsClientButton;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextArea txtMailContent;
    private javax.swing.JTextField txtMailSubject;
    private javax.swing.JTextField txtManagerLogin;
    private javax.swing.JTextField txtManagerName;
    private javax.swing.JTextField txtManagerSearchPesel;
    private javax.swing.JTextField txtManagerSearchSurname;
    private javax.swing.JTextField txtManagerSurname;
    private javax.swing.JTextField txtManagerType;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextArea txtReceip;
    private javax.swing.JTextField txtSearchPesel;
    private javax.swing.JTextField txtSearchSurname;
    private javax.swing.JButton txtSendMultipleMail;
    private javax.swing.JTextField txtUserLogin;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextField txtUserRaport;
    private javax.swing.JTextField txtUserSurname;
    private javax.swing.JTextField txtUserType;
    private javax.swing.JPanel userPanel;
    private javax.swing.JTable usersForManagerTable;
    // End of variables declaration//GEN-END:variables
}
