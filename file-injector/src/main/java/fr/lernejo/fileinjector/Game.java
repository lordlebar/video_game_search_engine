package fr.lernejo.fileinjector;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Game (
     Integer id,
     String title,
     String thumbnail,
     String short_description,
     String genre,
     String platform,
     String publisher,
     String developer,
     String release_date,
    @JsonProperty("freetogame_profile_url")
     String profile,
    @JsonProperty("game_url")
     String game
){}
