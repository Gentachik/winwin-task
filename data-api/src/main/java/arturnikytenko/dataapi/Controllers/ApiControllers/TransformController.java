package arturnikytenko.dataapi.Controllers.ApiControllers;

import arturnikytenko.dataapi.Models.DTO.TransformRequest;
import arturnikytenko.dataapi.Models.DTO.TransformResponse;
import arturnikytenko.dataapi.Service.TransformService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransformController {
    private final TransformService transformService;
    private final String internalToken;

    public TransformController(
            TransformService transformService,
            @Value("${internal.token}") String internalToken
    ) {
        this.transformService = transformService;
        this.internalToken = internalToken;
    }

    @PostMapping("/transform")
    public ResponseEntity<TransformResponse> transform(
            @RequestHeader(value = "X-Internal-Token", required = false) String token,
            @RequestBody TransformRequest request
    ) {
        if (token == null || !token.equals(internalToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String result = transformService.transform(request.text());

        return ResponseEntity.ok(new TransformResponse(result));
    }
}
