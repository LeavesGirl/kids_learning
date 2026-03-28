package com.kidslearning.controller;

import com.kidslearning.entity.ChineseCharacter;
import com.kidslearning.entity.query.CharacterQuery;
import com.kidslearning.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/character")
public class CharacterController extends BaseController<ChineseCharacter, CharacterQuery> {

    @Autowired      
    private CharacterService characterService;
   
    @Autowired
    public void setCharacterService(CharacterService characterService) {
        this.baseService = characterService;
    }
   
}