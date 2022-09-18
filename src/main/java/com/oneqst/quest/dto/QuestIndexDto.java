package com.oneqst.quest.dto;

import com.oneqst.quest.domain.Quest;
import lombok.Data;

@Data
public class QuestIndexDto {
    private String title;
    private String introduce;
    private String image;
    private String url;

    public QuestIndexDto(Quest quest) {
        this.title = quest.getQuestTitle();
        this.introduce = quest.getQuestIntroduce();
        this.image = quest.getQuestImage();
        this.url = quest.getQuestImage();
    }
}
