package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService {

	DatabaseConnector connector;
	Session session;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
		session = connector.getSession();
	}

	public Collection<Participant> getAll() {
		return session.createCriteria(Participant.class).list();
	}

	public Participant findByLogin(String login) {
		Participant participant = (Participant)session.get(Participant.class, login);
		return participant;
	}

	public void add(Participant participant) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		session.save(participant);
		transaction.commit();
	}

	public void delete(Participant participant) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		session.delete(participant);
		transaction.commit();	
	}
}
