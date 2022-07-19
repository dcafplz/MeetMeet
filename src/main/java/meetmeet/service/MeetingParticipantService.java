package meetmeet.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meetmeet.model.dao.MeetingParticipantRepository;
import meetmeet.model.dto.MeetingDTO;
import meetmeet.model.dto.MeetingParticipantDTO;
import meetmeet.model.entity.Meeting;
import meetmeet.model.entity.MeetingParticipant;

@Service
public class MeetingParticipantService {
	
	@Autowired
	private MeetingParticipantRepository meetingParticipantRepository;
	
	private ModelMapper modelMapper = new ModelMapper(); // 추후 빈에 등록 필요
	
	
	public Long meetParticipate(MeetingParticipantDTO mp) throws Exception{       
        return meetingParticipantRepository.save(modelMapper.map(mp, MeetingParticipant.class)).getMeetingId();
    }
	
	public MeetingParticipantDTO searchByParticipantAndMeet(Long id1, String id2){
		try {
			return modelMapper.map(meetingParticipantRepository.
					findMeetingParticipantByMeetingIdAndParticipantId(id1, id2),
					MeetingParticipantDTO.class);
		}catch (Exception e) {
			return null;
		}
    }
    
    public boolean meetParticipationDelete(Long id1, String id2){
    	MeetingParticipantDTO result = searchByParticipantAndMeet(id1, id2);
    	if(result != null) {
    		meetingParticipantRepository.deleteById(result.getMeetingParticipantId());
    		return true;
    	}
    	return false;
    }
    
    public List<MeetingParticipant> searchByParticipant(String id) {
    	List<MeetingParticipant> p = meetingParticipantRepository.findMeetingParticipantByParticipantId(id);
    	p.forEach(e -> modelMapper.map(e, MeetingParticipantDTO.class));
    	return p;
    }
	

}
