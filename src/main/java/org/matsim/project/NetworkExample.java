package org.matsim.project;

import com.google.inject.grapher.NodeId;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.pt.transitSchedule.api.TransitStopArea;
import org.matsim.pt.transitSchedule.api.TransitStopFacility;
import org.matsim.utils.objectattributes.attributable.Attributes;

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
    static final int LINK_SPEED_COLUMN = 4;


    /*package*/ final Network network;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map <Integer,Link> links = new HashMap<>();
    private final List<TransitStopFacility> stopFacilities = new ArrayList<>();

    public NetworkExample(Network network) {
        this.network = network;
    }


    protected void init() {
        buildNetwork();
//        buildStops();
    }

    protected void buildNetwork() {
        Map <Integer,Node> nodes = new HashMap<>();

//        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Mike\\git\\matsim-example-project\\scenarios\\Erding\\LinTimCSV\\Nodes.CSV"))) {
            try (BufferedReader reader = new BufferedReader(new FileReader("./scenarios/Erding/LinTimCSV/Nodes.CSV"))) {
            for (int i = 0; i < 5; i++) {
                reader.readLine();
            }
            int orchilinkId = 999001;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                final int nodeId = Integer.parseInt(values[NODE_ID_COLUMN]);
                final double xCoordinate = Double.parseDouble(values[NODE_XCOORD_COLUMN].replace(",", "."));
                final double yCoordinate = Double.parseDouble(values[NODE_YCOORD_COLUMN].replace(",", "."));
                final Node node = this.network.getFactory().createNode(Id.create(nodeId, Node.class), new Coord(xCoordinate, yCoordinate));
                final Node node2 = this.network.getFactory().createNode(Id.create(nodeId + 900000, Node.class),new Coord(xCoordinate + 0.5,yCoordinate + 0.5));
                nodes.put(nodeId, node);
                nodes.put(nodeId + 900000, node2);


                final Link orchilink = this.network.getFactory().createLink(Id.create(orchilinkId, Link.class), node, node2);
                links.put(orchilinkId,orchilink);
                links.get(orchilinkId).setLength(50);
                links.get(orchilinkId).setFreespeed(100);
                links.get(orchilinkId).setCapacity(100000.0);
                orchilinkId++;
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





//        try (BufferedReader reader = new BufferedReader(new FileReader("./scenarios/Erding/LinTimCSV/Link-Orchi.CSV"))) {
            try (BufferedReader reader = new BufferedReader(new FileReader("./scenarios/Erding/LinTimCSV/Link.CSV"))) {

                for (int i = 0; i < 5; i++) {
                reader.readLine();
            }
            String line;
            int linkId = 0;
//            int schleifenlinkId = 999001;
            while((line = reader.readLine()) != null) {
                String[] values = line.split(";");
//                final int linkId = Integer.parseInt(values[LINK_ID_COLUMN]);
//                final int lowerbound = Integer.parseInt(values[]);
                final int fromId = Integer.parseInt(values[FROM_NODE_ID_COLUMN]);
                final int toId = Integer.parseInt(values[TO_NODE_ID_COLUMN]);
                final double length = Double.parseDouble(values[LINK_LENGTH_COLUMN].replace(",","."));//.replace(",","."));
                final int linkspeed = Integer.parseInt(values[LINK_SPEED_COLUMN]);
                final Link link = this.network.getFactory().createLink(Id.create( linkId, Link.class), nodes.get(fromId), nodes.get(toId));
//                Link schleifenlinks = this.network.getFactory().createLink(Id.create(schleifenlinkId, Link.class),nodes.get(fromId), nodes.get(fromId));


                links.put(linkId,link);
                links.get(linkId).setLength(length * 1000 - 50);
                links.get(linkId).setFreespeed(length * 1000 / (linkspeed * 60) );
//              links.get(linkId).setFreespeed(links.get(linkId).getLength() / (60 * lowerbound));
                links.get(linkId).setCapacity(100000.0);
//              links.get(linkId).setNumberOfLanes(1.0);
//                links.get(linkId).setAllowedModes(Collections.singleton(("car")));
                linkId++;

//                this.nodes[0]  = this.network.getFactory().createNode(Id.create("0", Node.class), new Coord((double) 0, (double) 5000));
//                this.nodes[1]  = this.network.getFactory().createNode(Id.create("1", Node.class), new Coord((double) 4000, (double) 5000));

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
//
//
//
//        Schleifenlinks werden erstellt, eine andere Datei wird eingelesen
//        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Mike\\git\\matsim-example-project\\scenarios\\Erding\\LinTimCSV\\Nodes.CSV"))) {
//            for (int i = 0; i < 5; i++) {
//                reader.readLine();
//            }
//
//        for (Map.Entry<Integer,Node> entry : nodes.entrySet()){
//            String line;
//            int nodeverbindungslink = 999001;
//            while((line = reader.readLine()) != null) {
//                String[] values = line.split(";");
//                final int fromId = Integer.parseInt(values[LINK_ID_COLUMN]);
//                final int toId = Integer.parseInt(values[TO_NODE_ID_COLUMN]);
//                final double length = Double.parseDouble(values[LINK_LENGTH_COLUMN].replace(",","."));//.replace(",","."));
//                Link nodeverbindung = this.network.getFactory().createLink(Id.create(nodeverbindungslink, Link.class),nodes.get(fromId), nodes.get(fromId));
////
//                links.put(nodeverbindungslink,nodeverbindung);
//                links.get(nodeverbindungslink).setLength(100);
//                links.get(nodeverbindungslink).setCapacity(100000.0);
//                links.get(nodeverbindungslink).setFreespeed(100);
//                nodeverbindungslink++;
////
//                }
//        }
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
        for (Map.Entry<Integer,Link> entry : links.entrySet()) {

            this.network.addLink(entry.getValue());
        }
//        this.links[0]  = this.network.getFactory().createLink(Id.create( "0", Link.class), nodes.get(fromId-1), this.nodes[toId-1]);

    }

//    protected void buildStops() {
//        this.nodes[0]  = this.network.getFactory().createNode(Id.create("0", Node.class), new Coord((double) 0, (double) 5000));
//        this.nodes[1]  = this.network.getFactory().createNode(Id.create("1", Node.class), new Coord((double) 4000, (double) 5000));
//    }
}

