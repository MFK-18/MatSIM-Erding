package org.matsim.project;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.api.internal.MatsimWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.run.NetworkCleaner;

public class NetworkExamplePrintout {

    public static void main(String[] args) {

        Config config = ConfigUtils.createConfig();
        Scenario scenario = ScenarioUtils.loadScenario(config);
        Network network = scenario.getNetwork();
        NetworkExample example = new NetworkExample(network);
        example.init(); // iwas.iwas methoden aufruf

//      Population population = scenario.getPopulation();
//		CreatePopulation createPopulation = new CreatePopulation(population, network);
//		createPopulation.populate();
//
//      MatsimWriter popWriter = new PopulationWriter(population);
//      popWriter.write("./scenarios/Erding/Input/population-21-12-2020-6-12am--1-7pm.xml");

        new NetworkWriter(example.network).write("./scenarios/Erding/Input/NetworkErding-22-12-2020-Schleifenlinks(final).xml");
//        NetworkCleaner networkcleaner = new NetworkCleaner();
//        networkcleaner.run("./scenarios/Erding/Input/ErdingScenarioMatsim-14-11-2020.xml","./scenarios/Erding/Input/ErdingScenarioMatsim-14-11-2020-networkcelaned.xml");
    }
}
