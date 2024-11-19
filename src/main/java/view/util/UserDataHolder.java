package view.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class UserDataHolder {

    private static final UserDataHolder holder = new UserDataHolder();

    private String userName;


    private UserDataHolder() {
    }

    public static UserDataHolder getInstance() {
        return holder;
    }
}
