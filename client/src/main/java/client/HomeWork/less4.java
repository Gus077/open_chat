package client.HomeWork;

public class less4 {
    private static Object mon = new Object();
    private static volatile int currentNum = 1;
    private static final int num = 5;

    public static void main(String[] args) {

        new Thread(() -> {
            try{
            for (int i = 0; i < num; i++) {
                synchronized (mon){
                    while (currentNum != 1){
                            mon.wait();
                    }
                    System.out.print("A");
                    currentNum = 2;
                    mon.notifyAll();
                }
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try{
                for (int i = 0; i < num; i++) {
                    synchronized (mon){
                        while (currentNum != 2){
                            mon.wait();
                        }
                        System.out.print("B");
                        currentNum = 3;
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try{
                for (int i = 0; i < num; i++) {
                    synchronized (mon){
                        while (currentNum != 3){
                            mon.wait();
                        }
                        System.out.print("C ");
                        currentNum = 1;
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
