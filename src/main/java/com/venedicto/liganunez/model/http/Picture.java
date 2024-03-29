package com.venedicto.liganunez.model.http;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Picture
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-30T17:31:40.208117412Z[GMT]")


public class Picture   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("tournamentId")
  private String tournamentId = null;

  @JsonProperty("date")
  private String date = null;

  @JsonProperty("place")
  private String place = null;

  @JsonProperty("file")
  private String file = null;

  public Picture id(String id) {
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

  public Picture tournamentId(String tournamentId) {
    this.tournamentId = tournamentId;
    return this;
  }

  /**
   * Get tournamentId
   * @return tournamentId
   **/
  @Schema(example = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", description = "")
  
    public String getTournamentId() {
    return tournamentId;
  }

  public void setTournamentId(String tournamentId) {
    this.tournamentId = tournamentId;
  }

  public Picture date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
   **/
  @Schema(example = "15/06/2023", description = "")
  
    public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Picture place(String place) {
    this.place = place;
    return this;
  }

  /**
   * Get place
   * @return place
   **/
  @Schema(example = "La Plata", description = "")
  
    public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public Picture file(String file) {
    this.file = file;
    return this;
  }

  /**
   * Get file
   * @return file
   **/
  @Schema(example = "bh5aLyZALN4othXL2mByHo1aZA5ts5k/uw/sc7DBngGY......", description = "")
  
    public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Picture picture = (Picture) o;
    return Objects.equals(this.id, picture.id) &&
        Objects.equals(this.tournamentId, picture.tournamentId) &&
        Objects.equals(this.date, picture.date) &&
        Objects.equals(this.place, picture.place) &&
        Objects.equals(this.file, picture.file);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tournamentId, date, place, file);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Picture {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tournamentId: ").append(toIndentedString(tournamentId)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    place: ").append(toIndentedString(place)).append("\n");
    sb.append("    file: ").append(toIndentedString(file)).append("\n");
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
