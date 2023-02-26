package crypto.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import crypto.dto.LimitOrderDto;
import crypto.mapper.AccountMapper;
import crypto.mapper.LimitOrderMapper;
import crypto.repository.LimitOrderRepository;
import crypto.service.AccountService;
import crypto.service.LimitOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static crypto.constant.LimitOrderConstant.ACCOUNT_BALANCE_ERROR;
import static crypto.constant.LimitOrderConstant.AMOUNT;
import static crypto.constant.LimitOrderConstant.BUYING_PRICE;
import static crypto.constant.LimitOrderConstant.GET_RESPONSE;
import static crypto.constant.LimitOrderConstant.ORDER_ID;
import static crypto.constant.LimitOrderConstant.PRICE;
import static crypto.constant.LimitOrderConstant.PRICE_LIMIT;
import static crypto.constant.LimitOrderConstant.NOT_FOUND_EXCEPTION_MESSAGE;
import static crypto.constant.LimitOrderConstant.ORDER_NOT_FOUND;
import static crypto.constant.LimitOrderConstant.PRICE_KEY;
import static crypto.constant.LimitOrderConstant.URL;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;


@Service
@AllArgsConstructor
@Slf4j
public class LimitOrderServiceImpl implements LimitOrderService {

	private final LimitOrderRepository limitOrderRepository;
	private final AccountService accountService;

	@Override
	public LimitOrderDto createLimitOrder(LimitOrderDto dto) {
		var accountDto = accountService.fetchAccount(dto.getAccountDetails().getId());
		return LimitOrderMapper.INSTANCE
				.orderToOrderDto(limitOrderRepository.save(LimitOrderMapper.INSTANCE
						.orderDtoToOrder(dto)), AccountMapper.INSTANCE.accountDtoToAccount(accountDto));
	}

	@Override
	public LimitOrderDto fetchOrder(Long id) {
		var limitOrder = limitOrderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ORDER_NOT_FOUND + id));
		var accountDto = accountService.fetchAccount(limitOrder.getAccount().getId());
		return LimitOrderMapper.INSTANCE.orderToOrderDto(limitOrder, AccountMapper.INSTANCE.accountDtoToAccount(accountDto));

	}


	@Scheduled(cron = "${cron-string}")
	public void executeLimitOrder() {

		HttpResponse<String> response;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();

		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(URL))
					.build();

			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() == HTTP_NOT_FOUND) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_EXCEPTION_MESSAGE);
			}
			map = mapper.readValue(response.body(), Map.class);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		log.info(GET_RESPONSE + map.get(PRICE_KEY));
		var price = BigDecimal.valueOf((Double) map.get(PRICE_KEY))
				.setScale(3, RoundingMode.HALF_UP);

		limitOrderRepository.findAll()
				.parallelStream()
				.filter(order -> !order.isProcessed())
				.forEach(order -> {
					var buyingPrice = BigDecimal.valueOf(order.getAmount()).multiply(price);
					log.info(PRICE + price + PRICE_LIMIT + order.getPriceLimit() +
							BUYING_PRICE + buyingPrice + AMOUNT + order.getAmount());

					var account = AccountMapper.INSTANCE.accountDtoToAccount(accountService.fetchAccount(order.getAccount().getId()));
					if ((account.getUsdBalance().compareTo(buyingPrice)) < 0) {
						log.error(ACCOUNT_BALANCE_ERROR + account.getUsdBalance() + ORDER_ID + order.getId());
						return;
					}

					if ((price.compareTo(order.getPriceLimit())) < 0) {
						order.setProcessed(true);
						limitOrderRepository.save(order);
						account.setUsdBalance(account.getUsdBalance().subtract(buyingPrice));
						var totalBtc = account.getBtcBalance() + order.getAmount();
						account.setBtcBalance(totalBtc);
						accountService.createAccount(AccountMapper.INSTANCE.accountToAccountDto(account));
					}
				});

	}

}
