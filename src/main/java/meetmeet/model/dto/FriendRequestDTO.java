package meetmeet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FriendRequestDTO {
	
	private long preferenceId;
	private String accountId;
	private String category;
}
