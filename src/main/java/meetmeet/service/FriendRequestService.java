package meetmeet.service;

import java.util.ArrayList;
import java.util.List;

import meetmeet.model.dto.FriendListDTO;

public interface FriendRequestService {
	List<List<String>> findFriendRequestByRequestId(String requestId);

	List<List<String>> findFriendRequestByRequestedId(String requestedId);
}
