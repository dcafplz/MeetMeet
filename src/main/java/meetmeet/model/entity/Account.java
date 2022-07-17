package meetmeet.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Account {
	
	@Id
	private String accountId;
	private	String pw;
	private String hash_salt;
	private String pwQuestion;
	private String nick_name;
}
