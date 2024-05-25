package com.demesup.api.controller;

import com.demesup.api.dto.response.item.TimetableResponse;
import com.demesup.domain.User;
import com.demesup.exception.NotFoundException;
import com.demesup.service.GroupService;
import com.demesup.service.TimetableItemService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/timetable")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TimetableItemController {
  TimetableItemService service;
  GroupService groupService;


  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping("/{groupId}")
  public TimetableResponse getTimetableStructure(@PathVariable Long groupId, @AuthenticationPrincipal User user) {
    return service.getTimetableStructure(groupService.findById(groupId).orElseThrow(() -> new NotFoundException("Group", groupId)));
  }
}
