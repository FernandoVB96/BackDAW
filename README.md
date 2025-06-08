
# CoDrive - Sistema de Reservas de Transporte Compartido

**Ciclo:** Desarrollo de Aplicaciones Web (DAW)  
**Autor:** Fernando Vaquero Buzon

---

## Índice

- [Introducción](#introducción)
- [Funcionalidades](#funcionalidades)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Guía de instalación](#guía-de-instalación)
- [Guía de uso](#guía-de-uso)
- [Documentación de la API](#documentación-de-la-api)
- [Conclusión](#conclusión)
- [Contribuciones y agradecimientos](#contribuciones-y-agradecimientos)
- [Licencia](#licencia)
- [Contacto](#contacto)

---

## Introducción

CoDrive es una API REST y aplicación móvil para la gestión de viajes compartidos.  
Permite a usuarios registrarse, autenticarse, crear viajes, reservar plazas y gestionar sus vehículos.

### Justificación y Motivación

Reducir el impacto medioambiental y económico del transporte promoviendo el uso compartido de vehículos.

### Objetivos

- Permitir publicación de viajes.
- Facilitar la reserva y confirmación/cancelación.
- Gestionar roles: usuario y conductor.

---

## Funcionalidades

- Registro y autenticación con JWT.
- Creación y búsqueda de viajes.
- Reserva, confirmación y cancelación de plazas.
- Gestión de vehículos por parte de conductores.
- Panel de perfil del usuario.

---

## Tecnologías utilizadas

- **Backend:** Java 17+, Spring Boot, JPA, Spring Security, JWT, MySQL.
- **Frontend:** React Native con TypeScript.
- **Otros:** Axios, Fetch API, Context API.

---

## Guía de instalación

1. Configura `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/codrive
spring.datasource.username=root
spring.datasource.password=tu_password
jwt.secret=TuClaveSuperSecretaBase64==
```

2. Ejecuta la app:

```bash
./mvnw spring-boot:run
```

---

## Guía de uso

### Registro y Login

```bash
POST /auth/registro
POST /auth/login
```

### Crear un viaje (desde app)

```ts
fetch("https://codrive-9fbg.onrender.com/viajes", {
  method: "POST",
  headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
  body: JSON.stringify({ origen, destino, fechaHoraSalida, fechaHoraLlegada, plazasTotales }),
});
```

### Buscar viajes

```ts
GET /viajes/disponibles?origen=...&destino=...
```

### Crear reserva

```ts
POST /reservas
```

### Confirmar/Cancelar reserva

```ts
POST /reservas/:id/confirmar
POST /reservas/:id/cancelar
```

### Consultar perfil y vehículos

```ts
GET /usuarios/mi-perfil
GET /vehiculos/conductor/:id
```

### Crear / Editar / Eliminar vehículo

```ts
POST /vehiculos
PUT /vehiculos/:id
DELETE /vehiculos/:id
```

---

## Conclusión

CoDrive demuestra cómo aplicar Spring Security, JWT y arquitectura RESTful para una app moderna.

---

## Contribuciones y agradecimientos

- Agradecimientos al profesorado.
- Colaboraciones abiertas vía pull requests.

---

## Licencia

MIT License.

---

## Contacto

Fernando Vaquero Buzon - fernandovaquero96@gmail.com  
GitHub: [FernandoVB96](https://github.com/FernandoVB96)
