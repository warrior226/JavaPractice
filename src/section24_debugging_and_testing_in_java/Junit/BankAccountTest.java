package section24_debugging_and_testing_in_java.Junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest  {

    private  BankAccount account;

    @BeforeAll
    public static void beforeCLass(){
        System.out.println("This method is runned before any test in the test class");
    }
    @BeforeEach
    public  void setUp(){
     account =new BankAccount("Patrick","Rayaisse",1000.00,BankAccount.CHECKING);
     System.out.println("Running our test...");
    }
    @Test
    void deposit() {
        double balance=account.deposit(200.00,true);
        assertEquals(1200.00,balance,0);
    }

    @Test
    void withdraw_branch()throws Exception {
      double balance= account.withdraw(600.00,true);
      assertEquals(400.00,balance,0);
    }
    @Test
    void withdraw_notbranch() throws Exception {
        account.withdraw(600.00,false);
        fail("Should throw an exception");
    }

    @Test
    void getBalance_deposit() {
        account.deposit(200.00,true);
        assertEquals(1200.00,account.getBalance(),0);
        ;
    }
    @Test
    void getBalance_withdraw() {
        account.withdraw(200.00,true);
        assertEquals(800.00,account.getBalance(),0);
    }

    @Test
    public void isChecking_true(){
        assertTrue(account.isChecking(), "This account is NOT a checking account");
    }

    @AfterAll
    public  static void afterClass(){
        System.out.println("Runs after all the test is completed");
    }
}