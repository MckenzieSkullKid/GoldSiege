package com.pixelyeti.goldsiege.Entity;

import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Created by Callum on 09/01/2016.
 */
public class ShopMob extends EntityVillager {

    public ShopMob(World world, int i) {
        super(world);

        this.setProfession(i);
        this.setCustomName(ChatColor.GOLD + "Shop");

        try {
            Field gsa = PathfinderGoalSelector.class.getDeclaredField("b");
            gsa.setAccessible(true);
            gsa.set(this.goalSelector, new UnsafeList());
            gsa.set(this.targetSelector, new UnsafeList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

    }

    @Override
    public void collide(Entity entity) {
    }

    @Override
    public String getName() {
        return "gameShopVillager";
    }

    @Override
    public String toString() {
        return getCustomName() + " " + getUniqueID();
    }

    public boolean a(EntityHuman entityhuman) {
        if (isAlive()) {
            if (!this.world.isClientSide) {
                a(entityhuman);
                if (getBukkitEntity().getCustomName() != null) {
                    String name = getBukkitEntity().getCustomName();
                    Player p = (Player) entityhuman.getBukkitEntity();
                    if (name.equalsIgnoreCase(ChatColor.GOLD + "shop")) {

                    }
                }
            }
            return true;
        }
        return super.a(entityhuman);
    }

    @Override
    public void dropDeathLoot(boolean flag, int i) {
        int j = this.random.nextInt(2 + i);

        int k;

        for (k = 0; k < j; ++k) {
            this.a(Items.ROTTEN_FLESH, 1);
        }
    }

    @Override
    public void g(double d0, double d1, double d2) {
        return;
    }
}
