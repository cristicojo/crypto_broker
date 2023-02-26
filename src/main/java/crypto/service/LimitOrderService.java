package crypto.service;

import crypto.dto.LimitOrderDto;

public interface LimitOrderService {

	LimitOrderDto createLimitOrder(LimitOrderDto dto);
	LimitOrderDto fetchOrder(Long id);
}
