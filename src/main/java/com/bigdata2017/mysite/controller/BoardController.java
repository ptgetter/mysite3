package com.bigdata2017.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bigdata2017.mysite.service.BoardService;
import com.bigdata2017.mysite.vo.BoardVo;
import com.bigdata2017.mysite.vo.UserVo;
import com.bigdata2017.security.Auth;
import com.bigdata2017.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping( "" )
	public String index(
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
		Model model ) {
		
		Map<String, Object> map = 
				boardService.getMessageList( page, keyword );
		
		model.addAttribute( "map", map );
		return "board/list";
	}
	
	@RequestMapping("/view")
	public String View(
			@RequestParam( value="no", required=true, defaultValue="1") Long no,
			@RequestParam( value="p", required=true, defaultValue="1") Integer page,
			@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
			Model model) {
		
		boardService.updateHit(no);
		BoardVo boardVo = boardService.get(no);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		return "board/view";
	}
		
	@Auth
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String Write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String Write(@ModelAttribute BoardVo boardVo, @AuthUser UserVo authUser) {
		
		boardVo.setUserName(authUser.getName());
		boardVo.setUserNo(authUser.getNo());
		boardService.write(boardVo);
		
		return "redirect:/board";
	}
}
