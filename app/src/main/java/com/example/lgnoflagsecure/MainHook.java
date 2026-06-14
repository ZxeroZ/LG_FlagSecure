package com.example.lgnoflagsecure;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!"android".equals(lpparam.packageName)) {
            return;
        }

        try {
            // Buscamos la clase exacta de la ventana que extrajimos de tu LG
            Class<?> windowStateClass = XposedHelpers.findClass(
                    "com.android.server.wm.WindowState",
                    lpparam.classLoader
            );

            XposedHelpers.findAndHookMethod(
                    windowStateClass,
                    "isSecureLocked",
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            return false;
                        }
                    }
            );

            XposedBridge.log("¡Parche anti-FLAG_SECURE de LG aplicado con éxito en WindowState!");

        } catch (Throwable t) {
            XposedBridge.log("Error al aplicar el parche en WindowState de LG: " + t.getMessage());
        }
    }
}
