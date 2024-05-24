package com.kpop.demesup.domain.enums;

import lombok.Getter;

@Getter
public enum YearCode {
  BACHELOR_FIRST(1, "Bachelor First Year"),
  BACHELOR_SECOND(2, "Bachelor Second Year"),
  BACHELOR_THIRD(3, "Bachelor Third Year"),
  BACHELOR_FOURTH(4, "Bachelor Fourth Year"),
  MASTER_FIRST(1, "Master First Year"),
  MASTER_SECOND(2, "Master Second Year"),
  PHD_FIRST(1, "PhD First Year"),
  PHD_SECOND(2, "PhD Second Year"),
  PHD_THIRD(3, "PhD Third Year"),
  PHD_FOURTH(4, "PhD Fourth Year"),
  PHD_FIFTH(5, "PhD Fifth Year");

  private final int year;
  private final String description;

  YearCode(int year, String description) {
    this.year = year;
    this.description = description;
  }

}
