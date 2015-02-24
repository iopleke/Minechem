package minechem.asm.data;

import minechem.asm.LoadingPlugin;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public enum InstructionNode implements IInsnList
{
    RECOLOUR("bindTexture", "func_110577_a ", "glEnable", "glEnable", false),
    RESET("glDisable", "glDisable", "canUnlockAchievement", "func_77442_b",false),
    ICON("getTextureManager", "func_110434_K", "renderItemAndEffectIntoGUI", "func_82406_b", true),
    SET_SCALE("", "", "", "", false);

    private final String after, before;
    private final String obfAfter, obfBefore;
    public final boolean replace;
    public InsnList insnList;

    private InstructionNode(String after, String obfAfter,  String next, String obfNext, boolean replace)
    {
        this.after = after;
        this.obfAfter = obfAfter;
        this.before = next;
        this.obfBefore = obfNext;
        this.replace = replace;
    }

    public String getAfter()
    {
        return LoadingPlugin.runtimeDeobfEnabled ? obfAfter : after;
    }

    public String getBefore()
    {
        return LoadingPlugin.runtimeDeobfEnabled ? obfBefore : before;
    }

    static
    {
        RECOLOUR.insnList = createRenderHook();
        RESET.insnList = createResetHook();
        ICON.insnList = createIconHook();
        SET_SCALE.insnList = createSetScaleHook();
    }

    private static InsnList createRenderHook()
    {
        InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 33));
        insnList.add(new VarInsnNode(Opcodes.FLOAD, 36));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.RECOLOUR.getClazz().getASMName(), Hook.RECOLOUR.getName(), Hook.RECOLOUR.getParams(), false));
        return insnList;
    }

    private static InsnList createResetHook()
    {
        InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.FLOAD, 36));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.RESET.getClazz().getASMName(), Hook.RESET.getName(), Hook.RESET.getParams(), false));
        return insnList;
    }

    private static InsnList createIconHook()
    {
        InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 33));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.ICON.getClazz().getASMName(), Hook.ICON.getName(), Hook.ICON.getParams(), false));
        return insnList;
    }

    private static InsnList createSetScaleHook()
    {
        InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), Class.GUI_ACHIEVEMENTS.getField("currentPage").getName(), Class.GUI_ACHIEVEMENTS.getField("currentPage").getDesc()));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.SET_SCALE.getClazz().getASMName(), Hook.SET_SCALE.getName(), Hook.SET_SCALE.getParams(), false));
        insnList.add(new FieldInsnNode(Opcodes.PUTFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), Class.GUI_ACHIEVEMENTS.getField("field_146570_r").getName(), Class.GUI_ACHIEVEMENTS.getField("field_146570_r").getDesc()));
        return insnList;
    }

    public InsnList getInsnList()
    {
        return insnList;
    }
}
