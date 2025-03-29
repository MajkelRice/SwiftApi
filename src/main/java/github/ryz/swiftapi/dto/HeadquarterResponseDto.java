package github.ryz.swiftapi.dto;

import github.ryz.swiftapi.model.Bank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class HeadquarterResponseDto implements BankDetailsDto {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;
    private List<BranchResponseDto> branches;

    public static HeadquarterResponseDto from(Bank bank, List<Bank> branches) {
        List<BranchResponseDto> branchDtos = branches.stream()
                .map(BranchResponseDto::from)
                .collect(Collectors.toList());
        return new HeadquarterResponseDto(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountry().getCode(),
                bank.getCountry().getName(),
                bank.isHeadquarter(),
                bank.getSwiftCode(),
                branchDtos
        );
    }
}
