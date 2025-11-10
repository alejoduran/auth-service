package org.auth.model.dto;

import java.util.List;

public class ErrorResponse {
  private List<ErrorDetail> error;

  public ErrorResponse(List<ErrorDetail> error) {
    this.error = error;
  }

  public List<ErrorDetail> getError() {
    return error;
  }

  public void setError(List<ErrorDetail> error) {
    this.error = error;
  }
}
