package com.sbs.springPractice.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sbs.springPractice.dto.Article;
import com.sbs.springPractice.dto.ResultData;
import com.sbs.springPractice.util.Util;

@Component
public class ArticleDao {
	private int articlesLastId;
	private List<Article> articles;

	public ArticleDao() {
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

	public List<Article> getArticles(String searchKeywordType, String searchKeyword) {
		if (searchKeyword == null) {
			return articles;
		}

		List<Article> filtered = new ArrayList<>();

		for (Article article : articles) {
			boolean contains = false;

			if (searchKeywordType.equals("title")) {
				contains = article.getTitle().contains(searchKeyword);
			} else if (searchKeywordType.equals("body")) {
				contains = article.getBody().contains(searchKeyword);
			} else {
				contains = article.getTitle().contains(searchKeyword);

				if (contains == false) {
					contains = article.getBody().contains(searchKeyword);
				}

			}

			if (contains) {
				filtered.add(article);
			}
		}

		return filtered;
	}

	public ResultData addArticle(String title, String body) {
		String regDate = Util.getNowDateStr();
		String updateDate = Util.getNowDateStr();

		articles.add(new Article(++articlesLastId, regDate, updateDate, title, body));

		return new ResultData("S-1", "성공하였습니다.", "id", articlesLastId);
	}

	public boolean deleteArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				articles.remove(article);
				return true;
			}
		}
		return false;
	}

	public ResultData modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);

		article.setTitle(title);
		article.setBody(body);
		article.setUpdateDate(Util.getNowDateStr());

		return new ResultData("S-1", "게시물을 수정하였습니다.", "id", id);
	}

}
