package tk.samgrogan.dudewheresmymovies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gh0st on 2/1/2016.
 */
public class SingleMovie {

    private List<String> mTrailer = new ArrayList<String>();
    private List<String> trailerTitle = new ArrayList<String>();
    private List<String> mContent = new ArrayList<String>();
    private String title;
    private String mId;
    private String poster;
    private String desc;
    private String rate;
    private String date;

    public SingleMovie(){

    }

    public void setmContent(String item){
        this.mContent.add(item);
    }

    public List<String> getmContent(){
        return this.mContent;
    }

    public String getContentList(int position){
        return this.mContent.get(position);
    }

    public void setTrailer(String trailer){
        this.mTrailer.add(trailer);
    }

    public String getTrailer(int position){
        return this.mTrailer.get(position);
    }

    public void setTrailerTitle(String title){
        this.trailerTitle.add(title);
    }

    public List<String> getTrailerTitle(){
        return this.trailerTitle;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setmId(String id){
        this.mId = id;
    }

    public String getmId(){
        return this.mId;
    }

    public void setPoster(String poster){
        this.poster = poster;
    }

    public String getPoster(){
        return this.poster;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return this.desc;
    }

    public void setRate(String rate){
        this.rate = rate;
    }

    public String getRate(){
        return this.rate;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return this.date;
    }
}
