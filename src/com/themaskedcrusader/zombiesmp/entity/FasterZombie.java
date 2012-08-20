package com.themaskedcrusader.zombiesmp.entity;

import net.minecraft.server.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FasterZombie extends net.minecraft.server.EntityZombie {

    public FasterZombie(World world) {
        super(world);
        this.bw = 0.23F;

        try {
            //enable PathfinderGoalSelector's "a" field to be editable
            Field gsa = net.minecraft.server.PathfinderGoalSelector.class.getDeclaredField("a");
            gsa.setAccessible(true);

            // ok now take this instances goals and targets and blank them
            gsa.set(this.goalSelector, new ArrayList());
            gsa.set(this.targetSelector, new ArrayList());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalBreakDoor(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 0.4F, false));
        this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, this.bw, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, this.bw));
        this.goalSelector.a(5, new PathfinderGoalMoveThroughVillage(this, this.bw, false));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, this.bw));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
    }

}
