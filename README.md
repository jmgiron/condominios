# ResiSync (CondoApp)

Plataforma unificada para la gestión de accesos residenciales y control de visitantes a través de tecnología QR, notificaciones simuladas por WhatsApp y alertas nativas en la App.

## 🧱 Arquitectura del Proyecto
- **Backend**: Spring Boot 3.4 (Java 17), Spring Security JWT, WebSockets (Alertas), Swagger OpenAPI, ZXing (QR codes).
- **Frontend Móvil**: Flutter (Dart) con State Management por Provider, y networking por Dio.
- **Base de datos**: PostgreSQL 15.

## 📂 Organización de Carpetas
- `backend/`: Proyecto integro servidor REST API en Spring Boot, compilable con Maven.
- `flutter_app_source/`: Archivos e Interfaces visuales y lógica core (`pubspec.yaml`, carpeta `lib/`).
- `docker-compose.yml`: Archivo para el contenedor de PostgreSQL.
- `schema.sql`: Script DDL de datos relacionales básicos.

## 🚀 Guía de Despliegue Rápido (Local)

### 1. Base de Datos
Desde la carpeta raíz, levanta el contenedor de la BD:
```bash
docker-compose up -d
```
*(Spring Boot se encargará de mapear las tablas automáticamente al conectarse).*

### 2. Backend (Spring Boot)
Entra a la carpeta de backend y arranca la aplicación:
```bash
cd backend
./mvnw clean spring-boot:run
mvnw.cmd clean spring-boot:run
```
Una vez corriendo, puedes acceder la documentación con interfaces para probar el API:
**Swagger UI:** [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

### 3. Frontend de ResiSync (Flutter)
Debido a que el core de Flutter estaba bloqueado en tu ordenador al momento de generación por el instalador en caché (`engine.realm`), entregamos el código fuente de los elementos en `flutter_app_source`. Sigue estos pasos para integrarlo:

1. Crea un proyecto limpio en tu terminal (o IDE):
   ```bash
   flutter create resisync_mobile
   ```
2. Reemplaza las dependencias de archivo copiando el `pubspec.yaml` que dejamos en `flutter_app_source/pubspec.yaml`
3. Borra la carpeta `lib` generada y copia toda la carpeta `flutter_app_source/lib` a la raíz de tu proyecto `resisync_mobile`.
4. Descarga los paquetes y compila:
   ```bash
   flutter pub get
   flutter run
   ```

## 📦 Compilación móvil final a Dispositivo

**Para Android (.apk)** (Directorio `resisync_mobile`):
```bash
flutter build apk --release
```
La aplicación se guardará en `build/app/outputs/flutter-apk/app-release.apk`

**Para iOS (.ipa)** (Requisito: macOS, Xcode):
```bash
flutter build ipa --release
```
Sigue el procedimiento normal en Xcode Archive para firmar la App con tu certificado de Apple Developer.
