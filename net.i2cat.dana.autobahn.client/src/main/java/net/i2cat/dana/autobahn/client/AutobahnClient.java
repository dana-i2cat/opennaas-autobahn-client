package net.i2cat.dana.autobahn.client;

import net.geant.autobahn.administration.Administration;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.i2cat.dana.autobahn.client.security.WSSecurity;

/**
 * 
 * @author Isart Canyameres Gimenez (i2cat)
 * 
 */
public class AutobahnClient {

	private WSSecurity		security;
	private UserAccessPoint	uapService;
	private Administration	administrationService;

	public WSSecurity getSecurity() {
		return security;
	}

	public void setSecurity(WSSecurity security) {
		this.security = security;
	}

	public UserAccessPoint getUapService() {
		return uapService;
	}

	public void setUapService(UserAccessPoint uapService) {
		this.uapService = uapService;
	}

	public Administration getAdministrationService() {
		return administrationService;
	}

	public void setAdministrationService(Administration administrationService) {
		this.administrationService = administrationService;
	}

}
