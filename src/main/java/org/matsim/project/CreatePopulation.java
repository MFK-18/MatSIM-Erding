package org.matsim.project;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.api.internal.MatsimWriter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class CreatePopulation {
    private static final int RIGHTSTOP_COLUMN = 1;
    private static final int LEFTSTOP_Column = 0;
    private static final int TRIPS_COLUMN = 2;
    private final Population population;
    private final Network network;

    public CreatePopulation(Population population, Network network){
        this.population = population;
        this.network = network;
    }

    public void populate () {

        PopulationFactory populationFactory = population.getFactory();

        ArrayList <ODTrips> listtrips = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Mike\\git\\matsim-example-project\\scenarios\\Erding\\Input\\LinTim\\OD.giv"))) {

            for (int i = 0; i < 1; i++) {
                reader.readLine();
            }
            String line;
            while((line = reader.readLine()) != null) {
                String [] values = line.split("; ");
                final int leftstopId = Integer.parseInt(values[LEFTSTOP_Column]);
                final int rightstopId = Integer.parseInt(values[RIGHTSTOP_COLUMN]);
                final Double tripsNo = Double.parseDouble(values[TRIPS_COLUMN]);
                final int tripsInt = (int) Math.ceil(tripsNo);//ceil rundet immer auf, mathround für 0.5> aufrunde)
                listtrips.add(new ODTrips(leftstopId,rightstopId,tripsInt));
//                Person person = populationFactory.createPerson((Id.create(Id.createPersonId(from File), Person.class)));
//                population.addPerson(person);
//                Plan plan = populationFactory.createPlan();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        int totalTrips = 0;
        for(ODTrips aTrip : listtrips){
            totalTrips += aTrip.noOfTrips;

        }
//        System.out.println(totalTrips);

        final Map<Id<Node>,? extends Node> networkNodes = network.getNodes();


        for (ODTrips oneODpair: listtrips){
            for (int i = 0; i < oneODpair.noOfTrips; i++){
                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i;
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                    population.addPerson(person);
                    Plan plan = populationFactory.createPlan();

                    Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                    Activity activity1 = populationFactory.createActivityFromCoord("home",homeCoord);
                    double workstart = 25200;
//                            14400 * Math.random() + 21600;
                    activity1.setEndTime(workstart);
                    plan.addActivity(activity1);

                    plan.addLeg(populationFactory.createLeg("pt"));

                    Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                    Activity activity2 = populationFactory.createActivityFromCoord("work",(destinationCoord));
                    activity2.setEndTime(workstart + 28880);//4pm, need to be somehow randomized as well
                    plan.addActivity(activity2);
//
                    plan.addLeg(populationFactory.createLeg("pt"));
//
                    Activity activity3 = populationFactory.createActivityFromCoord("home",(homeCoord));
                    plan.addActivity(activity3);

                    person.addPlan(plan);
            }
        }

        MatsimWriter popWriter = new PopulationWriter(population);
        popWriter.write("./scenarios/Erding/Input/population-17-11-2020.xml");

        /*
         * Create a Person designated "1" and add it to the Population.
         */
//        Person person = populationFactory.createPerson(Id.create("1", Person.class));
//        population.addPerson(person);
//
//        Plan plan = populationFactory.createPlan();
//
//        /*
//         * Create a "home" Activity for the Person. In order to have the Person end its day at the same location,
//         * we keep the home coordinates for later use (see below).
//         * Note that we use the CoordinateTransformation created above.
//         */
//
//        Coord homeCoord = new Coord();
//        Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
//        activity1.setEndTime(21600);
//        plan.addActivity(activity1);
//        /*
//         * Create a Leg. A Leg initially hasn't got many attributes. It just says that a car will be used.
//         */
//        plan.addLeg(populationFactory.createLeg("minibus"));
//
//        Activity activity2 = populationFactory.createActivityFromCoord("work", (new Coord(14.34024, 51.75649)));
//        activity2.setEndTime(57600); // leave at 4 p.m.
//        plan.addActivity(activity2);
//
//        /*
//         * Create another car Leg.
//         */
//       // plan.addLeg(populationFactory.createLeg("car"));
//
//        /*
//         * End the day with another Activity at home. Note that it gets the same coordinates as the first activity.
//         */
//        Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
//        plan.addActivity(activity3);
//        person.addPlan(plan);
    }
}
