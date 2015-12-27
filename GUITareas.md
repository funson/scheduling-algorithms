![http://scheduling-algorithms.googlecode.com/files/GUI%20Tareas%20%28peri%C3%B3dicas%29.jpg](http://scheduling-algorithms.googlecode.com/files/GUI%20Tareas%20%28peri%C3%B3dicas%29.jpg)

![http://scheduling-algorithms.googlecode.com/files/GUI%20Tareas%20%28aperiodicas%20inicial%29.jpg](http://scheduling-algorithms.googlecode.com/files/GUI%20Tareas%20%28aperiodicas%20inicial%29.jpg)

![http://scheduling-algorithms.googlecode.com/files/GUI%20Tareas%20%28aperiodicas%20auto%29.jpg](http://scheduling-algorithms.googlecode.com/files/GUI%20Tareas%20%28aperiodicas%20auto%29.jpg)

![http://scheduling-algorithms.googlecode.com/files/GUI%20Tareas%20%28aperiodicas%20manual%29.jpg](http://scheduling-algorithms.googlecode.com/files/GUI%20Tareas%20%28aperiodicas%20manual%29.jpg)

  * En esta GUI se muestran las tareas de un conjunto (seleccionado en la GUI Scheduler).
  * Las tareas están separadas por pestañas, una para cada tipo de tarea.
  * Periódicas:
    * La pestaña de periódicas muestra todos los grupos de tareas periódicas del conjunto.
    * Los grupos y sus tareas se muestran en tablas separadas, y todo ello está dentro de un scroll panel.
    * Estas tareas son solo de consulta (las celdas de la tabla no son editables).
    * El botón de OK/Aceptar cierra esta GUI.
  * Aperiódicas:
    * La pestaña de aperiódicas muestra las tareas aperiódicas del conjunto.
    * Inicialmente:
      * Si no hay ningún grupo de tareas aperiódicas creado no se muestra ninguna tabla ni nada. Solo hay un botón "Generar" y 2 radio buttons para elegir el modo de generación de tareas aperiódicas.
      * Si hay algún grupo de tareas periódicas creado, se mostrará la información del grupo(s) según si ha(n) sido generado(s) manualmente (en este caso habrá un único grupo) o automáticamente (en este caso habrá varios grupos).
    * Al generar manualmente:
      * Se muestra una tabla que contendrá las tareas aperiódicas, la cual está dentro de un scroll panel. Al pulsar "Generar" en modo manual, la tabla contendrá una única tarea con la info. por defecto.
      * Las celdas de la tabla son editables.
      * A la derecha de cada fila hay un botón que permite eliminar la tarea del grupo de tareas aperiódicas del conjunto.
      * Debajo de la tabla hay un botón para insertar una nueva tarea a través de un cuadro de diálogo (diseñar al gusto).
    * Al generar automáticamente:
      * Se muestran todos los grupos de tareas aperiódicas generados automáticamente, distribuidos en tablas.
      * estas tablas no son editables.
    * El botón "Cancelar" cierra esta GUI. No se guarda ningún cambio.
    * Las modificaciones, eliminaciones e inserciones de tareas sólo se guardaran cuando se pulse "Guardar". Para ello se puede clonar el grupo de tareas aperiódicas (el método clone() ya lo hemos implementado a nivel de TaskGroup) y que los cambios en GUI afecten al grupo clonado. Al pulsar "Guardar" se sobreescribirá el grupo original con el clonado y se asignará al Scheduler; si en cambio se pulsa "Cancelar", el grupo clonado se descarta.