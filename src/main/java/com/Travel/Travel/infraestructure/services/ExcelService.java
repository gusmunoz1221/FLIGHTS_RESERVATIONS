package com.Travel.Travel.infraestructure.services;

import com.Travel.Travel.domain.entities.CustomerEntity;
import com.Travel.Travel.domain.repositories.CustomerRepository;
import com.Travel.Travel.infraestructure.abstract_services.IReportService;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
public class ExcelService implements IReportService {
    private final CustomerRepository customerRepository;
    private static final String SHEET_NAME = "Customer total sales";
    private static final String FONT_TYPE = "Arial";
    private static final String COLUMN_CUSTOMER_ID = "id";
    private static final String COLUMN_CUSTOMER_NAME = "name";
    private static final String COLUMN_CUSTOMER_PURCHASES = "purchases";
    private static final String REPORTS_PATH_WITH_NAME = "reports/Sales-%s";
    private static final String REPORTS_PATH = "reports";
                                //extencion del archivo
    private static final String FILE_TYPE= ".xlsx";
    private static final String FILE_NAME= "Sales-%s.xlsx";

    //sumatoria de todos las compras de un cliente
    private static Integer getTotalPurchase(CustomerEntity customer) {
        return customer.getTotalLodgings() + customer.getTotalFlights() + customer.getTotalTours();
    }

    public ExcelService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public byte[] readFile() {
        try {
            createReport();
            var path = Paths.get(REPORTS_PATH,String.format(FILE_NAME,LocalDate.now().getMonth())).toAbsolutePath();
            return Files.readAllBytes(path);
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    private void createReport() {
        var workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet(SHEET_NAME);

        sheet.setColumnWidth(0, 7000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);

        var header = sheet.createRow(0);
        var headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        var font = workbook.createFont();
        font.setFontName(FONT_TYPE);
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        // encabezados --> atributos del excel
        var headerCell = header.createCell(0);
        headerCell.setCellValue(COLUMN_CUSTOMER_ID);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue(COLUMN_CUSTOMER_NAME);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue(COLUMN_CUSTOMER_PURCHASES);
        headerCell.setCellStyle(headerStyle);

        // creando estilo al excel
        var style = workbook.createCellStyle();
        style.setWrapText(true);

        var customers = this.customerRepository.findAll();
        var rowPos = 1;

        //recorremos cada cliente
        for (CustomerEntity customer : customers) {
            var row = sheet.createRow(rowPos);//numero que tiene la rowposition que se incrementa uno en uno
            var cell = row.createCell(0);//son posiciones estaticas (tres encabezados)
            cell.setCellValue(customer.getDni());
            cell.setCellStyle(style);

            cell = row.createCell(1);//son posiciones estaticas (tres encabezados)
            cell.setCellValue(customer.getFullName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(getTotalPurchase(customer));
            cell.setCellStyle(style);

            rowPos++;
        }
//creamos un archivo en la compu local
        var report = new File(String.format(REPORTS_PATH_WITH_NAME, LocalDate.now().getMonth()));
        var path = report.getAbsolutePath();
        var fileLocation = path + FILE_TYPE;

        //try whit resources evita vulnerabilidades cerrando los objetos de tipo buffer
        // cuando detecta qe tiene un metodo close lo cierra automaticamente
        // si no usamos workbook.close
        try (var outputStream = new FileOutputStream(fileLocation)) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            /// log.error("Cant create Excel", e);
            throw new RuntimeException();
        }
    }
}
