package minechem.asm.data;

import minechem.asm.LoadingPlugin;

public class Field
{
    private final String name, obfName, desc, obfDesc;

    public Field(String name, String obfName, String desc, String obfDesc)
    {
        this.name = name;
        this.obfName = obfName;
        this.desc = desc;
        this.obfDesc = obfDesc;
    }
    
    public Field(String name, String obfName, String desc)
    {
        this.name = name;
        this.obfName = obfName;
        this.desc = desc;
        this.obfDesc = desc;
    }
    
    public Field(String name, String desc)
    {
        this.name = name;
        this.obfName = name;
        this.desc = desc;
        this.obfDesc = desc;
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
        return LoadingPlugin.runtimeDeobfEnabled ? obfDesc : desc;
    }
}
