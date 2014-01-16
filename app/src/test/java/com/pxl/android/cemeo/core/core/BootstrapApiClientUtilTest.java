

package com.pxl.android.cemeo.core.core;

import com.pxl.android.cemeo.core.BootstrapService;
import com.pxl.android.cemeo.core.User;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests of client API
 */
public class BootstrapApiClientUtilTest {

    @Test
    @Ignore("Requires the API to use basic authentication. Parse.com api does not. See BootstrapService for more info.")
    public void shouldCreateClient() throws Exception {
        List<User> users = new BootstrapService("demo@androidbootstrap.com", "foobar").getUsers();

        //assertThat(users.get(0).getUsername(), notNullValue());
    }
}
