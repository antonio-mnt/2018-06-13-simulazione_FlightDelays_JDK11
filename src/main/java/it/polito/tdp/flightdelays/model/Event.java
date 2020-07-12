package it.polito.tdp.flightdelays.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		PARTENZA
	}
	
	private EventType type;
	private Airport partenza;
	private Airport arrivo;
	private LocalDateTime time;
	private Flight volo;
	private int persona;
	
	public Event(EventType type, Airport partenza, Airport arrivo, LocalDateTime time, Flight volo, int persona) {
		super();
		this.type = type;
		this.partenza = partenza;
		this.arrivo = arrivo;
		this.time = time;
		this.volo = volo;
		this.persona = persona;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public Airport getPartenza() {
		return partenza;
	}
	public void setPartenza(Airport partenza) {
		this.partenza = partenza;
	}
	public Airport getArrivo() {
		return arrivo;
	}
	public void setArrivo(Airport arrivo) {
		this.arrivo = arrivo;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public Flight getVolo() {
		return volo;
	}
	public void setVolo(Flight volo) {
		this.volo = volo;
	}
	public int getPersona() {
		return persona;
	}
	public void setPersona(int persona) {
		this.persona = persona;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + persona;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (persona != other.persona)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Event [type=" + type + ", partenza=" + partenza + ", arrivo=" + arrivo + ", time=" + time + ", volo="
				+ volo + ", persona=" + persona + "]";
	}
	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}
	
	
	
	

}
