package crypto.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LimitOrderDto {

	private int id;
	private BigDecimal priceLimit;
	private int amount;

	private AccountDto accountDetails;

	private boolean processed;

}
