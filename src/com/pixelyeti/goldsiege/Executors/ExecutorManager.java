package com.pixelyeti.goldsiege.Executors;

import com.pixelyeti.goldsiege.Main;

/**
 * Created by Callum on 09/01/2016.
 */
public class ExecutorManager {

    private static SetSpawn setSpawn;

    public static void registerExecutors() {
        setSpawn = new SetSpawn(Main.plugin);
        Main.plugin.getCommand("setspawn").setExecutor(setSpawn);

    }
}
