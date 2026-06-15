# LG No FlagSecure (LSPosed Module)

<p align="center">
  <img src="https://shields.io" alt="Android" />
  <img src="https://shields.io" alt="Java" />
  <img src="https://shields.io" alt="Arch Linux" />
  <img src="https://shields.io" alt="LSPosed" />
</p>

---

Un módulo de **LSPosed / Xposed** personalizado diseñado específicamente para dispositivos **LG**. Desactiva por completo la restricción `FLAG_SECURE` a nivel de Framework del Sistema, evadiendo los bloqueos de capturas y grabaciones de pantalla impuestos por el fabricante de forma global.

## 🕵️‍♂️ El Problema con los Dispositivos LG
Los módulos genéricos de Xposed suelen fallar en los teléfonos LG debido a que la capa de personalización del fabricante no solo valida el bit clásico de la ventana (`WindowManager.LayoutParams.FLAG_SECURE`), sino que integró una restricción personalizada basada en su propio sistema **MDM (Mobile Device Management)**. 

Al analizar el archivo central `services.jar` del sistema, localizamos que el método de bloqueo se estructuraba de la siguiente manera:

```java
boolean isSecureLocked() {
    if ((this.mAttrs.flags & 8192) != 0 || !DevicePolicyCache.getInstance().isScreenCaptureAllowed(this.mShowUserId)) {
        return true;
    }
    if (Mdm.getInstance() != null) {
        // 🔒 Validación del sistema MDM exclusivo de LG
        String packageName = null;
        if (!isChildWindow() || getOwningPackage() != null) {
            packageName = getOwningPackage();
        }
        return !Mdm.getInstance().hasScreenCapturePolicy(packageName);
    }
    return false;
}
```

Cualquier validación positiva de `DevicePolicyCache` o del MDM propietario de LG (`Mdm.getInstance().hasScreenCapturePolicy`) forzaba un retorno `true`, manteniendo la pantalla en negro a pesar de haber removido los flags tradicionales de la aplicación objetivo.

## 🛠️ La Solución
Este módulo intercepta en caliente mediante **Reflexión en Java** el método `isSecureLocked()` de la clase interna del sistema `com.android.server.wm.WindowState` y anula por completo sus flujos internos, obligándolo a retornar siempre `false` (Ventana No Segura / Permitir Captura de forma permanente). 

Al actuar sobre el framework del núcleo de Android, el bypass se aplica de manera transparente a todo el sistema operativo.

## 🚀 Requisitos e Instalación
* 🔓 **Root:** Acceso mediante Magisk, KernelSU o APatch.
* 🧩 **Framework:** Tener instalado LSPosed 
* 📥 **Instalación:** Descarga el binario desde la sección de [Releases](../../releases) o compila el proyecto desde Android Studio.
* ⚙️ **Configuración:** Abre la interfaz de LSPosed, activa el módulo y **marca obligatoriamente el alcance (Scope) en "Sistema Android"** (`android`).
* 🔄 **Reinicio:** Realiza un reinicio completo del terminal para cargar el parche en la memoria RAM del sistema.

## ⚠️ Limitaciones Conocidas
Este módulo **no puede** saltarse las restricciones de aplicaciones que utilicen protección por **DRM de hardware (como Widevine L1)** para la reproducción de contenido protegido (Netflix, Amazon Prime, etc.). Dicho renderizado se procesa en una capa aislada del chip gráfico (GPU) totalmente inaccesible para el sistema operativo Android.

---
