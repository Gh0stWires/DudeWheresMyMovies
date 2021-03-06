package tk.samgrogan.dudewheresmymovies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gh0st on 2/1/2016.
 */
public class Movies {

    private List<String> movies = new ArrayList<String>();
    private List<String> titles = new ArrayList<String>();
    private List<String> desc = new ArrayList<String>();
    private List<String> rating = new ArrayList<String>();
    private List<String> releaseDate = new ArrayList<String>();
    private List<String> mId = new ArrayList<String>();
    private List<String> posters = new ArrayList<String>();

    public Movies(){

    }

    public List<String> getMList(){
        return movies;
    }

    public List<String> getTList(){
        return titles;
    }

    public List<String> getDList(){
        return desc;
    }

    public List<String> getRList(){
        return rating;
    }

    public List<String> getRDList(){
        return releaseDate;
    }

    public List<String> getIDList(){
        return mId;
    }

    public List<String> getPosters(){
        return posters;
    }

    public void setMovies(String movie){
        this.movies.add(movie);
    }

    public String getMovies(int position){
        return this.movies.get(position);
    }

    public void setTitles(String title){
        this.titles.add(title);
    }

    public String getTitles(int position){
        return this.titles.get(position);
    }

    public void setDesc(String desc){
        this.desc.add(desc);
    }

    public String getDesc(int position){
        return this.desc.get(position);
    }

    public void setRating(String rating){
        this.rating.add(rating);
    }

    public String getRating(int position){
        return this.rating.get(position);
    }

    public void setReleaseDate(String date){
        this.releaseDate.add(date);
    }

    public String getReleaseDate(int position){
        return this.releaseDate.get(position);
    }

    public void setmId(String id){
        this.mId.add(id);
    }

    public String getmId(int position){
        return this.mId.get(position);
    }

    public void setPoster(String poster){
        this.posters.add(poster);
    }

    public String getPoster(int position){
        return  this.posters.get(position);
    }

    public void setMList(List<String> list){
        this.movies = list;
    }

    public void setTList(List<String> list){
        this.titles = list;
    }

    public  void setDList(List<String> list){
        this.desc = list;
    }

    public void setRList(List<String> list){
        this.rating = list;
    }

    public void setRDList(List<String> list){
        this.releaseDate = list;
    }

    public void setIDList(List<String> list){
        this.mId = list;
    }




}
