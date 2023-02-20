package com.example.test.service;

import com.example.test.dto.BoardDto;
import com.example.test.file.FileUploader;
import com.example.test.repository.BoardRepository;
import com.example.test.repository.BoardRepositoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardRepositoryQuery boardRepositoryQuery;

    private final FileUploader fileUploader;

    public Page<BoardDto.Info> findAllBoard(Pageable pageable, BoardDto.Search param) {
        return null;
    }


    public BoardDto.Info findOneBoard(String boardId) {
        return null;
    }


    public BoardDto.Info saveBoard(BoardDto.Save param) {
        return null;
    }


    public BoardDto.Info updateBoard(BoardDto.Update param) {
        return null;
    }


    public void deleteBoard(BoardDto.Delete param) {
    }
}

