package arturnikytenko.authapi.Controllers.ApiControllers;

import arturnikytenko.authapi.Models.ProcessingLog.DTO.ProcessRequest;
import arturnikytenko.authapi.Models.ProcessingLog.DTO.ProcessResponse;
import arturnikytenko.authapi.Services.ProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProcessController {

    private final ProcessingService processingService;

    public ProcessController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/process")
    public ResponseEntity<ProcessResponse> process(
            @RequestBody ProcessRequest request,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());

        String result = processingService.process(
                userId,
                request.text()
        );

        return ResponseEntity.ok(new ProcessResponse(result));
    }
}