package meetmeet.model.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

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
	private String category;
	private int maxParticipant;
}
