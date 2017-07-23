package com.pixelyeti.goldsiege.Executors;

import com.pixelyeti.goldsiege.Main;

/**
 * Created by Callum on 09/01/2016.
 */
public class ExecutorManager {

    private static SetSpawn setSpawn;

    public static void registerExecutors(Main m) {
        //setSpawn = new SetSpawn(Main.instance);
        //Main.instance.getCommand("setspawn").setExecutor(setSpawn);
        m.getCommand("setspawn").setExecutor(new SetSpawn());
        m.getCommand("spawn").setExecutor(new Spawn(m));
        m.getCommand("tpworld").setExecutor(new TPWorld());
        m.getCommand("startgame").setExecutor(new StartGame());
        m.getCommand("addmapspawn").setExecutor(new AddMapSpawn());
        m.getCommand("listspawns").setExecutor(new ListAllMapSpawns());
        m.getCommand("endgame").setExecutor(new EndGame());
    }
}
