package minechem.asm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import minechem.asm.data.Class;
import minechem.asm.data.CodeBlock;
import minechem.asm.data.IInsnList;
import minechem.asm.data.InstructionNode;
import minechem.asm.data.Method;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MinechemTransformer implements IClassTransformer
{
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
            for (Method method : clazz.getMethods())
            {
                for (IInsnList node : method.getiInsnLists())
                {
                    if (node instanceof InstructionNode)
                    {
                        bytes = injectBytes(method, (InstructionNode) node, bytes);
                    } else if (node instanceof CodeBlock)
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
        int start = codeBlock.getLinesAfterStart();
        int end = codeBlock.getLinesAfterEnd() + 1;

        for (Iterator<AbstractInsnNode> itr = methodNode.instructions.iterator(); itr.hasNext();)
        {
            AbstractInsnNode node = itr.next();
            if (node instanceof LineNumberNode)
            {
                LineNumberNode lineNode = (LineNumberNode) node;
                if (lineNode.line >= codeBlock.getStartLine())
                {
                    delete = true;
                }
                if (lineNode.line >= codeBlock.getEndLine())
                {
                    done = true;
                }
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
                {
                    methodNode.instructions.remove(node);
                }
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
                    if (((MethodInsnNode) node).name.equals(instructionNode.getBefore()))
                    {
                        return instructionNode.replace ? node : result;
                    } else
                    {
                        close = false;
                    }
                }

                if (((MethodInsnNode) node).name.equals(instructionNode.getAfter()))
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
            if (methodNode.name.equals(method.getName()) && methodNode.desc.equals(method.getArgs()))
            {
                return methodNode;
            }
        }
        return null;
    }
}
