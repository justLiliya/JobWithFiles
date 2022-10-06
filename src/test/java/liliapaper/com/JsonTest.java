package liliapaper.com;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {

    ClassLoader cl = JsonTest.class.getClassLoader();

    @Test
    public void jsonPlantsTest() throws Exception {
        File file = new File("src/test/resources/plants.json");

        InputStream js = cl.getResourceAsStream("plants.json");
        ObjectMapper mapper = new ObjectMapper();
        Plants plants = mapper.readValue(file, Plants.class);

        assertThat(plants.name()).isEqualTo("Ficus elastica");
        assertThat(plants.family()).isEqualTo("Mulberry");
        assertThat(plants.pests.isTrue).isTrue();
        assertThat(plants.lights).contains("slight shading");

    }
}
