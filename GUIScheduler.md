![http://scheduling-algorithms.googlecode.com/files/GUI%20Scheduler.jpg](http://scheduling-algorithms.googlecode.com/files/GUI%20Scheduler.jpg)

  * La opción "Import" del menú permite importar un fichero de datos a la aplicación.
  * La opción "Export" del menú permite exportar los datos de la aplicación a un fichero legible por el Importador.
  * El panel de conjuntos deberá mostrar los conjuntos de tareas **una vez importado el fichero de datos**. Tener en cuenta que el número de conjuntos es variable.
  * El botón "Tareas" llama a la GUI Tareas, la cual mostrará las tareas **del conjunto seleccionado en el panel de conjuntos.**
  * El botón "Planificar" empezará la planificación **del conjunto seleccionado en el panel de conjuntos** en un hilo (el método a llamar y el runnable ya están creados). Mientras se esté planificando, tanto el botón "Planificar" como "Tareas" deben estar deshabilitados. Opcionalmente se puede mostrar un cuadro de diálogo mostrando que la aplicación está trabajando (por ejemplo un gif animado).