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

public class AgentTransformer implements ClassFileTransformer {
    
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        
        if (className == null) {
            return classfileBuffer;
        }

        // Target Executor implementations
        String[] targetClasses = {
            "org/apache/ibatis/executor/BaseExecutor",
//            "org/apache/ibatis/executor/SimpleExecutor",
//            "org/apache/ibatis/executor/ReuseExecutor",
//            "org/apache/ibatis/executor/BatchExecutor"
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

                // 파라미터의 개수를 계산
                int paramCount = getParameterCount(descriptor);
                
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        mv.visitLdcInsn(className + "." + name + "(" + paramCount + " params)"); // 파라미터 개수를 추가
                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                        
                        if("query".equals(name) && paramCount == 6) {
                            //System.out.printf("%s Query : [%s]\n", "[Agent]", sqlQuery);
                            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            mv.visitLdcInsn("%s Param : [%s]\n");
                            mv.visitInsn(Opcodes.ICONST_2);  // Load integer 2 onto the stack (argument count for the array)
                            mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object"); // Create a new array of Objects with a length of 2

                            // Add "[Agent]" string to the first position of the array
                            mv.visitInsn(Opcodes.DUP); // Duplicate the array reference
                            mv.visitInsn(Opcodes.ICONST_0); // Index 0
                            mv.visitLdcInsn("[Agent]");
                            mv.visitInsn(Opcodes.AASTORE); // Store the string in the array

                            // Check if 'parameter' is null and store appropriate string in the second position of the array
                            mv.visitInsn(Opcodes.DUP); // Duplicate the array reference
                            mv.visitInsn(Opcodes.ICONST_1); // Index 1
                            mv.visitVarInsn(Opcodes.ALOAD, 2); // Load 'parameter' onto operand stack

                            org.objectweb.asm.Label notNullLabel = new org.objectweb.asm.Label();
                            org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

                            mv.visitInsn(Opcodes.DUP); // Duplicate 'parameter' on the stack
                            mv.visitJumpInsn(Opcodes.IFNONNULL, notNullLabel); // Jump to notNullLabel if 'parameter' is not null

                            mv.visitInsn(Opcodes.POP); // Pop the duplicated 'parameter' from the stack
                            mv.visitLdcInsn("is NULL");
                            mv.visitJumpInsn(Opcodes.GOTO, endLabel);

                            mv.visitLabel(notNullLabel);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;", false);

                            mv.visitLabel(endLabel);
                            mv.visitInsn(Opcodes.AASTORE); // Store the string (either "is NULL" or the result of parameter.toString()) in the array

                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "printf", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;", false);
                            mv.visitInsn(Opcodes.POP); // Discard the result of printf
                            
                            
                            // System.out.printf("%s Query : [%s]\n", "[Agent]", sqlQuery);
                            mv.visitVarInsn(Opcodes.ALOAD, 1); // Load 'ms' onto operand stack
                            mv.visitVarInsn(Opcodes.ALOAD, 2); // Load 'parameter' onto operand stack
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "org/apache/ibatis/mapping/MappedStatement", "getBoundSql", "(Ljava/lang/Object;)Lorg/apache/ibatis/mapping/BoundSql;", false);
                            mv.visitVarInsn(Opcodes.ASTORE, 7); // Store the result (boundSql) to a new local variable

                            // String sqlQuery = boundSql.getSql();
                            mv.visitVarInsn(Opcodes.ALOAD, 7); // Load 'boundSql' onto operand stack
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "org/apache/ibatis/mapping/BoundSql", "getSql", "()Ljava/lang/String;", false);
                            mv.visitVarInsn(Opcodes.ASTORE, 8); // Store the result (sqlQuery) to a new local variable

                            // System.out.printf("%s Query : [%s]\n", "[Agent]", sqlQuery);
                            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            mv.visitLdcInsn("%s Query : [%s]\n"); // Push the format string onto the operand stack
                            mv.visitInsn(Opcodes.ICONST_2); // Push the number 2 (number of arguments for printf)
                            mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object"); // Create an array of Objects with size 2

                            // array[0] = "[Agent]"
                            mv.visitInsn(Opcodes.DUP); // Duplicate the array reference
                            mv.visitInsn(Opcodes.ICONST_0); // Push the index 0
                            mv.visitLdcInsn("[Agent]"); // Push the string "[Agent]"
                            mv.visitInsn(Opcodes.AASTORE); // Store the string at index 0 of the array

                            // array[1] = sqlQuery
                            mv.visitInsn(Opcodes.DUP); // Duplicate the array reference
                            mv.visitInsn(Opcodes.ICONST_1); // Push the index 1
                            mv.visitVarInsn(Opcodes.ALOAD, 8); // Load 'sqlQuery' onto operand stack
                            mv.visitInsn(Opcodes.AASTORE); // Store the string at index 1 of the array

                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "printf", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;", false);
                            mv.visitInsn(Opcodes.POP); // Discard the unwanted result from the printf method
                        }
                        else if("query".equals(name) && paramCount == 6){
                            
                        }
                    }
                };
            }
        };

        cr.accept(cv, 0);
        return cw.toByteArray();
    }
    
    private int getParameterCount(String descriptor) {
        // 괄호 사이의 파라미터 부분을 추출
        String params = descriptor.substring(descriptor.indexOf('(') + 1, descriptor.indexOf(')'));
        
        if (params.isEmpty()) {
            return 0;
        }
        
        // 간단한 파라미터 개수 계산 (주의: 이 방법은 배열, 제네릭 및 중첩된 클래스를 완벽하게 처리하지 않습니다)
        int count = 0;
        char[] chars = params.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch == 'L') {
                i = params.indexOf(';', i);
                count++;
            } else if (ch != '[') { // 배열 타입을 건너뛰기
                count++;
            }
        }
        
        return count;
    }
}
