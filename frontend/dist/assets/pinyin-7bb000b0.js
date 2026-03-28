import{a as t}from"./axios-8531ffad.js";const p={list:i=>t.get("/pinyin/listData",{params:i}),detail:i=>t.get(`/pinyin/detail/${i}`),update:(i,a)=>t.put(`/pinyin/update/${i}`,a)};export{p};
