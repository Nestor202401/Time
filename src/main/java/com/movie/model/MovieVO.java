package com.movie.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.movieimg.model.MovieImgVO;
import com.movietime.model.MovieTimeVO;

@Entity
@Table(name = "movie")
public class MovieVO implements java.io.Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", updatable = false)
    private Integer movieId;
    
    @Column(name = "movie_name")
    private String movieName;
    
    @Column(name = "movie_rating")
    private Integer movieRating;
    
    @Column(name = "director")
    private String director;
    
    @Column(name = "actor")
    private String actor;
    
    @Column(name = "release_date")
    private Date releaseDate;
    
    @Column(name = "end_date")
    private Date endDate;
    
    @Column(name = "runtime")
    private Integer runtime;
    
    @Column(name = "introduction", columnDefinition="TEXT")
    private String introduction;
    
    @OneToMany(mappedBy = "movieVO", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieImgVO> movieImgs;
    
    @OneToMany(mappedBy = "movieVO", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieTimeVO> movieTime;

    public Set<MovieTimeVO> getMovieTime() {
		return movieTime;
	}

	public void setMovieTime(Set<MovieTimeVO> movieTime) {
		this.movieTime = movieTime;
	}

	// getters and setters
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(Integer movieRating) {
        this.movieRating = movieRating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Set<MovieImgVO> getMovieImgs() {
        return movieImgs;
    }

    public void setMovieImgs(Set<MovieImgVO> movieImgs) {
        this.movieImgs = movieImgs;
    }

	@Override
	public String toString() {
		return "MovieVO [movieId=" + movieId + ", movieName=" + movieName + ", movieRating=" + movieRating
				+ ", director=" + director + ", actor=" + actor + ", releaseDate=" + releaseDate + ", endDate="
				+ endDate + ", runtime=" + runtime + ", introduction=" + introduction + ", movieImgs=" + movieImgs
				+ ", movieTime=" + movieTime + ", getMovieTime()=" + getMovieTime() + ", getMovieId()=" + getMovieId()
				+ ", getMovieName()=" + getMovieName() + ", getMovieRating()=" + getMovieRating() + ", getDirector()="
				+ getDirector() + ", getActor()=" + getActor() + ", getReleaseDate()=" + getReleaseDate()
				+ ", getEndDate()=" + getEndDate() + ", getRuntime()=" + getRuntime() + ", getIntroduction()="
				+ getIntroduction() + ", getMovieImgs()=" + getMovieImgs() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
