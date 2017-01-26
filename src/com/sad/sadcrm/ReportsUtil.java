package com.sad.sadcrm;

import com.sad.sadcrm.hibernate.ClientDAO;
import com.sad.sadcrm.model.Client;
import com.sad.sadcrm.model.User;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
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
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

public class ReportsUtil {
    private static JFileChooser fileChooser = new JFileChooser();
    private static FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls");

    public static void exportAll() {
        try {
            System.out.println("EXPORT ALL");

            fileChooser.setFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getAbsolutePath().endsWith(".xls")) {
                    fileToSave = new File(fileChooser.getSelectedFile() + ".xls");
                }

                HSSFWorkbook workbook = createWorkbookForAllClients(ClientDAO.searchClients());

                FileOutputStream fileOutput = new FileOutputStream(fileToSave);
                workbook.write(fileOutput);
                fileOutput.close();
                JOptionPane.showMessageDialog(null,
                        "Raport został wygenerowany.",
                        "Raport",
                        JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Report generated!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void exportMy(User user) {
        try {
            System.out.println("EXPORT ALL");

            fileChooser.setFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getAbsolutePath().endsWith(".xls")) {
                    fileToSave = new File(fileChooser.getSelectedFile() + ".xls");
                }

                List<Client> allClients = ClientDAO.findClientsByUser(user);
                HSSFWorkbook hwb = createWorkbookForAllClients(allClients);

                FileOutputStream fileOutput = new FileOutputStream(fileToSave);
                hwb.write(fileOutput);
                fileOutput.close();
                JOptionPane.showMessageDialog(null,
                        "Raport został wygenerowany.",
                        "Raport",
                        JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Report generated!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void exportTodays(String now) {
        try {
            fileChooser.setFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getAbsolutePath().endsWith(".xls")) {
                    fileToSave = new File(fileChooser.getSelectedFile() + ".xls");
                }

                String n = now.substring(0, 11);
                HSSFWorkbook hwb = createWorkbookForAllClients(ClientDAO.findTodays(n));

                FileOutputStream fileOutput = new FileOutputStream(fileToSave);
                hwb.write(fileOutput);
                fileOutput.close();
                JOptionPane.showMessageDialog(null,
                        "Raport został wygenerowany.",
                        "Raport",
                        JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Report generated!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void setSheetHeader(HSSFRow rowHead) {
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

    private static void createSheet(HSSFSheet sheet, List<Client> clients) {
        int i = 1;
        for (Client c : clients) {
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(c.getIdClient());
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

    private static HSSFWorkbook createWorkbookForAllClients(List<Client> allClients) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("new sheet");
        createSheet(sheet, allClients);
        HSSFRow rowHead = sheet.createRow(0);
        setSheetHeader(rowHead);
        return hwb;
    }

    public void createTelephonesReport(int choose) {
        int day = 100;
        switch (choose) {
            case 0:
                day = 1;
                break;
            case 1:
                day = 3;
                break;
            case 2:
                day = 7;
                break;
            case 3:
                day = 30;
                break;
            case 4:
                day = 90;
                break;
        }

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
                        Columns.column("Klient", "client", DataTypes.stringType()),
                        Columns.column("VIP", "vip", DataTypes.characterType()),
                        Columns.column("Szansa sprzedaży", "chance", DataTypes.stringType()),
                        Columns.column("Data kontaktu", "contactDate", DataTypes.stringType()),
                        Columns.column("Opiekun", "owner", DataTypes.stringType())
                ).groupBy(ownerGroup)
                .pageFooter(Components.pageXofY())
                .setDataSource(createDataSourceForTelephones(ClientDAO.phonesFromDate(day)));

        try {
            showTheReport(report);
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private void showTheReport(JasperReportBuilder report) throws DRException {
        JasperPrint reportPrint = report.toJasperPrint();
        if (reportPrint.getPages().isEmpty()) {
            new JasperViewer(reportPrint, false);
        } else {
            JasperViewer reportViewer = new JasperViewer(reportPrint, false);
            reportViewer.setTitle("Raporty");
            reportViewer.setVisible(true);
        }
    }

    public void createSellChanceReport() {
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
                        Columns.column("Klient", "client", DataTypes.stringType()),
                        Columns.column("VIP", "vip", DataTypes.characterType()),
                        Columns.column("Szansa sprzedaży", "chance", DataTypes.stringType()),
                        Columns.column("Telefon 1", "tel1", DataTypes.stringType()),
                        Columns.column("Telefon 2", "tel2", DataTypes.stringType()),
                        Columns.column("Opiekun", "owner", DataTypes.stringType())
                ).groupBy(sellChanceGroup)
                .pageFooter(Components.pageXofY())
                .setDataSource(createDataSourceForSellChance(ClientDAO.sellChanceClients()));

        try {
            //show the report
            showTheReport(report);
        } catch (DRException e) {
            e.printStackTrace();
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
                        Columns.column("Klient", "client", DataTypes.stringType()),
                        Columns.column("VIP", "vip", DataTypes.characterType()),
                        Columns.column("Szansa sprzedaży", "chance", DataTypes.stringType()),
                        Columns.column("Produkty", "products", DataTypes.stringType()),
                        Columns.column("Pesel", "pesel", DataTypes.stringType()),
                        Columns.column("Mail", "mail", DataTypes.stringType()),
                        Columns.column("Data telefonu", "tel", DataTypes.stringType())
                ).groupBy(itemGroup)
                .pageFooter(Components.pageXofY())
                .setDataSource(createUserReport(ClientDAO.findClientsByUser(user)));

        try {
            showTheReport(report);
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private static JRDataSource createDataSourceForTelephones(List<Client> clients) {
        DRDataSource dataSource = new DRDataSource("client", "vip", "chance", "contactDate", "owner");
        for (Client c : clients) {
            dataSource.add(c.getName() + " " + c.getSurname(), c.getVip(), c.getSellChance(), c.getTelDate(), c.getUser().getName() + " " + c.getUser().getSurname());
        }

        return dataSource;
    }

    private static JRDataSource createDataSourceForSellChance(List<Client> clients) {
        DRDataSource dataSource = new DRDataSource("client", "vip", "chance", "tel1", "tel2", "owner");
        for (Client c : clients) {
            dataSource.add(c.getName() + " " + c.getSurname(), c.getVip(), c.getSellChance(), c.getPhone1(), c.getPhone2(), c.getUser().getName() + " " + c.getUser().getSurname());
        }

        return dataSource;
    }

    private static JRDataSource createUserReport(List<Client> clients) {
        DRDataSource dataSource = new DRDataSource("owner", "client", "vip", "chance", "products", "pesel", "mail", "tel");
        for (Client c : clients) {
            dataSource.add(c.getUser().getName() + " " + c.getUser().getSurname(), c.getName() + " " + c.getSurname(), c.getVip(), c.getSellChance(), c.getProducts(), c.getPesel(), c.getMail(), c.getTelDate());
        }

        return dataSource;
    }

    private class OwnerExpression extends AbstractSimpleExpression<String> {
        private static final long serialVersionUID = 1L;

        @Override
        public String evaluate(ReportParameters reportParameters) {
            return reportParameters.getValue("owner");
        }
    }

    private class SellChanceExpression extends AbstractSimpleExpression<String> {
        private static final long serialVersionUID = 1L;

        @Override
        public String evaluate(ReportParameters reportParameters) {
            return reportParameters.getValue("chance");
        }
    }
}
