package minechem.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MinechemTransformer implements IClassTransformer
{
    private enum Class
    {
        GUI_ACHIEVEMENTS("net.minecraft.client.gui.achievement.GuiAchievements", "bei", Method.GUI_DRAW);
        
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
            return name.replace(".","/");
        }
    }
    
    private enum Method
    {
        GUI_DRAW("func_146552_b", "(IIF)V", InstructionNode.INIT, InstructionNode.DISABLE);
        
        private final String name, args;
        private InstructionNode[] instructions;
        
        private Method(String name, String args, InstructionNode... instructions)
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
    
    public enum InstructionNode
    {
        INIT("bindTexture","glEnable"),
        DISABLE("glDisable", "canUnlockAchievement");
        
        public final String after, before;
        public InsnList insnList;
        
        private InstructionNode(String after,String next)
        {
            this.after = after;
            this.before = next;
        }
        
        static 
        {
            INIT.insnList = createRenderHook();
            DISABLE.insnList = createResetHook();
        }

        private static InsnList createRenderHook()
        {
            InsnList insnList = new InsnList();
            insnList.insert(new VarInsnNode(Opcodes.ALOAD, 35));
            insnList.insert(new VarInsnNode(Opcodes.FLOAD, 36));
            insnList.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, "minechem/asm/MinechemHooks", "recolourAchievement", "(Lnet/minecraft/stats/Achievement;F)V", false));
            return insnList;
        }

        private static InsnList createResetHook()
        {
            InsnList insnList = new InsnList();
            insnList.insert(new VarInsnNode(Opcodes.FLOAD, 36));
            insnList.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, "minechem/asm/MinechemHooks", "resetGreyscale", "(F)V", false));
            return insnList;
        }
        
        public InsnList getInsnList()
        {
            return insnList;
        }
    }

    private static Map<String, Class> classMap = new HashMap<String,Class>();

    static
    {
        for (Class className : Class.values())
            classMap.put(className.getName(), className);
    }
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        Class clazz = classMap.get(name);
        if (clazz != null)
        {
            for (Method method : clazz.methods)
                for (InstructionNode node : method.instructions)
                    bytes = injectBytes(method, node, bytes);
            classMap.remove(name);
        }
        return bytes;
    }
    
    public byte[] injectBytes(Method method, InstructionNode instructionNode, byte[] data)
    {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(data);
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

        MethodNode methodNode = getMethodByName(classNode, method);
        AbstractInsnNode pos = findInstructionNode(instructionNode, methodNode).getNext();
        methodNode.instructions.insertBefore(pos, instructionNode.getInsnList());
        
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
                    if (((MethodInsnNode)node).name.equals(instructionNode.before)) return result;
                    else close = false;
                }
                
                if (((MethodInsnNode)node).name.equals(instructionNode.after))
                {
                    close = true;
                    result = node;
                }
            }
        }
        return methodNode.instructions.getLast();
    }

    public static MethodNode getMethodByName(ClassNode classNode, Method method) {
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
