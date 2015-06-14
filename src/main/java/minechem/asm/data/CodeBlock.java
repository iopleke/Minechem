package minechem.asm.data;

import minechem.asm.LoadingPlugin;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public enum CodeBlock implements IInsnList
{
    BACKGROUND(306, 0, 373, 1),
    CLAMP_ZOOM(183, 1, 184, 0),
    RENDER_OVERLAY(625, 1, 625, 0);

    private InsnList insnList;
    private int startLine, endLine;
    private int linesAfterStart, linesAfterEnd;

    private CodeBlock(int startLine, int linesAfterStart, int endLine, int linesAfterEnd)
    {
        this.startLine = startLine;
        this.endLine = endLine;
        this.linesAfterStart = linesAfterStart;
        this.linesAfterEnd = linesAfterEnd;
    }

    public int getStartLine()
    {
        return startLine;
    }

    public int getLinesAfterStart()
    {
        return linesAfterStart;
    }

    public int getEndLine()
    {
        return endLine;
    }

    public int getLinesAfterEnd()
    {
        return linesAfterEnd;
    }

    static
    {
        BACKGROUND.insnList = createBackgroundHook();
        CLAMP_ZOOM.insnList = createClampHook();
        RENDER_OVERLAY.insnList = createRenderOverlayHook();
    }

    private static InsnList createBackgroundHook()
    {
        InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), Class.GUI_ACHIEVEMENTS.getField("mc").getName(), Class.GUI_ACHIEVEMENTS.getField("mc").getDesc()));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), Class.GUI_ACHIEVEMENTS.getField("field_146570_r").getName(), Class.GUI_ACHIEVEMENTS.getField("field_146570_r").getDesc()));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 4));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 5));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), Class.GUI_ACHIEVEMENTS.getField("currentPage").getName(), Class.GUI_ACHIEVEMENTS.getField("currentPage").getDesc()));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.BACKGROUND.getClazz().getASMName(), Hook.BACKGROUND.getName(), Hook.BACKGROUND.getParams(), false));
        return insnList;
    }

    private static InsnList createClampHook()
    {
        InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), Class.GUI_ACHIEVEMENTS.getField("field_146570_r").getName(), Class.GUI_ACHIEVEMENTS.getField("field_146570_r").getDesc()));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), Class.GUI_ACHIEVEMENTS.getField("currentPage").getName(), Class.GUI_ACHIEVEMENTS.getField("currentPage").getDesc()));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.GET_MAX_ZOOM_IN.getClazz().getASMName(), Hook.GET_MAX_ZOOM_IN.getName(), Hook.GET_MAX_ZOOM_IN.getParams(), false));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), Class.GUI_ACHIEVEMENTS.getField("currentPage").getName(), Class.GUI_ACHIEVEMENTS.getField("currentPage").getDesc()));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.GET_MAX_ZOOM_OUT.getClazz().getASMName(), Hook.GET_MAX_ZOOM_OUT.getName(), Hook.GET_MAX_ZOOM_OUT.getParams(), false));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Class.MATH_HELPER.getASMName(), LoadingPlugin.runtimeDeobfEnabled ? "func_76131_a" : "clamp_float", "(FFF)F", false));
        insnList.add(new FieldInsnNode(Opcodes.PUTFIELD, Class.GUI_ACHIEVEMENTS.getASMName(), Class.GUI_ACHIEVEMENTS.getField("field_146570_r").getName(), Class.GUI_ACHIEVEMENTS.getField("field_146570_r").getDesc()));
        return insnList;
    }

    private static InsnList createRenderOverlayHook()
    {
        InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 2));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 3));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 4));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 5));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, Class.RENDER_ITEM.getASMName(), Class.RENDER_ITEM.getField("zLevel").getName(), Class.RENDER_ITEM.getField("zLevel").getDesc()));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.RENDER_OVERLAY.getClazz().getASMName(), Hook.RENDER_OVERLAY.getName(), Hook.RENDER_OVERLAY.getParams(), false));
        return insnList;
    }

    @Override
    public InsnList getInsnList()
    {
        return insnList;
    }
}
