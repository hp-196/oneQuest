package com.oneqst.quest.dto;

import com.oneqst.quest.domain.Quest;
import lombok.Data;

@Data
public class QuestIndexDto {
    private String title;
    private String image;
    private String url;

    public QuestIndexDto(Quest quest) {
        this.title = quest.getQuestTitle();
        this.image = quest.getQuestImage();
        this.url = quest.getQuestImage();
    }
}
