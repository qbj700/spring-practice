package com.sbs.springPractice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.springPractice.dao.ArticleDao;
import com.sbs.springPractice.dto.Article;
import com.sbs.springPractice.dto.ResultData;

@Service
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public List<Article> getArticles(String searchKeywordType, String searchKeyword) {
		return articleDao.getArticles(searchKeywordType, searchKeyword);
	}

	public ResultData addArticle(String title, String body) {
		return articleDao.addArticle(title, body);
	}

	public ResultData deleteArticle(int id) {
		boolean rs = articleDao.deleteArticle(id);
		
		if (rs == false) {
			new ResultData("F-1", String.format("%d번 게시물은 존재하지않습니다.", id));
		}
		
		return new ResultData("S-1", "성공하였습니다.", "id", id);
	}

	public ResultData modifyArticle(int id, String title, String body) {
		return articleDao.modifyArticle(id, title, body);
	}

}
