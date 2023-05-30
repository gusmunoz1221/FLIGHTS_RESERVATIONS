package com.Travel.Travel.api.controllers;

import com.Travel.Travel.infraestructure.abstract_services.IReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@Tag(name = "Report")
public class ReportController {
    private final IReportService reportService;
    //ccuando el navegador lo lea, va hacer automaticamente la descarga
    private static MediaType FORCE_DOWNLOAD = new MediaType("application","force-download");
    private static String FORCE_DOWNLOAD_HEADER_VALUE = "attachment; filename = report.xlsx";

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    //EL ARCHIVO NO SE LEE POR POSTAM SI NO SE DESCARGA COMO UNA IMAGEN EN EL NAVEGADOR
    @GetMapping
    ResponseEntity<Resource> get(){
        var headers = new HttpHeaders();

        //seteamos los headers
        headers.setContentType(FORCE_DOWNLOAD);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,FORCE_DOWNLOAD_HEADER_VALUE);

        var fileInBytes = reportService.readFile();
        var response = new ByteArrayResource(fileInBytes);

        return ResponseEntity.ok()
                             .headers(headers)
                             .contentLength(fileInBytes.length)
                             .contentType(MediaType.APPLICATION_OCTET_STREAM)
                             .body(response);
    }
}