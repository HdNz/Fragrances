package view.popups;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import dto.Entity;
import dto.User;
import util.json.Serializer;
import view.util.CommonNotification;

import java.util.Collections;
import java.util.List;

public class AddUser {

    public static Dialog buildNewUserDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("500px");
        dialog.setHeight("210px");

        FormLayout form = new FormLayout();
        TextField userName = new TextField();
        userName.setPlaceholder("Username");

        PasswordField password = new PasswordField();
        password.setPlaceholder("Password");

        form.add(userName, password);

        Button addButton = new Button("Add", e -> handleCreationUser(dialog));
        addButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
        dialog.add(form, addButton);

        return dialog;
    }

    private static void handleCreationUser(Dialog dialog) {
        Serializer.saveEntitiesToJsonFile(mapToEntityList(dialog));
        dialog.close();
        CommonNotification.buildNotification("User created :)").open();
    }

    private static List<Entity> mapToEntityList(Dialog dialog) {
        List<Component> textFields = getTextFieldsFromDialog(dialog);
        Entity entity = new Entity();
        User user = new User();
        user.setName(((TextField) textFields.get(0)).getValue());
        user.setPassword(((PasswordField) textFields.get(1)).getValue());
        entity.setUser(user);
        return Collections.singletonList(entity);
    }

    private static List<Component> getTextFieldsFromDialog(Dialog dialog) {
        return dialog.getChildren()
                .toList()
                .get(0)
                .getChildren()
                .toList();
    }
}
