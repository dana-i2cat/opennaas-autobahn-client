package net.i2cat.dana.autobahn.client;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.administration.Administration;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.i2cat.dana.autobahn.client.security.WSSecurity;

/**
 * 
 * @author Isart Canyameres Gimenez (i2cat)
 * 
 */
public class AutobahnClientFactory implements IAutobahnClientFactory {

	private final static QName	USER_ACCESS_POINT_SERVICE	= new QName("http://useraccesspoint.autobahn.geant.net/",
																	"UserAccessPointService");
	private final static QName	USER_ACCESS_POINT_PORT		= new QName("http://useraccesspoint.autobahn.geant.net/",
																	"UserAccessPointPort");
	private final static QName	ADMINISTRATION_SERVICE		= new QName("http://administration.autobahn.geant.net/",
																	"AdministrationService");
	private final static QName	ADMINISTRATION_PORT			= new QName("http://administration.autobahn.geant.net/",
																	"AdministrationPort");

	public AutobahnClient createAutobahnClient(String uapUri, String adminUri, WSSecurity security) throws Exception {

		UserAccessPoint uap;
		Administration administration;

		checkSecurityIsInitialized(security);

		try {
			if (Boolean.parseBoolean(security.getActivatedStr())) {
				uap = createSoapServiceSecure(uapUri, USER_ACCESS_POINT_SERVICE, USER_ACCESS_POINT_PORT, UserAccessPoint.class, security);
				administration = createSoapServiceSecure(adminUri, ADMINISTRATION_SERVICE, ADMINISTRATION_PORT, Administration.class, security);
			} else {
				uap = createSoapService(uapUri, USER_ACCESS_POINT_SERVICE, USER_ACCESS_POINT_PORT, UserAccessPoint.class);
				administration = createSoapService(adminUri, ADMINISTRATION_SERVICE, ADMINISTRATION_PORT, Administration.class);
			}
		} catch (Throwable t) {
			throw new Exception("Failed to instantiate AutoBAHN client.", t);
		}

		AutobahnClient client = new AutobahnClient();
		client.setSecurity(security);
		client.setUapService(uap);
		client.setAdministrationService(administration);
		return client;
	}

	private static void checkSecurityIsInitialized(WSSecurity security) {
		if (security == null)
			throw new IllegalArgumentException("Given WSSecurity object must be initialized.");
		// TODO check all required properties are loaded.
	}

	private <T> T createSoapServiceSecure(String uri,
			QName serviceName,
			QName portName,
			Class<T> clazz,
			WSSecurity security)
			throws Throwable
	{
		T service = createSoapService(uri, serviceName, portName, clazz);
		WSSecurity.setClientTimeout(service);
		security.configureEndpoint(service);

		return service;
	}

	private <T> T createSoapService(String uri,
			QName serviceName,
			QName portName,
			Class<T> clazz)
			throws WebServiceException
	{
		/*
		 * The JAXWS SPI uses the context class loader to locate an implementation. We therefore make sure the context class loader is set to our
		 * class loader.
		 */
		Thread thread = Thread.currentThread();
		ClassLoader oldLoader = thread.getContextClassLoader();
		try {
			thread.setContextClassLoader(getClass().getClassLoader());
			Service service = Service.create(serviceName);
			service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, uri);

			return service.getPort(portName, clazz);
		} finally {
			thread.setContextClassLoader(oldLoader);
		}
	}

}
