package agent.core;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.executor.Executor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AgentTransformerBack implements ClassFileTransformer {
    
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        
        if (className == null) {
            return classfileBuffer;
        }

        // Target Executor implementations
        String[] targetClasses = {
            "org/apache/ibatis/executor/BaseExecutor",
            "org/apache/ibatis/executor/SimpleExecutor",
            "org/apache/ibatis/executor/ReuseExecutor",
            "org/apache/ibatis/executor/BatchExecutor"
        };

        for (String targetClass : targetClasses) {
            if (targetClass.equals(className)) {
                return addPrintlnToMethods(classfileBuffer, className.replace('/', '.'));
            }
        }

        return classfileBuffer;
    }

    private byte[] addPrintlnToMethods(byte[] originalClass, String className) {
        ClassReader cr = new ClassReader(originalClass);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

                
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        mv.visitLdcInsn(className + "." + name + "()");
                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                    }
                };
            }
        };

        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}
