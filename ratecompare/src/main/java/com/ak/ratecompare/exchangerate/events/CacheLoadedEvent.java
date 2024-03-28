package com.ak.ratecompare.exchangerate.events;

import org.springframework.context.ApplicationEvent;

public class CacheLoadedEvent extends ApplicationEvent{

	public CacheLoadedEvent(Object source) {
		super(source);
	}
}
