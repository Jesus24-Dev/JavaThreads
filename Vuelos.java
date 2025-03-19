import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Definición de la clase principal "Vuelos"
public class Vuelos {
    
    // Método para inicializar la reserva de vuelos
    public void initReservaVuelos() throws InterruptedException {
        // Se crea un pool de 3 hilos para ejecutar tareas en paralelo
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Se crean y envían 6 tareas de reserva de vuelos al pool de hilos
        for (int i = 1; i <= 6; i++) {
            // Se crea una nueva instancia de ReservaVuelos con nombre de cliente, número de vuelo y fecha
            ReservaVuelos vuelo = new ReservaVuelos("Cliente" + i, 101, "14/11/24");
            // Se asigna el número de boleto correspondiente
            vuelo.setNumberTicket(i);
            // Se envía la tarea al pool de hilos para su ejecución
            executor.submit(vuelo);
        }

        // Se ordena el cierre del pool de hilos después de completar las tareas
        executor.shutdown();
        // Se espera a que todas las tareas finalicen en un tiempo máximo de 1 minuto
        executor.awaitTermination(1, TimeUnit.MINUTES);
        // Mensaje indicando que todas las reservas han sido procesadas
        System.out.println("Todos los vuelos han sido procesados.");
    }

    // Clase interna que representa la reserva de un vuelo
    public class ReservaVuelos implements Runnable {
        private final String customerName; // Nombre del cliente
        private final long flightNumber;   // Número de vuelo
        private final String flightDate;   // Fecha del vuelo
        protected int numberTicket;        // Número de boleto

        // Constructor para inicializar los atributos de la reserva
        public ReservaVuelos(String customerName, long flightNumber, String flightDate) {
            this.customerName = customerName;
            this.flightNumber = flightNumber;
            this.flightDate = flightDate;
        }

        // Método para asignar el número de boleto
        public void setNumberTicket(int numberTicket) {
            this.numberTicket = numberTicket;
        }

        // Método que se ejecuta al iniciar la tarea en el pool de hilos
        @Override
        public void run() {
            try {
                // Se crea una instancia de la clase CheckAvailability
                CheckAvailability checkAvailability = new CheckAvailability();
                // Se ejecuta la verificación de disponibilidad
                if (!doTasks(checkAvailability, "Verificar disponibilidad")) {
                    // Si no hay disponibilidad, se muestra un mensaje y se finaliza la tarea
                    System.out.println(customerName + ": No hay disponibilidad para el boleto " + numberTicket);
                    return;
                }

                // Se crea una instancia de la clase ProcessPayment
                ProcessPayment processPayment = new ProcessPayment();
                // Se ejecuta el procesamiento del pago
                if (!doTasks(processPayment, "Realizar pago")) {
                    // Si el pago falla, se muestra un mensaje y se finaliza la tarea
                    System.out.println(customerName + ": El pago no pudo ser procesado.");
                    return;
                }

                // Se crea una instancia de la clase GenerateTicket
                GenerateTicket generateTicket = new GenerateTicket();
                // Se ejecuta la generación del ticket
                if (!doTasks(generateTicket, "Generar Ticket")) {
                    // Si no se puede generar el ticket, se muestra un mensaje y se finaliza la tarea
                    System.out.println(customerName + ": No se pudo generar el ticket.");
                    return;
                }

                // Mensaje de éxito en la reserva del vuelo
                System.out.println("Reserva exitosa: " + customerName + ", Vuelo " + flightNumber + ", Numero de boleto: " + numberTicket);
            } catch (InterruptedException e) {
                // Si la tarea es interrumpida, se muestra un mensaje de error
                System.out.println("Vuelo interrumpido.");
            }
        }

        // Método para ejecutar una tarea específica
        private boolean doTasks(Runnable task, String taskName) throws InterruptedException {
            // Se muestra un mensaje indicando que la tarea está iniciando
            System.out.println(customerName + ": " + taskName + " - Iniciando");
            // Se crea un nuevo executor de un solo hilo para ejecutar la tarea
            ExecutorService taskExecutor = Executors.newSingleThreadExecutor();
            // Se envía la tarea al executor
            taskExecutor.submit(task);
            // Se cierra el executor después de completar la tarea
            taskExecutor.shutdown();
            // Se espera un máximo de 1 minuto a que la tarea finalice
            taskExecutor.awaitTermination(1, TimeUnit.MINUTES);
            // Se muestra un mensaje indicando que la tarea ha finalizado
            System.out.println(customerName + ": " + taskName + " - Finalizado");
            return true;
        }

        // Clase interna que verifica la disponibilidad del vuelo
        class CheckAvailability implements Runnable {
            @Override
            public void run() {
                try {
                    // Simula un tiempo de procesamiento aleatorio entre 1 y 2 segundos
                    Thread.sleep((long) ((Math.random() * 2) + 1) * 1000);
                    // Si el número de boleto es mayor a 5, se lanza una excepción para interrumpir la verificación
                    if (numberTicket > 5) {
                        throw new InterruptedException();
                    }
                } catch (InterruptedException e) {
                    // Mensaje indicando que la verificación fue interrumpida
                    System.out.println("Verificación de disponibilidad interrumpida para " + customerName);
                }
            }
        }

        // Clase interna que procesa el pago del boleto
        class ProcessPayment implements Runnable {
            @Override
            public void run() {
                try {
                    // Simula un tiempo de procesamiento aleatorio entre 2 y 5 segundos
                    Thread.sleep((long) ((Math.random() * 4) + 2) * 1000);
                } catch (InterruptedException e) {
                    // Mensaje indicando que el pago fue interrumpido
                    System.out.println("Procesar pago interrumpido.");
                }
            }
        }

        // Clase interna que genera el ticket del vuelo
        class GenerateTicket implements Runnable {
            @Override
            public void run() {
                try {
                    // Simula un tiempo de procesamiento aleatorio entre 1 y 2 segundos
                    Thread.sleep((long) ((Math.random() * 2) + 1) * 1000);
                } catch (InterruptedException e) {
                    // Mensaje indicando que la generación del ticket fue interrumpida
                    System.out.println("Generar ticket interrumpido.");
                }
            }
        }
    }

    // Método principal que inicia la ejecución del programa
    public static void main(String[] args) {
        try {
            // Se crea una instancia de la clase Vuelos
            Vuelos vuelos = new Vuelos();
            // Se inicia la reserva de vuelos
            vuelos.initReservaVuelos();
        } catch (InterruptedException e) {
            // Mensaje de error en caso de interrupción en la reserva
            System.out.println("Error en la reserva de vuelos.");
        }
    }
}
