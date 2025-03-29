package github.ryz.swiftapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CountrySwiftCodesResponseDto {
    private String countryISO2;
    private String countryName;
    private List<BankDetailsDto> swiftCodes;
}
