package com.sad.sadcrm;

import com.sad.sadcrm.hibernate.ClientDAO;
import com.sad.sadcrm.model.Client;
import com.sad.sadcrm.model.User;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.group.CustomGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.GroupHeaderLayout;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.datatype.DataTypes.booleanType;
import static net.sf.dynamicreports.report.builder.datatype.DataTypes.stringType;

public class ReportsUtil {
    private static JFileChooser fileChooser = new JFileChooser();
    private static FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls");

    public static void exportAll() {
        fileChooser.setFileFilter(filter);
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            exportClientsToFile(ClientDAO.searchClients());
        }
    }

    private static void exportClientsToFile(List<Client> clients) {
        exportWorkbookToFile(
                createWorkbookForAllClients(clients),
                getSelectedXlsFile()
        );
    }

    private static File getSelectedXlsFile() {
        File selectedFile = fileChooser.getSelectedFile();
        if (selectedFile.getAbsolutePath().endsWith(".xls")) {
            return selectedFile;
        }
        return new File(fileChooser.getSelectedFile() + ".xls");
    }

    private static void exportWorkbookToFile(HSSFWorkbook workbook, File fileToSave) {
        try {
            OutputStream fileStream = new FileOutputStream(fileToSave);
            workbook.write(fileStream);
            fileStream.close();

            JOptionPane.showMessageDialog(null,
                    "Raport został wygenerowany.",
                    "Raport",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void exportMy(User user) {
        fileChooser.setFileFilter(filter);
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            exportClientsToFile(ClientDAO.searchByUser(user));
        }
    }

    public static void exportTodays(String now) {
        fileChooser.setFileFilter(filter);
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String n = now.substring(0, 11);
            exportClientsToFile(ClientDAO.searchByCreateDate(n));
        }
    }

    private static HSSFWorkbook createWorkbookForAllClients(List<Client> allClients) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        createSheet(workbook.createSheet("new sheet"), allClients);
        createSheetHeader(workbook.createSheet("new sheet").createRow(0));
        return workbook;
    }

    private static void createSheet(HSSFSheet sheet, List<Client> clients) {
        int i = 1;
        for (Client c : clients) {
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(c.getId());
            row.createCell(1).setCellValue(c.getName());
            row.createCell(2).setCellValue(c.getSurname());
            row.createCell(3).setCellValue(c.getPesel());
            row.createCell(4).setCellValue(c.getPhone1());
            row.createCell(5).setCellValue(c.getPhone2());
            row.createCell(6).setCellValue(c.getAddress().getStreet());
            row.createCell(7).setCellValue(c.getAddress().getNumber());
            row.createCell(8).setCellValue(c.getAddress().getCity());
            row.createCell(9).setCellValue(c.getAddress().getPostCode());
            row.createCell(10).setCellValue(c.getMail());
            row.createCell(11).setCellValue(c.getProducts());
            row.createCell(12).setCellValue(c.getSellChance());
            row.createCell(13).setCellValue(c.getDescription());
            row.createCell(14).setCellValue(c.getVip());
            row.createCell(15).setCellValue(c.getCreated());
            row.createCell(16).setCellValue(c.getUser().getName() + " " + c.getUser().getSurname());

            i++;
        }
    }

    private static void createSheetHeader(HSSFRow rowHead) {
        rowHead.createCell(0).setCellValue("Id");
        rowHead.createCell(1).setCellValue("Imię");
        rowHead.createCell(2).setCellValue("Nazwisko");
        rowHead.createCell(3).setCellValue("Pesel");
        rowHead.createCell(4).setCellValue("Nr telefonu 1");
        rowHead.createCell(5).setCellValue("Nr telefonu 2");
        rowHead.createCell(6).setCellValue("Ulica");
        rowHead.createCell(7).setCellValue("Nr");
        rowHead.createCell(8).setCellValue("Miasto");
        rowHead.createCell(9).setCellValue("Kod pocztowy");
        rowHead.createCell(10).setCellValue("Mail");
        rowHead.createCell(11).setCellValue("Produkty");
        rowHead.createCell(12).setCellValue("Szansa sprzedaży");
        rowHead.createCell(13).setCellValue("Uwagi");
        rowHead.createCell(14).setCellValue("VIP");
        rowHead.createCell(15).setCellValue("Data utworzenia");
        rowHead.createCell(16).setCellValue("Utworzony przez");
    }

    public static void createTelephonesReport(int choose) {
        int day = getDayInterval(choose);

        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);
        CustomGroupBuilder ownerGroup = grp.group(new OwnerExpression());

        JasperReportBuilder report = DynamicReports.report();

        report.setColumnTitleStyle(columnTitleStyle)
                .highlightDetailEvenRows()
                .title(Components.text("Telefony do klientów z ostatniego dnia")
                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                        .setStyle(boldCenteredStyle))
                .columns(
                        Columns.column("Klient", "client", stringType()),
                        Columns.column("VIP", "vip", booleanType()),
                        Columns.column("Szansa sprzedaży", "chance", stringType()),
                        Columns.column("Data kontaktu", "contactDate", stringType()),
                        Columns.column("Opiekun", "owner", stringType())
                ).groupBy(ownerGroup)
                .pageFooter(Components.pageXofY())
                .setDataSource(createDataSourceForTelephones(ClientDAO.phonesFromDate(day)));

        try {
            showTheReport(report);
        } catch (DRException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static int getDayInterval(int choose) {
        switch (choose) {
            case 0:
                return 1;
            case 1:
                return 3;
            case 2:
                return 7;
            case 3:
                return 30;
            case 4:
                return 90;
            default:
                return 100;
        }
    }

    private static void showTheReport(JasperReportBuilder report) throws DRException {
        JasperPrint reportPrint = report.toJasperPrint();
        if (reportPrint.getPages().isEmpty()) {
            new JasperViewer(reportPrint, false);
        } else {
            JasperViewer reportViewer = new JasperViewer(reportPrint, false);
            reportViewer.setTitle("Raporty");
            reportViewer.setVisible(true);
        }
    }

    public static void createSellChanceReport() {
        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);
        CustomGroupBuilder sellChanceGroup = grp.group(new SellChanceExpression());

        JasperReportBuilder report = DynamicReports.report();

        report.setColumnTitleStyle(columnTitleStyle)
                .highlightDetailEvenRows()
                .title(Components.text("Raport - szansa spredaży produktów")
                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                        .setStyle(boldCenteredStyle))
                .columns(
                        Columns.column("Klient", "client", stringType()),
                        Columns.column("VIP", "vip", booleanType()),
                        Columns.column("Szansa sprzedaży", "chance", stringType()),
                        Columns.column("Telefon 1", "tel1", stringType()),
                        Columns.column("Telefon 2", "tel2", stringType()),
                        Columns.column("Opiekun", "owner", stringType())
                ).groupBy(sellChanceGroup)
                .pageFooter(Components.pageXofY())
                .setDataSource(createDataSourceForSellChance(ClientDAO.searchHasSellChance()));

        try {
            showTheReport(report);
        } catch (DRException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void createUserReport(User user) {
        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);

        CustomGroupBuilder itemGroup = grp.group("owner", String.class)
                .setStyle(boldStyle)
                .setTitle("")
                .setTitleStyle(boldStyle)
                .setTitleWidth(50)
                .setHeaderLayout(GroupHeaderLayout.TITLE_AND_VALUE);

        JasperReportBuilder report = DynamicReports.report();

        report.setColumnTitleStyle(columnTitleStyle)
                .highlightDetailEvenRows()
                .title(Components.text("Raport użytkownika " + user.getName() + " " + user.getSurname() + "\n").setStyle(boldCenteredStyle))
                .columns(
                        Columns.column("Klient", "client", stringType()),
                        Columns.column("VIP", "vip", booleanType()),
                        Columns.column("Szansa sprzedaży", "chance", stringType()),
                        Columns.column("Produkty", "products", stringType()),
                        Columns.column("Pesel", "pesel", stringType()),
                        Columns.column("Mail", "mail", stringType()),
                        Columns.column("Data telefonu", "tel", stringType())
                ).groupBy(itemGroup)
                .pageFooter(Components.pageXofY())
                .setDataSource(createUserReport(ClientDAO.searchByUser(user)));

        try {
            showTheReport(report);
        } catch (DRException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static JRDataSource createDataSourceForTelephones(List<Client> clients) {
        DRDataSource dataSource = new DRDataSource("client", "vip", "chance", "contactDate", "owner");
        for (Client client : clients) {
            dataSource.add(
                    client.getName() + " " + client.getSurname(),
                    client.getVip(),
                    client.getSellChance(),
                    client.getTelDate(),
                    client.getUser().getName() + " " + client.getUser().getSurname()
            );
        }

        return dataSource;
    }

    private static JRDataSource createDataSourceForSellChance(List<Client> clients) {
        DRDataSource dataSource = new DRDataSource("client", "vip", "chance", "tel1", "tel2", "owner");
        for (Client client : clients) {
            User owner = client.getUser();
            dataSource.add(
                    client.getName() + " " + client.getSurname(),
                    client.getVip(),
                    client.getSellChance(),
                    client.getPhone1(),
                    client.getPhone2(),
                    owner.getName() + " " + owner.getSurname()
            );
        }

        return dataSource;
    }

    private static JRDataSource createUserReport(List<Client> clients) {
        DRDataSource dataSource = new DRDataSource("owner", "client", "vip", "chance", "products", "pesel", "mail", "tel");
        for (Client client : clients) {
            User owner = client.getUser();
            dataSource.add(
                    owner.getName() + " " + owner.getSurname(),
                    client.getName() + " " + client.getSurname(),
                    client.getVip(),
                    client.getSellChance(),
                    client.getProducts(),
                    client.getPesel(),
                    client.getMail(),
                    client.getTelDate()
            );
        }
        return dataSource;
    }

    private static class OwnerExpression extends AbstractSimpleExpression<String> {
        private static final long serialVersionUID = 1L;

        @Override
        public String evaluate(ReportParameters reportParameters) {
            return reportParameters.getValue("owner");
        }
    }

    private static class SellChanceExpression extends AbstractSimpleExpression<String> {
        private static final long serialVersionUID = 1L;

        @Override
        public String evaluate(ReportParameters reportParameters) {
            return reportParameters.getValue("chance");
        }
    }
}
