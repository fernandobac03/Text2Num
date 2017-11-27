package com.mkyong.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import ec.edu.ucuenca.cepraXI.webservices.RestServices;

public class MessageApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public MessageApplication() {
		singletons.add(new RestServices());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
