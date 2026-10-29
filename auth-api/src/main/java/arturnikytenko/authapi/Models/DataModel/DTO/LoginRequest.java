package arturnikytenko.authapi.Models.DataModel.DTO;

public record LoginRequest(String email,
                           String password
) {
}
