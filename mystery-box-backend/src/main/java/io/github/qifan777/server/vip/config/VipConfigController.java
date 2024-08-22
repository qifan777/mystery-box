package io.github.qifan777.server.vip.config;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("vip-config")
@RestController
@AllArgsConstructor
public class VipConfigController {
    private final VipConfigRepository vipConfigRepository;

    @GetMapping
    public VipConfig getConfig() {
        return vipConfigRepository.get();
    }

    @SaCheckPermission("/vip-config")
    @PostMapping
    public void saveConfig(@RequestBody VipConfig vipConfig) {
        vipConfigRepository.save(vipConfig);
    }

}
