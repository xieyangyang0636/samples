package cn.yano.samples.java.jdk.main;

import cn.yano.samples.java.jdk.impls.FunctionalInterfaceService;

/**
 * FunctionalInterface
 * 函数式接口
 */
public class FunctionalInterfaceTest {
    public static void main(String[] args) {
        FunctionalInterfaceService service = new FunctionalInterfaceService();
        System.out.println(service.add(10, 2, 3));
        System.out.println(service.multi(10, 2, 3));
    }
}
