package com.continuuity.data.operation;

import com.continuuity.api.data.Delete;

/**
 * Undelete of a Delete.
 */
public class Undelete extends Delete {
  
  public Undelete(byte[] key) {
    super(key);
  }
  
  public Undelete(byte[] row, byte [] column) {
    super(row, column);
  }

  public Undelete(byte [] row, byte [][] columns) {
    super(row, columns);
  }
}
