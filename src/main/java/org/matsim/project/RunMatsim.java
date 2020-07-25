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

import com.sun.org.apache.xml.internal.security.Init;
import com.sun.tools.javac.resources.ct;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

/**
 * @author nagel
 *
 */
public class RunMatsim{

	public static void main(String[] args) {
		NetworkExample example = new NetworkExample();
		example.init();

		Config config = ConfigUtils.createConfig();
		Scenario scenario = ScenarioUtils.createScenario(config);
		Network network = scenario.getNetwork();
		Population population = scenario.getPopulation();
		// possibly modify config here

		/*
		 * Pick the PopulationFactory out of the Population for convenience.
		 * It contains methods to create new Population items.
		 */
		PopulationFactory populationFactory = population.getFactory();

		/*
		 * Create a Person designated "1" and add it to the Population.
		 */
		Person person = populationFactory.createPerson(Id.create("1", Person.class));
		population.addPerson(person);

		Plan plan = populationFactory.createPlan();

		/*
		 * Create a "home" Activity for the Person. In order to have the Person end its day at the same location,
		 * we keep the home coordinates for later use (see below).
		 * Note that we use the CoordinateTransformation created above.
		 */

		Coord homeCoord = new Coord();
		Activity activity1 = populationFactory.createActivityFromCoord("home", (homeCoord));
		activity1.setEndTime(21600);
		plan.addActivity(activity1);


		/*
		 * Create a Leg. A Leg initially hasn't got many attributes. It just says that a car will be used.
		 */
		plan.addLeg(populationFactory.createLeg("car"));

		Activity activity2 = populationFactory.createActivityFromCoord("work", (new Coord(14.34024, 51.75649)));
		activity2.setEndTime(57600); // leave at 4 p.m.
		plan.addActivity(activity2);

		/*
		 * Create another car Leg.
		 */
		plan.addLeg(populationFactory.createLeg("car"));

		/*
		 * End the day with another Activity at home. Note that it gets the same coordinates as the first activity.
		 */
		Activity activity3 = populationFactory.createActivityFromCoord("home", (homeCoord));
		plan.addActivity(activity3);
		person.addPlan(plan);

		// possibly modify scenario here
		
		// ---
		
		Controler controler = new Controler( scenario ) ;
		
		// possibly modify controler here

//		controler.addOverridingModule( new OTFVisLiveModule() ) ;
		
		// ---
		
		controler.run();
	}
	
}
