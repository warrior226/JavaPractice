import section21.exo325.Thread1;


//class OddThread extends Thread{
//    @Override
//    public void run() {
//        for(int i=1;i<=10;i+=2){
//            System.out.println("OddThread: "+i);
//            try{
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                System.out.println("OddThread interrupted!");
//                break;
//            }
//        }
//    }
//
//}

class EvenRunnable implements Runnable{

    @Override
    public void run() {
        for(int i=2;i<=10;i+=2){
            System.out.println("EvenThread: "+i);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("EvenThread interrupted!");
                break;
            }
        }

    }
}

public class Main {
    public static void main(String[] args) {

       // OddThread oddThread=new OddThread();
        Thread oddThread=new Thread(()->{
            for(int i =1;i<=10;i+=2){
                System.out.println("OddThread: "+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("OddThread interrupted!");
                       break;
                }
            }
        });
        Thread evenThread=new Thread(new EvenRunnable());
        oddThread.start();
        evenThread.start();

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        oddThread.interrupt();

    }
}