package github.ryz.swiftapi.configuration;

import github.ryz.swiftapi.exception.SwiftCodesCSVNotFoundException;
import github.ryz.swiftapi.model.Bank;
import github.ryz.swiftapi.model.Country;
import github.ryz.swiftapi.repository.BankRepository;
import github.ryz.swiftapi.repository.CountryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.InputStream;

@Component
public class DataLoader implements CommandLineRunner {
    private final BankRepository bankRepository;
    private final CountryRepository countryRepository;

    public DataLoader(BankRepository bankRepository, CountryRepository countryRepository) {
        this.bankRepository = bankRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public void run(String... args) throws SwiftCodesCSVNotFoundException {
        long count = bankRepository.count();
        if (count == 0) {
            InputStream csv = getClass().getClassLoader().getResourceAsStream("swift_codes.csv");
            if (csv == null) {
                throw new SwiftCodesCSVNotFoundException();
            }

            try (InputStreamReader reader = new InputStreamReader(csv)) {
                Iterable<CSVRecord> records = CSVFormat.EXCEL.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .build()
                        .parse(reader);

                for (CSVRecord record : records) {
                    bankRepository.save(toBank(record));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Data loaded successfully!");
        } else {
            System.out.println("Data already exists in the table.");
        }
    }

    private Bank toBank(CSVRecord record) {
        String swiftCode = record.get(1);

        Country country = getOrCreateCountry(record.get(6), record.get(0));

        return new Bank(
                record.get(4),
                record.get(3),
                country,
                swiftCode.endsWith("XXX"),
                swiftCode
        );
    }

    private Country getOrCreateCountry(String name, String code) {
        return countryRepository.findByName(name).orElseGet(() ->
                countryRepository.save(new Country(name, code)));
    }
}
