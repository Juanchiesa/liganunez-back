package com.venedicto.liganunez.model;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * HttpResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-18T18:56:59.203396144Z[GMT]")


public class HttpResponse   {
  @JsonProperty("opCode")
  private String opCode = null;

  @JsonProperty("errors")
  private Error errors = null;

  public HttpResponse opCode(String opCode) {
    this.opCode = opCode;
    return this;
  }

  /**
   * Get opCode
   * @return opCode
   **/
  @Schema(example = "404", description = "")
  
    public String getOpCode() {
    return opCode;
  }

  public void setOpCode(String opCode) {
    this.opCode = opCode;
  }

  public HttpResponse errors(Error errors) {
    this.errors = errors;
    return this;
  }

  /**
   * Get errors
   * @return errors
   **/
  @Schema(description = "")
  
    @Valid
    public Error getErrors() {
    return errors;
  }

  public void setErrors(Error errors) {
    this.errors = errors;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HttpResponse httpResponse = (HttpResponse) o;
    return Objects.equals(this.opCode, httpResponse.opCode) &&
        Objects.equals(this.errors, httpResponse.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(opCode, errors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HttpResponse {\n");
    
    sb.append("    opCode: ").append(toIndentedString(opCode)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
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
