package crypto.controller;

import crypto.dto.AccountDto;
import crypto.service.AccountService;
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
public class AccountController {

	private final AccountService service;


	@PostMapping("/createAccount")
	public AccountDto createAccount(@RequestBody @Valid AccountDto accountDto) {
		return service.createAccount(accountDto);
	}

	@GetMapping("/fetchAccountDetails/{id}")
	public AccountDto getAccountById(@PathVariable Long id) {
		return service.fetchAccount(id);
	}
}
