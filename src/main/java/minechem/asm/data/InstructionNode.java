package minechem.asm.data;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public enum InstructionNode implements IInsnList
{
    RECOLOUR("bindTexture", "glEnable", false),
    RESET("glDisable", "canUnlockAchievement", false),
    ICON("getTextureManager", "renderItemAndEffectIntoGUI", true),
    SET_SCALE("", "", false);

    public final String after, before;
    public final boolean replace;
    public InsnList insnList;

    private InstructionNode(String after, String next, boolean replace)
    {
        this.after = after;
        this.before = next;
        this.replace = replace;
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
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), "currentPage", "I"));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.SET_SCALE.getClazz().getASMName(), Hook.SET_SCALE.getName(), Hook.SET_SCALE.getParams(), false));
        insnList.add(new FieldInsnNode(Opcodes.PUTFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), "field_146570_r", "F"));
        return insnList;
    }

    public InsnList getInsnList()
    {
        return insnList;
    }
}
