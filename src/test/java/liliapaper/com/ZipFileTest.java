package liliapaper.com;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipFileTest {

    ClassLoader cl = ZipFileTest.class.getClassLoader();

    @Test
    public void zipTest() throws Exception {
        InputStream is = cl.getResourceAsStream("peoples.zip");
        ZipFile zipfile = new ZipFile("src/test/resources/peoples.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            String entryName = entry.getName();
            if (entryName.contains("csv")) {
                try (InputStream in = zipfile.getInputStream(entry)) {
                    CSVReader reader = new CSVReader(new InputStreamReader(in));
                    List<String[]> content = reader.readAll();
                    String[] row = content.get(0);
                    String[] rowb = content.get(1);
                    String[] rowc = content.get(2);
                    assertThat(row[0]).isEqualTo("name; surname; company;");
                    assertThat(rowb[0]).isEqualTo("Albert; Trust; Amazon;");
                    assertThat(rowc[0]).isEqualTo("Helen; Olive; Gazprom;");
                }
            }
            if (entryName.contains("pdf")) {
                try (InputStream in = zipfile.getInputStream(entry)) {
                    PDF pdf = new PDF(in);
                    assertThat(pdf.numberOfPages).isEqualTo(23);
                }
            }
            if (entryName.contains("xlsx")) {
                try (InputStream in = zipfile.getInputStream(entry)) {
                    XLS xls = new XLS(in);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(50)
                            .getCell(1)
                            .getStringCellValue())
                            .isEqualTo("Rasheeda");
                }
            }
        }
    }
}

