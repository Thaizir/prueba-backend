Suponiendo que hubiera un incremento significativo en las
consutas de horarios y creación de reservaciones podriamos considerar las siguientes opciones
dependiendo si estamos en una aplicacion monolitica o en una arquitectura de microservicios, o si
las fallas empiezan a ser del lado de requests o del lado de la base de datos.

Se podría utilizar un servicio de contenedor Amazon ECS para conteinerizar la aplicacion y ajustar
una politica de escalado automatico basado en uso del CPU de la instancia o el trafico, por ejemplo,
si la carga del cpu llega al 80% podemos buscar escalar horizontalmente hasta que la carga vuelva a
disminur. O en el caso que la aplicacion corra en varias instancias se puede utilizar un balanceador
de cargas como AWS ELB.

Por otra parte, si la base de datos no puede manejar adecuadamente el trafico de solicitudes se
podrían optimizar las queries a la base de datos para mejorar el rendimiento. O en el caso de usar
un servicio como Amazon RDS, podriamos subir al siguiente tier de capacidad.

Como los slots de reservas no deberian cambiar mucho, se podrian catchear en Redis con un time to
live de algunos minutos los horarios futuros dentro de un rango de tiempo y que el frontend los
filtre por disponibilidad.

Finalmente, en cuanto a microservicios, podriamos crear 3 microservicios:

- Servicio de reservaciones
- Servicio de horarios
- Servicio de clientes

Donde cada microservicio pueda implementar una arquitectura limpia (cebolla, hexagonal); lo que
permitiría que cada microservicio escale independientemente del otro y mejorando la ressilencia ante
fallos.
