/* *********************************************************************** *
 * project: org.matsim.*												   *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package org.matsim.project;


import ch.sbb.matsim.routing.pt.raptor.SwissRailRaptor;
import ch.sbb.matsim.routing.pt.raptor.SwissRailRaptorModule;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.*;
import org.matsim.contrib.minibus.PConfigGroup;
import org.matsim.contrib.minibus.hook.PModule;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigGroup;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.run.NetworkCleaner;

/**
 * @author nagel
 *
 */
public class RunMatsim{

//	private final static Logger log = Logger.getLogger(RunMinibus.class);

	public static void main(String[] args) {

//		Config config = ConfigUtils.loadConfig( args[0], new PConfigGroup() ) ;
//		pConfig.getCostPerKilometer();


		Config config = ConfigUtils.loadConfig("./scenarios/Erding/Input/config.xml", new PConfigGroup());
//		Config config = ConfigUtils.loadConfig("./scenarios/Erding/Input/minibusconfig.xml", new PConfigGroup());
//		Config config = ConfigUtils.loadConfig("./scenarios/Erding/Input/minibusconfigoriginal.xml", new PConfigGroup());

//		Config config = ConfigUtils.loadConfig("https://svn.vsp.tu-berlin.de/repos/public-svn/matsim/scenarios/countries/atlantis/minibus/config.xml", new PConfigGroup());
//		ConfigUtils.addOrGetModule(config, PConfigGroup.GROUP_NAME, PConfigGroup.class);
//		config.network().setInputFile(null);
//		config.plans().setInputFile(null);

//		Config config = ConfigUtils.createConfig();
		Scenario scenario = ScenarioUtils.loadScenario(config);

		Network network = scenario.getNetwork();
		NetworkExample networkExample = new NetworkExample(network);
		networkExample.init();

		Population population = scenario.getPopulation();
		CreatePopulation createPopulation = new CreatePopulation(population, network);
		createPopulation.populate();

		// possibly modify config here
		config.controler().setOutputDirectory("./Erding/output/24-11-2020-first400iterRun(stillwrongSpeed)(1)");
		config.plans().isRemovingUnneccessaryPlanAttributes();
		config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);

		// possibly modify scenario here

		
		Controler controler = new Controler( scenario ) ;
//		controler.getConfig().controler().setCreateGraphs(false);

		// possibly modify controler here

		controler.addOverridingModule( new PModule()) ;
		controler.addOverridingModule( new SwissRailRaptorModule());

		config.plansCalcRoute().setInsertingAccessEgressWalk( true );
		// ---
		
		controler.run();
	}
}
