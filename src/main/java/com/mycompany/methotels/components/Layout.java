package com.mycompany.methotels.components;

import com.mycompany.methotels.data.Role;
import com.mycompany.methotels.entities.User;
import com.mycompany.methotels.services.FacebookServiceInformation;
import org.apache.tapestry5.*;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;

/**
 * Layout component for pages of application test-project.
 */
@Import(module = "bootstrap/collapse")
public class Layout {

    @Inject
    private ComponentResources resources;

    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;

    @SessionState
    private User loggedInUser;

    @SessionState
    private FacebookServiceInformation facebookServiceInformation;
    @SessionState
    private FacebookServiceInformation information;
    @SessionState
    private com.restfb.types.User userfb;
    
    public String getClassForPageName() {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "active"
                : null;
    }

    public String[] getPageNames() {
        if(loggedInUser.getRola() == Role.Admin)
            return new String[]{"Index", "DodavanjeSoba", "DodavanjeRadnika", "RegistracijaKorisnika", "Rezervisanje","PretragaUseraAjaxJquery"};
        else return  new String[]{"Index"};
    }

    public boolean getloggedIn() {
        if (loggedInUser.getEmail() != null) {
            return true;
        }
        return false;
    }

    public void onActionFromLogout() {
        loggedInUser = null;
        facebookServiceInformation = null;
        information= null;
        userfb= null;
    }

    public String getLoggedInEmail() {
        return loggedInUser.getEmail();
    }

}
