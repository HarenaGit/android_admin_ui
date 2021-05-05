package mg.ny.adminui.data_model;

import java.util.ArrayList;

public class VisualizationDataModel {
    private String flightId;
    private String planeId;
    private String planeName;
    private ArrayList<ReservationDataModel> reservation;

    public VisualizationDataModel(String flightId, String planeId, String planeName, ArrayList<ReservationDataModel> reservation) {
        this.flightId = flightId;
        this.planeId = planeId;
        this.planeName = planeName;
        this.reservation = reservation;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getPlaneId() {
        return planeId;
    }

    public String getPlaneName() {
        return planeName;
    }

    public ArrayList<ReservationDataModel> getReservation() {
        return reservation;
    }
}
