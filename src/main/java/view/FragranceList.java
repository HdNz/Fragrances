package view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import dto.Entity;
import dto.Fragrance;
import dto.User;
import util.ResultService;
import util.ResultServiceImpl;
import util.json.Serializer;
import view.util.UserDataHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Route(value = "grid", layout = Main.class)
public class FragranceList extends HorizontalLayout implements HasUrlParameter<String> {

    private final ResultService resultService = new ResultServiceImpl();

    @Override
    public void setParameter(BeforeEvent beforeEvent, String exerciseName) {
        removeAll();
        add(buildGrip());
    }

    private Grid<Fragrance> buildGrip() {
        List<Fragrance> fragrances = Serializer.readEntitiesFromJson().stream()
                .filter(e -> e.getUser().getName().equals(UserDataHolder.getInstance().getUserName()))
                .findAny().orElseThrow(NoSuchElementException::new)
                .getFragrances();

        Grid<Fragrance> grid = new Grid<>(Fragrance.class);
        grid.setAllRowsVisible(true);
        grid.setColumnReorderingAllowed(true);
        grid.setItems(fragrances != null ? fragrances : new ArrayList<>());
        grid.setColumns("name", "description", "evaluation");
        grid.getColumnByKey("evaluation").setComparator((a, b) ->
                Float.compare(Float.parseFloat(a.getEvaluation().split("/")[0]), Float.parseFloat(b.getEvaluation().split("/")[0]))
        );
        Editor<Fragrance> editor = grid.getEditor();
        Grid.Column<Fragrance> editColumn = grid.addComponentColumn(fragrance -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(fragrance);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        Grid.Column<Fragrance> deleteColumn = grid.addComponentColumn(fragrance -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                User user = new User();
                user.setName(UserDataHolder.getInstance().getUserName());
                Entity entity = new Entity(user, Collections.singletonList(fragrance));
                resultService.deleteResult(entity);
                UI.getCurrent().navigate(FragranceList.class, "man");
            });
            return deleteButton;
        }).setWidth("150px").setFlexGrow(0);

        Binder<Fragrance> binder = new Binder<>(Fragrance.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField name = new TextField();
        name.setWidthFull();
        binder.forField(name)
                .asRequired("Must not be empty.")
                .bind(Fragrance::getName, Fragrance::setName);
        grid.getColumnByKey("name").setEditorComponent(name)
                .setWidth("350px").setFlexGrow(0);
        TextField description = new TextField();
        name.setWidthFull();
        binder.forField(description)
                .asRequired("Must not be empty.")
                .bind(Fragrance::getDescription, Fragrance::setDescription);
        grid.getColumnByKey("description").setEditorComponent(description);
        TextField evaluation = new TextField();
        name.setWidthFull();
        binder.forField(evaluation)
                .asRequired("Must not be empty.")
                .bind(Fragrance::getEvaluation, Fragrance::setEvaluation);
        grid.getColumnByKey("evaluation").setEditorComponent(evaluation)
                .setWidth("150px").setFlexGrow(0);

        Button saveButton = new Button("Save", e -> {
            editor.getBinder().writeBeanIfValid(editor.getItem());
            User user = new User();
            user.setName(UserDataHolder.getInstance().getUserName());
            Entity entity = new Entity(user, Collections.singletonList(editor.getItem()));
            resultService.deleteResult(entity);
            resultService.addResult(entity);
            UI.getCurrent().navigate(FragranceList.class, "man");
        });
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton, cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);
        deleteColumn.setEditorComponent(actions);

        grid.setColumnOrder(grid.getColumnByKey("name"),
                grid.getColumnByKey("description"),
                grid.getColumnByKey("evaluation"),
                editColumn, deleteColumn);

        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        return grid;
    }
}
