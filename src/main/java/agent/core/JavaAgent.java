package agent.core;

import java.lang.instrument.Instrumentation;

public class JavaAgent {

    /* 
     * 에이전트를 시작하고 프로파일링 또는 모니터링과 같은 작업을 수행하는데 사용.
     * 에이전트는 premain 또는 agentmain 메서드에서 Instrumentation 객체를 통해
     * JVM에게 클래스 변형 등의 정보를 전달할 수 있다. 
     * */
    private static Instrumentation instrumentation;

    /* 
     * 클래스 로딩 메커니즘과 관련된 클래스
     * BCI를 구현할 때는 클래스 로더를 변경하여 원하는 클래스 변형을 적용할 수 있다.
     * */
    private static ClassLoader platformClassLoader;

    
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent started with agentArgs: " + agentArgs);
        
        // ClassLoadTimeTransformer 클래스의 인스턴스를 등록
        // 클래스 로딩 시에 변형 로직이 동작하도록 설정하는 역할
        inst.addTransformer(new AgentTransformer());
    }
}
