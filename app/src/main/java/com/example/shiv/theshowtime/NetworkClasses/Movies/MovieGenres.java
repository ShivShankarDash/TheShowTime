package com.example.shiv.theshowtime.NetworkClasses.Movies;

import java.util.HashMap;
import java.util.List;

public class MovieGenres {


private static HashMap<Integer,String> genresMap;

public static boolean isGenresListLoaded(){
    return (genresMap!=null);
}

public static void loadGenresList(List<Genres> genres){

    if(genres==null) return;
    genresMap=new HashMap<>();
    for(Genres genres1:genres){

        genresMap.put(genres1.getId(),genres1.getGenreName());
    }
}

public static String getGenreName(Integer genreId){

    if(genreId==null) return null;
    return genresMap.get(genreId);

}





}
