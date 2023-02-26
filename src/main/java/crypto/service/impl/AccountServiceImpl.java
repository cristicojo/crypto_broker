package crypto.service.impl;

import crypto.dto.AccountDto;
import crypto.mapper.AccountMapper;
import crypto.repository.AccountRepository;
import crypto.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import static crypto.constant.AccountConstant.ACCOUNT_NOT_FOUND;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepository repo;


	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		return AccountMapper.INSTANCE
				.accountToAccountDto(repo.save(AccountMapper.INSTANCE
						.accountDtoToAccount(accountDto)));
	}

	@Override
	public AccountDto fetchAccount(Long id) {
		return AccountMapper.INSTANCE
				.accountToAccountDto(repo.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND + id)));
	}


}
