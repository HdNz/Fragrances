package view.popups;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import dto.Entity;
import dto.Fragrance;
import dto.User;
import util.ResultService;
import util.ResultServiceImpl;
import view.FragranceList;
import view.util.CommonNotification;
import view.util.UserDataHolder;

import java.util.ArrayList;
import java.util.List;

public class AddScore {

    private final ResultService resultService = new ResultServiceImpl();

    private static final TextField name = new TextField();
    private static final TextField description = new TextField();
    private static final TextField evaluation = new TextField();

    private final Button save = new Button("Save");

    public Dialog buildAddScoreDialog() {
        Dialog dialog = new Dialog();

        HorizontalLayout layout = buildLayout();
        HorizontalLayout actions = buildButtonLayout(dialog);

        dialog.add(layout, actions);
        return dialog;
    }

    private static HorizontalLayout buildLayout() {
        name.setPlaceholder("Name");
        name.setMinWidth("160px");
        description.setPlaceholder("description");
        description.setMinWidth("130px");
        evaluation.setPlaceholder("evaluation");
        evaluation.setMinWidth("130px");
        return new HorizontalLayout(name, description, evaluation);
    }

    private HorizontalLayout buildButtonLayout(Dialog dialog) {
        save.addClickListener(e -> handleSaveEvent(dialog));
        save.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
        return new HorizontalLayout(save);
    }

    private void handleSaveEvent(Dialog dialog) {
        Component component = dialog.getChildren().toList().get(0);
        List<Fragrance> fragrances = new ArrayList<>();

        String name = ((TextField) component.getChildren().toList().get(0)).getValue();
        String description = ((TextField) component.getChildren().toList().get(1)).getValue();
        String evaluation = ((TextField) component.getChildren().toList().get(2)).getValue();
        fragrances.add(new Fragrance(name, evaluation, description));

        ((TextField) component.getChildren().toList().get(0)).setValue("");
        ((TextField) component.getChildren().toList().get(1)).setValue("");
        ((TextField) component.getChildren().toList().get(2)).setValue("");

        User user = new User();
        user.setName(UserDataHolder.getInstance().getUserName());
        Entity entity = new Entity(user, fragrances);
        resultService.addResult(entity);
        dialog.close();
        UI.getCurrent().navigate(FragranceList.class, "man");
        CommonNotification.buildNotification("Element added :)").open();
    }
}
