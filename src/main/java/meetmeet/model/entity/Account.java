package meetmeet.model.entity;

<<<<<<< HEAD
import javax.persistence.Entity;
import javax.persistence.Id;
=======
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
=======
import javax.persistence.Entity;
import javax.persistence.Id;
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import lombok.ToString;
=======
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
=======
<<<<<<< HEAD
@ToString
@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private String accountId;
	private String pw;
	@Column(name = "hash_salt")
	private String hashSalt;
	@Column(name = "pw_question")
	private String pwQuestion;
	@Column(name = "nick_name")
	private String nickName;

	@OneToMany(mappedBy = "id1")
	private List<FriendList> friendlist1 = new ArrayList<FriendList>();

	@OneToMany(mappedBy = "id2")
	private List<FriendList> friendlist2 = new ArrayList<FriendList>();

	@OneToMany(mappedBy = "requestId")
	private List<FriendRequest> friendRequest1 = new ArrayList<FriendRequest>();

	@OneToMany(mappedBy = "requestedId")
	private List<FriendRequest> friendRequest2 = new ArrayList<FriendRequest>();
=======
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55

@Entity
public class Account {
	
	@Id
	private String accountId;
	private	String pw;
<<<<<<< HEAD
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
=======
	private String hash_salt;
	private String pwQuestion;
	private String nick_name;
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
}
