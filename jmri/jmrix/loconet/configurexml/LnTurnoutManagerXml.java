package jmri.jmrix.loconet.configurexml;

import org.jdom.Element;
import jmri.jmrix.loconet.LnTurnoutManager;

/**
 * Provides load and store functionality for
 * configuring LnTurnoutManagers.
 * <P>
 * Uses the store method from the abstract base class, but
 * provides a load method here.
 *
 * @author Bob Jacobsen Copyright: Copyright (c) 2002
 * @version $Revision: 1.8 $
 */
public class LnTurnoutManagerXml extends jmri.configurexml.AbstractTurnoutManagerConfigXML {

    public LnTurnoutManagerXml() {
        super();
    }

    public void setStoreElementClass(Element turnouts) {
        turnouts.setAttribute("class","jmri.jmrix.loconet.configurexml.LnTurnoutManagerXml");
    }

    public void load(Element element, Object o) {
        log.error("Invalid method called");
    }

    public void load(Element turnouts) {

        // load individual turnouts
        loadTurnouts(turnouts);
    }

    static org.apache.log4j.Category log = org.apache.log4j.Category.getInstance(LnTurnoutManagerXml.class.getName());

}