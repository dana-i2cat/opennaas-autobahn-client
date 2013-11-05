package net.i2cat.dana.autobahn.client.security;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.xml.xpath.XPathException;

/**
 * WSSecurityLoader is responsible for creating configured WSSecurity instances. It loads required Properties files and instantiates WSSecurity with
 * required values.
 * 
 * @author Isart Canyameres Gimenez (i2cat)
 * 
 */
public class WSSecurityLoader implements IWSSecurityFactory {

	public static final String	PROPERTY_ACTIVATED	= "net.geant.autobahn.security.activated";
	public static final String	PROPERTY_ENCRYPT	= "net.geant.autobahn.edugain.encrypt";
	public static final String	PROPERTY_TIMESTAMP	= "net.geant.autobahn.edugain.timestamp";
	public static final String	PROPERTY_EDUGAIN	= "net.geant.autobahn.edugain.activated";
	public static final String	PROPERTY_USER		= "org.apache.ws.security.crypto.merlin.keystore.alias";

	public WSSecurity createWSSecurity(String commonPath) throws IOException, XPathException {

		ClassLoader securityLoader = getClass().getClassLoader();

		URL edugain = securityLoader.getResource(commonPath + "/edugain/edugain.properties");
		URL securityUrl = securityLoader.getResource(commonPath + "/security.properties");
		URL wss4jPropsUrl = securityLoader.getResource(commonPath + "/security.properties");

		Properties wss4jProperties = readPropertiesFromUrl(wss4jPropsUrl);
		Properties securityProperties = readPropertiesFromUrl(securityUrl);

		WSSecurity wssec = new WSSecurity();
		setSecurityOptions(wssec, securityProperties);
		wssec.setWss4jProperties(wss4jProperties);

		SecurityPasswordCallback pwdCallback = new SecurityPasswordCallback(securityUrl);
		wssec.setSecurityPassword(pwdCallback);

		return wssec;
	}

	/**
	 * Reads security options from given properties and populates wssec internal fields accordingly.
	 * 
	 * @param securityConfigurationURL
	 * @throws IOException
	 */
	private static void setSecurityOptions(WSSecurity wssec, Properties securityProps) {
		wssec.setActivatedStr(securityProps.getProperty(PROPERTY_ACTIVATED));
		wssec.setTimestampStr(securityProps.getProperty(PROPERTY_TIMESTAMP));
		wssec.setEncryptStr(securityProps.getProperty(PROPERTY_ENCRYPT));
		wssec.setEdugainAct(securityProps.getProperty(PROPERTY_EDUGAIN));
		wssec.setSecurityUser(securityProps.getProperty(PROPERTY_USER));
	}

	private Properties readPropertiesFromUrl(URL propertiesUrl) throws IOException {

		Properties props = new Properties();
		props.load(propertiesUrl.openStream());
		return props;
	}

}
