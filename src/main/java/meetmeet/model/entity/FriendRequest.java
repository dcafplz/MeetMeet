package meetmeet.model.entity;

<<<<<<< HEAD
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
=======
import javax.persistence.Entity;
import javax.persistence.Id;
>>>>>>> 786581eb55fffb82c73722630ad380c640916168

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
import lombok.ToString;
=======
>>>>>>> 786581eb55fffb82c73722630ad380c640916168

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
@ToString
@Entity
@IdClass(FriendRequestId.class)
public class FriendRequest implements Serializable {
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "request_id")
	@JsonIgnore
	private Account requestId;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "requested_id")
	@JsonIgnore
	private Account requestedId;

=======

@Entity
public class FriendRequest {
	@Id
	private long preferenceId;
	private String accountId;
	private String category;
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
}
