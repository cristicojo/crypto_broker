package crypto.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class AccountDto {
	private Long id;

	@Size(min = 2, message = "name should have at least 2 characters")
	@Pattern(regexp = "[A-Za-z]", message = "only letters accepted and it cannot contain null")
	private String name;

	@NotNull
	private BigDecimal usdBalance;

	@Min(value = 0, message = "must be equal to 0")
	@Max(value = 0, message = "must be equal to 0")
	private int btcBalance;
}
