package cu.example.application.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorInfo {

    private String uri;

    private String method;

    private String message;

    @JsonProperty("http_status")
    private String httpStatus;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("code_error")
    private int errorCode;

    @JsonProperty("exception_type")
    private String type;
}
