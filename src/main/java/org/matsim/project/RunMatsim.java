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


import ch.sbb.matsim.routing.pt.raptor.SwissRailRaptorModule;
import org.apache.log4j.Logger;
import org.matsim.analysis.detailedPersonTripAnalysis.RunPersonTripAnalysis;
import org.matsim.api.core.v01.Scenario;
import org.matsim.contrib.minibus.PConfigGroup;
import org.matsim.contrib.minibus.hook.PModule;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author nagel
 *
 */
public class RunMatsim{

private static final Logger log = Logger.getLogger(RunMatsim.class );
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

//		Network network = scenario.getNetwork();
//		NetworkExample networkExample = new NetworkExample(network);
//		networkExample.init();

//		Population population = scenario.getPopulation();
//		CreatePopulation createPopulation = new CreatePopulation(population, network);
//		createPopulation.populate();
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter df = DateTimeFormatter.ISO_LOCAL_DATE;
		// possibly modify config here
		int iter = 500;//setzt die Anzahl der Iterations fest
		config.controler().setLastIteration(iter);
		config.controler().setWriteEventsInterval(iter);
		config.controler().setWritePlansInterval(iter);
		config.controler().setOutputDirectory("./Erding/output/" + now.format(df) + "-" + iter + "iter(10perc-0.8-0.08ea)");
//		config.plans().isRemovingUnneccessaryPlanAttributes();
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
//		runAnalysis(controler);
	}


//	public static void runAnalysis(Controler controler) {
//		Config config = controler.getConfig();
//
//		String modesString = "";
//		for (String mode: config.planCalcScore().getAllModes()) {
//			modesString = modesString + mode + ",";
//		}
//		// remove last ","
//		if (modesString.length() < 2) {
//			log.error("no valid mode found");
//			modesString = null;
//		} else {
//			modesString = modesString.substring(0, modesString.length() - 1);
//		}
//
//		String[] args = new String[] {
//				config.controler().getOutputDirectory(),
//				config.controler().getRunId(),
//				"null", // TODO: reference run, hard to automate
//				"null", // TODO: reference run, hard to automate
//				config.global().getCoordinateSystem(),
//				"https://svn.vsp.tu-berlin.de/repos/public-svn/matsim/scenarios/countries/de/berlin/projects/avoev/shp-files/shp-bezirke/bezirke_berlin.shp",
//				TransformationFactory.DHDN_GK4,
//				"SCHLUESSEL",
//				"home",
//				"1", // TODO: scaling factor, should be 10 for 10pct scenario and 100 for 1pct scenario
//				"null", // visualizationScriptInputDirectory
//				modesString
//		};
//
//		RunPersonTripAnalysis.main(args);
//	}
}