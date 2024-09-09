package yt.vibe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Currency {

    @JsonIgnore
    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Unique identifier of the currency", example = "USD")
    @Column(unique = true)
    private String code;
    @Schema(description = "Currency rate ", example = "90.8")
    @Min(value = 0, message = "Rate must be positive")
    private Double rate;

    public Currency(String code, Double rate) {
        this.code = code;
        this.rate = rate;
    }
}
