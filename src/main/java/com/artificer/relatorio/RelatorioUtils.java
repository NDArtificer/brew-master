package com.artificer.relatorio;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class RelatorioUtils {

    public static JasperReport loadReport(String nomeRelatorio) {
        JasperReport jasperReport = null;

        // 1. Tenta carregar o .jasper via classpath
        String pathName = "/relatorios/%s.jasper".formatted(nomeRelatorio);
        try (InputStream jasperStream = RelatorioUtils.class
                .getResourceAsStream(pathName)) {
            if (jasperStream != null) {
                jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            }
        } catch (Exception ignored) {}

        // 2. Se não achou, tenta compilar o .jrxml via classpath
        if (jasperReport == null) {
            pathName = "/relatorios/%s.jrxml".formatted(nomeRelatorio);
            try (InputStream jrxmlStream = RelatorioUtils.class
                    .getResourceAsStream(pathName)) {
                if (jrxmlStream != null) {
                    JasperDesign design = JRXmlLoader.load(jrxmlStream);
                    jasperReport = JasperCompileManager.compileReport(design);
                }
            } catch (Exception ignored) {}
        }

        // 3. Se ainda não achou, usa caminho absoluto com user.dir
        if (jasperReport == null) {
            String baseDir = System.getProperty("user.dir");
            String pathname = "%s/src/main/resources/relatorios/%s.jasper".formatted(baseDir, nomeRelatorio);
            File jasperFile = new File(pathname);
            if (jasperFile.exists()) {
                try {
                    jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(jasperFile));
                } catch (Exception ignored) {}
            } else {
                pathname = "%s/src/main/resources/relatorios/%s.jrxml".formatted(baseDir, nomeRelatorio);
                File jrxmlFile = new File(pathname);
                if (jrxmlFile.exists()) {
                    try {
                        JasperDesign design = JRXmlLoader.load(jrxmlFile);
                        jasperReport = JasperCompileManager.compileReport(design);
                    } catch (Exception ignored) {}
                }
            }
        }

        if (jasperReport == null) {
            throw new IllegalStateException("Relatório %s não encontrado!".formatted(nomeRelatorio));
        }

        return jasperReport;
    }
}
