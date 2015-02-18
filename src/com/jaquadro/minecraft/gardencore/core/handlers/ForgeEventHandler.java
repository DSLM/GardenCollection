package com.jaquadro.minecraft.gardencore.core.handlers;

import com.jaquadro.minecraft.gardencore.api.IPlantProxy;
import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.BlockGardenProxy;
import com.jaquadro.minecraft.gardencore.block.BlockGardenSoil;
import com.jaquadro.minecraft.gardencore.block.BlockSmallFire;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ForgeEventHandler
{
    @SubscribeEvent
    public void applyBonemeal (BonemealEvent event) {
        if (event.block instanceof IPlantProxy) {
            IPlantProxy proxy = (IPlantProxy) event.block;
            if (proxy.applyBonemeal(event.world, event.x, event.y, event.z))
                event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public void playerInteracts (PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            if (event.entityPlayer.capabilities.isCreativeMode) {
                if (BlockSmallFire.extinguishSmallFire(event.world, null, event.x, event.y, event.z, event.face)) {
                    event.setResult(Event.Result.ALLOW);
                    return;
                }
            }
            else {
                Block block = event.world.getBlock(event.x, event.y, event.z);
                if (!block.isAir(event.world, event.x, event.y, event.z)) {
                    if (BlockSmallFire.extinguishSmallFire(event.world, null, event.x, event.y, event.z, event.face)) {
                        event.setResult(Event.Result.ALLOW);
                        return;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void useHoe (UseHoeEvent event) {
        Block block = event.world.getBlock(event.x, event.y, event.z);
        if (block instanceof BlockGarden) {
            if (((BlockGarden) block).applyHoe(event.world, event.x, event.y, event.z))
                event.setResult(Event.Result.ALLOW);
        }
    }
}