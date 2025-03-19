//Implementado la interfaz Runnable

public class Hilos2 {
    static class MiTarea implements Runnable {
        
        private final int id;

        public MiTarea(int id){
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("Tarea en ejecución: " + this.id);
        }
    }

    public static void main(String[] args) {
        // Crear las tareas
        MiTarea tarea = new MiTarea(1);
        MiTarea tarea2 = new MiTarea(2);
        MiTarea tarea3 = new MiTarea(3);

        // Crear hilos para cada tarea
        Thread hilo1 = new Thread(tarea);
        Thread hilo2 = new Thread(tarea2);
        Thread hilo3 = new Thread(tarea3);

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
    }
}