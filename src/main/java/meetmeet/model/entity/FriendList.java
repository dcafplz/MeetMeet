package meetmeet.model.entity;

<<<<<<< HEAD
import javax.persistence.Entity;
import javax.persistence.Id;
=======
<<<<<<< HEAD
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@IdClass(FriendListId.class)
public class FriendList implements Serializable {

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id1")
	@JsonIgnore
	private Account id1;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id2")
	@JsonIgnore
	private Account id2;

=======
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55

@Entity
public class FriendList {
	
	@Id
	private long friendListId;
	private String id1;
	private String id2;
	
<<<<<<< HEAD
=======
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
}
