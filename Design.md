# Enfoque del cálculo de la planificación #

Hay principalmente 2 formas de calcular la planificación:
  * Vertical: Mirar en cada instante de tiempo la tarea a ejecutar.
  * Horizontal: Hacer la planificación completa de cada tarea en orden descendente de prioridad.

Hemos descartado la vertical porque es un planteamiento más difícil de implementar y menos eficiente.

En el enfoque horizontal iremos grabando los intervalos de tiempo de las ejecuciones de las tareas, a lo que nos referiremos como **Resumen de ejecución**. De esta manera se hará muy fácil saber si una tarea se puede ejecutar o no. Para añadir, este resumen ayudaría a la representación gráfica de la planificación que, en principio, sale del alcance de la práctica.

![http://scheduling-algorithms.googlecode.com/files/Planteamiento%20planificaci%C3%B3n.jpg](http://scheduling-algorithms.googlecode.com/files/Planteamiento%20planificaci%C3%B3n.jpg)

(230 no es el hiperperíodo sino que es un número arbitrario para poder ilustrar el enfoque que adoptaremos para hacer la planificación y el **Resumen de ejecución** resultante).


# Diagrama de clases #

![http://scheduling-algorithms.googlecode.com/files/Diagramadeclase.gif](http://scheduling-algorithms.googlecode.com/files/Diagramadeclase.gif)

**Planificador**: clase que realiza la planificación de un conjunto de tareas periódicas en orden descendente de prioridad y que da como resultado los diferentes **Resúmenes de planificación**, a la vez que genera los Resultados de la planificación.

**Resultado**: clase que contiene los resultados de planificación; en concreto, contiene un conjunto de sub-resultados, donde cada sub-resultado es el tiempo medio de respuesta aperiódica dados un servidor y una carga total (periódica + aperiódica).

**Importador**: clase que lee e interpreta el fichero de entrada para introducir la información de las tareas periódicas y los servidores (que también son tareas periódicas) a la aplicación.

**Exportador**: clase que permite generar un fichero de parámetros de la aplicación, igual al fichero que lee el **Importador**. También permite generar el fichero de resultados a partir de los Resultados de la planificación.

**Resumen**: clase que representa el Resumen de ejecución. Es una lista de nodos que representan los intervalos de tiempo de ejecución de las tareas así como los intervalos de tiempo libres donde no se ejecuta nada.

**Nodo**: clase que representa un nodo del Resumen de ejecución. Debe contener los instantes de tiempo en que comienza y termina así como la información de lo que está pasando entre estos 2 instantes de tiempo (ejecución de una tarea o libre).

**Conjunto de tareas**: conjunto de tareas que, tal y como se especifica en el enunciado, está formado por 10 grupos de tareas periódicas.

**Grupo de tareas**: clase que contiene la agrupación de tareas.

**Grupo de tareas periódicas**: agrupación de 10 tareas periódicas, tal y como se especifica en el enunciado.

**Grupo de tareas aperiódicas**: agrupación de las tareas aperiódicas que se deben planificar y que son comunes para todos los grupos de tareas periódicas, es decir, son siempre las mismas durante toda la planificación de un determinado conjunto de tareas.

**Tarea**: clase que representa una tarea que debe planificarse.

**Tarea periódica**: tarea repetitiva a lo largo del tiempo y que tiene un período definido que coincide con su deadline.

**Tarea aperiódica**: tarea esporádica que tiene un tiempo de llegada concreto.

**Servidor**: servidor que actúa como tarea periódica y planifica las tareas aperiódicas. Puede ser de uno de los siguientes tipos:
  * BS: Servidor de background
  * PS: Servidor interrogador
  * DS: Servidor aplazable
  * PE: Intercambio de prioridades
  * SS: Servidor esporádico