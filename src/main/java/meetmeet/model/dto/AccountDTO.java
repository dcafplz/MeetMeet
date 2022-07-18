package meetmeet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountDTO {

	private String accountId;
<<<<<<< HEAD
	private	String pw;
=======
	private String pw;
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
	private String hashSalt;
	private String pwQuestion;
	private String nickName;
}
