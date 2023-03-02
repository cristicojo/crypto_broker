package crypto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LimitOrderDto {

	private int id;
	@NotNull
	private BigDecimal priceLimit;

	@NotNull
	private Integer amount;

	@NotNull
	private AccountDto accountDetails;

	private boolean processed;

}
