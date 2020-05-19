package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("tensquare-user")
public interface UserClient {

    @RequestMapping(value = "/user/incfans/{userId}/{count}", method = RequestMethod.PUT)
    public void incFansCount(@PathVariable("userId") String userId, @PathVariable("count") int count);

    @RequestMapping(value = "/user/incfollow/{userId}/{count}", method = RequestMethod.PUT)
    public void incFollowCount(@PathVariable("userId") String userId, @PathVariable("count") int count);

}
