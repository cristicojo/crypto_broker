package crypto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class AccountDto {
	private Long id;
	@NotEmpty
	@Size(min = 2, message = "name should have at least 2 characters")
	private String name;

	@NotNull
	@DecimalMin(value = "11111.000", message = "the value entered must be more than 11111.000")
	private BigDecimal usdBalance;

	@Min(value = 0, message = "must be equal to 0")
	@Max(value = 0, message = "must be equal to 0")
	private int btcBalance;
}
