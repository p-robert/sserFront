package com.userfront.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="user_email")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Email {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@Column(name="subject")
	private String subject;
	
	@Column(name = "email_toto")
	private String to;
	
	@Column(name = "email_from")
	private String from;
	
	@Column(name="send_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("From: " + from + "\n")
		  .append("To: " + to + "\n")
		  .append("Subject: \n" )
		  .append(subject);
		return sb.toString();
	}
	
	
}
