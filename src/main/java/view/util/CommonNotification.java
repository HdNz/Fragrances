package view.util;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;

public class CommonNotification {

    public static Notification buildNotification(String msg) {
        Span content = new Span(msg);
        Notification notification = new Notification(content);
        notification.setDuration(2000);
        notification.setPosition(Notification.Position.TOP_STRETCH);
        return notification;
    }
}
