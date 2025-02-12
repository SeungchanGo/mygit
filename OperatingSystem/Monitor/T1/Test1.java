package Monitor.T1;


// 모니터를 사용하여 상호배타 해결

class BankAccount{
    int balance;
    synchronized void deposit(int amount){
        int temp = balance + amount;  //balance = balance + amount;
        System.out.print("+");
        balance = temp;
    }
    
    synchronized void withdraw(int amount){
        int temp = balance - amount;  //balance = balance - amount;
        System.out.print("-");
        balance = temp;
    }
    
    int getBalance(){
        return balance;
    }
}

// 부모 쓰레드
class Parent extends Thread{
    BankAccount b;
    Parent(BankAccount b){
        this.b = b;
    }

    public void run(){
        for(int i=0; i<1000; i++){
            b.deposit(1000);
        }
    }
}

// 자식 쓰레드
class Child extends Thread{
    BankAccount b;
    Child(BankAccount b){
        this.b = b;
    }

    public void run(){
        for(int i=0; i<1000; i++){
            b.withdraw(1000);
        }
    }
}


class Test1{
    public static void main(String[] args) throws InterruptedException {
        BankAccount b = new BankAccount();
        Parent p = new Parent(b);
        Child c = new Child(b);
        p.start();
        c.start();
        p.join();
        c.join();
        System.out.print("Balance = " + b.getBalance());
    }
}