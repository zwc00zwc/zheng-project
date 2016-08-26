package domain.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by XR on 2016/8/26.
 */
public class PermLevelDto extends BaseDto {
    private Long id;
    private String displayName;
    private List<PermLevelDto> permLevelDtos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<PermLevelDto> getPermLevelDtos() {
        return permLevelDtos;
    }

    public void setPermLevelDtos(List<PermLevelDto> permLevelDtos) {
        this.permLevelDtos = permLevelDtos;
    }
}
