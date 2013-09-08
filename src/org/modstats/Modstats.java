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

package org.modstats;

import cpw.mods.fml.common.FMLLog;

public class Modstats
{
    private static final Modstats INSTANCE = new Modstats();
    private static final String CLASS_TEMPLATE = "org.modstats.reporter.v%d.Reporter";
    private IModstatsReporter reporter;

    private Modstats()
    {
        reporter = locateReporter();
    }
    
    public IModstatsReporter getReporter()
    {
        return reporter;
    }
    
    private IModstatsReporter locateReporter()
    {
        int i=1;
        Class<?> latest = null;
        while(i<100)
        {
            try
            {
                Class<?> candidate = Class.forName(String.format(CLASS_TEMPLATE, i));
                if(IModstatsReporter.class.isAssignableFrom(candidate))
                {
                    latest = candidate;
                }
            }
            catch (Exception e) {
                break;
            }
            i++;
        }
        if(latest == null)
        {
            FMLLog.warning("Modstats reporter class not found.");
        }
        else
        {
            try
            {
                return (IModstatsReporter)latest.newInstance();
            } catch (Exception e)
            {
                FMLLog.warning("Modstats reporter class can't be instantiated.");
            } 
        }
        return null;
    }
    
    public static Modstats instance()
    {
        return INSTANCE;
    }
    
}
