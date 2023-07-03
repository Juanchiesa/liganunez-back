package com.venedicto.liganunez.model.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * UploadPicturesHttpResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-30T19:42:06.417708714Z[GMT]")


public class UploadPicturesHttpResponse extends HttpResponse  {
  @JsonProperty("data")
  @Valid
  private List<PictureInfo> data = null;

  public UploadPicturesHttpResponse data(List<PictureInfo> data) {
    this.data = data;
    return this;
  }

  public UploadPicturesHttpResponse addDataItem(PictureInfo dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<PictureInfo>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
   **/
  @Schema(description = "")
      @Valid
    public List<PictureInfo> getData() {
    return data;
  }

  public void setData(List<PictureInfo> data) {
    this.data = data;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UploadPicturesHttpResponse uploadPicturesHttpResponse = (UploadPicturesHttpResponse) o;
    return Objects.equals(this.data, uploadPicturesHttpResponse.data) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UploadPicturesHttpResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
