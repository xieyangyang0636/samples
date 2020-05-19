package cn.yano.samples.java.jdk.interfaces;

/**
 * 函数式接口
 */
@FunctionalInterface
public interface ThreeNumberFunction {
    int execute(int a, int b, int c);
}
