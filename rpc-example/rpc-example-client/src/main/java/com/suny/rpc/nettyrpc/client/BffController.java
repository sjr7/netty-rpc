package com.suny.rpc.nettyrpc.client;

import com.suny.rpc.nettyrpc.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjianrong
 * @date 2021/10/8 下午9:28
 */
@RequestMapping("/test")
@RestController
public class BffController {

    @Autowired
    private BffService bffService;

    @GetMapping
    public User testUser() {
        return bffService.getUserInfo();
    }
}
