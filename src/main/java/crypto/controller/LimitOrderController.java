package crypto.controller;

import crypto.dto.LimitOrderDto;
import crypto.service.LimitOrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LimitOrderController {

	private final LimitOrderService service;


	@PostMapping("/createLimitOrder")
	public LimitOrderDto createLimitOrder(@RequestBody @Valid LimitOrderDto limitOrderDto) {
		return service.createLimitOrder(limitOrderDto);
	}

	@GetMapping("/fetchOrderDetails/{id}")
	public LimitOrderDto getOrderById(@PathVariable Long id) {
		return service.fetchOrder(id);
	}


}
