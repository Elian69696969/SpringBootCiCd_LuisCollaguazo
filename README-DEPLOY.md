# Despliegue en Render

## Pasos para subir el proyecto a Render

### 1. Preparar el Repositorio
Asegúrate de que todos los cambios estén commitados:
```bash
git add .
git commit -m "Configuración para despliegue en Render"
git push origin main
```

### 2. Crear Cuenta en Render
- Ve a [render.com](https://render.com)
- Crea una cuenta usando GitHub
- Conecta tu repositorio de GitHub

### 3. Crear Nuevo Web Service
1. En el dashboard de Render, haz clic en "New +"
2. Selecciona "Web Service"
3. Conecta tu repositorio `spring-lab_Cs`
4. Render detectará automáticamente el archivo `render.yaml`

### 4. Configuración Automática
El archivo `render.yaml` creará automáticamente:
- **Web Service**: Tu aplicación Spring Boot
- **Base de datos PostgreSQL**: Para datos persistentes
- **Variables de entorno**: Configuración de producción

### 5. Variables de Entorno Configuradas
- `DATABASE_URL`: URL de la base de datos PostgreSQL
- `DB_USERNAME`: Usuario de la base de datos
- `DB_PASSWORD`: Contraseña de la base de datos
- `JWT_SECRET`: Secreto para tokens JWT (generado automáticamente)
- `SPRING_PROFILES_ACTIVE`: Perfil de producción

### 6. Proceso de Despliegue
Render ejecutará automáticamente:
```bash
./gradlew build -x test
java -jar build/libs/*.jar
```

### 7. Verificación
- El health check está configurado en `/actuator/health`
- La aplicación estará disponible en la URL proporcionada por Render
- Los logs estarán disponibles en el dashboard de Render

## Configuraciones Realizadas

### Archivos Creados/Modificados:
1. **`render.yaml`**: Configuración del servicio en Render
2. **`application-prod.yml`**: Configuración para producción
3. **`build.gradle`**: Agregadas dependencias de PostgreSQL y Actuator

### Cambios Importantes:
- **Base de datos**: MySQL local → PostgreSQL en producción
- **Health checks**: Endpoint `/actuator/health` configurado
- **Logs**: Desactivados en producción
- **DevTools**: Desactivados en producción

## URLs Importantes
- **API**: `https://tu-app.onrender.com/api/`
- **Health**: `https://tu-app.onrender.com/actuator/health`
- **Login**: `https://tu-app.onrender.com/api/auth/login`

## Notas
- El plan gratuito de Render tiene limitaciones (15 min inactividad)
- La base de datos PostgreSQL también es gratuita con limitaciones
- Los cambios en el repositorio git activarán nuevo despliegue automático
