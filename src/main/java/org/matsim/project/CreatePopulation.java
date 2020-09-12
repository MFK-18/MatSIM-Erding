package org.matsim.project;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.api.internal.MatsimWriter;
import org.matsim.core.router.TripStructureUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.matsim.core.controler.Controler.DefaultFiles.configReduced;
import static org.matsim.core.controler.Controler.DefaultFiles.network;

public class CreatePopulation {
    private static final int RIGHTSTOP_COLUMN = 1;
    private static final int LEFTSTOP_Column = 0;
    private static final int TRIPS_COLUMN = 2;
    private final Population population;

    public CreatePopulation (Population population){
        this.population = population;
    }

    public void populate () {

        /*
        Hallo Mike,
wenn ich auf https://github.com/FOR2083/PublicTransportNetworks/tree/master/Erding/Input/LinTim gehe, dann sehe ich da ein OD.giv

"The OD.giv file provides information for the demand on origin-destination level.
left-stop-ID: ID of the “left” stop
right-stop-ID: ID of the “right” stop
customers: It represent the amount of location changes in the network starting the trip at the origin and terminating the trip at the destination stop."

Das sieht für mich genau nach dem aus, was du suchst. Es enthält viele OD-Relationen mit 0 Fahrten, aber auch einige mit mehr Fahrten. Also alles ok.
Prüfe dann am Ende, ob du auch auf die 6697 Fahrten auf 2704 OD-Relationen kommst (https://github.com/FOR2083/PublicTransportNetworks/tree/master/Erding#demand-data)


Unklar ist nur wann diese Fahrten genau stattfinden. Da musst du mal in der Lösung nachschauen, auf welchen Zeitraum sich das bezieht
(Idee: anhand der Busfahrten in deren Lösung nachschauen, von wann bis wann es Abfahrten gibt).

Z.B. in https://github.com/FOR2083/PublicTransportNetworks/blob/master/Erding/Solutions/Solution_NDP_S007/Spreadsheet/VIS17_PuTAss_CapaRestNo_InterlYes_v06.xlsx
gibt es einen Reiter "vehjourney" mit den Spalten "DEP" und "ARR", was wohl Abfahrt bzw. Ankunftszeit in sec seit Mitternacht sind. Die S8 in Richtung ">"
gibt es z.B. von 15060 bis letzte Abfahrt 85860. Den Bus B1 von 18240 bis 83040 mit letzter Ankunft 84927 (alo ca. 5:04 bis 23:35). Da muss man nochmal nachhaken,
wie das gemeint ist und ob das Fahrtenangebot wirklich so gleichmäßig über den tag ist (immer gleicher Takt) wie es erstmal scheint. Ggf versuche da jemandem eine
Email zu schreiben, vielleicht allgemein dazu wie sie die Nachfrage auf den Tag verteilt haben (anhand der Datenformate vermute ich gleichmäßig über den ganzen Tag,
 aber besser fragen und wissen). Vielleicht ist es an dieser Stelle besser, wenn du erst mal versuchst die Lösung dort zu verstehen (musst du zum Vergleichen später sowieso)
  und erst danach in Matsim-Daten konvertierst.


Danach einfach OD-Relation nehmen und alle Fahrten möglichst gleichmäßig über den Zeitraum verteilen (= für jede Fahrt einen zufälligen Zeitpunkt im Zeitinterval ziehen,
sodass nicht alle Relationen mit nur 1 Fahrt gleichzeitig losfahren). Ungerade Fahrtenanzahlen runden (normal bis 0,5 abrunden, ab 0,5 aufrunden).
Dann nochmal prüfen ob du weiterhin (ungefähr) auf die 6697 Fahrten auf 2704 OD-Relationen kommst.





         * Pick the PopulationFactory out of the Population for convenience.
         * It contains methods to create new Population items.
         */

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
        System.out.println(totalTrips);

        int count = 1;
        //TODO Population createn looooops
        for (ODTrips oneODpair: listtrips){
            if (oneODpair.noOfTrips > 0){
                for (int i = 0; i < oneODpair.noOfTrips; i++){
                    Person person = populationFactory.createPerson(Id.create(count, Person.class));
                    population.addPerson(person);
                    Plan plan = populationFactory.createPlan();

                    Coord homeCoord = new Coord(); //TODO get coord form network file (nodes), same Node as oneODpar.leftstopId
                    Activity activity1 = populationFactory.createActivityFromCoord("home",homeCoord);
                    activity1.setEndTime(21600); //TODO let it be more randomized
                    plan.addActivity(activity1);

                    plan.addLeg(populationFactory.createLeg("pt"));
                    Activity activity2 = populationFactory.createActivityFromCoord("work",(new Coord()));//TODO get coord form network file (nodes), same Node as oneODpar.rightstopId
                    activity2.setEndTime(57600);//4pm, need to be somehow randomized as well
                    plan.addActivity(activity2);

                    plan.addLeg(populationFactory.createLeg("pt"));

                    Activity activity3 = populationFactory.createActivityFromCoord("home",(homeCoord));
                    person.addPlan(plan);

                    MatsimWriter popWriter = new PopulationWriter(population);
                    popWriter.write("./input/population.xml");
                    count++;
                }
            }
        }

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
