package com.example.tracker.repositories;

import com.example.tracker.models.CasesRecord;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CasesRecordRepository extends CrudRepository<CasesRecord, Long> {
    @Override
    List<CasesRecord> findAll();
    CasesRecord findByCountryAndProvince(String country, String province);
}
