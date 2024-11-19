package view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import view.popups.AddUser;
import view.util.LoginValidation;
import view.util.UserDataHolder;

@Route("login")
public class Login extends VerticalLayout {

    public Login() {
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(e -> handleLoginEvent(loginForm, e));

        Button button = new Button("Add account", e -> AddUser.buildNewUserDialog().open());
        button.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);

        setAlignItems(Alignment.CENTER);
        setAlignSelf(Alignment.END, button);
        add(button, loginForm);
    }

    private void handleLoginEvent(LoginForm loginForm, AbstractLogin.LoginEvent e) {
        if (new LoginValidation().validate(e)) {
            UserDataHolder.getInstance().setUserName(e.getUsername());
            UI.getCurrent().navigate(Main.class);
        } else {
            loginForm.setEnabled(true);
            loginForm.setError(true);
        }
    }
}
