<p align="center">
  <h1>📱 LG No FlagSecure</h1>
  <p>Bypass global de <code>FLAG_SECURE</code> y políticas MDM en dispositivos LG.</p>
</p>

<div align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/LSPosed-000000?style=flat&logo=xda-developers&logoColor=white"/>
</div>

---

## ¿Por qué?

Los módulos genéricos fallan en LG porque su capa del fabricante añade una restricción extra en `Mdm.getInstance().hasScreenCapturePolicy`. Este módulo hookea `com.android.server.wm.WindowState.isSecureLocked()` por reflexión para forzar siempre `false`.

## Requisitos

- Magisk / KernelSU / APatch
- LSPosed activo

## Instalación

1. Descarga el APK desde [Releases](../../releases)
2. Activa el módulo en LSPosed con alcance **Sistema Android** (`android`)
3. Reinicia

## ⚠️ Limitación

No funciona con **DRM de hardware (Widevine L1)** — Netflix, Prime, etc. El renderizado va aislado en GPU.
