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

//liest die Daten vom LinTim Input ein und wandelt die Formate für Matsim sinnvoll um

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

//die Pläne werden erstellt und im Namen die jeweilige Anfangszeit eingefügt, von 6-12Uhr

        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-6am";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 21600;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }
        for (ODTrips oneODpair: listtrips){
            for (int i = 0; i < oneODpair.noOfTrips; i++){
                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-7am";
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
                    activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                    plan.addActivity(activity2);
//
                    plan.addLeg(populationFactory.createLeg("pt"));
//
                    Activity activity3 = populationFactory.createActivityFromCoord("home",(homeCoord));
                    plan.addActivity(activity3);

                    person.addPlan(plan);
            }
        }
        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-8am";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 28800;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }

        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-9am";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 32400;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }

        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-10am";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 36000;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }
        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-11am";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 39600;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }
        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-12am";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 43200;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }


//        Rückfahrten




        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
//                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-1pm";
                final String idTag = oneODpair.destinationID + "-" + oneODpair.originID + "-" + i + "-1pm";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 46800;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }
        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
//                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-1pm";
                final String idTag = oneODpair.destinationID + "-" + oneODpair.originID + "-" + i + "-2pm";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 50400;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }

        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
//                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-1pm";
                final String idTag = oneODpair.destinationID + "-" + oneODpair.originID + "-" + i + "-3pm";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 54000;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }
        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
//                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-1pm";
                final String idTag = oneODpair.destinationID + "-" + oneODpair.originID + "-" + i + "-4pm";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 57600;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }
        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
//                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-1pm";
                final String idTag = oneODpair.destinationID + "-" + oneODpair.originID + "-" + i + "-5pm";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 61200;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }
        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
//                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-1pm";
                final String idTag = oneODpair.destinationID + "-" + oneODpair.originID + "-" + i + "-6pm";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 64800;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }
        for (ODTrips oneODpair: listtrips) {
            for (int i = 0; i < oneODpair.noOfTrips; i++) {
//                final String idTag = oneODpair.originID + "-" + oneODpair.destinationID + "-" + i + "-1pm";
                final String idTag = oneODpair.destinationID + "-" + oneODpair.originID + "-" + i + "-7pm";
                Person person = populationFactory.createPerson(Id.create(idTag, Person.class));
                population.addPerson(person);
                Plan plan = populationFactory.createPlan();

                Coord homeCoord = networkNodes.get(Id.createNodeId(oneODpair.destinationID)).getCoord();
                Activity activity1 = populationFactory.createActivityFromCoord("home", homeCoord);
                double workstart = 68400;
//                            14400 * Math.random() + 21600;
                activity1.setEndTime(workstart);
                plan.addActivity(activity1);

                plan.addLeg(populationFactory.createLeg("pt"));

                Coord destinationCoord = networkNodes.get(Id.createNodeId(oneODpair.originID)).getCoord();
                Activity activity2 = populationFactory.createActivityFromCoord("work", (destinationCoord));
                activity2.setEndTime(workstart + 28800);//4pm, need to be somehow randomized as well
                plan.addActivity(activity2);
//
                plan.addLeg(populationFactory.createLeg("pt"));
//
                Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
                plan.addActivity(activity3);

                person.addPlan(plan);
            }
        }
//        MatsimWriter popWriter = new PopulationWriter(population);
//        popWriter.write("./scenarios/Erding/Input/population-03-12-2020-3starttimes.xml");

    }
}
