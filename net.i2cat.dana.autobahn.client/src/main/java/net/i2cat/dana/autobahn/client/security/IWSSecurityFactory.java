package net.i2cat.dana.autobahn.client.security;

/**
 * 
 * @author Isart Canyameres Gimenez (i2cat)
 * 
 */
public interface IWSSecurityFactory {

	/**
	 * Creates a WSSecurity instance ready to use.
	 * <p>
	 * This method assumes following directory tree under given commonPath:
	 * <p>
	 * commonPath/
	 * <p>
	 * commonPath/security.properties
	 * <p>
	 * commonPath/edugain/
	 * <p>
	 * commonPath/edugain/edugain.properties
	 * </p>
	 * 
	 * @param commonPath
	 * @return
	 * @throws Exception
	 */
	public WSSecurity createWSSecurity(String commonPath) throws Exception;

}
