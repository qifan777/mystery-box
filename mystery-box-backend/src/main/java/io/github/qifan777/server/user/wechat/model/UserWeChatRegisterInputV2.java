package io.github.qifan777.server.user.wechat.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserWeChatRegisterInputV2 {
    @NotNull
    String phoneCode;
    @NotNull
    String loginCode;
    String inviteCode;
}
