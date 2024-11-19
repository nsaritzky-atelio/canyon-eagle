package org.atelio.demo.model.demographicInfo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.util.List;

@JsonSerialize
@ToString
@AllArgsConstructor
@Getter
public class DemographicInfo {
    private String name;

    private Type type;

    private String lastFourCard;

    @Nullable
    private String lastFourSsn;

    @Nullable
    private String address;

    private List<String> phoneNumbers;

    private String cardAddress;
}
