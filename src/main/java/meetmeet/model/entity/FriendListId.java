package meetmeet.model.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FriendListId implements Serializable {
	private Account id1;
	private Account id2;

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		FriendListId friendListId = (FriendListId) object;
		return Objects.equals(id1, friendListId.id1) && Objects.equals(id2, friendListId.id2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id1, id2);
	}
}
