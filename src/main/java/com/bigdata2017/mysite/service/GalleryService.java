package com.bigdata2017.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigdata2017.mysite.repository.GalleryDao;
import com.bigdata2017.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;

	public int insert(GalleryVo vo) {
		return galleryDao.insert(vo);
	}
	
	public List<GalleryVo> list() {
		return galleryDao.list();
	}
	
	public int delete(Long no) {
		return galleryDao.delete(no);
	}
}
