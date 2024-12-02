package com.idle.boardservice.presentation;

import com.idle.commonservice.annotation.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @Slf4j
@RequestMapping("/api/boards")
public class BoardController {

    @GetMapping
    public void test(@UserId Long userId) {
        log.info("userId = {} " , userId);
    }
}
