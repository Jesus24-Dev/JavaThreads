//Extendiendo de la clase Threads

public class Hilos1 {
    
    static class Proceso1 extends Thread{
        @Override
        public void run(){
            for (int i = 0; i < 5; i++){
                System.out.println("Proceso 1");
            }
        }
    }

    static class Proceso2 extends Thread{
        @Override
        public void run(){
            for (int i = 0; i < 5; i++){
                System.out.println("Proceso 2");
            }
        }
    }

    public static void main(String[] args) {        
        Proceso1 hilo1 = new Proceso1();
        Proceso2 hilo2 = new Proceso2();        
        hilo1.start();
        hilo2.start();        
    }
}