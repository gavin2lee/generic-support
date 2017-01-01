package com.lachesis.support.auth.demo.redis;

import java.util.Date;

public class Article {
	private Long id;
	private String title;
	private String link;
	private People poster;
	private Date pubAt;
	private Integer votes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public People getPoster() {
		return poster;
	}

	public void setPoster(People poster) {
		this.poster = poster;
	}

	public Date getPubAt() {
		return pubAt;
	}

	public void setPubAt(Date pubAt) {
		this.pubAt = pubAt;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

}
