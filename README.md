# Java Threads

Los hilos en Java son unidades de ejecución simultanea dentro de un programa. Cada tarea se ejecuta en un hilo diferente, por lo que se puede ejecutar múltiples operaciones de manera simultanea. 

Los hilos permiten aprovechar al máximo los recursos del procesador, evita que una tarea bloquee todo el programa y es modelado a sistemas concurrentes, por ejemplo, servidores web que manejan múltiples peticiones. 

## Como crear un hilo

Hay dos maneras de crear un hilo en Java

### 1. Extendiendo de la clase Thread.

Para esta forma, creamos una clase y la extendemos de *Thread*, sobrescribimos el método `run()` y para iniciar el hilo, llamamos el método `start()` en la instancia.


```java
class Proceso1 extends Thread{
    @Override
    public void run(){
        for (int i = 0; i < 5; i++){
            System.out.println("Proceso 1");
        }
    }
}

class Proceso2 extends Thread{
    @Override
    public void run(){
        for (int i = 0; i < 5; i++){
            System.out.println("Proceso 2");
        }
    }
}

public class Main {
    public static void main(String[] args) {        
        Proceso1 hilo1 = new Proceso1();
        Proceso2 hilo2 = new Proceso2();        
        hilo1.start();
        hilo2.start();        
    }
}

```

### 2. Implementando la interfaz Runnable

Se crea una clase que implementa la interfaz `Runnable`. Luego se implementa el método `run()` con el código a ejecutar en el hilo. Finalmente se crea una instancia de la clase `Thread` y se pasa un objeto de la clase implementadora de `Runnable` al constructor. 

```Java
class MiTarea implements Runnable {
    public void run() {
        System.out.println("Tarea en ejecución");
    }
}

public class EjemploHilos {
    public static void main(String[] args) {
        MiTarea tarea = new MiTarea();
        Thread hilo = new Thread(tarea);
        hilo.start();
    }
}
```

## Conceptos clave relacionados con hilos

- **Estado de un hilo:** Nuevo, ejecutable, en ejecución, bloqueado, terminado.
- **Prioridad de un hilo:** Permite controlar qué hilos se ejecutan primero.
- **Sincronización de hilos:** Mecanismos para coordinar la ejecución de múltiples hilos (monitores, semáforos).
- **Deadlocks:** Situaciones en las que dos o más hilos se bloquean mutuamente, esperando que el otro libere un recurso.

## ¿Cuándo utilizar hilos?

- **Tareas I/O:** Leer/escribir archivos, realizar peticiones a redes.
- **Cálculos intensivos:** Procesamiento de grandes cantidades de datos.
- **Interfaces gráficas:** Actualizar la interfaz mientras se realizan otras tareas en segundo plano.