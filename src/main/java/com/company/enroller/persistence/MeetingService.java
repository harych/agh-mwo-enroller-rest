package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;
	Session session;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
		session = connector.getSession();
	}

	public Collection<Meeting> getAll() {
		return session.createCriteria(Meeting.class).list();
	}
	
	public Meeting findById(Long id) {
		Meeting meeting = (Meeting)session.get(Meeting.class, id);
		return meeting;
	}
	
	public void add(Meeting meeting) {
		Transaction transaction = session.beginTransaction();
		session.save(meeting);
		transaction.commit();
	}
	
	public void delete(Meeting meeting) {
		Transaction transaction = session.beginTransaction();
		session.delete(meeting);
		transaction.commit();	
	}
	
	public void addMeetingParticipant(Meeting meeting, Participant participant) {
		Transaction transaction = session.beginTransaction();
		meeting.addParticipant(participant);
		session.save(meeting);
		transaction.commit();
	}
	
	public void removeMeetingParticipant(Meeting meeting, Participant participant) {
		Transaction transaction = session.beginTransaction();
		meeting.removeParticipant(participant);
		session.save(meeting);
		transaction.commit();
	}
	
	public void updateMeetingTitle(Meeting meeting, String title) {
		Transaction transaction = session.beginTransaction();
		meeting.setTitle(title);
		session.save(meeting);
		transaction.commit();
	}
	public void updateMeetingDescription(Meeting meeting, String description) {
		Transaction transaction = session.beginTransaction();
		meeting.setDescription(description);
		session.save(meeting);
		transaction.commit();
	}
	public void updateMeetingDate(Meeting meeting, String date) {
		Transaction transaction = session.beginTransaction();
		meeting.setDate(date);
		session.save(meeting);
		transaction.commit();
	}

}
