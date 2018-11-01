package com.yinhetianze.security.custom.userdetails;

import java.util.Map;

public interface UserDetailsService
{
    UserDetails getUserDetails(String userId);

    void saveUserDetails(UserDetails userDetails);

    void deleteUserDetails(String userId);

    void updateUserDetails(UserDetails userDetails);

    void saveGameKey(String key, Map keyMap);

    Map getGameToken(String gameToken);
}
