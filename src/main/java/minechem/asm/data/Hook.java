package minechem.asm.data;

public enum Hook
{
    RECOLOUR(Class.MINECHEM_HOOKS, "recolourAchievement", "(Lnet/minecraft/stats/Achievement;F)V"),
    RESET(Class.MINECHEM_HOOKS, "resetGreyscale", "(F)V"),
    ICON(Class.MINECHEM_HOOKS, "drawIconAchievement", "(Lnet/minecraft/client/renderer/entity/RenderItem;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IILnet/minecraft/stats/Achievement;)V"),
    BACKGROUND(Class.MINECHEM_HOOKS, "drawAchievementPageBackground", "(Lnet/minecraft/client/Minecraft;FIII)V"),
    SET_SCALE(Class.MINECHEM_HOOKS, "setScaleOnLoad", "(I)F"),
    GET_MAX_ZOOM_OUT(Class.MINECHEM_HOOKS, "getMaxZoomOut", "(I)F"),
    GET_MAX_ZOOM_IN(Class.MINECHEM_HOOKS, "getMaxZoomIn", "(I)F"),
    RENDER_OVERLAY(Class.MINECHEM_HOOKS, "renderOverlay", "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IIF)V");

    private final Class clazz;
    private final String name, params;

    private Hook(Class clazz, String name, String params)
    {
        this.clazz = clazz;
        this.name = name;
        this.params = params;
    }

    public String getName()
    {
        return name;
    }

    public String getParams()
    {
        return params;
    }

    public Class getClazz()
    {
        return clazz;
    }
}
