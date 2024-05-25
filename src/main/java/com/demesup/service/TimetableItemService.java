package com.demesup.service;


import com.demesup.api.dto.response.item.TimetableResponse;
import com.demesup.domain.Group;

public interface TimetableItemService {
  TimetableResponse getTimetableStructure(Group group);
}
