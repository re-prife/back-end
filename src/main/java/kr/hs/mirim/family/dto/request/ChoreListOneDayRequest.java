package kr.hs.mirim.family.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChoreListOneDayRequest {
    @NotNull
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate date;
}