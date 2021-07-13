package com.rantanplan.model;

import lombok.Builder;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Builder
public class Regions {
    List<Region> regions;

    public Region getProteinRegion(){
        return regions.stream()
                .filter(Region::isProtein)
                .findFirst().get();
    }
    public int size(){
        return regions.size();
    }
    public Stream<Region> stream(){
        return regions.stream();
    }

    public String toString(){
        return regions.toString();
    }

    public static class RegionsBuilder{

        public RegionsBuilder region(Region region){
            ArrayList<Region> newRegions = Optional.ofNullable(regions)
                    .map(ArrayList::new)
                    .orElse(new ArrayList<>());
            newRegions.add(region);
            regions = newRegions;
            return this;
        }
        public Regions build() {
            hasToContainProteinRegion();
            return new Regions(regions);
        }

        private void hasToContainProteinRegion() {
            Optional.ofNullable(regions)
                    .orElse(new ArrayList<>())
                    .stream()
                    .filter(Region::isProtein)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Regions must contain a protein region to translate"));
        }
    }
}
