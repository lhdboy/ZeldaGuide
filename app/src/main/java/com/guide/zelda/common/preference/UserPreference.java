package com.guide.zelda.common.preference;


import net.orange_box.storebox.annotations.method.ClearMethod;
import net.orange_box.storebox.annotations.method.KeyByString;
import net.orange_box.storebox.annotations.type.FilePreferences;

@FilePreferences(value = "UserPreference")
public interface UserPreference {

    @KeyByString("login_flag")
    boolean getLoginFlag();

    @KeyByString("login_flag")
    void setLoginFlag(boolean flag);

    @KeyByString("last_login_time")
    long getLastLoginTime();

    @KeyByString("last_login_time")
    void setLastLoginTime(long time);

    @KeyByString("phone_number")
    String getPhoneNumber();

    @KeyByString("phone_number")
    void setPhoneNumber(String phoneNumber);

    @KeyByString("user_name")
    String getUserName();

    @KeyByString("user_name")
    void setUserName(String userName);

    @KeyByString("user_avatar")
    String getUserAvatar();

    @KeyByString("user_avatar")
    void setUserAvatar(String avatarUrl);

    @KeyByString("user_token")
    String getUserAccessToken();

    @KeyByString("user_token")
    void setAccessToken(String accessToken);

    @KeyByString("push_token")
    String getPushToken();

    @KeyByString("push_token")
    void setPushToken(String pushToken);

    @KeyByString("operating_device_mac")
    String getOperatingDeviceId();

    @KeyByString("operating_device_mac")
    void setOperatingDeviceId(String mac);

    @KeyByString("operating_device_name")
    String getOperatingDeviceName();

    @KeyByString("operating_device_name")
    void setOperatingDeviceName(String name);

    @KeyByString("operating_device_ownership")
    int getOperatingDeviceOwnership();

    @KeyByString("operating_device_ownership")
    void setOperatingDeviceOwnership(int ownership);

    @KeyByString("wechat_name")
    String getWeChatName();

    @KeyByString("wechat_name")
    void setWeChatName(String name);

    @ClearMethod
    void clear();

}