![imagen](https://github.com/Null-Cat0/Xmonad_Dotfiles/assets/91385511/d9e85853-ae4e-43ab-bb15-1c0a01f8ce89)

![image](https://github.com/Null-Cat0/Booking/assets/91385511/6e119caa-5fe6-4f64-9138-a35727d31a0b)


# Booking
Proyecto de la asignatura de Programación en Internet
## Entrega 1

Requisitos funcionales:

1.- Inicio de sesión o creación de una cuenta (1,25 puntos). El proyecto debe incluir una
página desde la que un usuario existente en el sistema pueda iniciar sesión en la aplicación.
Esa misma página (u otra diferente si así se estima) podrá permitir el registro de un nuevo
usuario en la aplicación web a través de un correo electrónico y una contraseña robusta (que
al menos contendrá 8 dígitos entre los que se incluye un número, una mayúscula, una
minúscula y un carácter alfanumérico). Adicionalmente, se deben facilitar nombre y apellidos.
En la siguiente imagen se puede observar un ejemplo de la página de inicio de sesión (es
meramente orientativo).

2 .- Búsqueda por dirección y listado de categorías (1,25 puntos). Debe existir una página
que permita introducir una dirección para buscar alojamientos que den ese servicio en la
localización detallada. Adicionalmente, esta página debe mostrar el listado de categorías con
los tipos de alojamiento más destacados. Se muestra a continuación un ejemplo de página
para que sirva como referencia.

3 .- Listado de alojamientos resultantes de una búsqueda (1,25 puntos). El resultado de
una búsqueda de alojamientos por localidad o dirección debe ofrecer un listado con los
alojamientos coincidentes con dicha localidad. Para cada alojamiento debe aparecer su
nombre, categoría (estrellas o similar), valoración media, tipos de alojamientos ofrecidos
(habitaciones), distancia al centro y el precio por noche. Además, debe aparecer algún menú
o listado de características para poder filtrar según diferentes criterios (categoría, estrellas,
instalaciones o servicios, ...).


4 .- Página con detalle de un alojamiento (1,25 puntos). Al hacer click en uno de los
alojamientos disponibles, debe aparecer una página con los detalles del mismo. El
alojamiento debe incluir, al menos, la siguiente información:

- Nombre alojamiento.
- Dirección.
- Teléfono.
- Distancia al centro.
- Valoración media.
- Descripción.
- Servicios o instalaciones ofrecidos.
- Permite mascotas: sí/no.

Además, debe aparecer un listado con los tipos de alojamiento ofrecidos por el
establecimiento. Estos tipos de alojamientos podrán tener distintos precios y características.
Para cada uno de ellos se debe mostrar, al menos, el nombre, una descripción y el precio.

5 .- Página para edición de un alojamiento (1,25 puntos). Se debe diseñar una página para
la edición de los datos de un alojamiento. Esta página mostrará los campos para editar la
información disponible para el alojamiento, así como la forma de añadir tipos de alojamientos
(habitaciones) ofrecidos por el mismo.

Para la edición de la información básica del alojamiento se deben usar los siguientes tipos de
campos:
- Nombre alojamiento. text
- Dirección: text
- Teléfono: telephone
- Distancia al centro: number.
- Valoración media: number (float)
- Descripción: text
- Servicios o instalaciones ofrecidos: checkbox.
- Permite mascotas: radio button

6 .- Carrito de la compra (1,25 puntos). Debe existir una página en la que el usuario pueda
confirmar sus reservas. Para ello, podrá incluir en el carrito de la compra todas las
habitaciones que considere de los diferentes alojamientos existentes en la web y una vez en
el carrito podrá eliminarlas o confirmar la reserva de las mismas. A continuación se muestra
una página de ejemplo de confirmación de reservas. Aunque en esta página se muestra un
formulario para introducir los datos personales y de contacto de la reserva, este formulario
puede ser obviado si se consideran como datos personales para la reserva los datos del
usuario que ha iniciado sesión en la aplicación.

7 .- Mis reservas (1,25 puntos): Una vez el usuario ha confirmado una o más reservas,
existirá una página donde éste podrá ver el listado o histórico de reservas que ha llevado a
cabo en la web.

8 .- Página Trending destinations (1,25 puntos): Debe existir una página donde se
recomienden diferentes alojamientos en función de distintos criterios, por ejemplo, destinos
más populares o categorías más demandadas. Además, esta página debe mostrar también
una estadística del sitio que permita visualizar el número de alojamientos en los destinos más
populares, número de habitaciones ofrecidas en total en el sistema, etc. Se deja a libertad del
estudiante el diseño y elección de esta página para que pueda añadir otras funcionalidades
que considere.

## Entrega 2

Requisitos funcionales:

1.- Registro y acceso de usuarios: Cualquier persona puede registrarse como usuario
de la aplicación web a través de un correo electrónico y una contraseña robusta (que
al menos contendrá 8 dígitos entre los que se incluye un número, una mayúscula, una
minúscula y un carácter especial). Adicionalmente se podrán añadir otros datos como
nombre, apellidos y otros datos que se consideren necesarios. Tras realizar el alta,
cada usuario podrá editar su información y eliminar su cuenta en cualquier momento.
Para cumplir satisfactoriamente con este requisito el usuario debe ver la información
previamente cargada de su perfil cuando lo está editando, de modo que no sea
necesario volver a rellenar un campo que ya contenga información.

2.- Gestión de alojamientos: Cualquier usuario puede crear, ver, editar y eliminar un
alojamiento que contenga, al menos (se deben respetar los tipos de campos):

- Nombre alojamiento. text
- Dirección: text
- Teléfono: telephone
- Distancia al centro: number.
- Valoración media: number (float)
- Descripción: text
- Servicios o instalaciones ofrecidos: checkbox.
- Permite mascotas: radio button

Se pueden añadir otros campos que se consideren necesarios.
Para cumplir satisfactoriamente con este requisito cuando el usuario propietario del
alojamiento esté editando el mismo debe ver la información previamente
cargada y almacenada en el sistema cuando el alojamiento se introdujo. Además, el 
usuario propietario del alojamiento tendrá la opción de eliminar el mismo de la
aplicación cuando lo estime oportuno.


3.- Gestión de habitaciones ofrecidas por un alojamiento: el usuario dueño de un
alojamiento podrá gestionar la lista de habitaciones que ese alojamiento ofrece a los
clientes. Por cada habitación, al menos, se dispondrá de la siguiente información:
- Nombre de la habitación.
- Descripción.
- Precio/noche.
- Número de habitaciones disponibles.

4.- Gestión de reservas: Cualquier usuario puede realizar una reserva de alguna
habitación de un alojamiento. Como resultado de esta acción, el usuario dispondrá de
un registro/histórico de las reservas realizadas. Cabe destacar que un usuario podrá
realizar reservas de cualquier alojamiento, incluidos aquellos de los que es propietario.

5.- Valoración: Cada alojamiento podrá recoger una valoración por parte de los usuarios
que hace referencia al grado de satisfacción del cliente con el mismo. Esta valoración
tendrá un rango de 1 a 5. La valoración podrá ir acompañada de un comentario. En
las valoraciones de un alojamiento cada valoración debe mostrar el usuario que la ha
realizado. Los usuarios solo pueden hacer una valoración por alojamiento y siempre
que no sea el propietario del mismo. El sistema debe proporcionar una opción para
mostrar los alojamientos ordenados por su valoración media.

6.- Alojamientos favoritos: los usuarios podrán tener un conjunto de alojamientos
favoritos. En este caso, en su perfil aparecerá el listado de alojamientos favoritos. Un
mismo usuario no puede añadir dos veces el mismo alojamiento a su lista de favoritos.
Sí podrá quitar un alojamiento de sus favoritos. En el listado de favoritos debe
aparecer al menos el nombre del alojamiento.

7.- Buscar alojamiento: un usuario puede buscar un alojamiento concreto por su
localidad o escribiendo las palabras relativas al título o su descripción.


8.- Completo/disponible: un usuario puede cambiar el estado de sus alojamientos en
función de su grado de ocupación para indicar que el mismo no acepta más reservas
y no se encuentra disponible. La aplicación debe permitir a los usuarios filtrar los
alojamientos para mostrar solo aquellos que aceptan reservas (es decir, ocultar los
que no aceptan reservas). De este modo la aplicación contará con tres apartados:
- Todos: donde aparecen todos los alojamientos.
- Con disponibilidad: solo los alojamientos que aceptan reservas.
- Sin disponibilidad: solo los alojamientos que no aceptan reservas.


## Entrega 3

REQUISITOS FUNCIONALES (implementados mediante REST + AngularJS):
Mismos requisitos que los especificados para la Actividad 2 del proyecto.














