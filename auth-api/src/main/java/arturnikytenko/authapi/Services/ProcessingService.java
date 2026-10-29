package arturnikytenko.authapi.Services;

import arturnikytenko.authapi.Models.ProcessingLog.DTO.ProcessRequest;
import arturnikytenko.authapi.Models.ProcessingLog.DTO.ProcessResponse;
import arturnikytenko.authapi.Models.ProcessingLog.ProcessingLog;
import arturnikytenko.authapi.Repositories.ProcessingLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
public class ProcessingService {

    private final ProcessingLogRepository processingLogRepository;
    private final RestClient restClient;
    private final String internalToken;

    public ProcessingService(
            ProcessingLogRepository processingLogRepository,
            @Value("${data-api.base-url}") String dataApiBaseUrl,
            @Value("${internal.token}") String internalToken
    ) {
        this.processingLogRepository = processingLogRepository;
        this.restClient = RestClient.builder()
                .baseUrl(dataApiBaseUrl)
                .build();
        this.internalToken = internalToken;
    }

    public String process(UUID userId, String text) {
        ProcessResponse response = restClient.post()
                .uri("/api/transform")
                .header("X-Internal-Token", internalToken)
                .body(new ProcessRequest(text))
                .retrieve()
                .body(ProcessResponse.class);

        if (response == null) {
            throw new IllegalStateException(
                    "data-api returned an empty response"
            );
        }

        ProcessingLog log = new ProcessingLog(
                userId,
                text,
                response.result()
        );

        processingLogRepository.save(log);

        return response.result();
    }
}