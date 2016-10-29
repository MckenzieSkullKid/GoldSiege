package com.pixelyeti.goldsiege.Listeners;

import org.bukkit.WeatherType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by Callum on 08/01/2016.
 */
public class WeatherChange implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState()) {
        }
    }
}
