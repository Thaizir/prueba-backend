# Seccion 1

Según el problema propuesto, podríamos definir 3 entidades:

- Cliente (tabla clients)
- Horario (tabla schedules)
- Reservaciones (tabla reservations)

Y hacer las siguientes relaciones entre ellas:

### Tabla clients 

- Esta tabla almacena la informacion de los clientes: nombre, email y telefono.
- Hay una relacion de uno a muchos con la tabla reservations (1 a N) pues un cliente puede hacer
  varias reservacione, pero cada reservacion está asociada a un uúnico cliente.

### Tabla schedules

- La tabla define los horarios en los que se pueden hacer las reservaciones.
- Existe una relacion de uno a uno (1 a 1) con la tabla reservations ya que un horario puede
  estar asociado unicamente a una reservacion y viceversa al mismo tiempo lo cual asegura que un
  horario no pueda ser seleccionado para dos reservas.

### Tabla reservations 

- Conecta a los clientes con los horarios ya reservados, contiene un cliente y un horario
  relacionados por sus ids. Cada reservacion está asociada a un unico horario y a un unico cliente.
- Tiene una relaciones de muchos a uno (N a 1) con la table clientes porque varias reservaciones
  pueden estas asociadas al mismo cliente.
- TIene una relacion uno a uno con la tabla schedule (1 a 1) ya que un horario puede estar asociado
  a una sola reservacion y viceversa.
