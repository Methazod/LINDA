# LINDA

Este proyecto implementa un sistema de comunicación distribuida inspirado en el modelo de coordinación **LINDA**, utilizando el lenguaje de programación Java. Está diseñado para facilitar la interacción entre procesos distribuidos mediante un espacio de tuplas compartido.

## Características

- **Cliente (`Cliente.java`)**: Representa a los clientes que interactúan con el sistema, enviando y recibiendo mensajes a través del espacio de tuplas.
- **Servidor (`Servidor.java`)**: Gestiona el espacio de tuplas y coordina la comunicación entre los clientes.
- **Servidores Secundarios (`ServidorSecundario.java`)**: Manejan tareas específicas o cargas de trabajo distribuidas para mejorar la eficiencia del sistema.
- **Conexiones (`Conexion.java`)**: Gestiona la comunicación entre clientes y servidores.
- **Mensajes (`MensajeBusqueda.java` y `ResultadoBusqueda.java`)**: Definen la estructura de los mensajes utilizados para las búsquedas y los resultados en el espacio de tuplas.
- **Hilos (`ThreadCliente.java`, `ThreadServidor.java`, `ThreadServidorSecundario.java`)**: Manejan operaciones concurrentes en clientes y servidores para asegurar una comunicación eficiente.

## Estructura del Proyecto

El proyecto contiene los siguientes archivos principales:

- `Cliente.java`: Define la funcionalidad y comportamiento de los clientes en el sistema.
- `Servidor.java`: Implementa el servidor principal que gestiona el espacio de tuplas.
- `ServidorSecundario.java`: Implementa servidores secundarios para tareas especializadas.
- `Conexion.java`: Maneja las conexiones de red entre los diferentes componentes del sistema.
- `MensajeBusqueda.java`: Define la estructura de los mensajes de búsqueda en el espacio de tuplas.
- `ResultadoBusqueda.java`: Define la estructura de los mensajes de resultados de búsqueda.
- `MainCliente.java`: Clase principal para ejecutar un cliente.
- `MainServidor.java`: Clase principal para ejecutar el servidor principal.
- `MainServidorReplica.java`, `MainServidorSecundarioCortas.java`, `MainServidorSecundarioLargas.java`, `MainServidorSecundarioMedias.java`: Clases principales para ejecutar diferentes tipos de servidores secundarios.
- `ThreadCliente.java`: Maneja la comunicación en hilos separados para cada cliente.
- `ThreadServidor.java`: Maneja la comunicación en hilos separados en el servidor principal.
- `ThreadServidorSecundario.java`: Maneja la comunicación en hilos separados en los servidores secundarios.
- `config.properties`: Archivo de configuración que contiene parámetros como puertos y hosts utilizados por el sistema.

## Requisitos Previos

- **Java Development Kit (JDK)**: Asegúrate de tener instalado JDK 8 o superior en tu sistema.
- **Entorno de Ejecución**: Se recomienda utilizar un entorno de desarrollo integrado (IDE) como IntelliJ IDEA, Eclipse o NetBeans para facilitar la compilación y ejecución del proyecto.

## Cómo Ejecutar el Proyecto

1. **Clonar el repositorio**:   
   git clone https://github.com/Methazod/LINDA.git
   cd LINDA
2. **Configurar el archivo de propiedades**:
  Abre el archivo config.properties.
  Asegúrate de que los valores de host y puerto sean correctos y correspondan a tu configuración de red.
3. **Ejecutar el servidor principal**.
4. **Ejecutar servidores secundarios**.
5. **Ejecutar clientes**.

## Funcionamiento
El sistema se basa en el modelo de coordinación LINDA, donde los procesos (clientes) se comunican indirectamente a través de un espacio de tuplas administrado por el servidor principal. Los clientes pueden realizar operaciones como:

-  Escritura (out): Insertar una tupla en el espacio.
-  Lectura (rd): Leer una tupla que coincida con un patrón, sin eliminarla.
-  Toma (in): Leer y eliminar una tupla que coincida con un patrón.
Los servidores secundarios pueden encargarse de tareas específicas, como procesar ciertos tipos de tuplas o manejar cargas de trabajo particulares, mejorando así la eficiencia y escalabilidad del sistema.

## Contribuciones
Las contribuciones son bienvenidas. Si deseas mejorar este proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tu función o corrección:
  git checkout -b nombre-de-tu-rama
3. Realiza tus cambios y haz commits descriptivos.
4. Envía un pull request detallando tus modificaciones.
