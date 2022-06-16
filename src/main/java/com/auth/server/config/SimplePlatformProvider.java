package com.auth.server.config;

import org.keycloak.platform.PlatformProvider;
import org.keycloak.services.ServicesLogger;

import java.io.File;

public class SimplePlatformProvider implements PlatformProvider {

    Runnable runnableShutdownHook;
    @Override
    public void onStartup(Runnable runnable) {
         runnable.run();
    }

    @Override
    public void onShutdown(Runnable runnableShutdownHook) {
       this.runnableShutdownHook = runnableShutdownHook;
    }

    @Override
    public void exit(Throwable throwable) {
        ServicesLogger.LOGGER.fatal(throwable);
        exit(1);
    }

    @Override
    public File getTmpDirectory() {
        return new File(System.getProperty("java.io.tmpdir"));
    }
    private void exit(int status) {
        new Thread() {
            @Override
            public void run() {
                System.exit(status);
            }
        }.start();
    }

}
