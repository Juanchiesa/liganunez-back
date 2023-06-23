package com.venedicto.liganunez.configuration.retry;

public interface Rollbacker {
	public void rollback(Object[] parameters);
}