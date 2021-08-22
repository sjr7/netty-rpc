package com.suny.rpc.nettyrpc.server.biz;

import com.suny.rpc.nettyrpc.api.User;
import com.suny.rpc.nettyrpc.api.UserService;
import com.suny.rpc.nettyrpc.core.annotations.Service;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author sunjianrong
 * @date 2021/8/21 下午5:37
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User selectById(String id) {
        User user = new User();
        user.setId(id);
        user.setUsername(RandomStringUtils.randomAlphabetic(5));
        return user;
    }
}
