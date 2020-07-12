package it.polito.tdp.flightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Airport;
import it.polito.tdp.flightdelays.model.Arco;
import it.polito.tdp.flightdelays.model.Flight;

public class FlightDelaysDAO {

	public List<Airline> loadAllAirlines() {
		String sql = "SELECT ID, IATA_CODE, AIRLINE " + 
				"FROM airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Airline(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRLINE")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Airport> loadAllAirports() {
		String sql = "SELECT ID, IATA_CODE, AIRPORT, CITY, STATE, COUNTRY, LATITUDE, LONGITUDE, TIMEZONE_OFFSET " + 
				"FROM airports";
		List<Airport> result = new ArrayList<Airport>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRPORT"), rs.getString("CITY"),
						rs.getString("STATE"), rs.getString("COUNTRY"), rs.getDouble("LATITUDE"), rs.getDouble("LONGITUDE"), rs.getDouble("TIMEZONE_OFFSET"));
				result.add(airport);
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public Flight loadAllFlights(Airport a, LocalDateTime li) {
		String sql = "SELECT ID, AIRLINE_ID, FLIGHT_NUMBER, TAIL_NUMBER, ORIGIN_AIRPORT_ID, DESTINATION_AIRPORT_ID, SCHEDULED_DEPARTURE_DATE, DEPARTURE_DELAY, " + 
				"ELAPSED_TIME, flights.DISTANCE, ARRIVAL_DATE, ARRIVAL_DELAY " + 
				"FROM flights " + 
				"WHERE ORIGIN_AIRPORT_ID = ? AND SCHEDULED_DEPARTURE_DATE > ? " + 
				"ORDER BY SCHEDULED_DEPARTURE_DATE\n" + 
				"LIMIT 1";
		Flight result = null;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			Timestamp ts = Timestamp.valueOf(li);
			st.setTimestamp(2,ts);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Flight flight = new Flight(rs.getInt("ID"), rs.getInt("AIRLINE_ID"), rs.getInt("FLIGHT_NUMBER"), rs.getString("TAIL_NUMBER"),
						rs.getInt("ORIGIN_AIRPORT_ID"), rs.getInt("DESTINATION_AIRPORT_ID"),
						rs.getTimestamp("SCHEDULED_DEPARTURE_DATE").toLocalDateTime(), rs.getDouble("DEPARTURE_DELAY"), rs.getDouble("ELAPSED_TIME"),
						 rs.getInt("flights.DISTANCE"),
						rs.getTimestamp("ARRIVAL_DATE").toLocalDateTime(),
						rs.getDouble("ARRIVAL_DELAY"));
				result = flight;
			}else {
				return null;
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	public List<Arco> loadAllArchi(Airline linea, Map<Integer,Airport> idMap) {
		String sql = "SELECT ORIGIN_AIRPORT_ID, DESTINATION_AIRPORT_ID, AVG(ARRIVAL_DELAY) as peso " + 
				"FROM flights " + 
				"WHERE AIRLINE_ID = ? " + 
				"GROUP BY ORIGIN_AIRPORT_ID, DESTINATION_AIRPORT_ID";
		List<Arco> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, linea.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport a1 = idMap.get(rs.getInt("ORIGIN_AIRPORT_ID"));
				Airport a2 = idMap.get(rs.getInt("DESTINATION_AIRPORT_ID"));
				Double peso = rs.getDouble("peso");
				
				if(a1!=null && a2!=null) {
					result.add(new Arco(a1,a2,peso));
				}
				
				
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}

