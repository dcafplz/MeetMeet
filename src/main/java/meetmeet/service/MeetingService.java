package meetmeet.service;

import java.io.File;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import meetmeet.model.dao.MeetingRepository;
import meetmeet.model.dto.MeetingDTO;
import meetmeet.model.entity.Meeting;

@Service 
public class MeetingService {
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	@Autowired
	private FileSaveService fileSaveService;
	
	private ModelMapper modelMapper = new ModelMapper(); // 추후 빈에 등록 필요
	
	public Long meetCreate(MeetingDTO meeting, MultipartFile file) throws Exception{       

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        
        UUID uuid = UUID.randomUUID();  // random으로 식별자 이름 생

        String fileName = "meeting" + uuid + "_" + file.getOriginalFilename(); // 기존 file 이름 +
        
        fileSaveService.saveFileMeeting(file, "/"+ fileName, "/default_meeting.png");
        
        meeting.setFilename(fileName);
        meeting.setFilepath("/files/" + fileName);
        System.out.println("service : " + meeting);
        System.out.println(modelMapper.map(meeting, Meeting.class));
        Long id = meetingRepository.save(modelMapper.map(meeting, Meeting.class)).getMeetingId();
        return id;
    }
	
    public MeetingDTO meetView(Long id){
        return modelMapper.map(meetingRepository.findById(id).get(), MeetingDTO.class);
    }
    
    public void meetDelete(Long id){
    	meetingRepository.deleteById(id);
    }
    
    public Iterable<Meeting> meetList() {
    	Iterable<Meeting> p = meetingRepository.findAll();
    	p.forEach(e -> modelMapper.map(e, MeetingDTO.class));
    	return p;
    }

}
