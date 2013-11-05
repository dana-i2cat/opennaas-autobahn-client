package net.i2cat.dana.autobahn.client;

import net.i2cat.dana.autobahn.client.security.WSSecurity;

/**
 * 
 * @author Isart Canyameres Gimenez (i2cat)
 * 
 */
public interface IAutobahnClientFactory {

	/**
	 * Instantiates and configures an AutobahnClient using given WSSecurity.
	 * 
	 * @param uapUrl
	 *            URL where UserAccessPoint service is located
	 * @param adminUrl
	 *            URL where Administration service is located
	 * @param security
	 *            specifies the security to use
	 * @return ready to use AutobahnClient
	 * @throws Exception
	 *             if failed to instantiate or configure the client.
	 */
	public AutobahnClient createAutobahnClient(String uapUrl, String adminUrl, WSSecurity security) throws Exception;

}
