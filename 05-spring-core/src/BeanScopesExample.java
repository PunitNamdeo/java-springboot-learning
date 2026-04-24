/**
 * BeanScopesExample.java
 * Demonstrates Singleton vs Prototype bean behavior
 */
public class BeanScopesExample {

    public static void main(String[] args) {

        // --- Singleton Scope (default) ---
        System.out.println("=== Singleton Scope ===");
        // In Spring: only ONE instance is created and shared
        SingletonBean s1 = new SingletonBean();
        // Spring would give the same instance every time
        System.out.println("Singleton: same instance used everywhere");
        System.out.println("Good for: stateless services, repositories, controllers");
        System.out.println("Instance: " + s1);

        // --- Prototype Scope ---
        System.out.println("\n=== Prototype Scope ===");
        // In Spring: a NEW instance is created every time it's requested
        PrototypeBean p1 = new PrototypeBean("Request-1");
        PrototypeBean p2 = new PrototypeBean("Request-2");
        System.out.println("Prototype: new instance every time");
        System.out.println("Good for: stateful objects, report generators");
        System.out.println("Instance 1: " + p1.getId());
        System.out.println("Instance 2: " + p2.getId());

        // --- Bean Lifecycle ---
        System.out.println("\n=== Bean Lifecycle ===");
        LifecycleBean bean = new LifecycleBean();
        bean.init();         // @PostConstruct equivalent
        bean.doWork();
        bean.destroy();      // @PreDestroy equivalent
    }
}

class SingletonBean {
    // In real Spring: @Component — one instance shared everywhere
    private static int instanceCount = 0;
    private int id;
    public SingletonBean() { id = ++instanceCount; }
    @Override public String toString() { return "SingletonBean#" + id; }
}

class PrototypeBean {
    // In real Spring: @Component @Scope("prototype") — new instance each time
    private String id;
    public PrototypeBean(String id) { this.id = id; }
    public String getId() { return id; }
}

class LifecycleBean {
    public LifecycleBean() {
        System.out.println("1. Constructor called — bean instantiated");
    }
    public void init() {
        // In Spring: @PostConstruct
        System.out.println("2. @PostConstruct — initialize resources (connections, caches)");
    }
    public void doWork() {
        System.out.println("3. Bean in use — handling requests");
    }
    public void destroy() {
        // In Spring: @PreDestroy
        System.out.println("4. @PreDestroy — cleanup resources (close connections)");
    }
}
