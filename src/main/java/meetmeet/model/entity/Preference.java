package meetmeet.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Preference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long preferenceId;
	
	@Column(name = "account_id", insertable=false, updatable=false)
	private String accountId;
	
	private String category;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id") 
	private Account account;
	
}
