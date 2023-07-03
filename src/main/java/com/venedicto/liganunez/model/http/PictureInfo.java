package com.venedicto.liganunez.model.http;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PictureInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-30T19:42:06.417708714Z[GMT]")


public class PictureInfo   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("status")
  private String status = null;

  public PictureInfo id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", description = "")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PictureInfo status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @Schema(example = "201", description = "")
  
    public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PictureInfo pictureInfo = (PictureInfo) o;
    return Objects.equals(this.id, pictureInfo.id) &&
        Objects.equals(this.status, pictureInfo.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PictureInfo {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
