package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private static final String DBNAME="music.db";
    public static final String TABLE_ALBUMS="albums";
    public static final String COLUMN_ALBUM_ID="_id";
    public static final String COLUMN_ALBUM_NAME="name";
    public static final String COLUMN_ALBUM_ARTIST="artist";
    public static final String TABLE_ARTISTS="artists";
    public static final String COLUMN_ARTIST_ID="_id";
    public static final String COLUMN_ARTIST_NAME="name";
    public static final String TABLE_SONGS="songs";
    public static final String COLUMN_SONG_TRACK="track";
    public static final String COLUMN_SONG_ALBUM="album";
    public static final String COLUMN_SONG_ID="_id";
    public static final int ORDER_BY_NONE=0;
    public static final int ORDER_BY_ASC=1;
    public static final int ORDER_BY_DESC=2;
    public static final String connectionString="jdbc:sqlite:music.db";
    private Connection connection;
    public boolean open(){
        try{
            connection= DriverManager.getConnection(connectionString);
            return true;
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
        return false;
    }
    public void close()
    {
        try{
            if(connection!=null) {
                connection.close();
                //return true;
            }
        }
        catch(SQLException exception){
            System.out.println(exception.getMessage());
    }
    }
    public List<Artist> queryArtists(int sortType)
    {
        Statement statement=null;
        ResultSet results=null;
        try{
            statement=connection.createStatement();
            StringBuilder query=new StringBuilder(" SELECT * FROM ");
            query.append(TABLE_ARTISTS);
            if(sortType!=ORDER_BY_NONE) {
                query.append(" ORDER BY ");
                query.append(COLUMN_ARTIST_NAME);
                query.append(" COLLATE NOCASE ");
            }
           if(sortType==ORDER_BY_ASC)
            {
                query.append("ASC");
            }
           else{
               query.append("DESC");
           }
            results=statement.executeQuery(query.toString());
            List<Artist> Artists=new ArrayList<>();
            while (results.next())
            {
                Artist artist=new Artist();
                artist.set_id(results.getInt(COLUMN_ARTIST_ID));
                artist.setName(results.getString(COLUMN_ARTIST_NAME));
                Artists.add(artist);
            }
            return Artists;
        }
        catch(SQLException exception)
        {
            System.out.println(exception.getMessage());
            return null;
        }
        finally {
            try{
                if(results!=null)
                    results.close();

            }
            catch (SQLException exception)
            {
                System.out.println(exception.getMessage());
            }
                try{
                    if(statement!=null)
                        statement.close();
                }
                catch(SQLException exception)
                {

                }
        }

    }
    public List<String> queryAlbumsForArtist(String artistName,int sortType) {
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT ");
            query.append(TABLE_ALBUMS);
            query.append(".");
            query.append(COLUMN_ALBUM_NAME);
            query.append(" from ");
            query.append(TABLE_ALBUMS);
            query.append(" join ");
            query.append(TABLE_ARTISTS);
            query.append(" on ");
            query.append(TABLE_ALBUMS);
            query.append(".");
            query.append(COLUMN_ALBUM_ARTIST);
            query.append(" = ");
            query.append(TABLE_ARTISTS);
            query.append(".");
            query.append(COLUMN_ARTIST_ID);
            query.append(" WHERE ");
            query.append(TABLE_ARTISTS);
            query.append(".");
            query.append(COLUMN_ARTIST_NAME);
            query.append(" = \"");
            query.append(artistName);
            query.append("\"");
            if (sortType != ORDER_BY_NONE) {
                query.append(" ORDER BY ");
                query.append(TABLE_ALBUMS);
                query.append(".");
                query.append(COLUMN_ALBUM_NAME);
                query.append(" COLLATE NOCASE ");
            }
            if (sortType == ORDER_BY_ASC) {
                query.append("ASC ");
            } else {
                query.append("DESC ");
            }

            resultSet = statement.executeQuery(query.toString());
            System.out.println(resultSet.next());
            List<String> albumNamesOfArtist = new ArrayList<>();
            while (resultSet.next()) {
                albumNamesOfArtist.add(resultSet.getString(COLUMN_ALBUM_NAME));
            }
            System.out.println(albumNamesOfArtist);
            return albumNamesOfArtist;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return null;
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException exception) {

            }
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException exception) {

            }
        }
    }


}
