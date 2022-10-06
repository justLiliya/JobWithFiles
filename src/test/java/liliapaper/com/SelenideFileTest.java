package liliapaper.com;

import com.codeborne.selenide.Configuration;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.InflaterInputStream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFileTest {

    @BeforeAll
    public static void setUp() {
        Configuration.browserSize = "1920x1080";
    }


    //the most common way - from webelement with link "href"
    @Test
    public void fileFromHrefWithTryFinallyTest() throws Exception {
        open("http://www.24copy.ru/skachat-prajjs.html");
        File file = $("a[title='Скачать прайс-лист в формате Microsoft Excel']").download();
        InputStream is = new FileInputStream(file);
        try{
            XLS xls = new XLS(is);
            assertThat(xls.excel.getSheetAt(0)
                    .getRow(12)
                    .getCell(1)
                    .getStringCellValue())
                    .isEqualTo("Технические требования к оригинал - макетам для офсетной печати");
    }finally {
            is.close();
        }
    }

    @Test
    public void fileFromHrefWithTryWithResourcesTest() throws Exception {
        open("http://www.24copy.ru/skachat-prajjs.html");
        File file = $("a[title='Скачать прайс-лист в формате Microsoft Excel']").download();
        try(InputStream is = new FileInputStream(file)){
            XLS xls = new XLS(is);
            assertThat(xls.excel.getSheetAt(0)
                    .getRow(12)
                    .getCell(1)
                    .getStringCellValue())
                    .isEqualTo("Технические требования к оригинал - макетам для офсетной печати");
        }
    }

    @Test
    public void fileFromHrefWithoutTryCatchTest() throws Exception {
        open("https://github.com/hoppscotch/hoppscotch");
        final File file = $("a.Link--primary[href='#readme']").download();
        String contents = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        assertThat(contents).contains("Customizable combinations for background, foreground, and accent colors");
        }

        //download file by button
    @Test
    public void fileFromButton(){
        open("https://fineuploader.com/demos.html");
        File file = $("input[type='file']").uploadFromClasspath("матрица.png");
        $(".qq-file-info").shouldHave(text("матрица.png"));
        $(".qq-upload-size-selector.qq-upload-size").shouldHave(text("159.2kB"));
    }
}
