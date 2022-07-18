package meetmeet.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

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

}
