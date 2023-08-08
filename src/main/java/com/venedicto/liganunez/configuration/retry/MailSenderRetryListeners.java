package com.venedicto.liganunez.configuration.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.retry.support.Args;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.exception.MailSenderException;
import com.venedicto.liganunez.model.MailTypes;

@Component
public class MailSenderRetryListeners extends RetryListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(MailSenderRetryListeners.class);
	
    @Override
    public <T, E extends Throwable> void close(RetryContext context,
      RetryCallback<T, E> callback, Throwable throwable) {
        if(throwable != null) {
        	log.trace("[Mail Sender] Conexión abortada.. generando rollback");
        	
        	Args args = (Args) context.getAttribute("ARGS");
        	MailTypes mailType = (MailTypes) args.getArgs()[1];
        	
        	MailSenderException e = ((MailSenderException) throwable);
        	e.getRollbacker(mailType).rollback(args.getArgs());
        }
        
        log.trace("[Mail Sender] Consulta finalizada");
        super.close(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context,
      RetryCallback<T, E> callback, Throwable throwable) {
        log.trace("[Mail Sender] No se pudo obtener respuesta para la consulta.. [Intento: " + context.getRetryCount() + "]"); 
        super.onError(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context,
      RetryCallback<T, E> callback) {
        log.trace("[Mail Sender] Iniciando consulta");
        return super.open(context, callback);
    }
}