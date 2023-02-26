package crypto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Entity
@Table(name = "limit_order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LimitOrder {

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;


	@Id
	@Column(name = "order_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	@Column(name = "price_limit", precision = 10, scale = 3)
	private BigDecimal priceLimit;

	private int amount;

	@Column(columnDefinition = "BOOLEAN DEFAULT false")
	private boolean processed;


}
