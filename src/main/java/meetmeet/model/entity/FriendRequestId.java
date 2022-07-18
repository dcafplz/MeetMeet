package meetmeet.model.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestId implements Serializable {
	private Account requestId;
	private Account requestedId;

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		FriendRequestId friendRequestId = (FriendRequestId) object;
		return Objects.equals(requestId, friendRequestId.requestId)
				&& Objects.equals(requestedId, friendRequestId.requestedId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(requestId, requestedId);
	}
}
