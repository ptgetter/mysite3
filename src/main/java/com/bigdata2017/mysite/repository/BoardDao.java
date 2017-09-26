package com.bigdata2017.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigdata2017.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int write(BoardVo boardVo) {
		int count = sqlSession.insert("board.insert", boardVo);
		return count;
	}
	
	public int getTotalCount( String keyword ) {
		int totalCount = sqlSession.selectOne("board.getTotalCount", keyword);
		return totalCount;
	}
	
	public List<BoardVo> getList( String keyword, Integer page, Integer size ) {
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("keyword", keyword);
		parameterMap.put("page", page);
		parameterMap.put("size", size);
		List<BoardVo> list = sqlSession.selectList("board.getlist", parameterMap);
		
		return list;
	}
	
	public BoardVo get( Long boardNo ) {
		BoardVo vo = sqlSession.selectOne("board.get", boardNo);
		return vo;
	}
	
	public void increaseGroupOrder(BoardVo boardVo ){
		sqlSession.update("board.increaseGroupOrder", boardVo);
	}
	
	public void updateHit(Long boardNo ){
		sqlSession.update("board.updateHit", boardNo);
	}
	
	public void update( BoardVo boardVo ) {
		sqlSession.update("board.update", boardVo);
	}
	
	
	public void delete(BoardVo boardVo) {
		sqlSession.delete("board.delete", boardVo);
	}
	
}
