/**
 * Copyright (c) <2012>, Oleg Romanovskiy <shedarhome@gmail.com> aka Shedar
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.modstats.reporter.v1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

import org.modstats.IModstatsReporter;
import org.modstats.ModVersionData;
import org.modstats.ModstatInfo;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;


public class Reporter implements IModstatsReporter
{

    public Map<String, ModVersionData> registeredMods;
    private DataSender sender;
    public Config config; 
    
    /**
     * At least one auto check was completed successfully
     */
    private boolean checkedAuto;

    public Reporter()
    {
        checkedAuto = false;
        registeredMods = new  ConcurrentHashMap<String, ModVersionData>(2, 0.9f, 1);
        MinecraftForge.EVENT_BUS.register(this);
        config = new Config();
    }
    
    
    private void startCheck(boolean manual)
    {
        if(!config.allowUpdates)
            return;
        //only manual check is allowed on servers 
        if(!FMLCommonHandler.instance().getSide().isClient() && !manual)
            return;
        if(registeredMods.isEmpty())
            return;
        DataSender currentSender = sender;
        if(!manual && checkedAuto)
            return;
        if(currentSender!=null && (currentSender.manual == false || manual))
            return;
        currentSender = new DataSender(this, manual);
        currentSender.start();
        sender = currentSender;
        
    }
    
    @ForgeSubscribe
    public void worldLoad(WorldEvent.Load event)
    {
        startCheck(false);
    }

    
    @Override
    public void registerMod(Object mod)
    {
        if(!config.allowUpdates)
            return;
        if(mod == null)
        {
            FMLLog.warning("[Modstats] Can't register null mod.");
            return;
        }
        ModstatInfo info = mod.getClass().getAnnotation(ModstatInfo.class);
        if(info == null)
        {
            FMLLog.warning("[Modstats] ModstatsInfo annotation not found for given mod.");
            return;
        }
        
        if(info.prefix() == null || info.prefix().equals(""))
        {
            FMLLog.warning("[Modstats] Mod prefix can't be empty.");
            return;
        }
        Mod modData = mod.getClass().getAnnotation(Mod.class);
        ModVersionData data;
        if(modData == null)
        {
            if(info.name() == null || info.name().equals(""))
            {
                FMLLog.warning("[Modstats] Mod name can't be empty.");
                return;
            }
            if(info.version() == null || info.version().equals(""))
            {
                FMLLog.warning("[Modstats] Mod version can't be empty.");
                return;
            }
            data = new ModVersionData(info.prefix(), info.name(), info.version());
        }
        else
        {
            data = new ModVersionData(info.prefix(), modData.name(), modData.version());
        }
        registeredMods.put(info.prefix(), data);
    }

    @Override
    public void doManualCheck()
    {
        startCheck(true);
    }

}
