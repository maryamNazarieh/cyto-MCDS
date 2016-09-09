package org.cytoscape.MCDS.MCDS.internal;

import java.util.Properties;

import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.osgi.framework.BundleContext;
/**
 * MCDS App
 * @author Thorsten Will
 */
public class MCDSActivator extends AbstractCyActivator {

	private BundleContext context;
	private CyServiceRegistrar registrar;
	
	public MCDSActivator() {
		super();
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
		this.registrar = this.getService(CyServiceRegistrar.class);
		
		// start actual application
		registerService(new MCDSApplication(this), MCDSApplication.class);
	}
	
	 /*
	  * service-related functions
	  */
	
	public <S> S getService(Class<S> cls) {
		return this.getService(this.context, cls);
	}
	
	public <S> S getService(Class<S> cls, String properties) {
		return this.getService(this.context, cls, properties);
	}
	
	public <S> void registerService(S obj, Class<S> cls) {
		registrar.registerService(obj, cls, new Properties());
	}
	
	public void registerService(Object obj, Class<?> cls, Properties properties) {
		registrar.registerService(obj, cls, properties);
	}
	
	public <S> void unregisterService(S obj, Class<S> cls) {
		registrar.unregisterService(obj, cls);
	}
	
	public <S> void unregisterAllServices(S obj) {
		registrar.unregisterAllServices(obj);
	}
}
