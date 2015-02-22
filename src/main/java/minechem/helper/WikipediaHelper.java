package minechem.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikipediaHelper
{
    public static String suffix = "&action=query";
    public static String prefix = "http://en.wikipedia.org/w/api.php?format=json&prop=revisions&rvprop=content&continue=&titles=";
    public static Pattern redirect = Pattern.compile("#redirect\\s*\\[\\[([\\w|\\s]+)\\]\\]");
    public static Pattern missing = Pattern.compile("\"missing\":\"\"}}}}");
    public static Pattern title = Pattern.compile("\"title\":\"([\\w|\\s|\\(|\\)|\\d]+)\"");
    public static Pattern name = Pattern.compile("IUPACName\\s*=\\s*([\\w|\\s]+)");
    public static Pattern bPt = Pattern.compile("BoilingPtC\\s*=\\s*(\\d+)");
    public static Pattern mPt = Pattern.compile("MeltingPtC\\s*=\\s*(\\d+)");
    public static Pattern formula = Pattern.compile("Formula\\s*=\\s*([\\w|<|>|\\/]+)");
    public static Pattern density = Pattern.compile("Density\\s*=\\s*([\\d|\\.]+)\\s*([\\w|\\/]+)");
    public static Pattern chemSpider = Pattern.compile("ChemSpiderID\\s*=\\s*(\\d+)");
    public static Pattern molarMass = Pattern.compile("MolarMass\\s*=\\s*([\\d|\\.]+)");
    public static Pattern hazards = Pattern.compile("NFPA-(\\w)\\s*=\\s*(\\d|\\w+)");
    public static Map<String, String> hazardMap = new HashMap<String, String>();

    static
    {
        hazardMap.put("F","Flammability");
        hazardMap.put("H","Health");
        hazardMap.put("R","Reactivity");
        hazardMap.put("S","Special");
    }

    public static String getPage(String title)
    {
        try
        {
            title = title.replaceAll(" ", "%20");
            URL page = new URL(prefix + title + suffix);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(page.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                Matcher match = redirect.matcher(inputLine);
                if (match.find())
                {
                    return getPage(match.group(1));
                }
                else if (missing.matcher(inputLine).find()) return "";
                else return inputLine;
            }
            in.close();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }



    public static JsonObject getObject(String pageName, JsonObject result)
    {
        try{
            String page = getPage(pageName);
            if (page.isEmpty())
                return null;
            Matcher match;
            result.add("name", new JsonPrimitive((match = name.matcher(page)).find()?match.group(1):(match = title.matcher(page)).find()?match.group(1):pageName));
            if ((match = bPt.matcher(page)).find()) result.add("boilingPt", new JsonPrimitive(Double.valueOf(doubleSanitiser(match.group(1)))+273.3));
            if ((match = mPt.matcher(page)).find()) result.add("meltingPt", new JsonPrimitive(Double.valueOf(doubleSanitiser(match.group(1)))+273.3));
//            String form = (match = formula.matcher(page)).find() ? match.group(1) : "";
//            form = form.replaceAll("<.*>","");
//            if (!form.isEmpty()) result.add("formula", new JsonPrimitive(form));
            if ((match = density.matcher(page)).find()) result.add("density", new JsonPrimitive(Double.valueOf(doubleSanitiser(match.group(1)))));
            if ((match = molarMass.matcher(page)).find()) result.add("molarMass", new JsonPrimitive(Double.valueOf(doubleSanitiser(match.group(1)))));
            if ((match = chemSpider.matcher(page)).find()) result.add("chemSpiderID", new JsonPrimitive(Integer.valueOf(match.group(1))));
            match = hazards.matcher(page);
            while (match.find())
            {
                JsonPrimitive element;
                element = new JsonPrimitive(match.group(2));
                //else element = new JsonPrimitive(Integer.valueOf(match.group(2)));
                String key = match.group(1);
                key = hazardMap.containsKey(key) ? hazardMap.get(key) : key;
                result.add(key,element);
            }
            if (result.entrySet().isEmpty()) return null;
            return result;
        }catch(Exception e)
        {
            return null;
        }
    }

    private static Pattern singleDecimal = Pattern.compile("(\\d+\\.\\d+)");

    private static String doubleSanitiser(String val)
    {
        Matcher matcher = singleDecimal.matcher(val);
        if (matcher.find()) return matcher.group(1);
        return val;
    }
}
