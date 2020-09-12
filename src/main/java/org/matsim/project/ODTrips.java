package org.matsim.project;

public class ODTrips {
    public final int originID;

    public final int destinationID;

    public final int noOfTrips;

    public ODTrips(int originID, int destinationID, int noOfTrips) {
        this.originID = originID;
        this.destinationID = destinationID;
        this.noOfTrips = noOfTrips;
    }
}
