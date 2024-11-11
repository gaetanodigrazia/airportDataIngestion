package com.digrazia.dataIngestion.integration.model;

import java.util.Objects;

public class FlightInfoEntity {

    private String icao24;
    private long firstSeen;
    private String estDepartureAirport;
    private long lastSeen;
    private String estArrivalAirport;
    private String callsign;
    private int estDepartureAirportHorizDistance;
    private int estDepartureAirportVertDistance;
    private int estArrivalAirportHorizDistance;
    private int estArrivalAirportVertDistance;
    private int departureAirportCandidatesCount;
    private int arrivalAirportCandidatesCount;

    public FlightInfoEntity() {}

    public String getIcao24() {
        return icao24;
    }

    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    public long getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(long firstSeen) {
        this.firstSeen = firstSeen;
    }

    public String getEstDepartureAirport() {
        return estDepartureAirport;
    }

    public void setEstDepartureAirport(String estDepartureAirport) {
        this.estDepartureAirport = estDepartureAirport;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getEstArrivalAirport() {
        return estArrivalAirport;
    }

    public void setEstArrivalAirport(String estArrivalAirport) {
        this.estArrivalAirport = estArrivalAirport;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public int getEstDepartureAirportHorizDistance() {
        return estDepartureAirportHorizDistance;
    }

    public void setEstDepartureAirportHorizDistance(int estDepartureAirportHorizDistance) {
        this.estDepartureAirportHorizDistance = estDepartureAirportHorizDistance;
    }

    public int getEstDepartureAirportVertDistance() {
        return estDepartureAirportVertDistance;
    }

    public void setEstDepartureAirportVertDistance(int estDepartureAirportVertDistance) {
        this.estDepartureAirportVertDistance = estDepartureAirportVertDistance;
    }

    public int getEstArrivalAirportHorizDistance() {
        return estArrivalAirportHorizDistance;
    }

    public void setEstArrivalAirportHorizDistance(int estArrivalAirportHorizDistance) {
        this.estArrivalAirportHorizDistance = estArrivalAirportHorizDistance;
    }

    public int getEstArrivalAirportVertDistance() {
        return estArrivalAirportVertDistance;
    }

    public void setEstArrivalAirportVertDistance(int estArrivalAirportVertDistance) {
        this.estArrivalAirportVertDistance = estArrivalAirportVertDistance;
    }

    public int getDepartureAirportCandidatesCount() {
        return departureAirportCandidatesCount;
    }

    public void setDepartureAirportCandidatesCount(int departureAirportCandidatesCount) {
        this.departureAirportCandidatesCount = departureAirportCandidatesCount;
    }

    public int getArrivalAirportCandidatesCount() {
        return arrivalAirportCandidatesCount;
    }

    public void setArrivalAirportCandidatesCount(int arrivalAirportCandidatesCount) {
        this.arrivalAirportCandidatesCount = arrivalAirportCandidatesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightInfoEntity that = (FlightInfoEntity) o;
        return firstSeen == that.firstSeen && lastSeen == that.lastSeen && estDepartureAirportHorizDistance == that.estDepartureAirportHorizDistance && estDepartureAirportVertDistance == that.estDepartureAirportVertDistance && estArrivalAirportHorizDistance == that.estArrivalAirportHorizDistance && estArrivalAirportVertDistance == that.estArrivalAirportVertDistance && departureAirportCandidatesCount == that.departureAirportCandidatesCount && arrivalAirportCandidatesCount == that.arrivalAirportCandidatesCount && Objects.equals(icao24, that.icao24) && Objects.equals(estDepartureAirport, that.estDepartureAirport) && Objects.equals(estArrivalAirport, that.estArrivalAirport) && Objects.equals(callsign, that.callsign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icao24, firstSeen, estDepartureAirport, lastSeen, estArrivalAirport, callsign, estDepartureAirportHorizDistance, estDepartureAirportVertDistance, estArrivalAirportHorizDistance, estArrivalAirportVertDistance, departureAirportCandidatesCount, arrivalAirportCandidatesCount);
    }

    @Override
    public String toString() {
        return "FlightEntity{" +
                "icao24='" + icao24 + '\'' +
                ", firstSeen=" + firstSeen +
                ", estDepartureAirport='" + estDepartureAirport + '\'' +
                ", lastSeen=" + lastSeen +
                ", estArrivalAirport='" + estArrivalAirport + '\'' +
                ", callsign='" + callsign + '\'' +
                ", estDepartureAirportHorizDistance=" + estDepartureAirportHorizDistance +
                ", estDepartureAirportVertDistance=" + estDepartureAirportVertDistance +
                ", estArrivalAirportHorizDistance=" + estArrivalAirportHorizDistance +
                ", estArrivalAirportVertDistance=" + estArrivalAirportVertDistance +
                ", departureAirportCandidatesCount=" + departureAirportCandidatesCount +
                ", arrivalAirportCandidatesCount=" + arrivalAirportCandidatesCount +
                '}';
    }
}
