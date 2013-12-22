package dto;

import java.util.Date;

import entity.DatePersonalization;

public class DatePersonalizationDTO extends PersonalizationDTO {
	private int duration;
	private Date date;
	
	public DatePersonalizationDTO(DatePersonalization datePersonalization) {
		this.duration = datePersonalization.getDuration();
		this.date = (Date)datePersonalization.getDate().clone();
		this.id = datePersonalization.getId();
	}
	
	public DatePersonalizationDTO(int duration, Date date) {
		super();
		this.duration = duration;
		this.date = date;
		this.id = -1;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Date getInitialDate() {
		return date;
	}
	public void setInitialDate(Date date) {
		this.date = date;
	}
	public void setFinalDate(Date date) {
		if(date.getTime() < this.date.getTime())
			throw new IllegalArgumentException();
		this.duration = (int)((date.getTime() - this.date.getTime())/(1000*60));	//Calculate time in minutes
	}
	public Date getFinalDate() {
		return new Date(date.getTime()+duration*1000*60);		//Calculate time in minutes
	}
	
	

}
