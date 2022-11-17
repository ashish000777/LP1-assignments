import java.util.concurrent.Semaphore;

public class reader_writer {
    
    static int readerCount = 0;
    static Semaphore x = new Semaphore(1);
    static Semaphore xsem = new Semaphore(1);

    static class Read implements Runnable{

        @Override
        public void run() {
            try{
                x.acquire();
                readerCount++;
                if(readerCount == 1)
                {
                    xsem.acquire();
                }
                x.release();
                System.out.println("Thread " + Thread.currentThread().getName() + " is READING");
                Thread.sleep(1500);
                System.out.println("Thread " + Thread.currentThread().getName() + " is FINISHED READING");
                x.acquire();
                readerCount--;
                if(readerCount == 0)
                {
                    xsem.release();
                }
                x.release();
            }
            catch(InterruptedException e)
            {
                System.out.println(e.getMessage());
            }

        }
    }

    static class Write implements Runnable{
        @Override
        public void run()
        {
            try{
                xsem.acquire();
                System.out.println("Thread " + Thread.currentThread().getName() + " is WRITING");
                Thread.sleep(2500);
                System.out.println("Thread " + Thread.currentThread().getName() + " is FINISHED WRITING");
                xsem.release();
            }
            catch(InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    
    
    public static void main(String[] args) throws Exception{
        Read read = new Read();
        Write write = new Write();
        Thread t1 = new Thread(read);
        t1.setName("Thread1");
        Thread t2 = new Thread(read);
        t2.setName("Thread2");
        Thread t3 = new Thread(write);
        t3.setName("Thread3");
        Thread t4 = new Thread(read);
        t4.setName("Thread4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
