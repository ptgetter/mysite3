package com.bigdata2017.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.bigdata2017.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int delete( GuestbookVo vo ) {
		int count = sqlSession.delete("guestbook.delete", vo);
		return count;
	}
	
	public int insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo);
		System.out.println(vo.getNo());
		return count;
	}

	public List<GuestbookVo> getList() {
		
		List<GuestbookVo> list = sqlSession.selectList("guestbook.getlist");
		
		return list;
	}
	
	public List<GuestbookVo> getList(long no) {
		List<GuestbookVo> list = sqlSession.selectList("guestbook.getlist2", no);
		return list;
	}
}
