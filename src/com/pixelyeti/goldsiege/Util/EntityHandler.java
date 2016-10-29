package com.pixelyeti.goldsiege.Util;

import com.pixelyeti.goldsiege.Entity.ShopMob;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Callum on 09/01/2016.
 */
public class EntityHandler {

    public static void spawnShopMob(Location loc, int i, UUID id) {
        World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        ShopMob shopMob = new ShopMob(nmsWorld, i);
        shopMob.setPosition(loc.getX(), loc.getY(), loc.getZ());
        nmsWorld.addEntity(shopMob);
    }

    protected static Field mapStringToClassField, mapClassToStringField, mapClassToIdField, mapStringToIdField;
    protected static Field mapIdToClassField;

    static
    {
        try
        {
            mapStringToClassField = EntityTypes.class.getDeclaredField("c");
            mapClassToStringField = EntityTypes.class.getDeclaredField("d");
            mapIdToClassField = EntityTypes.class.getDeclaredField("e");
            mapClassToIdField = EntityTypes.class.getDeclaredField("f");
            mapStringToIdField = EntityTypes.class.getDeclaredField("g");

            mapStringToClassField.setAccessible(true);
            mapClassToStringField.setAccessible(true);
            mapIdToClassField.setAccessible(true);
            mapClassToIdField.setAccessible(true);
            mapStringToIdField.setAccessible(true);
        }
        catch(Exception e) {e.printStackTrace();}
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void addCustomEntity(Class entityClass, String name, int id)
    {
        if (mapStringToClassField == null || mapStringToIdField == null || mapClassToStringField == null || mapClassToIdField == null)
        {
            return;
        }
        else
        {
            try
            {
                Map mapStringToClass = (Map) mapStringToClassField.get(null);
                Map mapStringToId = (Map) mapStringToIdField.get(null);
                Map mapClasstoString = (Map) mapClassToStringField.get(null);
                Map mapClassToId = (Map) mapClassToIdField.get(null);

                mapStringToClass.put(name, entityClass);
                mapStringToId.put(name, Integer.valueOf(id));
                mapClasstoString.put(entityClass, name);
                mapClassToId.put(entityClass, Integer.valueOf(id));

                mapStringToClassField.set(null, mapStringToClass);
                mapStringToIdField.set(null, mapStringToId);
                mapClassToStringField.set(null, mapClasstoString);
                mapClassToIdField.set(null, mapClassToId);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
