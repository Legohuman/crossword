package ru.dlevin.cross.dto;

import java.util.ArrayList;
import java.util.List;

public class PlacementsDto {
    private List<PlacementDto> placements = new ArrayList<>();

    public List<PlacementDto> getPlacements() {
        return placements;
    }

    public void setPlacements(List<PlacementDto> placements) {
        this.placements = placements;
    }
}
