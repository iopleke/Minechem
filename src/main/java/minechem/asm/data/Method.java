package minechem.asm.data;

public enum Method
{
    GUI_DRAW("func_146552_b", "", "(IIF)V", CodeBlock.BACKGROUND, InstructionNode.RECOLOUR, InstructionNode.RESET, InstructionNode.ICON),
    ACTION_PREFORMED("actionPerformed", "", "(Lnet/minecraft/client/gui/GuiButton;)V", InstructionNode.SET_SCALE),
    DRAW_SCREEN("drawScreen", "", "(IIF)V", CodeBlock.CLAMP_ZOOM),
    RENDER_ITEM_AND_EFFECT_INTO_GUI("renderItemAndEffectIntoGUI", "", "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;II)V", CodeBlock.RENDER_OVERLAY);

    private final String name, args;
    private IInsnList[] iInsnLists;

    private Method(String name, String obfName, String args, IInsnList... iInsnLists)
    {
        this.name = name;
        this.args = args;
        this.iInsnLists = iInsnLists;
    }

    public String getName()
    {
        return this.name;
    }

    public String getArgs()
    {
        return args;
    }

    public IInsnList[] getiInsnLists()
    {
        return iInsnLists;
    }
}
