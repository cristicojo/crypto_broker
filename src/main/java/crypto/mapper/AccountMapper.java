package crypto.mapper;

import crypto.dto.AccountDto;
import crypto.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);


	AccountDto accountToAccountDto(Account account);
	Account accountDtoToAccount(AccountDto dto);
}
