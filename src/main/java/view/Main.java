package view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import view.popups.AddScore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Route("main-view")
public class Main extends AppLayout {

    public Main() {
        Image img = new Image("/azzaro_sea.png", "Azzaro Sea");
        addToNavbar(true, new DrawerToggle(), img, buildSignOutMenuBar());
        addToDrawer(buildButton(), buildAccordionVerticalLayout());
    }

    private VerticalLayout buildAccordionVerticalLayout() {
        VerticalLayout mainAccordionVerticalLayout = new VerticalLayout();
        Accordion mainAccordion = new Accordion();
        mainAccordion.add("For man", buildFragrancesButtons());
        mainAccordionVerticalLayout.add(mainAccordion);
        mainAccordion.close();
        return mainAccordionVerticalLayout;
    }

    private VerticalLayout buildFragrancesButtons() {
        VerticalLayout buttonsVerticalLayout = new VerticalLayout();
        Button button = new Button("Show fragrance list", v -> UI.getCurrent().navigate(FragranceList.class, "man"));

        File file = new File("fragrances.json");
        StreamResource streamResource = new StreamResource(file.getName(), () -> getStream(file));
        Anchor link = new Anchor(streamResource, String.format("%s (%d KB)", "Export data",
                (int) file.length() / 1024));
        link.getElement().setAttribute("download", true);
        
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonsVerticalLayout.add(button);
        buttonsVerticalLayout.add(link);
        return buttonsVerticalLayout;
    }

    private InputStream getStream(File file) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stream;
    }

    private MenuBar buildSignOutMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Sign Out", e -> handleSignOutEvent());
        menuBar.getStyle().set("margin-left", "100em");
        menuBar.addThemeVariants(MenuBarVariant.LUMO_PRIMARY, MenuBarVariant.LUMO_SMALL);
        return menuBar;
    }

    private VerticalLayout buildButton() {
        VerticalLayout buttonVerticalLayout = new VerticalLayout();
        Button home = new Button("Home", e -> UI.getCurrent().navigate(Main.class));
        home.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonVerticalLayout.add(home);
        buttonVerticalLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button account = new Button("Account", e -> UI.getCurrent().navigate(Main.class));
        account.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonVerticalLayout.add(account);

        Button addScore = new Button("Add element", e -> new AddScore().buildAddScoreDialog().open());
        addScore.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonVerticalLayout.add(addScore);

        return buttonVerticalLayout;
    }

    private void handleSignOutEvent() {
        UI.getCurrent().navigate(Login.class);
    }
}
