package section24_debugging_and_testing_in_java.Junit.Challenge;

public class Utilities {

    public char[]everyNthChar(char[] sourceArray,int n){
      if(sourceArray==null || sourceArray.length<n){
          return sourceArray;
      }

      int returnedLength=sourceArray.length/n;
      char[] result=new char[returnedLength];
      int index =0;
      for(int i=n-1;i<sourceArray.length;i+=n){
          result[index++]=sourceArray[i];
      }
      return result;
    }

}
