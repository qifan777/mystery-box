package io.github.qifan777.server.user.wechat.model;

import lombok.Data;

@Data
public class UserWeChatRegisterInput {

    private String inviteCode;
    private String loginCode;
    private String phone;
    private String code;
}
