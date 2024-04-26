package team5.BW5.CSVImport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import team5.BW5.entities.Province;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class ProvinceCSV implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    @Value("classpath:CSVFile/province-italiane.csv")
    private Resource provinceCSV;

    public ProvinceCSV(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void importProvinceData() {
        try (InputStream inputStream = provinceCSV.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] provinceData = line.split(";");
                String provinceCode = provinceData[0];
                String provinceName = provinceData[1];
                String region = provinceData[2]; // Aggiungi la regione dal file CSV

                Province province = new Province();
                province.setProvinceCode(provinceCode);
                province.setProvinceName(provinceName);
                province.setRegion(region); // Imposta la regione sulla provincia


                // Salva la provincia nel database
                jdbcTemplate.update("INSERT INTO province (province_code, province_name, region) VALUES (?, ?, ?)",
                        provinceCode, provinceName, region);
            }

            System.out.println("Dati importati con successo nella tabella province.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.importProvinceData();
    }
}
