package com.oneqst.quest.repository.query;

import com.oneqst.quest.dto.MyAuthDto;

import java.util.List;

public interface AuthPostRepositoryCustom {
    List<MyAuthDto> myActivityAuthPostLookup(Long MemberId);
}
