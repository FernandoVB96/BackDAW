# CoDrive - Sistema de Reservas de Transporte

CoDrive es una API REST para gestionar reservas de viajes compartidos. Permite registrar usuarios, autenticar con JWT, crear y gestionar reservas, y manejar permisos según roles.

---

## Tecnologías

- Java 17+
- Spring Boot
- Spring Security con JWT
- JPA/Hibernate
- Lombok
- Base de datos relacional (MySQL, PostgreSQL, etc.)

---

## Endpoints Principales

### Autenticación y Registro (`/auth`)

- `POST /auth/registro`  
  Registra un usuario nuevo. Devuelve JWT.  
  Valida que el email no exista ya.  

- `POST /auth/login`  
  Login con email y contraseña. Devuelve JWT.

### Gestión de Reservas (`/reservas`)

- `POST /reservas`  
  Crea una reserva para un viaje y usuario autenticado.

- `PUT /reservas/{id}`  
  Actualiza una reserva existente.

- `DELETE /reservas/{id}`  
  Elimina una reserva.

- `GET /reservas/{id}`  
  Obtiene reserva por ID.

- `GET /reservas/usuario/{usuarioId}`  
  Obtiene todas las reservas de un usuario.

- `GET /reservas/mis-viajes/reservas`  
  Obtiene reservas de los viajes donde el usuario es conductor.

- `GET /reservas/viaje/{viajeId}`  
  Obtiene todas las reservas de un viaje específico.

- `POST /reservas/{id}/confirmar`  
  Confirma una reserva.

- `POST /reservas/{id}/cancelar`  
  Cancela una reserva.

---

## Seguridad y Autenticación

### Autenticación con JWT

- Los endpoints bajo `/auth/**` son públicos.  
- El resto requiere token JWT válido en el header `Authorization: Bearer <token>`.  
- El token contiene el email y rol del usuario.  
- Se valida con `JwtAuthFilter` en cada petición.

### Configuración de Spring Security

- Seguridad stateless, no hay sesión en servidor.  
- CSRF deshabilitado porque usamos JWT.  
- CORS configurado para aceptar peticiones desde cualquier origen.  
- Contraseñas guardadas con BCrypt.

### Servicio de Usuarios para Seguridad

- `UserDetailsServiceImpl` carga usuario por email.  
- Traduce rol de la base de datos a `ROLE_<ROL>`, para que Spring Security entienda.

---

## Manejo de Errores Global

- Excepciones personalizadas como `EmailAlreadyExistsException` devuelven errores claros y códigos HTTP adecuados.  
- Credenciales inválidas responden con 401.  
- Usuarios no encontrados con 404.  
- Errores no controlados retornan 500 con mensaje genérico.

---

## Ejemplo de Uso

1. Registro:

```bash
POST /auth/registro
Content-Type: application/json

{
  "nombre": "Usuario",
  "email": "usuario@example.com",
  "password": "secreto123"
}
```

2. Login:

```bash
POST /auth/login
Content-Type: application/json

{
  "email": "usuario@example.com",
  "password": "secreto123"
}
```

Respuesta: 
```bash
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
### Servicio de Usuarios para Seguridad
1. Configura tu base de datos y cambia las propiedades en application.properties:ç

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/codrive
spring.datasource.username=root
spring.datasource.password=tu_password
jwt.secret=TuClaveSuperSecretaBase64==
```

2. Ejecuta la aplicación:

```bash
./mvnw spring-boot:run
```


