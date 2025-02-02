package jmri.jmrit.display;

import java.awt.GraphicsEnvironment;

import jmri.NamedBeanHandle;
import jmri.Turnout;
import jmri.jmrit.catalog.NamedIcon;
import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;
import org.junit.Assert;
import org.junit.Assume;
import org.netbeans.jemmy.operators.JComponentOperator;
import org.netbeans.jemmy.operators.JFrameOperator;

/**
 * Swing tests for the TurnoutIcon
 *
 * @author Bob Jacobsen Copyright 2009, 2010
 */
public class TurnoutIconWindowTest {

    @Test
    public void testPanelEditor() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        jmri.jmrit.display.panelEditor.PanelEditor panel
                = new jmri.jmrit.display.panelEditor.PanelEditor("TurnoutIconWindowTest.testPanelEditor");

        panel.getTargetPanel();

        TurnoutIcon icon = new TurnoutIcon(panel);
        Turnout sn = jmri.InstanceManager.turnoutManagerInstance().provideTurnout("IT1");
        icon.setTurnout(new NamedBeanHandle<Turnout>("IT1", sn));

        icon.setDisplayLevel(Editor.TURNOUTS);

        icon.setIcon("TurnoutStateClosed",
                new NamedIcon("resources/icons/smallschematics/tracksegments/os-lefthand-east-closed.gif",
                        "resources/icons/smallschematics/tracksegments/os-lefthand-east-closed.gif"));
        icon.setIcon("TurnoutStateThrown",
                new NamedIcon("resources/icons/smallschematics/tracksegments/os-lefthand-east-thrown.gif",
                        "resources/icons/smallschematics/tracksegments/os-lefthand-east-thrown.gif"));
        icon.setIcon("BeanStateInconsistent",
                new NamedIcon("resources/icons/smallschematics/tracksegments/os-lefthand-east-error.gif",
                        "resources/icons/smallschematics/tracksegments/os-lefthand-east-error.gif"));
        icon.setIcon("BeanStateUnknown",
                new NamedIcon("resources/icons/smallschematics/tracksegments/os-lefthand-east-unknown.gif",
                        "resources/icons/smallschematics/tracksegments/os-lefthand-east-unknown.gif"));

        panel.putItem(icon);
        panel.setVisible(true);

        Assert.assertEquals("initial state", Turnout.UNKNOWN, sn.getState());

        // Click icon change state to Active
        JComponentOperator co = new JComponentOperator(panel.getTargetPanel());
        int xloc = icon.getLocation().x + icon.getSize().width / 2;
        int yloc = icon.getLocation().y + icon.getSize().height / 2;
        co.clickMouse(xloc,yloc,1);

        // this will wait for WAITFOR_MAX_DELAY (15 seconds) max
        // checking the condition every WAITFOR_DELAY_STEP (5 mSecs)
        // if it's still false after max wait it throws an assert.
        JUnitUtil.waitFor(() -> {
            return sn.getState() == Turnout.CLOSED;
        }, "state after one click");

        // Click icon change state to inactive
        co.clickMouse(xloc,yloc,1);
        JUnitUtil.waitFor(() -> {
            return sn.getState() == Turnout.THROWN;
        }, "state after two clicks");

        // close the panel editor frame
        JFrameOperator eo = new JFrameOperator(panel);
        eo.requestClose();

        // close the panel target frame.
        EditorFrameOperator to = new EditorFrameOperator(panel.getTargetFrame());
        to.closeFrameWithConfirmations();
        EditorFrameOperator.clearEditorFrameOperatorThreads();
    }

    @Test
    public void testLayoutEditor() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        jmri.jmrit.display.layoutEditor.LayoutEditor panel
                = new jmri.jmrit.display.layoutEditor.LayoutEditor("TurnoutIconWindowTest.testLayoutEditor");

        panel.getTargetPanel();

        TurnoutIcon icon = new TurnoutIcon(panel);
        icon.setDisplayLevel(Editor.TURNOUTS);

        Turnout sn = jmri.InstanceManager.turnoutManagerInstance().provideTurnout("IT1");
        icon.setTurnout("IT1");

        icon.setIcon("TurnoutStateClosed",
                new NamedIcon("resources/icons/smallschematics/tracksegments/os-lefthand-east-closed.gif",
                        "resources/icons/smallschematics/tracksegments/os-lefthand-east-closed.gif"));
        icon.setIcon("TurnoutStateThrown",
                new NamedIcon("resources/icons/smallschematics/tracksegments/os-lefthand-east-thrown.gif",
                        "resources/icons/smallschematics/tracksegments/os-lefthand-east-thrown.gif"));
        icon.setIcon("BeanStateInconsistent",
                new NamedIcon("resources/icons/smallschematics/tracksegments/os-lefthand-east-error.gif",
                        "resources/icons/smallschematics/tracksegments/os-lefthand-east-error.gif"));
        icon.setIcon("BeanStateUnknown",
                new NamedIcon("resources/icons/smallschematics/tracksegments/os-lefthand-east-unknown.gif",
                        "resources/icons/smallschematics/tracksegments/os-lefthand-east-unknown.gif"));

        panel.putItem(icon);
        panel.setVisible(true);

        Assert.assertEquals("initial state", Turnout.UNKNOWN, sn.getState());

        // Click icon change state to Active
        JComponentOperator co = new JComponentOperator(panel.getTargetPanel());
        int xloc = icon.getLocation().x + icon.getSize().width / 2;
        int yloc = icon.getLocation().y + icon.getSize().height / 2;
        co.clickMouse(xloc,yloc,1);

        JUnitUtil.waitFor(() -> {
            return sn.getState() != Turnout.UNKNOWN;
        }, "Not initial state");

        JUnitUtil.waitFor(() -> {
            return sn.getState() == Turnout.CLOSED;
        }, "state after one click");

        // Click icon change state to inactive
        co.clickMouse(xloc,yloc,1);

        JUnitUtil.waitFor(() -> {
            return sn.getState() == Turnout.THROWN;
        }, "state after two clicks");

        // close the panel editor frame
        EditorFrameOperator to = new EditorFrameOperator(panel);
        to.closeFrameWithConfirmations();
        EditorFrameOperator.clearEditorFrameOperatorThreads();
    }

    @BeforeEach
    public void setUp() throws Exception {
        JUnitUtil.setUp();
        jmri.util.JUnitUtil.resetProfileManager();
        JUnitUtil.initInternalTurnoutManager();
        JUnitUtil.initInternalSensorManager();
    }

    @AfterEach
    public void tearDown() throws Exception {
        JUnitUtil.resetWindows(false,false);
        JUnitUtil.deregisterBlockManagerShutdownTask();
        JUnitUtil.tearDown();
    }
}
