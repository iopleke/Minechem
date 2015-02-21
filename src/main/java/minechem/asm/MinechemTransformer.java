package minechem.asm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public class MinechemTransformer implements IClassTransformer
{
    private static enum Class
    {
        GUI_ACHIEVEMENTS("net.minecraft.client.gui.achievement.GuiAchievements", "bei", Method.GUI_DRAW, Method.DRAW_SCREEN, Method.ACTION_PREFORMED),
        RENDER_ITEM("net.minecraft.client.renderer.entity.RenderItem", "bny" ,Method.RENDER_ITEM_AND_EFFECT_INTO_GUI);

        private final String name, obfName;
        private Method[] methods;

        private Class(String name, String obfName, Method... methods)
        {
            this.name = name;
            this.obfName = obfName;
            this.methods = methods;
        }

        public String getName()
        {
            return LoadingPlugin.runtimeDeobfEnabled ? this.obfName : this.name;
        }

        public String getASMName()
        {
            return name.replace(".", "/");
        }
    }

    private enum Hook
    {
        RECOLOUR("minechem.asm.MinechemHooks", "recolourAchievement", "(Lnet/minecraft/stats/Achievement;F)V"),
        RESET("minechem.asm.MinechemHooks", "resetGreyscale", "(F)V"),
        ICON("minechem.asm.MinechemHooks", "drawIconAchievement", "(Lnet/minecraft/client/renderer/entity/RenderItem;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IILnet/minecraft/stats/Achievement;)V"),
        BACKGROUND("minechem.asm.MinechemHooks", "drawAchievementPageBackground","(Lnet/minecraft/client/Minecraft;FIII)V"),
        SET_SCALE("minechem.asm.MinechemHooks", "setScaleOnLoad", "(I)F"),
        GET_MAX_ZOOM_OUT("minechem.asm.MinechemHooks", "getMaxZoomOut", "(I)F"),
        GET_MAX_ZOOM_IN("minechem.asm.MinechemHooks", "getMaxZoomIn", "(I)F"),
        RENDER_OVERLAY("minechem.asm.MinechemHooks", "renderOverlay", "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IIF)V");

        private final String className, name, params;
        private Hook(String className, String name, String params)
        {
            this.className = className;
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

        public String getASMName()
        {
            return className.replace(".", "/");
        }
    }

    private enum Method
    {
        GUI_DRAW("func_146552_b", "(IIF)V", CodeBlock.BACKGROUND, InstructionNode.RECOLOUR, InstructionNode.RESET, InstructionNode.ICON),
        ACTION_PREFORMED("actionPerformed", "(Lnet/minecraft/client/gui/GuiButton;)V", InstructionNode.SET_SCALE),
        DRAW_SCREEN("drawScreen", "(IIF)V", CodeBlock.CLAMP_ZOOM), 
        RENDER_ITEM_AND_EFFECT_INTO_GUI("renderItemAndEffectIntoGUI", "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;II)V", CodeBlock.RENDER_OVERLAY);

        private final String name, args;
        private IInsnList[] instructions;

        private Method(String name, String args, IInsnList... instructions)
        {
            this.name = name;
            this.args = args;
            this.instructions = instructions;
        }

        public String getName()
        {
            return this.name;
        }
    }
    
    private interface IInsnList
    {
        public InsnList getInsnList();
    }

    private enum InstructionNode implements IInsnList
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
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.RECOLOUR.getASMName(), Hook.RECOLOUR.getName(), Hook.RECOLOUR.getParams(), false));
            return insnList;
        }

        private static InsnList createResetHook()
        {
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(Opcodes.FLOAD, 36));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.RESET.getASMName(), Hook.RESET.getName(), Hook.RESET.getParams(), false));
            return insnList;
        }

        private static InsnList createIconHook()
        {
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 33));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.ICON.getASMName(), Hook.ICON.getName(), Hook.ICON.getParams(), false));
            return insnList;
        }
        
        private static InsnList createSetScaleHook()
        {
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/gui/achievement/GuiAchievements", "currentPage", "I"));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.SET_SCALE.getASMName(), Hook.SET_SCALE.getName(), Hook.SET_SCALE.getParams(), false));
            insnList.add(new FieldInsnNode(Opcodes.PUTFIELD, "net/minecraft/client/gui/achievement/GuiAchievements", "field_146570_r", "F"));
            return insnList;
        }

        public InsnList getInsnList()
        {
            return insnList;
        }
    }
    
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
            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/gui/achievement/GuiAchievements", "mc", "Lnet/minecraft/client/Minecraft;"));
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/gui/achievement/GuiAchievements", "field_146570_r", "F"));
            insnList.add(new VarInsnNode(Opcodes.ILOAD, 4));
            insnList.add(new VarInsnNode(Opcodes.ILOAD, 5));
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/gui/achievement/GuiAchievements", "currentPage", "I"));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.BACKGROUND.getASMName(), Hook.BACKGROUND.getName(), Hook.BACKGROUND.getParams(), false));
            return insnList;
        }
        
        private static InsnList createClampHook()
        {
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/gui/achievement/GuiAchievements", "field_146570_r", "F"));
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/gui/achievement/GuiAchievements", "currentPage", "I"));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.GET_MAX_ZOOM_IN.getASMName(), Hook.GET_MAX_ZOOM_IN.getName(), Hook.GET_MAX_ZOOM_IN.getParams(), false));
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/gui/achievement/GuiAchievements", "currentPage", "I"));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.GET_MAX_ZOOM_OUT.getASMName(), Hook.GET_MAX_ZOOM_OUT.getName(), Hook.GET_MAX_ZOOM_OUT.getParams(), false));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/util/MathHelper", "clamp_float", "(FFF)F", false));
            insnList.add(new FieldInsnNode(Opcodes.PUTFIELD, "net/minecraft/client/gui/achievement/GuiAchievements", "field_146570_r", "F"));
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
            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/entity/RenderItem", "zLevel", "F"));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.RENDER_OVERLAY.getASMName(), Hook.RENDER_OVERLAY.getName(), Hook.RENDER_OVERLAY.getParams(), false));
            return insnList;
        }

        @Override
        public InsnList getInsnList()
        {
            return insnList;
        }
    }

    private static Map<String, Class> classMap = new HashMap<String, Class>();

    static
    {
        for (Class className : Class.values())
        {
            classMap.put(className.getName(), className);
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        Class clazz = classMap.get(name);
        if (clazz != null)
        {
            for (Method method : clazz.methods)
            {
                for (IInsnList node : method.instructions)
                {
                    if (node instanceof InstructionNode)
                    {
                        bytes = injectBytes(method, (InstructionNode) node, bytes);
                    }
                    else if (node instanceof CodeBlock)
                    {
                        bytes = replaceBytes(method, (CodeBlock) node, bytes);
                    }
                }
            }
            classMap.remove(name);
        }
        return bytes;
    }
    
    private byte[] replaceBytes(Method method, CodeBlock codeBlock, byte[] data)
    {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(data);
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

        MethodNode methodNode = getMethodByName(classNode, method);
        AbstractInsnNode pos = null;

        boolean delete = false;
        boolean done = false;
        int start = codeBlock.linesAfterStart;
        int end = codeBlock.linesAfterEnd+1;

        for (Iterator<AbstractInsnNode> itr = methodNode.instructions.iterator(); itr.hasNext();)
        {
            AbstractInsnNode node = itr.next();
            if (node instanceof LineNumberNode)
            {
                LineNumberNode lineNode = (LineNumberNode) node;
                if (lineNode.line >= codeBlock.startLine)
                    delete = true;
                if (lineNode.line >= codeBlock.endLine)
                    done = true;
            }
            
            if (delete)
            {
                if (done)
                {
                    if (end-- > 0)
                    {
                        methodNode.instructions.remove(node);
                        continue;
                    }
                    pos = node;
                    break;
                }
                if (--start < 0)
                    methodNode.instructions.remove(node);
            }
        }

        methodNode.instructions.insertBefore(pos, codeBlock.getInsnList());

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    private byte[] injectBytes(Method method, InstructionNode instructionNode, byte[] data)
    {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(data);
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

        MethodNode methodNode = getMethodByName(classNode, method);
        AbstractInsnNode pos = findInstructionNode(instructionNode, methodNode);
        if (instructionNode.replace)
        {
            methodNode.instructions.insertBefore(pos, instructionNode.getInsnList());
            methodNode.instructions.remove(pos);
        } else
        {
            methodNode.instructions.insertBefore(pos, instructionNode.getInsnList());
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    private static AbstractInsnNode findInstructionNode(InstructionNode instructionNode, MethodNode methodNode)
    {
        boolean close = false;
        AbstractInsnNode result = null;
        for (Iterator<AbstractInsnNode> itr = methodNode.instructions.iterator(); itr.hasNext();)
        {
            AbstractInsnNode node = itr.next();
            if (node instanceof MethodInsnNode)
            {
                if (close)
                {
                    if (((MethodInsnNode) node).name.equals(instructionNode.before))
                    {
                        return instructionNode.replace ? node : result;
                    } else
                    {
                        close = false;
                    }
                }

                if (((MethodInsnNode) node).name.equals(instructionNode.after))
                {
                    close = true;
                    result = node;
                }
            }
        }
        return result == null ? methodNode.instructions.getLast().getPrevious() : result;
    }

    private static MethodNode getMethodByName(ClassNode classNode, Method method)
    {
        List<MethodNode> methods = classNode.methods;
        for (MethodNode methodNode : methods)
        {
            if (methodNode.name.equals(method.getName()) && methodNode.desc.equals(method.args))
            {
                return methodNode;
            }
        }
        return null;
    }
}
