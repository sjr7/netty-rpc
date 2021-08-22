package com.suny.rpc.nettyrpc.client;

import com.suny.rpc.nettyrpc.api.User;
import com.suny.rpc.nettyrpc.api.UserService;
import com.suny.rpc.nettyrpc.core.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author sunjianrong
 * @date 2021/8/22 下午10:20
 */
@Service
@Slf4j
public class BffService {

    @Reference
    private UserService userService;


    public User getUserInfo() {
        String userId = "1";
        User user = userService.selectById(userId);
        log.debug("getUserInfo rpc调用结果: {}", user.toString());
        return user;
    }


}
