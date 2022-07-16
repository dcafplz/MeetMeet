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
	private String hashSalt;
	private String pwQuestion;
	private String nickName;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [accountId=");
		builder.append(accountId);
		builder.append(", pw=");
		builder.append(pw);
		builder.append(", hashSalt=");
		builder.append(hashSalt);
		builder.append(", pwQuestion=");
		builder.append(pwQuestion);
		builder.append(", nickName=");
		builder.append(nickName);
		builder.append("]");
		return builder.toString();
	}
}
