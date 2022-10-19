package com.oneqst.Member.dto;

import com.oneqst.Member.domain.Member;
import com.oneqst.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagDto {
    private String title;

    public TagDto(Member profileMember) {
        this.title = profileMember.returnTags();
    }
}
