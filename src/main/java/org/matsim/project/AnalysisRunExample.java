package org.matsim.project;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.matsim.analysis.AgentAnalysisFilter;
import org.matsim.analysis.AgentFilter;
import org.matsim.analysis.MatsimAnalysis;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class AnalysisRunExample {
    private static final Logger log = Logger.getLogger(org.matsim.analysis.AnalysisRunExample.class);

    public AnalysisRunExample() {
    }

    public static void main(String[] args) throws IOException {
        String runDirectory = "./2021-04-08-500iter(10perc-0.5-0.05ea)";
        String runId = "Erding-Matsim";
        String modesString = "car,pt";
        String scenarioCRS = "EPSG:31468";
        Scenario scenario1 = loadScenario("./Erding/output/2021-04-08-500iter(10perc-0.5-0.05ea)", "Erding-Matsim", "EPSG:31468");
        List<AgentFilter> filters1 = new ArrayList();
        AgentAnalysisFilter filter1a = new AgentAnalysisFilter("A");
        filter1a.preProcess(scenario1);
        filters1.add(filter1a);
        List<String> modes = new ArrayList();
        String[] var9 = "car,pt".split(",");
        int var10 = var9.length;

        for(int var11 = 0; var11 < var10; ++var11) {
            String mode = var9[var11];
            modes.add(mode);
        }

        MatsimAnalysis analysis = new MatsimAnalysis();
        analysis.setScenario1(scenario1);
        analysis.setAgentFilters(filters1);
        analysis.setTripFilters(new ArrayList<>());
        analysis.setModes(modes);
        analysis.setScenarioCRS("EPSG:31468");
        analysis.run();
    }

    private static Scenario loadScenario(String runDirectory, String runId, String scenarioCRS) {
        log.info("Loading scenario...");
        if (runDirectory != null && !runDirectory.equals("") && !runDirectory.equals("null")) {
            if (!runDirectory.endsWith("/")) {
                runDirectory = runDirectory + "/";
            }

            String networkFile = runDirectory + runId + ".output_network.xml.gz";
            String populationFile = runDirectory + runId + ".output_plans.xml.gz";
            String facilitiesFile = runDirectory + runId + ".output_facilities.xml.gz";
            Config config = ConfigUtils.createConfig();
            config.global().setCoordinateSystem(scenarioCRS);
            config.controler().setRunId(runId);
            config.controler().setOutputDirectory(runDirectory);
            config.plans().setInputFile(populationFile);
            config.network().setInputFile(networkFile);
            config.facilities().setInputFile(facilitiesFile);
            return ScenarioUtils.loadScenario(config);
        } else {
            return null;
        }
    }
}
