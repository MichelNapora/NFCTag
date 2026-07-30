package com.nfctag.features.presence;

import java.util.List;

/** Métadonnées des filtres de la page Interventions : années disponibles et compteurs. */
public class SearchMetaDTO {

    private List<Integer> years;
    private long all;
    private long ongoing;
    private long done;
    private long estimated;
    private long suspect;

    public SearchMetaDTO(List<Integer> years, long all, long ongoing, long done, long estimated, long suspect){
        this.years=years;
        this.all=all;
        this.ongoing=ongoing;
        this.done=done;
        this.estimated=estimated;
        this.suspect=suspect;
    }

    public SearchMetaDTO(){}

    public List<Integer> getYears(){
        return this.years;
    }

    public long getAll(){
        return this.all;
    }

    public long getOngoing(){
        return this.ongoing;
    }

    public long getDone(){
        return this.done;
    }

    public long getEstimated(){
        return this.estimated;
    }

    public long getSuspect(){
        return this.suspect;
    }
}