package github.ryz.swiftapi.dto;

import github.ryz.swiftapi.model.Bank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BranchResponseDto implements BankDetailsDto {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;

    public static BranchResponseDto from(Bank bank) {
        return new BranchResponseDto(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountry().getCode(),
                bank.getCountry().getName(),
                bank.isHeadquarter(),
                bank.getSwiftCode()
        );
    }
}
