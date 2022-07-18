package meetmeet.model.entity;

<<<<<<< HEAD
import javax.persistence.Entity;
import javax.persistence.Id;

=======
<<<<<<< HEAD
import javax.persistence.Entity;
import javax.persistence.Id;

=======
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Meeting {
	
	@Id
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
	private long meetingId;
	private java.util.Date meetingDate;
	private String meetingName;
	private String meetingDetail;
	private String meetingPlace;
	private String master_id;
<<<<<<< HEAD
=======
=======
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private long meetingId;
	
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
