package api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors (chain = true)
public class Order {
    @JsonProperty("id")
    Object id;
    @JsonProperty("petId")
    Object petId;
    @JsonProperty("quantity")
    Object quantity;
    @JsonProperty("shipDate")
    Object shipDate;
    @JsonProperty("status")
    Object status;
    @JsonProperty("complete")
    Object complete;
}