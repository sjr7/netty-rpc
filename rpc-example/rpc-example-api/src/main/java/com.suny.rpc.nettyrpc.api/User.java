package com.suny.rpc.nettyrpc.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sunjianrong
 * @date 2021/8/21 下午5:35
 */
@Data
public class User implements Serializable {

    private String id;

    private String username;
}
