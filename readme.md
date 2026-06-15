# LG No FlagSecure (LSPosed Module)

Un módulo de LSPosed/Xposed personalizado diseñado específicamente para dispositivos LG que desactiva por completo la restricción `FLAG_SECURE` a nivel de Framework del Sistema, evadiendo los bloqueos de capturas y grabaciones de pantalla impuestos por el fabricante.

## El Problema con los Dispositivos LG
Los módulos genéricos de Xposed suelen fallar en los teléfonos LG porque el fabricante no solo valida el bit clásico de la ventana (`WindowManager.LayoutParams.FLAG_SECURE`), sino que integró una capa de seguridad personalizada basada en su propio sistema **MDM (Mobile Device Management)**. 

Al descompilar el archivo central `services.jar` del sistema, localizamos que el método de bloqueo se estructuraba de la siguiente manera:

```java
boolean isSecureLocked() {
    if ((this.mAttrs.flags & 8192) != 0 || !DevicePolicyCache.getInstance().isScreenCaptureAllowed(this.mShowUserId)) {
        return true;
    }
    if (Mdm.getInstance() != null) {
        // Validación del sistema MDM exclusivo de LG
        String packageName = null;
        if (!isChildWindow() || getOwningPackage() != null) {
            packageName = getOwningPackage();
        }
        return !Mdm.getInstance().hasScreenCapturePolicy(packageName);
    }
    return false;
}
```

Cualquier validación positiva de `DevicePolicyCache` o del MDM propietario de LG (`Mdm.getInstance().hasScreenCapturePolicy`) forzaba un retorno `true`, manteniendo la pantalla en negro a pesar de haber removido los flags tradicionales de la aplicación.

## 🛠️ La Solución
Este módulo intercepta en caliente mediante reflexión el método `isSecureLocked()` de la clase interna del sistema `com.android.server.wm.WindowState` y anula por completo sus flujos internos, obligándolo a retornar siempre `false` (Ventana No Segura/Permitir Captura). Al actuar sobre el framework central, el bypass se aplica de manera global y transparente.

## 🚀 Requisitos e Instalación
1. Dispositivo LG con acceso **Root** (Magisk / KernelSU / APatch).
2. Tener instalado el Framework **LSPosed** (o variantes modernas compatibles).
3. Compilar el APK desde Android Studio o descargar el binario desde los lanzamientos (Releases).
4. Instalar el APK en el dispositivo.
5. Abrir el administrador de LSPosed, activar el módulo y **marcar obligatoriamente el alcance (Scope) en "Sistema Android"** (`android`).
6. Reiniciar el dispositivo para aplicar los cambios en la memoria RAM del sistema.

## ⚠️ Limitaciones Conocidas
Este módulo no puede saltarse las restricciones de aplicaciones que utilicen protección por **DRM de hardware (como Widevine L1)** para reproducción de video (Netflix, Amazon Prime, etc.), ya que dicho renderizado se procesa en una capa aislada del procesador gráfico inaccesible para el sistema operativo Android.
