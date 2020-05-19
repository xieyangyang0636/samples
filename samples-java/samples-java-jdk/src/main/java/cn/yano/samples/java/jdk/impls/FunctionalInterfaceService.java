package cn.yano.samples.java.jdk.impls;

import cn.yano.samples.java.jdk.interfaces.ThreeNumberFunction;

/**
 * 函数式接口
 */
public class FunctionalInterfaceService {

    /**
     * 加法
     *
     * @param a 入参
     * @param b 入参
     * @param c 入参
     * @return 结果
     */
    public int add(int a, int b, int c) {
        return executeTemplate((a1, b1, c1) -> a1 + b1 + c1, a, b, c);
    }

    /**
     * 乘法
     *
     * @param a 入参
     * @param b 入参
     * @param c 入参
     * @return 结果
     */
    public int multi(int a, int b, int c) {
        return executeTemplate((a1, b1, c1) -> a1 * b1 * c1, a, b, c);
    }

    /**
     * 通用模板方法
     *
     * @param threeNumberFunction 函数
     * @param a                   入参
     * @param b                   入参
     * @param c                   入参
     * @return 结果
     */
    public int executeTemplate(ThreeNumberFunction threeNumberFunction, int a, int b, int c) {
        long start = System.currentTimeMillis();
        int num = threeNumberFunction.execute(a, b, c);
        long end = System.currentTimeMillis();
        System.out.println("execute method cost " + (end - start) + "ms");
        return num;
    }
}
