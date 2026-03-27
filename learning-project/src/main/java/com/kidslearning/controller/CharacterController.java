package com.kidslearning.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.ChineseCharacter;
import com.kidslearning.entity.query.CharacterQuery;
import com.kidslearning.service.BaseService;
import com.kidslearning.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/character")
public class CharacterController extends BaseController<ChineseCharacter, CharacterQuery> {

    @Autowired      
    private CharacterService characterService;

}