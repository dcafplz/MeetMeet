package meetmeet.model.dto;

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
import lombok.ToString;
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
@ToString
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55

public class MeetingDTO {
	
	private long meetingId;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
	private java.util.Date meetingDate;
	private String meetingName;
	private String meetingDetail;
	private String meetingPlace;
	private String master_id;
<<<<<<< HEAD
=======
=======
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate meetingStartDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate meetingEndDate;
	private String meetingName;
	private String meetingDetail;
	private String meetingPlace;
	private String meetingPlaceLat;
	private String meetingPlaceLng;
	private String master_id;
	private String filename;
    private String filepath;
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
	private String category;
	private int maxParticipant;
}
