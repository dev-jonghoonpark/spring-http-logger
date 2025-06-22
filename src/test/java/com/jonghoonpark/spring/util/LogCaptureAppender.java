package com.jonghoonpark.spring.util;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class LogCaptureAppender extends AppenderBase<ILoggingEvent> {

	private final List<ILoggingEvent> logEvents = new ArrayList<>();

	@Override
	protected void append(ILoggingEvent eventObject) {
		this.logEvents.add(eventObject);
	}

	public List<ILoggingEvent> getLogEvents() {
		return this.logEvents;
	}

	public void clear() {
		this.logEvents.clear();
	}

}
