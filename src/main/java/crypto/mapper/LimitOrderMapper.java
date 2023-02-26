package crypto.mapper;

import crypto.dto.LimitOrderDto;
import crypto.entity.Account;
import crypto.entity.LimitOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LimitOrderMapper {

	LimitOrderMapper INSTANCE = Mappers.getMapper(LimitOrderMapper.class);


	@Mapping(target="accountDetails.name", source = "account.name")
	@Mapping(target="accountDetails.usdBalance", source = "account.usdBalance")
	@Mapping(target="accountDetails.id", source = "account.id")
	@Mapping(target="id", source = "limitOrder.id")
	LimitOrderDto orderToOrderDto(LimitOrder limitOrder, Account account);


	@Mapping(target="account", source = "limitOrderDto.accountDetails")
	LimitOrder orderDtoToOrder(LimitOrderDto limitOrderDto);


}
