package com.userfront.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.AppointmentDao;
import com.userfront.domain.Appointment;
@Service
public class AppointmentServiceImpl implements AppointmentService {

	
	@Autowired
	private AppointmentDao appointmentDao;
	
	public Appointment createAppointment(Appointment appointment){
		return appointmentDao.save(appointment);
	}
	
	
	public List<Appointment> findAll() {
		return appointmentDao.findAll();
	}
	
	
	public void confirmAppointment(Long id) {
		Appointment appointment = findAppointment(id);
		appointment.setConfirmed(true);
		appointmentDao.save(appointment);
	}


	@Override
	public Appointment findAppointment(Long id) {
		// TODO Auto-generated method stub
		return appointmentDao.findOne(id);
	}
	
	
}
