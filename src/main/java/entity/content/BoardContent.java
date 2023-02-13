package entity.content;

import entity.Board;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BoardContent extends Content{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    public static BoardContent create(Content content, Board board) {
        BoardContent boardContentBuilder = BoardContent.builder()
                .fileNm(content.getFileNm())
                .filePath(content.getFilePath())
                .fileOrgNm(content.getFileOrgNm())
                .mimeTp(content.getMimeTp())
                .fileSize(content.getFileSize())
                .board(board)
                .build();

        return boardContentBuilder;
    }

}
