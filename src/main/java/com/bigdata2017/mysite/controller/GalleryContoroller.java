package com.bigdata2017.mysite.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bigdata2017.mysite.service.FileUploadService;
import com.bigdata2017.mysite.service.GalleryService;
import com.bigdata2017.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryContoroller {
	
	private static final Log LOG = LogFactory.getLog( GalleryContoroller.class );
	
	@Autowired
	private GalleryService galleryService; 
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = galleryService.list();
		model.addAttribute("list", list);
		LOG.debug(String.format("index: listSize:{3}", list.size()) );
		
		return "gallery/index";
	}
	
	@RequestMapping("/upload")
	public String upload(
			@RequestParam String comment, 
			@RequestParam( "file" ) MultipartFile file) {
		
		LOG.debug(String.format("upload: comment:{0}", comment));		
		String imageURL = fileUploadService.restore(file);
		GalleryVo vo = new GalleryVo();
		vo.setComments(comment);
		vo.setImageURL(imageURL);
		int count = galleryService.insert(vo);
		LOG.debug(count);
		
		return "redirect:/gallery";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable Long no) {
		int count = galleryService.delete(no);
		LOG.debug(String.format("delete: count:{0}", count) );
		return "redirect:/gallery";
	}

}
