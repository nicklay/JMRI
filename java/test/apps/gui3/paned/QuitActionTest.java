package apps.gui3.paned;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.util.swing.JFrameInterface;

/**
 *
 * @author Paul Bender Copyright (C) 2017	
 */
public class QuitActionTest {

    @Test
    public void testCTor() {
        JFrameInterface w = new JFrameInterface(new jmri.util.JmriJFrame("foo"));
        QuitAction t = new QuitAction("test",w);
        Assert.assertNotNull("exists",t);
    }

    // The minimal setup for log4J
    @Before
    public void setUp() {
        apps.tests.Log4JFixture.setUp();
        jmri.util.JUnitUtil.resetInstanceManager();
    }

    @After
    public void tearDown() {
        jmri.util.JUnitUtil.resetInstanceManager();
        apps.tests.Log4JFixture.tearDown();
    }

    private final static Logger log = LoggerFactory.getLogger(QuitActionTest.class.getName());

}
