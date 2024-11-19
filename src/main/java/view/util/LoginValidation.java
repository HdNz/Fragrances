package view.util;

import com.vaadin.flow.component.login.AbstractLogin;
import dto.Entity;
import util.json.Serializer;

public class LoginValidation {
    public boolean validate(AbstractLogin.LoginEvent event) {
        Entity entity = Serializer.readEntitiesFromJson().stream()
                .filter(e -> e.getUser().getName().equals(event.getUsername()))
                .findAny()
                .orElse(null);
        return entity != null && event.getPassword().equals(entity.getUser().getPassword());
    }
}
