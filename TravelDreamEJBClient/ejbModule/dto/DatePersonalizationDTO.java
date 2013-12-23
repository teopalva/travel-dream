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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + duration;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatePersonalizationDTO other = (DatePersonalizationDTO) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (duration != other.duration)
			return false;
		return true;
	}
	
	

}
