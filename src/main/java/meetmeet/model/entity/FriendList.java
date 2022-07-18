package meetmeet.model.entity;

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

}
