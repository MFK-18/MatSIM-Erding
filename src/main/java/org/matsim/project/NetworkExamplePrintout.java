package org.matsim.project;

import org.matsim.core.network.io.NetworkWriter;

public class NetworkExamplePrintout {

    public static void main(String[] args) {
        NetworkExample example = new NetworkExample();
        example.init();

//        new NetworkWriter(example.network).write("BeispielNetzwerkmitLinks(1).xml");
    }
}
