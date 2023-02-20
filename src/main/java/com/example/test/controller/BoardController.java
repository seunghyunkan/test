package com.example.test.controller;

import com.example.test.dto.BoardDto;
import com.example.test.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping({"", "/"})
    public Page<BoardDto.Info> findAllBoard(Pageable pageable, BoardDto.Search search) {
        return boardService.findAllBoard(pageable, search);
    }

    @GetMapping("/{boardId}")
    public BoardDto.Info findOneBoard(@PathVariable("boardId") @Valid String boardId) {
        return boardService.findOneBoard(boardId);
    }

    @PostMapping(value = {"/", ""})
    public BoardDto.Info saveBoard(@ModelAttribute @Valid BoardDto.Save param) {
        return boardService.saveBoard(param);
    }

    @PostMapping("/update")
    public BoardDto.Info updateBoard(@ModelAttribute @Valid BoardDto.Update param) {
        return boardService.updateBoard(param);
    }

    @PostMapping("/delete")
    public void deleteBoard(@RequestBody @Valid BoardDto.Delete param) {
        boardService.deleteBoard(param);
    }
}
