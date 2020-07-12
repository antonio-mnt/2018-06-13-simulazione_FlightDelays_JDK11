package it.polito.tdp.flightdelays.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;
import it.polito.tdp.flightdelays.model.Event.EventType;



public class Simulator {
	
	
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	private FlightDelaysDAO dao;
	
	private Map<Integer, Double> ritardi;
	private Map<Integer, Integer> numeroVoli;
	
	private Map<Integer, Airport> idMap;
	
	private int voli = 5;
	
	
	public void simula(int k, int v, List<Airport> aeroporti, Map<Integer,Airport> map) {
		
		this.dao = new FlightDelaysDAO();
		this.ritardi = new TreeMap<>();
		this.numeroVoli = new HashMap<>();
		this.idMap = map;
		
		this.voli = v;
		
		for(int i = 0; i<k; i++) {
			this.ritardi.put(i, 0.0);
			this.numeroVoli.put(i, 0);
		}
		
		this.queue.clear();
		
		for(Integer in: this.ritardi.keySet()) {
			Airport a = estraiAeroporto(aeroporti);
			LocalDateTime time = LocalDateTime.of(2014, 1 , 1, 1, 1, 1);
			
			Event ev = new Event(EventType.PARTENZA,null,a,time,null,in);
			this.queue.add(ev);
			
		}
		
		while(!this.queue.isEmpty()) {
		    Event e = this.queue.poll();
		    processEvent(e);
		}
		
		
		
		
		
	}
	
	
	
	private void processEvent(Event e) {
		switch(e.getType()) {
		case PARTENZA:
			
			
			if(this.numeroVoli.get(e.getPersona())>=this.voli) {
				
			}else {
				Flight volo = this.dao.loadAllFlights(e.getArrivo(), e.getTime());
				if(volo==null) {
					
				}else {
					Event ev  = new Event(EventType.PARTENZA,e.getArrivo(),this.idMap.get(volo.getDestinationAirportId()),volo.getArrivalDate(),volo,e.getPersona());
					
					this.queue.add(ev);
					this.numeroVoli.put(e.getPersona(),this.numeroVoli.get(e.getPersona())+1);
					this.ritardi.put(e.getPersona(), this.ritardi.get(e.getPersona())+volo.getArrivalDelay());
				}
				
				
			}
			

			
			break;
		}
		
	}



	public Airport estraiAeroporto(List<Airport> aeroporti) {
		
		int i = (int) (Math.random()*aeroporti.size());
		
		return aeroporti.get(i);
		
	}



	public Map<Integer, Double> getRitardi() {
		return ritardi;
	}
	
	

}
