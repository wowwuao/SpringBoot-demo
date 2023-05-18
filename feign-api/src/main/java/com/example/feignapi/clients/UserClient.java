package com.example.feignapi.clients;

import com.example.feignapi.model.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "demo-service-provider",path = "/feign/user")
public interface UserClient {
    @GetMapping("/{id}")
    UserVO findById(@PathVariable("id") Long id);
}
