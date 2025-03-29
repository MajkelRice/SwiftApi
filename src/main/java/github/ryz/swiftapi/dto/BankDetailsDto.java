package github.ryz.swiftapi.dto;

import jakarta.validation.constraints.Size;

public interface BankDetailsDto {

    String getAddress();

    String getBankName();

    String getCountryISO2();

    String getCountryName();

    boolean isHeadquarter();

    @Size(min = 11, max = 11)
    String getSwiftCode();
}
