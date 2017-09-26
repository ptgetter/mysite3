package com.bigdata2017.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigdata2017.mysite.repository.GuestbookDao;
import com.bigdata2017.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookDao guestbookDao;
	public List<GuestbookVo> getMessageList() {
		List<GuestbookVo> list = guestbookDao.getList(); 
		return list;
	}
	
	public List<GuestbookVo> getMessageList(long no) {
		List<GuestbookVo> list = guestbookDao.getList(no); 
		return list;
	}
	
	public int insert(GuestbookVo vo) {
		int count = guestbookDao.insert(vo);
		return count;
	}
	
	public int delete(GuestbookVo vo) {
		int count = guestbookDao.delete(vo);
		return count;
	}
}
