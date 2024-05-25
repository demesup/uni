package com.demesup.domain.enums;

import lombok.Getter;

@Getter
public enum Hour {
  H08(8),
  H09(9),
  H10(10),
  H11(11),
  H12(12),
  H13(13),
  H14(14),
  H15(15),
  H16(16),
  H17(17),
  H18(18),
  H19(19),
  H20(20),
  H21(21);

  private final int hour;

  Hour(int hour) {
    this.hour = hour;
  }

  @Override
  public String toString() {
    return String.format("%02d:00", hour);
  }

  public static Hour fromHour(int hour) {
    for (Hour h : values()) {
      if (h.hour == hour) {
        return h;
      }
    }
    throw new IllegalArgumentException("Invalid hour: " + hour);
  }
}
