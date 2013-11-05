package net.i2cat.dana.autobahn.client.test;

import java.io.IOException;
import java.util.List;

import javax.xml.xpath.XPathException;

import net.geant.autobahn.administration.Administration;
import net.geant.autobahn.administration.ReservationType;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.useraccesspoint.PortType;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.i2cat.dana.autobahn.client.AutobahnClient;
import net.i2cat.dana.autobahn.client.AutobahnClientFactory;
import net.i2cat.dana.autobahn.client.security.WSSecurity;

import org.junit.Before;
import org.junit.Test;

public class AutobahnClientTest {

	private static final String	TEST_SERVER_URL		= "http://62.217.124.238:8081";
	private static final String	UAP_SERVICE_URL		= TEST_SERVER_URL + "/autobahn/uap";
	private static final String	ADMIN_SERVICE_URL	= TEST_SERVER_URL + "/autobahn/administration";
	
	private static final String SECURITY_CONFIG_PATH = "etc/security";

	private WSSecurity			security;

	@Before
	public void initSecurity() throws XPathException, IOException {
		security = new WSSecurity(SECURITY_CONFIG_PATH);
	}

	@Test
	public void uapClientTest() throws Exception {

		AutobahnClientFactory factory = new AutobahnClientFactory();
		AutobahnClient client = factory.createAutobahnClient(UAP_SERVICE_URL, ADMIN_SERVICE_URL, security);

		UserAccessPoint uap = client.getUapService();

		// List client ports
		List<PortType> ptList = uap.getDomainClientPorts();
		for (PortType pt : ptList) {
			System.out.println("Got domain client port: " + pt.getAddress() + " " + pt.getDescription());
		}
		// List client ports
		ptList = uap.getAllClientPorts();
		for (PortType pt : ptList) {
			System.out.println("Got client port: " + pt.getAddress() + " " + pt.getDescription());
		}
		// List idcp ports
		// ptList = uap.getIdcpPorts();
		// for (PortType pt : ptList) {
		// System.out.println("Got idcp port: " + pt.getFriendlyName());
		// }
		// List client links
		List<String> links = uap.getAllLinks();
		for (String link : links) {
			System.out.println("Got client link: " + link);
		}
		// List all links
		List<String> alllinks = uap.getAllLinksNonClient();
		for (String link : alllinks) {
			System.out.println("Got link: " + link);
		}

	}

	@Test
	public void adminClientTest() throws Exception {

		AutobahnClientFactory factory = new AutobahnClientFactory();
		AutobahnClient client = factory.createAutobahnClient(UAP_SERVICE_URL, ADMIN_SERVICE_URL, security);

		Administration administration = client.getAdministrationService();

		Status status = administration.getStatus();
		System.out.println("Current domain: " + status.getDomain());

		for (ServiceType service : administration.getServices()) {
			System.out.println("Got service: " + service.getBodID() + " " + service.getUser().getName() + " " + service.getJustification());
			for (ReservationType reservation : service.getReservations()) {
				System.out.println("With reservation: " + reservation.getBodID());
			}
		}

	}
}
