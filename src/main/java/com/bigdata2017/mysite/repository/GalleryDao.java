package com.bigdata2017.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigdata2017.mysite.vo.GalleryVo;

@Repository
public class GalleryDao {

	@Autowired
	private SqlSession sqlSession;

	public int insert(GalleryVo vo) {
		int count = sqlSession.delete("gallery.insert", vo);
		return count;
	}

	public List<GalleryVo> list() {
		List<GalleryVo> list = sqlSession.selectList("gallery.getlist");
		return list;
	}

	public int delete(Long no) {
		int count = sqlSession.delete("gallery.delete", no);
		return count;
	}

}
