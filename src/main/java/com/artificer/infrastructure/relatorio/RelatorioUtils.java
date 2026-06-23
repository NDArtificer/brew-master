package com.artificer.infrastructure.relatorio;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class RelatorioUtils {

    public static final String JASPER_PATH = "/relatorios/%s.jasper";
    public static final String JRXML_PATH = "/relatorios/%s.jrxml";
    public static final String JASPER_ABSOLUTE_PATH = "%s/src/main/resources/relatorios/%s.jasper";
    public static final String JRXML_ABSOLUTE_PATH = "%s/src/main/resources/relatorios/%s.jrxml";

    public static JasperReport loadReport(String nomeRelatorio) {
        JasperReport jasperReport = null;

        String pathName = JASPER_PATH.formatted(nomeRelatorio);
        try (InputStream jasperStream = RelatorioUtils.class
                .getResourceAsStream(pathName)) {
            if (jasperStream != null) {
                
                jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            }
        } catch (Exception ignored) {}

        if (jasperReport == null) {
            pathName = JRXML_PATH.formatted(nomeRelatorio);
            try (InputStream jrxmlStream = RelatorioUtils.class
                    .getResourceAsStream(pathName)) {
                if (jrxmlStream != null) {
                    JasperDesign design = JRXmlLoader.load(jrxmlStream);
                    jasperReport = JasperCompileManager.compileReport(design);
                }
            } catch (Exception ignored) {}
        }

        if (jasperReport == null) {
            String baseDir = System.getProperty("user.dir");
            String pathname = JASPER_ABSOLUTE_PATH.formatted(baseDir, nomeRelatorio);
            File jasperFile = new File(pathname);
            if (jasperFile.exists()) {
                try {
                    jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(jasperFile));
                } catch (Exception ignored) {}
            } else {
                pathname = JRXML_ABSOLUTE_PATH.formatted(baseDir, nomeRelatorio);
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
