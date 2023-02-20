package com.example.test.entity.content;

import com.example.test.entity.Board;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = ContentType.BOARD)
public class BoardContent extends Content{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    public static BoardContent create(Content content, Board board) {
        BoardContent cont = new BoardContent();
        cont.setFilePath(content.getFilePath());
        cont.setFileNm(content.getFileNm());
        cont.setFileOrgNm(content.getFileOrgNm());
        cont.setMimeTp(content.getMimeTp());
        cont.setFileSize(content.getFileSize());
        cont.setBoard(board);
        return cont;
    }

}
