package com.idle.boardservice.presentation;

import com.idle.boardservice.application.BoardService;
import com.idle.boardservice.application.dto.response.BoardListResponse;
import com.idle.boardservice.application.dto.response.BoardResponse;
import com.idle.boardservice.presentation.dto.request.BoardRequest;
import com.idle.commonservice.annotation.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BoardResponse createBoard(@UserId Long userId, @RequestBody BoardRequest boardRequest) {
        return boardService.createBoard(userId, boardRequest);
    }

    // 게시글 단일 조회
    @GetMapping(path = "/{boardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoardResponse getBoardById(@PathVariable Long boardId) {
        return boardService.getBoardById(boardId);
    }

    // 특정 위치 반경 5km 이내 게시글 조회
    @GetMapping(path = "/radius", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoardListResponse getBoardsWithRadius(@RequestParam double latitude, @RequestParam double longitude) {
        return boardService.getBoardsWithRadius(latitude, longitude);
    }
}
