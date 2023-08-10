package agent.core;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AgentTransformer implements ClassFileTransformer{

    public static ThreadLocal<ClassLoader> hookingCtx = new ThreadLocal<ClassLoader>();
    
//    @Override
//    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
//            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//        if(className != null && className.startsWith("com/shopping/")) {
//            System.out.printf("%s Class Loading : [%s]\n", DEFAULT.LOG_KEY, className);
//        }
//        
//        try {
//            hookingCtx.set(loader);
//            
//            if(className == null)
//                return null;
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        return classfileBuffer;
//    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.equals("com/shopping/controller/TestClassLoad")) {
            System.out.println("Transforming class: " + className);

            // ASM 라이브러리를 사용하여 클래스 변환 작업 수행
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM7, classWriter) {
                @Override
                public MethodVisitor visitMethod(
                        int access,
                        String name,
                        String descriptor,
                        String signature,
                        String[] exceptions
                ) {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions);
                    if (name.equals("PullOut")) {
                        // "PullOut" 메서드의 코드를 수정하여 "Rabbit!" 출력
                        return new MethodVisitor(Opcodes.ASM7, methodVisitor) {
                            @Override
                            public void visitCode() {
                                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                                super.visitCode();
                                visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                                visitLdcInsn("Rabbit!");
                                visitMethodInsn(
                                        Opcodes.INVOKEVIRTUAL,
                                        "java/io/PrintStream",
                                        "println",
                                        "(Ljava/lang/String;)V",
                                        false
                                );
                            }
                        };
                    }
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    return methodVisitor;
                }
            };
            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

            // 수정된 바이트 코드 반환
            return classWriter.toByteArray();
        }
        return classfileBuffer;
    }
}
