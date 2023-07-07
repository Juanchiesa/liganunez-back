package com.venedicto.liganunez.model.http;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DownloadStatsHttpResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-07-07T20:57:15.350055179Z[GMT]")
public class DownloadStatsHttpResponse extends HttpResponse {
	@JsonProperty("data")
	  private Integer data = null;

	  public DownloadStatsHttpResponse data(Integer data) {
	    this.data = data;
	    return this;
	  }

	  /**
	   * Get data
	   * @return data
	   **/
	  @Schema(description = "")
	  
	    public Integer getData() {
	    return data;
	  }

	  public void setData(Integer data) {
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
	    DownloadStatsHttpResponse downloadStatsHttpResponse = (DownloadStatsHttpResponse) o;
	    return Objects.equals(this.data, downloadStatsHttpResponse.data) &&
	        super.equals(o);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(data, super.hashCode());
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DownloadStatsHttpResponse {\n");
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