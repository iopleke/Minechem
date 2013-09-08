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

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class Config
{
    private static final String CONFIG_NAME = "modstats.cfg"; 
    
    public boolean allowUpdates;
    public boolean betaNotifications;
    public boolean forCurrentMinecraftVersion;
    public boolean logOnly;
    
    public Config()
    {
        File configLocation = new File(Loader.instance().getConfigDir(), CONFIG_NAME);
        Configuration configuration = new Configuration(configLocation);
        configuration.load();
        
        Property prop = configuration.get("updates", "AllowUpdates", true);
        prop.comment = "Allow to send current mod versions to the server and check for updates.\nIt allows to mod authors to see mod's popularity. Please don't disable it without necessity";
        allowUpdates = prop.getBoolean(true);
        
        prop = configuration.get("updates", "LogOnly", false);
        prop.comment = "Don't display chat message, just add message to the log.";
        logOnly = prop.getBoolean(false);
        
        prop = configuration.get("updates", "BetaNotifications", false);
        prop.comment = "Set true to receive notifications about beta versions. Otherwise you will only receive information about stable versions";
        betaNotifications = prop.getBoolean(false);
        
        prop = configuration.get("updates", "ForCurrentMinecraftVersion", false);
        prop.comment = "Check for updates only for current MC version.\nEx:if you have MC 1.4.2 and ForCurrentMinecraftVersion is true, then you wouldn't receive notifications about versions for MC 1.4.5";
        forCurrentMinecraftVersion = prop.getBoolean(false);
        
        configuration.save();
        
        FMLLog.info("[Modstats] Config loaded. allowUpdates: %b,  betaNotification: %b, strict: %b", allowUpdates, betaNotifications, forCurrentMinecraftVersion);
    }

}
