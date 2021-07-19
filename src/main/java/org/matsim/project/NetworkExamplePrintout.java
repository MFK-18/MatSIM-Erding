package org.matsim.project;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.contrib.minibus.PConfigGroup;
import org.matsim.core.api.internal.MatsimWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.TransitSchedule;
import org.matsim.pt.transitSchedule.api.TransitScheduleReader;
import org.matsim.pt.utils.TransitScheduleValidator;
import org.matsim.run.NetworkCleaner;

public class NetworkExamplePrintout {

    public static void main(String[] args) {

//        Config config = ConfigUtils.createConfig();
        Config config = ConfigUtils.loadConfig("./scenarios/Erding/Input/config.xml", new PConfigGroup());
        Scenario scenario = ScenarioUtils.loadScenario(config);
        Network network = scenario.getNetwork();
        NetworkExample example = new NetworkExample(network);
        example.init(); // iwas.iwas methoden aufruf

//        TransitSchedule erdingts = scenario.getTransitSchedule();
      Population population = scenario.getPopulation();
//		CreatePopulation createPopulation = new CreatePopulation(population, network);
//		createPopulation.populate();
////
//      MatsimWriter popWriter = new PopulationWriter(population);
//      popWriter.write("./scenarios/Erding/Input/population-2021-03-24-6-10-rand.xml");

//        TransitScheduleReader read = new TransitScheduleReader("C:\\Users\\Mike\\git\\");
//        TransitScheduleValidator.validateAll(erdingts,network);
//        TransitScheduleValidator.printResult(TransitScheduleValidator.validateAll(erdingts,network));

//        NetworkCleaner networkcleaner = new NetworkCleaner();
//        networkcleaner.run("./scenarios/Erding/Input/NetworkErding-23-01-2021(Orchi-mit-1speed).xml","./scenarios/Erding/Input/NetworkErding-23-01-2021(Orchi-mit-1speed)(cleaned).xml");

//        new NetworkWriter(example.network).write("./scenarios/Erding/Input/NetworkErding-25-01-2021-Orchi.xml");
//        new NetworkWriter(example.network).write("./scenarios/Erding/Input/NetworkErding-16-02-2021(Test).xml");

    }
}
