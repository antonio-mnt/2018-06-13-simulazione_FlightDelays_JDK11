package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;

public class Model {
	
	private FlightDelaysDAO dao;
	private SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	private List<Airline> airlines;
	private List<Airport> aeroporti;
	private Map<Integer,Airport> idMap;
	private List<Arco> archi;
	
	public Model() {
		this.dao = new FlightDelaysDAO();
		this.airlines = new ArrayList<>(this.dao.loadAllAirlines());
	}
	
	
	public void creaGrafo(Airline linea) {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.aeroporti = new ArrayList<>(this.dao.loadAllAirports());
		
		Graphs.addAllVertices(this.grafo, this.aeroporti);
		
		this.idMap = new HashMap<>();
		
		for(Airport a: this.aeroporti) {
			this.idMap.put(a.getId(),a);
		}
		
		this.archi = new ArrayList<>(this.dao.loadAllArchi(linea, idMap));
		
		for(Arco a: this.archi) {
			Graphs.addEdge(this.grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		
	}


	public List<Airline> getAirlines() {
		return airlines;
	}
	
	public int getNumeroVertici() {
		return this.grafo.vertexSet().size();
	}
		
		
	public int getNumeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Arco> getPeggiori(){
		
		Collections.sort(this.archi);
		
		return this.archi ;
		
	}
	
	public Map<Integer,Double> simula(int k, int voli){
		
		Simulator sim = new Simulator();
		sim.simula(k, voli, aeroporti, idMap);
		
		return sim.getRitardi();
		
	}

}
