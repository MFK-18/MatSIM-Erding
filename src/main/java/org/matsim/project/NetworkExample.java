package org.matsim.project;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.pt.transitSchedule.api.TransitStopFacility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*package*/ class NetworkExample {

    static final int NODE_ID_COLUMN = 0;
//    static final int NODE_CODE_COLUMN = 1;
//    static final int NODE_NAME_COLUMN = 2;
    static final int NODE_XCOORD_COLUMN = 3;
    static final int NODE_YCOORD_COLUMN = 4;

    static final int LINK_ID_COLUMN = 0;
    static final int FROM_NODE_ID_COLUMN = 1;
    static final int TO_NODE_ID_COLUMN = 2;
    static final int LINK_LENGTH_COLUMN = 3;


    /*package*/ final Network network;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map <Integer,Link> links = new HashMap<>();
    private final List<TransitStopFacility> stopFacilities = new ArrayList<>();

    public NetworkExample(Network network) {
        this.network = network;
    }


    protected void init() {
        buildNetwork();
    }

    protected void buildNetwork() {
        Map <Integer,Node> nodes = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Mike\\git\\matsim-example-project\\scenarios\\Erding\\LinTimCSV\\Nodes.CSV"))) {
            for (int i = 0; i < 5; i++) {
                reader.readLine();
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                final int nodeId = Integer.parseInt(values[NODE_ID_COLUMN]);
                final double xCoordinate = Double.parseDouble(values[NODE_XCOORD_COLUMN].replace(",", "."));
                final double yCoordinate = Double.parseDouble(values[NODE_YCOORD_COLUMN].replace(",", "."));
                final Node node = this.network.getFactory().createNode(Id.create(nodeId, Node.class), new Coord(xCoordinate, yCoordinate));
                nodes.put(nodeId, node);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
//        nodes[0]  = this.network.getFactory().createNode(Id.create("0", Node.class), new Coord((double) 0, (double) 5000));
        for (Map.Entry<Integer,Node> entry : nodes.entrySet()) {
            this.network.addNode(entry.getValue());
        }





        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Mike\\git\\matsim-example-project\\scenarios\\Erding\\LinTimCSV\\Link.CSV"))) {
            for (int i = 0; i < 5; i++) {
                reader.readLine();
            }
            String line;
            int linkId = 0;
            while((line = reader.readLine()) != null) {
                String[] values = line.split(";");
//                final int linkId = Integer.parseInt(values[LINK_ID_COLUMN]);

                final int fromId = Integer.parseInt(values[FROM_NODE_ID_COLUMN]);
                final int toId = Integer.parseInt(values[TO_NODE_ID_COLUMN]);
                final double length = Double.parseDouble(values[LINK_LENGTH_COLUMN].replace(",","."));//.replace(",","."));
                final Link link = this.network.getFactory().createLink(Id.create( linkId, Link.class), nodes.get(fromId), nodes.get(toId));
                links.put(linkId,link);
                links.get(linkId).setLength(length);
//              links.get(linkId).setFreespeed(44.44);
//              links.get(linkId).setCapacity(2000.0);
//              links.get(linkId).setNumberOfLanes(1.0);
                links.get(linkId).setAllowedModes(Collections.singleton(("car, pt")));
                linkId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        for (Map.Entry<Integer,Link> entry : links.entrySet()) {

            this.network.addLink(entry.getValue());
        }
//        this.links[0]  = this.network.getFactory().createLink(Id.create( "0", Link.class), nodes.get(fromId-1), this.nodes[toId-1]);

    }
}

