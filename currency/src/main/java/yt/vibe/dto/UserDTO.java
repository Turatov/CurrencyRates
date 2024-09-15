package yt.vibe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    @Schema(description = "User authority ('USER' || 'ADMIN' ", example = "ADMIN")
    private String role;
    @Schema(description = " Code from authenticated user to creat user with 'ADMIN' authority  ", example = "TOTORO")
    private String referenceCode;

}
