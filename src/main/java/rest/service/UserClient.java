package rest.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rest.model.User;

import java.util.List;

@FeignClient(name = "userClient", url = "${url}")
public interface UserClient {
    @GetMapping
    List<User> getUserById(@RequestParam("id") int userId);
}
