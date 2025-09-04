package security.instagram.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class DoSomethingController {
    @GetMapping("/test")
    public ResponseEntity<String> doSomething() {
        return ResponseEntity.ok("Do something");
    }
}
