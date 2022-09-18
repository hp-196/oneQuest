package com.oneqst.quest.dto;

import com.oneqst.quest.domain.QuestPost;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestPostUpdateDto {

    private String title;

    private String content;

    public QuestPostUpdateDto(QuestPost questPost) {
        this.title = questPost.getTitle();
        this.content = questPost.getContent();
    }
}
