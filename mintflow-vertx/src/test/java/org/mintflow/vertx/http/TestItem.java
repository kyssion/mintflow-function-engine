package org.mintflow.vertx.http;

public class TestItem {
    public interface Strategy{
        int doOperation(int num1, int num2);
    }
    public static class OperationAdd implements Strategy{
        @Override
        public int doOperation(int num1, int num2) {
            return num1 + num2;
        }
    }
    public static class OperationSubtract implements Strategy{
        @Override
        public int doOperation(int num1, int num2) {
            return num1 - num2;
        }
    }
    public static class OperationMultiply implements Strategy{
        @Override
        public int doOperation(int num1, int num2) {
            return num1 * num2;
        }
    }
    public static class Context {
        private Strategy strategy;
        public Context(Strategy strategy){
            this.strategy = strategy;
        }
        public int executeStrategy(int num1, int num2){
            return strategy.doOperation(num1, num2);
        }
    }
    public static void main(String[] args) {
        System.out.println("10 + 5 = " + (10+5));
        System.out.println("10 - 5 = " + (10+5));
        System.out.println("10 * 5 = " + (10+5));
    }
}
