package chae4ek.transgura.third_party.ldtk;

import com.fasterxml.jackson.annotation.*;

/** Level background image position info */
public class LevelBackgroundPosition {
  private double[] cropRect;
  private double[] scale;
  private int[] topLeftPx;

  /**
   * An array of 4 float values describing the cropped sub-rectangle of the displayed background
   * image. This cropping happens when original is larger than the level bounds. Array format: `[
   * cropX, cropY, cropWidth, cropHeight ]`
   */
  @JsonProperty("cropRect")
  public double[] getCropRect() {
    return cropRect;
  }

  @JsonProperty("cropRect")
  public void setCropRect(final double[] value) {
    cropRect = value;
  }

  /**
   * An array containing the `[scaleX,scaleY]` values of the **cropped** background image, depending
   * on `bgPos` option.
   */
  @JsonProperty("scale")
  public double[] getScale() {
    return scale;
  }

  @JsonProperty("scale")
  public void setScale(final double[] value) {
    scale = value;
  }

  /**
   * An array containing the `[x,y]` pixel coordinates of the top-left corner of the **cropped**
   * background image, depending on `bgPos` option.
   */
  @JsonProperty("topLeftPx")
  public int[] getTopLeftPx() {
    return topLeftPx;
  }

  @JsonProperty("topLeftPx")
  public void setTopLeftPx(final int[] value) {
    topLeftPx = value;
  }
}
