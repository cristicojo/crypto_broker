package crypto.service;

import crypto.dto.AccountDto;

public interface AccountService {

	AccountDto createAccount(AccountDto accountDto);

	AccountDto fetchAccount(Long id);


}
