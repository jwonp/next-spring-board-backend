package com.ikiningyou.cb.util;

import java.util.HashMap;

public final class BoardNameMap {

  private HashMap<String, String> boardNameMap = new HashMap<String, String>();

  public BoardNameMap() {
    this.boardNameMap.put("MENU1", "Board One");
    this.boardNameMap.put("MENU2", "Board Two");
    this.boardNameMap.put("MENU3", "Board Three");
    this.boardNameMap.put("MENU4", "Board Four");
  }

  public boolean isBoardName(String name) {
    return boardNameMap.values().contains(name);
  }
}
