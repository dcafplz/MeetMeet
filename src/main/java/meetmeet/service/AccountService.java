package meetmeet.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meetmeet.model.dao.AccountRepository;
import meetmeet.model.dto.AccountDTO;
import meetmeet.model.entity.Account;

@Service
public class AccountService{
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private AccountRepository accountRepository;

	public void save(AccountDTO account) throws NoSuchAlgorithmException {
		Random random = new Random();
		account.setHashSalt(random.ints(48, 123).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(3)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());
		account.setPw(PwSecurityService.hashing(account.getPw(), account.getHashSalt()));
		Account accountEntity = modelMapper.map(account, Account.class);
		accountRepository.save(accountEntity);
	}

	public Optional<Account> findById(String accountId) {
		return accountRepository.findById(accountId);
	}

	public void changeNickName(String accountId, String nickName) {
		
		Account account = accountRepository.findById(accountId).get();
		account.setNickName(nickName);
		accountRepository.save(account);
		
	}

	public void changePw(String accountId, String pw) throws NoSuchAlgorithmException {
		
		Account account = accountRepository.findById(accountId).get();
		account.setPw(PwSecurityService.hashing(pw, account.getHashSalt()));
		accountRepository.save(account);
		
	}

	public String findPw(AccountDTO account) throws NoSuchAlgorithmException {

		Account accountEntity = findById(account.getAccountId()).get();
		if (accountEntity.getPwQuestion().equals(account.getPwQuestion())) {
			accountEntity.setPw(PwSecurityService.hashing(account.getPw(), accountEntity.getHashSalt()));
			accountRepository.save(accountEntity);
			return "redirect:/tologin";
		}
		return "redirect:/tofindpw";
	}

	public List<Account> findByNickNameContainingAndAccountIdNot(String searching, String id) {
		return accountRepository.findByNickNameContainingAndAccountIdNot(searching, id);
	}


}
