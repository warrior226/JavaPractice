package section21.exo325;

public class Thread1 extends Thread{

    int evenNumberCount=0;
    @Override
    public void run() {
        for(int i=0;i<20;i++){
            if(i%2==0){
                evenNumberCount++;
                if(evenNumberCount<=5){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    break;
                }
            }
        }
    }

}
