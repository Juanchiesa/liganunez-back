package com.venedicto.liganunez.configuration.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class DbRetryListeners extends RetryListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(DbRetryListeners.class);

    @Override
    public <T, E extends Throwable> void close(RetryContext context,
      RetryCallback<T, E> callback, Throwable throwable) {
        log.trace("[DB] Consulta finalizada");
        super.close(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context,
      RetryCallback<T, E> callback, Throwable throwable) {
        log.trace("[DB] No se pudo obtener respuesta para la consulta.. [Intento: " + context.getRetryCount() + "]"); 
        super.onError(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context,
      RetryCallback<T, E> callback) {
        log.trace("[DB] Iniciando consulta");
        return super.open(context, callback);
    }
}