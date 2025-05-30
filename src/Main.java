import EnumProject.DayOfTheWeek;
import section21.exo325.Thread1;

import java.util.Random;


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

        DayOfTheWeek dayOfTheWeek = DayOfTheWeek.FRI;
        System.out.println(dayOfTheWeek);
        System.out.printf("The name of the date is %s and the order is %d%n",dayOfTheWeek.name(),dayOfTheWeek.ordinal());

        for(int i=0;i<10;i++){
            dayOfTheWeek=getRandomDay();
            //System.out.printf("The name of the date is %s and the order is %d%n",dayOfTheWeek.name(),dayOfTheWeek.ordinal());
            switchDayOfWeek(dayOfTheWeek);

        }

    }

    public static  DayOfTheWeek getRandomDay(){
        int randomInteger=new Random().nextInt(7);
        var allDays=DayOfTheWeek.values();
        return allDays[randomInteger];
    }

    public static void switchDayOfWeek(DayOfTheWeek weekDay){
        int weekDayInteger= weekDay.ordinal()+1;
        switch (weekDay){
            case MON -> System.out.println("Monday is Day "+ weekDayInteger);
            case WED ->System.out.println("Wednesday is Day "+ weekDayInteger);
            default -> System.out.println(weekDay.name().charAt(0)+weekDay.name().substring(1).toLowerCase()+"day is Day "+weekDayInteger);
        }
    }
}