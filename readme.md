<p align="center">
  <h1>📱 LG No FlagSecure</h1>
  <h3>Bypass global de FLAG_SECURE y políticas MDM de ventanas en dispositivos LG.</h3>
</p>

<p align="center">
  <img src="https://shields.io" />
  <img src="https://shields.io" />
  <img src="https://shields.io" />
  <img src="https://shields.io" />
</p>

---

### 🔍 ¿Por qué este módulo?
Los módulos genéricos fallan en LG porque la capa del fabricante incluye una restricción extra basada en su propio **MDM** (`Mdm.getInstance().hasScreenCapturePolicy`). Este módulo inyecta un hook por reflexión en `com.android.server.wm.WindowState.isSecureLocked()` para obligarlo a retornar siempre `false`.

### 🚀 Requisitos e Instalación
* **Root:** Magisk, KernelSU o APatch.
* **Framework:** LSPosed instalado y activo.
* **Instalación:** Descarga el APK desde la pestaña de [Releases](../../releases).
* **Configuración:** Activa el módulo en LSPosed y selecciona obligatoriamente el alcance **Sistema Android** (`android`).
* **Paso final:** Reinicia el dispositivo.

### ⚠️ Limitación
No funciona en aplicaciones que utilicen protección por **DRM de hardware (Widevine L1)** (Netflix, Amazon Prime, etc.), ya que el renderizado se procesa de forma aislada en la GPU.
