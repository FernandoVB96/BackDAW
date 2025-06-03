# CoDrive - Sistema de Gestión de Viajes Compartidos

CoDrive es una aplicación backend construida en **Java con Spring Boot** para la gestión de viajes compartidos. Permite a conductores crear viajes, gestionar vehículos, y a usuarios unirse a viajes, dejar valoraciones y reservar plazas. Está diseñada pensando en un flujo claro, con roles y seguridad básicos.

---

## Estructura del Proyecto

### Modelos Principales (`/persistance/models`)

- **Usuario:** Representa a un usuario del sistema, con roles (`ADMIN`, `USUARIO`, `CONDUCTOR`), datos personales y credenciales (password oculto en respuestas).
- **Rol:** Enum para los tipos de usuario, con métodos útiles para validar permisos.
- **Vehiculo:** Vehículos asociados a conductores, con datos básicos y plazas disponibles.
- **Viaje:** Viajes creados por conductores, con origen, destino, fechas, plazas totales y disponibles, además de la lista de pasajeros.
- **Reserva:** Reserva de plazas en viajes, con estados (`PENDIENTE`, `CONFIRMADA`, `CANCELADA`) y fechas.
- **Valoracion:** Valoraciones que usuarios hacen a conductores, con puntuación, comentario y fechas.

---

## Endpoints Principales (`/controller`)

### ValoracionController (`/valoraciones`)

- **POST /** Crear una valoración.
- **GET /conductor/{conductorId}** Listar valoraciones para un conductor.
- **GET /usuario/{usuarioId}** Listar valoraciones hechas por un usuario.
- **GET /conductor/{conductorId}/media** Calcular la valoración media de un conductor.

### VehiculoController (`/vehiculos`)

- **POST /** Agregar un vehículo.
- **GET /{id}** Obtener vehículo por ID.
- **DELETE /{id}** Eliminar vehículo.
- **GET /conductor/{conductorId}** Listar vehículos de un conductor.
- **PUT /{id}** Actualizar vehículo.

### ViajeController (`/viajes`)

- **POST /** Crear un nuevo viaje (solo conductores).
- **GET /disponibles** Listar viajes con plazas disponibles (filtrado por origen, destino y plazas mínimas).
- **GET /mis-viajes** Viajes del usuario autenticado.
- **DELETE /{viajeId}** Cancelar un viaje (solo conductor).
- **POST /{viajeId}/unirse** Unirse a un viaje.
- **POST /{viajeId}/abandonar** Abandonar un viaje.
- **GET /{viajeId}/pasajeros** Listar pasajeros de un viaje.

---

## Tecnologías y Dependencias

- Java 17+ (o compatible)
- Spring Boot (Web, Validation, Security)
- Lombok (para evitar boilerplate)
- Jakarta Persistence API (JPA/Hibernate)
- Validaciones mediante `jakarta.validation.constraints`
- JSON manejo con Jackson

---

## Requisitos y Consideraciones

- **Roles:** Solo usuarios con rol `CONDUCTOR` pueden crear viajes.
- **Validaciones:** Se aplican restricciones en entidades para garantizar datos válidos (fechas futuras, rangos de puntuación, formatos).
- **Seguridad:** Se asume que Spring Security está configurado para manejar autenticación y roles.
- **Relaciones:** Uso de `@ManyToOne`, `@ManyToMany`, etc., para relacionar usuarios, viajes y vehículos.
- **Carga perezosa:** Se usa `FetchType.LAZY` para evitar cargar datos innecesarios.

---

## Cómo arrancar el proyecto

1. Clonar el repositorio.
2. Configurar la base de datos en `application.properties` o `application.yml`.
3. Ejecutar la aplicación con `mvn spring-boot:run` o desde tu IDE favorito.
4. Probar los endpoints con Postman o similar.

---

## Ideas para Mejorar

- Implementar lógica en servicios para gestión de plazas y reservas (evitar sobreventa).
- Añadir control de excepciones personalizadas para errores comunes.
- Incorporar tests unitarios e integración.
- Mejorar seguridad con JWT y cifrado de passwords.
- Optimizar consultas para evitar N+1 problem.

---

## Contacto

Si necesitas ayuda o quieres aportar, me encuentras bajo el alias **Kazurro**.
