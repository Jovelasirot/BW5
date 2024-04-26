package team5.BW5.CSVImport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import team5.BW5.entities.Town;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class ComuniCSV {

    private final JdbcTemplate jdbcTemplate;
    @Value("classpath:CSVFile/comuni-italiani.csv")
    private Resource provinceCSV;

    public ComuniCSV(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void importTownData() {
        try (InputStream inputStream = provinceCSV.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] townData = line.trim().split(";");

                if (townData.length < 4) continue;

                String provinceId = townData[0];
                String townId = townData[1];
                String townName = townData[2];
                String provinceName = townData[3];

                Town town = new Town();
                town.setProvinceId(provinceId);
                town.setTownId(townId);
                town.setTownName(townName);
                town.setProvinceName(provinceName);

                // Salva la provincia nel database
                jdbcTemplate.update("INSERT INTO town (province_id, town_id, town_name, province_name) VALUES (?, ?, ?, ?)",
                        provinceId, townId, townName, provinceName);
            }

            System.out.println("Dati importati con successo nella tabella town.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
