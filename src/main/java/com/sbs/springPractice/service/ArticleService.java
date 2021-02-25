package com.sbs.springPractice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.springPractice.dao.ArticleDao;
import com.sbs.springPractice.dto.Article;
import com.sbs.springPractice.dto.ResultData;
import com.sbs.springPractice.util.Util;

@Service
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private MemberService memberService;

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public List<Article> getArticles(String searchKeywordType, String searchKeyword) {
		return articleDao.getArticles(searchKeywordType, searchKeyword);
	}

	public ResultData addArticle(Map<String, Object> param) {
		articleDao.addArticle(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", "성공하였습니다.", "id", id);
	}

	public ResultData deleteArticle(int id) {
		articleDao.deleteArticle(id);
		
		return new ResultData("S-1", "성공하였습니다.", "id", id);
	}

	public ResultData modifyArticle(int id, String title, String body) {
		articleDao.modifyArticle(id, title, body);

		return new ResultData("S-1", "게시물을 수정하였습니다.", "id", id);
	}

	public ResultData getActorCanModifyRd(Article article, int actorId) {
		if ( article.getMemberId() == actorId) {
			return new ResultData("S-1", "가능합니다.");
		}
		
		if ( memberService.isAdmin(actorId)) {
			return new ResultData("S-1", "가능합니다.");
		}
		
		return new ResultData("F-1", "해당 게시물의 권한이 존재하지 않습니다.");
	}

	public ResultData getActorCanDeleteRd(Article article, int actorId) {
		
		return getActorCanModifyRd(article, actorId);
	}

	public Article getForPrintArticle(int id) {
		return articleDao.getForPrintArticle(id);
	}

}
