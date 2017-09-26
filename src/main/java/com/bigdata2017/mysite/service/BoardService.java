package com.bigdata2017.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigdata2017.mysite.repository.BoardDao;
import com.bigdata2017.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	private static final int LIST_SIZE = 5; //리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; //페이지 리스트의 페이지 수
	
	@Autowired
	private BoardDao boardDao;
	
	
	public int write(BoardVo boardVo) {
		return boardDao.write(boardVo);		
	}
	public int getTotalCount( String keyword ) {
		return boardDao.getTotalCount(keyword);
	}
	
	public Map<String, Object> getMessageList( int currentPage, String keyword ){
		//1. 페이징을 위한 기본 데이터 계산
		int totalCount = boardDao.getTotalCount( keyword ); 
		int pageCount = (int)Math.ceil( (double)totalCount / LIST_SIZE );
		int blockCount = (int)Math.ceil( (double)pageCount / PAGE_SIZE );
		int currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
		
		//2. 파라미터 page 값  검증
		if( currentPage < 1 ) {
			currentPage = 1;
			currentBlock = 1;
		} else if( currentPage > pageCount ) {
			currentPage = pageCount;
			currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
		}
		
		//3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1)*PAGE_SIZE + 1;
		int prevPage = ( currentBlock > 1 ) ? ( currentBlock - 1 ) * PAGE_SIZE : 0;
		int nextPage = ( currentBlock < blockCount ) ? currentBlock * PAGE_SIZE + 1 : 0;
		int endPage = ( nextPage > 0 ) ? ( beginPage - 1 ) + LIST_SIZE : pageCount;
		
		//4. 리스트 가져오기
		List<BoardVo> list = boardDao.getList( keyword, currentPage, LIST_SIZE );
		
		//5. 리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put( "list", list );
		map.put( "totalCount", totalCount );
		map.put( "listSize", LIST_SIZE );
		map.put( "currentPage", currentPage );
		map.put( "beginPage", beginPage );
		map.put( "endPage", endPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		map.put( "keyword", keyword );
		
		return map;
	}
	
	public List<BoardVo> getList( String keyword, Integer page, Integer size ) {
		return boardDao.getList(keyword, page, size);
	}
	
	public BoardVo get (Long boardNo) {
		return boardDao.get(boardNo);
	}
	
	public void increaseGroupOrder(BoardVo boardVo ){
		boardDao.increaseGroupOrder(boardVo);
	}
	
	public void updateHit(Long boardNo ){
		boardDao.updateHit(boardNo);
	}
	
	public void update(BoardVo boardVo) {
		boardDao.update(boardVo);
	}
	
	public void delete(BoardVo boardVo) {
		boardDao.delete(boardVo);
	}
	

	

}
