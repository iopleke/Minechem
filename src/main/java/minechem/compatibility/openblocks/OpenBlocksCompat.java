package minechem.compatibility.openblocks;

import minechem.Compendium;
import minechem.compatibility.CompatBase;
import minechem.helper.LogHelper;

import java.lang.reflect.Method;

public class OpenBlocksCompat extends CompatBase
{
    @Override
    protected void init()
    {
        setDonationURL(Compendium.Naming.id, Compendium.MetaData.patreon);
    }

    public static void setDonationURL(String modID, String donationUrl)
    {
        try
        {
            Class donationManager = Class.forName("openblocks.common.DonationURLManager");
            Method instance = donationManager.getMethod("instance");
            Method url = donationManager.getMethod("addUrl");
            url.invoke(instance.invoke(null), modID, donationUrl);
            LogHelper.info("Donation Station Integration Completion");
        } catch (ClassNotFoundException e)
        {
            LogHelper.info("Donation Station Perambulation, Location Calibration Frustration");
        } catch (Exception e)
        {
            LogHelper.warn("Donation Station Integration Frustration");
        }
    }
}
