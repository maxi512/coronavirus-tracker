package com.example.tracker.services;

import com.example.tracker.models.CasesRecord;
import com.example.tracker.repositories.CasesRecordRepository;
import com.example.tracker.utils.CSVGetter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;

@Service
public class CoronavirusDataService {
    @Autowired
    private CasesRecordRepository casesRecordRepository;
    private final String PROVINCE_COLUMN_LABEL = "Province/State";
    private final String COUNTRY_COLUMN_LABEL = "Country/Region";

    @PostConstruct
    public void fetchCases() throws IOException, InterruptedException {
        casesRecordRepository.deleteAll();
        createRecordsFromCSV(CSVGetter.getCSV());
    }

    @Scheduled(cron = "0 12 * * ?")
    public void updateCases() throws IOException, InterruptedException{
        for (CSVRecord record: getCSVRecordsIterable(CSVGetter.getCSV())){
            CasesRecord casesRecord = casesRecordRepository
                    .findByCountryAndProvince(record.get(COUNTRY_COLUMN_LABEL), record.get(PROVINCE_COLUMN_LABEL));
            long todayCases = Long.parseLong(record.get(record.size() - 1));
            long newCases = todayCases - casesRecord.getConfirmed();
            casesRecord.setConfirmed(todayCases);
            casesRecord.setNewCases(newCases);
            casesRecordRepository.save(casesRecord);
        }
    }

    private void createRecordsFromCSV(String csv) throws IOException {
        for (CSVRecord record : getCSVRecordsIterable(csv)) {
            String province = record.get(PROVINCE_COLUMN_LABEL);
            String country = record.get(COUNTRY_COLUMN_LABEL);
            long todayCases = Long.parseLong(record.get(record.size() - 1));
            long newCases = todayCases - Long.parseLong(record.get(record.size() - 2));
            CasesRecord casesRecord = new CasesRecord();
            casesRecord.setCountry(country);
            casesRecord.setProvince(province);
            casesRecord.setConfirmed(todayCases);
            casesRecord.setNewCases(newCases);
            casesRecordRepository.save(casesRecord);
        }
    }

    private Iterable<CSVRecord> getCSVRecordsIterable(String csv) throws IOException {
        StringReader csvBodyReader = new StringReader(csv);
        return CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvBodyReader);
    }

}
