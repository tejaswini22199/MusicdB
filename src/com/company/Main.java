package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here
        DataSource ds = new DataSource();

        if (!ds.open()) {
            System.out.println("cant open");
        }
//        List<Artist> artists = ds.queryArtists(2);
//        if (artists == null){
//            System.out.println("no artists");
//        return;
  //}
//        for(Artist artist:artists)
//        {
//            System.out.println(artist.get_id()+" "+artist.getName());
//        }
        List<String> albums= ds.queryAlbumsForArtist("Iron Maiden",DataSource.ORDER_BY_ASC);
        if(albums==null)
            return;
        for(String album:albums)
        {
            System.out.println(album);
        }
        ds.close();
    }
}
