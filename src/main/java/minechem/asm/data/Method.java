package minechem.asm.data;

import minechem.asm.LoadingPlugin;

public enum Method
{
    GUI_DRAW("func_146552_b", "func_146552_b", "(IIF)V", CodeBlock.BACKGROUND, InstructionNode.RECOLOUR, InstructionNode.RESET, InstructionNode.ICON),
    ACTION_PREFORMED("actionPerformed", "func_146284_a ", "(Lnet/minecraft/client/gui/GuiButton;)V", "(Lbcb;)V", InstructionNode.SET_SCALE),
    DRAW_SCREEN("drawScreen", "func_73863_a", "(IIF)V", CodeBlock.CLAMP_ZOOM),
    RENDER_ITEM_AND_EFFECT_INTO_GUI("renderItemAndEffectIntoGUI", "func_82406_b", "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;II)V", "(Lbbu;Lbqf;Ladd;II)V", CodeBlock.RENDER_OVERLAY);

    private final String name, obfName, args, obfArgs;
    private IInsnList[] iInsnLists;

    private Method(String name, String obfName, String args, String obfArgs, IInsnList... iInsnLists)
    {
        this.name = name;
        this.obfName = obfName;
        this.args = args;
        this.obfArgs = obfArgs;
        this.iInsnLists = iInsnLists;
    }

    private Method(String name, String obfName, String args, IInsnList... iInsnLists)
    {
        this.name = name;
        this.obfName = obfName;
        this.args = args;
        this.obfArgs = args;
        this.iInsnLists = iInsnLists;
    }

    public String getName()
    {
        return LoadingPlugin.runtimeDeobfEnabled ? obfName : name;
    }

    public String getArgs()
    {
        return LoadingPlugin.runtimeDeobfEnabled ? obfArgs : args;
    }

    public IInsnList[] getiInsnLists()
    {
        return iInsnLists;
    }
}
