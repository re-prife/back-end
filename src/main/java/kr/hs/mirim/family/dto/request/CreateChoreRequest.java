package kr.hs.mirim.family.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChoreRequest {
    @NotEmpty
    String choreTitle;

    @NotNull
    String choreCategory;

    @NotNull
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate choreDate;

    @NotNull
    long choreUserId;
}
