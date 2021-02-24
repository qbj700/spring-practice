package com.sbs.springPractice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sbs.springPractice.dto.Article;
import com.sbs.springPractice.dto.ResultData;
import com.sbs.springPractice.util.Util;

@Service
public class ArticleService {

	private int articlesLastId;
	private List<Article> articles;

	public ArticleService() {
		articlesLastId = 0;
		articles = new ArrayList<>();

		articles.add(new Article(++articlesLastId, "2020-12-12 12:12:12", "2020-12-12 12:12:12", "제목1", "내용1"));
		articles.add(new Article(++articlesLastId, "2020-12-12 12:12:12", "2020-12-12 12:12:12", "제목2", "내용2"));
	}

	public Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}

		return null;
	}

	public List<Article> getArticles(String searchKeyword) {
		if (searchKeyword == null) {
			return articles;
		}
		
		List<Article> filtered = new ArrayList<>();
		
		for ( Article article : articles ) {
			if ( article.getTitle().contains(searchKeyword)) {
				filtered.add(article);
			}
		}
		
		return filtered;
	}

	public ResultData add(String title, String body) {
		String regDate = Util.getNowDateStr();
		String updateDate = Util.getNowDateStr();

		articles.add(new Article(++articlesLastId, regDate, updateDate, title, body));

		return new ResultData("S-1", "성공하였습니다.", "id", articlesLastId);
	}

	public ResultData deleteArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				articles.remove(article);
				return new ResultData("S-1", "성공하였습니다.", "id", id);
			}
		}
		return new ResultData("F-1", String.format("%d번 게시물은 존재하지않습니다.", id));
	}

	public ResultData modify(int id, String title, String body) {
		
		Article article = getArticle(id);

		article.setTitle(title);
		article.setBody(body);
		article.setUpdateDate(Util.getNowDateStr());

		return new ResultData("S-1", "게시물을 수정하였습니다.", "id", id);
	}

}
