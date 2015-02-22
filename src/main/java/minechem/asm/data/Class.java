package minechem.asm.data;

import minechem.asm.LoadingPlugin;
import minechem.asm.MinechemTransformer;

import java.util.Map;
import java.util.TreeMap;

public enum Class
{
    GUI_ACHIEVEMENTS("net.minecraft.client.gui.achievement.GuiAchievements", "bei"),
    RENDER_ITEM("net.minecraft.client.renderer.entity.RenderItem", "bny"),
    MATH_HELPER("net.minecraft.util.MathHelper", "qh"),
    MINECHEM_HOOKS("minechem.asm.MinechemHooks", "minechem.asm.MinechemHooks");

    private final String name, obfName;
    private Method[] methods;
    private Map<String, Field> fields;

    private Class(String name, String obfName)
    {
        this.name = name;
        this.obfName = obfName;
        this.fields = new TreeMap<String, Field>();
    }
    
    static 
    {
        GUI_ACHIEVEMENTS.addField(new Field("field_146570_r", "F"));
        GUI_ACHIEVEMENTS.addField(new Field("currentPage", "I"));
        GUI_ACHIEVEMENTS.addField(new Field("mc", "field_146297_k", "Lnet/minecraft/client/Minecraft;", "Lbao;"));
        RENDER_ITEM.addField(new Field("zLevel", "field_77023_b", "F"));
        
        GUI_ACHIEVEMENTS.setMethods(Method.GUI_DRAW, Method.DRAW_SCREEN, Method.ACTION_PREFORMED);
        RENDER_ITEM.setMethods(Method.RENDER_ITEM_AND_EFFECT_INTO_GUI);
    }
    
    private void setMethods(Method... methods)
    {
        this.methods = methods;
    }
    
    private void addField(Field field)
    {
        fields.put(field.getUnObfName(), field);
    }

    public String getName()
        {
            return LoadingPlugin.runtimeDeobfEnabled ? this.obfName : this.name;
        }
    
    public Method[] getMethods()
    {
        return methods == null ? new Method[0] : methods;
    }

    public String getASMName()
    {
        return name.replace(".", "/");
    }
    
    public Field getField(String name)
    {
        return fields.get(name);
    }

}
