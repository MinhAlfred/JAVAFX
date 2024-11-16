
package org.example.hsf301.dtos.request;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class KoiRequest {

    private String koiName;
    private String image;
    private String description;
    private String color;
    private String origin;

}
