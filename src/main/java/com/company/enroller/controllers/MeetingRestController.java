package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") Long id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) { 
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK); 
	}
	
	@RequestMapping(value="", method = RequestMethod.POST)
	public ResponseEntity<?> createMeeting(@RequestBody Meeting meeting){
		if (meetingService.findById(meeting.getId())!=null) {
			return new ResponseEntity<String>("Meeting with this id '" + meeting.getId() + "' already exists", HttpStatus.CONFLICT);
		}
		meetingService.add(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeeting(@PathVariable("id") Long id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) { 
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} 
		meetingService.delete(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.NO_CONTENT); 
	}
	
	@RequestMapping(value="/{id}/participants/{login}", method = RequestMethod.POST)
	public ResponseEntity<?> addMeetingParticipant(@PathVariable("id") Long id, @PathVariable("login") String login){
		Meeting meeting = meetingService.findById(id);
		ParticipantService participantService = new ParticipantService();
		Participant participant = participantService.findByLogin(login);
		if (meeting == null) {
			return new ResponseEntity<String>("Meeting with this id '" + id + "' doesn't exist", HttpStatus.CONFLICT);
		} 
		if (participant == null) {
			return new ResponseEntity<String>("Participant with this login '" + login + "' doesn't exist", HttpStatus.CONFLICT);
		}
		if (meeting.getParticipants().contains(participant)) {
			return new ResponseEntity<String>("'" + login + "' is already a participant of meeting '" + id + "'", HttpStatus.CONFLICT);
		}
		meetingService.addMeetingParticipant(meeting, participant);
		return new ResponseEntity<Collection<Participant>>(meeting.getParticipants(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{id}/participants/{login}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeMeetingParticipant(@PathVariable("id") Long id, @PathVariable("login") String login){
		Meeting meeting = meetingService.findById(id);
		ParticipantService participantService = new ParticipantService();
		Participant participant = participantService.findByLogin(login);
		if (meeting == null) {
			return new ResponseEntity<String>("Meeting with this id '" + id + "' doesn't exist", HttpStatus.CONFLICT);
		} 
		if (participant == null) {
			return new ResponseEntity<String>("Participant with this login '" + login + "' doesn't exist", HttpStatus.CONFLICT);
		}
		if (!meeting.getParticipants().contains(participant)) {
			return new ResponseEntity<String>("'" + login + "' is not a participant of meeting '" + id + "'", HttpStatus.CONFLICT);
		}
		meetingService.removeMeetingParticipant(meeting, participant);
		return new ResponseEntity<Collection<Participant>>(meeting.getParticipants(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetingParticipants(@PathVariable("id") Long id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) { 
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity<Collection<Participant>>(meeting.getParticipants(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}/title", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMeetingTitle(@PathVariable("id") Long id, @RequestBody String title){
		Meeting meeting = meetingService.findById(id);
		if (meeting==null) {
			return new ResponseEntity<String>("Meeting with this id '" + id + "' doesn't exist", HttpStatus.CONFLICT);
		}
		meetingService.updateMeetingTitle(meeting, title);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
	}
	@RequestMapping(value="/{id}/description", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMeetingDescription(@PathVariable("id") Long id, @RequestBody String description){
		Meeting meeting = meetingService.findById(id);
		if (meeting==null) {
			return new ResponseEntity<String>("Meeting with this id '" + id + "' doesn't exist", HttpStatus.CONFLICT);
		}
		meetingService.updateMeetingDescription(meeting, description);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
	}
	@RequestMapping(value="/{id}/date", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMeetingDate(@PathVariable("id") Long id, @RequestBody String date){
		Meeting meeting = meetingService.findById(id);
		if (meeting==null) {
			return new ResponseEntity<String>("Meeting with this id '" + id + "' doesn't exist", HttpStatus.CONFLICT);
		}
		meetingService.updateMeetingDate(meeting, date);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
	}
}

