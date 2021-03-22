package pw.bazz;

import org.junit.Assert;
import org.junit.Test;
import pw.bazz.Controller.AlertManager;

public class AlertManagerTest {
    @Test
    public void testLoadAllAlertUsers(){
        Assert.assertEquals("Testing amount of alert users: ", 1, AlertManager.loadAlertUsers().size());
    }
}
