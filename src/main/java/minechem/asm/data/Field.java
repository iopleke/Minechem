package minechem.asm.data;

import minechem.asm.LoadingPlugin;

public class Field
{
    private final String name, obfName, desc;
    
    public Field(String name, String obfName, String desc)
    {
        this.name = name;
        this.obfName = obfName;
        this.desc = desc;
    }
    
    public Field(String name, String desc)
    {
        this.name = name;
        this.obfName = name;
        this.desc = desc;
    }

    public String getName()
    {
        return LoadingPlugin.runtimeDeobfEnabled ? obfName : name;
    }
    
    protected String getUnObfName()
    {
        return name;
    }

    public String getDesc()
    {
        return desc;
    }
}
